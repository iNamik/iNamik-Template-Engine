/*
 * $Id: Boolean.java,v 1.1 2006-08-21 03:07:12 dave Exp $
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
package com.inamik.template.lib.main.functions;

import com.inamik.template.TemplateFunctionException;
import com.inamik.template.TemplateFunctionTag;
import com.inamik.template.TemplateVariable;

/**
 * Boolean - Evaluate a parameter as a boolean.
 * <p>
 * The evaluation is achived via <code>TemplateVariable.asBoolean()</code>.
 * <p>
 * The usefullness of this function is questionable, but I'm supplying it
 * just the same.
 * <p>
 * Parms: Exactly One (1)
 * <p>
 * Created on Aug 19, 2006
 * @author Dave
 *
 * @see TemplateVariable#asBoolean()
 */
public final class Boolean implements TemplateFunctionTag
{

	/**
	 * Constructor
	 */
	public Boolean()
	{
		super();
	}

	public TemplateVariable execute(TemplateVariable[] args) throws TemplateFunctionException
	{
		if (args.length != 1)
		{
			throw new TemplateFunctionException("Function boolean requires exactly 1 parameter");
		}

		return TemplateVariable.valueOf(args[0].asBoolean());
	}
}
