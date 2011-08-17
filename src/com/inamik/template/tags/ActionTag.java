/*
 * $Id: ActionTag.java,v 1.10 2006-11-21 09:34:42 dave Exp $
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

import com.inamik.template.*;
import com.inamik.template.tokens.*;

/**
 * ActionTag
 */
public class ActionTag extends AbstractTag implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = 2889752716268943228L;
	/* Acceptable Parms Are
	 * ExpressionToken
	 * VariableExpressionToken
	 * AttributeListToken
	 * ExpressionListToken
	 * EmptyToken
	 * IdToken
	 * IdListToken
	 */
	private TemplateTagName fqName = null;
	private Token           parm   = null;
	private boolean         inline = false;

	/**
	 * Constructor w/o parm
	 */
	public ActionTag(TemplateTagName fqName)
	{
		super();

		this.fqName = fqName;
		this.parm   = EmptyToken.getInstance();
	}

	/**
	 * Constructor w/ExpressionToken
	 */
	public ActionTag(TemplateTagName fqName, ExpressionToken parm)
	{
		super();

		this.fqName = fqName;
		this.parm   = parm;
	}

	/**
	 * Constructor w/VariableExpressionToken
	 */
	public ActionTag(TemplateTagName fqName, VariableExpressionToken parm)
	{
		super();

		this.fqName = fqName;
		this.parm   = parm;
	}

	/**
	 * Constructor w/AttributeListToken
	 */
	public ActionTag(TemplateTagName fqName, AttributeListToken parm)
	{
		super();

		this.fqName = fqName;
		this.parm   = parm;
	}

	/**
	 * Constructor w/ExpressionListToken
	 */
	public ActionTag(TemplateTagName fqName, ExpressionListToken parm)
	{
		super();

		this.fqName = fqName;
		this.parm   = parm;
	}

	/**
	 * Constructor w/IdToken
	 */
	public ActionTag(TemplateTagName fqName, IdToken parm)
	{
		super();

		this.fqName = fqName;
		this.parm   = parm;
	}

	/**
	 * Constructor w/IdListToken
	 */
	public ActionTag(TemplateTagName fqName, IdListToken parm)
	{
		super();

		this.fqName = fqName;
		this.parm   = parm;
	}

	/**
	 * getFQName
	 */
	public TemplateTagName getFQName()
	{
		return fqName;
	}

	/**
	 * getParmType
	 */
	public TokenType getParmType()
	{
		return parm.getType();
	}

	/**
	 * getParm
	 */
	public Token getParm()
	{
		return parm;
	}

	/**
	 * isInline
	 */
	public boolean isInline()
	{
		return inline;
	}

	/**
	 * setInline
	 */
	public void setInline(final boolean inline)
	{
		this.inline = inline;
	}

	public TagType getType()
	{
		return TagType.ACTION;
	}

	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer();
		result.append("{")
		      .append(fqName.toString());

		if (parm.getType() != TokenType.EMPTY)
		{
			String parmString = parm.toString();
			if (parmString != null && parmString.length() > 0)
			{
				result.append(" ")
				      .append(parmString);
			}
		}

		result.append("}");

		return result.toString();
	}
}
