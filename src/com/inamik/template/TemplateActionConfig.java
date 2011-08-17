/*
 * $Id: TemplateActionConfig.java,v 1.4 2006-08-14 02:15:33 dave Exp $
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

/**
 * TemplateActionConfig
 * Created on Jul 10, 2006
 * @author Dave
 */
public final class TemplateActionConfig
{
	public static enum ParmType
	{
		EMPTY          ("empty"          ),
		ATTRIBUTES     ("attributes"     ),
		EXPRESSION     ("expression"     ),
		EXPRESSION_LIST("expression-list"),
		ID             ("id"             ),
		ID_LIST        ("id-list"        ),
		;
		private final String name;
		ParmType(final String name) { this.name = name; }
		public String getName() { return this.name; }
		public static ParmType findByName(final String name)
		{
			for (ParmType p : values())
			{
				if (p.name.equalsIgnoreCase(name))
				{
					return p;
				}
			}

			return null;
		}
	};

	public static enum BlockType
	{
		EMPTY   ("empty"   ),
		BODY    ("body"    ),
		BODY_ALT("body-alt");
		private final String name;
		BlockType(final String name) { this.name = name; }
		public String getName() { return this.name; }
		public static BlockType findByName(final String name)
		{
			for (BlockType b : values())
			{
				if (b.name.equalsIgnoreCase(name))
				{
					return b;
				}
			}

			return null;
		}
	};

	public static enum BodyContent
	{
		EMPTY   ("empty"   ),
		TEMPLATE("template"),
		TEXT    ("text"    ),
		MACRO   ("macro"   ),
		;
		private final String name;
		BodyContent(final String name) { this.name = name; }
		public String getName() { return this.name; }
		public static BodyContent findByName(final String name)
		{
			for (BodyContent b : values())
			{
				if (b.name.equalsIgnoreCase(name))
				{
					return b;
				}
			}

			return null;
		}
	};

	private final String      name;
	private final String      clazz;
	private final ParmType    parmType;
	private final BlockType   blockType;
	private final BodyContent bodyContent;

	/**
	 * Constructor
	 */
	public TemplateActionConfig
	(
		final String      name,
		final String      clazz,
		final ParmType    parmType,
		final BlockType   blockType,
		final BodyContent bodyContent
	)
	{
		super();

		this.name        = name;
		this.clazz       = clazz;
		this.parmType    = parmType;
		this.blockType   = blockType;
		this.bodyContent = bodyContent;
	}

	/**
	 * getBlockType
	 * @return Returns the blockType.
	 */
	public BlockType getBlockType()
	{
		return this.blockType;
	}

	/**
	 * getBodyContent
	 * @return Returns the bodyContent.
	 */
	public BodyContent getBodyContent()
	{
		return this.bodyContent;
	}

	/**
	 * getClazz
	 * @return Returns the clazz.
	 */
	public String getClazz()
	{
		return this.clazz;
	}

	/**
	 * getName
	 * @return Returns the name.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * getParmType
	 * @return Returns the parmType.
	 */
	public ParmType getParmType()
	{
		return this.parmType;
	}
}
