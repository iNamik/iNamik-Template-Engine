/*
 * $Id: TemplateConfigUtil.java,v 1.8 2006-08-22 10:39:24 dave Exp $
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
package com.inamik.template.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.inamik.template.TemplateActionConfig;
import com.inamik.template.TemplateException;
import com.inamik.template.TemplateFilterConfig;
import com.inamik.template.TemplateFunctionConfig;
import com.inamik.template.TemplateCacheConfig;
import com.inamik.template.TemplateEngineConfig;
import com.inamik.template.TemplateLibConfig;
import com.inamik.template.TemplateCacheConfig.EvictionPolicy;
import com.inamik.template.jaxb.config.TemplateConfig;
import com.inamik.template.jaxb.config.TemplateConfig.Cache;
import com.inamik.template.jaxb.lib.TemplateLib;
import com.inamik.template.jaxb.lib.TemplateLib.Action.Attribute;
import com.inamik.template.jaxb.lib.TemplateLib.Action.Example;

/**
 * TemplateConfigUtil - Utility class for loading template engine and
 * template library configurations.
 * <p>
 * Created on Jul 27, 2006
 * @author Dave
 */
public final class TemplateConfigUtil
{
	/**
	 * TemplateEngine.getInstance() will search for this file, first on the
	 * filesystem, then on the classpath.
	 */
	public static final String DEFAULT_ENGINE_CONFIG = "inamik_template_engine.xml";

	private static final String JAXB_CONFIG_PACKAGE   = "com.inamik.template.jaxb.config";
	private static final String JAXB_LIB_PACKAGE      = "com.inamik.template.jaxb.lib";

	private static final Log LOG = LogFactory.getLog(TemplateConfigUtil.class.getName());

	private static TemplateEngineConfig defaultConfig = null;

	/**
	 * Constructor (private)
	 */
	private TemplateConfigUtil()
	{
		super();
	}

	/**
	 * readTemplateEngineConfig - Read the default template engine xml
	 * configuration, or return it if it has already been created.
	 * <p>
	 * First, it looks for DEFAULT_ENGINE_CONFIG in the current directory.
	 * If it does not find it there, then it looks for it on the classpath.
	 * If it still cannot find the file, then it uses the default values
	 * for TemplateEngineConfig.
	 * <p>
	 * This method returns a copy of the default TemplateEngineConfig that is
	 * safe to modify for your own needs.
	 * <p>
	 * WARNING: If you are not planning to modify the the returned
	 * TemplateEngineConfig, then you should not use this method and should use
	 * <code>TemplateEngine.createInstance()</code> instead.
	 *
	 * @return A copy of the default TemplateEngineConfig, suitable for
	 *         modification.
	 * @throws TemplateException
	 *
	 * @see #DEFAULT_ENGINE_CONFIG
	 * @see TemplateEngineConfig
	 * @see ClassLoaderUtil
	 */
	public static synchronized TemplateEngineConfig readTemplateEngineConfig() throws TemplateException
	{

		if (defaultConfig == null)
		{
			TemplateEngineConfig engineConfig;

			// Try to find instance of default xml engineConfig
			try
			{
				engineConfig = TemplateConfigUtil.readTemplateEngineConfig(DEFAULT_ENGINE_CONFIG);
			}
			catch (FileNotFoundException fnfe)
			{
				engineConfig = null;
			}

			// If there was no file found, then try to find it on the classpath
			if (engineConfig == null)
			{
				InputStream stream = ClassLoaderUtil.getResourceAsStream(DEFAULT_ENGINE_CONFIG);

				if (stream != null)
				{
					engineConfig = TemplateConfigUtil.readTemplateEngineConfig(stream);
					StreamUtil.close(stream);
				}
			}

			// If not on classpath, then build default engineConfig
			if (engineConfig == null)
			{
				if (LOG.isWarnEnabled())
					LOG.warn("Could not locate " + DEFAULT_ENGINE_CONFIG + ". Creating a default TemplateEngineConfig");

				engineConfig = new TemplateEngineConfig();
			}

			assert engineConfig != null;

			defaultConfig = engineConfig;
		}

		return new TemplateEngineConfig(defaultConfig);
	}

	/**
	 * readTemplateEngineConfig w/String - Read a template engine xml
	 * configuration from a file.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>readTeplateEngineConfig(new FileInputStream(filename))</code>
	 *
	 * @param filename The name of the file to read.
	 * @return A template engine configuration suitable for instantiating
	 *         a TemplateEngine class.
	 * @throws FileNotFoundException
	 * @throws TemplateException
	 * @throws NullPointerException if <code>filename == null</code>
	 *
	 * @see #readTemplateEngineConfig(InputStream)
	 */
	public static TemplateEngineConfig readTemplateEngineConfig(final String filename) throws FileNotFoundException, TemplateException
	{
		if (filename == null)
		{
			throw new NullPointerException("filename");
		}

		return readTemplateEngineConfig(new FileInputStream(filename));
	}

	/**
	 * readTemplateEngineConfig w/File - Read a template engine xml
	 * configuration from a java.io.File.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>readTeplateEngineConfig(new FileInputStream(file))</code>
	 *
	 * @param file The java.io.File to read.
	 * @return A template engine configuration suitable for instantiating
	 *         a TemplateEngine class.
	 * @throws FileNotFoundException
	 * @throws TemplateException
	 * @throws NullPointerException if <code>file == null</code>
	 *
	 * @see #readTemplateEngineConfig(InputStream)
	 */
	public static TemplateEngineConfig readTemplateEngineConfig(final File file) throws FileNotFoundException, TemplateException
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		return readTemplateEngineConfig(new FileInputStream(file));
	}

	/**
	 * readTemplateEngineConfig w/URL - Read a template engine xml
	 * configuration from a URL.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>readTeplateEngineConfig(url.oipenStream())</code>
	 *
	 * @param url The URL to read.
	 * @return A template engine configuration suitable for instantiating
	 *         a TemplateEngine class.
	 * @throws IOException
	 * @throws TemplateException
	 * @throws NullPointerException if <code>url == null</code>
	 *
	 * @see #readTemplateEngineConfig(InputStream)
	 */
	public static TemplateEngineConfig readTemplateEngineConfig(final URL url) throws IOException, TemplateException
	{
		if (url == null)
		{
			throw new NullPointerException("url");
		}

		return readTemplateEngineConfig(url.openStream());
	}

	/**
	 * readTemplateEngineConfig w/InputStream - Read a template engine
	 * xml configuration from an InputStream.
	 *
	 * @param stream The InputStream to read.
	 * @return A template engine configuration suitable for instantiating
	 *         a TemplateEngine class.
	 * @throws TemplateException This method uses JAXB to parse the xml
	 *         configuration.  If JAXB throws an exception, this method catches
	 *         it and re-throws it as a wrapped TemplateException.
	 * @throws NullPointerException if <code>stream == null</code>
	 */
	public static TemplateEngineConfig readTemplateEngineConfig(final InputStream stream) throws TemplateException
	{
		if (stream == null)
		{
			throw new NullPointerException("stream");
		}

		TemplateEngineConfig engineConfig = new TemplateEngineConfig();

		try
		{
			JAXBContext jc = JAXBContext.newInstance(JAXB_CONFIG_PACKAGE);

			// create an Unmarshaller
			Unmarshaller u = jc.createUnmarshaller();

			TemplateConfig tc = (TemplateConfig)u.unmarshal(stream);

			if (tc.getApplicationRoot() != null)
			{
				engineConfig.setApplicationRoot(tc.getApplicationRoot());
			}

			engineConfig.setTemplateRoot(tc.getTemplateRoot());

			if (tc.getTagDelimeter() != null)
			{
				engineConfig.setTagDelimeter(tc.getTagDelimeter());
			}

			engineConfig.setUseDefaultLib(tc.isUseDefaultLib());

			engineConfig.setDebug(tc.isDebug());

			Cache cache = tc.getCache();

			if (cache != null)
			{
				TemplateCacheConfig cacheConfig = new TemplateCacheConfig();

				// Name
				cacheConfig.setName(cache.getName());

				// diskExpiryThreadIntervalSeconds
				if (cache.getDiskExpiryThreadIntervalSeconds() != null)
				{
					final int i = cache.getDiskExpiryThreadIntervalSeconds().intValue();
					cacheConfig.setDiskExpiryThreadIntervalSeconds(i);
				}

				// diskPersistent
				cacheConfig.setDiskPersistent(cache.isDiskPersistent());

				// diskRoot
				if (cache.getDiskRoot() != null)
				{
					cacheConfig.setDiskRoot(cache.getDiskRoot());
				}

				// eternal (required)
				cacheConfig.setEternal(cache.isEternal());

				// maxElementsInMemory
				if (cache.getMaxElementsInMemory() != null)
				{
					int i = cache.getMaxElementsInMemory().intValue();
					cacheConfig.setMaxElementsInMemory(i);
				}

				// memoryEvictionPolicy
				if (cache.getMemoryStoreEvictionPolicy() != null)
				{
					EvictionPolicy ep = EvictionPolicy.valueOf(cache.getMemoryStoreEvictionPolicy());
					cacheConfig.setMemoryStoreEvictionPolicy(ep);
				}

				// overflowToDisk (required)
				cacheConfig.setOverflowToDisk(cache.isOverflowToDisk());

				// timeToIdleSeconds
				if (cache.getTimeToIdleSeconds() != null)
				{
					int i = cache.getTimeToIdleSeconds().intValue();
					cacheConfig.setTimeToIdleSeconds(i);
				}

				// timeToLiveSeconds
				if (cache.getTimeToLiveSeconds() != null)
				{
					int i = cache.getTimeToLiveSeconds().intValue();
					cacheConfig.setTimeToLiveSeconds(i);
				}

				engineConfig.setCacheConfig(cacheConfig);
			}

			List<TemplateConfig.UseLib> useLibs = tc.getUseLib();

			// We can't resolve the use-libs now because the application-root
			// Has not been verified.  So we store them to be resolved later
			// by the TemplateEngine
			for (TemplateConfig.UseLib lib : useLibs)
			{
				TemplateEngineConfig.UseLib useLib = new TemplateEngineConfig.UseLib(lib.getFile(), lib.getResource(), lib.getPrefix());

				engineConfig.addUseLib(useLib);
			}
		}
		catch (JAXBException e)
		{
			throw new TemplateException(e);
		}

		return engineConfig;
	}

	/**
	 * readTemplateLibConfig w/File, UseLib - Read a template library xml
	 * configuration, using a UseLib library specification object and an
	 * applicationRoot.
	 * <p>
	 * This method is used by the Template Engine to resolve libraries and is
	 * probbaly not very useful for the general pbulic.
	 *
	 * @param applicationRoot A java.io.File representing the application root.
	 *        <code>null</code> is Okay.
	 * @param useLib The UseLib specification to load the template library from.
	 * @return A template library configuration suitable for adding to a
	 *         template engine configuration.
	 * @throws FileNotFoundException
	 * @throws NullPointerException if <code>useLib == null</code>
	 *
	 * @see com.inamik.template.TemplateEngineConfig.UseLib
	 * @see TemplateEngineConfig
	 */
	public static TemplateLibConfig readTemplateLibConfig(final File applicationRoot, final TemplateEngineConfig.UseLib useLib) throws FileNotFoundException, TemplateException
	{
		if (useLib == null)
		{
			throw new NullPointerException("uselib");
		}

		TemplateLibConfig result;

		if (useLib.getFile() != null)
		{
			result = readTemplateLibConfig(new File(applicationRoot, useLib.getFile()));
		}
		else
		if (useLib.getResource() != null)
		{
			InputStream libStream = ClassLoaderUtil.getResourceAsStream(useLib.getResource());

			if (libStream != null)
			{
				result = readTemplateLibConfig(libStream);
			}
			else
			{
				result = null;

				if (LOG.isWarnEnabled())
					LOG.warn("Could not find resource for template lib '" + useLib.getResource() + "'");
			}
		}
		else
		{
			result = null;

			if (LOG.isWarnEnabled())
				LOG.warn("found use-lib element with no 'resource' or 'file' attribute");
		}

		return result;
	}

	/**
	 * readTemplateLibConfig w/String -  Read a template library xml
	 * configuration from a file.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>readTeplateLibConfig(new FileInputStream(filename))</code>
	 *
	 * @param filename The name of the file to read.
	 * @return A template library configuration suitable for adding to a
	 *         template engine configuration.
	 * @throws FileNotFoundException
	 * @throws TemplateException
	 * @throws NullPointerException if <code>filename == null</code>
	 *
	 * @see #readTemplateLibConfig(InputStream)
	 * @see TemplateEngineConfig
	 */
	public static TemplateLibConfig readTemplateLibConfig(final String filename) throws FileNotFoundException, TemplateException
	{
		if (filename == null)
		{
			throw new NullPointerException("filename");
		}

		return readTemplateLibConfig(new FileInputStream(filename));
	}

	/**
	 * readTemplateLibConfig w/File - Read a template library xml
	 * configuration from a java.io.File.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>readTeplateLibConfig(new FileInputStream(file))</code>
	 *
	 * @param file The java.io.File to read.
	 * @return A template library configuration suitable for adding to a
	 *         template engine configuration.
	 * @throws FileNotFoundException
	 * @throws TemplateException
	 * @throws NullPointerException if <code>file == null</code>
	 *
	 * @see #readTemplateLibConfig(InputStream)
	 * @see TemplateEngineConfig
	 */
	public static TemplateLibConfig readTemplateLibConfig(final File file) throws FileNotFoundException, TemplateException
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		return readTemplateLibConfig(new FileInputStream(file));
	}

	/**
	 * readTemplateLibConfig w/URL - Read a template library xml
	 * configuration from a URL.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>readTeplateLibConfig(url.oipenStream())</code>
	 *
	 * @param url The URL to read.
	 * @return A template library configuration suitable for adding to a
	 *         template engine configuration.
	 * @throws IOException
	 * @throws TemplateException
	 * @throws NullPointerException if <code>url == null</code>
	 *
	 * @see #readTemplateLibConfig(InputStream)
	 * @see TemplateEngineConfig
	 */
	public static TemplateLibConfig readTemplateLibConfig(final URL url) throws IOException, TemplateException
	{
		return readTemplateLibConfig(url.openStream());
	}

	/**
	 * readTemplateLibConfig w/InputStream - Read a template library
	 * xml configuration from an InputStream.
	 *
	 * @param stream The InputStream to read.
	 * @return A template library configuration suitable for adding to a
	 *         template engine configuration.
	 * @throws TemplateException This method uses JAXB to parse the xml
	 *         configuration.  If JAXB throws an exception, this method catches
	 *         it and re-throws it as a wrapped TemplateException.
	 * @throws NullPointerException if <code>stream == null</code>
	 *
	 * @see TemplateEngineConfig
	 */
	public static TemplateLibConfig readTemplateLibConfig(final InputStream stream) throws TemplateException
	{
		TemplateLibConfig libConfig = new TemplateLibConfig();

		try
		{
			JAXBContext jc = JAXBContext.newInstance(JAXB_LIB_PACKAGE);

			// create an Unmarshaller
			Unmarshaller u = jc.createUnmarshaller();

			TemplateLib tl;

			tl = (TemplateLib)u.unmarshal(stream);

			List<Object> list = tl.getActionOrFunctionOrFilter();

			for (Object o : list)
			{
				// TemplateActionTag
				if (o instanceof TemplateLib.Action)
				{
					TemplateLib.Action a = (TemplateLib.Action)o;

					String name  = a.getName();
					String clazz = a.getClazz();

					TemplateActionConfig.ParmType    parmType  = TemplateActionConfig.ParmType   .findByName(a.getParmType()   );
					TemplateActionConfig.BlockType   blockType = TemplateActionConfig.BlockType  .findByName(a.getBlockType()  );
					TemplateActionConfig.BodyContent bodyType  = TemplateActionConfig.BodyContent.findByName(a.getBodyContent());

					TemplateActionConfig actionConfig = new TemplateActionConfig(name, clazz, parmType, blockType, bodyType);

					libConfig.addAction(actionConfig);
				}
				else
				// TemplateFunctionTag
				if (o instanceof TemplateLib.Function)
				{
					TemplateLib.Function function = (TemplateLib.Function)o;

					String name  = function.getName();
					String clazz = function.getClazz();

					TemplateFunctionConfig functionConfig = new TemplateFunctionConfig(name, clazz);

					libConfig.addFunction(functionConfig);
				}
				else
				// TemplateFilterTag
				if (o instanceof TemplateLib.Filter)
				{
					TemplateLib.Filter filter = (TemplateLib.Filter)o;

					String name  = filter.getName();
					String clazz = filter.getClazz();

					TemplateFilterConfig filterConfig = new TemplateFilterConfig(name, clazz);

					libConfig.addFilter(filterConfig);
				}
				else
				{
					throw new RuntimeException("Unknown type" + o.getClass().getCanonicalName());
				}
			}
		}
		catch (JAXBException e)
		{
			throw new TemplateException(e);
		}

		return libConfig;
	}
}
