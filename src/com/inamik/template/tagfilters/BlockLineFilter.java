/*
 * $Id: BlockLineFilter.java,v 1.3 2006-11-21 09:34:43 dave Exp $
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

import java.util.Stack;

import com.inamik.template.tags.ActionTag;
import com.inamik.template.tags.EndlTag;
import com.inamik.template.tags.Tag;

/**
 * BlockLineFilter - This filter enforces the simple rule:
 * Unless the open and close tags for a body action are on the same line,
 * The open and close (and any alt-body) tags should be on their own line.
 * Created on Aug 2, 2006
 * @author Dave
 */
public final class BlockLineFilter extends AbstractLineFilter
{
	/**
	 * Constructor
	 */
	public BlockLineFilter()
	{
		super();
	}

	@Override
	protected void processLine()
	{
		// If the line consists of just a tag
		if	(
				(isStartBlock_EndL())
			||	(isSpace_StartBlock_EndL())
			||	(isAltBlock_EndL())
			||	(isSpace_AltBlock_EndL())
			||	(isEndBlock_EndL())
			||	(isSpace_EndBlock_EndL())
			||	(isSpace_EmptyAction_EndL())
			||	(isEmptyAction_EndL())
			)
		{
			putAll();
			clear();
			return;
		}

		Stack<Integer> startIndexStack  = new Stack<Integer>();

		boolean fixup = false;
		int fixupIndex = -1;

		// Cycle through the tags in the line
		for (int i = 0, n = size(); fixup == false && i < n; ++i)
		{
			// Did we find a start-block tag?
			if (isStartBlockTag(i))
			{
				// store the position
				startIndexStack.push(new Integer(i));
			}
			else
			// Did we find an alt-block tag?
			if (isAltBlockTag(i))
			{
				// If there is no start-block tag on this line
				if (startIndexStack.empty() == true)
				{
					fixup = true;
					fixupIndex = i;
					break;
				}
			}
			else
			// Did we find an end tag?
			if (isEndBlockTag(i))
			{
				// If there is no start-block on this line
				if (startIndexStack.empty() == true)
				{
					fixup = true;
					fixupIndex = i;
				}
				// There is a start-block tag, all is well
				else
				{
					// Forget about this block
					Integer startIndex = startIndexStack.pop();
					ActionTag actionTag = (ActionTag)tag(startIndex);
					actionTag.setInline(true);
				}
			}
// DAVE I'm not sure this is more useful than leaving it alone
//			else
//			// Did we find an empty action tag?
//			if (isEmptyActionTag(i))
//			{
//				if (startIndexStack.empty() == true)
//				{
//					fixup = true;
//					fixupIndex = i;
//					break;
//				}
//			}
		}

		// If we didn't find a problem
		if (fixup == false)
		{
			assert fixupIndex == -1;

			// Is there a dangling start-block tag?
			if (startIndexStack.empty() == false)
			{
				// Grab the first onee
				fixupIndex = startIndexStack.firstElement().intValue();
				fixup = true;
			}
		}
		// We did find a problem
		else
		{
			// Some sanity checks
			assert fixupIndex > -1;
			assert startIndexStack.empty() == true;
		}

		// Did we find a problem?
		if (fixup == true)
		{
			Tag spaceTag;

			// Check for leading whitepsace
			if (isSpaceOrLeadingSpace(0))
			{
				spaceTag = tag(0);
			}
			else
			{
				spaceTag = null;
			}

			// Copy tags before fixupIndex to output
			for (int i = 0; i < fixupIndex; ++i)
			{
				put(0);
				remove(0);
			}

			// If the offending tag was not at the beginning of the line
			if (!(
					(fixupIndex == 0)
				||	(
						(fixupIndex == 1)
					&&	(spaceTag != null)
					)
				))
			{
				// Insert a new line between the leading tags and the
				// offending tag
				putTag(EndlTag.getInstance());

				// Place the offending tag at the same leading-space level
				// as the original line
				if (spaceTag != null)
				{
					putTag(spaceTag);
				}
			}

			// Output the offending tag
			put(0);
			remove(0);

			// If the tag after the offending tag is not a new line or EOF
			if (isEndlOrEofTag(0) == false)
			{
				// Insert a new line between the trailing tags and the
				// offending tag
				putTag(EndlTag.getInstance());

//				// Bring the remaining tags on the line to the same
//				// leading space level as the original line
//				if (spaceTag != null)
//				{
//					if (isSpaceOrLeadingSpace(0))
//					{
//						set(0, spaceTag);
//					}
//					else
//					{
//						add(0, spaceTag);
//					}
//				}

				// Process the remaining line
				processLine();
			}
		}
		// No problems
		else
		{
			putAll();
			clear();
		}
	}
}
