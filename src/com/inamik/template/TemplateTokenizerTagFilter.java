/*
 * $Id: TemplateTokenizerTagFilter.java,v 1.1 2006-08-04 03:50:04 davidpfarrell Exp $
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

import java.util.List;

import com.inamik.template.tags.Tag;

/**
 * TemplateTokenizerTagFilter
 * Created on Aug 2, 2006
 * @author Dave
 */
public interface TemplateTokenizerTagFilter
{
	public void setEngineConfig(final TemplateEngineConfig engineConfig);
	public void setOutput(final List<Tag> outputList);
	public void setOutput(final TemplateTokenizerTagFilter outputFilter);

	public void addTag(final Tag tag);
}
