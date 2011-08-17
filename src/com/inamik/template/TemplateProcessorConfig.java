/*
 * $Id: TemplateProcessorConfig.java,v 1.3 2006-09-28 20:02:26 dave Exp $
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
 * TemplateProcessorConfig
 * Created on Jul 27, 2006
 * @author Dave
 * @deprecated
 */
@Deprecated
public abstract class TemplateProcessorConfig
{
	public static enum ProcessorType
	{
		COMPILED,
		INTERPRETED
	};

	private final ProcessorType processorType;

	/**
	 * Constructor w/ProcessorType
	 */
	protected TemplateProcessorConfig(final ProcessorType processorType)
	{
		super();

		this.processorType = processorType;
	}

	/**
	 * getProcessorType
	 */
	public final ProcessorType getProcessorType()
	{
		return this.processorType;
	}

}
