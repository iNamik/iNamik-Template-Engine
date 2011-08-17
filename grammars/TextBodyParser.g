/*
 * $Id: TextBodyParser.g,v 1.4 2006-08-22 03:28:14 dave Exp $
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

	import com.inamik.template.*;
	import com.inamik.template.tags.*;
	import com.inamik.template.tokens.*;
	import com.inamik.template.tagfilters.*;

	import java.util.Stack;
	import java.util.List;
	import java.util.ArrayList;
}

{@SuppressWarnings("all")}
class TextBodyParser extends Parser;
options
{
	k=1;
	importVocab=TextBodyLexer;
//	defaultErrorHandler=false;
}

/*
 * Class Code
 */
{
	TemplateTokenizerTagFilter tagFilter = null;

	List<Tag> tagList = new ArrayList<Tag>();

	Stack<BodyActionTag>        bodyActionTagStack = new Stack<BodyActionTag>();
	Stack<TemplateActionConfig> actionConfigStack  = new Stack<TemplateActionConfig>();
	Stack<MultipleTagsTag>      multipleTagsStack  = new Stack<MultipleTagsTag>();

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

	protected void print(String text)
	{
		System.out.println("FileParser:" + text);
	}
}

/*
 * start_file (Start Rule)
 */
start_textBody [TemplateTagName openTagName] returns [TextToken token = null]
	{
		assert openTagName != null;
		StringBuffer textBuffer = new StringBuffer();
		boolean printOpenTag = true;
		boolean quit = false;
	}
	:
	(
		{quit == false}?
		(
			(
				t:TEXT      { textBuffer.append( t.getText() ); }
			|
				w:WS        { textBuffer.append( w.getText() ); }
			|
				c:CLOSE_TAG { textBuffer.append( c.getText() ); }
			|
				i:ID        { textBuffer.append( i.getText() ); }
			|
				ENDL   { textBuffer.append( "\n" ); }
			|
				SLASH  { textBuffer.append( "/"  ); }
			|
				COLON  { textBuffer.append( ":"  ); }
			)
		|
			(
				o1:OPEN_TAG { printOpenTag = true; }
				(
//					(
						options
						{
							generateAmbigWarnings=false;
						}
						:
						(w1:WS)?
						SLASH
						(w2:WS)?
						i1:ID
						(w3:WS)?
						(
							COLON
							(w4:WS)?
							i2:ID
							(w5:WS)?
						)?
						c1:CLOSE_TAG
//					)
					{
						TemplateTagName closeTagName;

						if (i2 != null)
						{
							closeTagName = new TemplateTagName(i1.getText(), i2.getText());
						}
						else
						{
							closeTagName = new TemplateTagName(i1.getText());
						}

						if (openTagName.equals(closeTagName))
						{
							printOpenTag = false;

							quit = true;
						}
						else
						{
							printOpenTag = false;
							                textBuffer.append(o1.getText());
							if (w1 != null) textBuffer.append(w1.getText());
							                textBuffer.append("/");
							if (w2 != null) textBuffer.append(w2.getText());
							                textBuffer.append(i1.getText());
							if (w3 != null) textBuffer.append(w3.getText());
							if (i2 != null)
							{
							                textBuffer.append(":");
							if (w4 != null) textBuffer.append(w4.getText());
							                textBuffer.append(i2.getText());
							if (w5 != null) textBuffer.append(w5.getText());
							}
							                textBuffer.append(c1.getText());
						}
					}
				)?
				{
					if (printOpenTag)
					{
						textBuffer.append(o1.getText());
					}
				}
			)
		)
	)+
	{
		token = new TextToken(textBuffer.toString());

		if (isDebug())
		{
			print ("TextBody");
		}
	}
	;
