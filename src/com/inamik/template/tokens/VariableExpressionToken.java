/*
 * $Id: VariableExpressionToken.java,v 1.9 2006-08-14 22:31:00 dave Exp $
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
 * VariableIdToken
 */
public class VariableExpressionToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -2361451534213229817L;
	private transient List<VariableExpressionElementToken> elements = new ArrayList<VariableExpressionElementToken>();
	private FilterListToken filterList = null;

	/**
	 * Constructor (Default)
	 */
	public VariableExpressionToken()
	{
		super();
	}

	/**
	 * addElement
	 */
	public void addElement(VariableExpressionElementToken element)
	{
		elements.add(element);
	}

	/**
	 * size
	 */
	public int size()
	{
		return elements.size();
	}

	/**
	 * get
	 */
	public VariableExpressionElementToken get(int index)
	{
		return elements.get(index);
	}

	/**
	 * setFilterList
	 */
	public void setFilterList(FilterListToken filterList)
	{
		this.filterList = filterList;
	}

	/**
	 * getFilterList
	 */
	public FilterListToken getFilterList()
	{
		return this.filterList;
	}

	public TokenType getType()
	{
		return TokenType.VARIABLE_EXPRESSION;
	}

	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer();

		for (int iFig = 0; iFig < elements.size(); ++iFig)
		{
			VariableExpressionElementToken element = elements.get(iFig);

			if (element.getTokenType() == TokenType.INDEX)
			{
				result.append("[").append(element.toString()).append("]");
			}
			else
			{
				if (iFig > 0)
				{
					result.append(".");
				}
				result.append(element.toString());
			}
		}

		if (filterList != null && filterList.size() > 0)
		{
			for (int i = 0, n = filterList.size(); i < n; ++i)
			{
				FilterToken filter = filterList.get(i);
				result.append("|").append(filter.toString());
			}
		}

		return result.toString();
	}

	/**
	 * writeObject
	 */
	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();

		s.writeInt(elements.size());

		for (int i = 0, n = elements.size(); i < n; ++i)
		{
			s.writeObject(elements.get(i));
		}

//		s.writeObject(filterList);
	}

	/**
	 * readObject
	 */
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();

		int size = s.readInt();

		elements = new ArrayList<VariableExpressionElementToken>(size);

		for (int i = 0; i < size; ++i)
		{
			elements.add( (VariableExpressionElementToken)s.readObject() );
		}

//		filterList = (FilterListToken)s.readObject();
	}
}
