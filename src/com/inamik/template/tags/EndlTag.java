/*
 * $Id: EndlTag.java,v 1.6 2006-08-14 22:31:00 dave Exp $
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
 * EndlTag
 */
public class EndlTag extends AbstractTag implements Serializable
{
	private static final EndlTag instance = new EndlTag();

	/** serialVersionUID */
	private static final long serialVersionUID = -5726482937622382229L;

	/**
	 * Constructor (private)
	 */
	private EndlTag()
	{
		super();
	}

	public static EndlTag getInstance()
	{
		return instance;
	}

	public TagType getType()
	{
		return TagType.ENDL;
	}

	@Override
	public String toString()
	{
		return "<\n";
	}
}
