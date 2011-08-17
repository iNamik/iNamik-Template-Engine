/*
 * $Id: TemplateActionException.java,v 1.1 2006-08-12 01:44:56 dave Exp $
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

/**
 * TemplateActionException
 * Created on Aug 11, 2006
 * @author Dave
 */
public class TemplateActionException extends TemplateException
{

	/** serialVersionUID */
	private static final long serialVersionUID = 9129445238309877786L;

	/**
	 * Constructor
	 */
	public TemplateActionException()
	{
		super();
	}

	/**
	 * Constructor
	 */
	public TemplateActionException(String message)
	{
		super(message);
	}

	/**
	 * Constructor
	 */
	public TemplateActionException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructor
	 */
	public TemplateActionException(Throwable cause)
	{
		super(cause);
	}

}
