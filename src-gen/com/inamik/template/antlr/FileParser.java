// $ANTLR 2.7.7 (2006-11-01): "FileParser.g" -> "FileParser.java"$

	package com.inamik.template.antlr;

	import antlr.TokenStreamSelector;

	import com.inamik.template.*;
	import com.inamik.template.tags.*;
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
public class FileParser extends antlr.LLkParser       implements FileParserTokenTypes
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

protected FileParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public FileParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected FileParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public FileParser(TokenStream lexer) {
  this(lexer,1);
}

public FileParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final TokenizedTemplate  start_file() throws RecognitionException, TokenStreamException {
		TokenizedTemplate c = new TokenizedTemplate();
		
		
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
			
		
		try {      // for error handling
			{
			int _cnt4=0;
			_loop4:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					{
					switch ( LA(1)) {
					case TEXT:
					{
						t=textTag();
						break;
					}
					case WS:
					{
						t=spaceTag();
						break;
					}
					case ENDL:
					{
						t=endlTag();
						break;
					}
					case OPEN_TAG:
					{
						t=scriptTag();
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					
								addTag(t, c);
							
				}
				else {
					if ( _cnt4>=1 ) { break _loop4; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt4++;
			} while (true);
			}
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		return c;
	}
	
	public final TextTag  textTag() throws RecognitionException, TokenStreamException {
		 TextTag tag = null;
		
		
				String t = null;
			
		
		try {      // for error handling
			t=text();
			
					tag = new TextTag(t);
			
					if (isDebug())
					{
						print("TextTag");
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return tag;
	}
	
	public final SpaceTag  spaceTag() throws RecognitionException, TokenStreamException {
		SpaceTag tag = null;
		
		
				String w = null;
			
		
		try {      // for error handling
			w=ws();
			
					tag = new SpaceTag(w);
			
					if (isDebug())
					{
						print("SpaceTag");
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return tag;
	}
	
	public final EndlTag  endlTag() throws RecognitionException, TokenStreamException {
		EndlTag tag = null;
		
		
		try {      // for error handling
			endl();
			
					tag = EndlTag.getInstance();
			
					if (isDebug())
					{
						print("EndlTag");
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return tag;
	}
	
	public final Tag  scriptTag() throws RecognitionException, TokenStreamException {
		 Tag tag = null;
		
		Token  o = null;
		
		try {      // for error handling
			o = LT(1);
			match(OPEN_TAG);
			
					tag = getTag();
			
					tag.setFilename(getFilename());
					tag.setLine(o.getLine());
					tag.setColumn(o.getColumn());
			
					if (isDebug())
					{
						print("ScriptTag - " + tag.getTypeName());
					}
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return tag;
	}
	
	public final String  text() throws RecognitionException, TokenStreamException {
		String s = null;
		
		Token  t = null;
		
		try {      // for error handling
			
					StringBuffer textBuffer = new StringBuffer();
				
			{
			int _cnt11=0;
			_loop11:
			do {
				if ((LA(1)==TEXT)) {
					t = LT(1);
					match(TEXT);
					
								textBuffer.append(t.getText());
							
				}
				else {
					if ( _cnt11>=1 ) { break _loop11; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt11++;
			} while (true);
			}
			
					s = textBuffer.toString();
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return s;
	}
	
	public final String  ws() throws RecognitionException, TokenStreamException {
		String s = null;
		
		Token  w = null;
		
		try {      // for error handling
			w = LT(1);
			match(WS);
			
					s = w.getText();
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return s;
	}
	
	public final void endl() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(ENDL);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"TEXT",
		"OPEN_TAG",
		"OPEN_TAG1",
		"OPEN_TAG2",
		"OPEN_TAG3",
		"CLOSE_TAG",
		"WS",
		"ENDL"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 3120L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 3122L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	
	}
