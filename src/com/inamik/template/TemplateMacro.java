/*
 * $Id: TemplateMacro.java,v 1.3 2006-08-21 03:07:12 dave Exp $
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
 * TemplateMacro - Macros enable templates to create small, reusable
 * template snippets.
 * <p>
 * Without macros, users would have to create lots of
 * tiny little include files.
 * <p>
 * Created on Aug 4, 2006
 * @author Dave
 */
public final class TemplateMacro
{
	private final TemplateProcessor processor;

//	/**
//	 * Default Constructor (Private)
//	 */
//	private TemplateMacro() {}

	/**
	 * Constructor (Package-Private) w/TemplateProcessor
	 */
	TemplateMacro(final TemplateProcessor processor)
	{
		super();

		if (processor == null)
		{
			throw new NullPointerException("processor");
		}

		this.processor = processor;
	}

	/**
	 * process
	 */
	void process(final TemplateContext context) throws TemplateException
	{
		if (context == null)
		{
			throw new NullPointerException("context");
		}

		processor.process(context);
	}
}
