/*
 * $Id: TagLexer.g,v 1.13 2006-09-01 02:21:00 dave Exp $
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
class TagLexer extends Lexer;
options
{
	k=2;  // needed for 2-character tokens (i.e. '==', '!=', etc.)
	exportVocab=TagLexer;
	charVocabulary = '\u0000'..'\u00ff';
//	defaultErrorHandler=false;
}

tokens
{
	// Tokens
	BLOCK_COMMENT_TAG	;	LINE_COMMENT_TAG		;
	LCURLY				;	RCURLY					;
	LESS_THAN			;	GREATER_THAN			;
	PERCENT				;
	BLOCK_CLOSE_TAG1	;	BLOCK_CLOSE_TAG2		;
	BLOCK_CLOSE_TAG3	;
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

	protected void print(String s)
	{
		System.out.println("TagLexer : " + s);
	}
}

/*
 * ID
 */
ID	options {testLiterals = true; }
	:
	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
	{
		if (isDebug())
		{
			print("ID - " + $getText);
		}
	}
	;

/*
 * WS - White Space
 */
WS :
	(' '|'\t')+
	{
		$setType(Token.SKIP);

		if (isDebug())
		{
			print("WS");
		}
	}
	;

/*
 * ENDL - End of Line
 */
ENDL :
	('\n' | ('\r' ('\n')?))
	{
		newline();

		$setType(Token.SKIP);

		if (isDebug())
		{
			print("ENDL");
		}
	}
	;

/*
 * Number
 */
NUMBER
	:
	('0'..'9')+
	{
		if (isDebug())
		{
			print("NUMBER - " + $getText);
		}
	}
	;

QUOTE               : '\"' { if (isDebug()) { print("QUOTE"              ); } } ;
SQUOTE              : '\'' { if (isDebug()) { print("SQUOTE"             ); } } ;
DOLLAR              : '$'  { if (isDebug()) { print("DOLLAR"             ); } } ;
DOT                 : '.'  { if (isDebug()) { print("DOT"                ); } } ;
EQUALS              : '='  { if (isDebug()) { print("EQUALS"             ); } } ;
EQUALS_EQUALS       : "==" { if (isDebug()) { print("EQUALS_EQUALS"      ); } } ;
NOT_EQUALS          : "!=" { if (isDebug()) { print("NOT_EQUALS"         ); } } ;
MINUS               : '-'  { if (isDebug()) { print("MINUS"              ); } } ;
PLUS                : '+'  { if (isDebug()) { print("PLUS"               ); } } ;
MULTIPLY            : '*'  { if (isDebug()) { print("MULTIPLY"           ); } } ;
//DIVIDE              : '/'  { if (isDebug()) { print("DIVIDE"             ); } } ;
LESS_THAN_EQUALS    : "<=" { if (isDebug()) { print("LESS_THAN_EQUALS"   ); } } ;
GREATER_THAN        : '>'  { if (isDebug()) { print("GREATER_THAN"       ); } } ;
GREATER_THAN_EQUALS : ">=" { if (isDebug()) { print("GREATER_THAN_EQUALS"); } };
LPAREN              : '('  { if (isDebug()) { print("LPAREN"             ); } } ;
RPAREN              : ')'  { if (isDebug()) { print("RPAREN"             ); } } ;
LOGICAL_NOT         : '!'  { if (isDebug()) { print("LOGICAL_NOT"        ); } } ;
LOGICAL_OR          : "||" { if (isDebug()) { print("LOGICAL_OR"         ); } } ;
LOGICAL_AND         : "&&" { if (isDebug()) { print("LOGICAL_AND"        ); } } ;
LBRACKET            : '['  { if (isDebug()) { print("LBRACKET"           ); } } ;
RBRACKET            : ']'  { if (isDebug()) { print("RBRACKET"           ); } } ;
PIPE                : '|'  { if (isDebug()) { print("PIPE"               ); } } ;
COLON               : ':'  { if (isDebug()) { print("COLON"              ); } } ;
//DEREF             : "->" { if (isDebug()) { print("DEREF"              ); } } ;
ASSOCIATE           : "=>" { if (isDebug()) { print("ASSOCIATE"          ); } } ;
COMMA               : ","  { if (isDebug()) { print("COMMA"              ); } } ;

/*
 * Open Tag 1
 * {{
 */
OPEN_TAG1 :
	{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY}?
	(
		("{{//}}") =>
		"{{//}}"
		(~('\n'|'\r'))*  // May want to ignore the new line as well
		{
			$setType(LINE_COMMENT_TAG);

			if (isDebug())
			{
				print("LINE_COMMENT_TAG");
			}
		}
	|
		("{{*") =>
		"{{*"
		(
			options
			{
				generateAmbigWarnings=false;
			}
			:
			{ LA(2) != '}' && LA(3) != '}' }? '*'
		|
			'\r' '\n'		{newline();}
		|
			'\r'			{newline();}
		|
			'\n'			{newline();}
		|
			~('*'|'\n'|'\r')
		)*
		"*}}"
		{
			$setType(BLOCK_COMMENT_TAG);

			if (isDebug())
			{
				print("BLOCK_COMMENT_TAG");
			}
		}
	|
		("{{") => "{{"
		{
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
		("{//}") =>
		"{//}"
		(~('\n'|'\r'))*  // May want to ignore the new line as well
		{
			$setType(LINE_COMMENT_TAG);

			if (isDebug())
			{
				print("LINE_COMMENT_TAG");
			}
		}
	|
		("{*") =>
		"{*"
		(
			options
			{
				generateAmbigWarnings=false;
			}
			:
			{ LA(2)!='}' }? '*'
		|
			'\r' '\n'		{newline();}
		|
			'\r'			{newline();}
		|
			'\n'			{newline();}
		|
			~('*'|'\n'|'\r')
		)*
		"*}"
		{
			$setType(BLOCK_COMMENT_TAG);

			if (isDebug())
			{
				print("BLOCK_COMMENT_TAG");
			}
		}
	|
		("{") => "{"
		{
			if (isDebug())
			{
				print("OPEN_TAG");
			}
		}
	)
	|
		"{"
		{
			$setType(LCURLY);

			if (isDebug())
			{
				print("LCURLY");
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
			if (isDebug())
			{
				print("CLOSE_TAG");
			}
		}
	)
	|	"}"
		{
			$setType(RCURLY);

			if (isDebug())
			{
				print("RCURLY");
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
		("<%//%>") =>
		"<%//%>"
		(~('\n'|'\r'))*  // May want to ignore the new line as well
		{
			$setType(LINE_COMMENT_TAG);

			if (isDebug())
			{
				print("LINE_COMMENT_TAG");
			}
		}
	|
		("<%*") =>
		"<%*"
		(
			options
			{
				generateAmbigWarnings=false;
			}
			:
			{ LA(2)!= '<' && LA(3) != '%'}? '*'
		|
			'\r' '\n'		{newline();}
		|
			'\r'			{newline();}
		|
			'\n'			{newline();}
		|
			~('*'|'\n'|'\r')
		)*
		"*%>"
		{
			$setType(BLOCK_COMMENT_TAG);

			if (isDebug())
			{
				print("BLOCK_COMMENT_TAG");
			}
		}
	|
		("<%") => "<%"
		{
			if (isDebug())
			{
				print("OPEN_TAG");
			}
		}
	)
	|
		"<"
		{
			$setType(LESS_THAN);

			if (isDebug())
			{
				print("LESS_THAN");
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
			if (isDebug())
			{
				print("CLOSE_TAG");
			}
		}
	)
	|	"%"
		{
			$setType(PERCENT);

			if (isDebug())
			{
				print("PERCENT");
			}
		}
	;

/*
 *	Divide or close_clock_tag
 */
DIVIDE :
	'/'
	(
		// nothing
		{
			if (isDebug())
			{
				print("DIVIDE");
			}
		}
		|
		{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY}?
		"}}"
		{
			$setType(BLOCK_CLOSE_TAG1);

			if (isDebug())
			{
				print("BLOCK_CLOSE_TAG1");
			}
		}
		|
		{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY}?
		"}"
		{
			$setType(BLOCK_CLOSE_TAG2);

			if (isDebug())
			{
				print("BLOCK_CLOSE_TAG2");
			}
		}
		|
		{engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.ANGLE_PERCENT}?
		"%>"
		{
			$setType(BLOCK_CLOSE_TAG3);

			if (isDebug())
			{
				print("BLOCK_CLOSE_TAG3");
			}
		}
	)
	;