/*
 * $Id: TemplateBodyContent.java,v 1.4 2008-02-22 03:02:06 dave Exp $
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
import java.util.Iterator;
import java.util.List;

/**
 * TemplateBodyContent - A container for the processed body of a body action
 * tag or captured output from the TemplatePrintBuffer.
 * <p>
 * Used when an action tag has parmType="body" or parmType="body-alt" and has
 * bodyContent="template" or bodyContent="text".
 * <p>
 * The template engine will pass the body of your action into
 * <code>afterBody(TemplateBodyTag)</code>.
 * <p>
 * Created on Jul 10, 2006
 * @author Dave
 */
public final class TemplateBodyContent implements Iterable<com.inamik.template.TemplateBodyContent.Line>
{
	private final List<Line> bodyContent;

	/**
	 * Constructor w/List<Line>
	 * Makes a copy of the supplied body content so as to stay immutable.
	 * @param bodyContent The body content
	 */
	public TemplateBodyContent(final List<Line> bodyContent)
	{
		super();

		if (bodyContent == null)
		{
			throw new NullPointerException("bodyContent");
		}

		this.bodyContent = new ArrayList<Line>(bodyContent);
	}

	/**
	 * empty - Determine if the body content is empty
	 * @return true if the body content is empty, false otherwise
	 */
	public boolean empty()
	{
		return bodyContent.size() == 0;
	}

	boolean inline()
	{
		return (bodyContent.size() == 1 && bodyContent.get(0).hasNewLine() == false);
	}

	/**
	 * iterator - Iterate over the body content
	 * @return an iterator
	 */
	public Iterator<Line> iterator()
	{
		return new LineListIterator(bodyContent);
	}

	/**
	 * toString - retrieve the body content as a String.
	 * @return the body content as a String
	 */
	@Override
	public String toString()
	{
		final StringBuffer buffer = new StringBuffer();

		for (final Line line: bodyContent)
		{
			buffer.append(line.toString());
		}

		return buffer.toString();
	}

	/**
	 * LineListIterator (private)
	 */
	private static final class LineListIterator implements Iterator<Line>
	{
		private Iterator<Line> listIterator;

		public LineListIterator(final List<Line> lineList)
		{
			super();

			this.listIterator = lineList.iterator();
		}

		public boolean hasNext()
		{
			return listIterator.hasNext();
		}

		public Line next()
		{
			return listIterator.next();
		}

		public void remove()
		{
			throw new UnsupportedOperationException("remove");
		}
	}

	/**
	 * Line - Represents one line of captured body content
	 */
	public static final class Line
	{
		private final String line;
		private boolean hasNewLine = false;

		/**
		 * Constructor w/String, boolean
		 * @param line The portion of the line preceeding the endl
		 * @param hasNewLine Specifies if the line ends with an endl
		 */
		public Line(final String line, final boolean hasNewLine)
		{
			super();

			if (line == null)
			{
				throw new NullPointerException("line");
			}

			this.line = line;

			this.hasNewLine = hasNewLine;
		}

		/**
		 * getLine - Get the line content
		 * @return the line content
		 */
		public String getLine()
		{
			return this.line;
		}

		/**
		 * hasNewLine - Determine if the line ends with an endl
		 * @return true if the line ends with an endl, false otherwise
		 */
		public boolean hasNewLine()
		{
			return this.hasNewLine;
		}

		@Override
		public String toString()
		{
			final StringBuffer buffer= new StringBuffer();

			buffer.append(line);

			if (hasNewLine)
			{
				buffer.append('\n');
			}

			return buffer.toString();
		}
	}
}
