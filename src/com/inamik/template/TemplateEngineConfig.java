/*
 * $Id: TemplateEngineConfig.java,v 1.8 2006-08-31 22:56:38 dave Exp $
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * TemplateEngineConfig - Configuration class for TemplateEngine.
 * <p>
 * This class  is instantiated with acceptable defaults and can be used
 * to create a TemplateEngine without any modification.
 * <p>
 * Created on Jul 9, 2006
 * @author Dave
 * @see com.inamik.template.TemplateEngine
 * @see com.inamik.template.util.TemplateConfigUtil
 */
public final class TemplateEngineConfig
{
	/**
	 * TagDelimeter - An enumeration specifying the acceptable template
	 * tag-delimeters.
	 * <p>
	 * Values are:
	 * <p>
	 * CURLY         - Tags are delimeted with '{' and '}'
	 * <br>
	 * CURLY_CURLY   - Tags are delimeted with '{{' and '}}'
	 * <br>
	 * ANGLE_PERCENT - Tags are delimeted with '&lt;%' and '%&gt;'
	 */
	public static enum TagDelimeter
	{
		/** Tags are delimeted with '{' and '}' */
		CURLY,

		/** Tags are delimeted with '{{' and '}}' */
		CURLY_CURLY,

		/** Tags are delimeted with '&lt;%' and '%&gt;' */
		ANGLE_PERCENT,
		;
	};

	/**
	 * UseLib - Simple bean to describe a useLib specification
	 */
	public static final class UseLib
	{
		private final String file, resource, prefix;

		/**
		 * Constructor
		 */
		public UseLib(final String file, final String resource, final String prefix)
		{
			super();

			this.file     = file;
			this.resource = resource;
			this.prefix   = prefix;
		}

		/**
		 * Copy Constructor
		 */
		public UseLib(final UseLib useLib)
		{
			super();

			this.file     = useLib.file;
			this.resource = useLib.resource;
			this.prefix   = useLib.prefix;
		}

		public String getFile    () { return this.file;     }
		public String getPrefix  () { return this.prefix;	}
		public String getResource()	{ return this.resource;	}
	}

	public static final String       DEFAULT_APPLICATION_ROOT = ".";
	public static final String       DEFAULT_TEMPLATE_ROOT    = "/";
	public static final TagDelimeter DEFAULT_TAG_DELIMETER    = TagDelimeter.CURLY;
	public static final boolean      DEFAULT_USE_DEFAULT_LIB  = true;

	private String       applicationRoot;
	private String       templateRoot;
	private TagDelimeter tagDelimeter;
	private boolean      useDefaultLib;

	private Map<TemplateTagName, TemplateActionConfig  > actions;
	private Map<TemplateTagName, TemplateFilterConfig  > filters;
	private Map<TemplateTagName, TemplateFunctionConfig> functions;

	private List<UseLib> useLibs;

//	private TemplateProcessorConfig processorConfig;

	private TemplateCacheConfig cacheConfig;

	private boolean debug;

	/**
	 * Default Constructor
	 */
	public TemplateEngineConfig()
	{
		super();

		this.applicationRoot = DEFAULT_APPLICATION_ROOT;
		this.templateRoot    = DEFAULT_TEMPLATE_ROOT;
		this.tagDelimeter    = DEFAULT_TAG_DELIMETER;
		this.useDefaultLib   = DEFAULT_USE_DEFAULT_LIB;

		this.actions   = new HashMap<TemplateTagName, TemplateActionConfig  >();
		this.filters   = new HashMap<TemplateTagName, TemplateFilterConfig  >();
		this.functions = new HashMap<TemplateTagName, TemplateFunctionConfig>();

		this.useLibs = new ArrayList<UseLib>();

//		this.processorConfig = null;

		this.cacheConfig = null;

		this.debug = false;
	}

	/**
	 * Copy Constructor - Create an instance as a copy of another instance.
	 *
	 * @param engineConfig The instance to copy.
	 *
	 * @throws NullPointerException If <code>engineConfig == null</code>
	 */
	public TemplateEngineConfig(final TemplateEngineConfig engineConfig)
	{
		super();

		if (engineConfig == null)
		{
			throw new NullPointerException("engineConfig");
		}

		this.applicationRoot = engineConfig.applicationRoot;
		this.templateRoot    = engineConfig.templateRoot;
		this.tagDelimeter    = engineConfig.tagDelimeter;
		this.useDefaultLib   = engineConfig.useDefaultLib;

		actions   = new HashMap<TemplateTagName, TemplateActionConfig  >(engineConfig.actions  );
		filters   = new HashMap<TemplateTagName, TemplateFilterConfig  >(engineConfig.filters  );
		functions = new HashMap<TemplateTagName, TemplateFunctionConfig>(engineConfig.functions);

		useLibs = new ArrayList<UseLib>(engineConfig.useLibs);

		if (engineConfig.cacheConfig != null)
		{
			cacheConfig = new TemplateCacheConfig(engineConfig.cacheConfig);
		}
		else
		{
			cacheConfig = null;
		}

		debug = engineConfig.debug;
	}

	/**
	 * setApplicationRoot - Set the application root folder.
	 * <p>
	 * By default, the application root is set to ".", or the current
	 * working directory.  Use this method to set the application root
	 * to some other folder on the filesystem.
	 *
	 * @param applicationRoot The application root folder.  <code>null</code> is
	 *        permissable and is equivelent to "."
	 *
	 * @see #DEFAULT_APPLICATION_ROOT
	 * @see #getApplicationRoot()
	 * @see #setTemplateRoot(String)
	 */
	public void setApplicationRoot(final String applicationRoot)
	{
		if (applicationRoot != null)
		{
			this.applicationRoot = applicationRoot;
		}
		else
		{
			this.applicationRoot = DEFAULT_APPLICATION_ROOT;
		}
	}

	/**
	 * getApplicationRoot - Get the application root folder.
	 * @return The application root folder
	 * @see #DEFAULT_APPLICATION_ROOT
	 * @see #setApplicationRoot(String)
	 */
	public String getApplicationRoot()
	{
		return this.applicationRoot;
	}

	/**
	 * setTemplateRoot - Set the template root folder.
	 * <p>
	 * By default, the template root folder is set to the application root
	 * folder. Use this method to set the template root to some other folder on
	 * the filesystem.
	 * <p>
	 * If the specified folder is relative, it is considered to be relative
	 * to the application root folder.
	 *
	 * @param root The template root folder.  <code>null</code> is okay
	 *        and indicates that you want the template root folder to be the
	 *        same as the application root folder.
	 *
	 * @see #DEFAULT_TEMPLATE_ROOT
	 * @see #setApplicationRoot(String)
	 * @see #getTemplateRoot()
	 */
	public void setTemplateRoot(final String root)
	{
		if (templateRoot != null)
		{
			this.templateRoot = root;
		}
		else
		{
			this.templateRoot = DEFAULT_TEMPLATE_ROOT;
		}

//!		System.err.println("setTemplateRoot(" + root + ")");
	}

	/**
	 * getTemplateRoot - Get the template root folder.
	 * <p>
	 * The default template root is "/", which is treated as being whatever
	 * the application root folder is defined as.
	 *
	 * @return The template root folder.
	 *
	 * @see #DEFAULT_TEMPLATE_ROOT
	 * @see #setTemplateRoot(String)
	 * @see #setApplicationRoot(String)
	 */
	public String getTemplateRoot()
	{
		return this.templateRoot;
	}

	/**
	 * getTagDelimeter - Get the tag delimeter.
	 *
	 * @return The tag delimeter
	 * @see #DEFAULT_TAG_DELIMETER
	 * @see TagDelimeter
	 */
	public TagDelimeter getTagDelimeter()
	{
		return this.tagDelimeter;
	}

	/**
	 * setTagDelimeter w/TagDelimeter - Set the tag delimeter.
	 *
	 * @param tagDelimeter The tag delimeter
	 * @see #DEFAULT_TAG_DELIMETER
	 * @see TagDelimeter
	 */
	public void setTagDelimeter(final TagDelimeter tagDelimeter)
	{
		this.tagDelimeter = tagDelimeter;
	}

	/**
	 * setTagDelimeter w/String - Set the tag delimeter.
	 * <p>
	 * This is a convenience method for setting the delimeter from a
	 * <code>String</code>
	 *
	 * @param tagDelimeter The name of the tag delimeter.
	 *
	 * @throws IllegalArgumentException via the valueOf() contract for
	 *         eunumerations.
	 *
	 * @see #DEFAULT_TAG_DELIMETER
	 * @see TagDelimeter
	 */
	public void setTagDelimeter(final String tagDelimeter)
	{
		if (tagDelimeter == null)
		{
			throw new NullPointerException("tagDelimeter");
		}

		this.tagDelimeter = TagDelimeter.valueOf(tagDelimeter);
	}

	/**
	 * isUseDefaultLib - Indicates if the default template library should be
	 * automatically added to the configuration.
	 *
	 * @return A value indicating if the default template library
	 *         should be automatically added to the configuration.
	 *
	 * @see #DEFAULT_USE_DEFAULT_LIB
	 * @see #setUseDefaultLib(boolean)
	 */
	public boolean isUseDefaultLib()
	{
		return this.useDefaultLib;
	}

	/**
	 * setUseDefaultLib - Sets if the default template library should be
	 * automatically added to the configuration.
	 *
	 * @param useDefaultLib A value indicating if the default template library
	 *         should be automatically added to the configuration.
	 *
	 * @see #DEFAULT_USE_DEFAULT_LIB
	 * @see #isUseDefaultLib()
	 */
	public void setUseDefaultLib(boolean useDefaultLib)
	{
		this.useDefaultLib = useDefaultLib;
	}

	/**
	 * setDebug - Enable or disable debugging mode.
	 * <p>
	 * This is really only meant for the project developers and should probably
	 * not be used by normal users. This configures the template parser to
	 * generate a ton of ouput, usefull for tracking down parsing errors.
	 */
	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}

	/**
	 * isDebug
	 */
	public boolean isDebug()
	{
		return debug;
	}

	/**
	 * addAction - Add a template action tag to the configuration.
	 * <p>
	 * This is a convenience method for adding a single action.  For adding an
	 * entire tag library, see addTemplateLib().
	 *
	 * @param prefix The action prefix
	 * @param config The TemplateActionConfig for the action.
	 *
	 * @throws NullPointerException if <code>prefix == null</code> or
	 *         <code>config == null</code>
	 *
	 * @see TemplateActionConfig
	 * @see TemplateTagName
	 * @see TemplateActionTag
	 * @see #addTemplateLib(String, TemplateLibConfig)
	 */
	public void addAction(final String prefix, final TemplateActionConfig config)
	{
		if (prefix == null)
		{
			throw new NullPointerException("prefix");
		}
		if (config == null)
		{
			throw new NullPointerException("config");
		}

		actions.put(new TemplateTagName(prefix, config.getName()) , config);
	}

	/**
	 * getActionConfig - Get the configuration for a specified action tag.
	 *
	 * @param name The full name of the configuration to retrieve.
	 *
	 * @return The TemplateActionConfig for the specified action name
	 *
	 * @throws NullPointerException if <code>name == null</code>
	 * @throws IllegalArgumentException if no action is configured for the
	 *         specified name
	 *
	 * @see #addAction(String, TemplateActionConfig)
	 * @see TemplateTagName
	 * @see TemplateActionConfig
	 * @see TemplateActionTag
	 */
	public TemplateActionConfig getActionConfig(final TemplateTagName name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		if (actions.containsKey(name) == false)
		{
			throw new IllegalArgumentException("TemplateActionTag not registered: " + name);
		}

		return actions.get(name);
	}

	/**
	 * addFilter - Add a template filter tag to the configuration.
	 * <p>
	 * This is a convenience method for adding a single filter.  For adding an
	 * entire tag library, see addTemplateLib().
	 *
	 * @param prefix The filter prefix
	 * @param config The TemplateFilterConfig for the filter.
	 *
	 * @throws NullPointerException if <code>prefix == null</code> or
	 *         <code>config == null</code>
	 *
	 * @see TemplateFilterConfig
	 * @see TemplateTagName
	 * @see TemplateFilterTag
	 * @see #addTemplateLib(String, TemplateLibConfig)
	 */
	public void addFilter(final String prefix, final TemplateFilterConfig config)
	{
		if (prefix == null)
		{
			throw new NullPointerException("prefix");
		}
		if (config == null)
		{
			throw new NullPointerException("config");
		}

		filters.put(new TemplateTagName(prefix, config.getName()), config);
	}

	/**
	 * getFilterConfig - Get the configuration for a specified filter tag.
	 *
	 * @param name The full name of the configuration to retrieve.
	 *
	 * @return The TemplateFilterConfig for the specified filter name
	 *
	 * @throws NullPointerException if <code>name == null</code>
	 * @throws IllegalArgumentException if no filter is configured for the
	 *         specified name
	 *
	 * @see #addFilter(String, TemplateFilterConfig)
	 * @see TemplateTagName
	 * @see TemplateFilterConfig
	 * @see TemplateFilterTag
	 */
	public TemplateFilterConfig getFilterConfig(final TemplateTagName name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		if (filters.containsKey(name) == false)
		{
			throw new IllegalStateException("TemplateFilterTag not registered: " + name);
		}

		return filters.get(name);
	}

	/**
	 * addFunction - Add a template function tag to the configuration.
	 * <p>
	 * This is a convenience method for adding a single function.  For adding an
	 * entire tag library, see addTemplateLib().
	 *
	 * @param prefix The function prefix
	 * @param config The TemplateFunctionConfig for the function.
	 *
	 * @throws NullPointerException if <code>prefix == null</code> or
	 *         <code>config == null</code>
	 *
	 * @see TemplateFunctionConfig
	 * @see TemplateTagName
	 * @see TemplateFunctionTag
	 * @see #addTemplateLib(String, TemplateLibConfig)
	 */
	public void addFunction(final String prefix, TemplateFunctionConfig config)
	{
		if (prefix == null)
		{
			throw new NullPointerException("prefix");
		}
		if (config == null)
		{
			throw new NullPointerException("config");
		}

		functions.put(new TemplateTagName(prefix, config.getName()), config);
	}

	/**
	 * getFunctionConfig - Get the configuration for a specified function tag.
	 *
	 * @param name The full name of the configuration to retrieve.
	 *
	 * @return The TemplateFunctionConfig for the specified function name
	 *
	 * @throws NullPointerException if <code>name == null</code>
	 * @throws IllegalArgumentException if no function is configured for the
	 *         specified name
	 *
	 * @see #addFunction(String, TemplateFunctionConfig)
	 * @see TemplateTagName
	 * @see TemplateFunctionConfig
	 * @see TemplateFunctionTag
	 */
	public TemplateFunctionConfig getFunctionConfig(final TemplateTagName name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		if (functions.containsKey(name) == false)
		{
			throw new IllegalStateException("TemplateFunctionTag not registered: " + name);
		}

		return functions.get(name);
	}

	/**
	 * addTemplateLib - Add a template libary to the configuration.
	 *
	 * @param prefix The prefix for each tag in the library.
	 * @param libConfig The TemplateLibConfig to add
	 *
	 *  @throws NullPointerException if <code>prefix == null</code> or
	 *          <code>libConfig == null</code>
	 *
	 * @see TemplateLibConfig
	 */
	public void addTemplateLib(final String prefix, final TemplateLibConfig libConfig)
	{
		if (prefix == null)
		{
			throw new NullPointerException("prefix");
		}
		if (libConfig == null)
		{
			throw new NullPointerException("libConfig");
		}

		for (TemplateActionConfig config : libConfig.getActions())
		{
			addAction(prefix, config);
		}
		for (TemplateFunctionConfig config : libConfig.getFunctions())
		{
			addFunction(prefix, config);
		}
		for (TemplateFilterConfig config : libConfig.getFilters())
		{
			addFilter(prefix, config);
		}
	}

//	/**
//	 * getProcessorConfig
//	 * @return Returns the processorConfig.
//	 */
//	public TemplateProcessorConfig getProcessorConfig()
//	{
//		return this.processorConfig;
//	}
//
//	/**
//	 * setProcessorConfig
//	 * @param processorConfig The processorConfig to set.
//	 */
//	public void setProcessorConfig(TemplateProcessorConfig processorConfig)
//	{
//		if (processorConfig == null)
//		{
//			throw new NullPointerException("processorConfig");
//		}
//
//		this.processorConfig = processorConfig;
//	}

	/**
	 * addUseLib - Add a reference to template library XML configuration.
	 * <p>
	 * This allows you to add a reference to a template library
	 * xml configuration that will be loaded and processed by the template
	 * engine during instance creation.
	 * <p>
	 * This is basically a hack to get around the fact that we don't know /
	 * haven't verified the application root while processing the
	 * template engine XML configuration and have to go back after we have
	 * an application root and load any libraries that use 'file =' to specify
	 * their location.
	 * <p>
	 * I would make this to be package-private, but template engine uses
	 * <code>com.inamik.template.util.TemplateConfigUtil</code> and I
	 * want that to stay in .util for now.
	 *
	 * @param useLib The library reference to add
	 *
	 * @see UseLib
	 * @see #getUseLibs()
	 * @see #clearUseLibs()
	 */
	public void addUseLib(final UseLib useLib)
	{
		this.useLibs.add(useLib);
	}

	/**
	 * getUseLibs - Get the list of configured useLib's.
	 *
	 * @return An array of UseLib[]
	 *
	 * @see #addUseLib(UseLib)
	 */
	public UseLib[] getUseLibs()
	{
		return useLibs.toArray(new UseLib[useLibs.size()]);
	}

	/**
	 * clearUseLibs - To be called after use-libs have been resolved.
	 */
	public void clearUseLibs()
	{
		useLibs.clear();
	}

	/**
	 * getCacheConfig - Get the cache coniguration, or <code>null</code>
	 * if none has been set.
	 *
	 * @return Returns the cacheConfig.
	 *
	 * @see TemplateCacheConfig
	 */
	public TemplateCacheConfig getCacheConfig()
	{
		return this.cacheConfig;
	}

	/**
	 * setCacheConfig - Set the cache configuration
	 *
	 * @param cacheConfig The cacheConfig to set. <code>null</code> is okay.
	 *
	 * @see TemplateCacheConfig
	 */
	public void setCacheConfig(TemplateCacheConfig cacheConfig)
	{
		this.cacheConfig = cacheConfig;
	}
}
