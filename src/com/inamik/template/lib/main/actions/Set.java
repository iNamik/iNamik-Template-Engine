/*
 * $Id: Set.java,v 1.4 2006-08-21 03:07:12 dave Exp $
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
import com.inamik.template.TemplateVariable;

/**
 * Set - Set template variables.
 * <p>
 * ParmType: Attributes
 * <br>
 * BodyType: Empty
 * <br>
 * BodyContent: Empty
 * <p>
 * Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {set foo="foo", bar="bar"}
 * <br>
 * &nbsp;&nbsp; Hello, {$foo}
 * <br>
 * &nbsp;&nbsp; Goodbye, {$bar}
 * </code>
 * <p>
 * Created on Jul 21, 2006
 * @author Dave
 */
public final class Set extends AbstractTemplateActionTag
{

	/**
	 * Constructor
	 */
	public Set()
	{
		super();
	}

	@Override
	public ExecuteResult execute(Map<String, TemplateVariable> attributes)
	{
		getContext().addVariables(attributes);
		return ExecuteResult.OK;
	}
}
