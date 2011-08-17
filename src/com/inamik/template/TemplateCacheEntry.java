/*
 * $Id: TemplateCacheEntry.java,v 1.1 2006-08-16 08:50:28 dave Exp $
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

import java.io.Serializable;

/**
 * TemplateCacheEntry
 * Created on Aug 15, 2006
 * @author Dave
 */
public final class TemplateCacheEntry implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = 4771812650232868283L;

	private final long creationTime;
	private final Serializable value;

	public TemplateCacheEntry(final Serializable value)
	{
		this(value, System.currentTimeMillis());
	}

	public TemplateCacheEntry(final Serializable value, final long creationTime)
	{
		super();

		this.value          = value;
		this.creationTime   = creationTime;
	}

//	public TemplateCacheEntry(final TemplateCacheEntry entry)
//	{
//		super();
//
//		this.value          = entry.value;
//		this.creationTime   = entry.creationTime;
//	}

	public long getCreationTime()
	{
		return this.creationTime;
	}

	public Serializable getValue()
	{
		return this.value;
	}
}
