// $ANTLR 2.7.7 (2006-11-01): "VariableExpressionParser.g" -> "VariableExpressionParser.java"$

	package com.inamik.template.antlr;

	import antlr.TokenStreamSelector;
	import antlr.TokenStreamRetryException;

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
public class VariableExpressionParser extends antlr.LLkParser       implements VariableExpressionParserTokenTypes
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

protected VariableExpressionParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public VariableExpressionParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected VariableExpressionParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public VariableExpressionParser(TokenStream lexer) {
  this(lexer,1);
}

public VariableExpressionParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final VariableExpressionToken  start_variableExpression() throws RecognitionException, TokenStreamException {
		VariableExpressionToken varEx = new VariableExpressionToken();
		
		
				IdToken                        i   = null;
				VariableExpressionElementToken e   = null;
				VariableIdToken                vid = null;
				ExpressionToken                ge  = null;
			
		
		try {      // for error handling
			{
			i=id();
			
						e = new VariableExpressionElementToken(new VariableIdToken(i));
						varEx.addElement(e);
					
			}
			{
			_loop7:
			do {
				switch ( LA(1)) {
				case LBRACKET:
				{
					{
					ge=indexedGeneralExpression();
					
									e = new VariableExpressionElementToken( new IndexToken(ge) );
									varEx.addElement(e);
								
					}
					break;
				}
				case DOT:
				{
					{
					match(DOT);
					{
					switch ( LA(1)) {
					case DOLLAR:
					{
						match(DOLLAR);
						vid=variableId();
						
											e = new VariableExpressionElementToken( vid );
											varEx.addElement(e);
										
						break;
					}
					case ID:
					{
						i=id();
						
											e = new VariableExpressionElementToken( i );
											varEx.addElement(e);
										
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					}
					break;
				}
				default:
				{
					break _loop7;
				}
				}
			} while (true);
			}
			
					if (isDebug())
					{
						print("VariableId : " + varEx.toString());
					}
				
		}
		catch (TokenStreamException tse) {
			
					if (isDebug())
					{
						print ("VarParser.VariableExpression - TokenStreamException: " + tse);
					}
			//		getInputState().reset();
				
		}
		return varEx;
	}
	
	public final IdToken  id() throws RecognitionException, TokenStreamException {
		IdToken token = null;
		
		Token  i = null;
		
		try {      // for error handling
			i = LT(1);
			match(ID);
			
					token = new IdToken(i.getText());
			
					if (isDebug())
					{
						print("id : " + token.toString());
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		return token;
	}
	
	public final ExpressionToken  indexedGeneralExpression() throws RecognitionException, TokenStreamException {
		ExpressionToken token = null;
		
		
		try {      // for error handling
			{
			match(LBRACKET);
			
						token = getIndexedGeneralExpression();
					
			}
			
					if (isDebug())
					{
						print("GeneralExpression - " + token.toString());
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		return token;
	}
	
	public final VariableIdToken  variableId() throws RecognitionException, TokenStreamException {
		 VariableIdToken varId = null ;
		
		
				IdToken i = null;
			
		
		try {      // for error handling
			i=id();
			
					varId = new VariableIdToken(i);
			
					if (isDebug())
					{
						print("VariableId : " + varId.toString());
					}
				
		}
		catch (TokenStreamException tse) {
			
					if (isDebug())
					{
						print ("VarParser.variableId - TokenStreamException: " + tse);
					}
			//		getInputState().reset();
				
		}
		return varId;
	}
	
	public final VariableIdToken  start_variableId() throws RecognitionException, TokenStreamException {
		 VariableIdToken varId = null ;
		
		
		try {      // for error handling
			varId=variableId();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		return varId;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"ID",
		"NUMBER",
		"DOLLAR",
		"DOT",
		"DASH",
		"LBRACKET"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 642L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	
	}
