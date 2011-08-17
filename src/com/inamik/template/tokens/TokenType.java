/*
 * $Id: TokenType.java,v 1.5 2006-08-21 03:07:12 dave Exp $
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

/**
 * TokenType
 */
public enum TokenType
{
	ATTRIBUTE_LIST              ("Attribute-List"             ),
	ATTRIBUTE                   ("Attribute"                  ),
	BOOLEAN                     ("Boolean"                    ),
	EMPTY                       ("Empty"                      ),
	ESCAPE_CHARACTER            ("Escape-Character"           ),
	EXPRESSION_LIST             ("Expression-List"            ),
	EXPRESSION_OPERAND          ("Expression-Operand"         ),
	EXPRESSION_OPERATOR         ("Expression-Operator"        ),
	EXPRESSION                  ("Expression"                 ),
	FILTER                      ("Filter"                     ),
	FILTER_LIST                 ("Filter-List"                ),
	FQ_NAME                     ("TagName"                    ),
	FUNCTION                    ("Function"                   ),
	ID                          ("ID"                         ),
	ID_LIST                     ("ID-List"                    ),
	INDEX                       ("Index"                      ),
	NUMBER                      ("Number"                     ),
	QUOTED_STRING_ELEMENT       ("Quoted-String-Element"      ),
	QUOTED_STRING               ("Quoted-String"              ),
	TEXT                        ("Text"                       ),
	VARIABLE_EXPRESSION_ELEMENT ("Variable-Expression-Element"),
	VARIABLE_EXPRESSION         ("Variable-Expression"        ),
	VARIABLE_ID                 ("Variable-ID"                ),
	;

	private final String name;

	/**
	 * Constructor
	 */
	TokenType(final String name)
	{
		this.name = name;
	}

	/**
	 * getName
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * findByName
	 */
	public static TokenType findByName(final String name)
	{
		for (TokenType p : values())
		{
			if (p.name.equalsIgnoreCase(name))
			{
				return p;
			}
		}

		return null;
	}
}
