/*
 * $Id: VariableIdToken.java,v 1.5 2006-08-14 22:31:00 dave Exp $
 *
 * iNamik Template Engine
 * Copyright (C) 2003-2006 David Farrell (davidpfarrell@yahoo.com)
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
package com.inamik.template.tokens;

import java.io.Serializable;


/**
 * VariableIdToken
 * Created on Apr 6, 2003
 */
public class VariableIdToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = 4876529790453309471L;
	private IdToken id = null;

	/**
	 * Constructor w/VariableIdToken
	 */
	public VariableIdToken(IdToken id)
	{
		super();

		this.id = id;
	}

	/**
	 * getId
	 */
	public IdToken getId()
	{
		return id;
	}

	public TokenType getType()
	{
		return TokenType.VARIABLE_ID;
	}

	@Override
	public String toString()
	{
		return "$" + id.toString();
	}
}
