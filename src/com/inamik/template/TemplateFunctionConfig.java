/*
 * $Id: TemplateFunctionConfig.java,v 1.2 2006-08-22 10:39:24 dave Exp $
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
 * TemplateFunctionConfig
 * Created on Jul 10, 2006
 * @author Dave
 */
public final class TemplateFunctionConfig
{
	private final String name;
	private final String clazz;

	/**
	 * Constructor
	 */
	public TemplateFunctionConfig(final String name, final String clazz)
	{
		super();

		this.name  = name;
		this.clazz = clazz;
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
}
