/*
 * $Id: ExpressionTag.java,v 1.2 2006-08-14 22:31:00 dave Exp $
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
 * ExpressionTag
 */
public class ExpressionTag extends AbstractTag implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -851436594100476789L;
	private ExpressionToken expression = null;

	/**
	 * Constructor
	 */
	public ExpressionTag(ExpressionToken expression)
	{
		super();

		this.expression = expression;
	}

	/**
	 * getExpression
	 */
	public ExpressionToken getExpression()
	{
		return expression;
	}

	public TagType getType()
	{
		return TagType.EXPRESSION;
	}

	@Override
	public String toString()
	{
		return "{" + expression.toString() + "}";
	}
}
