/*
 * $Id: CommentFilter.java,v 1.1 2006-11-21 09:34:43 dave Exp $
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

import com.inamik.template.tags.TagType;

/**
 * CommentFilter
 * @author Dave
 *
 */
public final class CommentFilter extends AbstractLineFilter
{

	/**
	 * Default Constructor
	 */
	public CommentFilter()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.inamik.template.tagfilters.AbstractLineFilter#processLine()
	 */
	@Override
	protected void processLine()
	{
		boolean commentFound =  false;

		// Walk the list backward so we can safely remove
		for (int i = size() - 1;  i >= 0; i--)
		{
			if	(
					(tag(i).getType() == TagType.BLOCK_COMMENT)
				||	(tag(i).getType() == TagType.LINE_COMMENT )
				)
			{
				commentFound = true;
				remove(i);
			}
		}

		if (commentFound == true)
		{
			if	(
					(tag(0).getType() == TagType.ENDL)
				||	(isSpaceOrLeadingSpace(0) && isEndlOrEofTag(1))
				)
			{
				clear();
			}
			else
			{
				putAll();
				clear();

			}
		}
		else
		{
			putAll();
			clear();
		}
	}
}
