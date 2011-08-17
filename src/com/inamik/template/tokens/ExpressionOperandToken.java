/*
 * $Id: ExpressionOperandToken.java,v 1.10 2006-08-21 03:07:12 dave Exp $
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
 * ExpressionOperandToken
 */
public final class ExpressionOperandToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = 6239583841734113087L;
	// Valid operands :
	//	VariableExpression
	//	Number
	//  QString
	//	Expression
	//	FunctionCall
	//	ExpressionList
	//	AttributeList
	//  Boolean
	private Token token = null;

	/**
	 * Constructor w/VariableExpressionToken
	 */
	public ExpressionOperandToken(VariableExpressionToken token)
	{
		this();

		this.token = token;
	}

	/**
	 * Constructor w/NumberToken
	 */
	public ExpressionOperandToken(NumberToken token)
	{
		this();

		this.token = token;
	}

	/**
	 * Constructor w/QuotedStringToken
	 */
	public ExpressionOperandToken(QuotedStringToken token)
	{
		this();

		this.token = token;
	}

	/**
	 * Constructor w/FunctionCallToken
	 */
	public ExpressionOperandToken(FunctionToken token)
	{
		this();

		this.token = token;
	}

	/**
	 * Constructor w/ExpressionToken
	 */
	public ExpressionOperandToken(ExpressionToken token)
	{
		this();

		Token newToken = token;

		// Normalize
		while	(
					(newToken.getType() == TokenType.EXPRESSION)
				&&	( ((ExpressionToken)newToken).isSingleOperand() == true)
				)
		{
			newToken = ((ExpressionToken)newToken).getLeftOperand().getToken();
		}

		this.token = newToken;
	}

	/**
	 * Constructor w/ExpressionListToken
	 */
	public ExpressionOperandToken(ExpressionListToken token)
	{
		this();

		this.token = token;
	}

	/**
	 * Constructor w/AttributeListToken
	 */
	public ExpressionOperandToken(AttributeListToken token)
	{
		this();

		this.token = token;
	}

	/**
	 * Constructor w/BooleanToken
	 */
	public ExpressionOperandToken(BooleanToken token)
	{
		this();

		this.token = token;
	}

	/**
	 * Constructor (Default, Private)
	 */
	private ExpressionOperandToken()
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
		return TokenType.EXPRESSION_OPERAND;
	}

	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer();

		if (token.getType() == TokenType.EXPRESSION)
		{
			ExpressionToken et = (ExpressionToken)token;

			if (et.getLeftOperand() != null && et.getRightOperand() != null)
			{
				result.append("(").append(token.toString()).append(")");
			}
			else
			{
				result.append(token.toString());
			}
		}
		else
		{
			result.append(token.toString());
		}

		return result.toString();
	}
}
