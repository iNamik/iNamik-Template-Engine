/*
 * $Id: EasyHibernateCache.java,v 1.3 2006-08-17 04:43:55 dave Exp $
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

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.inamik.template.TemplateCache;
import com.inamik.template.TemplateCacheEntry;

/**
 * EasyHibernateCache
 * Created on Aug 15, 2006
 * @author Dave
 */
public final class EasyHibernateCache implements TemplateCache
{

	private final Ehcache cache;

	/**
	 * Constructor (Package-Private) w/Cache
	 */
	EasyHibernateCache(final Ehcache cache)
	{
		super();

		this.cache = cache;
	}

	public void putEntry(final String name, final TemplateCacheEntry entry)
	{
		cache.put( new Element(name, entry));
	}

	public TemplateCacheEntry getEntry(final String name)
	{
		TemplateCacheEntry result;

		Element element = cache.get(name);

		if (element != null)
		{
			result = (TemplateCacheEntry)element.getValue();
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
