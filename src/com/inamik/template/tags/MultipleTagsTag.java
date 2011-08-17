/*
 * $Id: MultipleTagsTag.java,v 1.3 2006-08-14 22:31:00 dave Exp $
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
package com.inamik.template.tags;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * MultipleTagsTag
 */
public class MultipleTagsTag extends AbstractTag implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -1372016193931396576L;
	private transient List<Tag> elements = new ArrayList<Tag>();

	/*
	 * Default Constructor
	 */
	public MultipleTagsTag()
	{
		super();
	}

	/**
	 * addElement
	 */
	public void addElement(Tag tag)
	{
		// Normalize
		if (tag.getType() == TagType.MULTIPLE_TAGS)
		{
			MultipleTagsTag m = (MultipleTagsTag)tag;

			for (int iFig = 0; iFig < m.size(); iFig++)
			{
				elements.add(m.get(iFig));
			}
		}
		else
		{
			elements.add(tag);
		}
	}

	/**
	 * get
	 */
	public Tag get(int index)
	{
		return elements.get(index);
	}

	/**
	 * size
	 */
	public int size()
	{
		return elements.size();
	}

	/* (non-Javadoc)
	 * @see com.inamik.pscript.tags.Tag#getType()
	 */
	public TagType getType()
	{
		return TagType.MULTIPLE_TAGS;
	}

	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer();

		for (int iFig = 0; iFig < size(); iFig++)
		{
			result.append(get(iFig).toString());
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
	}

	/**
	 * readObject
	 */
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();

		int size = s.readInt();

		elements = new ArrayList<Tag>(size);

		for (int i = 0; i < size; ++i)
		{
			elements.add( (Tag)s.readObject() );
		}
	}
}
