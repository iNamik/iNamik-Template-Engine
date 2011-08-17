/*
 * $Id: EscapeCharacterToken.java,v 1.7 2006-08-14 22:31:00 dave Exp $
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
package com.inamik.template.tokens;

import java.io.Serializable;

/**
 * EscapeCharacterToken
 */
public class EscapeCharacterToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -26456475727113048L;

	public static enum EscapeCode
	{
		TAB     ("t", "\t"),
		NEWLINE ("n", "\n");

		private final String code;
		private final String sequence;

		EscapeCode(final String code, final String sequence) { this.code = code; this.sequence = sequence;  }
		public String getCode() { return this.code; }
		public String getSequence() { return this.sequence; }
		public static EscapeCode findByCode(final String name)
		{
			for (EscapeCode e : values())
			{
				if (e.code.equalsIgnoreCase(name))
				{
					return e;
				}
			}

			return null;
		}
	}

	private String text = null;

	/**
	 * Constructor
	 */
	public EscapeCharacterToken(String text)
	{
		super();

		this.text = text;
	}

	/**
	 * getText
	 */
	public String getText()
	{
		return text;
	}

	public TokenType getType()
	{
		return TokenType.ESCAPE_CHARACTER;
	}

	@Override
	public String toString()
	{
		return "\\" + text;
	}
}
