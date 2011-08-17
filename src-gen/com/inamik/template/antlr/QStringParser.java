// $ANTLR 2.7.7 (2006-11-01): "QStringParser.g" -> "QStringParser.java"$

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
public class QStringParser extends antlr.LLkParser       implements QStringParserTokenTypes
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

protected QStringParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public QStringParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected QStringParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public QStringParser(TokenStream lexer) {
  this(lexer,1);
}

public QStringParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final QuotedStringToken  start_qstring() throws RecognitionException, TokenStreamException {
		QuotedStringToken token = new QuotedStringToken();
		
		
				QuotedStringElementToken e = null;
			
		
		try {      // for error handling
			{
			_loop3:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					e=qstring2();
					
								token.addElement(e);
							
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
			match(QUOTE);
			
					if (isDebug())
					{
						print("QString - " + token.toString());
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		return token;
	}
	
	public final QuotedStringElementToken  qstring2() throws RecognitionException, TokenStreamException {
		QuotedStringElementToken token = null;
		
		Token  t = null;
		Token  e = null;
		
				VariableExpressionToken v  = null;
				ExpressionToken         ge = null;
			
		
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
			case DOLLAR:
			{
				v=variableExpression();
				
							token = new QuotedStringElementToken(v);
						
				break;
			}
			case LBRACE:
			{
				ge=bracedGeneralExpression();
				
							token = new QuotedStringElementToken(ge);
						
				break;
			}
			case ESCAPE:
			{
				e = LT(1);
				match(ESCAPE);
				
							token = new QuotedStringElementToken(new EscapeCharacterToken(e.getText()));
						
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
						print("QString2 : " + token.toString());
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return token;
	}
	
	public final VariableExpressionToken  variableExpression() throws RecognitionException, TokenStreamException {
		VariableExpressionToken varEx = null;
		
		
		try {      // for error handling
			match(DOLLAR);
			
					VariableIdToken varId = getVariableId();
			
					varEx = new VariableExpressionToken();
					varEx.addElement(new VariableExpressionElementToken(varId));
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return varEx;
	}
	
	public final ExpressionToken  bracedGeneralExpression() throws RecognitionException, TokenStreamException {
		ExpressionToken ge = null;
		
		
		try {      // for error handling
			match(LBRACE);
			
					ge = getBracedGeneralExpression();
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return ge;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"TEXT",
		"QUOTE",
		"DOLLAR",
		"LBRACE",
		"ESCAPE"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 464L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 496L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	
	}
