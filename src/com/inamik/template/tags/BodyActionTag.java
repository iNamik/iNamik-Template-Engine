/*
 * $Id: BodyActionTag.java,v 1.6 2006-08-14 22:31:00 dave Exp $
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
 * BodyActionTag
 */
public class BodyActionTag extends AbstractTag implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = 8928984008011619140L;
	private ActionTag       action;
	private MultipleTagsTag body = null;

	private transient List<BodyActionTag> altBodyList = new ArrayList<BodyActionTag>();

	/**
	 * Constructor w/ActionTag
	 */
	public BodyActionTag(ActionTag action)
	{
		super();

		this.action = action;
	}

	/**
	 * getAction
	 */
	public ActionTag getAction()
	{
		return action;
	}

	/**
	 * setBody
	 */
	public void setBody(final MultipleTagsTag body)
	{
		this.body = body;
	}

	/**
	 * getBody
	 */
	public MultipleTagsTag getBody()
	{
		return body;
	}

	/**
	 * addAltBody
	 */
	public void addAltBodyElement(final BodyActionTag tag)
	{
		altBodyList.add(tag);
	}

	/**
	 * getAltBodySize
	 */
	public int getAltBodySize()
	{
		return altBodyList.size();
	}

	/**
	 * getAltBodyElement
	 */
	public BodyActionTag getAltBodyElement(final int index)
	{
		return altBodyList.get(index);
	}

	public TagType getType()
	{
		return TagType.BODY_ACTION;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(action.toString());
		if (body != null)
		{
			sb.append(body.toString());
		}
		for (BodyActionTag altBody : altBodyList)
		{
			sb.append(altBody.action.toString());
			sb.append(altBody.body.toString());
		}
		sb.append("{/");
		sb.append(action.getFQName());
		sb.append("}");
		return sb.toString();
	}

	/**
	 * writeObject
	 */
	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();

//		s.writeObject(action);
//		s.writeObject(body);

		s.writeInt(altBodyList.size());

		for (int i = 0, n = altBodyList.size(); i < n; ++i)
		{
			s.writeObject(altBodyList.get(i));
		}
	}

	/**
	 * readObject
	 */
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();

//		action = (ActionTag)s.readObject();
//		body   = (MultipleTagsTag)s.readObject();

		int size = s.readInt();

		altBodyList = new ArrayList<BodyActionTag>(size);

		for (int i = 0; i < size; ++i)
		{
			altBodyList.add( (BodyActionTag)s.readObject() );
		}
	}
}
