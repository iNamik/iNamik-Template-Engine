/*
 * $Id: Default.java,v 1.3 2006-08-21 08:09:40 dave Exp $
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
package com.inamik.template.lib.main.filters;

import com.inamik.template.TemplateFilterException;
import com.inamik.template.TemplateFilterTag;
import com.inamik.template.TemplateVariable;

/**
 * Default - If the filter target is empty, scan the list of extra parameters,
 * returning the first non-empty parameter, or <code>null</code> if all
 * extra parameters are empty.
 * <p>
 * The test for emptyness differs from the test for booleanness that can be
 * achieved with <code>{if $foo || $bar}</code>.
 * <p>
 * In this filter, an expression is considered empty if it resolves to
 * <code>null</code> or to the empty String ("").
 * <p>
 * ExtraParms: 1 or more
 * <p>
 * Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {$foo |default($bar1, $bar2)}
 * </code>
 * <p>
 * Created on Jul 22, 2006
 * @author Dave
 */
public final class Default implements TemplateFilterTag
{
	/**
	 * Constructor
	 */
	public Default()
	{
		super();
	}

	public TemplateVariable execute(TemplateVariable[] args) throws TemplateFilterException
	{
		assert args.length > 0;

		if (args.length < 2)
		{
			throw new TemplateFilterException("Filter default requires at least 1 extra parameter");
		}

		TemplateVariable result = null;

		for (TemplateVariable arg : args)
		{
			assert result == null;

			if	(
					(arg.isNull() == false)
				&&	(
						(arg.isString() == false)
					||	(arg.toString().length() > 0)
					)
				)
			{
				result = arg;
				break;
			}
		}

		return result != null ? result : TemplateVariable.NULL;
	}
}
