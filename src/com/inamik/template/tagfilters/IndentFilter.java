/*
 * $Id: IndentFilter.java,v 1.3 2006-11-21 09:34:43 dave Exp $
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

import com.inamik.template.tags.LeadingSpaceTag;
import com.inamik.template.tags.Tag;
import com.inamik.template.tags.TagType;

/**
 * IndentFilter - This filter fixes extra indentations that get introduced
 * through well-formatted template files.
 * Note: This filter requires the BlockLine Filter and must appear in the
 * chain AFTER the BlockLine Filter but before any filters that break the
 * BlockLine Rule
 * Created on Aug 2, 2006
 * @author Dave
 */
public final class IndentFilter extends AbstractLineFilter
{
	private Stack<List<List<Tag>>> blockStack = new Stack<List<List<Tag>>>();

	/**
	 * Constructor
	 */
	public IndentFilter()
	{
		super();
	}

	@Override
	protected void doClose()
	{
		super.doClose();

		while (blockStack.empty() == false)
		{
			List<List<Tag>> lineList = blockStack.pop();

			lineList.add(new ArrayList<Tag>(this.line));

			if (blockStack.empty() == false)
			{
				List<List<Tag>> peekList = blockStack.peek();

				for (List<Tag> lineItem : lineList)
				{
					peekList.add(lineItem);
				}
			}
			else
			{
				for (List<Tag> lineItem : lineList)
				{
					putAll(lineItem);
				}
			}
		}

	}

	@Override
	protected void processLine()
	{
		assert size() > 0;
		assert type(-1) == TagType.ENDL ||	type(-1) == TagType.EOF;
		assert type(0) != TagType.SPACE;

		if	(
				(isSpace_StartBlock_EndL())
			||	(isStartBlock_EndL())
			)
		{
			List<List<Tag>> lineList = new ArrayList<List<Tag>>();
			lineList.add(new ArrayList<Tag>(this.line));
			blockStack.push(lineList);
		}
		else
		if	(
				(isSpace_EndBlock_EndL())
			||	(isEndBlock_EndL())
			)
		{
			if (blockStack.empty() == false)
			{
				List<List<Tag>> lineList = blockStack.pop();

				lineList.add(new ArrayList<Tag>(this.line));

				normalize3(lineList);

				if (blockStack.empty() == false)
				{
					List<List<Tag>> peekList = blockStack.peek();

					for (List<Tag> lineItem : lineList)
					{
						peekList.add(lineItem);
					}
				}
				else
				{
					for (List<Tag> lineItem : lineList)
					{
						putAll(lineItem);
					}
				}

//				clear();
			}
			// User has unopened close tag, not my problem
			else
			{
				putAll();
//				clear();
			}
		}
		else
		{
			if (blockStack.empty() == false)
			{
				blockStack.peek().add(new ArrayList<Tag>(this.line));
			}
			else
			{
				putAll();
//				clear();
			}
		}

		clear();
	}

	/**
	 * normalize1
	 * @param lineList
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void normalize1(final List<List<Tag>> lineList)
	{
		assert lineList.size() > 0;

		List<Tag>oldLine = this.line;

		List<Tag> leadLine = lineList.get(0);

		LeadingSpaceTag leadSpaceTag  = leadLine.get(0).getType() == TagType.LEADING_SPACE ? (LeadingSpaceTag)leadLine.get(0) : null;
		String          leadSpaceText = leadSpaceTag != null ? leadSpaceTag.getText() : null;

		boolean normalizeDisabled = false;

		String shortestExtraSpaceText = null;

		// Cycle through each line
		for (int i = 0, n = lineList.size(); normalizeDisabled == false && i < n; ++i)
		{
//			List<Tag> currentLine = lineList.get(i);

			line = lineList.get(i);

			// Skip first and last tags
			if (i == 0 || i == lineList.size() - 1 || isSpace_AltBlock_EndL() || isAltBlock_EndL())
			{
				continue;
			}

			assert (i != 0 && i != lineList.size() - 1);

			LeadingSpaceTag spaceTag  = type(0) == TagType.LEADING_SPACE ? (LeadingSpaceTag)tag(0) : null;
			String          spaceText = spaceTag != null ? spaceTag.getText() : null;

			String extraSpaceText;

			// Need to find out if this tag starts with the same leading
			// space (or none) as the lead line

			// If lead line has leading space
			if (leadSpaceText != null)
			{
				// If the current line has leading space
				if (spaceText != null)
				{
					// If the current line starts with the same text
					// as the lead line
					if (spaceText.startsWith(leadSpaceText) == true)
					{
						extraSpaceText = spaceText.substring(leadSpaceText.length());
					}
					// The current line does not start with the same text
					// as the lead line
					else
					{
						extraSpaceText = null;
						normalizeDisabled = true;
						break;
					}
				}
				// The current line does not have any leading space
				else
				{
					// Okay to ignore.  A tag with no leading space
					// Does not disable normalization
					extraSpaceText = null;
				}
			}
			// The lead line has no leading space
			else
			{
				// If the current line does have leading space
				if (spaceText != null)
				{
					extraSpaceText = spaceText;
				}
				else
				{
					extraSpaceText = null;
				}
			}

			if	(
					(extraSpaceText != null)
				&&	(
						(shortestExtraSpaceText == null)
					||	(extraSpaceText.length() < shortestExtraSpaceText.length())
					)
				)
			{
				shortestExtraSpaceText = extraSpaceText;
			}
		} // for

		// Are we still normalizing?
		if	(
				(normalizeDisabled == true)
			||	(shortestExtraSpaceText == null)
			||	(shortestExtraSpaceText.length() == 0)
			)
		{
			this.line = oldLine;
			return;
		}

		// Cycle through each line
		for (int i = 0, n = lineList.size(); i < n; ++i)
		{
//			List<Tag> currentLine = lineList.get(i);
			line = lineList.get(i);

			// Skip first tag
			if (i == 0)
			{
				continue;
			}

			// Set the end block leading space to match the start block
			// leading space
			if (i == lineList.size() - 1 || isSpace_AltBlock_EndL() || isAltBlock_EndL())
			{
				if (type(0) == TagType.LEADING_SPACE)
				{
					if (leadSpaceTag != null)
					{
						set(0, leadSpaceTag);
					}
					else
					{
						remove(0);
					}
				}
				else
				{
					if (leadSpaceTag != null)
					{
						add(0, leadSpaceTag);
					}
					else
					{
						; // Nothing to do
					}
				}

				continue;
			}

			assert (i != 0 && i != lineList.size() - 1);

			LeadingSpaceTag spaceTag  = type(0) == TagType.LEADING_SPACE ? (LeadingSpaceTag)tag(0) : null;
			String          spaceText = spaceTag != null ? spaceTag.getText() : null;

			String extraSpaceText;

			// Need to find out if this tag starts with the same leading
			// space (or none) as the lead line

			// If lead line has leading space
			if (leadSpaceText != null)
			{
				// If the current line has leading space
				if (spaceText != null)
				{
					// If the current line starts with the same text
					// as the lead line
					if (spaceText.startsWith(leadSpaceText) == true)
					{
						// Grab the extra space
						extraSpaceText = spaceText.substring(leadSpaceText.length());

						// If the extra space starts with the shortest extra space
						if (extraSpaceText.startsWith(shortestExtraSpaceText) == true)
						{
							// Remove the shortest extra space
							extraSpaceText = extraSpaceText.substring(shortestExtraSpaceText.length());
						}
					}
					// The current line does not start with the same text
					// as the lead line
					else
					{
						extraSpaceText = null;
					}
				}
				// The current line does not have any leading space
				else
				{
					// Okay to ignore.  A tag with no leading space
					// Does not disable normalization
					extraSpaceText = null;
				}
			}
			// The lead line has no leading space
			else
			{
				// If the current line does have leading space
				if (spaceText != null)
				{
					// Grab the extra space
					extraSpaceText = spaceText;

					// If the extra space starts with the shortest extra space
					if (extraSpaceText.startsWith(shortestExtraSpaceText) == true)
					{
						// Remove the shortest extra space
						extraSpaceText = extraSpaceText.substring(shortestExtraSpaceText.length());
					}
				}
				else
				{
					extraSpaceText = null;
				}
			}

			if	(
					(extraSpaceText != null)
				)
			{
				LeadingSpaceTag newLeadingSpace;

				if (leadSpaceText != null)
				{
					newLeadingSpace = new LeadingSpaceTag(leadSpaceText + extraSpaceText);
				}
				else
				{
					newLeadingSpace = new LeadingSpaceTag(extraSpaceText);
				}

				if (type(0) == TagType.LEADING_SPACE)
				{
					set(0, newLeadingSpace);
				}
				else
				{
					add(0, newLeadingSpace);
				}
			}
		} // for

		this.line = oldLine;
	}

	/**
	 * normalize2
	 * @param lineList
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void normalize2(final List<List<Tag>> lineList)
	{
		assert lineList.size() > 0;

		List<Tag>oldLine = this.line;

		boolean normalizeDisabled = false;

		String shortestLeadingSpaceText = null;

		// Cycle through each line
		for (int i = 0, n = lineList.size(); normalizeDisabled == false && i < n; ++i)
		{
			line = lineList.get(i);

			// Skip first and last tags
			if (i == 0 || i == lineList.size() - 1 || isSpace_AltBlock_EndL() || isAltBlock_EndL())
			{
				continue;
			}

			assert (i != 0 && i != lineList.size() - 1);

			LeadingSpaceTag spaceTag  = type(0) == TagType.LEADING_SPACE ? (LeadingSpaceTag)tag(0) : null;
			String          spaceText = spaceTag != null ? spaceTag.getText() : null;

			// If we have some leading space already
			if (shortestLeadingSpaceText != null)
			{
				// If the current line has leading space
				if (spaceText != null)
				{
					if	(
							(spaceText.length() > shortestLeadingSpaceText.length())
						&&	(spaceText.startsWith(shortestLeadingSpaceText) == true)
						)
					{
						; // nothing to do
					}
					else
					if	(
							(shortestLeadingSpaceText.length() > spaceText.length())
						&&	(shortestLeadingSpaceText.startsWith(spaceText) == true)
						)
					{
						shortestLeadingSpaceText = spaceText;
					}
					else
					if	(
							(spaceText.length() == shortestLeadingSpaceText.length())
						&&	(spaceText.equals(shortestLeadingSpaceText) == true)
						)
					{
						; // nothing to do
					}
					else
					{
						shortestLeadingSpaceText = null;
						normalizeDisabled = true;
						break;
					}
				}
				// The current line does not have any leading space
				else
				{
					; // nothing to do
				}
			}
			// The lead line has no leading space
			else
			{
				shortestLeadingSpaceText = spaceText;
			}
		} // for

		// Are we still normalizing?
		if	(
				(normalizeDisabled == true)
			||	(shortestLeadingSpaceText == null)
			||	(shortestLeadingSpaceText.length() == 0)
			)
		{
			this.line = oldLine;
			return;
		}

		assert shortestLeadingSpaceText != null && shortestLeadingSpaceText.length() > 0;

		// Cycle through each line
		for (int i = 0, n = lineList.size(); i < n; ++i)
		{
			line = lineList.get(i);

			if (i == 0 || i == lineList.size() - 1 || isSpace_AltBlock_EndL() || isAltBlock_EndL())
			{
				if (type(0) == TagType.LEADING_SPACE)
				{
					remove(0);
				}
				continue;
			}

			assert (i != 0 && i != lineList.size() - 1);

			LeadingSpaceTag spaceTag  = type(0) == TagType.LEADING_SPACE ? (LeadingSpaceTag)tag(0) : null;
			String          spaceText = spaceTag != null ? spaceTag.getText() : null;

			// If the current line has leading space
			if (spaceText != null)
			{
				assert type(0) == TagType.LEADING_SPACE;
				assert spaceText.startsWith(shortestLeadingSpaceText) == true;

				// Grab the extra space
				String newLeadingSpaceText = spaceText.substring(shortestLeadingSpaceText.length());

				if (newLeadingSpaceText != null && newLeadingSpaceText.length() > 0)
				{
					LeadingSpaceTag newLeadingSpaceTag = new LeadingSpaceTag(newLeadingSpaceText);
					set(0, newLeadingSpaceTag);
				}
				else
				{
					remove(0);
				}
			}
		} // for

		this.line = oldLine;
	}

	/**
	 * normalize3
	 * @param lineList
	 */
	private void normalize3(final List<List<Tag>> lineList)
	{
		assert lineList.size() > 0;

		List<Tag>oldLine = this.line;

		boolean normalizeDisabled = false;

		String shortestLeadingSpaceText = null;

		// Cycle through each line
		for (int blockDepth = 0, i = 0, n = lineList.size(); normalizeDisabled == false && i < n; ++i)
		{
			line = lineList.get(i);

			// Skip first and last lines
			if (i == 0 || i == n - 1)
			{
				continue;
			}
			else
			// If we find a start block line
			if (isSpace_StartBlock_EndL() || isStartBlock_EndL())
			{
				blockDepth++;
				if (blockDepth > 1)
				{
					continue;
				}
				; // NOTE that we don't continue, we want to track the start tag
			}
			else
			// Are we in a sub-block?
			if (blockDepth > 0)
			{
				// If we find an end block line
				if (isSpace_EndBlock_EndL() || isEndBlock_EndL())
				{
					blockDepth--;
				}

				continue;
			}
			else
			// Otherwise, we skip end-block tags and alt-block tags
			if (isSpace_EndBlock_EndL() || isEndBlock_EndL() || isSpace_AltBlock_EndL() || isAltBlock_EndL())
			{
				continue;
			}

			assert (i != 0 && i != n - 1);

			LeadingSpaceTag spaceTag  = type(0) == TagType.LEADING_SPACE ? (LeadingSpaceTag)tag(0) : null;
			String          spaceText = spaceTag != null ? spaceTag.getText() : null;

			// If we have some leading space already
			if (shortestLeadingSpaceText != null)
			{
				// If the current line has leading space
				if (spaceText != null)
				{
					if	(
							(spaceText.length() > shortestLeadingSpaceText.length())
						&&	(spaceText.startsWith(shortestLeadingSpaceText) == true)
						)
					{
						; // nothing to do
					}
					else
					if	(
							(shortestLeadingSpaceText.length() > spaceText.length())
						&&	(shortestLeadingSpaceText.startsWith(spaceText) == true)
						)
					{
						shortestLeadingSpaceText = spaceText;
					}
					else
					if	(
							(spaceText.length() == shortestLeadingSpaceText.length())
						&&	(spaceText.equals(shortestLeadingSpaceText) == true)
						)
					{
						; // nothing to do
					}
					else
					{
						shortestLeadingSpaceText = null;
						normalizeDisabled = true;
						break;
					}
				}
				// The current line does not have any leading space
				else
				if (isEndlOrEofTag(0) == false)
				{
					shortestLeadingSpaceText = null;
					normalizeDisabled = true;
					break;
//					; // nothing to do
				}
			}
			// The lead line has no leading space
			else
			{
				shortestLeadingSpaceText = spaceText;
			}
		} // for

		// Are we still normalizing?
		if	(
				(normalizeDisabled == true)
			||	(shortestLeadingSpaceText == null)
			||	(shortestLeadingSpaceText.length() == 0)
			)
		{
			this.line = oldLine;
			return;
		}

		assert shortestLeadingSpaceText != null && shortestLeadingSpaceText.length() > 0;

		// Cycle through each line
		for (int blockDepth = 0, i = 0, n = lineList.size(); i < n; ++i)
		{
			line = lineList.get(i);

			// Skip first and last tags
			if (i == 0 || i == n - 1)
			{
				continue;
			}
			else
			if (isSpace_StartBlock_EndL() || isStartBlock_EndL())
			{
				blockDepth++;
				if (blockDepth > 1)
				{
					continue;
				}
			}
			else
			if (blockDepth > 0)
			{
				if (isSpace_EndBlock_EndL() || isEndBlock_EndL())
				{
					blockDepth--;
				}

				continue;
			}
			else
			if (isSpace_EndBlock_EndL() || isEndBlock_EndL() || isSpace_AltBlock_EndL() || isAltBlock_EndL())
			{
				continue;
			}

			assert (i != 0 && i != n - 1);

			LeadingSpaceTag spaceTag  = type(0) == TagType.LEADING_SPACE ? (LeadingSpaceTag)tag(0) : null;
			String          spaceText = spaceTag != null ? spaceTag.getText() : null;

			// If the current line has leading space
			if (spaceText != null)
			{
				assert type(0) == TagType.LEADING_SPACE;
				assert spaceText.startsWith(shortestLeadingSpaceText) == true;

				// Grab the extra space
				String newLeadingSpaceText = spaceText.substring(shortestLeadingSpaceText.length());

				if (newLeadingSpaceText != null && newLeadingSpaceText.length() > 0)
				{
					LeadingSpaceTag newLeadingSpaceTag = new LeadingSpaceTag(newLeadingSpaceText);
					set(0, newLeadingSpaceTag);
				}
				else
				{
					remove(0);
				}
			}
		} // for

		this.line = oldLine;
	}
}
