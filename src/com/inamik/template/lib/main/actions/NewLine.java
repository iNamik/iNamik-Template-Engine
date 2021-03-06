/*
 * $Id: NewLine.java,v 1.3 2006-08-21 03:07:12 dave Exp $
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

import com.inamik.template.AbstractTemplateActionTag;

/**
 * NewLine - Print a newline ("\n")to the template's output stream.
 * <p>
 * ParmType: Empty
 * <br>
 * BodyType: Empty
 * <br>
 * BodyContent: Empty
 * <p>
 * Created on Aug 8, 2006
 * @author Dave
 */
public final class NewLine extends AbstractTemplateActionTag
{
	/**
	 * Constructor
	 */
	public NewLine()
	{
		super();
	}

	@Override
	public ExecuteResult execute()
	{
		getContext().getOut().println();

		return ExecuteResult.OK;
	}
}
