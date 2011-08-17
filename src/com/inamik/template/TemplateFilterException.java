/*
 * $Id: TemplateFilterException.java,v 1.1 2006-08-21 03:07:12 dave Exp $
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
 * TemplateFilterException
 * Created on Aug 20, 2006
 * @author Dave
 */
public class TemplateFilterException extends TemplateFunctionException
{

	/** serialVersionUID */
	private static final long serialVersionUID = -7193161704152166957L;

	/**
	 * Constructor
	 */
	public TemplateFilterException()
	{
		super();
	}

	/**
	 * Constructor
	 */
	public TemplateFilterException(String message)
	{
		super(message);
	}

	/**
	 * Constructor
	 */
	public TemplateFilterException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructor
	 */
	public TemplateFilterException(Throwable cause)
	{
		super(cause);
	}

}
