/*
 * $Id: AbstractLineFilter.java,v 1.3 2006-11-21 09:34:43 dave Exp $
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
package com.inamik.template.tagfilters;

import java.util.ArrayList;
import java.util.List;

import com.inamik.template.AbstractTokenizerTagFilter;
import com.inamik.template.TemplateActionConfig;
import com.inamik.template.TemplateEngineConfig;
import com.inamik.template.tags.ActionTag;
import com.inamik.template.tags.LeadingSpaceTag;
import com.inamik.template.tags.SpaceTag;
import com.inamik.template.tags.Tag;
import com.inamik.template.tags.TagType;

/**
 * AbstractLineFilter
 * Created on Aug 2, 2006
 * @author Dave
 */
abstract class AbstractLineFilter extends AbstractTokenizerTagFilter
{
	protected List<Tag> line = new ArrayList<Tag>();

	/**
	 * Constructor
	 */
	public AbstractLineFilter()
	{
		super();
	}

	/* (non-Javadoc)
	 * @see com.inamik.template.AbstractTokenizerTagFilter#doAddTag(com.inamik.template.tags.Tag)
	 */
	@Override
	protected final void doAddTag(final Tag tag)
	{
		assert tag != null;

//		System.err.println("TagType = " + tag.getType());
//		System.err.println("line = " + line.toString());


		// If first tag and is space tag,
		// then change to leading space tag
		if	(
				(size() == 0)
			&&	(tag.getType() == TagType.SPACE)
			)
		{
			Tag newTag = new LeadingSpaceTag( ((SpaceTag)tag).getText());
			newTag.setColumn(tag.getColumn());
			newTag.setFilename(tag.getFilename());
			newTag.setLine(tag.getLine());
			line.add(newTag);

			assert type(0) != TagType.SPACE;
		}
		else
		{
			assert size() == 0 || tag.getType() != TagType.LEADING_SPACE;

			line.add(tag);

			assert type(0) != TagType.SPACE;

			if (tag.getType() == TagType.ENDL || tag.getType() == TagType.EOF)
			{

				stripTrailingWhitespace();
				processLine();
			}
		}
	}

	protected abstract void processLine();

	protected final void put(final int at)
	{
		putTag(tag(at));
	}

	protected final void putAll()
	{
		putAll(line);
	}

	protected final void stripTrailingWhitespace()
	{
		assert type(-1) == TagType.ENDL || type(-1) == TagType.EOF;

		if	(
				(size() > 1)
			&&	(type(-2) == TagType.SPACE)
			)
		{
			remove(-2);
		}
	}


	protected final boolean isStartBlockTag(final int at)
	{
		return isStartBlockTag(tag(at), engineConfig);
	}

	protected final static boolean isStartBlockTag(final Tag tag, final TemplateEngineConfig engineConfig)
	{
		boolean result = false;

		if (tag.getType() == TagType.ACTION)
		{
			ActionTag actionTag = (ActionTag)tag;
			TemplateActionConfig ac = engineConfig.getActionConfig(actionTag.getFQName());

			result = (ac.getBlockType() == TemplateActionConfig.BlockType.BODY);
		}

		return result;
	}

	protected final boolean isAltBlockTag(final int at)
	{
		return isAltBlockTag(tag(at), engineConfig);
	}

	protected final static boolean isAltBlockTag(final Tag tag, final TemplateEngineConfig engineConfig)
	{
		boolean result = false;

		if (tag.getType() == TagType.ACTION)
		{
			ActionTag actionTag = (ActionTag)tag;
			TemplateActionConfig ac = engineConfig.getActionConfig(actionTag.getFQName());

			result = (ac.getBlockType() == TemplateActionConfig.BlockType.BODY_ALT);
		}

		return result;
	}

	protected final boolean isEmptyActionTag(final int at)
	{
		return isEmptyActionTag(tag(at), engineConfig);
	}

	protected final static boolean isEmptyActionTag(final Tag tag, final TemplateEngineConfig engineConfig)
	{
		boolean result = false;

		if (tag.getType() == TagType.ACTION)
		{
			ActionTag actionTag = (ActionTag)tag;
			TemplateActionConfig ac = engineConfig.getActionConfig(actionTag.getFQName());

			result =	(
							(ac.getBlockType()   == TemplateActionConfig.BlockType.EMPTY)
						&&	(ac.getBodyContent() == TemplateActionConfig.BodyContent.EMPTY)
						);
		}

		return result;
	}

	protected final boolean isEndBlockTag(final int at)
	{
		return isEndBlockTag(tag(at));
	}
	protected final static boolean isEndBlockTag(final Tag tag)
	{
		return tag.getType() == TagType.END_BLOCK;
	}

	protected final boolean isEndlOrEofTag(final int at)
	{
		return isEndlOrEofTag(tag(at));
	}

	protected final static boolean isEndlOrEofTag(final Tag tag)
	{
		return tag.getType() == TagType.ENDL || tag.getType() == TagType.EOF;
	}

	protected final boolean isSpaceOrLeadingSpace(final int at)
	{
		return isSpaceOrLeadingSpace(tag(at));
	}

	protected final static boolean isSpaceOrLeadingSpace(final Tag tag)
	{
		return	(
					(tag.getType() == TagType.SPACE)
				||	(tag.getType() == TagType.LEADING_SPACE)
				);
	}

	protected final boolean isSpace_StartBlock_EndL()
	{
		return isSpace_StartBlock_EndL(line, engineConfig);
	}
	protected final static boolean isSpace_StartBlock_EndL(final List<Tag> line, TemplateEngineConfig engineConfig)
	{
		return	(
					(line.size() == 3)
				&&	(isSpaceOrLeadingSpace(line.get(0)))
				&&	(isStartBlockTag(line.get(1), engineConfig))
				&&	(
						(line.get(2).getType() == TagType.ENDL)
					||	(line.get(2).getType() == TagType.EOF)
					)
				);
	}

	protected final boolean isStartBlock_EndL()
	{
		return isStartBlock_EndL(line, engineConfig);
	}
	protected final static boolean isStartBlock_EndL(final List<Tag> line, final TemplateEngineConfig engineConfig)
	{
		return	(
					(line.size() == 2)
				&&	(isStartBlockTag(line.get(0), engineConfig))
				&&	(
						(line.get(1).getType() == TagType.ENDL)
					||	(line.get(1).getType() == TagType.EOF)
					)
				);
	}

	protected final boolean isSpace_EndBlock_EndL()
	{
		return isSpace_EndBlock_EndL(line);
	}

	protected final static boolean isSpace_EndBlock_EndL(final List<Tag> line)
	{
		return	(
					(line.size() == 3)
				&&	(isSpaceOrLeadingSpace(line.get(0)))
				&&	(line.get(1).getType() == TagType.END_BLOCK)
				&&	(
						(line.get(2).getType() == TagType.ENDL)
					||	(line.get(2).getType() == TagType.EOF)
					)
				);
	}

	protected final boolean isEndBlock_EndL()
	{
		return isEndBlock_EndL(line);
	}
	protected final static boolean isEndBlock_EndL(final List<Tag> line)
	{
		return	(
					(line.size() == 2)
				&&	(line.get(0).getType() == TagType.END_BLOCK)
				&&	(
						(line.get(1).getType() == TagType.ENDL)
					||	(line.get(1).getType() == TagType.EOF)
					)
				);
	}

	protected final boolean isSpace_AltBlock_EndL()
	{
		return isSpace_AltBlock_EndL(line, engineConfig);
	}

	protected final static boolean isSpace_AltBlock_EndL(final List<Tag> line, final TemplateEngineConfig engineConfig)
	{
		return	(
					(line.size() == 3)
				&&	(isSpaceOrLeadingSpace(line.get(0)))
				&&	(isAltBlockTag(line.get(1), engineConfig))
				&&	(
						(line.get(2).getType() == TagType.ENDL)
					||	(line.get(2).getType() == TagType.EOF)
					)
				);
	}

	protected final boolean isSpace_EmptyAction_EndL()
	{
		return isSpace_EmptyAction_EndL(line, engineConfig);
	}

	protected final static boolean isSpace_EmptyAction_EndL(final List<Tag> line, final TemplateEngineConfig engineConfig)
	{
		return	(
					(line.size() == 3)
				&&	(isSpaceOrLeadingSpace(line.get(0)))
				&&	(isEmptyActionTag(line.get(1), engineConfig))
				&&	(
						(line.get(2).getType() == TagType.ENDL)
					||	(line.get(2).getType() == TagType.EOF)
					)
				);
	}

	protected final boolean isAltBlock_EndL()
	{
		return isAltBlock_EndL(line, engineConfig);
	}

	protected final static boolean isAltBlock_EndL(final List<Tag> line, final TemplateEngineConfig engineConfig)
	{
		return	(
					(line.size() == 2)
				&&	(isAltBlockTag(line.get(0), engineConfig))
				&&	(
						(line.get(1).getType() == TagType.ENDL)
					||	(line.get(1).getType() == TagType.EOF)
					)
				);
	}

	protected final boolean isEmptyAction_EndL()
	{
		return isEmptyAction_EndL(line, engineConfig);
	}

	protected final static boolean isEmptyAction_EndL(final List<Tag> line, final TemplateEngineConfig engineConfig)
	{
		return	(
					(line.size() == 2)
				&&	(isEmptyActionTag(line.get(0), engineConfig))
				&&	(
						(line.get(1).getType() == TagType.ENDL)
					||	(line.get(1).getType() == TagType.EOF)
					)
				);
	}
	protected final int translateIndex(final int at)
	{
		int index = at >= 0 ? at : size() + at;

		assert index >= 0 && index < size();

		return index;
	}

	protected final Tag tag(final int at)
	{
		return line.get(translateIndex(at));
	}

	protected final TagType type(final int at)
	{
		return line.get(translateIndex(at)).getType();
	}

	protected final Tag remove(final int at)
	{
		return line.remove(translateIndex(at));
	}

	protected final Tag set(final int at, final Tag tag)
	{
		return line.set(translateIndex(at), tag);
	}

	protected final void add(final int at, final Tag tag)
	{
		line.add(translateIndex(at), tag);
	}

	protected final void clear()
	{
		line.clear();
	}

	protected final int size()
	{
		return line.size();
	}
}
