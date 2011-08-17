/*
 * $Id: IndexToken.java,v 1.6 2006-08-14 22:31:00 dave Exp $
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
 * IndexToken
 */
public class IndexToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -3084973926935137358L;
	// Acceptable token types :
	//	TextToken
	//	VariableExpressionToken
	//	QuotedStringToken
	private Token token = null;

//	/**
//	 * Constructor w/TextToken
//	 */
//	public IndexToken(TextToken token)
//	{
//		this();
//
//		this.token = token;
//	}
//
//	/**
//	 * Constructor w/VariableExpressionToken
//	 */
//	public IndexToken(VariableExpressionToken token)
//	{
//		this();
//
//		this.token = token;
//	}
//
//	/**
//	 * Constructor w/QuotedStringToken
//	 */
//	public IndexToken(QuotedStringToken token)
//	{
//		this();
//
//		this.token = token;
//	}

	/**
	 * Constructor w/ExpressionToken
	 */
	public IndexToken(ExpressionToken token)
	{
		this();

		this.token = token;
	}

	/**
	 * Constructor (Default, Private)
	 */
	private IndexToken()
	{
		super();
	}

	/**
	 * getTokenType
	 */
	public TokenType getTokenType()
	{
		return token.getType();
	}

	/**
	 * getToken
	 */
	public Token getToken()
	{
		return token;
	}

	public TokenType getType()
	{
		return TokenType.INDEX;
	}

	@Override
	public String toString()
	{
		return token.toString();
	}
}
