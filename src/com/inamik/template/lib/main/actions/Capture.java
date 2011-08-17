/*
 * $Id: Capture.java,v 1.5 2006-11-21 09:34:43 dave Exp $
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
import com.inamik.template.TemplateBodyContent;
import com.inamik.template.TemplateVariable;
import com.inamik.template.TemplateActionTag.ExecuteResult;

/**
 * Capture - Capture a template block and store it as a variable.
 * <p>
 * ParmType: Attributes
 * <br>
 * BlockType: Body
 * <br>
 * BodyContent: Template
 * <br>
 * Supports Body-Alt: No
 * <p>
 * Attribute: id
 * <br>
 * Required: Yes
 * <br>
 * Description: The variable name to store the captured content under.
 * <p>
 * Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {capture id='capture'}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Hello, {$name}
 * <br>
 * &nbsp;&nbsp; {/capture}
 * <br>
 * &nbsp;&nbsp; {$capture}
 * </code>
 * <p>
 * Created on Jul 22, 2006
 * @author Dave
 */
public class Capture extends AbstractTemplateActionTag
{
	private static final String ATTRIBUTE_ID = "id";

	private String id = null;

	/**
	 * Constructor
	 */
	public Capture()
	{
		super();
	}

	@Override
	public ExecuteResult execute(Map<String, TemplateVariable> attributes) throws TemplateActionException
	{
		id = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_ID));

		if (id == null)
		{
			throw new TemplateActionException("Capture action missing required attribute 'id'");
		}

		return ExecuteResult.OK;
	}

	@Override
	public AfterBodyResult afterBody(TemplateBodyContent bodyContent)
	{
		assert id != null;

		if (bodyContent.empty() == false)
		{
			getContext().addVariable(id, TemplateVariable.newInstance(bodyContent));
		}

		return AfterBodyResult.OK;
	}
}
