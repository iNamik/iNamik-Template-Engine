/*
 * $Id: InTrim.java,v 1.1 2008-02-22 03:02:06 dave Exp $
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
package com.inamik.template.lib.main.filters;

import java.util.StringTokenizer;

import com.inamik.template.TemplateFilterException;
import com.inamik.template.TemplateFilterTag;
import com.inamik.template.TemplateVariable;

/**
 * InTrim - Remove leading and trailing whitespace, and convert any inner
 *          occurrences of one or more whitespace characters into a single
 *          space character
 * <p>
 * ExtraParms: none
 * <p>
 * <code>
 * &nbsp;&nbsp; {$foo |inTrim}
 * </code>
 * <p>
 * Created on Feb 17, 2008
 * @author Dave
 *
 */
public final class InTrim implements TemplateFilterTag
{
	/**
	 * Constructor
	 */
	public InTrim()
	{
		super();
	}

	public TemplateVariable execute(TemplateVariable[] args) throws TemplateFilterException
	{
		assert args.length > 0;

		if (args.length != 1)
		{
			throw new TemplateFilterException("Filter inTrim does not accept extra parameters");
		}

		final String string = args[0].toString().trim();

		StringBuffer buffer = new StringBuffer();

		StringTokenizer tokenizer = new StringTokenizer(string);

		while (tokenizer.hasMoreTokens() == true)
		{
			if (buffer.length() > 0)
			{
				buffer.append(' ');
			}

			buffer.append(tokenizer.nextToken());
		}

		return TemplateVariable.newInstance(buffer.toString());
	}
}