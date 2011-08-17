/*
 * $Id: TemplatePrintBuffer.java,v 1.5 2008-02-22 03:02:06 dave Exp $
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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.inamik.template.TemplateBodyContent.Line;


/**
 * TemplatePrintBuffer
 * Created on Jul 22, 2006
 * @author Dave
 */
public final class TemplatePrintBuffer
{
	private final Stack<LineCapturePrinter> captureStack = new Stack<LineCapturePrinter>();

	private final PrintWriter printWriter;
	private final Printer out;

	/**
	 * Constructor
	 */
	public TemplatePrintBuffer(final PrintWriter printWriter)
	{
		super();

		if (printWriter == null)
		{
			throw new NullPointerException("printWriter");
		}

		this.printWriter = printWriter;
		this.out = new PrintWriterPrinter(printWriter);
	}

	/**
	 * startCapture (Package-Private)
	 */
	void startCapture()
	{
		getPrinter().startCapture();

		LineCapturePrinter capture = new LineCapturePrinter(this.printWriter);
		captureStack.push(capture);
	}

	/**
	 * stopCature (Package-Private)
	 */
	TemplateBodyContent stopCapture()
	{
		if (captureStack.empty() == true)
		{
			throw new IllegalStateException("stopCature() called with no matchin startCapture() call");
		}

		final TemplateBodyContent bodyContent = captureStack.pop().getContent();

		getPrinter().stopCapture(bodyContent);

		return bodyContent;
	}

	/**
	 * flush
	 */
	public void flush()
	{
		getPrinter().flush();
	}

	/**
	 * getPrinter (Private)
	 */
	private Printer getPrinter()
	{
		final Printer printer;

		if (captureStack.empty() == true)
		{
			printer = out;
		}
		else
		{
			printer = captureStack.peek();
		}

		return printer;
	}

	/**
	 * println
	 */
	public synchronized void println()
	{
		getPrinter().println();
	}

	/**
	 * print
	 */
	public synchronized void print(final String string)
	{
		getPrinter().print(string);
	}

	/**
	 * println
	 */
	public void println(final String string)
	{
		final Printer printer = getPrinter();

		printer.print(string);

		printer.println();
	}

	/**
	 * printLeadingSpace (Package-Private)
	 */
	void printLeadingSpace(final String leadingSpace)
	{
		if (leadingSpace == null)
		{
			throw new NullPointerException("leadingSpace");
		}

		getPrinter().printLeadingSpace(leadingSpace);
	}

	/**
	 * print
	 */
	public void print(final TemplateBodyContent bodyContent)
	{
		if (bodyContent == null)
		{
			throw new NullPointerException("bodyContent");
		}

		getPrinter().print(bodyContent);
	}

	/**
	 * beginBlockActionTag (Package-Private)
	 */
	void beginBlockActionTag(final boolean inline)
	{
		getPrinter().beginBlockActionTag(inline);
	}

	/**
	 * endBlockActionTag (Package-Private)
	 */
	void endBlockActionTag()
	{
		getPrinter().endBlockActionTag();
	}

	/**
	 * beginActionTag (Package-Private)
	 */
	void beginActionTag()
	{
		getPrinter().beginActionTag();
	}

	/**
	 * endActionTag (Package-Private)
	 */
	void endActionTag()
	{
		getPrinter().endActionTag();
	}

	/**
	 * Abstract Printer
	 */
	private static abstract class Printer
	{
		private final boolean debug = false;

		private String  leadingSpace  = null;
		private boolean lineStarted   = false;

		private boolean ignoreNewLine      = false;
		private boolean ignoreLeadingSpace = false;

		private boolean actionTagStarted      = false;
		private boolean blockActionTagStarted = false;
		private boolean inlineBlockActionTag  = false;
		private boolean captureStarted        = false;

		private boolean contentPrinted = false;
		private boolean newLineJustPrinted = false;

		private final PrintWriter out;

		public  Printer(PrintWriter out)
		{
			super();
			this.out = out;
		}

		protected abstract void doPrintln();
		protected abstract void doPrint(String string);

		public final void println()
		{
			// If body content was printed and ended in a newline
			// Then ignore a newline that immediately follows
			if (ignoreNewLine == true)
			{
				assert lineStarted == false;

				ignoreNewLine = false;
				newLineJustPrinted = false;

				if (ignoreLeadingSpace == true)
				{
					leadingSpace = null;
					ignoreLeadingSpace = false;
				}

				return;
			}

			doPrintln();
			flush();

			if (blockActionTagStarted == true)
			{

			}
			else
			{
				// Reset leading space
				leadingSpace = null;
			}

			// Reset line state
			lineStarted = false;

			// Notify that we have printed something
			contentPrinted = true;

			// Notify that we have printed a newline
			newLineJustPrinted = true;
		}

		public final void print(final String string)
		{
			assert string != null;

			if (string == null || string.length() == 0)
			{
				return;
			}

			ensureLineStarted();

			doPrint(string);

			// Since a newline was not printed
			// we no longer have to worry about it
			ignoreNewLine = false;

			// Notify that we have printed something
			contentPrinted = true;

			// Notify that we have not printed a newline
			newLineJustPrinted = false;
		}

		public final void println(String string)
		{
			print(string);
			println();
		}

		public final void printLeadingSpace(@SuppressWarnings("hiding") final String leadingSpace)
		{
			assert leadingSpace != null;

			if (lineStarted == true)
			{
				lineStarted = true;
			}

			assert lineStarted == false;

			// We store the leading space, but don't print
			// it yet in case it has to be ignored later
			this.leadingSpace = leadingSpace;

			// Since a newline was not printed
			// we no longer have to worry about it
			ignoreNewLine = false;

			// Notify that we have not printed a newline
			newLineJustPrinted = false;
		}

		public final void print(final TemplateBodyContent bodyContent)
		{
			assert bodyContent != null;

			if (debug)
					out.print("{p:");

			if (bodyContent.inline() == true)
			{
				print(bodyContent.iterator().next().getLine());
				return;
			}

			@SuppressWarnings("hiding")
			final String leadingSpace = this.leadingSpace;

			boolean trailingEndl = false;

			for(final Line line : bodyContent)
			{

				String sLine = line.getLine();

				if (lineStarted == false && leadingSpace != null)
				{
					printLeadingSpace(leadingSpace);
				}

				if (sLine.length() > 0)
				{
					print(line.getLine());
				}

				if (line.hasNewLine())
				{
					trailingEndl = true;
					println();
				}
				else
				{
					trailingEndl = false;
				}
			}

			if (blockActionTagStarted == true)
			{

			}
			else
			{
				if (trailingEndl == true)
				{
					ignoreNewLine = true;
				}

				this.leadingSpace = null;
			}

			if (debug)
				out.print(":p}");
		}

		public void flush()
		{
			; //
		}

		public final void startCapture()
		{
			captureStarted = true;

			if (debug)
				out.print("{c:");
		}

		public final void stopCapture(final TemplateBodyContent bodyContent)
		{
			captureStarted = false;
			if (debug)
				out.print(":c" + (bodyContent.inline() ? '1' : '0') + "}");
		}

		public final void beginBlockActionTag(final boolean inline)
		{
			blockActionTagStarted = true;
			inlineBlockActionTag = inline;
			contentPrinted = false;
			newLineJustPrinted = false;
			if (debug)
				out.print("{b" + (inline ? '1' : '0') + ":");
		}
		public final void endBlockActionTag()
		{
			blockActionTagStarted = false;
			if (debug)
				out.print(":b" + (contentPrinted ? '1' : '0') + "}");

			// If any content was printed
			if (contentPrinted)
			{
				if (newLineJustPrinted == true)
				{
					ignoreNewLine = true;
				}

				leadingSpace = null;
			}
			// No content was printed
			else
			{
				// If we're dealing with an empty inline tag
				if (inlineBlockActionTag == true)
				{
					ignoreNewLine = true;
					ignoreLeadingSpace = true;
				}
				// Dealing with an empty multi-line tag
				else
				{
					leadingSpace = null;
				}
			}
		}

		public final void beginActionTag()
		{
			actionTagStarted = true;
			contentPrinted = false;
			if (debug)
				out.print("{a:");
		}

		public final void endActionTag()
		{
			actionTagStarted = false;
			if (contentPrinted == false)
			{
// DAVE - This was commented out.
//        I Had to uncomment to get project I was working on to work.
//        I am not sure exactly why it had been commented out.
//        I'm guessing something else will break now :(
				ignoreNewLine = true;
			}
			if (debug)
				out.print(":a" + (contentPrinted ? '1' : '0') + "}");
		}

		private final void ensureLineStarted()
		{
			if (leadingSpace != null && lineStarted == false)
			{
				if (debug)
					doPrint("[");
				doPrint(leadingSpace);
				if (debug)
					doPrint("]");
				lineStarted = true;
				contentPrinted = true;
			}
		}
	}

	/**
	 * PrintWriterPrinter
	 */
	private static final class PrintWriterPrinter extends Printer
	{
		private final PrintWriter printWriter;

		public PrintWriterPrinter(final PrintWriter printWriter)
		{
			super(printWriter);

			if (printWriter == null)
			{
				throw new NullPointerException("printWriter");
			}

			this.printWriter = printWriter;
		}

		@Override
		public void flush()
		{
			printWriter.flush();
		}

		public PrintWriter getPrintWriter()
		{
			return this.printWriter;
		}

		@Override
		protected void doPrint(String string)
		{
			printWriter.print(string);
		}

		@Override
		protected void doPrintln()
		{
			printWriter.println();
		}
	}

	/**
	 * LineCapturePrinter
	 * @author Dave
	 */
	private static final class LineCapturePrinter extends Printer
	{
		private final StringBuffer currentLine = new StringBuffer();
		private final List<Line> lineList      = new ArrayList<Line>();

		public LineCapturePrinter(PrintWriter printWriter) { super(printWriter); }

		public TemplateBodyContent getContent()
		{
			// Finish off current line
			if (currentLine.length() > 0)
			{
				lineList.add(new Line(currentLine.toString(), false));
				currentLine.setLength(0);
			}

			final TemplateBodyContent content = new TemplateBodyContent(lineList);

			lineList.clear();

			return content;
		}

		@Override
		protected void doPrint(String string)
		{
			currentLine.append(string);
		}

		@Override
		protected void doPrintln()
		{
//			currentLine.append('\n');
			lineList.add(new Line(currentLine.toString(), true));
			currentLine.setLength(0);
		}
	}
}
