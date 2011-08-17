/*
 * $Id: FileLexer.g,v 1.9 2006-08-14 22:31:00 dave Exp $
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
class FileLexer extends Lexer;
options
{
	k=2; // For two-char open tags
	exportVocab=FileLexer;
	charVocabulary = '\u0000'..'\u00ff';
//	defaultErrorHandler=false;
}
tokens
{
	// Allows us to reference the token type by name
	TEXT; OPEN_TAG;
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
		System.out.println("FileLexer : " + s);
	}
}


/*
 * TEXT
 */
TEXT :
	// Accept all printable ascii chars except
	// '<' (\u003c)
	// '{' (\u007b)
	// '{' (\u007d)
	('\u0021'..'\u003b'|'\u003d'..'\u007a'|'\u007c'|'\u007e'..'\u007f')+
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
 *
 * "{{"
 */
OPEN_TAG1
	{
		int m = mark();
	}
	:
	{
		engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY
	}? "{" ("{")+
	{
		String t = $getText;

		if (t.length() == 2)
		{
			rewind(m);

			// ** DEBUG **
			if (isDebug())
			{
				print("OPEN TAG - " + $getText);
			}

			$setType(OPEN_TAG);
		}
		else
		{
			commit();

			$setText(t.substring(1));

			$setType(TEXT);
		}
	}
	;

/*
 * Open Tag 2
 *
 * "{"
 */
OPEN_TAG2
	{
		int m = mark();
	}
	:
		{ engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY }? ("{")+
		{
			String t = $getText;

			if (t.length() == 1)
			{
				rewind(m);

				// ** DEBUG **
				if (isDebug())
				{
					print("OPEN TAG - " + $getText);
				}

				$setType(OPEN_TAG);
			}
			else
			{
				commit();

				$setText(t.substring(1));

				$setType(TEXT);
			}
		}
	|	"{"
		{
			commit();

			// ** DEBUG **
			if (isDebug())
			{
				print("TEXT - " + $getText);
			}

			$setType(TEXT);
		}
	;

/*
 * Open Tag 3
 *
 * "<%"
 */
OPEN_TAG3
	{
		int m = mark();
	}
	:
		{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.ANGLE_PERCENT}? "<%"
		{
			rewind(m);

			// ** DEBUG **
			if (isDebug())
			{
				print("OPEN TAG - " + $getText);
			}

			$setType(OPEN_TAG);
		}
	|	"<"
		{
			commit();

			// ** DEBUG **
			if (isDebug())
			{
				print("TEXT - " + $getText);
			}

			$setType(TEXT);
		}
	;

/*
 * Close Tag
 * }
 */
CLOSE_TAG :
	("}")+
	{
		String t = $getText;

		if	(
				(
					( engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY )
				&&	(t.length() > 1)
				)
			||	(
					( engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY )
				&&	(t.length() > 2)
				)
			)
		{
			$setText(t.substring(1));
		}

		$setType(TEXT);
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
