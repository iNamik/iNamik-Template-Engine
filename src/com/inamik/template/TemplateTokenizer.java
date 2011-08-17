/*
 * $Id: TemplateTokenizer.java,v 1.5 2006-08-17 04:43:55 dave Exp $
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
package com.inamik.template;

import antlr.TokenStreamSelector;

import com.inamik.template.antlr.*;

import java.io.InputStream;

/**
 * TemplateTokenizer - Compiles template files (input streams) into CompileUnits
 */
public class TemplateTokenizer
{
	private TemplateEngineConfig engineConfig;
	private InputStream           in;
	private String                name = null;

	/**
	 * Constructor (Package-Private)
	 */
	TemplateTokenizer(final TemplateEngineConfig engineConfig, final InputStream in)
	{
		super();

		assert engineConfig != null;
		assert in           != null;

		this.engineConfig = engineConfig;
		this.in			  = in;
	}

	/**
	 * setName - For debug purposes
	 */
	public void setName(final String name)
	{
		this.name = name;
	}

	public TokenizedTemplate compile()
	{
		TokenizedTemplate cu = null;

		try
		{
			// Create the lexers, sharing the input state
			FileLexer               fileLexer               = new FileLexer(in);
			TagLexer                tagLexer                = new TagLexer(fileLexer.getInputState());
			QStringLexer            qstringLexer            = new QStringLexer(fileLexer.getInputState());
			SQStringLexer           sqStringLexer           = new SQStringLexer(fileLexer.getInputState());
			VariableExpressionLexer variableExpressionLexer = new VariableExpressionLexer(fileLexer.getInputState());
			TextBodyLexer           textBodyLexer           = new TextBodyLexer(fileLexer.getInputState());

			// Create token selector
			TokenStreamSelector selector = new TokenStreamSelector();

			// Add each lexer to the selector list
			selector.addInputStream(fileLexer              , "FileLexer"              );
			selector.addInputStream(tagLexer               , "TagLexer"               );
			selector.addInputStream(qstringLexer           , "QStringLexer"           );
			selector.addInputStream(sqStringLexer          , "SQStringLexer"          );
			selector.addInputStream(variableExpressionLexer, "VariableExpressionLexer");
			selector.addInputStream(textBodyLexer          , "TextBodyLexer"          );

			// Set file lexer as starting lexer
			selector.select("FileLexer");

			// Add selector to lexers
			fileLexer              .setSelector(selector);
			tagLexer               .setSelector(selector);
			qstringLexer           .setSelector(selector);
			sqStringLexer          .setSelector(selector);
			variableExpressionLexer.setSelector(selector);
			textBodyLexer          .setSelector(selector);

			// Add TemplateContext
			fileLexer              .setTemplateEngineConfig(engineConfig);
			tagLexer               .setTemplateEngineConfig(engineConfig);
			qstringLexer           .setTemplateEngineConfig(engineConfig);
			sqStringLexer          .setTemplateEngineConfig(engineConfig);
			variableExpressionLexer.setTemplateEngineConfig(engineConfig);
			textBodyLexer          .setTemplateEngineConfig(engineConfig);

			// Create file parser
			FileParser fileParser = new FileParser(selector);
			fileParser.setSelector(selector);
			fileParser.setTemplateEngineConfig(engineConfig);
			fileParser.setFilename(name);

			cu = fileParser.start_file();
		}
		catch (Exception e)
		{
			System.err.println("Tokenizer Exception: ");
			e.printStackTrace(System.err);
		}

		return cu;
	}
}
