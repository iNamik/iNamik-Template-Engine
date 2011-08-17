/*
 * $Id: BlockTagFilter.java,v 1.1 2006-11-21 09:34:43 dave Exp $
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
import java.util.Stack;

import com.inamik.template.AbstractTokenizerTagFilter;
import com.inamik.template.TemplateActionConfig;
import com.inamik.template.TemplateTagName;
import com.inamik.template.TemplateTokenizerTagFilter;
import com.inamik.template.tags.ActionTag;
import com.inamik.template.tags.BodyActionTag;
import com.inamik.template.tags.EndBlockTag;
import com.inamik.template.tags.MultipleTagsTag;
import com.inamik.template.tags.Tag;
import com.inamik.template.tags.TagType;

/**
 * BlockTagFilter
 * @author Dave
 */
public final class BlockTagFilter extends AbstractTokenizerTagFilter implements TemplateTokenizerTagFilter
{
	List<Tag> tagList = new ArrayList<Tag>();

	Stack<BodyActionTag>        bodyActionTagStack = new Stack<BodyActionTag>();
	Stack<TemplateActionConfig> actionConfigStack  = new Stack<TemplateActionConfig>();
	Stack<MultipleTagsTag>      multipleTagsStack  = new Stack<MultipleTagsTag>();

	/**
	 * 
	 */
	public BlockTagFilter()
	{
		super();
	}

	/* (non-Javadoc)
	 * @see com.inamik.template.AbstractTokenizerTagFilter#doAddTag(com.inamik.template.tags.Tag)
	 */
	@Override
	protected void doAddTag(Tag tag)
	{
		if (tag.getType() == TagType.EOF)
		{
			putTag(tag);
			return;
		}

		assert tag.getType() != TagType.EOF;

		TemplateActionConfig ac = null;

		if (tag.getType() == TagType.ACTION)
		{
			TemplateTagName name = ((ActionTag)tag).getFQName();

			ac = engineConfig.getActionConfig(name);

			if (ac == null)
			{
				throw new RuntimeException("Unknown Action Tag: " + name);
			}
		}

		// if its a body tag
		if	(
				(ac != null)
			&&	(
					(ac.getBlockType() == TemplateActionConfig.BlockType.BODY)
				||	(ac.getBlockType() == TemplateActionConfig.BlockType.BODY_ALT)
				)
			)
		{
			if (ac.getBlockType() == TemplateActionConfig.BlockType.BODY)
			{
				BodyActionTag bat = new BodyActionTag((ActionTag)tag);
				bodyActionTagStack.push(bat);
				actionConfigStack.push(ac);
				MultipleTagsTag mtt = new MultipleTagsTag();
				multipleTagsStack.push(mtt);
			}
			else
			if (ac.getBlockType() == TemplateActionConfig.BlockType.BODY_ALT)
			{
				if (bodyActionTagStack.empty() == true)
				{
					throw new RuntimeException(tag.getFilename() + " (" + tag.getLine() + ", " + tag.getColumn() + "): Illegal use of Body-Alt Tag: " + ac.getName());
				}

				assert multipleTagsStack.empty() == false;

				MultipleTagsTag      mtt = multipleTagsStack.pop();
				TemplateActionConfig bac = actionConfigStack.peek();

				// Is it a body tag
				if (bac.getBlockType() == TemplateActionConfig.BlockType.BODY)
				{
					BodyActionTag bat = bodyActionTagStack.peek();

					bat.setBody(mtt);
				}
				else
				// is it an alt-body tag
				if (bac.getBlockType() == TemplateActionConfig.BlockType.BODY_ALT)
				{
					BodyActionTag bat = bodyActionTagStack.pop();

					bac = actionConfigStack.pop();

					bat.setBody(mtt);

					BodyActionTag         bat2 = bodyActionTagStack.peek();
					TemplateActionConfig  bac2 = actionConfigStack.peek();

					assert bac2.getBlockType() == TemplateActionConfig.BlockType.BODY;

					bat2.addAltBodyElement(bat);
				}

				BodyActionTag bat = new BodyActionTag((ActionTag)tag);
				bodyActionTagStack.push(bat);
				actionConfigStack.push(ac);
				mtt = new MultipleTagsTag();
				multipleTagsStack.push(mtt);
			}
		}
		else
		// End block tag
		if (tag.getType() == TagType.END_BLOCK)
		{
			TemplateTagName name = ((EndBlockTag)tag).getFQName();

			if (bodyActionTagStack.empty() == true)
			{
				if (name == null)
				{
					throw new RuntimeException("dangling close tag");
				}
				else
				{
					throw new RuntimeException("dangling close tag: " + name);
				}
			}

			BodyActionTag        bat = bodyActionTagStack.pop();
			TemplateActionConfig bac = actionConfigStack.pop();
			MultipleTagsTag      mtt = multipleTagsStack.pop();

			bat.setBody(mtt);

			// is it an alt-body tag
			if (bac.getBlockType() == TemplateActionConfig.BlockType.BODY_ALT)
			{
					BodyActionTag        bat2 = bodyActionTagStack.pop();
					TemplateActionConfig bac2 = actionConfigStack.pop();

					assert bac2.getBlockType() == TemplateActionConfig.BlockType.BODY;

					bat2.addAltBodyElement(bat);

					bat = bat2;
					bac = bac2;
					mtt = null;
			}

			TemplateTagName batName = bat.getAction().getFQName();

			if (name != null)
			{
				if (name.equals(batName) == false)
				{
					throw new RuntimeException("close tag " + name + " does not match open tag " + batName);
				}
			}

			if (multipleTagsStack.empty() == false)
			{
				assert bodyActionTagStack.empty() == false;
				assert actionConfigStack.empty() == false;

				mtt = multipleTagsStack.peek();
				mtt.addElement(bat);
			}
			else
			{
//				c.add(bat);
				putTag(bat);
			}
		}
		else
		// NULL
		if (tag.getType() == TagType.NULL)
		{
			; // do nothing
		}
		// Other
		else
		if (multipleTagsStack.empty() == false)
		{
			assert bodyActionTagStack.empty() == false;
			assert actionConfigStack.empty() == false;

			MultipleTagsTag mtt = multipleTagsStack.peek();
			mtt.addElement(tag);
		}
		else
		{
//			c.add(tag);
			putTag(tag);
		}
	}

}
