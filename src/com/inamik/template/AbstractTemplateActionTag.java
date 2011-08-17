/*
 * $Id: AbstractTemplateActionTag.java,v 1.8 2006-11-21 09:34:42 dave Exp $
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

import java.util.Map;

/**
 * AbstractTemplateActionTag - Base class for action tags.
 * <p>
 * Implements default versions of all interface methods.
 * <p>
 * All of the execute() method just throw IllegalStateException, as they
 * should never be called.  Your class must override the appropriate execute()
 * method for the action's parmType.
 * <p>
 * Similarly, afterBody(TemplateMacro) also throws an IllegalStateException.
 * <p>
 * Created on Jul 9, 2006
 * @author Dave
 * @see com.inamik.template.TemplateActionTag
 */
public abstract class AbstractTemplateActionTag implements TemplateActionTag
{
//	private static final Log LOG = LogFactory.getLog(TemplateActionTag.class.getName());

	private TemplateContext context = null;

	/**
	 * Default Constructor
	 */
	public AbstractTemplateActionTag()
	{
		super();
	}

	/**
	 * setContext - Sets the TemplateContext for the action.
	 * <p>
	 * This base class manages the context for you and provides an
	 * accessor, getContext().
	 *
	 * @see TemplateActionTag#setContext(com.inamik.template.TemplateContext)
	 * @see #getContext()
	 * @see TemplateContext
	 */
	public final void setContext(final TemplateContext context)	{
//		LOG.trace("setContext()");

		if (context == null)
		{
			throw new NullPointerException("context");
		}

		this.context = context;
	}

	/**
	 * getContext (Protected) - Get the TemplateContext.
	 * @see TemplateContext
	 */
	protected final TemplateContext getContext()
	{
		if (context == null)
		{
			throw new IllegalStateException("null context");
		}

		return context;
	}

	/**
	 * init - Default implementation that does nothing.
	 * @see com.inamik.template.TemplateActionTag#init()
	 */
	@SuppressWarnings("unused")
	public void init() throws TemplateActionException
	{
//		LOG.trace("init()");
	}

	/**
	 * reset() - Default implementation that does nothing.
	 * @see com.inamik.template.TemplateActionTag#reset()
	 */
	@SuppressWarnings("unused")
	public void reset() throws TemplateActionException
	{
//		LOG.trace("reset()");
	}

	/**
	 * destroy() - Default implementation that does nothing.
	 * @see com.inamik.template.TemplateActionTag#destroy()
	 */
	@SuppressWarnings("unused")
	public void destroy() throws TemplateActionException
	{
//		LOG.trace("destroy()");
	}

	/**
	 * execute w/No Parms - throw an IllegalStateException,
	 * as it should never be called.  Your class must override the appropriate
	 * execute() method for the action's parmType.
	 *
	 * @see com.inamik.template.TemplateActionTag#execute()
	 */
	@SuppressWarnings("unused")
	public ExecuteResult execute() throws TemplateActionException
	{
		throw new IllegalStateException("Illegal call to TemplateActonTag.execute()");
	}

	/**
	 * execute w/Map - Just throws an IllegalStateException,
	 * as it should never be called.  Your class must override the appropriate
	 * execute() method for the action's parmType.
	 *
	 * @see com.inamik.template.TemplateActionTag#execute(java.util.Map)
	 */
	@SuppressWarnings("unused")
	public ExecuteResult execute(final Map<String, TemplateVariable> attributes) throws TemplateActionException
	{
		throw new IllegalStateException("Illegal call to TemplateActonTag.execute(Map<String, TemplateVariable> attributes)");
	}

	/**
	 * execute w/TemplateVariable - Just throws
	 * IllegalStateException, as it should never be called.  Your class must
	 * override the appropriate execute() method for the action's parmType.
	 *
	 * @see com.inamik.template.TemplateActionTag#execute(com.inamik.template.TemplateVariable)
	 */
	@SuppressWarnings("unused")
	public ExecuteResult execute(final TemplateVariable expression) throws TemplateActionException
	{
		throw new IllegalStateException("Illegal call to TemplateActonTag.execute(TemplateVariable expression)");
	}

	/**
	 * execute w/TemplateVariable[] - Just throws
	 * IllegalStateException, as it should never be called.  Your class must
	 * override the appropriate execute() method for the action's parmType.
	 *
	 * @see com.inamik.template.TemplateActionTag#execute(com.inamik.template.TemplateVariable[])
	 */
	@SuppressWarnings("unused")
	public ExecuteResult execute(final TemplateVariable[] expressionList) throws TemplateActionException
	{
		throw new IllegalStateException("Illegal call to TemplateActonTag.execute(TemplateVariable[] expressionList)");
	}

	/**
	 * execute w/String - Just throws IllegalStateException,
	 * as it should never be called.  Your class must override the appropriate
	 * execute() method for the action's parmType.
	 *
	 * @see com.inamik.template.TemplateActionTag#execute(java.lang.String)
	 */
	@SuppressWarnings("unused")
	public ExecuteResult execute(final String id) throws TemplateActionException
	{
		throw new IllegalStateException("Illegal call to TemplateActonTag.execute(String id)");
	}

	/**
	 * execute w/String[] - Just throws IllegalStateException,
	 * as it should never be called.  Your class must override the appropriate
	 * execute() method for the action's parmType.
	 *
	 * @see com.inamik.template.TemplateActionTag#execute(java.lang.String[])
	 */
	@SuppressWarnings("unused")
	public ExecuteResult execute(final String[] idList) throws TemplateActionException
	{
		throw new IllegalStateException("Illegal call to TemplateActonTag.execute(String[] idList)");
	}

	/**
	 * afterBody w/No Parm - Default implementation that just
	 * returns AfterBodyResult.OK
	 *
	 * @see com.inamik.template.TemplateActionTag#afterBody()
	 */
	@SuppressWarnings("unused")
	public AfterBodyResult afterBody() throws TemplateActionException
	{
//		LOG.trace("afterBody()");

		return AfterBodyResult.OK;
	}

	/**
	 * afterBody w/TemplateBody Content - Default implementation
	 * that just prints the body content and returns AfterBodyResult.OK
	 *
	 * @see com.inamik.template.TemplateActionTag#afterBody(com.inamik.template.TemplateBodyContent)
	 */
	@SuppressWarnings("unused")
	public AfterBodyResult afterBody(final TemplateBodyContent bodyContent) throws TemplateActionException
	{
//		LOG.trace("afterBody(bodyContent)");
		
		context.getOut().print(bodyContent);

		return AfterBodyResult.OK;
	}

	/**
	 * afterBody w/TemplateMacro - Just throws
	 * IllegalStateException, as it should never be called.
	 * <p>
	 * If your action has parmType="macro", then you must want to do something
	 * with that macro, so do it.
	 *
	 * @see com.inamik.template.TemplateActionTag#afterBody(com.inamik.template.TemplateMacro)
	 */
	@SuppressWarnings("unused")
	public AfterBodyResult afterBody(final TemplateMacro macro) throws TemplateActionException
	{
//		LOG.trace("afterBody(macro)");

		throw new IllegalStateException("Illegal call to TemplateActonTag.afterBody(TemplateMacro macro)");
	}
}
