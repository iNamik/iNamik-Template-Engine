/*
 * $Id: TemplateEngine.java,v 1.14 2006-09-28 09:38:39 dave Exp $
 *
 * iNamik Template Engine
 * Copyright (C) 2006 David Farrell (davidpfarrell@yahoo.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.inamik.template;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.inamik.template.cache.EasyHibernateCacheFactory;
import com.inamik.template.cache.WeakReferenceCache;
import com.inamik.template.util.ClassLoaderUtil;
import com.inamik.template.util.StreamUtil;
import com.inamik.template.util.TemplateConfigUtil;

/**
 * TemplateEngine - A factory capable of generating templates and macros.
 * <p>
 * When applicable, the template engine will cache for faster retrieval and
 * execution.
 * <p>
 * Created on Jul 27, 2006
 * @author Dave
 * @see com.inamik.template.TemplateEngineConfig
 * @see com.inamik.template.Template
 * @see com.inamik.template.TemplateMacro
 */
public abstract class TemplateEngine
{
//	/**
//	 * TemplateEngine.getInstance() will search for this file, first on the
//	 * filesystem, then on the classpath.
//	 */
//	public  static final String DEFAULT_ENGINE_CONFIG = "inamik_template_engine.xml";

	private static final String INAMIK_LIB_MAIN = "com/inamik/template/lib/main/inamik_template_lib-main.xml";

	private static final String PROTOCOL_FILE = "file";

	private static final long TEMPLATE_START_TIME = System.currentTimeMillis();

    private static final Log LOG = LogFactory.getLog(TemplateEngine.class.getName());

	private static TemplateEngine defaultEngine = null;
	private static TemplateLibConfig inamikMainLib = null;

	private TemplateEngineConfig engineConfig;

	private File applicationDir;
	private File templateDir;

    private TemplateCache cache = null;

	/**
	 * Constructor (private)
	 */
	private TemplateEngine() {}

	/**
	 * Constructor (Package-Private)
	 */
	TemplateEngine(final TemplateEngineConfig engineConfig)
	{
		super();

		if (engineConfig == null)
		{
			throw new NullPointerException("engineConfig");
		}

		this.engineConfig = engineConfig;

		init();
	}

	/**
	 * getInstance - Factory method to return a singleton TemplateEngine,
	 * or create one with the default configuration if a singletone has not
	 * already been created.
	 * <p>
	 * This method is kept for compability and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>TemplateEngine.getDefaultInstance()</code>
	 *
	 * @deprecated
	 * @return The default TemplateEngine
	 * @throws TemplateException
	 *
	 * @see TemplateEngine#getDefaultInstance()
	 * @see TemplateConfigUtil
	 * @see TemplateConfigUtil#DEFAULT_ENGINE_CONFIG
	 * @see TemplateEngineConfig
	 */
	@Deprecated
	public static TemplateEngine getInstance() throws TemplateException
	{
		return getDefaultInstance();
	}

	/**
	 * getInstance w/String - Factory method to create a TemplateEngine from
	 * a file.
	 * <p>
	 * The specified file must conform to the layout described in the file
	 * 'inamik_template_lib-sample.xml'.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>TemplateEngine.getInstance(TemplateConfigUtil.readTemplateEngineConfig(filename))</code>
	 *
	 * @param filename The configuration file to load
	 * @return A TemplateEngine based on the specified configuration.
	 * @throws FileNotFoundException
	 * @throws TemplateException
	 * @throws NullPointerException If <code>filename == null</code>.
	 *
	 * @see TemplateConfigUtil
	 */
	public static TemplateEngine getInstance(final String filename) throws FileNotFoundException, TemplateException
	{
		if (filename == null)
		{
			throw new NullPointerException("filename");
		}

		return getInstance(TemplateConfigUtil.readTemplateEngineConfig(filename));
	}

	/**
	 * getInstance w/File - Factory method to create a TemplateEngine from
	 * a java.io.File.
	 * <p>
	 * The specified file must conform to the layout described in the file
	 * 'inamik_template_lib-sample.xml'
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>TemplateEngine.getInstance(TemplateConfigUtil.readTemplateEngineConfig(file))</code>
	 *
	 * @param file The configuration file to load
	 * @return A TemplateEngine based on the specified configuration.
	 * @throws FileNotFoundException
	 * @throws TemplateException
	 * @throws NullPointerException If <code>file == null</code>.
	 *
	 * @see TemplateConfigUtil
	 */
	public static TemplateEngine getInstance(final File file) throws FileNotFoundException, TemplateException
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		return getInstance(TemplateConfigUtil.readTemplateEngineConfig(file));
	}

	/**
	 * getInstance w/TemplateEngineConfig - Factory method to create a
	 * TemplateEngine from a TemplateEngineConfig instance.
	 * <p>
	 * This allows you to create TemplateEngine instances without having
	 * to create a configuration file.
	 * <p>
	 * This method makes a defensive copy of the supplied TemplateEngineConfig
	 * and is guaranteed not to modify the caller's copy.
	 *
	 * @param engineConfig The configuration use
	 * @return A TemplateEngine based on the specified configuration.
	 * @throws TemplateException
	 * @throws NullPointerException If <code>engineConfig == null</code>
	 *
	 * @see TemplateEngineConfig
	 */
	public static TemplateEngine getInstance(final TemplateEngineConfig engineConfig) throws TemplateException
	{
		if (engineConfig == null)
		{
			throw new NullPointerException("engineConfig");
		}

		TemplateEngineConfig myEngineConfig = new TemplateEngineConfig(engineConfig);

		addInamikMainLib(myEngineConfig);

		TemplateEngine instance = new InterpretedTemplateEngine(myEngineConfig);

		return instance;
	}

	/**
	 * getDefaultInstance - Factory method to return a singleton TemplateEngine,
	 * or create one with the default configuration if a singletone has not
	 * already been created.
	 * <p>
	 * To create a default configuration, first it looks for
	 * DEFAULT_ENGINE_CONFIG in the current directory.
	 * If it does not find it there, then it looks for it on the classpath.
	 * If it still cannot find the file, then it uses the default values
	 * for TemplateEngineConfig.
	 *
	 * @return The default TemplateEngine
	 * @throws TemplateException
	 *
	 * @see TemplateConfigUtil
	 * @see TemplateConfigUtil#DEFAULT_ENGINE_CONFIG
	 * @see TemplateEngineConfig
	 */
	public static synchronized TemplateEngine getDefaultInstance() throws TemplateException
	{
		if (defaultEngine != null)
		{
			return defaultEngine;
		}

		defaultEngine = getInstance(TemplateConfigUtil.readTemplateEngineConfig());

		return defaultEngine;
	}

	/**
	 * getDefaultInstance w/File - Factory method to create a singleton
	 * TemplateEngine from a java.io.File.
	 * <p>
	 * The specified file must conform to the layout described in the file
	 * 'inamik_template_lib-sample.xml'
	 * <p>
	 * This method will throw an IllegalStateException if a singleton
	 * TemplateEngine already exists, regardless of how the singleton was
	 * created.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>TemplateEngine.getDefaultInstance(TemplateConfigUtil.readTemplateEngineConfig(file))</code>
	 *
	 * @param file The configuration file to load
	 * @return A TemplateEngine based on the specified configuration.
	 * @throws FileNotFoundException
	 * @throws TemplateException
	 * @throws IllegalStateException if a singleton TemplateEngine already exists
	 * @throws NullPointerException If <code>file == null</code>.
	 *
	 * @see TemplateConfigUtil
	 */
	public static synchronized TemplateEngine getDefaultInstance(final File file) throws FileNotFoundException, TemplateException
	{
		if (defaultEngine != null)
		{
			throw new IllegalStateException("Default Template Engine already configured");
		}

		if (file == null)
		{
			throw new NullPointerException("file");
		}

		defaultEngine = getInstance(TemplateConfigUtil.readTemplateEngineConfig(file));

		return defaultEngine;
	}

	/**
	 * getInstance w/String - Factory method to create a singleton
	 * TemplateEngine from a file.
	 * <p>
	 * The specified file must conform to the layout described in the file
	 * 'inamik_template_lib-sample.xml'.
	 * <p>
	 * This method will throw an IllegalStateException if a singleton
	 * TemplateEngine alredy exists, regardless of how the singleton was
	 * created.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>TemplateEngine.getDefaultInstance(TemplateConfigUtil.readTemplateEngineConfig(filename))</code>
	 *
	 * @param filename The configuration file to load
	 * @return A TemplateEngine based on the specified configuration.
	 * @throws FileNotFoundException
	 * @throws TemplateException
	 * @throws IllegalStateException if a singleton TemplateEngine already exists
	 * @throws NullPointerException If <code>filename == null</code>.
	 *
	 * @see TemplateConfigUtil
	 */
	public static synchronized TemplateEngine getDefaultInstance(final String filename) throws FileNotFoundException, TemplateException
	{
		if (defaultEngine != null)
		{
			throw new IllegalStateException("Default Template Engine already configured");
		}

		if (filename == null)
		{
			throw new NullPointerException("filename");
		}

		defaultEngine = getInstance(TemplateConfigUtil.readTemplateEngineConfig(filename));

		return defaultEngine;
	}

	/**
	 * getDefaultInstance w/TemplateEngineConfig - Factory method to create a
	 * TemplateEngine from a TemplateEngineConfig instance.
	 * <p>
	 * This allows you to create TemplateEngine instances without having
	 * to create a configuration file.
	 * <p>
	 * This method will throw an IllegalStateException if a singleton
	 * TemplateEngine alredy exists, regardless of how the singleton was
	 * created.
	 * <p>
	 * This method makes a defensive copy of the supplied TemplateEngineConfig
	 * and is guaranteed not to modify the caller's copy.
	 *
	 * @param engineConfig The configuration use
	 * @return A TemplateEngine based on the specified configuration.
	 * @throws TemplateException
	 * @throws IllegalStateException if a singleton TemplateEngine already exists
	 * @throws NullPointerException If <code>engineConfig == null</code>
	 *
	 * @see TemplateEngineConfig
	 */
	public static synchronized TemplateEngine getDefaultInstance(final TemplateEngineConfig engineConfig) throws TemplateException
	{
		if (engineConfig == null)
		{
			throw new IllegalStateException("Default Template Engine already configured");
		}

		TemplateEngineConfig myEngineConfig = new TemplateEngineConfig(engineConfig);

		addInamikMainLib(myEngineConfig);

		TemplateEngine instance = new InterpretedTemplateEngine(myEngineConfig);

		return instance;
	}

	/**
	 * addInamikMainLib
	 * @throws TemplateException
	 */
	private static synchronized void addInamikMainLib(TemplateEngineConfig engineConfig) throws TemplateException
	{
		if (engineConfig.isUseDefaultLib() == false)
		{
			return;
		}

		if (inamikMainLib == null)
		{
			TemplateEngineConfig.UseLib useLib = new TemplateEngineConfig.UseLib(null, INAMIK_LIB_MAIN, TemplateTagName.DEFAULT_PREFIX);

			TemplateLibConfig libConfig;

			try
			{
				libConfig = TemplateConfigUtil.readTemplateLibConfig(null, useLib);
			}
			catch (FileNotFoundException e)
			{
				libConfig = null;
			}

			if (libConfig == null)
			{
				throw new RuntimeException("Error reading template lib: " + INAMIK_LIB_MAIN);
			}

			inamikMainLib = libConfig;
		}

		engineConfig.addTemplateLib(TemplateTagName.DEFAULT_PREFIX, inamikMainLib);
	}

	/**
	 * getConfig (Package-Private)
	 */
	final TemplateEngineConfig getEngineConfig()
	{
		return engineConfig;
	}

	/**
	 * setTemplateRoot - A convenience method for setting the templateRoot.
	 * <p>
	 * This method is most useful when using a TemplateEngine without a
	 * configuration file.  With this method, you can have a usable
	 * TemplateEngine in just two lines of code
	 * <p>
	 * <code>
	 *    TemplateEngine templateEngine = TemplateEngine.getInstance();<br>
	 *    templateEngine.setTemplateRoot("/templates");<br>
	 * </code>
	 * <p>
	 * Verifies that the templateRoot exists and is a directory
	 *
	 * @param templateRoot The template root folder.  <code>null</code> is okay
	 *        and indicates that you want the template root folder to be the
	 *        same as the application root folder.
	 *
	 * @throws FileNotFoundException If templateRoot does not exist or is not a
	 *          directory.
	 * @throws IOException
	 *
	 * @see TemplateEngineConfig#setTemplateRoot(String)
	 */
	public void setTemplateRoot(final String templateRoot) throws FileNotFoundException, IOException
	{
		engineConfig.setTemplateRoot(templateRoot);
		updateTemplateRootDir();
	}

	private void updateTemplateRootDir() throws FileNotFoundException, IOException
	{
		assert this.applicationDir != null;

		File templateRootDir;

		final String templateRoot = engineConfig.getTemplateRoot();

		if (templateRoot != null)
		{
			templateRootDir = new File(applicationDir, templateRoot).getCanonicalFile();
		}
		else
		{
			templateRootDir = new File(applicationDir, "").getCanonicalFile();
		}

		if (templateRootDir.exists() == false)
		{
			throw new FileNotFoundException("Template Root Dir '" + templateRootDir.getPath() + "' does not exist");
		}

		if (templateRootDir.isDirectory() == false)
		{
			throw new FileNotFoundException("Template Root Dir '" + templateRootDir.getPath() + "' is not a directory");
		}

		this.templateDir = templateRootDir;

		if (LOG.isDebugEnabled())
			LOG.debug("TemplateRoot: " + templateDir.getPath());
	}

	private void updateApplicationRootDir() throws FileNotFoundException, IOException
	{
		File applicationRootDir;

		final String applicationRoot = engineConfig.getApplicationRoot();

		if (applicationRoot != null)
		{
			applicationRootDir = new File(applicationRoot).getCanonicalFile();
		}
		else
		{
			applicationRootDir = new File("").getCanonicalFile();
		}

		if (applicationRootDir.exists() == false)
		{
			throw new FileNotFoundException("Application Root Dir '" + applicationRootDir.getPath() + "' does not exist");
		}

		if (applicationRootDir.isDirectory() == false)
		{
			throw new FileNotFoundException("Application Root Dir '" + applicationRootDir.getPath() + "' is not a directory");
		}

		this.applicationDir = applicationRootDir;

		if (LOG.isDebugEnabled())
			LOG.debug("ApplicationRoot: " + applicationDir.getPath());
	}

	/**
	 * evalTemplate w/String - Compile a template from an String and return
	 * it as a Template.
	 *
	 * @param templateText The String to read the template from
	 * @return A Template compiled from the input String
	 * @throws NullPointerException If <code>templateText == null</code>
	 *
	 * @see Template
	 */
	public final Template evalTemplate(final String templateText)
	{
		if (templateText == null)
		{
			throw new NullPointerException("templateText");
		}

		return evalTemplate(new ByteArrayInputStream(templateText.getBytes()));
	}

	/**
	 * evalTemplate w/InputStream - Compile a template from an InputStream
	 * and return it as a Template.
	 * <p>
	 * This method does not close the InputStream
	 *
	 * @param stream The InputStream to read the template from
	 * @return A Template compiled from the InputStream
	 * @throws NullPointerException If <code>stream == null</code>
	 *
	 * @see Template
	 */
	public final Template evalTemplate(final InputStream stream)
	{
		if (stream  == null)
		{
			throw new NullPointerException("stream");
		}

		TokenizedTemplate cu = compileTemplate(stream, "evalTemplate");

		TemplateContext context = new TemplateContext();
		context.setTemplateEngine(this);

		TemplateProcessor processor = new InterpretedTemplateProcessor(cu);
		Template template = new Template(processor, context);
		return template;
	}

	/**
	 * evalMacro w/String - Compile a template from an String and return it as
	 * a TemplateMacro.
	 *
	 * @param macroText The String to read the macro from
	 * @return A TemplateMacro compiled from the input String
	 * @throws NullPointerException If <code>macroText == null</code>
	 *
	 * @see TemplateMacro
	 */
	public final TemplateMacro evalMacro(final String macroText)
	{
		if (macroText == null)
		{
			throw new NullPointerException("macroText");
		}

		return evalMacro(new ByteArrayInputStream(macroText.getBytes()));
	}

	/**
	 * evalMacro w/InputStream - Compile a template from an InputStream
	 * and return it as a TemplateMacro.
	 * <p>
	 * This method does not close the InputStream.
	 *
	 * @param stream The InputStream to read the macro from
	 * @return A TemplateMacro compiled from the InputStream
	 * @throws NullPointerException If <code>stream == null</code>
	 *
	 * @see TemplateMacro
	 */
	public final TemplateMacro evalMacro(final InputStream stream)
	{
		if (stream  == null)
		{
			throw new NullPointerException("stream");
		}

		TokenizedTemplate cu = compileTemplate(stream, "evalMacro");

		TemplateContext context = new TemplateContext();
		context.setTemplateEngine(this);

		TemplateProcessor processor = new InterpretedTemplateProcessor(cu);

		TemplateMacro macro = new TemplateMacro(processor);

		return macro;
	}

	private final Serializable getCachedObject(final String name, final long lastModifiedTime)
	{
		assert name  != null;
		assert cache != null;

		if (LOG.isTraceEnabled())
			LOG.trace("Looking for template '" + name + "' in cache");

		Serializable cachedObject = null;

		TemplateCacheEntry entry;

		try
		{
			entry = cache.getEntry(name);
		}
		catch (Exception e)
		{
			if (LOG.isWarnEnabled())
				LOG.warn("Error retrieving entry from cache: " + name, e);

			entry = null;
		}

		if (entry != null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Template found in cache with lastModified of " + entry.getCreationTime() + ": "  + name);

			if (lastModifiedTime <= entry.getCreationTime())
			{
				cachedObject = entry.getValue();

				if (LOG.isDebugEnabled())
					LOG.debug("Template lastModified is good.  Template rectrieved from cache: "  + name);
			}
			else
			{
				if (LOG.isDebugEnabled())
					LOG.debug("Template lastModified is bad, compared to " + lastModifiedTime + ". Remove templat from cache: "  + name);

				cache.removeEntry(name);
			}
		}
		else
			if (LOG.isTraceEnabled())
				LOG.trace("Template '" + name + "' not found in cache");

		return cachedObject;
	}

	/**
	 * loadTemplateFromResource - Compile a Template from the classpath.
	 *
	 * @param resourceName The resource to read the template from
	 * @return A Template compiled from the input file
	 * @throws IOException
	 * @throws NullPointerException If <code>resourceName == null</code>
	 *
	 * @see Template
	 * @see ClassLoaderUtil
	 */
	public final Template loadTemplateFromResource(final String resourceName) throws IOException
	{
		if (resourceName == null)
		{
			throw new NullPointerException("resourceName");
		}

		TemplateContext context = new TemplateContext();
		context.setTemplateEngine(this);

		return loadTemplateFromResource(resourceName, context);
	}

	/**
	 * loadTemplateFromResource (Package-Private) w/String, TemplateContext
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	final Template loadTemplateFromResource(final String name, final TemplateContext context) throws IOException
	{
		assert name    != null;
		assert context != null;

		TemplateProcessor processor = getProcessorFromResource(name);
		Template template = new Template(processor, context);
		return template;
	}

	/**
	 * loadTemplateFromURL - Compile a Template from a URL.
	 *
	 * @param url The URL to read the template from
	 * @return A Template compiled from the input file
	 * @throws IOException
	 * @throws NullPointerException If <code>url == null</code>
	 *
	 * @see Template
	 * @see ClassLoaderUtil
	 */
	public final Template loadTemplateFromURL(final URL url) throws IOException
	{
		if (url == null)
		{
			throw new NullPointerException("url");
		}

		TemplateContext context = new TemplateContext();
		context.setTemplateEngine(this);

		return loadTemplateFromURL(url, context);
	}

	/**
	 * loadTemplateFromURL (Package-Private) w/String, TemplateContext
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	final Template loadTemplateFromURL(final URL url, final TemplateContext context) throws IOException
	{
		assert url     != null;
		assert context != null;

		TemplateProcessor processor = getProcessor(url, getURLModifiedTime(url));
		Template template = new Template(processor, context);
		return template;
	}

	/**
	 * getURLModifiedTime (private) - Tries to determine the last modified
	 * time of the specified URL.  If the URL is not a file, then it returns
	 * TemplateEngine.TEMPLATE_START_TIME
	 * @param url
	 * @return Last modified time of the specified URL
	 * @throws FileNotFoundException
	 * @see {@link TemplateEngine#TEMPLATE_START_TIME}
	 */
	private long getURLModifiedTime(final URL url) throws FileNotFoundException
	{
		final long lastModifiedTime;

		if (PROTOCOL_FILE.equals(url.getProtocol()) == true)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Resource uses protocol " + PROTOCOL_FILE + ", looking for lastModified: " + url.getPath());

			try
			{
				File file = new File(url.toURI());

				lastModifiedTime = file.lastModified();

				if (LOG.isDebugEnabled())
					LOG.debug("Resource has lastModified of " + lastModifiedTime + ": " + url.getPath());
			}
			catch (URISyntaxException e)
			{
				if (LOG.isWarnEnabled())
					LOG.warn("Unexpected URISyntaxException for resource: " + url.getPath(), e);

				throw new FileNotFoundException(e.getMessage());
			}
		}
		else
		{
			lastModifiedTime = TEMPLATE_START_TIME;
		}

		return lastModifiedTime;
	}

	/**
	 * getProcessorFromResource
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private final TemplateProcessor getProcessorFromResource(final String name) throws IOException
	{
		assert name  != null;

		final URL url = ClassLoaderUtil.getResourceAsURL(name);

		if (url == null)
		{
			throw new FileNotFoundException("Cannot find resource: " + name);
		}

		return getProcessor(url, getURLModifiedTime(url));
	}

	/**
	 * getProcessor
	 * @throws IOException
	 */
	private final TemplateProcessor getProcessor(final URL url, final long lastModificationTime) throws IOException
	{
		assert url   != null;
		assert cache != null;

		Serializable cachedObject = null;

		final String fullName = url.toExternalForm();

		synchronized (cache)
		{
			// Resources are considered never to expire within a JVM
			cachedObject = getCachedObject(fullName, lastModificationTime);

			if (cachedObject == null)
			{
				InputStream stream =  url.openStream();

				TokenizedTemplate cu = compileTemplate(stream, fullName);

				stream.close();

				cachedObject = getCacheableObject(cu);

				assert cachedObject != null;

				if (LOG.isTraceEnabled())
					LOG.trace("Adding template '" + fullName + "' to cache");

				cache.putEntry(fullName, new TemplateCacheEntry(cachedObject, lastModificationTime));
			}
			else
				if (LOG.isTraceEnabled())
					LOG.trace("Retrieved template '" + fullName + "' from cache");
		}

		assert cachedObject != null;

		return getProcessorFromCachedObject(cachedObject);
	}

	/**
	 * loadTemplateFromFile w/String - Compile a Template from a file, relative
	 * to the templateRoot.
	 *
	 * @param filename The file, relative to the TemplateRoot, to read the
	 *        template from
	 * @return A Template compiled from the input file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>filename == null</code>
	 *
	 * @see Template
	 * @see #setTemplateRoot(String)
	 */
	public final Template loadTemplateFromFile(final String filename) throws FileNotFoundException, IOException
	{
		if (filename == null)
		{
			throw new NullPointerException("filename");
		}

		TemplateContext context = new TemplateContext();
		context.setTemplateEngine(this);

		return loadTemplateFromFile(filename, context);
	}

	/**
	 * loadTemplateFromFile (Package-Private) w/String, TemplateContext
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	final Template loadTemplateFromFile(final String name, final TemplateContext context) throws FileNotFoundException, IOException
	{
		assert name    != null;
		assert context != null;

		TemplateProcessor processor = getProcessorFromFile(name);
		Template template = new Template(processor, context);
		return template;
	}

	/**
	 * loadTemplateFromFile w/File - Compile a Template from a java.io.File.
	 *
	 * @param file The java.io.File to read the template from
	 * @return A Template compiled from the input File
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>file == null</code>
	 *
	 * @see Template
	 * @see #setTemplateRoot(String)
	 */
	public final Template loadTemplateFromFile(final File file) throws FileNotFoundException, IOException
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		TemplateContext context = new TemplateContext();
		context.setTemplateEngine(this);

		return loadTemplateFromFile(file, context);
	}

	/**
	 * loadTemplateFromFile (Package-Private) w/File, TemplateContext
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	final Template loadTemplateFromFile(final File file, final TemplateContext context) throws FileNotFoundException, IOException
	{
		assert file    != null;
		assert context != null;

		TemplateProcessor processor = getProcessorFromFile(file);
		Template template = new Template(processor, context);
		return template;
	}

	/**
	 * loadMacroFromFile w/String - Compile a Macro from a file, relative
	 * to the templateRoot.
	 *
	 * @param filename The file, relative to the TemplateRoot, to read the
	 *        template from
	 * @return A TemplateMacro compiled from the input file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>filename == null</code>
	 *
	 * @see TemplateMacro
	 * @see #setTemplateRoot(String)
	 */
	public final TemplateMacro loadMacroFromFile(final String filename) throws FileNotFoundException, IOException
	{
		if (filename == null)
		{
			throw new NullPointerException("filename");
		}

		TemplateProcessor processor = getProcessorFromFile(filename);
		TemplateMacro macro = new TemplateMacro(processor);
		return macro;
	}

	/**
	 * loadMacroFromFile w/File - Compile a Macro from a java.io.File.
	 *
	 * @param file The java.io.File to read the macro from
	 * @return A TemplateMacro compiled from the input File
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>file == null</code>
	 *
	 * @see TemplateMacro
	 * @see #setTemplateRoot(String)
	 */
	public final TemplateMacro loadMacroFromFile(final File file) throws FileNotFoundException, IOException
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		TemplateProcessor processor = getProcessorFromFile(file);
		TemplateMacro macro = new TemplateMacro(processor);
		return macro;
	}

	/**
	 * loadMacroFromResource - Compile a Macro from the classpath.
	 *
	 * @param resourceName The resource to read the macro from
	 * @return A TemplateMacro compiled from the input file
	 * @throws IOException
	 * @throws NullPointerException If <code>resourceName == null</code>
	 *
	 * @see TemplateMacro
	 * @see ClassLoaderUtil
	 */
	public final TemplateMacro loadMacroFromResource(final String resourceName) throws IOException
	{
		if (resourceName == null)
		{
			throw new NullPointerException("resourceName");
		}

		TemplateProcessor processor = getProcessorFromResource(resourceName);
		TemplateMacro macro = new TemplateMacro(processor);
		return macro;
	}

	/**
	 * getProcessorFromFile w/String
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private final TemplateProcessor getProcessorFromFile(final String name) throws IOException
	{
		assert name != null;

		return getProcessorFromFile(getFileFromTemplateRoot(name));
	}

	/**
	 * getProcessorFromFile w/File
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private final TemplateProcessor getProcessorFromFile(final File templateFile) throws IOException
	{
		assert templateFile != null;

		return getProcessor(templateFile.toURI().toURL(), templateFile.lastModified());
	}

	protected abstract Serializable getCacheableObject(final TokenizedTemplate cu);
	protected abstract TemplateProcessor getProcessorFromCachedObject(final Serializable object);

	/**
	 * init (Private)
	 */
	private final void init()
	{
		try
		{
			// ApplicationRoot

			updateApplicationRootDir();

			// TemplateRoot

			updateTemplateRootDir();

			// Use-Libs

			for (TemplateEngineConfig.UseLib useLib : engineConfig.getUseLibs())
			{
				TemplateLibConfig libConfig = TemplateConfigUtil.readTemplateLibConfig(applicationDir, useLib);

				if (libConfig != null)
				{
					engineConfig.addTemplateLib(useLib.getPrefix(), libConfig);
				}
			}

			engineConfig.clearUseLibs();

			// Cache

			final TemplateCacheConfig cacheConfig = engineConfig.getCacheConfig();

			if (cacheConfig != null)
			{
				if (cacheConfig.getName() != null)
				{
					boolean ehcacheAvailable;

					try
					{
						Class.forName("net.sf.ehcache.Cache");
						ehcacheAvailable = true;
					}
					catch (Exception e)
					{
						ehcacheAvailable = false;
					}

					if (ehcacheAvailable == true)
					{
						String diskRoot = null;

						if	(
								(cacheConfig.isOverflowToDisk() == true)
							&&	(cacheConfig.getDiskRoot()      != null)
							)
						{
							// Although EHCACHE doesn't require the cache folder to exist at
							// runtime, we require it as a way to ensure that the application
							// root and disk root have been configured correclty.
							File cacheDir = new File(getApplicationDir(), cacheConfig.getDiskRoot());

							if (cacheDir.exists() == false)
							{
								if (LOG.isWarnEnabled())
									LOG.warn("Template Cache Dir '" + cacheDir.getCanonicalPath() + "' does not exist.  Setting overflowToDisk=false");
							}
							else
							if (cacheDir.isDirectory() == false)
							{
								if (LOG.isWarnEnabled())
									LOG.warn("Template Cache Dir '" + cacheDir.getCanonicalPath() + "' is not a directory.  Setting overflowToDisk=false");
							}
							else
							{
								diskRoot = cacheDir.getCanonicalPath();
							}
						}

						if (diskRoot == null)
						{
							cacheConfig.setOverflowToDisk(false);
							cacheConfig.setDiskPersistent(false);
							cacheConfig.setDiskRoot(null);
						}

						cacheConfig.setDiskRoot(diskRoot);

						this.cache = EasyHibernateCacheFactory.getCache(cacheConfig);
					}
					else
					{
						if (LOG.isWarnEnabled())
							LOG.warn("Class net.sf.ehcache.Cache not available on classpath. Skipping cache creation");
					}
				}
				else
				{
					if (LOG.isWarnEnabled())
						LOG.warn("No name defined for Cache. Skipping cache creation");
				}
			} // cacheConfig != null

			// If we did not configure a cache
			if (this.cache == null)
			{
				if (LOG.isInfoEnabled())
					LOG.info("Could not configure main cache.  Using backup cache");

				this.cache = new WeakReferenceCache();
			}
		}
		catch (RuntimeException rte)
		{
			throw rte;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * getApplicationDir
	 */
	protected final File getApplicationDir()
	{
		return this.applicationDir;
	}

	/**
	 * getFileFromApplicationRoot - Get a java.io.File, using the applicationRoot
	 * as the base.
	 * <p>
	 * This method verifies that the file exists and is readable.
	 * @param filename The file, relative to the applicationRoot
	 * @return A java.io.File
	 * @throws FileNotFoundException If the file does not exist or is not
	 *         readable
	 * @throws NullPointerException If <code>filaneme == null</code>
	 *
	 * @see TemplateEngineConfig
	 */
	public final File getFileFromApplicationRoot(final String filename) throws FileNotFoundException
	{
		if (filename == null)
		{
			throw new NullPointerException("name");
		}

		assert applicationDir != null;

		File templateFile = new File(applicationDir, filename);

		if (templateFile.exists() == false)
		{
			throw new FileNotFoundException("Cannot Find File '" + templateFile.getPath() + "'");
		}

		if (templateFile.canRead() == false)
		{
			throw new FileNotFoundException("Cannot Read File '" + templateFile.getPath() + "'");
		}

		return templateFile;
	}

	/**
	 * getFileFromTemplateRoot - Get a File, using the templateRoot
	 * as the base.
	 * <p>
	 * This method verifies that the file exists and is readable.
	 * @param filename The file, relative to the templateRoot
	 * @return A java.io.File
	 * @throws FileNotFoundException If the file does not exist or is not
	 *         readable
	 * @throws NullPointerException If <code>filename == null</code>
	 *
	 * @see #setTemplateRoot(String)
	 * @see TemplateEngineConfig
	 */
	public final File getFileFromTemplateRoot(final String filename) throws FileNotFoundException
	{
		if (filename == null)
		{
			throw new NullPointerException("filename");
		}

		assert templateDir != null;

		File templateFile = new File(templateDir, filename);

		if (templateFile.exists() == false)
		{
			throw new FileNotFoundException("Cannot Find File '" + templateFile.getPath() + "'");
		}

		if (templateFile.canRead() == false)
		{
			throw new FileNotFoundException("Cannot Read File '" + templateFile.getPath() + "'");
		}

		return templateFile;
	}

//	/**
//	 * compileTemplate (Protected) w/String
//	 * @throws FileNotFoundException
//	 * @deprecated
//	 */
//	@Deprecated
//	protected final TokenizedTemplate compileTemplate(final String name) throws FileNotFoundException
//	{
//		assert name != null;
//
//		return compileTemplate(getFileFromTemplateRoot(name));
//	}

//	/**
//	 * compileTemplate (Protected) w/File
//	 * @deprecated
//	 */
//	@Deprecated
//	protected final TokenizedTemplate compileTemplate(final File file)
//	{
//		assert file != null;
//
//		InputStream stream;
//
//		try
//		{
//			stream = new FileInputStream(file);
//		}
//		catch (FileNotFoundException fnfe)
//		{
//			throw new RuntimeException(fnfe);
//		}
//
//		return compileTemplate(stream, file.getPath());
//	}

	/**
	 * compileTemplate (Protected) w/InputStream
	 */
	protected final TokenizedTemplate compileTemplate(final InputStream stream, final String name)
	{
		assert stream != null;

		assert name != null;

		if (LOG.isTraceEnabled())
			LOG.trace("Compiling template: " + name);

		TemplateTokenizer compiler = new TemplateTokenizer(engineConfig, stream);

		compiler.setName(name);

		return compiler.compile();
	}
}
