/*
 * $Id: TextBodyLexer.g,v 1.3 2006-08-22 03:28:14 dave Exp $
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
header
{
	package com.inamik.template.antlr;

	import antlr.TokenStreamSelector;

	import com.inamik.template.TemplateEngineConfig;

}

{@SuppressWarnings("all")}
class TextBodyLexer extends Lexer;
options
{
	k=2; // For two-char open tags
	exportVocab=TextBodyLexer;
	charVocabulary = '\u0000'..'\u00ff';
//	defaultErrorHandler=false;
}
tokens
{
	// Allows us to reference the token type by name
	TEXT; OPEN_TAG; CLOSE_TAG;
}

/*
 * Class Info
 */
{
	TemplateEngineConfig engineConfig = null;
	public void setTemplateEngineConfig(final TemplateEngineConfig engineConfig)
	{
		this.engineConfig = engineConfig;
	}

	private final boolean isDebug()
	{
		return engineConfig.isDebug();
	}

	TokenStreamSelector selector = null;

	public void setSelector(TokenStreamSelector s)
	{
		selector = s;
	}

	private void print(String s)
	{
		System.out.println("TextBodyLexer : " + s);
	}
}


/*
 * TEXT
 */
TEXT :
	// Accept all printable ascii chars except
	// '/' (\u002f)
	// '<' (\u003c)
	// '{' (\u007b)
	// '}' (\u007d)
//	('\u0021'..'\u002e'|'\u0030'..'\u003b'|'\u003d'..'\u007a'|'\u007c'|'\u007e'..'\u007f')+
	(
	~( '\u0000'..'\u0020'
	 | 'a'..'z' | 'A'..'Z' | '0'..'9' | '_'
	 | '/' | '<' | '%' | '>' | '{' | '}' | ':'
	 | '\u0080'..'\u00ff'
	 )
	)+
	{
		// ** DEBUG **
		if (isDebug())
		{
			print("TEXT - " + $getText);
		}
	}
	;

/*
 * Open Tag 1
 * {{
 */
OPEN_TAG1 :
	{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY}?
	(
		"{{"
		{
			$setType(OPEN_TAG);

			if (isDebug())
			{
				print("OPEN_TAG1");
			}
		}
	)
	;

/*
 * Close Tag 1
 * }}
 */
CLOSE_TAG1 :
	{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY}?
	(
		"}}"
		{
			$setType(CLOSE_TAG);

			if (isDebug())
			{
				print("CLOSE_TAG1");
			}
		}
	)
	;

/*
 * Open Tag 2
 * {
 */
OPEN_TAG2 :
	{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY}?
	(
		"{"
		{
			$setType(OPEN_TAG);

			if (isDebug())
			{
				print("OPEN_TAG");
			}
		}
	)
	|
		"{"
		{
			$setType(TEXT);

			if (isDebug())
			{
				print("TEXT - " + $getText);
			}
		}
	;


/*
 * Close Tag 2
 * }
 */
CLOSE_TAG2 :
	{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY}?
	(
		"}"
		{
			$setType(CLOSE_TAG);

			if (isDebug())
			{
				print("CLOSE_TAG");
			}
		}
	)
	|	"}"
		{
			$setType(TEXT);

			if (isDebug())
			{
				print("TEXT - " + $getText);
			}
		}
	;

/*
 * Open Tag 3
 * <%
 */
OPEN_TAG3 :
	{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.ANGLE_PERCENT}?
	(
		"<%"
		{
			$setType(OPEN_TAG);

			if (isDebug())
			{
				print("OPEN_TAG");
			}
		}
	)
	|
		"<"
		{
			$setType(TEXT);

			if (isDebug())
			{
				print("TEXT - " + $getText);
			}
		}
	;

/*
 * Close Tag 3
 * %>
 */
CLOSE_TAG3 :
	{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.ANGLE_PERCENT}?
	(
		"%>"
		{
			$setType(CLOSE_TAG);

			if (isDebug())
			{
				print("CLOSE_TAG");
			}
		}
	)
	|	"%"
		{
			$setType(TEXT);

			if (isDebug())
			{
				print("TEXT - " + $getText);
			}
		}
	;

/*
 * WS (WhiteSpace)
 */
WS :
	(' '|'\t')+
	{
		// ** DEBUG **
		if (isDebug())
		{
			print("WS");
		}
	}
	;

/*
 * ENDL
 */
ENDL :
	( '\n'| '\r'('\n')? )
	{
		newline();

		// ** DEBUG **
		if (isDebug())
		{
			print("ENDL");
		}
	}
	;

/*
 * SLASH
 */
SLASH :
	'/'
	{
		// ** DEBUG **
		if (isDebug())
		{
			print("SLASH");
		}
	}
	;

/*
 * COLON
 */
COLON :
	':'
	{
		// ** DEBUG **
		if (isDebug())
		{
			print("COLON");
		}
	}
	;

/*
 * ID
 */
ID :
	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
	{
		if (isDebug())
		{
			print("ID - " + $getText);
		}
	}
	;
