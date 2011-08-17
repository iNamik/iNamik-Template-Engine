/*
 * $Id: VariableExpressionTag.java,v 1.5 2006-08-14 22:31:00 dave Exp $
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

import java.io.Serializable;

import com.inamik.template.tokens.*;

/**
 * VariableTag
 * @deprecated
 */
@Deprecated
public class VariableExpressionTag extends AbstractTag implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -2866175676921807074L;
	private VariableExpressionToken varExpression = null;

	/**
	 * Constructor
	 */
	public VariableExpressionTag(VariableExpressionToken varExpression)
	{
		super();

		this.varExpression = varExpression;
	}

	/**
	 * getVarExpression
	 */
	public VariableExpressionToken getVarExpression()
	{
		return varExpression;
	}

	public TagType getType()
	{
		return TagType.VARIABLE_EXPRESSION;
	}

	@Override
	public String toString()
	{
		return new StringBuffer().append("{").append(varExpression.toString()).append("}").toString();
	}
}
