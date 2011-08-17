/*
 * $Id: VariableExpressionParser.g,v 1.7 2006-08-14 22:31:00 dave Exp $
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
	import antlr.TokenStreamRetryException;

	import com.inamik.template.tags.*;
	import com.inamik.template.tokens.*;

	import com.inamik.template.TemplateEngineConfig;
}

{@SuppressWarnings("all")}
class VariableExpressionParser extends Parser;
options
{
	k=1;
	importVocab=VariableExpressionLexer;
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
		System.out.println("VariableExpressionParser: " + s);
	}

	/**
	 * getIndexedGeneralExpression
	 */
	private ExpressionToken getIndexedGeneralExpression()
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
			token = tagParser.start_indexedGeneralExpression();

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
				print("Changing lexers to VariableExpressionLexer");
			}

			selector.pop();

//			getInputState().reset();
		}
		return token;
	}
}

/*
 * variableExpression (Start Rule)
 */
start_variableExpression returns [VariableExpressionToken varEx = new VariableExpressionToken()]
	{
		IdToken                        i   = null;
		VariableExpressionElementToken e   = null;
		VariableIdToken                vid = null;
		ExpressionToken                ge  = null;
	}
	:
	(
		i=id
		{
			e = new VariableExpressionElementToken(new VariableIdToken(i));
			varEx.addElement(e);
		}
	)
	(
		(
			ge=indexedGeneralExpression
			{
				e = new VariableExpressionElementToken( new IndexToken(ge) );
				varEx.addElement(e);
			}
		)
	|	(
			DOT
			(
				DOLLAR vid=variableId
				{
					e = new VariableExpressionElementToken( vid );
					varEx.addElement(e);
				}
			|
				i=id
				{
					e = new VariableExpressionElementToken( i );
					varEx.addElement(e);
				}
			)
		)
	)*
	{
		if (isDebug())
		{
			print("VariableId : " + varEx.toString());
		}
	}
	;
	exception
	catch [TokenStreamException tse]
	{
		if (isDebug())
		{
			print ("VarParser.VariableExpression - TokenStreamException: " + tse);
		}
//		getInputState().reset();
	}

/*
 * start_variableId
 */
start_variableId returns [ VariableIdToken varId = null ] : varId=variableId;

/*
 * VariableId (Start Rule)
 */
variableId returns [ VariableIdToken varId = null ]
	{
		IdToken i = null;
	}
	:
	i=id
	{
		varId = new VariableIdToken(i);

		if (isDebug())
		{
			print("VariableId : " + varId.toString());
		}
	}
	;
	exception
	catch [TokenStreamException tse]
	{
		if (isDebug())
		{
			print ("VarParser.variableId - TokenStreamException: " + tse);
		}
//		getInputState().reset();
	}

/*
 * id
 */
id returns [IdToken token = null] :
	i:ID
	{
		token = new IdToken(i.getText());

		if (isDebug())
		{
			print("id : " + token.toString());
		}
	}
	;

/*
 * indexedGeneralExpression
 */
indexedGeneralExpression returns [ExpressionToken token = null]
	:
	(
		LBRACKET
		{
			token = getIndexedGeneralExpression();
		}
//!		RBRACKET
	)
	{
		if (isDebug())
		{
			print("GeneralExpression - " + token.toString());
		}
	}
	;
