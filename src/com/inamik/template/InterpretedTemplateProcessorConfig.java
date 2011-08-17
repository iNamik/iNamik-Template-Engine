/*
 * $Id: InterpretedTemplateProcessorConfig.java,v 1.4 2006-09-28 20:02:26 dave Exp $
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
 * InterpretedTemplateProcessorConfig
 * Created on Jul 27, 2006
 * @author Dave
 * @deprecated
 */
@Deprecated
public final class InterpretedTemplateProcessorConfig extends TemplateProcessorConfig
{

	private TemplateCacheConfig cacheConfig = null;

	/**
	 * Constructor
	 */
	public InterpretedTemplateProcessorConfig()
	{
		super(ProcessorType.INTERPRETED);
	}

	/**
	 * setCacheConfig
	 */
	public void setCacheConfig(TemplateCacheConfig cacheConfig)
	{
		this.cacheConfig = cacheConfig;
	}

	/**
	 * getCacheConfig
	 */
	public TemplateCacheConfig getCacheConfig()
	{
		return this.cacheConfig;
	}

}
