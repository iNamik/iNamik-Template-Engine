/*
 * $Id: WeakReferenceCache.java,v 1.2 2006-08-17 04:43:55 dave Exp $
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

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.inamik.template.TemplateCache;
import com.inamik.template.TemplateCacheEntry;
import com.inamik.template.TokenizedTemplate;

/**
 * WeakReferenceCache
 * Created on Aug 15, 2006
 * @author Dave
 */
public final class WeakReferenceCache implements TemplateCache
{
	private static final class CacheEntry
	{
		protected final long creationTime;

		protected final WeakReference<Serializable> value;

		protected CacheEntry(final TemplateCacheEntry entry)
		{
			super();

			this.creationTime   = entry.getCreationTime();
			this.value          = new WeakReference<Serializable>(entry.getValue());
		}
	}

	private Map<String, CacheEntry> cache;

	/**
	 * Constructor
	 */
	public WeakReferenceCache()
	{
		super();

		cache = new HashMap<String, CacheEntry>();
	}

	public void putEntry(String name, TemplateCacheEntry entry)
	{
		cache.put(name, new CacheEntry(entry));
	}

	public TemplateCacheEntry getEntry(String name)
	{
		TemplateCacheEntry result;

		CacheEntry cacheEntry = cache.get(name);

		if (cacheEntry != null)
		{
			WeakReference<Serializable> ref = cacheEntry.value;

			Serializable value = ref.get();

			if (value != null)
			{
				result = new TemplateCacheEntry(value, cacheEntry.creationTime);
			}
			else
			{
				result = null;
			}
		}
		else
		{
			result = null;
		}

		return result;
	}

	public void removeEntry(String name)
	{
		cache.remove(name);
	}
}
