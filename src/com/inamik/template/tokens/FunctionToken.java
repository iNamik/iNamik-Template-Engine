/*
 * $Id: FunctionToken.java,v 1.4 2006-08-14 22:31:00 dave Exp $
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
 * FunctionToken
 */
public class FunctionToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -5929148574332385670L;
	private TemplateTagName			    name  = null;
	private ExpressionListToken parms = null;

	/**
	 * Constructor w/name + parms
	 */
	public FunctionToken(TemplateTagName name, ExpressionListToken parms)
	{
		this();

		this.name  = name;
		this.parms = parms;
	}

	/**
	 * Constructor (Default, Private)
	 */
	private FunctionToken()
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
		return TokenType.FUNCTION;
	}

	@Override
	public String toString()
	{
		String parmString = "";

		if (parms != null)
		{
			parmString = parms.toString();
		}

		return name.toString() + "(" + parmString + ")";
	}
}
