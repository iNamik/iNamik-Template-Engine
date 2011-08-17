/*
 * $Id: Include.java,v 1.9 2006-11-21 09:34:43 dave Exp $
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.inamik.template.AbstractTemplateActionTag;
import com.inamik.template.TemplateActionException;
import com.inamik.template.TemplateBodyContent;
import com.inamik.template.TemplateException;
import com.inamik.template.TemplateVariable;

/**
 * Include - Include a template, either from a file or a resource on the
 * classpath.
 * <p>
 * The included template can optionally be stored in a template variable
 * instead of being sent to the template's output stream.
 * <p>
 * Any attributes provided other than the ones mentioned below will be used
 * to set variables for the included template.  See the examples for more
 * information.
 * <p>
 * ParmType: Attributes
 * <br>
 * BodyType: Empty
 * <br>
 * BodyContent: Empty
 * <p>
 * Attribute: file
 * <br>
 * Required: One of 'file' or 'resource'
 * <br>
 * Description: When including a template from a file, the attribute specifies
 *              the file to include.
 * <p>
 * Attribute: resource
 * <br>
 * Required: One of 'resource' or 'file'
 * <br>
 * Description: When including a template from the classpath, the attribute
 *              specifies the resource to include.
 * <p>
 * Attribute: id
 * <br>
 * Required: No
 * <br>
 * Description: If present, stores the processed template in the specified
 *              variable.
 * <p>
 * Attribute: *
 * <br>
 * Required: No
 * <br>
 * Description: Any attributes provided other than the ones named above will be
 *             used to set variables for the included template.
 * <p>
 * Examples:
 * <p>
 * &nbsp; File Example With Extra Variables:
 * <p>
 * <code>
 * &nbsp;&nbsp; {include file="include.tpl" foo="foo" bar="bar"}
 * </code>
 * <p>
 * &nbsp; Resource Example With 'id' Attribute:
 * <p>
 * <code>
 * &nbsp;&nbsp; {include file="include.tpl" id=include}
 * <br>
 * &nbsp;&nbsp; {$include}
 * </code>
 * <p>
 * Created on Jul 28, 2006
 * @author Dave
 */
public class Include extends AbstractTemplateActionTag
{
	private static final String ATTRIBUTE_FILE     = "file";
	private static final String ATTRIBUTE_RESOURCE = "resource";
	private static final String ATTRIBUTE_ID       = "id";

	/**
	 * Constructor
	 */
	public Include()
	{
		super();
	}

	@Override
	public ExecuteResult execute(Map<String, TemplateVariable> attributes) throws TemplateActionException
	{
		String file     = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_FILE    ));
		String resource = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_RESOURCE));
		String id       = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_ID      ));

		attributes.remove(ATTRIBUTE_FILE);
		attributes.remove(ATTRIBUTE_RESOURCE);
		attributes.remove(ATTRIBUTE_ID);

		if (file == null && resource == null)
		{
			throw new TemplateActionException("Action include requires one of 'file' or 'resource' attributes");
		}

		if (file != null && resource != null)
		{
			throw new TemplateActionException("Action include requires only one of 'file' or 'resource' attributes");
		}

		final boolean capture = (id != null);

		TemplateBodyContent result;

		try
		{
			getContext().addVariables(attributes);

			if (file != null)
			{
				assert resource == null;
				result = getContext().includeTemplateFromFile(file, capture);
			}
			else
			{
				assert resource != null;
				result = getContext().includeTemplateFromResource(resource, capture);
			}
		}
		catch (FileNotFoundException e)
		{
			throw new TemplateActionException(e);
		}
		catch (IOException e)
		{
			throw new TemplateActionException(e);
		}
		catch (TemplateException e)
		{
			throw new TemplateActionException(e);
		}

		if (capture)
		{
			assert id != null;
			assert result != null;
			getContext().addVariable(id, TemplateVariable.newInstance(result));
		}

		return ExecuteResult.OK;
	}

}
