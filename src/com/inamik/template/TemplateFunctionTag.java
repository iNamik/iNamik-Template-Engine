/*
 * $Id: TemplateFunctionTag.java,v 1.2 2006-08-21 03:07:12 dave Exp $
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
package com.inamik.template;

/**
 * TemplateFunctionTag - The interface for defining your own template functions.
 * <p>
 * Created on Jul 7, 2006
 * @author Dave
 */
public interface TemplateFunctionTag
{
	/**
	 *  execute - Execute the filter.
	 *  @param args The list of arguments to this function.
	 *
	 *  @return The function result.
	 *
	 *  @throws TemplateFunctionException
	 */
	public TemplateVariable execute(final TemplateVariable[] args) throws TemplateFunctionException;
}
