/*
 * $Id: QStringParser.g,v 1.7 2006-08-14 22:31:00 dave Exp $
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

	import com.inamik.template.tags.*;
	import com.inamik.template.tokens.*;

	import com.inamik.template.TemplateEngineConfig;
}

{@SuppressWarnings("all")}
class QStringParser extends Parser;
options
{
	k=1;
	importVocab=QStringLexer;
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
		System.out.println("QStringParser: " + s);
	}

	/**
	 * getVariableId
	 */
	private VariableIdToken getVariableId()
	{
		VariableIdToken token = null;

		VariableExpressionParser varParser = new VariableExpressionParser(selector);

		if (isDebug())
		{
			print("Change lexers to VariableExpressionLexer");
		}

		varParser.setSelector(selector);
		varParser.setTemplateEngineConfig(engineConfig);

		selector.push("VariableExpressionLexer");

		try
		{
			token = varParser.start_variableId();

			if (isDebug())
			{
				print("VariableId - " + token);
			}
		}
		catch (TokenStreamException tse)
		{
			print ("TokenStreamException: " + tse);
//			getInputState().reset();
		}
		catch (RecognitionException re)
		{
			print ("RecognitionException: " + re);
//			getInputState().reset();
		}
		finally
		{
			if (isDebug())
			{
				print("Change lexers to QStringLexer");
			}

			selector.pop();

//			getInputState().reset();
		}

		return token;
	}

	/**
	 * getBracedGeneralExpression
	 */
	private ExpressionToken getBracedGeneralExpression()
	{
		ExpressionToken token = null;

		// Switch parsers/lexers
		TagParser tagParser = new TagParser(selector);

		if (isDebug())
		{
			print("Changing lexers to TagLexer");
		}

		tagParser.setSelector(selector);
		tagParser.setTemplateEngineConfig(engineConfig);

		selector.push("TagLexer");

		try
		{
			token = tagParser.start_bracedGeneralExpression();

			if (isDebug())
			{
				print("GeneralExpression - " + token);
			}
		}
		catch (TokenStreamException tse)
		{
			print ("TokenStreamException: " + tse);
//			getInputState().reset();
		}
		catch (RecognitionException re)
		{
			print ("RecognitionException: " + re);
//			getInputState().reset();
		}
		finally
		{
			if (isDebug())
			{
				print("Changing lexers to QStringLexer");
			}

			selector.pop();

//			getInputState().reset();
		}

		return token;
	}
}

/*
 * qstring (Start Rule)
 * NOTE: Assumes leading '"'
 */
start_qstring returns [QuotedStringToken token = new QuotedStringToken()]
	{
		QuotedStringElementToken e = null;
	}
	:
	(
		e=qstring2
		{
			token.addElement(e);
		}
	)*
	QUOTE
	{
		if (isDebug())
		{
			print("QString - " + token.toString());
		}
	}
	;

/*
 * qstring2
 */
qstring2 returns [QuotedStringElementToken token = null]
	{
		VariableExpressionToken v  = null;
		ExpressionToken         ge = null;
	}
	:
	(
		t:TEXT
		{
			token = new QuotedStringElementToken(new TextToken(t.getText()));
		}
	|
		// VariableExpression
		v=variableExpression
		{
			token = new QuotedStringElementToken(v);
		}
	|
		// generalExpression
		ge=bracedGeneralExpression
		{
			token = new QuotedStringElementToken(ge);
		}
	|
		e:ESCAPE
		{
			token = new QuotedStringElementToken(new EscapeCharacterToken(e.getText()));
		}
	)
	{
		if (isDebug())
		{
			print("QString2 : " + token.toString());
		}
	}
	;

/*
 * variableExpression
 */
variableExpression returns [VariableExpressionToken varEx = null] :
	DOLLAR
	{
		VariableIdToken varId = getVariableId();

		varEx = new VariableExpressionToken();
		varEx.addElement(new VariableExpressionElementToken(varId));
	}
	;

/*
 * bracedGeneralExpression
 */
bracedGeneralExpression returns [ExpressionToken ge = null] :
	LBRACE
	{
		ge = getBracedGeneralExpression();
	}
//!	RBRACE
	;
