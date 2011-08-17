/*
 * $Id: VariableExpressionElementToken.java,v 1.6 2006-08-14 22:31:00 dave Exp $
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
 * VariableIdElementToken
 */
public class VariableExpressionElementToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -1265711315095123393L;
	// Valid token types :
	// VARIABLE_ID
	// ID
	// INDEX
	private Token token = null;

	/**
	 * Constructor w/ Id
	 */
	public VariableExpressionElementToken(IdToken token)
	{
		this();
		this.token = token;
	}

	/**
	 * Constructor w/ VariableId
	 */
	public VariableExpressionElementToken(VariableIdToken token)
	{
		this();
		this.token = token;
	}

	/**
	 * Constructor w/ Index
	 */
	public VariableExpressionElementToken(IndexToken token)
	{
		this();
		this.token = token;
	}


	/**
	 * Constructor (Default, Private)
	 */
	private VariableExpressionElementToken()
	{
		super();
	}

	/**
	 * getToken
	 */
	public Token getToken()
	{
		return token;
	}

	/**
	 * getTokenType
	 */
	public TokenType getTokenType()
	{
		return token.getType();
	}

	public TokenType getType()
	{
		return TokenType.VARIABLE_EXPRESSION_ELEMENT;
	}

	@Override
	public String toString()
	{
		String result;

		if (token != null)
		{
			result = token.toString();
		}
		else
		{
			result = "<undefined>";
		}

		return result;
	}
}
