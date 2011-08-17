// $ANTLR 2.7.7 (2006-11-01): "TextBodyParser.g" -> "TextBodyParser.java"$

	package com.inamik.template.antlr;

	import antlr.TokenStreamSelector;

	import com.inamik.template.*;
	import com.inamik.template.tags.*;
	import com.inamik.template.tokens.*;
	import com.inamik.template.tagfilters.*;

	import java.util.Stack;
	import java.util.List;
	import java.util.ArrayList;

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
public class TextBodyParser extends antlr.LLkParser       implements TextBodyParserTokenTypes
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

protected TextBodyParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public TextBodyParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected TextBodyParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public TextBodyParser(TokenStream lexer) {
  this(lexer,1);
}

public TextBodyParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final TextToken  start_textBody(
		TemplateTagName openTagName
	) throws RecognitionException, TokenStreamException {
		TextToken token = null;
		
		Token  t = null;
		Token  w = null;
		Token  c = null;
		Token  i = null;
		Token  o1 = null;
		Token  w1 = null;
		Token  w2 = null;
		Token  i1 = null;
		Token  w3 = null;
		Token  w4 = null;
		Token  i2 = null;
		Token  w5 = null;
		Token  c1 = null;
		
				assert openTagName != null;
				StringBuffer textBuffer = new StringBuffer();
				boolean printOpenTag = true;
				boolean quit = false;
			
		
		try {      // for error handling
			{
			int _cnt13=0;
			_loop13:
			do {
				if (((_tokenSet_0.member(LA(1))))&&(quit == false)) {
					{
					switch ( LA(1)) {
					case TEXT:
					case CLOSE_TAG:
					case WS:
					case ENDL:
					case SLASH:
					case COLON:
					case ID:
					{
						{
						switch ( LA(1)) {
						case TEXT:
						{
							t = LT(1);
							match(TEXT);
							textBuffer.append( t.getText() );
							break;
						}
						case WS:
						{
							w = LT(1);
							match(WS);
							textBuffer.append( w.getText() );
							break;
						}
						case CLOSE_TAG:
						{
							c = LT(1);
							match(CLOSE_TAG);
							textBuffer.append( c.getText() );
							break;
						}
						case ID:
						{
							i = LT(1);
							match(ID);
							textBuffer.append( i.getText() );
							break;
						}
						case ENDL:
						{
							match(ENDL);
							textBuffer.append( "\n" );
							break;
						}
						case SLASH:
						{
							match(SLASH);
							textBuffer.append( "/"  );
							break;
						}
						case COLON:
						{
							match(COLON);
							textBuffer.append( ":"  );
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						break;
					}
					case OPEN_TAG:
					{
						{
						o1 = LT(1);
						match(OPEN_TAG);
						printOpenTag = true;
						{
						if ((LA(1)==WS||LA(1)==SLASH)) {
							{
							switch ( LA(1)) {
							case WS:
							{
								w1 = LT(1);
								match(WS);
								break;
							}
							case SLASH:
							{
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
							match(SLASH);
							{
							switch ( LA(1)) {
							case WS:
							{
								w2 = LT(1);
								match(WS);
								break;
							}
							case ID:
							{
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
							i1 = LT(1);
							match(ID);
							{
							switch ( LA(1)) {
							case WS:
							{
								w3 = LT(1);
								match(WS);
								break;
							}
							case CLOSE_TAG:
							case COLON:
							{
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
							{
							switch ( LA(1)) {
							case COLON:
							{
								match(COLON);
								{
								switch ( LA(1)) {
								case WS:
								{
									w4 = LT(1);
									match(WS);
									break;
								}
								case ID:
								{
									break;
								}
								default:
								{
									throw new NoViableAltException(LT(1), getFilename());
								}
								}
								}
								i2 = LT(1);
								match(ID);
								{
								switch ( LA(1)) {
								case WS:
								{
									w5 = LT(1);
									match(WS);
									break;
								}
								case CLOSE_TAG:
								{
									break;
								}
								default:
								{
									throw new NoViableAltException(LT(1), getFilename());
								}
								}
								}
								break;
							}
							case CLOSE_TAG:
							{
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
							c1 = LT(1);
							match(CLOSE_TAG);
							
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
						else if ((_tokenSet_1.member(LA(1)))) {
						}
						else {
							throw new NoViableAltException(LT(1), getFilename());
						}
						
						}
						
											if (printOpenTag)
											{
												textBuffer.append(o1.getText());
											}
										
						}
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else {
					if ( _cnt13>=1 ) { break _loop13; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt13++;
			} while (true);
			}
			
					token = new TextToken(textBuffer.toString());
			
					if (isDebug())
					{
						print ("TextBody");
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return token;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"TEXT",
		"OPEN_TAG",
		"CLOSE_TAG",
		"OPEN_TAG1",
		"CLOSE_TAG1",
		"OPEN_TAG2",
		"CLOSE_TAG2",
		"OPEN_TAG3",
		"CLOSE_TAG3",
		"WS",
		"ENDL",
		"SLASH",
		"COLON",
		"ID"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 254064L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 254066L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	
	}
