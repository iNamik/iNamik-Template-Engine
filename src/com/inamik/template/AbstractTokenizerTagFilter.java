/*
 * $Id: AbstractTokenizerTagFilter.java,v 1.1 2006-08-04 03:50:04 davidpfarrell Exp $
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
import com.inamik.template.tags.TagType;

/**
 * AbstractTokenizerTagFilter
 * Created on Aug 2, 2006
 * @author Dave
 */
public abstract class AbstractTokenizerTagFilter implements TemplateTokenizerTagFilter
{
	protected TemplateEngineConfig engineConfig = null;
	private TemplateTokenizerTagFilter outputFilter = null;
	private List<Tag>           outputList   = null;
	private boolean             closed       = false;

	/**
	 * Constructor
	 */
	public AbstractTokenizerTagFilter()
	{
		super();
	}

	public final void setEngineConfig(final TemplateEngineConfig engineConfig)
	{
		assert engineConfig != null;
		this.engineConfig = engineConfig;
	}

	/* (non-Javadoc)
	 * @see com.inamik.template.TemplateTokenizerTagFilter#setOutput(java.util.List)
	 */
	public final void setOutput(final List<Tag> outputList)
	{
		assert this.outputFilter == null;
		assert      outputList   != null;

		this.outputList = outputList;
	}

	/* (non-Javadoc)
	 * @see com.inamik.template.TemplateTokenizerTagFilter#addTag(com.inamik.template.tags.Tag)
	 */
	public final void addTag(Tag tag)
	{
		assert engineConfig != null;
		assert outputFilter == null || outputList == null;
		assert closed == false;
		assert tag != null;

		doAddTag(tag);

		closed = tag.getType() == TagType.EOF;

		if (closed)
		{
			doClose();
		}
	}

	/* (non-Javadoc)
	 * @see com.inamik.template.TemplateTokenizerTagFilter#setOutput(com.inamik.template.TemplateTokenizerTagFilter)
	 */
	public final void setOutput(final TemplateTokenizerTagFilter outputFilter)
	{
		assert this.outputList   == null;
		assert      outputFilter != null;

		this.outputFilter = outputFilter;
	}

	protected final void putTag(final Tag tag)
	{
		assert outputFilter == null || outputList == null;

		if (outputFilter != null)
		{
			outputFilter.addTag(tag);
		}
		else
		{
			outputList.add(tag);
		}
	}

	protected final void putAll(final List<Tag> tagList)
	{
		assert outputFilter == null || outputList == null;

		if (outputFilter != null)
		{
			for (Tag tag : tagList)
			{
				putTag(tag);
			}
		}
		else
		{
			outputList.addAll(tagList);
		}
	}

	/**
	 * doAddTag (Protected/Abstract)
	 */
	protected abstract void doAddTag(final Tag tag);

	/**
	 * doClose - Called after EofTag has been sent & Processed
	 */
	protected void doClose()
	{
		; //
	}
}
