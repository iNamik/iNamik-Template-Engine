/*
 * $Id: FilterListToken.java,v 1.6 2006-08-14 22:31:00 dave Exp $
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
 * FilterListToken
 */
public class FilterListToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -1527699871252025470L;
	private transient List<FilterToken> filterList = new ArrayList<FilterToken>();

	/**
	 * Constructor (Default)
	 */
	public FilterListToken()
	{
		super();
	}

	/**
	 * addExpression
	 */
	public void addFilter(FilterToken filter)
	{
		filterList.add(filter);
	}

	/**
	 * size
	 */
	public int size()
	{
		return filterList.size();
	}

	/**
	 * get
	 */
	public FilterToken get(int index)
	{
		return filterList.get(index);
	}

	public TokenType getType()
	{
		return TokenType.FILTER_LIST;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		for (int iFig = 0; iFig < filterList.size(); ++iFig)
		{
			sb.append("|");
			sb.append(filterList.get(iFig).toString());
		}

		return sb.toString();
	}

	/**
	 * writeObject
	 */
	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();

		s.writeInt(filterList.size());

		for (int i = 0, n = filterList.size(); i < n; ++i)
		{
			s.writeObject(filterList.get(i));
		}
	}

	/**
	 * readObject
	 */
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();

		int size = s.readInt();

		filterList = new ArrayList<FilterToken>(size);

		for (int i = 0; i < size; ++i)
		{
			filterList.add( (FilterToken)s.readObject() );
		}
	}
}
