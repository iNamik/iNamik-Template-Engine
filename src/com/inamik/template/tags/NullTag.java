/*
 * $Id: NullTag.java,v 1.2 2006-07-31 10:07:10 davidpfarrell Exp $
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
package com.inamik.template.tags;

/**
 * NullTag - Used only in the parser and not passed to the compiler
 * Created on Jul 22, 2006
 * @author Dave
 */
public final class NullTag extends AbstractTag
{
	/** serialVersionUID */
	private static final long serialVersionUID = -1539793425240373335L;
	private static final NullTag instance = new NullTag();

	/**
	 * Constructor (private)
	 */
	private NullTag()
	{
		super();
	}

	public static NullTag getInstance()
	{
		return instance;
	}

	/**
	 * getType
	 */
	public TagType getType()
	{
		return TagType.NULL;
	}

	@Override
	public String toString()
	{
		return "";
	}
}
