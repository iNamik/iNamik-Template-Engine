/*
 * $Id: ExpressionListToken.java,v 1.7 2006-08-14 22:31:00 dave Exp $
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
import java.util.List;
import java.util.ArrayList;

/**
 * ExpressionListToken
 * Created on Jun 14, 2003
 */
public class ExpressionListToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -7072687105285658734L;
	private transient List<ExpressionToken> expressionList = new ArrayList<ExpressionToken>();

	/**
	 * Constructor
	 */
	public ExpressionListToken()
	{
		super();
	}

	/**
	 * addExpression
	 */
	public void addExpression(ExpressionToken expression)
	{
		expressionList.add(expression);
	}

	/**
	 * size
	 */
	public int size()
	{
		return expressionList.size();
	}

	/**
	 * get
	 */
	public ExpressionToken get(int index)
	{
		return expressionList.get(index);
	}

	public TokenType getType()
	{
		return TokenType.EXPRESSION_LIST;
	}

	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer();

		for (int iFig = 0; iFig < expressionList.size(); ++iFig)
		{
			if (iFig > 0)
			{
				result.append(", ");
			}

			result.append(expressionList.get(iFig).toString());
		}

		return result.toString();
	}

	/**
	 * writeObject
	 */
	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();

		s.writeInt(expressionList.size());

		for (int i = 0, n = expressionList.size(); i < n; ++i)
		{
			s.writeObject(expressionList.get(i));
		}
	}

	/**
	 * readObject
	 */
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();

		int size = s.readInt();

		expressionList = new ArrayList<ExpressionToken>(size);

		for (int i = 0; i < size; ++i)
		{
			expressionList.add( (ExpressionToken)s.readObject() );
		}
	}
}
