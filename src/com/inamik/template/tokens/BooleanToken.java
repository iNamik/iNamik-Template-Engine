/*
 * $Id: BooleanToken.java,v 1.1 2006-08-21 03:07:12 dave Exp $
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

/**
 * BooleanToken
 * Created on Aug 19, 2006
 * @author Dave
 */
public final class BooleanToken extends AbstractToken
{
	/** serialVersionUID */
	private static final long serialVersionUID = -2558807722673668186L;
	private final boolean value;

	/**
	 * Constructor w/boolean
	 */
	public BooleanToken(final boolean value)
	{
		super();

		this.value = value;
	}

	/**
	 * getValue
	 */
	public boolean getValue()
	{
		return value;
	}

	public TokenType getType()
	{
		return TokenType.BOOLEAN;
	}

	@Override
	public String toString()
	{
		return value ? "true" : "false";
	}

}
