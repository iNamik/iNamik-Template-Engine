/*
 * $Id: FilterToken.java,v 1.5 2006-08-14 22:31:00 dave Exp $
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
package com.inamik.template.tokens;

import java.io.Serializable;

import com.inamik.template.TemplateTagName;

/**
 * FilterToken
 */
public class FilterToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -878166270852892520L;
	private TemplateTagName			    name  = null;
	private ExpressionListToken parms = null;

	/**
	 * Constructor w/name + parms
	 */
	public FilterToken(TemplateTagName name, ExpressionListToken parms)
	{
		this();

		this.name  = name;
		this.parms = parms;
	}

	/**
	 * Constructor (Default, Private)
	 */
	private FilterToken()
	{
		super();
	}

	/**
	 * getName
	 */
	public TemplateTagName getName()
	{
		return name;
	}


	/**
	 * getParms
	 */
	public ExpressionListToken getParms()
	{
		return parms;
	}

	public TokenType getType()
	{
		return TokenType.FILTER;
	}

	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer();

		result.append(name.toString());

		if (parms != null && parms.size() > 0)
		{
			result.append("(").append(parms.toString()).append(")");
		}

		return result.toString();
	}
}
