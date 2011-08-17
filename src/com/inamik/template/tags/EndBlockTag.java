/*
 * $Id: EndBlockTag.java,v 1.7 2006-08-14 22:31:00 dave Exp $
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

import com.inamik.template.TemplateTagName;

/**
 * EndBlockTag - Now only used by the parser and is not passed to the compiler
 */
public class EndBlockTag extends AbstractTag
{
	/** serialVersionUID */
	private static final long serialVersionUID = -3189375410099147038L;
	private TemplateTagName fqname = null;

	/**
	 * Constructor w/fqname
	 */
	public EndBlockTag(final TemplateTagName fqname)
	{
		this();

		this.fqname = fqname;
	}

	/**
	 * Constructor w/o fqname
	 */
	public EndBlockTag()
	{
		super();
	}

	/**
	 * getFQName
	 */
	public TemplateTagName getFQName()
	{
		return fqname;
	}

	public TagType getType()
	{
		return TagType.END_BLOCK;
	}

	@Override
	public String toString()
	{
		String result;

		if (fqname != null)
		{
			result = new StringBuffer().append("{/").append(fqname).append("}").toString();
		}
		else
		{
			result = "{/}";
		}

		return result;
	}
}
