/*
 * $Id: TagType.java,v 1.7 2006-11-21 09:34:42 dave Exp $
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
 * TagType
 */
public enum TagType
{
	ACTION              ("Action"             ),
	BLOCK_COMMENT       ("Block-Comment"      ),
	BODY_ACTION         ("Body-Action"        ),
	END_BLOCK           ("End-Block"          ),
	ENDL                ("ENDL"               ),
	EOF                 ("EOF"                ),
	EXPRESSION          ("Expression"         ),
	LEADING_SPACE       ("Leading-Space"      ),
	LINE_COMMENT        ("Line-Comment"       ),
	MULTIPLE_TAGS       ("Multiple-Tags"      ),
	NULL                ("Null"               ),
	QUOTED_STRING       ("Quoted-String"      ),
	SPACE               ("Space"              ),
	TEXT                ("Text"               ),
	VARIABLE_EXPRESSION ("Variable-Expression"),
	;

	private final String name;

	/**
	 * Constructor
	 */
	TagType(final String name)
	{
		this.name = name;
	}

	/**
	 * getName
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * findByName
	 */
	public static TagType findByName(final String name)
	{
		for (TagType t : values())
		{
			if (t.name.equalsIgnoreCase(name))
			{
				return t;
			}
		}

		return null;
	}
}
