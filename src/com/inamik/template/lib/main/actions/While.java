/*
 * $Id: While.java,v 1.5 2008-02-22 03:02:06 dave Exp $
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

import com.inamik.template.AbstractTemplateActionTag;
import com.inamik.template.TemplateBodyContent;
import com.inamik.template.TemplateVariable;

/**
 * While - Allows you to continuously include a template body while a condition
 * is met.
 * <p>
 * Takes a single expression as a parameter and continuously includes the
 * template body while the expression resolves to <code>true</code>.
 * <p>
 * ParmType: Expression
 * <br>
 * BodyTyp: Body
 * <br>
 * BodyContent: Template
 * <p>
 * Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {set counter = 0}
 * <br>
 * &nbsp;&nbsp; {while $counter < 10}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; {$counter}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; {set counter = $counter + 1}
 * <br>
 * &nbsp;&nbsp; {/while}
 * </code>
 * <p>
 * Created on Jul 22, 2006
 * @author Dave
 */
public class While extends AbstractTemplateActionTag
{
	boolean repeat = true;

	/**
	 * Constructor
	 */
	public While()
	{
		super();
	}

	@Override
	public ExecuteResult execute(TemplateVariable expression)
	{
		if (expression.asBoolean() == true)
		{
			return ExecuteResult.OK;
		}

		repeat = false;
		return ExecuteResult.SKIP_BODY;
	}

	@Override
	public AfterBodyResult afterBody(TemplateBodyContent bodyContent)
	{
		getContext().getOut().print(bodyContent);

		if (repeat == true)
		{
			return AfterBodyResult.REPEAT_ACTION;
		}
		return AfterBodyResult.OK;
	}
}
