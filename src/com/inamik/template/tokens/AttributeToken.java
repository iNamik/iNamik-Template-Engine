/*
 * $Id: AttributeToken.java,v 1.6 2006-08-14 22:31:00 dave Exp $
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

/**
 * AttributeToken
 */
public class AttributeToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -1910531213687226147L;
	private IdToken         id    = null;
	private ExpressionToken value = null;

	/**
	 * Constructor w/id+parms
	 */
	public AttributeToken(IdToken id, ExpressionToken value)
	{
		this();

		this.id    = id;
		this.value = value;
	}

	/**
	 * Default Constructor (private)
	 */
	private AttributeToken()
	{
		super();
	}

	/**
	 * getId
	 */
	public IdToken getId()
	{
		return id;
	}

	/**
	 * getValue
	 */
	public ExpressionToken getValue()
	{
		return value;
	}

	public TokenType getType()
	{
		return TokenType.ATTRIBUTE;
	}

	@Override
	public String toString()
	{
		return id.toString() + " = " + value.toString();
	}
}
