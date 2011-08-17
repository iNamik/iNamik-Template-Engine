/*
 * $Id: NewLineFilter.java,v 1.6 2006-11-21 09:34:43 dave Exp $
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

import com.inamik.template.tags.EndlTag;
import com.inamik.template.tags.TagType;

/**
 * NewLineFilter - This filter removes any extra new lines (carriage-returns)
 * that get introduced by the template tags.
 * Created on Aug 2, 2006
 * @author Dave
 */
public final class NewLineFilter extends AbstractLineFilter
{
	private static enum LineType
	{
		UNKNOWN     (false),
		START_BLOCK (true ),
		ALT_BLOCK   (true ),
		END_BLOCK   (true ),
		EMPTY_ACTION(false),
		;
		private boolean blockType = false;
		private LineType(boolean blockType) { this.blockType = blockType; }
		public boolean isBlockType() { return this.blockType; }
	}

	private int      newLineCount = 0;
	private LineType lastLineType = null;


	/**
	 * Constructor
	 */
	public NewLineFilter()
	{
		super();
	}

	@Override
	protected void processLine()
	{
		assert size() > 0;
		assert	type(-1) == TagType.ENDL ||	type(-1) == TagType.EOF;

		// Track consecutive newlines
		if	(
				(size() == 1)
			&&	(type(0) == TagType.ENDL)
			)
		{
			// If we are not at the beginning of the file
			if (lastLineType != null)
			{
				newLineCount++;
			}

			clear();

			return;
		}

		// If line consists only of EOF
		// And there are trailing newlines
		// Then print one trailing newline at end of file
		if	(
				(size() == 1)
			&&	(type(0) == TagType.EOF)
			)
		{
			// Don't output a newline unless the file ends with at
			// least one newline
			if (newLineCount > 0)
			{
				putTag(EndlTag.getInstance());
			}
			putAll();
			clear();
			newLineCount = 0;
			return;
		}

		// If this is our first time through
		if (lastLineType == null)
		{
			assert newLineCount == 0;
		}

		LineType thisLineType = getLineType();

		// Trim block tags
		switch (thisLineType)
		{
			case ALT_BLOCK   :
			case END_BLOCK   :
			case EMPTY_ACTION:
			case START_BLOCK :
			{
				if (type(-1) == TagType.ENDL)
				{
					remove(-1);
				}

				break;
			}
			case UNKNOWN :
			{
				// Nothing
				break;
			}
			default :
			{
				throw new IllegalStateException("Unknown LineType :" + thisLineType);
			}
		}

		if (lastLineType != null)
		{
			if (lastLineType.isBlockType())
			{
				newLineCount = 0;
			}
		}
		else
		{
			assert newLineCount == 0;
		}

		// Under no circumstances can we print more than 2
		if (newLineCount > 2)
		{
			newLineCount = 2;
		}

		if (newLineCount > 0)
		{
			final EndlTag endl = EndlTag.getInstance();

			for(int i = 0; i < newLineCount; ++i)
			{
				putTag(endl);
			}

			newLineCount = 0;
		}

		assert newLineCount == 0;

		// If there's anything left, look for an endl
		if (size() > 0)
		{
			if (type(-1) == TagType.ENDL)
			{
				newLineCount++;
				remove(-1);
			}
		}

		// If we still have something to print, add it to the output queue
		if (size() > 0)
		{
			lastLineType = thisLineType;
			putAll();
		}

		clear();
	}

	private LineType getLineType()
	{
		if	(
				(isSpace_StartBlock_EndL())
			||	(isStartBlock_EndL())
			)
		{
			return LineType.START_BLOCK;
		}

		if	(
				(isSpace_AltBlock_EndL())
			||	(isAltBlock_EndL())
			)
		{
			return LineType.ALT_BLOCK;
		}

		if	(
				(isSpace_EndBlock_EndL())
			||	(isEndBlock_EndL())
			)
		{
			return LineType.END_BLOCK;
		}

		if	(
				(isSpace_EmptyAction_EndL())
			||	(isEmptyAction_EndL())
			)
		{
			return LineType.EMPTY_ACTION;
		}

		return LineType.UNKNOWN;
	}
}
