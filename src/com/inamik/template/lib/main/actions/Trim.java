/*
 * $Id: Trim.java,v 1.1 2008-02-22 03:02:06 dave Exp $
 *
 * iNamik Template Engine
 * Copyright (C) 2008 David Farrell (davidpfarrell@yahoo.com)
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

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import com.inamik.template.AbstractTemplateActionTag;
import com.inamik.template.TemplateActionException;
import com.inamik.template.TemplateBodyContent;
import com.inamik.template.TemplateVariable;
import com.inamik.template.TemplateActionTag.ExecuteResult;
import com.inamik.template.TemplateBodyContent.Line;

/**
 * Trim - Capture a template block, trim it, and either print it or
 *        store it as a variable.
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
 * &nbsp;&nbsp; {trim}{$trimmable_text}{/trim}
 * </code>
 * <p>
 * Created on Feb 17, 2008
 * @author Dave
 */
public class Trim extends AbstractTemplateActionTag
{
	private static final String ATTRIBUTE_ID = "id";

	private String id = null;

	/**
	 * Constructor
	 */
	public Trim()
	{
		super();
	}

	@Override
	public ExecuteResult execute(Map<String, TemplateVariable> attributes) throws TemplateActionException
	{
		id = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_ID));

		return ExecuteResult.OK;
	}

	@Override
	public AfterBodyResult afterBody(TemplateBodyContent bodyContent)
	{
		if (bodyContent.empty() == false)
		{
			final String stringContent = bodyContent.toString().trim();

			final ArrayList<Line> newContent = new ArrayList<Line>();

			final StringTokenizer tokenizer = new StringTokenizer(stringContent, "\n\r");

			String string = null;

			while (tokenizer.hasMoreTokens() == true)
			{
				if (string != null)
				{
					newContent.add(new Line(string, true));
				}

				string = tokenizer.nextToken().trim();
			}

			if (string != null)
			{
				newContent.add(new Line(string, false));
			}

			TemplateBodyContent newBodyContent = new TemplateBodyContent(newContent);

			if (id != null)
			{
				getContext().addVariable(id, TemplateVariable.newInstance(newBodyContent));
			}
			else
			{
				getContext().getOut().print(newBodyContent);
			}
		}

		return AfterBodyResult.OK;
	}
}
