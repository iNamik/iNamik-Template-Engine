/*
 * $Id: TemplateCacheConfig.java,v 1.5 2006-08-31 22:56:38 dave Exp $
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

/**
 * TemplateCacheConfig - Configuration class for EasyHibernateCache's.
 * <p>
 * This class is instantiated with acceptable defaults and can be used
 * to create a functioning in-memory cache without any modification.
 * <p>
 * Created on Jul 27, 2006
 * @author Dave
 */
public final class TemplateCacheConfig
{
	/**
	 * EvictionPolicy - An enumeration specifying the acceptable memory
	 * eviction policies.
	 * <p>
	 * Values are:
	 * <p>
	 * LFU - Least Frequently Used
	 * <br>
	 * LRU - Least Recently Used
	 * <br>
	 * FIFO - First In First Out
	 */
	public static enum EvictionPolicy
	{
		/** Least Frequently Used */
		LFU,

		/** Least Recently Used */
		LRU,

		/** First In First Out */
		FIFO,
	}

	public static final String         DEFAULT_CACHE_NAME                          = "deafult";
	public static final int            DEFAULT_DISK_EXPIRY_THREAD_INTERVAL_SECONDS = 120;
	public static final boolean        DEFAULT_DISK_PERSISTENT                     = false;
	public static final String         DEFAULT_DISK_ROOT                           = null;
	public static final boolean        DEFAULT_ETERNAL                             = true;
	public static final int            DEFAULT_MAX_ELEMENTS_IN_MEMORY              = 100;
	public static final EvictionPolicy DEFAULT_EVICTION_POLICY                     = EvictionPolicy.LFU;
	public static final boolean        DEFAULT_OVERFLOW_TO_DISK                    = false;
	public static final int            DEFAULT_TIME_TO_IDLE_SECONDS                = 30;
	public static final int            DEFAULT_TIME_TO_LIVE_SECONDS                = 300;

	private String         name;
	private int            diskExpiryThreadIntervalSeconds;
	private boolean        diskPersistent;
	private String         diskRoot;
	private boolean        eternal;
	private int            maxElementsInMemory;
	private EvictionPolicy memoryStoreEvictionPolicy;
	private boolean        overflowToDisk;
	private int            timeToIdleSeconds;
	private int            timeToLiveSeconds;

	/**
	 * Default Constructor
	 */
	public TemplateCacheConfig()
	{
		super();

		this.name                            = DEFAULT_CACHE_NAME;
		this.diskExpiryThreadIntervalSeconds = DEFAULT_DISK_EXPIRY_THREAD_INTERVAL_SECONDS;
		this.diskPersistent                  = DEFAULT_DISK_PERSISTENT;
		this.diskRoot                        = DEFAULT_DISK_ROOT;
		this.eternal                         = DEFAULT_ETERNAL;
		this.maxElementsInMemory             = DEFAULT_MAX_ELEMENTS_IN_MEMORY;
		this.memoryStoreEvictionPolicy       = DEFAULT_EVICTION_POLICY;
		this.overflowToDisk                  = DEFAULT_OVERFLOW_TO_DISK;
		this.timeToIdleSeconds               = DEFAULT_TIME_TO_IDLE_SECONDS;
		this.timeToLiveSeconds               = DEFAULT_TIME_TO_LIVE_SECONDS;
	}

	/**
	 * Copy Constructor - Create an instance as a copy of another instance.
	 *
	 * @param cacheConfig The instance to copy.
	 *
	 * @throws NullPointerException If <code>cacheConfig == null</code>
	 */
	public TemplateCacheConfig(final TemplateCacheConfig cacheConfig)
	{
		super();

		if (cacheConfig == null)
		{
			throw new NullPointerException("cacheConfig");
		}

		this.name                            = cacheConfig.name;
		this.diskExpiryThreadIntervalSeconds = cacheConfig.diskExpiryThreadIntervalSeconds;
		this.diskPersistent                  = cacheConfig.diskPersistent;
		this.diskRoot                        = cacheConfig.diskRoot;
		this.eternal                         = cacheConfig.eternal;
		this.maxElementsInMemory             = cacheConfig.maxElementsInMemory;
		this.memoryStoreEvictionPolicy       = cacheConfig.memoryStoreEvictionPolicy;
		this.overflowToDisk                  = cacheConfig.overflowToDisk;
		this.timeToIdleSeconds               = cacheConfig.timeToIdleSeconds;
		this.timeToLiveSeconds               = cacheConfig.timeToLiveSeconds;
	}

	/**
	 * setName - Set the name of the cache configuration.
	 * <p>
	 * The name is most important when you are using multiple
	 * template engine instances with different cache
	 * configurations.  If you use the same name as an
	 * already loaded cache configuration, the interpreter
	 * will assume you want to re-use that configuration.
	 * This feature can come in handy in situations where you
	 * really do want to use the same cache configuration
	 * across different template engine configurations.
	 * <p>
	 * Just to be clear: If you are running multiple template
	 * engine instances with different cache configurations,
	 * make sure each configuration has a unique 'name'
	 * <p>
	 * If you're just using a single configuration, then the
	 * default name ('defaut') should work fine.
	 *
	 * @param name The name of the cache configuration.
	 *        <code>null</code> is okay and is equivelent to
	 *        the value defined in DEFAULT_CACHE_NAME
	 *
	 * @see #DEFAULT_CACHE_NAME
	 */
	public void setName(final String name)
	{
		if (name != null)
		{
			this.name = name;
		}
		else
		{
			this.name = DEFAULT_CACHE_NAME;
		}
	}

	/**
	 * getName - Get the cache configuration name.
	 *
	 * @return The cache configuration name.
	 * @see #DEFAULT_CACHE_NAME
	 */
	public String getName()
	{
		assert name != null;

		return this.name;
	}

	/**
	 * getDiskExpiryThreadIntervalSeconds -
	 *  Gets The interval, in seconds, between runs of the disk
	 *  expiry thread.
	 *
	 * @return The interval, in seconds.
	 * @see #DEFAULT_DISK_EXPIRY_THREAD_INTERVAL_SECONDS
	 */
	public int getDiskExpiryThreadIntervalSeconds()
	{
		return this.diskExpiryThreadIntervalSeconds;
	}

	/**
	 * setDiskExpiryThreadIntervalSeconds -
	 *  Sets The interval, in seconds, between runs of the disk
	 *  expiry thread. This is not the same thing as time to
	 *  live or time to idle. When the thread runs it checks
	 *  these things. So this value is how often we check for
	 *  expiry.
	 *
	 * @param interval The desired interval, in seconds.
	 * @throws IllegalArgumentException If
	 *         <code>interval &lt; 0</code>
	 * @see #DEFAULT_DISK_EXPIRY_THREAD_INTERVAL_SECONDS
	 */
	public void setDiskExpiryThreadIntervalSeconds(int interval)
	{
		if (interval < 0)
		{
			throw new IllegalArgumentException("interval < 0");
		}

		this.diskExpiryThreadIntervalSeconds = interval;
	}

	/**
	 * isDiskPersistent - For caches that overflow to disk,
	 * does the disk cache persist between Template Engine instances.
	 *
	 * @return A value indicating if the disk cache persist between Template
	 *         Engine instances.
	 *
	 * @see #DEFAULT_DISK_PERSISTENT
	 */
	public boolean isDiskPersistent()
	{
		return this.diskPersistent;
	}

	/**
	 * setDiskPersistent - For caches that overflow to disk,
	 * does the disk cache persist between JVM instances.
	 *
	 * @param diskPersistent A value indicating if the disk cache persist
	 *        between Template Engine instances.
	 *
	 * @see #DEFAULT_DISK_PERSISTENT
	 */
	public void setDiskPersistent(boolean diskPersistent)
	{
		this.diskPersistent = diskPersistent;
	}

	/**
	 * getDiskRoot -
	 * Get The root directory in which to create the cache data and index files.
	 *
 	 * @return The root directory in which to create the cache data and index
 	 *         files, or <code>null</code> if one has not been set.
 	 *
 	 * @see #DEFAULT_DISK_ROOT
 	 * @see #setOverflowToDisk(boolean)
	 */
	public String getDiskRoot()
	{
		return this.diskRoot;
	}

	/**
	 * setDiskRoot
	 * Set The root directory in which to create the cache data and index files.
	 * <p>
	 * Multiple cache configurations can share the same
	 * disk root, as the 'name' will be incorperated into the
	 * cache data and index files.
	 * <p>
	 * If the specified folder is relative, then it is considered to be
	 * relative to the applicationRoot.
	 * <p>
	 * In order to enable disk-caching, you have to set diskRoot as well as
	 * <code>setOverflowToDisk(true)</code>
	 *
	 * @param diskRoot The root directory in which to create the cache data and
	 *        index files. <code>null</code> is okay and indicates that you
	 *        do not want to enable disk caching.
 	 *
 	 * @see #DEFAULT_DISK_ROOT
 	 * @see #setOverflowToDisk(boolean)
 	 * @see TemplateEngineConfig#setApplicationRoot(String)
	 */
	public void setDiskRoot(String diskRoot)
	{
		this.diskRoot = diskRoot;
	}

	/**
	 * isEternal - Get whether elements are non-expiring.
	 * @return Whether elements are non-expiring.
	 * @see #DEFAULT_ETERNAL
	 */
	public boolean isEternal()
	{
		return this.eternal;
	}

	/**
	 * setEternal - Set whether elements are non-expiring.
	 * <p>
	 * When elements are non-expiring, the <code>timeToIdleSeconds</code> and
	 * <code>timeToLiveSeconds</code> are ignored.
	 *
	 * @param eternal Whether elements are non-expiring.
	 * @see #DEFAULT_ETERNAL
	 * @see #setTimeToIdleSeconds(int)
	 * @see #setTimeToLiveSeconds(int)
	 */
	public void setEternal(boolean eternal)
	{
		this.eternal = eternal;
	}

	/**
	 * getMaxElementsInMemory - Get the maximun number of elements the cache
	 *  will keep in memory.
	 *
	 * @return The maximun number of elements the cache
	 *         will keep in memory.
	 *
	 * @see #DEFAULT_MAX_ELEMENTS_IN_MEMORY
	 */
	public int getMaxElementsInMemory()
	{
		return this.maxElementsInMemory;
	}

	/**
	 * setMaxElementsInMemory - Set the maximun number of elements the cache
	 * will keep in memory.
	 * <p>
	 * Once this number is reached, new elements to the cache will cause
	 * older elements to either be evicted from the cache or, if enabled,
	 * spooled to disk.
	 * <p>
	 * WARNING: This value should not be less than 1 as the cache
	 *          performance will be greatly affected.
	 *
	 * @param maxElementsInMemory The maximun number of elements the cache
	 *        will keep in memory.
	 *
	 * @throws IllegalArgumentException If
	 *         <code>maxElementsInMemory &lt; 0</code>
	 *
	 * @see #DEFAULT_MAX_ELEMENTS_IN_MEMORY
	 */
	public void setMaxElementsInMemory(int maxElementsInMemory)
	{
		if (maxElementsInMemory < 0)
		{
			throw new IllegalArgumentException("maxElementsInMemory < 0");
		}

		this.maxElementsInMemory = maxElementsInMemory;
	}

	/**
	 * getMemoryStoreEvictionPolicy - Get the policy used to evict elements
	 * from the cache.
	 * @return The policy used to evict elements from the cache.
	 * @see #DEFAULT_EVICTION_POLICY
	 * @see EvictionPolicy
	 */
	public TemplateCacheConfig.EvictionPolicy getMemoryStoreEvictionPolicy()
	{
		return this.memoryStoreEvictionPolicy;
	}

	/**
	 * setMemoryStoreEvictionPolicy - Set the policy used to evict elements
	 * from the cache.
	 * @param policy The policy used to evict elements from
	 *        the cache.
	 * @throws NullPointerException If <code>policy == null</code>
	 * @see #DEFAULT_EVICTION_POLICY
	 * @see EvictionPolicy
	 */
	public void setMemoryStoreEvictionPolicy(final EvictionPolicy policy)
	{
		if (policy == null)
		{
			throw new NullPointerException("policy");
		}

		this.memoryStoreEvictionPolicy = policy;
	}

	/**
	 * isOverflowToDisk - Get whether cache elements in this cache overflow to
	 * disk.
	 * @return Whether cache elements in this cache overflow to disk.
	 * @see #DEFAULT_OVERFLOW_TO_DISK
	 */
	public boolean isOverflowToDisk()
	{
		return this.overflowToDisk;
	}

	/**
	 * setOverflowToDisk - Set whether cache elements in this cache overflow to
	 * disk.
	 * <p>
	 *  To enable disk-caching, you need to setOverflowToDisk(true),
	 *  as well as setDiskRoot() to valid folder.
	 * @param overflowToDisk Whether cache elements in this cache overflow to
	 *        disk.
	 * @see #DEFAULT_OVERFLOW_TO_DISK
	 * @see #setDiskRoot(String)
	 */
	public void setOverflowToDisk(boolean overflowToDisk)
	{
		this.overflowToDisk = overflowToDisk;
	}

	/**
	 * getTimeToIdleSeconds - Get the maximum amount of time, in seconds,
	 * between cache retrievals before an element expires.
	 *
	 * @return The maximum amount of time, in seconds,
	 *         between cache retrievals before an element expires.
	 *
	 * @see #DEFAULT_TIME_TO_IDLE_SECONDS
	 */
	public int getTimeToIdleSeconds()
	{
		return this.timeToIdleSeconds;
	}

	/**
	 * setTimeToIdleSeconds - Set the maximum amount of time, in seconds,
	 * between cache retrievals before an element expires.
	 * <p>
	 * Is only used if <code>isEternal() == false</code>.
	 *
	 * @param timeToIdleSeconds The maximum amount of time, in seconds,
	 *        between cache retrievals before an element expires.
	 *
	 * @see #DEFAULT_TIME_TO_IDLE_SECONDS
	 * @see #setEternal(boolean)
	 */
	public void setTimeToIdleSeconds(int timeToIdleSeconds)
	{
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

	/**
	 * getTimeToLiveSeconds - Get the maximum time, in seconds, between
	 * creation time and when an element expires.
	 *
	 * @return The maximum time, in seconds, between
	 *         creation time and when an element expires.
	 *
	 * @see #DEFAULT_TIME_TO_LIVE_SECONDS
	 * @see #setEternal(boolean)
	 */
	public int getTimeToLiveSeconds()
	{
		return this.timeToLiveSeconds;
	}

	/**
	 * setTimeToLiveSeconds - Set the maximum time, in seconds, between
	 * creation time and when an element expires.
	 * <p>
	 * Is only used if <code>isEternal() == false</code>.
	 *
	 * @param timeToLiveSeconds The maximum time, in seconds, between
	 *        creation time and when an element expires.
	 *
	 * @see #DEFAULT_TIME_TO_LIVE_SECONDS
	 * @see #setEternal(boolean)
	 */
	public void setTimeToLiveSeconds(int timeToLiveSeconds)
	{
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

}