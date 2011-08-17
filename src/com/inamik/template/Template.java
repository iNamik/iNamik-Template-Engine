/*
 * $Id: Template.java,v 1.5 2006-08-21 03:07:12 dave Exp $
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

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Template - Represents a compiled template.
 * <p>
 * Created on Jul 28, 2006
 * @author Dave
 * @see com.inamik.template.TemplateEngine
 */
public final class Template
{
	private PrintWriter                   out       = null;
	private TemplateProcessor             processor = null;
	private TemplateContext               context   = null;
	private Map<String, TemplateVariable> variables = new HashMap<String, TemplateVariable>();
	private Map<String, TemplateMacro>    macros    = new HashMap<String, TemplateMacro>();

	/**
	 * Constructor (private
	 */
	private Template() {}

	/**
	 * Constructor (Package-Private)
	 */
	Template(final TemplateProcessor processor, final TemplateContext context)
	{
		super();

		this.processor = processor;
		this.context   = context;
	}

	/**
	 * addVariable w/String, TemplateVariable - Adds a variable that can be
	 * accessed by the template during process().
	 * <p>
	 * As a convenience, Template remembers variables that have been set
	 * and will automatically re-add them to the context in each call to
	 * process().
	 * <p>
	 * NOTE: Remember to call reset() to reset the context before
	 * each subsequent call to process()
	 *
	 * @param name The name of the variable.
	 * @param value The variable to add
	 * @see #process()
	 * @see #reset()
	 * @see TemplateVariable
	 */
	public void addVariable(final String name, final TemplateVariable value)
	{
		variables.put(name, value);
	}

	/**
	 * addVariable w/String, Object - Adds a variable that can be
	 * accessed by the template during process().
	 * <p>
	 * This is a convenience method for adding variables without have
	 * to deal with TemplateVariable.newInstance(). It is eqvuivelent to the
	 * following:
	 * <p>
	 * <code>addVariable(name, TemplateVariable.newInstance(value));</code>
	 * <p>
	 * As a convenience, Template remembers variables that have been set
	 * and will automatically re-add them to the context in each call to
	 * process().
	 * <p>
	 * NOTE: Remember to call reset() to reset the context before
	 * each subsequent call to process()
	 *
	 * @param name The name of the variable.
	 * @param value The variable to add
	 * @see #process()
	 * @see #reset()
	 * @see TemplateVariable
	 */
	public void addVariable(final String name, final Object value)
	{
		addVariable(name, TemplateVariable.newInstance(value));
	}

	/**
	 * addMacro - Adds a macro that can be accessed by the template during
	 * process().
	 * <p>
	 * As a convenience, Template remembers macros that have been set
	 * and will automatically re-add them to the context in each call to
	 * process().
	 * <p>
	 * NOTE: Remember to call reset() to reset the context before
	 * each subsequent call to process()
	 *
	 * @param name The name of the macro.
	 * @param macro The macro to add
	 * @see #process()
	 * @see #reset()
	 * @see TemplateMacro
	 */
	public void addMacro(final String name, final TemplateMacro macro)
	{
		macros.put(name, macro);
	}

	/**
	 * setOut w/PrintWriter - Sets the PrintWriter that should receive the
	 * output from the processed template.
	 * <p>
	 * If no PrintWriter is set before the call to process(), then the Template
	 * will default to System.out.
	 *
	 * @param out The PrintWriter that should receive the output from the
	 *        processed template.
	 *
	 * @see PrintWriter
	 * @see System#out
	 */
	public void setOut(final PrintWriter out)
	{
		this.out = out;
	}

	/**
	 * setOut w/PrintStream - Sets the PrintStream that should receive the
	 * output from the processed template.
	 * <p>
	 * This is a convenience method for setting the output to a PrintStream
	 * and is equivelent to the following:
	 * <p>
	 * <code>setOut(new PrintWriter(out))</code>
	 * <p>
	 *
	 * @param out The PrintStream that should receive the output from the
	 *        processed template.
	 *
	 * @see #setOut(PrintWriter)
	 * @see PrintStream
	 */
	public void setOut(final PrintStream out)
	{
		setOut(new PrintWriter(out));
	}

	/**
	 * reset - Reset the context.  If you want to process a template multiple
	 * times, you should call reset() between calls to process().
	 * </p>
	 * You do not need to call reset() before your FIRST call to process().
	 * <p>
	 * You do not have to re-add any variables or macros defined with
	 * addVariable() or addMacro().  They will be re-added to the context
	 * during process()
	 *
	 * @see #addVariable(String, TemplateVariable)
	 * @see #addMacro(String, TemplateMacro)
	 */
	public void reset()
	{
		context.reset();
	}

	/**
	 * process - Process the template. Any defined variables or macros are
	 * added to the context and output is sent to the specified PrintWriter,
	 * or System.out if one is not defined.
	 *
	 * @throws TemplateException
	 */
	public void process() throws TemplateException
	{
		// Save printBuffer from context
		TemplatePrintBuffer oldPrintBuffer = context.getPrintBuffer();

		TemplatePrintBuffer printBuffer = null;

		// If this template does not have an out defined
		if (out == null)
		{
			// If the context does not already have a printbuffer
			if (oldPrintBuffer == null)
			{
				// Then we'll use a default printbuffer
				setOut(System.out);
				printBuffer = new TemplatePrintBuffer(out);
			}
		}
		// The template does have an out defined
		else
		{
			// We'll use the template's out
			printBuffer = new TemplatePrintBuffer(out);
		}

		// If we defined an overriding printBuffer
		if (printBuffer != null)
		{
			context.setPrintBuffer(printBuffer);
		}

		if (variables.size() > 0)
		{
			context.addVariables(variables);
		}

		processor.process(context);

		// If we used our own printBuffer, then take it back
		if (printBuffer != null)
		{
			context.setPrintBuffer(oldPrintBuffer);
		}
	}
}
