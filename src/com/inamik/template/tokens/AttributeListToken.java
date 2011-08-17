/*
 * $Id: AttributeListToken.java,v 1.6 2006-08-14 22:31:00 dave Exp $
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
 * AttributeListToken
 */
public class AttributeListToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -1151022709205976634L;
	private transient List<AttributeToken> attributeList = new ArrayList<AttributeToken>();

	/**
	 * Constructor
	 */
	public AttributeListToken()
	{
		super();
	}

	/**
	 * addAttribute
	 */
	public void addAttribute(AttributeToken attribute)
	{
		attributeList.add(attribute);
	}


	/**
	 * size
	 */
	public int size()
	{
		return attributeList.size();
	}


	/**
	 * get
	 */
	public AttributeToken get(int index)
	{
		return attributeList.get(index);
	}

	public TokenType getType()
	{
		return TokenType.ATTRIBUTE_LIST;
	}

	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer();

		for (int iFig = 0; iFig < attributeList.size(); ++iFig)
		{
			if (iFig > 0)
			{
				result.append(", ");
			}

			result.append(attributeList.get(iFig).toString());
		}

		return result.toString();
	}

	/**
	 * writeObject
	 */
	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();

		s.writeInt(attributeList.size());

		for (int i = 0, n = attributeList.size(); i < n; ++i)
		{
			s.writeObject(attributeList.get(i));
		}
	}

	/**
	 * readObject
	 */
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();

		int size = s.readInt();

		attributeList = new ArrayList<AttributeToken>(size);

		for (int i = 0; i < size; ++i)
		{
			attributeList.add( (AttributeToken)s.readObject() );
		}
	}
}
