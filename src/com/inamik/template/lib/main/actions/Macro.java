/*
 * $Id: Macro.java,v 1.3 2006-08-21 03:07:12 dave Exp $
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

import java.util.Map;

import com.inamik.template.AbstractTemplateActionTag;
import com.inamik.template.TemplateActionException;
import com.inamik.template.TemplateMacro;
import com.inamik.template.TemplateVariable;

/**
 * Macro - Capture a template block and store it as a Macro.
 * <p>
 * Macros allow you to re-execute the template body multiple times, possibly
 * generating different output each time.
 * <p>
 * Macros are referenced just like variables.  See the example for more
 * information.
 * <p>
 * ParmType: Attributes
 * <br>
 * BlockType: Body
 * <br>
 * BodyContent: Macro
 * <br>
 * Supports Body-Alt: No
 * <p>
 * Attribute: id
 * <br>
 * Required: Yes
 * <br>
 * Description: The macro name to store the captured content under.
 * <p>
 * Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {macro id='macro'}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Hello, {$name}
 * <br>
 * &nbsp;&nbsp; {/macro}
 * <br>
 * &nbsp;&nbsp; {set name='foo'}
 * <br>
 * &nbsp;&nbsp; {$macro}
 * <br>
 * &nbsp;&nbsp; {set name='bar'}
 * <br>
 * &nbsp;&nbsp; {$macro}
 * </code>
 * <p>
 * Created on Aug 6, 2006
 * @author Dave
 */
public final class Macro extends AbstractTemplateActionTag
{
	private static final String ATTRIBUTE_ID = "id";

	String id = null;

	/**
	 * Constructor
	 */
	public Macro()
	{
		super();
	}

	@Override
	public ExecuteResult execute(Map<String, TemplateVariable> attributes) throws TemplateActionException
	{
		id = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_ID));

		if (id == null)
		{
			throw new TemplateActionException("Macro action missing required attribute 'id'");
		}

		return ExecuteResult.OK;
	}

	@Override
	public AfterBodyResult afterBody(TemplateMacro macro)
	{
		getContext().addMacro(id, macro);
		return AfterBodyResult.OK;
	}
}
