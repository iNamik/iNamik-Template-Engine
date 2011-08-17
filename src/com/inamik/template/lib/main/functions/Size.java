/*
 * $Id: Size.java,v 1.3 2006-08-21 08:09:40 dave Exp $
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
 * Size -  Get the size of the specified parameter.
 * <p>
 * If the parameter resolves to a container who's size can be determined
 * (i.e. <code>TemplateVariable.isSizeable() == true</code>), then the size is
 * determined by calling <code>TemplateVariable.size()</code>.
 * <p>
 * if the parameter resolves to a String, then the size is determined by
 * <code>string.length()</code>.
 * <p>
 * I the parameter does not match any of the above rules, then the size is
 * deemed to be zero (0).
 * <p>
 * Parms: Exactly One (1)
 * <p>
 * Created on Jul 22, 2006
 * @author Dave
 *
 * @see TemplateVariable#isSizeable()
 * @see TemplateVariable#size()
 */
public final class Size implements TemplateFunctionTag
{
	/**
	 * Constructor
	 */
	public Size()
	{
		super();
	}

	public TemplateVariable execute(TemplateVariable[] args) throws TemplateFunctionException
	{
		if (args.length != 1)
		{
			throw new TemplateFunctionException("Function size requires exactly 1 parameter");
		}

		TemplateVariable result = null;

		final TemplateVariable arg0 = args[0];

		if (arg0.isSizeable() == true)
		{
			result = TemplateVariable.newInstance(new Integer(arg0.size()));
		}
		else
		if (arg0.isString())
		{
			final String s = arg0.toString();

			result = TemplateVariable.newInstance(new Integer(s.length()));
		}

		return result != null? result : TemplateVariable.ZERO;
	}
}
