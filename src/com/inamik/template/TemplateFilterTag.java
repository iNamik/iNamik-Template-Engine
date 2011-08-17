/*
 * $Id: TemplateFilterTag.java,v 1.3 2006-08-21 03:07:12 dave Exp $
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
 * TemplateFilterTag - The interface for defining your own template filters.
 * <p>
 * Filters behave very similarly to functions.  So much so in fact that the
 * TemplateFilterTag interface extends the TemplateFunctionTag interface.
 * <p>
 * This relationship allows your filter classes to double as functions.  You
 * still have to declare the function in your tag library configuration, but
 * you can use your filter's class as the implementation of your function.
 * <p>
 * Created on Jul 7, 2006
 * @author Dave
 * @see com.inamik.template.TemplateFunctionTag
 */
public interface TemplateFilterTag extends TemplateFunctionTag
{
	/**
	 *  execute - Execute the filter.
	 *  <p>
	 *  The first paramer is implicitly set to the item being filtered.
	 *  For example, see the following teamplte snippet:
	 *  <p>
	 *  &nbsp;&nbsp; <code>{"text" | filter("parm")}</code>
	 *  <p>
	 *  This snippet gets essentially translated to :
	 *  <p>
	 *  &nbsp;&nbsp <code>filter.execute("text", "parm")</code>
	 *  <p>
	 *  When a filter is used as a function, there is no implicit first
	 *  parameter.  The item to be filtered has to be explicitly passed.
	 *  For example :
	 *  <p>
	 *  &nbsp;&nbsp; <code>{if filter("text", "parm")}...{/if}</code>
	 *
	 *  @param args The list of arguments to this filter, with the first
	 *         argument being the item to be filtered.
	 *
	 *  @return The filtered result.  When used in a filtered context, this
	 *          result will replace the item being filtered.
	 *
	 * @throws TemplateFilterException
	 */
	public TemplateVariable execute(final TemplateVariable[] args) throws TemplateFilterException;
}
