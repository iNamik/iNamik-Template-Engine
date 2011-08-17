/*
 * $Id: TemplateLibConfig.java,v 1.1 2006-07-31 10:07:10 davidpfarrell Exp $
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

import java.util.ArrayList;
import java.util.List;

/**
 * TemplateLibConfig
 * Created on Jul 27, 2006
 * @author Dave
 */
public final class TemplateLibConfig
{
	private List<TemplateActionConfig>   actions   = new ArrayList<TemplateActionConfig>();
	private List<TemplateFunctionConfig> functions = new ArrayList<TemplateFunctionConfig>();
	private List<TemplateFilterConfig>   filters   = new ArrayList<TemplateFilterConfig>();

	/**
	 * Constructor
	 */
	public TemplateLibConfig()
	{
		super();
	}

	/**
	 * addAction
	 */
	public void addAction(final TemplateActionConfig action)
	{
		actions.add(action);
	}

	/**
	 * getActions
	 */
	public TemplateActionConfig[] getActions()
	{
		return actions.toArray(new TemplateActionConfig[actions.size()]);
	}

	/**
	 * addFunction
	 */
	public void addFunction(final TemplateFunctionConfig function)
	{
		functions.add(function);
	}

	/**
	 * getFunctions
	 */
	public TemplateFunctionConfig[] getFunctions()
	{
		return functions.toArray(new TemplateFunctionConfig[functions.size()]);
	}

	/**
	 * addFilter
	 */
	public void addFilter(final TemplateFilterConfig filter)
	{
		filters.add(filter);
	}

	/**
	 * getFilters
	 */
	public TemplateFilterConfig[] getFilters()
	{
		return filters.toArray(new TemplateFilterConfig[filters.size()]);
	}

}
