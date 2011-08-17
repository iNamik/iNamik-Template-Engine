/*
 * $Id: TemplateTagName.java,v 1.3 2006-08-21 03:07:12 dave Exp $
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * TemplateTagName - A fully qualified name of a template tag.
 * <p>
 * This class is a container for a prefix and name, which are both necessary
 * to fully qualify a template tag.
 * <p>
 * A convenience constructor is provided for generating a name with the default
 * prefix.
 * <p>
 * Created on Jul 10, 2006
 *
 * @author Dave
 * @see #DEFAULT_PREFIX
 */
public final class TemplateTagName implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -5239680821726335835L;

	public static final String DEFAULT_PREFIX = "main";

	private transient volatile int hashCode = 0;

	private String prefix;
	private String name;

	public TemplateTagName(final String name)
	{
		this(DEFAULT_PREFIX, name);
	}

	/**
	 * Constructor
	 */
	public TemplateTagName(final String prefix, final String name)
	{
		super();

		init(prefix, name);

	}

	private void init(final String aPrefix, final String aName)
	{
		if (aPrefix == null)
		{
			throw new NullPointerException("prefix");
		}
		if (aName == null)
		{
			throw new NullPointerException("name");
		}

		this.prefix = aPrefix;
		this.name   = aName;

		// Precompute hash code since we will be using this
		// as a hash key
		int h = 17;
		h = 37 * h + aPrefix.hashCode();
		h = 37 * h + aName.hashCode();

		this.hashCode = h;
	}

	/**
	 * getPrefix - Get the prefix for the tag name.
	 * @return The prefix for the tag name.
	 */
	public String getPrefix()
	{
		return this.prefix;
	}

	/**
	 * getName - Get the name of the tag.
	 * @return The name of the tag.
	 */
	public String getName()
	{
		return this.name;
	}

	@Override
	public boolean equals(Object obj)
	{
		TemplateTagName f = (TemplateTagName)obj;

		return prefix.equals(f.getPrefix()) && name.equals(f.getName());
	}

	@Override
	public int hashCode()
	{
		return hashCode;
	}

	@Override
	public String toString()
	{
		if (DEFAULT_PREFIX.equals(prefix) == true)
		{
			return name;
		}

		return prefix + ":" + name;
	}

	/**
	 * writeObject
	 */
	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();
//		s.writeObject(prefix);
//		s.writeObject(name);
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();
//		prefix = (String)s.readObject();
//		name   = (String)s.readObject();

		init(prefix, name);
	}
}
