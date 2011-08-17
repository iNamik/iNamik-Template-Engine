/*
 * $Id: ExpressionOperatorToken.java,v 1.6 2006-08-14 22:31:00 dave Exp $
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * ExpressionOperatorToken
 */
public class ExpressionOperatorToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = 8296628359484426810L;

	public static enum Operator
	{
		PLUS                ("+"),
		MINUS               ("-"),
		MULTIPLY            ("*"),
		DIVIDE              ("/"),
		MODULO              ("%"),
		EQUALS_EQUALS       ("=="),
		NOT_EQUALS          ("!="),
		GREATER_THAN        (">"),
		LESS_THAN           ("<"),
		GREATER_THAN_EQUALS (">="),
		LESS_THAN_EQUALS    ("<="),
		LOGICAL_NOT         ("!"),
		LOGICAL_AND         ("&&"),
		LOGICAL_OR          ("||"),
//		EQUALS              ("="),
//		PLUS_EQUALS         ("+="),
//		MINUS_EQUALS        ("-="),
//		MULTIPLY_EQUALS     ("*="),
//		DIVIDE_EQUALS       ("/="),
//		MODULO_EQUALS       ("%="),
//		BITWISE_AND_EQUALS  ("&="),
//		BITWISE_OR_EQUALS   ("|="),
//		BITWISE_XOR_EQUALS  ("^="),
//		PLUS_PLUS           ("++"),
//		MINUS_MINUS         ("--"),
//		BITWISE_NOT         ("~"),
//		BITWISE_AND         ("&"),
//		BITWISE_OR          ("|"),
//		BITWISE_XOR         ("^"),
		;
		private final String symbol;
		Operator(final String symbol) { this.symbol = symbol; }
		public String getSymbol() { return this.symbol; }
		public static Operator findBySymbol(final String symbol)
		{
			for (Operator o : values())
			{
				if (o.symbol.equalsIgnoreCase(symbol))
				{
					return o;
				}
			}

			return null;
		}
	}

	private transient Operator operator;

	/**
	 * Constructor w/Operator
	 */
	public ExpressionOperatorToken(Operator operator)
	{
		super();

		this.operator = operator;
	}

	/**
	 * getOperator
	 */
	public Operator getOperator()
	{
		return this.operator;
	}

	public TokenType getType()
	{
		return TokenType.EXPRESSION_OPERATOR;
	}

	@Override
	public String toString()
	{
		return operator.getSymbol();
	}

	/**
	 * writeObject
	 */
	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();

		s.writeObject(operator.name());
	}

	/**
	 * readObject
	 */
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();

		String name = (String)s.readObject();

		operator = Operator.valueOf(name);
	}
}
