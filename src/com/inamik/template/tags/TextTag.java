/*
 * $Id: TextTag.java,v 1.4 2006-08-14 22:31:00 dave Exp $
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

import java.io.Serializable;

/**
 * TextTag
 */
public class TextTag extends AbstractTag implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = 5078796190023540688L;
	private String text = null;

	/**
	 * Constructor
	 */
	public TextTag(String text)
	{
		super();

		this.text = text;
	}

	/**
	 * getText
	 */
	public String getText()
	{
		return text;
	}

	public TagType getType()
	{
		return TagType.TEXT;
	}

	@Override
	public String toString()
	{
		return getText();
	}
}
