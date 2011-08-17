/*
 * $Id: EasyHibernateCacheFactory.java,v 1.2 2006-08-16 22:41:51 dave Exp $
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
package com.inamik.template.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import com.inamik.template.TemplateCacheConfig;

/**
 * EasyHibernateCacheFactory
 * Created on Aug 15, 2006
 * @author Dave
 */
public final class EasyHibernateCacheFactory
{
    private static final Log LOG = LogFactory.getLog(EasyHibernateCacheFactory.class.getName());

	private static final String CACHE_NAME_PREFIX = "com.inamik.template.templateCache-";

	private static Map<String, CacheManager> ALL_CACHE_MANAGERS = null;

	/**
	 * Default Constructor (Private)
	 */
	private EasyHibernateCacheFactory() {}

	public static EasyHibernateCache getCache(final TemplateCacheConfig cacheConfig)
	{
		final CacheManager cacheManager = getCacheManager(cacheConfig.getDiskRoot());

		if (LOG.isDebugEnabled())
		{
			for (String s : cacheManager.getCacheNames())
			{
				LOG.debug("Registered Cache: " + s);
			}
		}

		final String cacheName = CACHE_NAME_PREFIX + cacheConfig.getName();

		// If cache does not already exist
		if (cacheManager.cacheExists(cacheName) == false)
		{
			Cache newCache = new Cache
			(
					cacheName,
					cacheConfig.getMaxElementsInMemory(),
					MemoryStoreEvictionPolicy.fromString(cacheConfig.getMemoryStoreEvictionPolicy().name()),
					cacheConfig.isOverflowToDisk(),
					cacheConfig.getDiskRoot(), // Not really needed, gets overwritten
					cacheConfig.isEternal(),
					cacheConfig.getTimeToLiveSeconds(),
					cacheConfig.getTimeToIdleSeconds(),
					cacheConfig.isDiskPersistent(),
					cacheConfig.getDiskExpiryThreadIntervalSeconds(),
					null
			);

			cacheManager.addCache(newCache);
		}
		// Cache already exists
		else
		{
			if (LOG.isWarnEnabled())
				LOG.warn("Cache with name '" + cacheName + "' already exists.  Using it.");
		}

		Ehcache ehCache = cacheManager.getCache(cacheName);

		return new EasyHibernateCache(ehCache);
	}

	/**
	 * getCacheManager - Finds the cache manager for the specified cache
	 * directory, creating a new one instance if one does not exist.
	 */
	private static synchronized CacheManager getCacheManager(final String diskRoot)
	{
		if (ALL_CACHE_MANAGERS == null)
		{
			ALL_CACHE_MANAGERS = new HashMap<String, CacheManager>();
		}

		if (ALL_CACHE_MANAGERS.containsKey(diskRoot) == false)
		{
			if (LOG.isTraceEnabled())
				LOG.trace("Creating CacheManager for diskRoot '" + diskRoot + "'");

			final CacheConfiguration  defaultcc   = new CacheConfiguration();
			final TemplateCacheConfig cacheConfig = new TemplateCacheConfig(); // Default Config

			defaultcc.setName                           (null); // default
			defaultcc.setDiskExpiryThreadIntervalSeconds(cacheConfig.getDiskExpiryThreadIntervalSeconds());
			defaultcc.setDiskPersistent                 (cacheConfig.isDiskPersistent());
			defaultcc.setEternal                        (cacheConfig.isEternal());
			defaultcc.setMaxElementsInMemory            (cacheConfig.getMaxElementsInMemory());
			defaultcc.setMemoryStoreEvictionPolicy      (cacheConfig.getMemoryStoreEvictionPolicy().name());
			defaultcc.setOverflowToDisk                 (cacheConfig.isOverflowToDisk());
			defaultcc.setTimeToIdleSeconds              (cacheConfig.getTimeToIdleSeconds());
			defaultcc.setTimeToLiveSeconds              (cacheConfig.getTimeToLiveSeconds());

			final Configuration cmc = new Configuration();

			if (diskRoot != null)
			{
				final DiskStoreConfiguration dsc = new DiskStoreConfiguration();

				dsc.setPath(diskRoot);

				cmc.addDiskStore(dsc);
			}

			cmc.setDefaultCacheConfiguration(defaultcc);

			final CacheManager cacheManager = new CacheManager(cmc);

			ALL_CACHE_MANAGERS.put(diskRoot, cacheManager);
		}

		assert ALL_CACHE_MANAGERS.containsKey(diskRoot);

		if (LOG.isTraceEnabled())
			LOG.trace("Retrieved CacheManager for diskRoot '" + diskRoot + "'");

		return ALL_CACHE_MANAGERS.get(diskRoot);
	}
}
