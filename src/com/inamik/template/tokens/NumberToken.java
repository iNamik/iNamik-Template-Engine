/*
 * $Id: NumberToken.java,v 1.5 2006-08-14 22:31:00 dave Exp $
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
 * NumberToken
 */
public class NumberToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -7941632294704204664L;
	private String n1 = null;
	private String n2 = null;

	/**
	 * Constructor w/single number (integer)
	 */
	public NumberToken(String n1)
	{
		super();

		this.n1 = n1;
	}

	/**
	 * Constructor w/two integers (float)
	 */
	public NumberToken(String n1, String n2)
	{
		super();

		this.n1 = n1;
		this.n2 = n2;
	}

	/**
	 * getN1
	 */
	public String getN1()
	{
		return n1;
	}

	/**
	 * getN2
	 */
	public String getN2()
	{
		return n2;
	}

	public TokenType getType()
	{
		return TokenType.NUMBER;
	}

	@Override
	public String toString()
	{
		return n1 + (n2 != null ? "." + n2 : "");
	}
}
