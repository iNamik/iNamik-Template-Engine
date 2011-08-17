/*
 * $Id: TokenizedTemplate.java,v 1.2 2006-08-14 22:31:00 dave Exp $
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
package com.inamik.template;

import com.inamik.template.tags.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * TokenizedTemplate
 */
public class TokenizedTemplate implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = 7735606570562439556L;
	private transient List<Tag> tags = new ArrayList<Tag>();

	public TokenizedTemplate()
	{
		super();
	}

	public TokenizedTemplate(List<Tag> tags)
	{
		super();

		this.tags.addAll(tags);
	}

	public void add(Tag tag)
	{
		if (tag != null)
		{
			tags.add(tag);
		}
		else
		{
			// Error
			System.err.println("Error : Null tag passed to TokenizedTemplate.add()");
		}
	}


	public int size()
	{
		return tags.size();
	}


	public Tag get(int index)
	{
		return tags.get(index);
	}


	@Override
	public String toString()
	{
		String value = "";

		for (int iFig = 0; iFig < tags.size(); ++iFig)
		{
			Tag tag = tags.get(iFig);

			value += tag.toString();
		}

		return value;
	}

	/**
	 * writeObject
	 */
	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();

		s.writeInt(tags.size());

		for (int i = 0, n = tags.size(); i < n; ++i)
		{
			s.writeObject(tags.get(i));
		}
	}

	/**
	 * readObject
	 */
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();

		int size = s.readInt();

		tags = new ArrayList<Tag>(size);

		for (int i = 0; i < size; ++i)
		{
			tags.add( (Tag)s.readObject() );
		}
	}
}
