/*
 * $Id: Foreach.java,v 1.6 2006-11-21 09:34:43 dave Exp $
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
package com.inamik.template.lib.main.actions;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Enumeration;

import com.inamik.template.AbstractTemplateActionTag;
import com.inamik.template.TemplateBodyContent;
import com.inamik.template.TemplateVariable;

/**
 * Foreach - Iterate over a container of items.
 * <p>
 * When iterating over a list, you can access each item of the list under
 * the variable defined in the 'id' attribute.
 * <p>
 * When iterating over a map, each element is a java.util.Map.Enty, providing
 * you access to the the key and the value.  You may also provide an optional
 * 'key' attribute, which would then store the key for each iteration and store
 * the value under the 'id' field for direct access.  See the examples for more
 * information.
 * <p>
 * Also accepts an optional 'loop' attribute that gives you access to
 * information about each iteration.
 * <p>
 * ParmType: Attributes
 * <br>
 * BodyType: Body
 * <br>
 * BodyContent: Template
 * <p>
 * Attribute: id
 * <br>
 * Required: Yes
 * <br>
 * Description: The name of the template variable to assign the element to
 *              during each iteration.
 * <p>
 * Attribute: in
 * <br>
 * Required: Yes
 * <br>
 * Description: The container to iterate over.
 * <p>
 * Attribute: key
 * <br>
 * Required: No
 * <br>
 * Description: Optionally used when iterating over a map.  If provided,
 *              stores the key of the map entry in this field and stores
 *              the value in the 'id' field.
 * <p>
 * Attribute: loop
 * <br>
 * Required: no
 * <br>
 * Description: If provided, stores information about each iteration. The
 *              following properties are available:
 * <p>
 * &nbsp;&nbsp; iteration : The iteraction count, starting from 0
 * <br>
 * &nbsp;&nbsp; size : The number of elements to be iterated over,
 *              or 0 if unknown.
 * <br>
 * &nbsp;&nbsp; first : <code>true</code> if this iteration is the first
 *              iteration, <code>false</code> otherwise.
 * <br>
 * &nbsp;&nbsp; last : <code>true</code> if this iteration is the last
 *              iteration, <code>false</code> otherwise.
 * <p>
 * Examples:
 * <p>
 * &nbsp; List Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {foreach id=item in=$list}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; item = {$item}
 * <br>
 * &nbsp;&nbsp; {/foreach}
 * </code>
 * <p>
 * &nbsp; Map Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {foreach id=item in=$map}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; key = {$item.key}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; value = {$item.value}
 * <br>
 * &nbsp;&nbsp; {/foreach}
 * </code>
 * <p>
 * &nbsp; Map Example With Key:
 * <p>
 * <code>
 * &nbsp;&nbsp; {foreach id=item in=$map key=key}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; key = {$key}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; value = {$item}
 * <br>
 * &nbsp;&nbsp; {/foreach}
 * </code>
 * <p>
 * &nbsp; List Example With Loop:
 * <p>
 * <code>
 * &nbsp;&nbsp; {foreach id=item in=$list loop=loop}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; iteration = {$loop.iteration}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; size  = {$loop.size}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; first = {$loop.first}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; last  = {$loop.last}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; item  = {$item}
 * <br>
 * &nbsp;&nbsp; {/foreach}
 * </code>
 * <p>
 * Created on Jul 21, 2006
 * @author Dave
 *
 * @see java.util.Map.Entry
 */
public final class Foreach extends AbstractTemplateActionTag
{
	private static final String ID   = "id";
	private static final String IN   = "in";
	private static final String KEY  = "key";
	private static final String LOOP = "loop";

	private TemplateVariable in   = null;
	private String           id   = null;
	private String           key  = null;
	private String           loop = null;

	private Iterator iterator = null;

	private ForeachId        foreachId  = null;
	private TemplateVariable vForeachId = null;

	/**
	 * Constructor
	 */
	public Foreach()
	{
		super();
	}

	@Override
	public ExecuteResult execute(Map<String, TemplateVariable> attributes)
	{
		// Get parms

		in = attributes.get(IN);

		TemplateVariable temp;

		temp = attributes.get(ID);

		if (temp != null)
		{
			id = temp.toString();
		}

		temp = attributes.get(KEY);

		if (temp != null)
		{
			key = temp.toString();
		}

		temp = attributes.get(LOOP);

		if (temp != null)
		{
			loop = temp.toString();
		}

		if (in == null || id == null)
		{
			return ExecuteResult.SKIP_ALL;
		}

		// Get iterator

		int size = 0;

		if (in.implementsList())
		{
			List list = (List)in.asObject();
			iterator  = list.iterator();
			size      = list.size();
		}
		else
		if (in.implementsMap())
		{
			Map map = (Map)in.asObject();
			iterator = map.entrySet().iterator();
			size     = map.size();
		}
		else
		if (in.implementsIterable())
		{
			iterator = ((Iterable)in.asObject()).iterator();
		}
		else
		if (in.isArray())
		{
			Object[] array = in.asObjectArray();
			iterator = Arrays.asList(array).iterator();
			size     = array.length;
		}
		else
		if (in.isEnumeration() == true)
		{
			iterator = new EnumerationIterator((Enumeration)in.asObject());
		}
		else
		if (in.isIterator())
		{
			iterator = (Iterator)in.asObject();
		}

		// If we didnt find antying to iterator over
		// or if we did, but there are no items left to iterate over
		if (iterator == null || iterator.hasNext() == false)
		{
			return ExecuteResult.SKIP_ALL;
		}

		// Do we want to be informed of the loop info?
		if (loop != null)
		{
			foreachId      = new ForeachId();
			foreachId.size = size;
			vForeachId     = TemplateVariable.newInstance(foreachId);
		}

		updateItem();

		updateForeachId();

		return ExecuteResult.OK;
	}

	@Override
	public AfterBodyResult afterBody(TemplateBodyContent bodyContent)
	{
		if (iterator == null)
		{
			return AfterBodyResult.OK;
		}

		getContext().getOut().print(bodyContent);

		if (iterator.hasNext() == false)
		{
			return AfterBodyResult.OK;
		}

		updateItem();
		updateForeachId();

		return AfterBodyResult.REPEAT_BODY;
	}

	/**
	 * updateItem
	 */
	private void updateItem()
	{
		if (iterator.hasNext())
		{
			Object o = iterator.next();

			if (in.implementsMap() && key != null)
			{
				Map.Entry mapEntry = (Map.Entry)o;
				o = mapEntry.getValue();

				TemplateVariable vKey = TemplateVariable.newInstance(mapEntry.getKey());
				getContext().addVariable(key, vKey);
			}

			TemplateVariable vItem = TemplateVariable.newInstance(o);
			getContext().addVariable(id, vItem);

			if (foreachId != null)
			{
				foreachId.iteration++;
			}
		}
	}

	/**
	 * updateForeachId
	 * NOTE: Should be called AFTER updateItem to sure that foreachId.last is
	 * updated appropriately.
	 */
	private void updateForeachId()
	{
		if (loop != null && vForeachId != null)
		{
			foreachId.first = foreachId.iteration == 1;
			foreachId.last  = iterator.hasNext() == false;
			getContext().addVariable(loop, vForeachId);
		}
	}

	/** ForeachId */
	public static final class ForeachId
	{
		protected int     iteration = 0;
		protected boolean first     = true;
		protected boolean last      = false;
		protected int     size      = 0;

		public int     getIteration() { return iteration; }
		public boolean isFirst     () { return first;     }
		public boolean isLast      () { return last;      }
		public int     getSize     () { return size;      }
	}


	/** EnumerationIterator */
	private static final class EnumerationIterator implements Iterator
	{
		private Enumeration from;

		public EnumerationIterator(final Enumeration from)
		{
			super();

			this.from = from;
		}

		public boolean hasNext() { return from.hasMoreElements(); }
		public Object  next   () { return from.nextElement(); }
		public void    remove () {}
	}

}
