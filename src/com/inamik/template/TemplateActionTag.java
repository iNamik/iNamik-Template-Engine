/*
 * $Id: TemplateActionTag.java,v 1.6 2006-08-21 03:07:12 dave Exp $
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
 * TemplateActionTag - Action tags are the heart of the iNamik Template Engine.
 * <p>
 * It is recommended that your own actions extend AbstractTemplateActionTag as
 * that class implements default versions of all 5 execute methods and all 3
 * afterBody methods.
 * <p>
 * Lifecycle - Actions have the following lifecycle:
 * <p>
 * init()
 * <br>
 * [
 * <br>
 * &nbsp;&nbsp; setContext()
 * <br>
 * &nbsp;&nbsp; execute()
 * <br>
 * &nbsp;&nbsp; afterBody()
 * <br>
 * &nbsp;&nbsp; reset()
 * <br>
 * ]*
 * <br>
 * destroy()
 * <p>
 * Created on Jul 7, 2006
 * @author Dave
 * @see com.inamik.template.TemplateActionConfig
 * @see com.inamik.template.AbstractTemplateActionTag
 */
public interface TemplateActionTag
{
	/**
	 * ExecuteResult - An enumeration specifying the acceptable return
	 * values for the various execute() methods
	 */
	public static enum ExecuteResult
	{
		/**
		 * The only return type for non-body action tags.
		 * <p>
		 * For body action tags, process this body and skip any body-alts.
		 */
		OK,

		/** For body action tags, skip this body and look for body-alts */
		SKIP_BODY,

		/** For body action tags, skip this body and any body-alts */
		SKIP_ALL,
	};

	/**
	 * AfterBodyResult - An enumeration specifying the accepting return
	 * values for the various afterBody() methods
	 */
	public static enum AfterBodyResult
	{
		/**
		 * The only return type for non-body action tags
		 * <p>
		 * For body action tags, end action
		 */
		OK,

		/** For body action tags, execute the tag body again */
		REPEAT_BODY,

		/** For body action tags, execute the entire action again */
		REPEAT_ACTION
	};

	/**
	 * init - Lifecycle method called after instance creation to give the
	 * action a chance to allocate any resources it needs to operate.
	 *
	 * @throws TemplateActionException
	 */
	public void init()    throws TemplateActionException;

	/**
	 * reset - Lifecycle method called after afterBody() to give the action
	 * a chance to reset its state.
	 *
	 * @throws TemplateActionException
	 */
	public void reset()   throws TemplateActionException;

	/**
	 * destroy - Lifecycle method called before the instance is removed from
	 * service to give the action a chance to free any resources it acquired
	 * during init().
	 *
	 * @throws TemplateActionException
	 */
	public void destroy() throws TemplateActionException;

	/**
	 * setContext - Lifecycle method called after reset() and before execute()
	 * to set the TemplateContext that the action will be executing in.
	 *
	 * @param context The TemplateContext
	 */
	public void setContext(final TemplateContext context);

	/**
	 * execute w/No Parms - Implement this method if your action has
	 * parmType="empty"
	 */
	public ExecuteResult execute()                                               throws TemplateActionException;
	/**
	 * execute w/TemplateVariable - Implement this method if your action has
	 * parmType="expression"
	 */
	public ExecuteResult execute(final TemplateVariable expression)              throws TemplateActionException;

	/**
	 * execute w/TemplateVariable[] - Implement this method if your action
	 * has parmType="expression-list"
	 */
	public ExecuteResult execute(final TemplateVariable[] expressionList)        throws TemplateActionException;

	/**
	 * execute w/Map<String, TemplateVariable> - Implement this method if your
	 * action has parmType="attributes"
	 */
	public ExecuteResult execute(final Map<String, TemplateVariable> attributes) throws TemplateActionException;

	/**
	 * execute w/String - Implement this method if your action has
	 * parmType="id"
	 */
	public ExecuteResult execute(final String id)                                throws TemplateActionException;

	/**
	 * execute w/String[] - Implement this method if your action has
	 * parmType="id-list"
	 */
	public ExecuteResult execute(final String[] idList)                          throws TemplateActionException;

	/**
	 * afterBody w/No Parms - Implemet this method if your action has
	 * bodyContent="empty"
	 */
	public AfterBodyResult afterBody()                                      throws TemplateActionException;

	/**
	 * afterBody w/TemplateBodyContent - Implemet this method if your action has
	 * bodyContent="template" or bodyContent="text"
	 */
	public AfterBodyResult afterBody(final TemplateBodyContent bodyContent) throws TemplateActionException;

	/**
	 * afterBody w/TemplateMacro - Implemet this method if your action has
	 * bodyContent="macro"
	 */
	public AfterBodyResult afterBody(final TemplateMacro macro)             throws TemplateActionException;
}
