/*
 * $Id: Print.java,v 1.5 2006-11-21 09:34:43 dave Exp $
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

import java.io.PrintWriter;

import com.inamik.template.AbstractTemplateActionTag;
import com.inamik.template.TemplatePrintBuffer;
import com.inamik.template.TemplateVariable;

/**
 * Print - Send data to the template's output stream.
 * <p>
 * This action may seem useless in light of the variable and string tags,
 * but it can come in handy when trying to output results from functions.
 * <p>
 * ParmType: Expression-List
 * <br>
 * BodyType: Empty
 * <br>
 * BodyContent: Text
 * <p>
 * Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {print lower("Hello "), upper($name) }
 * </code>
 * <p>
 * Created on Jul 22, 2006
 * @author Dave
 */
public final class Print extends AbstractTemplateActionTag
{
	/**
	 * Constructor
	 */
	public Print()
	{
		super();
	}

	@Override
	public ExecuteResult execute(TemplateVariable[] expressionList)
	{
		TemplatePrintBuffer out = getContext().getOut();

		for (TemplateVariable var : expressionList)
		{
			out.print(var.toString());
		}

		return ExecuteResult.OK;
	}
}
