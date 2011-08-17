// $ANTLR 2.7.7 (2006-11-01): "SQStringParser.g" -> "SQStringParser.java"$

	package com.inamik.template.antlr;

	import antlr.TokenStreamSelector;

	import com.inamik.template.tags.*;
	import com.inamik.template.tokens.*;

	import com.inamik.template.TemplateEngineConfig;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
@SuppressWarnings("all")
public class SQStringParser extends antlr.LLkParser       implements SQStringParserTokenTypes
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
		System.out.println("SQStringParser: " + s);
	}

protected SQStringParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public SQStringParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected SQStringParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public SQStringParser(TokenStream lexer) {
  this(lexer,1);
}

public SQStringParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final QuotedStringToken  start_sqString() throws RecognitionException, TokenStreamException {
		QuotedStringToken token = new QuotedStringToken();
		
		
				QuotedStringElementToken e = null;
			
		
		try {      // for error handling
			{
			_loop3:
			do {
				if (((LA(1) >= TEXT && LA(1) <= BACKSLASH))) {
					e=sqString2();
					
								token.addElement(e);
							
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
			match(SQUOTE);
			
					if (isDebug())
					{
						print("SQString - " + token.toString());
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		return token;
	}
	
	public final QuotedStringElementToken  sqString2() throws RecognitionException, TokenStreamException {
		QuotedStringElementToken token = null;
		
		Token  t = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case TEXT:
			{
				t = LT(1);
				match(TEXT);
				
							token = new QuotedStringElementToken(new TextToken(t.getText()));
						
				break;
			}
			case QUOTE:
			{
				match(QUOTE);
				
							token = new QuotedStringElementToken(new EscapeCharacterToken("\""));
						
				break;
			}
			case BACKSLASH:
			{
				match(BACKSLASH);
				
							token = new QuotedStringElementToken(new EscapeCharacterToken("\\"));
						
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			
					if (isDebug())
					{
						print("SQString2 : " + token.toString());
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		return token;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"TEXT",
		"QUOTE",
		"BACKSLASH",
		"SQUOTE"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 240L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	
	}
