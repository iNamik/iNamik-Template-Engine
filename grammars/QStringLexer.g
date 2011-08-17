/*
 * $Id: QStringLexer.g,v 1.7 2006-08-14 22:31:00 dave Exp $
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
class QStringLexer extends Lexer;
options
{
	k=1;
	exportVocab=QStringLexer;
	charVocabulary = '\u0020'..'\u007f';
//	defaultErrorHandler=false;
}

/*
 * Class Code
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
		System.out.println("QStringLexer : " + s);
	}
}

/*
 * TEXT
 */
TEXT :
	// Accept all printable ascii chars except :
	// '"' (\u0022)
	// '$' (\u0024)
	// '\' (\u005c)
	// '{' (\u007b)
	// '}' (\u007d)
	(
		'\u0020'..'\u0021'
	|
		'\u0023'
	|
		'\u0025'..'\u005b'
	|
		'\u005d'..'\u007a'
	|
		'\u007c'
	|
		'\u007e'..'\u007f'
	)+
	{
		if (isDebug())
		{
			print("TEXT : " + $getText);
		}
	}
	;

/*
 * QUOTE
 */
QUOTE :
	'\"'
	{
		if (isDebug())
		{
			print("QUOTE");
		}
	}
	;

/*
 * DOLLAR
 */
DOLLAR :
	'$'
	{
		if (isDebug())
		{
			print("DOLLAR");
		}
	}
	;

/*
 * LBRACE
 */
LBRACE :
	'{'
	{
		if (isDebug())
		{
			print("LBRACE");
		}
	}
	;

///*
// * RBRACE
// */
//RBRACE :
//	'}'
//	{
//		if (isDebug())
//		{
//			print("LBRACE");
//		}
//	}
//	;

/*
 * ESCAPE
 */
ESCAPE :
	'\\'! ( '\u0020'..'\u00ff')
	{
		if (isDebug())
		{
			print("ESCAPE : " + $getText);
		}
	}
	;
