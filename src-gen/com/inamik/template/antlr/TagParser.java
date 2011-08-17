// $ANTLR 2.7.7 (2006-11-01): "TagParser.g" -> "TagParser.java"$

	package com.inamik.template.antlr;

	import antlr.TokenStreamSelector;

	import com.inamik.template.*;
	import com.inamik.template.tags.*;
	import com.inamik.template.tokens.*;

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
public class TagParser extends antlr.LLkParser       implements TagParserTokenTypes
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

	protected boolean useParen = true;

	protected TokenStreamSelector selector = null;

	/**
	 * setSelector
	 */
	protected void setSelector(TokenStreamSelector s)
	{
		selector = s;
	}

	/**
	 * print
	 */
	protected void print(String s)
	{
		System.out.println("TagParser: " + s);
	}

	/**
	 * getVariableExpression
	 */
	private VariableExpressionToken getVariableExpression()
	{
		VariableExpressionToken token = null;

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
			token = varParser.start_variableExpression();

			if (isDebug())
			{
				print("VariableExpression - " + token);
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

		return token;
	}

	/**
	 * getQString
	 */
	private QuotedStringToken getQString()
	{
		QuotedStringToken token = null;

		QStringParser parser = new QStringParser(selector);

		if (isDebug())
		{
			print("Change lexers to QStringLexer");
		}

		parser.setSelector(selector);
		parser.setTemplateEngineConfig(engineConfig);

		selector.push("QStringLexer");

		try
		{
			token = parser.start_qstring();

			if (isDebug())
			{
				print("QuotedString - " + token);
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

		return token;
	}

	/**
	 * getSQString
	 */
	private QuotedStringToken getSQString()
	{
		QuotedStringToken token = null;

		SQStringParser parser = new SQStringParser(selector);

		if (isDebug())
		{
			print("Change lexers to SQStringLexer");
		}

		parser.setSelector(selector);
		parser.setTemplateEngineConfig(engineConfig);

		selector.push("SQStringLexer");

		try
		{
			token = parser.start_sqString();

			if (isDebug())
			{
				print("SQuotedString - " + token);
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

		return token;
	}

	/**
	 * getTextBody
	 */
	private TextToken getTextBody(final TemplateTagName openTagName)
	{
		TextToken token = null;

		TextBodyParser parser = new TextBodyParser(selector);

		if (isDebug())
		{
			print("Change lexers to TextBodyLexer");
		}

		parser.setSelector(selector);
		parser.setTemplateEngineConfig(engineConfig);

		selector.push("TextBodyLexer");

		try
		{
			token = parser.start_textBody(openTagName);

			if (isDebug())
			{
				print("TextBody - " + token.getText().length() + " chars");
				print(token.getText());
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

		return token;
	}

protected TagParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public TagParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected TagParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public TagParser(TokenStream lexer) {
  this(lexer,1);
}

public TagParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final Tag  start_tag() throws RecognitionException, TokenStreamException {
		Tag tag = null;
		
		
				boolean closeBlock = false;
			
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case OPEN_TAG1:
			{
				match(OPEN_TAG1);
				{
				switch ( LA(1)) {
				case LCURLY:
				case ID:
				case NUMBER:
				case QUOTE:
				case SQUOTE:
				case DOLLAR:
				case MINUS:
				case PLUS:
				case LPAREN:
				case LOGICAL_NOT:
				case LBRACKET:
				case OPEN_TAG2:
				{
					{
					tag=tag2();
					{
					switch ( LA(1)) {
					case BLOCK_CLOSE_TAG1:
					{
						{
						match(BLOCK_CLOSE_TAG1);
						if ( inputState.guessing==0 ) {
							closeBlock = true;
						}
						}
						break;
					}
					case CLOSE_TAG1:
					{
						match(CLOSE_TAG1);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					if ( inputState.guessing==0 ) {
						if (tag == null) tag = NullTag.getInstance();
					}
					}
					break;
				}
				case BLOCK_CLOSE_TAG1:
				case DIVIDE:
				{
					{
					switch ( LA(1)) {
					case DIVIDE:
					{
						{
						tag=endBlockTag();
						match(CLOSE_TAG1);
						}
						break;
					}
					case BLOCK_CLOSE_TAG1:
					{
						{
						match(BLOCK_CLOSE_TAG1);
						if ( inputState.guessing==0 ) {
							tag = new EndBlockTag();
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
			case OPEN_TAG2:
			{
				match(OPEN_TAG2);
				{
				switch ( LA(1)) {
				case LCURLY:
				case ID:
				case NUMBER:
				case QUOTE:
				case SQUOTE:
				case DOLLAR:
				case MINUS:
				case PLUS:
				case LPAREN:
				case LOGICAL_NOT:
				case LBRACKET:
				case OPEN_TAG2:
				{
					{
					tag=tag2();
					{
					switch ( LA(1)) {
					case BLOCK_CLOSE_TAG2:
					{
						{
						match(BLOCK_CLOSE_TAG2);
						if ( inputState.guessing==0 ) {
							closeBlock = true;
						}
						}
						break;
					}
					case CLOSE_TAG2:
					{
						match(CLOSE_TAG2);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					if ( inputState.guessing==0 ) {
						if (tag == null) tag = NullTag.getInstance();
					}
					}
					break;
				}
				case BLOCK_CLOSE_TAG2:
				case DIVIDE:
				{
					{
					switch ( LA(1)) {
					case DIVIDE:
					{
						{
						tag=endBlockTag();
						match(CLOSE_TAG2);
						}
						break;
					}
					case BLOCK_CLOSE_TAG2:
					{
						{
						match(BLOCK_CLOSE_TAG2);
						if ( inputState.guessing==0 ) {
							tag = new EndBlockTag();
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
			case OPEN_TAG3:
			{
				match(OPEN_TAG3);
				{
				switch ( LA(1)) {
				case LCURLY:
				case ID:
				case NUMBER:
				case QUOTE:
				case SQUOTE:
				case DOLLAR:
				case MINUS:
				case PLUS:
				case LPAREN:
				case LOGICAL_NOT:
				case LBRACKET:
				case OPEN_TAG2:
				{
					{
					tag=tag2();
					{
					switch ( LA(1)) {
					case BLOCK_CLOSE_TAG3:
					{
						{
						match(BLOCK_CLOSE_TAG3);
						if ( inputState.guessing==0 ) {
							closeBlock = true;
						}
						}
						break;
					}
					case CLOSE_TAG3:
					{
						match(CLOSE_TAG3);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					if ( inputState.guessing==0 ) {
						if (tag == null) tag = NullTag.getInstance();
					}
					}
					break;
				}
				case BLOCK_CLOSE_TAG3:
				case DIVIDE:
				{
					{
					switch ( LA(1)) {
					case DIVIDE:
					{
						{
						tag=endBlockTag();
						match(CLOSE_TAG3);
						}
						break;
					}
					case BLOCK_CLOSE_TAG3:
					{
						{
						match(BLOCK_CLOSE_TAG3);
						if ( inputState.guessing==0 ) {
							tag = new EndBlockTag();
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
			case BLOCK_COMMENT_TAG:
			{
				{
				match(BLOCK_COMMENT_TAG);
				if ( inputState.guessing==0 ) {
					tag = BlockCommentTag.getInstance();
				}
				}
				break;
			}
			case LINE_COMMENT_TAG:
			{
				{
				match(LINE_COMMENT_TAG);
				if ( inputState.guessing==0 ) {
					tag = LineCommentTag.getInstance();
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
			if ( inputState.guessing==0 ) {
				
						assert tag != null;
				
						if (tag.getType() == TagType.ACTION)
						{
							ActionTag action = (ActionTag)tag;
				
							TemplateTagName tagName = action.getFQName();
				
							assert tagName  != null;
				
							TemplateActionConfig ac = engineConfig.getActionConfig(tagName);
				
							// If it's a body tag
							if	(
									(ac.getBlockType() == TemplateActionConfig.BlockType.BODY)
								||	(ac.getBlockType() == TemplateActionConfig.BlockType.BODY_ALT)
								)
							{
								// If it was closed early
								if (closeBlock == true)
								{
									BodyActionTag   bat = new BodyActionTag(action);
									MultipleTagsTag mtt = new MultipleTagsTag();
									bat.setBody(mtt);
									tag = bat;
								}
								else
								// If it is has body content of TEXT
								if (ac.getBodyContent() == TemplateActionConfig.BodyContent.TEXT)
								{
									TextToken textBody = getTextBody(tagName);
									BodyActionTag   bat = new BodyActionTag(action);
									MultipleTagsTag mtt = new MultipleTagsTag();
									mtt.addElement(new TextTag(textBody.getText()));
									bat.setBody(mtt);
									tag = bat;
								}
							}
						}
				
						if (isDebug())
						{
							print("Tag - " + tag.getTypeName() +  ": " + tag.toString());
						}
					
			}
		}
		catch (TokenStreamException tse) {
			if (inputState.guessing==0) {
				
						if (isDebug())
						{
							print("TagParser.tag - TokenStreamException: " + tse);
						}
				//		getInputState().reset();
					
			} else {
				throw tse;
			}
		}
		catch (NoViableAltException nvae) {
			if (inputState.guessing==0) {
				
						if (isDebug())
						{
							print("TagParser.tag - NoViableAltException: " + nvae);
						}
				//		getInputState().reset();
					
			} else {
				throw nvae;
			}
		}
		return tag;
	}
	
	public final Tag  tag2() throws RecognitionException, TokenStreamException {
		Tag tag = null;
		
		
		try {      // for error handling
			{
			boolean synPredMatched29 = false;
			if (((_tokenSet_0.member(LA(1))))) {
				int _m29 = mark();
				synPredMatched29 = true;
				inputState.guessing++;
				try {
					{
					switch ( LA(1)) {
					case DOLLAR:
					{
						match(DOLLAR);
						break;
					}
					case QUOTE:
					{
						match(QUOTE);
						break;
					}
					case SQUOTE:
					{
						match(SQUOTE);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched29 = false;
				}
				rewind(_m29);
inputState.guessing--;
			}
			if ( synPredMatched29 ) {
				tag=generalExpressionTag();
			}
			else if ((LA(1)==ID)) {
				tag=actionTag();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				
							if (tag == null)
							{
								tag = NullTag.getInstance();
							}
				
							if (isDebug())
							{
								print("Tag2 - " + tag.getTypeName());
							}
						
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return tag;
	}
	
	public final EndBlockTag  endBlockTag() throws RecognitionException, TokenStreamException {
		 EndBlockTag tag = null;
		
		
				FQNameToken f = null;
			
		
		try {      // for error handling
			match(DIVIDE);
			{
			switch ( LA(1)) {
			case ID:
			{
				f=fqname();
				break;
			}
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
						if (f != null)
						{
							tag = new EndBlockTag(f.getFQName());
						}
						else
						{
							tag = new EndBlockTag();
						}
				
						if (isDebug())
						{
							print("EndBlockTag");
						}
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		return tag;
	}
	
	public final ExpressionTag  generalExpressionTag() throws RecognitionException, TokenStreamException {
		 ExpressionTag v = null ;
		
		
				ExpressionToken x = null;
			
		
		try {      // for error handling
			x=generalExpressionWithFilters();
			if ( inputState.guessing==0 ) {
				
						v = new ExpressionTag(x);
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return v;
	}
	
	public final ActionTag  actionTag() throws RecognitionException, TokenStreamException {
		 ActionTag a = null ;
		
		
				FQNameToken             f  = null;
				AttributeListToken      al = null;
				ExpressionToken         e  = null;
				ExpressionListToken     el = null;
				TemplateActionConfig    ac = null;
				IdToken                 i  = null;
				IdListToken             il = null;
		
				TemplateActionConfig.ParmType parmType = null;
			
		
		try {      // for error handling
			f=fqname();
			if ( inputState.guessing==0 ) {
				
						// Get parm type for named action
						ac = engineConfig.getActionConfig(f.getFQName());
						parmType = ac.getParmType();
						if (isDebug()) print("Tag " + f.getFQName() + " has parm type: " + parmType);
					
			}
			{
			if (((_tokenSet_3.member(LA(1))))&&(parmType == TemplateActionConfig.ParmType.ATTRIBUTES)) {
				al=attributeList();
				if ( inputState.guessing==0 ) {
					
								assert al != null;
					
								a = new ActionTag(f.getFQName(), al);
							
				}
			}
			else if (((_tokenSet_0.member(LA(1))))&&(parmType == TemplateActionConfig.ParmType.EXPRESSION)) {
				e=generalExpression();
				if ( inputState.guessing==0 ) {
					
								a = new ActionTag(f.getFQName(), e);
							
				}
			}
			else if (((_tokenSet_4.member(LA(1))))&&(parmType == TemplateActionConfig.ParmType.EXPRESSION_LIST)) {
				el=expressionList();
				if ( inputState.guessing==0 ) {
					
								assert el != null;
					
								a = new ActionTag(f.getFQName(), el);
							
				}
			}
			else if (((LA(1)==ID||LA(1)==DOLLAR))&&(parmType == TemplateActionConfig.ParmType.ID)) {
				i=attributeId();
				if ( inputState.guessing==0 ) {
					
								a = new ActionTag(f.getFQName(), i);
							
				}
			}
			else if (((_tokenSet_3.member(LA(1))))&&(parmType == TemplateActionConfig.ParmType.ID_LIST)) {
				il=attributeIdList();
				if ( inputState.guessing==0 ) {
					
								assert il != null;
					
								a = new ActionTag(f.getFQName(), il);
							
				}
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				if ( inputState.guessing==0 ) {
					
								assert parmType == TemplateActionConfig.ParmType.EMPTY;
					
								a = new ActionTag(f.getFQName());
							
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return a;
	}
	
	public final FQNameToken  fqname() throws RecognitionException, TokenStreamException {
		FQNameToken ft = null;
		
		
				IdToken i1 = null;
				IdToken i2 = null;
			
		
		try {      // for error handling
			i1=id();
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				i2=id();
				break;
			}
			case LCURLY:
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case ID:
			case NUMBER:
			case QUOTE:
			case SQUOTE:
			case DOLLAR:
			case MINUS:
			case PLUS:
			case LPAREN:
			case LOGICAL_NOT:
			case LBRACKET:
			case PIPE:
			case CLOSE_TAG1:
			case OPEN_TAG2:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
						TemplateTagName f;
				
						if (i2 != null)
						{
							f = new TemplateTagName(i1.getText(), i2.getText());
						}
						else
						{
							f = new TemplateTagName(i1.getText());
						}
				
						ft = new FQNameToken(f);
				
						if (isDebug())
						{
							print("fqname : " + ft.toString());
						}
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		return ft;
	}
	
	public final AttributeListToken  attributeList() throws RecognitionException, TokenStreamException {
		 AttributeListToken al = new AttributeListToken() ;
		
		
				AttributeToken a = null;
			
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			case DOLLAR:
			{
				a=attribute();
				if ( inputState.guessing==0 ) {
					
								al.addAttribute(a);
							
				}
				{
				_loop46:
				do {
					if ((LA(1)==ID||LA(1)==DOLLAR||LA(1)==COMMA)) {
						{
						switch ( LA(1)) {
						case COMMA:
						{
							match(COMMA);
							break;
						}
						case ID:
						case DOLLAR:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						a=attribute();
						if ( inputState.guessing==0 ) {
							
											al.addAttribute(a);
										
						}
					}
					else {
						break _loop46;
					}
					
				} while (true);
				}
				break;
			}
			case RCURLY:
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		return al;
	}
	
	public final ExpressionToken  generalExpression() throws RecognitionException, TokenStreamException {
		ExpressionToken x = null;
		
		
				ExpressionToken         right = null;
				ExpressionOperatorToken o     = null;
			
		
		try {      // for error handling
			x=logicalAndExpression();
			{
			switch ( LA(1)) {
			case LOGICAL_OR:
			{
				match(LOGICAL_OR);
				right=generalExpression();
				if ( inputState.guessing==0 ) {
					
								o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LOGICAL_OR);
								x = new ExpressionToken(x, o, right);
							
				}
				break;
			}
			case RCURLY:
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case ID:
			case DOLLAR:
			case RPAREN:
			case RBRACKET:
			case PIPE:
			case COMMA:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			} else {
			  throw ex;
			}
		}
		return x;
	}
	
	public final ExpressionListToken  expressionList() throws RecognitionException, TokenStreamException {
		ExpressionListToken xl = new ExpressionListToken();
		
		
				ExpressionToken x = null;
			
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LCURLY:
			case ID:
			case NUMBER:
			case QUOTE:
			case SQUOTE:
			case DOLLAR:
			case MINUS:
			case PLUS:
			case LPAREN:
			case LOGICAL_NOT:
			case LBRACKET:
			case OPEN_TAG2:
			{
				x=generalExpression();
				if ( inputState.guessing==0 ) {
					
								xl.addExpression(x);
							
				}
				{
				_loop64:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						x=generalExpression();
						if ( inputState.guessing==0 ) {
							
											xl.addExpression(x);
										
						}
					}
					else {
						break _loop64;
					}
					
				} while (true);
				}
				break;
			}
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case RPAREN:
			case RBRACKET:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		return xl;
	}
	
	public final IdToken  attributeId() throws RecognitionException, TokenStreamException {
		IdToken i = null ;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case DOLLAR:
			{
				match(DOLLAR);
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
			i=id();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return i;
	}
	
	public final IdListToken  attributeIdList() throws RecognitionException, TokenStreamException {
		IdListToken il = new IdListToken();
		
		
				IdToken i = null;
			
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			case DOLLAR:
			{
				i=attributeId();
				if ( inputState.guessing==0 ) {
					
								il.addId(i);
							
				}
				{
				_loop54:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						i=attributeId();
						if ( inputState.guessing==0 ) {
							
											il.addId(i);
										
						}
					}
					else {
						break _loop54;
					}
					
				} while (true);
				}
				break;
			}
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return il;
	}
	
	public final ExpressionToken  generalExpressionWithFilters() throws RecognitionException, TokenStreamException {
		 ExpressionToken x = null ;
		
		
				FilterListToken f = null;
			
		
		try {      // for error handling
			x=generalExpression();
			{
			switch ( LA(1)) {
			case PIPE:
			{
				f=filterList();
				if ( inputState.guessing==0 ) {
					x.setFilterList(f);
				}
				break;
			}
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
						if (isDebug())
						{
							print("GeneralExpressionWithFilters: " + x.toString());
						}
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return x;
	}
	
	public final FilterListToken  filterList() throws RecognitionException, TokenStreamException {
		 FilterListToken fl = new FilterListToken() ;
		
		
				FilterToken f = null;
			
		
		try {      // for error handling
			{
			int _cnt57=0;
			_loop57:
			do {
				if ((LA(1)==PIPE)) {
					match(PIPE);
					f=filter();
					if ( inputState.guessing==0 ) {
						
									fl.addFilter(f);
								
					}
				}
				else {
					if ( _cnt57>=1 ) { break _loop57; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt57++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return fl;
	}
	
	public final VariableExpressionToken  variableExpression() throws RecognitionException, TokenStreamException {
		VariableExpressionToken varEx = null;
		
		
		try {      // for error handling
			match(DOLLAR);
			if ( inputState.guessing==0 ) {
				
						varEx = getVariableExpression();
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return varEx;
	}
	
	public final QuotedStringToken  qstring() throws RecognitionException, TokenStreamException {
		QuotedStringToken token = null;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case QUOTE:
			{
				{
				match(QUOTE);
				if ( inputState.guessing==0 ) {
					
									token = getQString();
					
									if (isDebug())
									{
										print("QString - " + token.toString());
									}
								
				}
				}
				break;
			}
			case SQUOTE:
			{
				{
				match(SQUOTE);
				if ( inputState.guessing==0 ) {
					
									token = getSQString();
					
									if (isDebug())
									{
										print("SQString - " + token.toString());
									}
								
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
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return token;
	}
	
	public final AttributeToken  attribute() throws RecognitionException, TokenStreamException {
		 AttributeToken a = null ;
		
		
				IdToken i = null;
				ExpressionToken e = null;
			
		
		try {      // for error handling
			i=attributeId();
			{
			switch ( LA(1)) {
			case EQUALS:
			{
				match(EQUALS);
				break;
			}
			case ASSOCIATE:
			{
				match(ASSOCIATE);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			e=generalExpression();
			if ( inputState.guessing==0 ) {
				a = new AttributeToken(i, e);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		return a;
	}
	
	public final IdToken  id() throws RecognitionException, TokenStreamException {
		IdToken token = null;
		
		Token  i = null;
		
		try {      // for error handling
			i = LT(1);
			match(ID);
			if ( inputState.guessing==0 ) {
				
						token = new IdToken(i.getText());
				
						if (isDebug())
						{
							print("id : " + token.toString());
						}
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		return token;
	}
	
	public final FilterToken  filter() throws RecognitionException, TokenStreamException {
		 FilterToken f = null ;
		
		
				FQNameToken         n = null;
				ExpressionListToken l = null;
			
		
		try {      // for error handling
			n=fqname();
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				match(LPAREN);
				l=expressionList();
				match(RPAREN);
				break;
			}
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case PIPE:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
						// Just to verify at parse-time that function is registered
						engineConfig.getFilterConfig(n.getFQName());
				
						if (l == null)
						{
							l = new ExpressionListToken();
						}
				
						f = new FilterToken(n.getFQName(), l);
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_13);
			} else {
			  throw ex;
			}
		}
		return f;
	}
	
	public final FunctionToken  function() throws RecognitionException, TokenStreamException {
		 FunctionToken f = null ;
		
		
				FQNameToken         n = null;
				ExpressionListToken l = null;
			
		
		try {      // for error handling
			n=fqname();
			match(LPAREN);
			l=expressionList();
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				
						// Just to verify at parse-time that function is registered
						engineConfig.getFunctionConfig(n.getFQName());
				
						f = new FunctionToken(n.getFQName(), l);
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return f;
	}
	
	public final NumberToken  number() throws RecognitionException, TokenStreamException {
		NumberToken n = null;
		
		Token  n1 = null;
		Token  n2 = null;
		
		try {      // for error handling
			n1 = LT(1);
			match(NUMBER);
			{
			switch ( LA(1)) {
			case DOT:
			{
				match(DOT);
				n2 = LT(1);
				match(NUMBER);
				break;
			}
			case RCURLY:
			case LESS_THAN:
			case GREATER_THAN:
			case PERCENT:
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case ID:
			case DOLLAR:
			case EQUALS_EQUALS:
			case NOT_EQUALS:
			case MINUS:
			case PLUS:
			case MULTIPLY:
			case LESS_THAN_EQUALS:
			case GREATER_THAN_EQUALS:
			case RPAREN:
			case LOGICAL_OR:
			case LOGICAL_AND:
			case RBRACKET:
			case PIPE:
			case COMMA:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			case DIVIDE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
						if (n2 != null)
						{
							n = new NumberToken(n1.getText(), n2.getText());
						}
						else
						{
							n = new NumberToken(n1.getText());
						}
				
						if (isDebug())
						{
							print("Number - " + n.toString());
						};
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return n;
	}
	
	public final ExpressionToken  logicalAndExpression() throws RecognitionException, TokenStreamException {
		ExpressionToken x = null;
		
		
				ExpressionToken         right = null;
				ExpressionOperatorToken o     = null;
			
		
		try {      // for error handling
			x=equalityExpression();
			{
			switch ( LA(1)) {
			case LOGICAL_AND:
			{
				match(LOGICAL_AND);
				right=logicalAndExpression();
				if ( inputState.guessing==0 ) {
					
								o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LOGICAL_AND);
								x = new ExpressionToken(x, o, right);
							
				}
				break;
			}
			case RCURLY:
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case ID:
			case DOLLAR:
			case RPAREN:
			case LOGICAL_OR:
			case RBRACKET:
			case PIPE:
			case COMMA:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return x;
	}
	
	public final ExpressionToken  equalityExpression() throws RecognitionException, TokenStreamException {
		ExpressionToken x = null;
		
		
				ExpressionToken         right = null;
				ExpressionOperatorToken o     = null;
			
		
		try {      // for error handling
			x=relationalExpression();
			{
			switch ( LA(1)) {
			case EQUALS_EQUALS:
			case NOT_EQUALS:
			{
				o=equalityOperator();
				right=equalityExpression();
				if ( inputState.guessing==0 ) {
					
								x = new ExpressionToken(x, o, right);
							
				}
				break;
			}
			case RCURLY:
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case ID:
			case DOLLAR:
			case RPAREN:
			case LOGICAL_OR:
			case LOGICAL_AND:
			case RBRACKET:
			case PIPE:
			case COMMA:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		return x;
	}
	
	public final ExpressionToken  relationalExpression() throws RecognitionException, TokenStreamException {
		ExpressionToken x = null;
		
		
				ExpressionToken         right = null;
				ExpressionOperatorToken o     = null;
			
		
		try {      // for error handling
			x=additiveExpression();
			{
			switch ( LA(1)) {
			case LESS_THAN:
			case GREATER_THAN:
			case LESS_THAN_EQUALS:
			case GREATER_THAN_EQUALS:
			{
				o=relationalOperator();
				right=relationalExpression();
				if ( inputState.guessing==0 ) {
					
								x = new ExpressionToken(x, o, right);
							
				}
				break;
			}
			case RCURLY:
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case ID:
			case DOLLAR:
			case EQUALS_EQUALS:
			case NOT_EQUALS:
			case RPAREN:
			case LOGICAL_OR:
			case LOGICAL_AND:
			case RBRACKET:
			case PIPE:
			case COMMA:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_16);
			} else {
			  throw ex;
			}
		}
		return x;
	}
	
	public final ExpressionOperatorToken  equalityOperator() throws RecognitionException, TokenStreamException {
		ExpressionOperatorToken o = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case NOT_EQUALS:
			{
				match(NOT_EQUALS);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.NOT_EQUALS   );
				}
				break;
			}
			case EQUALS_EQUALS:
			{
				match(EQUALS_EQUALS);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.EQUALS_EQUALS);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return o;
	}
	
	public final ExpressionToken  additiveExpression() throws RecognitionException, TokenStreamException {
		ExpressionToken x = null;
		
		
				ExpressionToken         right = null;
				ExpressionOperatorToken o     = null;
			
		
		try {      // for error handling
			x=multiplicativeExpression();
			{
			switch ( LA(1)) {
			case MINUS:
			case PLUS:
			{
				o=additiveOperator();
				right=additiveExpression();
				if ( inputState.guessing==0 ) {
					
								x = new ExpressionToken(x, o, right);
							
				}
				break;
			}
			case RCURLY:
			case LESS_THAN:
			case GREATER_THAN:
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case ID:
			case DOLLAR:
			case EQUALS_EQUALS:
			case NOT_EQUALS:
			case LESS_THAN_EQUALS:
			case GREATER_THAN_EQUALS:
			case RPAREN:
			case LOGICAL_OR:
			case LOGICAL_AND:
			case RBRACKET:
			case PIPE:
			case COMMA:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		return x;
	}
	
	public final ExpressionOperatorToken  relationalOperator() throws RecognitionException, TokenStreamException {
		ExpressionOperatorToken o = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LESS_THAN:
			{
				match(LESS_THAN);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LESS_THAN          );
				}
				break;
			}
			case GREATER_THAN:
			{
				match(GREATER_THAN);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.GREATER_THAN       );
				}
				break;
			}
			case LESS_THAN_EQUALS:
			{
				match(LESS_THAN_EQUALS);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LESS_THAN_EQUALS   );
				}
				break;
			}
			case GREATER_THAN_EQUALS:
			{
				match(GREATER_THAN_EQUALS);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.GREATER_THAN_EQUALS);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return o;
	}
	
	public final ExpressionToken  multiplicativeExpression() throws RecognitionException, TokenStreamException {
		ExpressionToken x = null;
		
		
				ExpressionToken         right = null;
				ExpressionOperatorToken o     = null;
			
		
		try {      // for error handling
			x=unaryExpression();
			{
			switch ( LA(1)) {
			case PERCENT:
			case MULTIPLY:
			case DIVIDE:
			{
				o=multiplicativeOperator();
				right=multiplicativeExpression();
				if ( inputState.guessing==0 ) {
					
								x = new ExpressionToken(x, o, right);
							
				}
				break;
			}
			case RCURLY:
			case LESS_THAN:
			case GREATER_THAN:
			case BLOCK_CLOSE_TAG1:
			case BLOCK_CLOSE_TAG2:
			case BLOCK_CLOSE_TAG3:
			case ID:
			case DOLLAR:
			case EQUALS_EQUALS:
			case NOT_EQUALS:
			case MINUS:
			case PLUS:
			case LESS_THAN_EQUALS:
			case GREATER_THAN_EQUALS:
			case RPAREN:
			case LOGICAL_OR:
			case LOGICAL_AND:
			case RBRACKET:
			case PIPE:
			case COMMA:
			case CLOSE_TAG1:
			case CLOSE_TAG2:
			case CLOSE_TAG3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		return x;
	}
	
	public final ExpressionOperatorToken  additiveOperator() throws RecognitionException, TokenStreamException {
		ExpressionOperatorToken o = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			{
				match(PLUS);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.PLUS);
				}
				break;
			}
			case MINUS:
			{
				match(MINUS);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.MINUS);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return o;
	}
	
	public final ExpressionToken  unaryExpression() throws RecognitionException, TokenStreamException {
		ExpressionToken x = null;
		
		
				ExpressionToken         left  = null;
				ExpressionToken         right = null;
				ExpressionOperatorToken o     = null;
			
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case MINUS:
			case PLUS:
			{
				o=unaryOperator();
				right=unaryExpression();
				if ( inputState.guessing==0 ) {
					
								x = new ExpressionToken(o, right);
							
				}
				break;
			}
			case LCURLY:
			case ID:
			case NUMBER:
			case QUOTE:
			case SQUOTE:
			case DOLLAR:
			case LPAREN:
			case LOGICAL_NOT:
			case LBRACKET:
			case OPEN_TAG2:
			{
				left=unaryExpressionNotPlusMinus();
				if ( inputState.guessing==0 ) {
					
								x = left;
							
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
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return x;
	}
	
	public final ExpressionOperatorToken  multiplicativeOperator() throws RecognitionException, TokenStreamException {
		ExpressionOperatorToken o = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case MULTIPLY:
			{
				match(MULTIPLY);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.MULTIPLY);
				}
				break;
			}
			case DIVIDE:
			{
				match(DIVIDE);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.DIVIDE  );
				}
				break;
			}
			case PERCENT:
			{
				match(PERCENT);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.MODULO  );
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return o;
	}
	
	public final ExpressionOperatorToken  unaryOperator() throws RecognitionException, TokenStreamException {
		ExpressionOperatorToken o = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case MINUS:
			{
				match(MINUS);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.MINUS);
				}
				break;
			}
			case PLUS:
			{
				match(PLUS);
				if ( inputState.guessing==0 ) {
					o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.PLUS);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return o;
	}
	
	public final ExpressionToken  unaryExpressionNotPlusMinus() throws RecognitionException, TokenStreamException {
		ExpressionToken e = null;
		
		
				ExpressionToken         right = null;
				ExpressionOperatorToken o     = null;
				ExpressionOperandToken  p     = null;
			
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LOGICAL_NOT:
			{
				o=unaryOperatorNotPlusMinus();
				right=unaryExpression();
				if ( inputState.guessing==0 ) {
					
								e = new ExpressionToken(o, right);
							
				}
				break;
			}
			case LCURLY:
			case ID:
			case NUMBER:
			case QUOTE:
			case SQUOTE:
			case DOLLAR:
			case LPAREN:
			case LBRACKET:
			case OPEN_TAG2:
			{
				p=primaryExpression();
				if ( inputState.guessing==0 ) {
					
								e = new ExpressionToken(p);
							
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
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return e;
	}
	
	public final ExpressionOperatorToken  unaryOperatorNotPlusMinus() throws RecognitionException, TokenStreamException {
		ExpressionOperatorToken o = null;
		
		
		try {      // for error handling
			match(LOGICAL_NOT);
			if ( inputState.guessing==0 ) {
				o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LOGICAL_NOT);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return o;
	}
	
	public final ExpressionOperandToken  primaryExpression() throws RecognitionException, TokenStreamException {
		ExpressionOperandToken eo = null;
		
		
				NumberToken             n = null;
				ExpressionToken         e = null;
				QuotedStringToken       q = null;
				VariableExpressionToken v = null;
				FunctionToken           f = null;
				ExpressionListToken    el = null;
				AttributeListToken     al = null;
				com.inamik.template.tokens.Token t = null;
			
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case NUMBER:
			{
				n=number();
				if ( inputState.guessing==0 ) {
					
								eo = new ExpressionOperandToken(n);
							
				}
				break;
			}
			case DOLLAR:
			{
				v=variableExpression();
				if ( inputState.guessing==0 ) {
					
								eo = new ExpressionOperandToken(v);
							
				}
				break;
			}
			case QUOTE:
			case SQUOTE:
			{
				q=qstring();
				if ( inputState.guessing==0 ) {
					
								eo = new ExpressionOperandToken(q);
							
				}
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				e=generalExpression();
				match(RPAREN);
				if ( inputState.guessing==0 ) {
					
								eo = new ExpressionOperandToken(e);
							
				}
				break;
			}
			case LBRACKET:
			{
				match(LBRACKET);
				el=expressionList();
				match(RBRACKET);
				if ( inputState.guessing==0 ) {
					
								eo = new ExpressionOperandToken(el);
							
				}
				break;
			}
			case LCURLY:
			case OPEN_TAG2:
			{
				{
				switch ( LA(1)) {
				case LCURLY:
				{
					match(LCURLY);
					break;
				}
				case OPEN_TAG2:
				{
					match(OPEN_TAG2);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				al=attributeList();
				{
				switch ( LA(1)) {
				case RCURLY:
				{
					match(RCURLY);
					break;
				}
				case CLOSE_TAG2:
				{
					match(CLOSE_TAG2);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					
								eo = new ExpressionOperandToken(al);
							
				}
				break;
			}
			default:
				if (((LA(1)==ID))&&(LA(2) == COLON || LA(2) == LPAREN)) {
					f=function();
					if ( inputState.guessing==0 ) {
						
									eo = new ExpressionOperandToken(f);
								
					}
				}
				else if ((LA(1)==ID)) {
					t=simpleTextOrBoolean();
					if ( inputState.guessing==0 ) {
						
									if (t.getType() == TokenType.QUOTED_STRING)
									{
										eo = new ExpressionOperandToken((QuotedStringToken)t);
									}
									else
									{
										assert t.getType() == TokenType.BOOLEAN;
										eo = new ExpressionOperandToken((BooleanToken)t);
									}
								
					}
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return eo;
	}
	
	public final com.inamik.template.tokens.Token  simpleTextOrBoolean() throws RecognitionException, TokenStreamException {
		 com.inamik.template.tokens.Token t = null ;
		
		Token  i = null;
		
		try {      // for error handling
			i = LT(1);
			match(ID);
			if ( inputState.guessing==0 ) {
				
						if ("true".equalsIgnoreCase(i.getText()))
						{
							t = new BooleanToken(true);
						}
						else
						if ("false".equalsIgnoreCase(i.getText()))
						{
							t = new BooleanToken(false);
						}
						else
						{
							QuotedStringToken q = new QuotedStringToken();
							q.addElement(new QuotedStringElementToken(new TextToken(i.getText())));
							t = q;
						}
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return t;
	}
	
	public final ExpressionToken  start_indexedGeneralExpression() throws RecognitionException, TokenStreamException {
		 ExpressionToken token = null ;
		
		
		try {      // for error handling
			token=generalExpression();
			match(RBRACKET);
			if ( inputState.guessing==0 ) {
				
						if (isDebug())
						{
							print("IndexedGeneralExpression - [" + token + "]");
						}
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return token;
	}
	
	public final ExpressionToken  start_bracedGeneralExpression() throws RecognitionException, TokenStreamException {
		 ExpressionToken token = null ;
		
		
		try {      // for error handling
			token=generalExpression();
			{
			switch ( LA(1)) {
			case RCURLY:
			{
				match(RCURLY);
				break;
			}
			case CLOSE_TAG2:
			{
				match(CLOSE_TAG2);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
						if (isDebug())
						{
							print("BracedGeneralExpression - {" + token + "}");
						}
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return token;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"BLOCK_COMMENT_TAG",
		"LINE_COMMENT_TAG",
		"LCURLY",
		"RCURLY",
		"LESS_THAN",
		"GREATER_THAN",
		"PERCENT",
		"BLOCK_CLOSE_TAG1",
		"BLOCK_CLOSE_TAG2",
		"BLOCK_CLOSE_TAG3",
		"ID",
		"WS",
		"ENDL",
		"NUMBER",
		"QUOTE",
		"SQUOTE",
		"DOLLAR",
		"DOT",
		"EQUALS",
		"EQUALS_EQUALS",
		"NOT_EQUALS",
		"MINUS",
		"PLUS",
		"MULTIPLY",
		"LESS_THAN_EQUALS",
		"GREATER_THAN_EQUALS",
		"LPAREN",
		"RPAREN",
		"LOGICAL_NOT",
		"LOGICAL_OR",
		"LOGICAL_AND",
		"LBRACKET",
		"RBRACKET",
		"PIPE",
		"COLON",
		"ASSOCIATE",
		"COMMA",
		"OPEN_TAG1",
		"CLOSE_TAG1",
		"OPEN_TAG2",
		"CLOSE_TAG2",
		"OPEN_TAG3",
		"CLOSE_TAG3",
		"DIVIDE"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 8835924115520L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 92358976747520L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 92358976733184L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 92358977812480L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 101194900863040L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 101332339816512L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 92358976747648L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 93666795354240L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 92429843707904L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 94008248383488L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 234431118868352L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 93458489440384L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 103256489359424L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 92496415700992L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 93675385288832L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 93692565158016L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 93692590323840L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 93693395630976L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 93693496294272L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	
	}
