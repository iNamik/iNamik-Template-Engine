/*
 * $Id: TemplateContext.java,v 1.10 2006-11-21 09:34:42 dave Exp $
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

/**
 * TemplateContext - The container for the runtime-state of a template.
 * <p>
 * The context manages variables and macros and provides other
 * functions, such as those for including other templates.
 * <p>
 * Created on or before July 11, 2006
 * @author Dave
 */
public final class TemplateContext
{
	private static final int MAX_INCLUDE_COUNT = 10;

	private TemplateEngine                engine        = null;
	private TemplatePrintBuffer           printBuffer   = null;
	private Map<String, TemplateVariable> variables     = new HashMap<String, TemplateVariable>();
	private Map<String, TemplateMacro>    macros        = new HashMap<String, TemplateMacro>();
	private Stack<TemplateActionTag>      actionStack   = new Stack<TemplateActionTag>();
	private Map<String, Integer>          includeCounts = new HashMap<String, Integer>();

	/**
	 * Constructor (Package-Private)
	 */
	TemplateContext()
	{
		super();
	}

	/**
	 * setTemplateEngine (Package-Private)
	 */
	 void setTemplateEngine(final TemplateEngine engine)
	 {
	 	this.engine = engine;
	 }

	 /**
	  * getTemplateEngine - Returns the TemplateEngine associated with this
	  * context.
	  *
	  * @return The TemplateEngine associated with this context.
	  *
	  * @see TemplateEngine
	  */
	 public TemplateEngine getTemplateEngine()
	 {
		 return this.engine;
	 }

	/**
	 * getConfig (Package-Private)
	 */
	TemplateEngineConfig getConfig()
	{
		return engine.getEngineConfig();
	}

	/**
	 * setPrintBuffer (Package-Private)
	 */
	void setPrintBuffer(final TemplatePrintBuffer printBuffer)
	{
		this.printBuffer = printBuffer;
	}

	/**
	 * getPrintBuffer (Package-Private)
	 */
	TemplatePrintBuffer getPrintBuffer()
	{
		return this.printBuffer;
	}

	/**
	 * startCapture (Package-Private) - Proxy to
	 * getPrintBuffer().startCapture().
	 */
	void startCapture()
	{
		printBuffer.startCapture();
	}

	/**
	 * stopCapture (Package-Private) - Proxy to getPrintBuffer().stopCapture().
	 */
	TemplateBodyContent stopCapture()
	{
		return printBuffer.stopCapture();
	}

	/**
	 * pushAction (Package-Private)
	 */
	void pushAction(final TemplateActionTag action)
	{
		actionStack.push(action);
	}

	/**
	 * popAction (Package-Private)
	 */
	TemplateActionTag popAction()
	{
		assert actionStack.empty() == false;

		if (actionStack.empty() == true)
		{
			throw new RuntimeException("popAction() called on empty stack");
		}
		return actionStack.pop();
	}

	/**
	 * getOut - Get the current TemplatePrintBuffer for this context.
	 *
	 * @return The current TemplatePrintBuffer for this context.
	 * @see TemplatePrintBuffer
	 */
	public TemplatePrintBuffer getOut()
	{
		return printBuffer;
	}

	/**
	 * flush - Flush the current PrintWriter for this context.
	 *
	 * @see PrintWriter
	 */
	public void flush()
	{
		printBuffer.flush();
	}

	/**
	 * getParent - Get the parent action tag of the current action tag.
	 * <p>
	 * WARNING: As of this writing, this method has not been tested.
	 *
	 * @return The parent action tag of the current action tag, or
	 *         <code>null</code> if there isn't one.
	 *
	 * @see #findAncestorWithType(Class)
	 * @see TemplateActionTag
	 */
	public TemplateActionTag getParent()
	{
		if (actionStack.empty())
		{
			return null;
		}

		return actionStack.peek();
	}

	/**
	 * findAncestorWithType - Search the tag hierarchy of this context,
	 * looking for the first action tag of the specified class.
	 * <p>
	 * WARNING: As of this writing, this method has not been tested.
	 *
	 * @param clazz The class to search for.
	 * @return The first ancestor action tag of the specified class, or
	 *         <code>null</code> if one does not exist.
	 *
	 * @throws NullPointerException if <code>clazz == null</code>
	 */
	public TemplateActionTag findAncestorWithType(final Class<?> clazz)
	{
		if (clazz == null)
		{
			throw new NullPointerException("clazz");
		}

		if (actionStack.empty())
		{
			return null;
//			throw new EmptyStackException();

		}

		for (TemplateActionTag a : actionStack)
		{
			if (clazz.isAssignableFrom(a.getClass()) == true)
			{
				return a;
			}
		}

		return null;
	}

	/**
	 * addVariable - Add a variable to the context.
	 *
	 * @param name The name of the variable to add
	 * @param value The variable to add
	 * @throws NullPointerException If <code>name == null</code> or
	 *         <code>value == null</code>
	 *
	 * @see #getVariable(String)
	 * @see #variableExists(String)
	 * @see #removeVariable(String)
	 * @see #addVariables(Map)
	 * @see #clearVariables()
	 * @see TemplateVariable
	 */
	public void addVariable(final String name, final TemplateVariable value)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}
		if (value == null)
		{
			throw new NullPointerException("value");
		}

		variables.put(name, value);
	}

	/**
	 * variableExists - Test whether a variable with the specified name
	 * exists in this context.
	 * @param name The name of the variable to test for
	 * @return Whether a variable with the specified name
	 *         exists in this context.
	 * @throws NullPointerException If <code>name == null</code>
	 *
	 * @see #addVariable(String, TemplateVariable)
	 * @see #getVariable(String)
	 * @see #removeVariable(String)
	 * @see #addVariables(Map)
	 * @see #clearVariables()
	 * @see TemplateVariable
	 */
	public boolean variableExists(final String name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		return variables.containsKey(name);
	}

	/**
	 * removeVariable - Remove a variable from the context.
	 *
	 * @param name The name of the variable to remove
	 * @return The variable that was removed, or <code>null</code> if there
	 *         wasn't one under the specified name.
	 * @throws NullPointerException If <code>name == null</code>
	 *
	 * @see #addVariable(String, TemplateVariable)
	 * @see #getVariable(String)
	 * @see #variableExists(String)
	 * @see #addVariables(Map)
	 * @see #clearVariables()
	 * @see TemplateVariable
	 */
	public TemplateVariable removeVariable(final String name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		TemplateVariable v = variables.remove(name);

//		if (v == null)
//		{
//			v = TemplateVariable.NULL;
//		}

		return v;
	}

	/**
	 * addVariables - Add multiple variables to the context.
	 * <p>
	 * This is mostly here as a convenience method for the Template class,
	 * but it might come in handy for general use.
	 *
	 * @param variables - Map of variables to add.
	 * @throws NullPointerException If <code>variables == null</code>
	 *
	 * @see #addVariable(String, TemplateVariable)
	 * @see #getVariable(String)
	 * @see #variableExists(String)
	 * @see #removeVariable(String)
	 * @see #clearVariables()
	 * @see TemplateVariable
	 */
	public void addVariables(@SuppressWarnings("hiding") final Map<String, TemplateVariable> variables)
	{
		if (variables == null)
		{
			throw new NullPointerException("variables");
		}

		this.variables.putAll(variables);
	}

	/**
	 * clearVariables - Remove all variables.
	 *
	 * @see #addVariable(String, TemplateVariable)
	 * @see #getVariable(String)
	 * @see #variableExists(String)
	 * @see #removeVariable(String)
	 * @see #addVariables(Map)
	 * @see TemplateVariable
	 */
	public void clearVariables()
	{
		variables.clear();
	}

	/**
	 * getVariable - Get a variable from the context.
	 *
	 * @param name The name of the variable to get
	 * @return The variable with the specified name, or
	 *         <code>TemplateVariable.NULL</code> if there wasn't one.
	 * @throws NullPointerException If <code>name == null</code>
	 *
	 * @see #addVariable(String, TemplateVariable)
	 * @see #variableExists(String)
	 * @see #removeVariable(String)
	 * @see #addVariables(Map)
	 * @see #clearVariables()
	 * @see TemplateVariable
	 */
	public TemplateVariable getVariable(final String name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		TemplateVariable v = variables.get(name);

		if (v == null)
		{
			v = TemplateVariable.NULL;
		}

		return v;
	}

	/**
	 * addMacro - Add a macro to the context.
	 *
	 * @param name The name of the macro to add
	 * @param macro The macro to add
	 * @throws NullPointerException If <code>name == null</code> or
	 *         <code>macro == null</code>
	 *
	 * @see #getMacro(String)
	 * @see #macroExists(String)
	 * @see #removeMacro(String)
	 * @see #addMacros(Map)
	 * @see #clearMacros()
	 * @see #processMacro(TemplateMacro)
	 * @see TemplateMacro
	 */
	public void addMacro(final String name, final TemplateMacro macro)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}
		if (macro == null)
		{
			throw new NullPointerException("macro");
		}

		macros.put(name, macro);
	}

	/**
	 * macroExists - Test whether a macro with the specified name
	 * exists in this context.
	 *
	 * @param name The name of the macro to test for
	 * @return Whether a macro with the specified name
	 *         exists in this context.
	 * @throws NullPointerException If <code>name == null</code>
	 *
	 * @see #addMacro(String, TemplateMacro)
	 * @see #getMacro(String)
	 * @see #removeMacro(String)
	 * @see #addMacros(Map)
	 * @see #clearMacros()
	 * @see #processMacro(TemplateMacro)
	 * @see TemplateMacro
	 */
	public boolean macroExists(final String name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		return macros.containsKey(name);
	}

	/**
	 * removeMacro - Remove a macro from the context.
	 *
	 * @param name The name of the macro to remove
	 * @return The macro that was removed, or <code>null</code> if there wasn't
	 *         one under the specified name.
	 * @throws NullPointerException If <code>name == null</code>
	 *
	 * @see #addMacro(String, TemplateMacro)
	 * @see #getMacro(String)
	 * @see #macroExists(String)
	 * @see #addMacros(Map)
	 * @see #clearMacros()
	 * @see #processMacro(TemplateMacro)
	 * @see TemplateMacro
	 */
	public TemplateMacro removeMacro(final String name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		return macros.remove(name);
	}

	/**
	 * addMacros - Add multiple macros to the context.
	 * <p>
	 * This is mostly here as a convenience method for the Template class,
	 * but it might come in handy for general use.
	 *
	 * @param macros - Map of macros to add.
	 * @throws NullPointerException If <code>macros == null</code>
	 *
	 * @see #addMacro(String, TemplateMacro)
	 * @see #getMacro(String)
	 * @see #macroExists(String)
	 * @see #removeMacro(String)
	 * @see #clearMacros()
	 * @see #processMacro(TemplateMacro)
	 * @see TemplateMacro
	 */
	public void addMacros(@SuppressWarnings("hiding") final Map<String, TemplateMacro> macros)
	{
		if (macros == null)
		{
			throw new NullPointerException("macros");
		}

		this.macros.putAll(macros);
	}

	/**
	 * clearMacros - Remove all macros.
	 *
	 * @see #addMacro(String, TemplateMacro)
	 * @see #getMacro(String)
	 * @see #macroExists(String)
	 * @see #removeMacro(String)
	 * @see #addMacros(Map)
	 * @see #processMacro(TemplateMacro)
	 * @see TemplateMacro
	 */
	public void clearMacros()
	{
		macros.clear();
	}

	/**
	 * getMacro - Get a macro from the context.
	 *
	 * @param name The name of the macro to get
	 * @return The macro with the specified name, or <code>null</code> if
	 *         there wasn't one.
	 * @throws NullPointerException If <code>name == null</code>
	 *
	 * @see #addMacro(String, TemplateMacro)
	 * @see #macroExists(String)
	 * @see #removeMacro(String)
	 * @see #addMacros(Map)
	 * @see #clearMacros()
	 * @see #processMacro(TemplateMacro)
	 * @see TemplateMacro
	 */
	public TemplateMacro getMacro(final String name)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		return macros.get(name);
	}

	/**
	 * processMacro - Process a macro within this context.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>processMacro(macro, false)</code>
	 *
	 * @throws TemplateException
	 * @throws NullPointerException If <code>macro == null</code>
	 *
	 * @see #addMacro(String, TemplateMacro)
	 * @see #getMacro(String)
	 * @see #macroExists(String)
	 * @see #removeMacro(String)
	 * @see #addMacros(Map)
	 * @see #clearMacros()
	 * @see TemplateMacro
	 */
	public void processMacro(final TemplateMacro macro) throws TemplateException
	{
		processMacro(macro, false);
	}

	/**
	 * processMacro - Process a macro within this context.
	 * <p>
	 * This method can optionally capture the output generated by the processed
	 * macro and return it to the caller.
	 *
	 * @param macro The macro to process
	 * @param capture Whether or not to capture the macro output.
	 *
	 * @throws TemplateException
	 * @throws NullPointerException If <code>macro == null</code>
	 *
	 * @see #addMacro(String, TemplateMacro)
	 * @see #getMacro(String)
	 * @see #macroExists(String)
	 * @see #removeMacro(String)
	 * @see #addMacros(Map)
	 * @see #clearMacros()
	 * @see TemplateMacro
	 */
	public TemplateBodyContent processMacro(final TemplateMacro macro, final boolean capture) throws TemplateException
	{
		if (macro == null)
		{
			throw new NullPointerException("macro");
		}

		TemplateBodyContent result = null;

//		if (capture)
		{
			startCapture();
		}

		macro.process(this);

//		if (capture)
		{
			result = stopCapture();
		}

		if (capture == false)
		{
			getOut().print(result);
			result = null;
		}

		return result;
	}

	/**
	 * includeTemplateFromFile - Load a template from a file and process it
	 * within the current context.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>includeTemplateFromFile, filename, false)</code>
	 *
	 * @param filename The filename of the template to load
	 *
	 * @throws TemplateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>filename == null</code>
	 *
	 * @see #includeTemplateFromFile(String, boolean)
	 */
	public void includeTemplateFromFile(final String filename) throws TemplateException, FileNotFoundException, IOException
	{
		includeTemplateFromFile(filename, false);
	}

	/**
	 * includeTemplateFromFile - Load a template from a file and process it
	 * within the current context.
	 * <p>
	 * This method can optionally capture the output generated by the included
	 * template and return it to the caller.
	 *
	 * @param filename The filename of the template to load
	 * @param capture Whether or not to capture the template output.
	 *
	 * @return If <code>capture == true</code>, returns any generated output
	 *         from the included template, otherwise returns <code>null</code>
	 *
	 * @throws TemplateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>filename == null</code>
	 *
	 * @see TemplateEngine#loadTemplateFromFile(String)
	 */
	public TemplateBodyContent includeTemplateFromFile(final String filename, final boolean capture) throws TemplateException, FileNotFoundException, IOException
	{
		if (filename == null)
		{
			throw new NullPointerException("filename");
		}

		Template template = engine.loadTemplateFromFile(filename, this);

		return includeTemplate(template, filename, capture);
	}

	/**
	 * includeTemplateFromResource - Load a template from the classpath and
	 * process it within the current context.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>includeTemplateFromResource, resourceName, false)</code>
	 *
	 * @param resourceName The name of the template to load from the classpath.
	 *
	 * @throws TemplateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>resourceName == null</code>
	 *
	 * @see #includeTemplateFromResource(String, boolean)
	 */
	public void includeTemplateFromResource(final String resourceName) throws TemplateException, FileNotFoundException, IOException
	{
		includeTemplateFromResource(resourceName, false);
	}

	/**
	 * includeTemplateFromResource - Load a template from the classpath and
	 * process it within the current context.
	 * <p>
	 * This method can optionally capture the output generated by the included
	 * template and return it to the caller.
	 *
	 * @param resourceName The name of the template to load from the classpath.
	 * @param capture Whether or not to capture the template output.
	 *
	 * @return If <code>capture == true</code>, returns any generated output
	 *         from the included template, otherwise returns <code>null</code>
	 *
	 * @throws TemplateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>resourceName == null</code>
	 *
	 * @see TemplateEngine#loadTemplateFromResource(String)
	 */
	public TemplateBodyContent includeTemplateFromResource(final String resourceName, final boolean capture) throws TemplateException, FileNotFoundException, IOException
	{
		if (resourceName == null)
		{
			throw new NullPointerException("resourceName");
		}

		Template template = engine.loadTemplateFromResource(resourceName, this);

		return includeTemplate(template, resourceName, capture);
	}

	/**
	 * includeTemplateFromURL - Load a template from a URL and
	 * process it within the current context.
	 * <p>
	 * This is a convenience method and is equivelent to:
	 * <p>
	 * &nbsp;&nbsp; <code>includeTemplateFromURL(url, false)</code>
	 *
	 * @param url The URL of the template to load
	 *
	 * @throws TemplateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>url == null</code>
	 *
	 * @see #includeTemplateFromURL(URL, boolean)
	 */
	public void includeTemplateFromURL(final URL url) throws TemplateException, FileNotFoundException, IOException
	{
		includeTemplateFromURL(url, false);
	}

	/**
	 * includeTemplateFromURL - Load a template from a URL and
	 * process it within the current context.
	 * <p>
	 * This method can optionally capture the output generated by the included
	 * template and return it to the caller.
	 *
	 * @param url The URL of the template to load.
	 * @param capture Whether or not to capture the template output.
	 *
	 * @return If <code>capture == true</code>, returns any generated output
	 *         from the included template, otherwise returns <code>null</code>
	 *
	 * @throws TemplateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NullPointerException If <code>url == null</code>
	 *
	 * @see TemplateEngine#loadTemplateFromURL(URL)
	 */
	public TemplateBodyContent includeTemplateFromURL(final URL url, final boolean capture) throws TemplateException, FileNotFoundException, IOException
	{
		if (url == null)
		{
			throw new NullPointerException("url");
		}

		Template template = engine.loadTemplateFromURL(url, this);

		return includeTemplate(template, url.toExternalForm(), capture);
	}

	/**
	 * getFileFromTemplateRoot - Allows you to search for a file off of the
	 * template root.
	 *
	 * @param filename The name of the file to search for, relative to the
	 *        template root
	 * @throws FileNotFoundException
	 * @throws NullPointerException If <code>filename == null</code>
	 *
	 * @see TemplateEngine#getFileFromTemplateRoot(String)
	 * @see TemplateEngine#setTemplateRoot(String)
	 */
	public File getFileFromTemplateRoot(final String filename) throws FileNotFoundException
	{
		if (filename == null)
		{
			throw new NullPointerException("filename");
		}

		return engine.getFileFromTemplateRoot(filename);
	}

	/**
	 * includeTemplate w/Template, String (Private)
	 *
	 * @throws TemplateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private TemplateBodyContent includeTemplate(final Template template, final String name, final boolean capture) throws TemplateException
	{
		assert template != null;
		assert name != null;

		TemplateBodyContent result = null;

		if (includeCounts.containsKey(name))
		{
			int i = includeCounts.get(name).intValue();

			i++;

			if (i >= MAX_INCLUDE_COUNT)
			{
				getOut().println("Max include count reached for template: " + name);
				return null;
			}

			includeCounts.put(name, new Integer(i));
		}
		else
		{
			includeCounts.put(name, new Integer(1));
		}

//		if (capture)
//		{
			startCapture();
//		}

		template.process();

//		if (capture)
//		{
			result = stopCapture();
//		}

		if (capture == false)
		{
			getOut().print(result);
			result = null;
		}

		assert includeCounts.containsKey(name) == true;

		int i = includeCounts.get(name).intValue();

		i--;

		if (i > 0)
		{
			includeCounts.put(name, new Integer(i));
		}
		else
		{
			includeCounts.remove(name);
		}

		return result;
	}

	/**
	 * reset (Package-Private)
	 */
	void reset()
	{
		actionStack.clear();
		includeCounts.clear();
		macros.clear();
		variables.clear();
	}
}
