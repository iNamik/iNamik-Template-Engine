/*
 * $Id: ClassLoaderUtil.java,v 1.3 2006-08-21 03:07:12 dave Exp $
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

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ClassLoaderUtil - Utility class for dealing with classloaders and resources
 * on the classpath.
 * <p>
 * Borrowed ideas found in EHCACHE
 * <p>
 * Created on Jul 30, 2006
 * @author Dave
 */
public final class ClassLoaderUtil
{
	private static final Log LOG = LogFactory.getLog(ClassLoaderUtil.class.getName());

	/**
	 * Constructor
	 */
	private ClassLoaderUtil() {}

	/**
	 * getStandardClassLoader - Get the current thread's ClassLoader.
	 *
	 * @return The current thread's ClassLoader
	 */
	public static ClassLoader getStandardClassLoader()
	{
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * getLocalClassLoader - Get the ClassLoader for this class
	 * (ClassLoaderUtil).
	 *
	 * @return The ClassLoader for ClassLoaderUtil
	 */
	public static ClassLoader getLocalClassLoader()
	{
		return ClassLoaderUtil.class.getClassLoader();
	}

	/**
	 * getResourceAsStream - Try to find a resource on the classpath and
	 * return a stream to the found resource.
	 * <p>
	 * This method first checks the standard class loader.  If that fails,
	 * it then checks the local class loader.
	 * <p>
	 * It is up to the caller to close the stream.
	 *
	 * @param resource The resource to find on the classpath.
	 *
	 * @return A stream to the classpath resource, or <code>null</code>
	 *         if the resource was not found.
	 *
	 * @see #getStandardClassLoader()
	 * @see #getLocalClassLoader()
	 */
	public static InputStream getResourceAsStream(final String resource)
	{
		if (resource == null)
		{
			throw new NullPointerException("resource");
		}

		InputStream stream = getStandardClassLoader().getResourceAsStream(resource);

		if (stream == null)
		{
			stream = getLocalClassLoader().getResourceAsStream(resource);
		}

		if (stream == null)
		{
			if (LOG.isInfoEnabled())
				LOG.info("Unable to find resource '" + resource + "'");
		}

		return stream;
	}

	/**
	 * getResourceAsURL - Try to find a resource on the classpath and
	 * return a URL to the found resource.
	 * <p>
	 * This method first checks the standard class loader.  If that fails,
	 * it then checks the local class loader.
	 *
	 * @param resource The resource to find on the classpath.
	 *
	 * @return A URL to the classpath resource, or <code>null</code>
	 *         if the resource was not found.
	 *
	 * @see #getStandardClassLoader()
	 * @see #getLocalClassLoader()
	 */
	public static URL getResourceAsURL(final String resource)
	{
		if (resource == null)
		{
			throw new NullPointerException("resource");
		}

		URL url = getStandardClassLoader().getResource(resource);

		if (url == null)
		{
			url = getLocalClassLoader().getResource(resource);
		}

		if (url == null)
		{
			if (LOG.isInfoEnabled())
				LOG.info("Unable to find resource '" + resource + "'");
		}

		return url;
	}

	/**
	 * createNewInstance - Create a new instance of the specified class.
	 * <p>
	 * This method first tries to create the new instance using the standard
	 * class loader.  If that fails, it then tries using the local class
	 * loader.
	 *
	 * @param className The name of the class to instantiate.
	 * @return New instance of the specified class, or null if the class could
	 *         not be found or instantiated.
	 *
	 * @see #getStandardClassLoader()
	 * @see #getLocalClassLoader()
	 */
	public static Object createNewInstance(final String className)
	{
		if (className == null)
		{
			throw new NullPointerException("className");
		}

		Class clazz;

		Object newInstance;

		try
		{
			clazz = Class.forName(className, true, getStandardClassLoader());
		}
		catch (ClassNotFoundException e)
		{
			try
			{
				clazz = Class.forName(className, true, getLocalClassLoader());
			}
			catch (ClassNotFoundException ex)
			{
				if (LOG.isWarnEnabled())
					LOG.warn("Unable to find class '" + className + "'");

				return null;
			}
		}

		try
		{
			newInstance = clazz.newInstance();
		}
		catch (IllegalAccessException e)
		{
			if (LOG.isWarnEnabled())
				LOG.warn("Unable to load class '" + className + "'. Cause was " + e.getMessage(), e);

			return null;
		}
		catch (InstantiationException e)
		{
			if (LOG.isWarnEnabled())
				LOG.warn("Unable to load class '" + className + "'. Cause was " + e.getMessage(), e);

			return null;
		}

		return newInstance;
	}
}
