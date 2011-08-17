/*
 * $Id: Lower.java,v 1.3 2006-08-21 08:09:40 dave Exp $
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
 * Lower - Transform the filter target into lowercase.
 * <p>
 * This filter resolves the filter target to a String, then executes
 * <code>string.toLowerCase()</code>, returning the resulting String.
 * <p>
 * ExtraParms: none
 * <p>
 * <code>
 * &nbsp;&nbsp; {$foo |lower}
 * </code>
 * <p>
 * Created on Jul 22, 2006
 * @author Dave
 *
 * @see java.lang.String#toLowerCase()
 */
public final class Lower implements TemplateFilterTag
{
	/**
	 * Constructor
	 */
	public Lower()
	{
		super();
	}

	public TemplateVariable execute(TemplateVariable[] args) throws TemplateFilterException
	{
		assert args.length > 0;

		if (args.length != 1)
		{
			throw new TemplateFilterException("Filter lower does not accept extra parameters");
		}

		return TemplateVariable.newInstance(args[0].toString().toLowerCase());
	}
}
