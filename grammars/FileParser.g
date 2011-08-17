/*
 * $Id: FileParser.g,v 1.12 2006-11-21 09:34:42 dave Exp $
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
	import com.inamik.template.tagfilters.*;

	import java.util.Stack;
	import java.util.List;
	import java.util.ArrayList;
}

{@SuppressWarnings("all")}
class FileParser extends Parser;
options
{
	k=1;
	importVocab=FileLexer;
//	defaultErrorHandler=false;
}

/*
 * Class Code
 */
{
	List<Tag> tagList = new ArrayList<Tag>();

	TemplateTokenizerTagFilter tagFilter = null;

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

	/**
	 * getTag
	 */
	private Tag getTag()
	{
		Tag tag = null;

		TagParser parser = new TagParser(selector);

		if (isDebug())
		{
			print("Change lexers to TagLexer");
		}

		parser.setSelector(selector);
		parser.setTemplateEngineConfig(engineConfig);

		selector.push("TagLexer");

		try
		{
			tag = parser.start_tag();

			if (isDebug())
			{
				print("Tag - " + tag);
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
				print("Change lexers to TagLexer");
			}

			selector.pop();

//			getInputState().reset();
		}

		return tag;
	}

	protected void addTag(Tag t, TokenizedTemplate c)
	{
		assert tagFilter != null;

		if (t.getType() != TagType.NULL)
		{
//			print(">> " + t.getTypeName() + " - " + t);
			tagFilter.addTag(t);
		}

		while (tagList.isEmpty() == false)
		{
			t = tagList.remove(0);
//			print("<< " + t.getTypeName() + " - " + t);

			if (t.getType() == TagType.EOF)
			{
				continue;
			}

			assert t.getType() != TagType.EOF;

			c.add(t);

			// ** DEBUG **
			if (isDebug())
			{
				print("Tag - " + t.getTypeName());
			}
		}
	}
}

/*
 * start_file (Start Rule)
 */
start_file returns [TokenizedTemplate c = new TokenizedTemplate()]
	{
		assert tagFilter == null;

		tagFilter = new CommentFilter();

		TemplateTokenizerTagFilter tagFilter2 = new BlockLineFilter();
		TemplateTokenizerTagFilter tagFilter3 = new IndentFilter();
		TemplateTokenizerTagFilter tagFilter4 = new LeadingSpaceFilter();
		TemplateTokenizerTagFilter tagFilter5 = new NewLineFilter();
		TemplateTokenizerTagFilter tagFilter6 = new BlockTagFilter();

		tagFilter.setEngineConfig(engineConfig);
		tagFilter2.setEngineConfig(engineConfig);
		tagFilter3.setEngineConfig(engineConfig);
		tagFilter4.setEngineConfig(engineConfig);
		tagFilter5.setEngineConfig(engineConfig);
		tagFilter6.setEngineConfig(engineConfig);

		tagFilter.setOutput(tagFilter2);
		tagFilter2.setOutput(tagFilter3);
		tagFilter3.setOutput(tagFilter4);
		tagFilter4.setOutput(tagFilter5);
		tagFilter5.setOutput(tagFilter6);
		tagFilter6.setOutput(tagList);

		Tag t = null;
	}
	:
	(
		(
			t=textTag
		|
			t=spaceTag
		|
			t=endlTag
		|
			t=scriptTag
		)
		{
			addTag(t, c);
		}
	)+
	{
		addTag(EofTag.getInstance(), c);

		// Check for missing close tags
		if (bodyActionTagStack.empty() == false)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("no close tags for open tags: ");
			while (bodyActionTagStack.empty() == false)
			{
				assert actionConfigStack.empty() == false;
				assert multipleTagsStack.empty() == false;

				TemplateActionConfig bac = actionConfigStack.pop();

				if (bac.getBlockType() == TemplateActionConfig.BlockType.BODY)
				{
					sb.append(bodyActionTagStack.pop());
					sb.append("; ");
				}
			}

			throw new RuntimeException(sb.toString());
		}
		if (isDebug())
		{
			print("TokenizedTemplate - " + c.size() + " elements");
			print("\n" + c.toString());
		}
	}
	;


/*
 * textTag
 */
textTag returns [ TextTag tag = null]
	{
		String t = null;
	}
	:
	t=text
	{
		tag = new TextTag(t);

		if (isDebug())
		{
			print("TextTag");
		}
	}
	;

/*
 * spaceTag
 */
spaceTag returns [SpaceTag tag = null]
	{
		String w = null;
	}
	:
	w=ws
	{
		tag = new SpaceTag(w);

		if (isDebug())
		{
			print("SpaceTag");
		}
	}
	;

/*
 * endlTag
 */
endlTag returns [EndlTag tag = null] :
	endl
	{
		tag = EndlTag.getInstance();

		if (isDebug())
		{
			print("EndlTag");
		}
	}
	;

/*
 * scriptTag
 */
scriptTag returns [ Tag tag = null] :
	o:OPEN_TAG
	{
		tag = getTag();

		tag.setFilename(getFilename());
		tag.setLine(o.getLine());
		tag.setColumn(o.getColumn());

		if (isDebug())
		{
			print("ScriptTag - " + tag.getTypeName());
		}
	}
	;

/*
 * text - Merges adjacent TEXT tokens into one return string
 */
text returns [String s = null] :
	{
		StringBuffer textBuffer = new StringBuffer();
	}
	(
	options
	{
		greedy=true;
	}
	:
		t:TEXT
		{
			textBuffer.append(t.getText());
		}
	)+
	{
		s = textBuffer.toString();
	}
	;

/*
 * ws
 */
ws returns [String s = null] :
	w:WS
	{
		s = w.getText();
	}
	;

/*
 * endl
 */
endl :
	ENDL
	;
