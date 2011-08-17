/*
 * $Id: TemplateVariable.java,v 1.6 2006-11-21 09:34:42 dave Exp $
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

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * TemplateVariable - A container for variables within a template context.
 * <p>
 * At construction time, various attributes of the wrapped object are
 * determined, via several instanceof checks, and the results stored and made
 * available so as to help avoid further instanceof checks.
 * <p>
 * Created on Jul 12, 2006
 * @author Dave
 */
public final class TemplateVariable
{
	/**
	 *  A constant representing <code>null</code>.  To facilitate easy
	 *  comparisons, TemplateVariable.newInstnace() is guaranteed to return
	 *  this constant if passed a <code>null</code> value.
	 */
	public static final TemplateVariable NULL  = new TemplateVariable((Object)null);

	/**
	 *  A constant representing <code>Boolean.TRUE</code>.  To facilitate easy
	 *  comparisons, TemplateVariable.newInstnace() is guaranteed to return this constant
	 *  if passed a Boolean of object where <code>booleanValue() == true</code>
	 */
	public static final TemplateVariable TRUE  = new TemplateVariable(Boolean.TRUE);

	/**
	 *  A constant representing <code>Boolean.FALSE</code>.  To facilitate easy
	 *  comparisons, TemplateVariable.newInstnace() is guaranteed to return this constant
	 *  if passed a Boolean of object where <code>booleanValue() == false</code>
	 */
	public static final TemplateVariable FALSE = new TemplateVariable(Boolean.FALSE);

	/**
	 *  A constant representing <code>new Integer(0)</code>.
	 */
	public static final TemplateVariable ZERO  = new TemplateVariable(new Integer(0));

	/**
	 *  A constant representing <code>new Integer(1)</code>.
	 */
	public static final TemplateVariable ONE   = new TemplateVariable(new Integer(1));

//	private static final String STRING_TRUE  = "true";
	private static final String STRING_FALSE = "false";
	private static final String STRING_ZERO  = "0";

	private boolean implementsIterable     = false;
	private boolean implementsCollection   = false;
	private boolean implementsList         = false;
	private boolean implementsMap          = false;
	private boolean implementsRandomAccess = false;

	private boolean isIterator    = false;
	private boolean isEnumeration = false;
	private boolean isArray       = false;
	private boolean isNull        = false;
	private boolean isString      = false;
	private boolean isNumber      = false;
	private boolean isBoolean     = false;
	private boolean isTemplateBodyContent = false;

	private Object object = null;

	/**
	 * Constructor w/Object
	 */
	private TemplateVariable(Object object)
	{
		super();

		this.object = object;

		// null
		if (object == null)
		{
			this.isNull = true;
		}
		else
		// String
		if (object instanceof String)
		{
			this.isString = true;
		}
		else
		// Number
		if (object instanceof Number)
		{
			this.isNumber = true;
		}
		else
		// Map
		if (object instanceof Map)
		{
			this.implementsMap = true;
		}
		else
		// List
		if (object instanceof List)
		{
			this.implementsList       = true;
			this.implementsCollection = true;
			this.implementsIterable   = true;

			if (object instanceof RandomAccess)
			{
				this.implementsRandomAccess = true;
			}
		}
		else
		// Collection
		if (object instanceof Collection)
		{
			this.implementsCollection = true;
			this.implementsIterable   = true;
		}
		else
		// TemplateBodyContent
		if (object instanceof TemplateBodyContent)
		{
			this.isTemplateBodyContent = true;
			this.implementsIterable    = true;
		}
		else
		// Iterable
		if (object instanceof Iterable)
		{
			this.implementsIterable = true;
		}
		else
		// Iterator
		if (object instanceof Iterator)
		{
			this.isIterator = true;
		}
		else
		// object[]
		if (object instanceof Object[])
		{
			this.isArray = true;
		}
		else
		// Enumeration
		if (object instanceof Enumeration)
		{
			this.isEnumeration = true;
		}
		else
		// Boolean
		if (object instanceof Boolean)
		{
			this.isBoolean = true;
		}
	}

	/**
	 * newInstance (Static) - Create a new TemplateVariable.
	 * <p>
	 * There can be only one instance of TemplateVariable that wraps
	 * a <code>null</code> value.  That instance is defined as the constant
	 * <code>TemplateVariable.NULL</code>.  Passing a <code>null</code> value
	 * into this method is guaranteed to return
	 * <code>TemplateVariable.NULL</code>
	 * <p>
	 * Passing a Boolean object into this method is guaranteed to
	 * return one of <code>TemplateVariable.TRUE</code> or
	 * <code>TemplateVariable.FALSE</code>.  If you know that your source
	 * object is a Boolean, consider using the valueOf(boolean) method instead.
	 * <p>
	 * TemplateVariable cannot wrap another TemplateVariable instance.
	 * This method will simply return the passed-in object if it is a
	 * TemplateVariable.
	 *
	 * @param object The Object to be wrapped.
	 * @return A TemplateVariable, wrapping the passed in object.
	 *
	 * @see #valueOf(boolean)
	 */
	public static TemplateVariable newInstance(final Object object)
	{
		if (object == null)
		{
			return NULL;
		}
		else
		if (object instanceof Boolean)
		{
			return valueOf( ((Boolean)object).booleanValue());
		}
		else
		if (object instanceof TemplateVariable)
		{
			return (TemplateVariable)object;
		}
		else
		{
			return new TemplateVariable(object);
		}
	}

	/**
	 * valueOf (Static) w/boolean - Convenience method for wrapping a boolean
	 * in a TemplateVariable.
	 * <p>
	 * This method is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code> b ? TemplateVariable.TRUE : TemplateVariable.FALSE</code>
	 *
	 * @param b - The boolean to be wrapped
	 * @return One of <code>TemplateVariable.TRUE</code> or
	 *         <code>TemplateVariable.FALSE</code>.
	 */
	public static TemplateVariable valueOf(boolean b)
	{
        return (b ? TRUE : FALSE);
    }

	/**
	 * implementsCollection - Does the wrapped object implement
	 * <code>java.util.Collection</code>.
	 *
	 * @return <code>true</code> if the wrapped object implements
	 *         <code>java.util.Collection</code>, <code>false</code> otherwise.
	 *
	 * @see Collection
	 */
	public boolean implementsCollection()
	{
		return implementsCollection;
	}

	/**
	 * implementsList - Does the wrapped object implement
	 * <code>java.util.List</code>.
	 *
	 * @return <code>true</code> if the wrapped object implements
	 *         <code>java.util.List</code>, <code>false</code> otherwise.
	 *
	 * @see List
	 */
	public boolean implementsList()
	{
		return implementsList;
	}

	/**
	 * implementsIterable - Does the wrapped object implements
	 * <code>java.lang.Iteratable</code>.
	 *
	 * @return <code>true</code> if the wrapped object implements
	 *         <code>java.lang.Iterable</code>, <code>false</code> otherwise.
	 *
	 * @see Iterable
	 */
	public boolean implementsIterable()
	{
		return implementsIterable;
	}

	/**
	 * implementsMap - Does the wrapped object implements
	 * <code>java.util.Map</code>.
	 *
	 * @return <code>true</code> if the wrapped object implements
	 *         <code>java.util.Map</code>, <code>false</code> otherwise.
	 *
	 * @see Map
	 */
	public boolean implementsMap()
	{
		return implementsMap;
	}

	/**
	 * implementsRandomeAccess - Does the wrapped object implements
	 * <code>java.util.RandomAccess</code>.
	 *
	 * @return <code>true</code> if the wrapped object implements
	 *         <code>java.util.RandomAccess</code>, <code>false</code>
	 *         otherwise.
	 *
	 * @see RandomAccess
	 */
	public boolean implementsRandomAcces()
	{
		return implementsRandomAccess;
	}

	/**
	 * isArray - Is the wrapped object an array.
	 *
	 * @return <code>true</code> if the wrapped object is an array,
	 *         <code>false</code> otherwise.
	 */
	public boolean isArray()
	{
		return isArray;
	}

	/**
	 * isBoolean - Is the wrapped object of type
	 * <code>java.lang.Boolean</code>.
	 *
	 * @return <code>true</code> if the wrapped object is of type
	 *         <code>java.lang.Boolean</code>, <code>false</code> otherwise.
	 *
	 * @see Boolean
	 */
	public boolean isBoolean()
	{
		return isBoolean;
	}

	/**
	 * isEnumeration - Is the wrapped object of type
	 * <code>java.util.Enumeration</code>.
	 *
	 * @return <code>true</code> if the wrapped object is of type
	 *         <code>java.util.Enumeration</code>, <code>false</code> otherwise.
	 *
	 * @see Enumeration
	 */
	public boolean isEnumeration()
	{
		return isEnumeration;
	}

	/**
	 * isIterator - Is the wrapped object of type
	 * <code>java.util.Iterator</code>.
	 *
	 * @return <code>true</code> if the wrapped object is of type
	 *         <code>java.util.Iterator</code>, <code>false</code> otherwise.
	 *
	 * @see Iterator
	 */
	public boolean isIterator()
	{
		return isIterator;
	}

	/**
	 * isNull - Is the wrapped object <code>null</code>.
	 *
	 * @return <code>true</code> if the wrapped object is
	 *         <code>null</code>, <code>false</code> otherwise.
	 */
	public boolean isNull()
	{
		return isNull;
	}

	/**
	 * isNumber - Is the wrapped object of type
	 * <code>java.lang.Number</code>.
	 *
	 * @return <code>true</code> if the wrapped object is of type
	 *         <code>java.lang.Number</code>, <code>false</code> otherwise.
	 *
	 * @see java.lang.Number
	 */
	public boolean isNumber()
	{
		return isNumber;
	}

	/**
	 * isString - Is the wrapped object of type
	 * <code>java.lang.String</code>.
	 *
	 * @return <code>true</code> if the wrapped object is of type
	 *         <code>java.lang.String</code>, <code>false</code> otherwise.
	 *
	 * @see java.lang.String
	 */
	public boolean isString()
	{
		return isString;
	}

	/**
	 * isSizeable - Is the wrapped object a container who's size can be
	 * determined.
	 *
	 * @return <code>true</code> if the wrapped object is a container who's
	 *         size can be determined, <code>false</code> otherwise.
	 *
	 * @see #isArray()
	 * @see #implementsCollection()
	 * @see #implementsMap()
	 */
	public boolean isSizeable()
	{
		return isArray || implementsCollection || implementsMap;
	}

	/**
	 * isIndexable - Is the wrapped object a container that supports numerical
	 * indexing.
	 * <p>
	 * This is a helper method for determining if it is save/appropriate to
	 * call <code>getIndexedProperty</code>.
	 *
	 * @return <code>true</code> if the wrapped object is a container that
	 *         supports numerical indexing, <code>false</code> otherwise.
	 *
	 * @see #getIndexProperty(int)
	 * @see #isArray
	 * @see #implementsList
	 */
	public boolean isIndexable()
	{
		return isArray || implementsList;
	}

	/**
	 * isMappable - Is the wrapped object a container that supports key/value
	 * mapping.
	 * <p>
	 * This is a helper method for determining if it is safe/appropriate to
	 * call <code>getMappedProperty()</code>.
	 * <p>
	 * NOTE: The relationship isMappable() == implementsMap() is not guaranteed
	 *
	 * @return <code>true</code> if the wrapped object is a container that
	 *         supports key/value mapping, <code>false</code> otherwise.
	 *
	 * @see #getMappedProperty(Object)
	 * @see #implementsMap
	 */
	public boolean isMappable()
	{
		return implementsMap;
	}

	/**
	 * isTemplateBodyContent - Is the wrapped object an instance of
	 * TemplateBodyObject.
	 *
	 * @return <code>true</code> if the wrapped object is an instance of
	 *         TemplateBodyContent, <code>false</code> otherwise.
	 *
	 * @see TemplateBodyContent
	 */
	public boolean isTemplateBodyContent()
	{
		return isTemplateBodyContent;
	}

	/**
	 * asObject - Retrieve the wrapped object as type
	 * <code>java.lang.Object</code>.
	 *
	 * @return The wrapped object as type <code>java.lang.Object</code>
	 *
	 * @see Object
	 */
	public Object asObject()
	{
		return object;
	}

	/**
	 * asObjectArray - Retrieve the wrapped object as type
	 * <code>java.lang.Object[]</code>.
	 * <p>
	 * If the wrapped object is not already of type
	 * <code>java.lang.Object[]</code> (i.e. <code>isArray() == false</code>),
	 * then this method will allocate a new Object array of size one (1) and
	 * store the wrapped value in the new array.
	 *
	 * @return The wrapped object as type <code>java.lang.Object[]</code>
	 *
	 * @see Object
	 */
	public Object[] asObjectArray()
	{
		Object[] result;

		if (isArray)
		{
			result = (Object[])object;
		}
		else
		{
			Object[] array = new Object[1];

			array[0] = object;

			result = array;
		}

		return result;
	}

	/**
	 * asBoolean - Retrieve the wrapped object as type
	 * <code>boolean</code>.
	 * <p>
	 * If the wrapped object is not already of type
	 * <code>java.lang.Boolean</code>, then this method will try to determine
	 * an appropriate value based on the object's type.
	 * <p>
	 * If the wrapped object is <code>null</code>, then the result is
	 * <code>false</code>.
	 * <p>
	 * If the wrapped object is of type <code>java.lang.Number</code>, then
	 * the result is true if <code>number != 0</code>, and false otherwise.
	 * <p>
	 * If the wrapped object is of type <code>java.lang.String</code>, then
	 * the result is true unless one of the following rules matches:
	 * <p>
	 * &nbsp;&nbsp; false if <code>string.length() == 0</code>
	 * <br>
	 * &nbsp;&nbsp; false if <code>string.equalsIgnoreCase("false")</code>
	 * <br>
	 * &nbsp;&nbsp; false if <code>string.equals("0")</code>
	 * <p>
	 * If the wrapped object is a container who's size can be determined
	 * (i.e. <code>this.isSizeable() == true</code>, then the result is
	 * true if <code>this.size() > 0</code> and false otherwise.
	 * <p>
	 * If the wrapped object is not of any of the above types, and is not
	 * <code>null</code>, then the result is true.
	 *
	 * @return The wrapped object as type <code>boolean</code>.
	 */
	public boolean asBoolean()
	{
		boolean result;

		if (isNull)
		{
			result = false;
		}
		else
		if (isBoolean)
		{
			result = ((Boolean)object).booleanValue();
		}
		else
		if (isNumber)
		{
			result = ( ((Number)object).doubleValue() != 0.0);
		}
		else
		if (isString)
		{
			String string = (String)object;

			if	(
					(string.length() == 0)
				||	(STRING_FALSE.equalsIgnoreCase(string))
				||	(STRING_ZERO .equals(string))
				)
			{
				result = false;
			}
			else
			{
				result = true;
			}
		}
		else
		if (isSizeable() == true)
		{
			result = size() > 0 ? true : false;
		}
		else
		{
			result = true;
		}

		return result;
	}

	/**
	 * asDouble - Retrieve the wrapped object as type
	 * <code>double</code>.
	 * <p>
	 * If the wrapped object is not already of type
	 * <code>java.lang.Number</code>, then this method will try to determine
	 * an appropriate value based on the object's type.
	 * <p>
	 * If the wrapped object is <code>null</code>, then the result is
	 * <code>0.0</code>.
	 * <p>
	 * if the wrapped object is of type <code>java.lang.String</code>, then
	 * the result is <code>Double.parseDouble(string)</code>.
	 * <p>
	 * If the wrapped object is of type <code>java.lang.Boolean</code>, then
	 * the result is <code>boolean.booleanValue() ? 1.0 : 0.0</code>.
	 * <p>
	 * If the wrapped object is a container who's size can be determined
	 * (i.e. <code>this.isSizeable() == true</code>), then the result is
	 * <code>tihs.size()</code>.
	 * <p>
	 * If the wrapped object is not of any of the above types, and is not
	 * <code>null</code>, then the result is <code>1.0</code>.
	 *
	 * @return The wrapped object as type <code>double</code>.
	 *
	 * @see Double#parseDouble(java.lang.String)
	 */
	public double asDouble()
	{
		double result;

		if (isNull == true)
		{
			result = 0.0;
		}
		else
		if (isNumber == true)
		{
			result = ((Number)object).doubleValue();
		}
		else
		if (isString == true)
		{
			try
			{
				result = Double.parseDouble((String)object);
			}
			catch (Exception e)
			{
				result = 0.0;
			}
		}
		else
		if (isBoolean == true)
		{
			result = ((Boolean)object).booleanValue() == true ? 1.0 : 0.0;
		}
		else
		if (isSizeable() == true)
		{
			result = (double)size();
		}
		else
		{
			result = 1.0;
		}

		return result;
	}

	/**
	 * size - For wrapped objects that are containers who's size can be
	 * determined (i.e. <code>this.isSizeable() == true</code>), get the size
	 * of the container.
	 *
	 * @return The size of the wrapped object, or 0 if it is not a container
	 *         who's size can be determined.
	 *
	 * @see #isSizeable()
	 */
	public int size()
	{
		if (isArray)
		{
			return ((Object[])object).length;
		}

		if (implementsCollection)
		{
			return ((Collection)object).size();
		}

		if (implementsMap)
		{
			return ((Map)object).size();
		}

		return 0;
	}

	/**
	 * getIndexedProperty - For wrapped objects that are containers that
	 * support numerical indexing (i.e. <code>this.isIndesable() == true</code>),
	 * get the object at the specified index within the container.
	 *
	 * @return A TemplateVariable containing the object at the specified index
	 * of the wrapped container, or <code>TemplateVariable.NULL</code> if the
	 * wrapped object is not a container that supports numerical indexing.
	 *
	 * @see #isIndexable()
	 */
	public TemplateVariable getIndexProperty(final int index)
	{
		if (index < 0)
		{
			return NULL;
		}

		Object o = null;

		if (isArray)
		{
			Object[] array = (Object[])object;

			if (index >= array.length)
			{
				return NULL;
			}

			o = array[index];
		}
		else
		if (implementsList)
		{
			List l  = (List)object;

			if (index >= l.size())
			{
				return NULL;
			}

			o = l.get(index);
		}

		return newInstance(o);
	}

	/**
	 * getMappedProperty - For wrapped objects that are containers that
	 * support key/value mapping (i.e. <code>this.isMappable() == true</code>),
	 * get the object with the specified key within the container.
	 *
	 * @return A TemplateVariable containing the object with the specified key
	 * within the wrapped container, or <code>TemplateVariable.NULL</code> if
	 * the wrapped object is not a container that key/value mapping.
	 *
	 * @see #isMappable()
	 */
	public TemplateVariable getMappedProperty(final Object key)
	{
		if (key == null)
		{
			return NULL;
		}

		if (implementsMap)
		{
			Map m = (Map)object;

			return newInstance(m.get(key));
		}

		return NULL;
	}

	/**
	 * getNamedProperty - For wrapped objects that are Java Beans, get the
	 * named property of the bean.
	 * <p>
	 * This method uses the Apache Commons BeanUtils package.
	 *
	 * @return A TemplateVariable containing the named property of the wrapped
	 *         bean, or <code>TemplateVariable.NULL</code> if the wrapped
	 *         object is not a Java Bean.
	 *
	 * @throws TemplateException If the Apache BeanUtils package throws an
	 *         exception, that exception is wrapped in a TemplateException
	 *         and re-thrown.
	 */
	public TemplateVariable getNamedProperty(final String property) throws TemplateException
	{
		if (property == null)
		{
			return NULL;
		}

		if (isNull)
		{
			return NULL;
		}

		if (isArray)
		{
			return NULL;
		}

		try
		{
			Object o = PropertyUtils.getSimpleProperty(object, property);

			return newInstance(o);
		}
		catch (Exception e)
		{
			throw new TemplateException(e);
		}
	}

	/**
	 * toString - Get the String representation of the wrapped object.
	 * <p>
	 * If the wrapped object is <code>null</code>, then the  result is the
	 * empty String ("").
	 * <p>
	 * If the wrapped object is an array, the result is similar to how
	 * Lists perl toString (i.e. "[item0, item1, ...]").
	 *
	 * @return The String representation of the wrapped object.
	 */
	@Override
	public String toString()
	{
		String result;

		if (object == null)
		{
			result = "";
		}
		else
		if (isArray)
		{
			Object[] array = (Object[])object;

			StringBuffer buffer = new StringBuffer();

			buffer.append("[");

			for (int i = 0, n = array.length; i < n; i++)
			{
				if (i > 0)
				{
					buffer.append(", ");
				}

				buffer.append(array[i]);
			}

			buffer.append("]");

			result = buffer.toString();
		}
		else
		{
			result = object.toString();
		}

		return result;
	}

	/**
	 * asStringOrNull - Get the String representation of the specified
	 * TemplateVariable, or null if the variable is null.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>TemplateVariable.asStringOrDefault(variable, null)</code>
	 *
	 * @return The String representation of the specified
	 *         TemplateVariable, or null if the variable is null.
	 *
	 * @see #asStringOrDefault(TemplateVariable, String)
	 */
	public static String asStringOrNull(final TemplateVariable variable)
	{
		return asStringOrDefault(variable, null);
	}

	/**
	 * asStringOrDefault - Getthe String representation of the specified
	 * TemplateVariable, or, if the variable is null, it returns the specified
	 * default.
	 * <p>
	 * The check for null is determined by <code>variable == null</code> and
	 * does not count the case where <code>variable == TemplateVariable.NULL</code>.
	 * <p>
	 * For non-null variables, the String representation is determined by a
	 * call to TemplateVariable.toString().
	 *
	 * @return The String representation of the specified
	 *         TemplateVariable, or, if the variable is null, it returns the
	 *         specified default.
	 *
	 * @see #toString()
	 */
	public static String asStringOrDefault(final TemplateVariable variable, final String defaultString)
	{
		return variable == null ? defaultString : variable.toString();
	}

	/**
	 * asDoubleOrZero - Get the double representation of the specified
	 * TemplateVariable, or 0.0 if the variable is null.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>TemplateVariable.asDoubleOrDefault(variable, 0.0)</code>
	 *
	 * @return The double representation of the specified
	 *         TemplateVariable, or 0.0 if the variable is null.
	 *
	 * @see #asDoubleOrDefault(TemplateVariable, double)
	 */
	public static double asDoubleOrZero(final TemplateVariable variable)
	{
		return asDoubleOrDefault(variable, 0.0);
	}

	/**
	 * asDoubleOrDefault - Get the double representation of the specified
	 * TemplateVariable, or, if the variable is null, the specified
	 * default.
	 * <p>
	 * The check for null is determined by <code>variable == null</code> and
	 * does not count the case where <code>variable == TemplateVariable.NULL</code>.
	 * <p>
	 * For non-null variables, the double representation is determined by a
	 * call to <code>TemplateVarible.asDouble()</code>
	 *
	 * @return The double representation of the specified
	 *         TemplateVariable, or, if the variable is null, the specified
	 *         default.
	 *
	 * @see #asDouble()
	 */
	public static double asDoubleOrDefault(final TemplateVariable variable, final double defaultDouble)
	{
		return variable == null ? defaultDouble : variable.asDouble();
	}

	/**
	 * asBooleanOrFalse - Get the boolean representation of the specified
	 * TemplateVariable, or false if the variable is null.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp;<code>TemplateVariable.asBooleanOrDefault(variable, false)</code>
	 *
	 * @return the boolean representation of the specified
	 *         TemplateVariable, or false if the variable is null.
	 *
	 * @see #asBooleanOrDefault(TemplateVariable, boolean)
	 */
	public static boolean asBooleanOrFalse(final TemplateVariable variable)
	{
		return asBooleanOrDefault(variable, false);
	}

	/**
	 * asBooleanOrDefault - Get the boolean representation of the specified
	 * TemplateVariable, or, if the variable is null, it returns the specified
	 * default.
	 * <p>
	 * The check for null is determined by <code>variable == null</code> and
	 * does not count the case where <code>variable == TemplateVariable.NULL</code>.
	 * <p>
	 * For non-null variables, the boolean representation is determined by a
	 * call to <code>TemplateVariable.asBoolean()</code>
	 *
	 * @return The boolean representation of the specified
	 *         TemplateVariable, or, if the variable is null, it returns the
	 *         specified default.
	 *
	 * @see #asBoolean()
	 */
	public static boolean asBooleanOrDefault(final TemplateVariable variable, final boolean defaultBoolean)
	{
		return variable == null ? defaultBoolean : variable.asBoolean();
	}

	/**
	 * asObjectOrNull - Get the Object representation of the specified
	 * TemplateVariable, or null if the variable is null.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * &nbsp;&nbsp; <code>TemplateVariable.asObjectOrDefault(variable, null)</code>
	 *
	 * @return The Object representation of the specified
	 *         TemplateVariable, or null if the variable is null.
	 *
	 * @see #asObjectOrDefault(TemplateVariable, Object)
	 */
	public static Object asObjectOrNull(final TemplateVariable variable)
	{
		return asObjectOrDefault(variable, null);
	}

	/**
	 * asObjectOrDefault - Get the Object representation of the specified
	 * TemplateVariable, or, if the variable is null, it returns the specified
	 * default.
	 * <p>
	 * The check for null is determined by <code>variable == null</code> and
	 * does not count the case where <code>variable == TemplateVariable.NULL</code>.
	 * <p>
	 * For non-null variables, the Object representation is determined by a
	 * call to <pre>TemplateVariable.asObject()</pre>
	 *
	 * @return The Object representation of the specified
	 *         TemplateVariable, or, if the variable is null, it returns the
	 *         specified default.
	 *
	 * @see #asObject()
	 */
	public static Object asObjectOrDefault(final TemplateVariable variable, final Object defaultObject)
	{
		return variable == null ? defaultObject : variable.asObject();
	}
}
