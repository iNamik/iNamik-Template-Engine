/*
 * $Id: AbstractTag.java,v 1.4 2006-07-31 10:07:10 davidpfarrell Exp $
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
package com.inamik.template.tags;

import java.io.Serializable;

/**
 * Tag - Abstract
 */
public abstract class AbstractTag implements Tag, Serializable
{
	private String filename = null;
	private int    line     = 0;
	private int    column   = 0;

	/**
	 * getColumn
	 * @return Returns the column.
	 */
	public int getColumn()
	{
		return this.column;
	}

	/**
	 * setColumn
	 * @param column The column to set.
	 */
	public void setColumn(int column)
	{
		this.column = column;
	}

	/**
	 * getFilename
	 * @return Returns the filename.
	 */
	public String getFilename()
	{
		return this.filename;
	}

	/**
	 * setFilename
	 * @param filename The filename to set.
	 */
	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	/**
	 * getLine
	 * @return Returns the line.
	 */
	public int getLine()
	{
		return this.line;
	}

	/**
	 * setLine
	 * @param line The line to set.
	 */
	public void setLine(int line)
	{
		this.line = line;
	}

	/**
	 * getTypeName
	 */
	public String getTypeName()
	{
		return getType().getName();
	}
}
