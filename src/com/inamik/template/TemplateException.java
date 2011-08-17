/*
 * $Id: TemplateException.java,v 1.1 2006-08-12 01:44:56 dave Exp $
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
 * TemplateException
 * Created on Aug 11, 2006
 * @author Dave
 */
public class TemplateException extends Exception
{
	/** serialVersionUID */
	private static final long serialVersionUID = 9053449827027810742L;

	private String filename = null;
	private int    line     = 0;
	private int    column   = 0;

	/**
	 * Constructor
	 */
	public TemplateException()
	{
		super();
	}

	/**
	 * Constructor
	 */
	public TemplateException(String message)
	{
		super(message);
	}

	/**
	 * Constructor
	 */
	public TemplateException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructor
	 */
	public TemplateException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * getColumn
	 * @return Returns the column.
	 */
	public final int getColumn()
	{
		return this.column;
	}

	/**
	 * setColumn
	 * @param column The column to set.
	 */
	public final void setColumn(int column)
	{
		this.column = column;
	}

	/**
	 * getFilename
	 * @return Returns the filename.
	 */
	public final String getFilename()
	{
		return this.filename;
	}

	/**
	 * setFilename
	 * @param filename The filename to set.
	 */
	public final void setFilename(String filename)
	{
		this.filename = filename;
	}

	/**
	 * getLine
	 * @return Returns the line.
	 */
	public final int getLine()
	{
		return this.line;
	}

	/**
	 * setLine
	 * @param line The line to set.
	 */
	public final void setLine(int line)
	{
		this.line = line;
	}

}
