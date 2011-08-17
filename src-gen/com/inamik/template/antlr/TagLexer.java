// $ANTLR 2.7.7 (2006-11-01): "TagLexer.g" -> "TagLexer.java"$

	package com.inamik.template.antlr;

	import antlr.TokenStreamSelector;

	import com.inamik.template.TemplateEngineConfig;

import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import antlr.CharScanner;
import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.MismatchedCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.SemanticException;
@SuppressWarnings("all")
public class TagLexer extends antlr.CharScanner implements TagLexerTokenTypes, TokenStream
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

	protected void print(String s)
	{
		System.out.println("TagLexer : " + s);
	}
public TagLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public TagLexer(Reader in) {
	this(new CharBuffer(in));
}
public TagLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public TagLexer(LexerSharedInputState state) {
	super(state);
	caseSensitiveLiterals = true;
	setCaseSensitive(true);
	literals = new Hashtable();
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case 'A':  case 'B':  case 'C':  case 'D':
				case 'E':  case 'F':  case 'G':  case 'H':
				case 'I':  case 'J':  case 'K':  case 'L':
				case 'M':  case 'N':  case 'O':  case 'P':
				case 'Q':  case 'R':  case 'S':  case 'T':
				case 'U':  case 'V':  case 'W':  case 'X':
				case 'Y':  case 'Z':  case '_':  case 'a':
				case 'b':  case 'c':  case 'd':  case 'e':
				case 'f':  case 'g':  case 'h':  case 'i':
				case 'j':  case 'k':  case 'l':  case 'm':
				case 'n':  case 'o':  case 'p':  case 'q':
				case 'r':  case 's':  case 't':  case 'u':
				case 'v':  case 'w':  case 'x':  case 'y':
				case 'z':
				{
					mID(true);
					theRetToken=_returnToken;
					break;
				}
				case '\t':  case ' ':
				{
					mWS(true);
					theRetToken=_returnToken;
					break;
				}
				case '\n':  case '\r':
				{
					mENDL(true);
					theRetToken=_returnToken;
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					mNUMBER(true);
					theRetToken=_returnToken;
					break;
				}
				case '"':
				{
					mQUOTE(true);
					theRetToken=_returnToken;
					break;
				}
				case '\'':
				{
					mSQUOTE(true);
					theRetToken=_returnToken;
					break;
				}
				case '$':
				{
					mDOLLAR(true);
					theRetToken=_returnToken;
					break;
				}
				case '.':
				{
					mDOT(true);
					theRetToken=_returnToken;
					break;
				}
				case '-':
				{
					mMINUS(true);
					theRetToken=_returnToken;
					break;
				}
				case '+':
				{
					mPLUS(true);
					theRetToken=_returnToken;
					break;
				}
				case '*':
				{
					mMULTIPLY(true);
					theRetToken=_returnToken;
					break;
				}
				case '(':
				{
					mLPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case ')':
				{
					mRPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case '&':
				{
					mLOGICAL_AND(true);
					theRetToken=_returnToken;
					break;
				}
				case '[':
				{
					mLBRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case ']':
				{
					mRBRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case ':':
				{
					mCOLON(true);
					theRetToken=_returnToken;
					break;
				}
				case ',':
				{
					mCOMMA(true);
					theRetToken=_returnToken;
					break;
				}
				case '%':
				{
					mCLOSE_TAG3(true);
					theRetToken=_returnToken;
					break;
				}
				case '/':
				{
					mDIVIDE(true);
					theRetToken=_returnToken;
					break;
				}
				default:
					if ((LA(1)=='=') && (LA(2)=='=')) {
						mEQUALS_EQUALS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='!') && (LA(2)=='=')) {
						mNOT_EQUALS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (LA(2)=='=')) {
						mLESS_THAN_EQUALS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (LA(2)=='=')) {
						mGREATER_THAN_EQUALS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='|') && (LA(2)=='|')) {
						mLOGICAL_OR(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (LA(2)=='>')) {
						mASSOCIATE(true);
						theRetToken=_returnToken;
					}
					else if (((LA(1)=='{') && (LA(2)=='{'))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY)) {
						mOPEN_TAG1(true);
						theRetToken=_returnToken;
					}
					else if (((LA(1)=='}') && (LA(2)=='}'))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY)) {
						mCLOSE_TAG1(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (true)) {
						mEQUALS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (true)) {
						mGREATER_THAN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='!') && (true)) {
						mLOGICAL_NOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='|') && (true)) {
						mPIPE(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='{') && (true)) {
						mOPEN_TAG2(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='}') && (true)) {
						mCLOSE_TAG2(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (true)) {
						mOPEN_TAG3(true);
						theRetToken=_returnToken;
					}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_ttype = testLiteralsTable(_ttype);
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ID;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			matchRange('A','Z');
			break;
		}
		case '_':
		{
			match('_');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		_loop4:
		do {
			switch ( LA(1)) {
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('a','z');
				break;
			}
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':  case 'G':  case 'H':
			case 'I':  case 'J':  case 'K':  case 'L':
			case 'M':  case 'N':  case 'O':  case 'P':
			case 'Q':  case 'R':  case 'S':  case 'T':
			case 'U':  case 'V':  case 'W':  case 'X':
			case 'Y':  case 'Z':
			{
				matchRange('A','Z');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			case '_':
			{
				match('_');
				break;
			}
			default:
			{
				break _loop4;
			}
			}
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			
					if (isDebug())
					{
						print("ID - " + new String(text.getBuffer(),_begin,text.length()-_begin));
					}
				
		}
		_ttype = testLiteralsTable(_ttype);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		int _saveIndex;
		
		{
		int _cnt7=0;
		_loop7:
		do {
			switch ( LA(1)) {
			case ' ':
			{
				match(' ');
				break;
			}
			case '\t':
			{
				match('\t');
				break;
			}
			default:
			{
				if ( _cnt7>=1 ) { break _loop7; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			}
			_cnt7++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			
					_ttype = Token.SKIP;
			
					if (isDebug())
					{
						print("WS");
					}
				
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mENDL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ENDL;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '\n':
		{
			match('\n');
			break;
		}
		case '\r':
		{
			{
			match('\r');
			{
			if ((LA(1)=='\n')) {
				match('\n');
			}
			else {
			}
			
			}
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			
					newline();
			
					_ttype = Token.SKIP;
			
					if (isDebug())
					{
						print("ENDL");
					}
				
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNUMBER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NUMBER;
		int _saveIndex;
		
		{
		int _cnt14=0;
		_loop14:
		do {
			if (((LA(1) >= '0' && LA(1) <= '9'))) {
				matchRange('0','9');
			}
			else {
				if ( _cnt14>=1 ) { break _loop14; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt14++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			
					if (isDebug())
					{
						print("NUMBER - " + new String(text.getBuffer(),_begin,text.length()-_begin));
					}
				
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mQUOTE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = QUOTE;
		int _saveIndex;
		
		match('\"');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("QUOTE"              ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSQUOTE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SQUOTE;
		int _saveIndex;
		
		match('\'');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("SQUOTE"             ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDOLLAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DOLLAR;
		int _saveIndex;
		
		match('$');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("DOLLAR"             ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DOT;
		int _saveIndex;
		
		match('.');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("DOT"                ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mEQUALS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EQUALS;
		int _saveIndex;
		
		match('=');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("EQUALS"             ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mEQUALS_EQUALS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EQUALS_EQUALS;
		int _saveIndex;
		
		match("==");
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("EQUALS_EQUALS"      ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNOT_EQUALS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NOT_EQUALS;
		int _saveIndex;
		
		match("!=");
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("NOT_EQUALS"         ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMINUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MINUS;
		int _saveIndex;
		
		match('-');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("MINUS"              ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PLUS;
		int _saveIndex;
		
		match('+');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("PLUS"               ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMULTIPLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MULTIPLY;
		int _saveIndex;
		
		match('*');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("MULTIPLY"           ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLESS_THAN_EQUALS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LESS_THAN_EQUALS;
		int _saveIndex;
		
		match("<=");
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("LESS_THAN_EQUALS"   ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGREATER_THAN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GREATER_THAN;
		int _saveIndex;
		
		match('>');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("GREATER_THAN"       ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGREATER_THAN_EQUALS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GREATER_THAN_EQUALS;
		int _saveIndex;
		
		match(">=");
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("GREATER_THAN_EQUALS"); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LPAREN;
		int _saveIndex;
		
		match('(');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("LPAREN"             ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RPAREN;
		int _saveIndex;
		
		match(')');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("RPAREN"             ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLOGICAL_NOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LOGICAL_NOT;
		int _saveIndex;
		
		match('!');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("LOGICAL_NOT"        ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLOGICAL_OR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LOGICAL_OR;
		int _saveIndex;
		
		match("||");
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("LOGICAL_OR"         ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLOGICAL_AND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LOGICAL_AND;
		int _saveIndex;
		
		match("&&");
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("LOGICAL_AND"        ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLBRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LBRACKET;
		int _saveIndex;
		
		match('[');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("LBRACKET"           ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRBRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RBRACKET;
		int _saveIndex;
		
		match(']');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("RBRACKET"           ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPIPE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PIPE;
		int _saveIndex;
		
		match('|');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("PIPE"               ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COLON;
		int _saveIndex;
		
		match(':');
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("COLON"              ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mASSOCIATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ASSOCIATE;
		int _saveIndex;
		
		match("=>");
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("ASSOCIATE"          ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMA;
		int _saveIndex;
		
		match(",");
		if ( inputState.guessing==0 ) {
			if (isDebug()) { print("COMMA"              ); }
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mOPEN_TAG1(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OPEN_TAG1;
		int _saveIndex;
		
		if (!(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY))
		  throw new SemanticException("engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY");
		{
		boolean synPredMatched42 = false;
		if (((LA(1)=='{') && (LA(2)=='{'))) {
			int _m42 = mark();
			synPredMatched42 = true;
			inputState.guessing++;
			try {
				{
				match("{{//}}");
				}
			}
			catch (RecognitionException pe) {
				synPredMatched42 = false;
			}
			rewind(_m42);
inputState.guessing--;
		}
		if ( synPredMatched42 ) {
			match("{{//}}");
			{
			_loop45:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					{
					match(_tokenSet_0);
					}
				}
				else {
					break _loop45;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				
							_ttype = LINE_COMMENT_TAG;
				
							if (isDebug())
							{
								print("LINE_COMMENT_TAG");
							}
						
			}
		}
		else {
			boolean synPredMatched47 = false;
			if (((LA(1)=='{') && (LA(2)=='{'))) {
				int _m47 = mark();
				synPredMatched47 = true;
				inputState.guessing++;
				try {
					{
					match("{{*");
					}
				}
				catch (RecognitionException pe) {
					synPredMatched47 = false;
				}
				rewind(_m47);
inputState.guessing--;
			}
			if ( synPredMatched47 ) {
				match("{{*");
				{
				_loop50:
				do {
					if (((LA(1)=='*') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff')))&&( LA(2) != '}' && LA(3) != '}' )) {
						match('*');
					}
					else if ((LA(1)=='\r') && (LA(2)=='\n')) {
						match('\r');
						match('\n');
						if ( inputState.guessing==0 ) {
							newline();
						}
					}
					else if ((LA(1)=='\r') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
						match('\r');
						if ( inputState.guessing==0 ) {
							newline();
						}
					}
					else if ((LA(1)=='\n')) {
						match('\n');
						if ( inputState.guessing==0 ) {
							newline();
						}
					}
					else if ((_tokenSet_1.member(LA(1)))) {
						{
						match(_tokenSet_1);
						}
					}
					else {
						break _loop50;
					}
					
				} while (true);
				}
				match("*}}");
				if ( inputState.guessing==0 ) {
					
								_ttype = BLOCK_COMMENT_TAG;
					
								if (isDebug())
								{
									print("BLOCK_COMMENT_TAG");
								}
							
				}
			}
			else {
				boolean synPredMatched52 = false;
				if (((LA(1)=='{') && (LA(2)=='{'))) {
					int _m52 = mark();
					synPredMatched52 = true;
					inputState.guessing++;
					try {
						{
						match("{{");
						}
					}
					catch (RecognitionException pe) {
						synPredMatched52 = false;
					}
					rewind(_m52);
inputState.guessing--;
				}
				if ( synPredMatched52 ) {
					match("{{");
					if ( inputState.guessing==0 ) {
						
									if (isDebug())
									{
										print("OPEN_TAG1");
									}
								
					}
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}}
				}
				if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
					_token = makeToken(_ttype);
					_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
				}
				_returnToken = _token;
			}
			
	public final void mCLOSE_TAG1(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CLOSE_TAG1;
		int _saveIndex;
		
		if (!(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY))
		  throw new SemanticException("engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY");
		{
		match("}}");
		if ( inputState.guessing==0 ) {
			
						if (isDebug())
						{
							print("CLOSE_TAG1");
						}
					
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mOPEN_TAG2(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OPEN_TAG2;
		int _saveIndex;
		
		if (((LA(1)=='{') && (true))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY)) {
			{
			boolean synPredMatched58 = false;
			if (((LA(1)=='{') && (LA(2)=='/'))) {
				int _m58 = mark();
				synPredMatched58 = true;
				inputState.guessing++;
				try {
					{
					match("{//}");
					}
				}
				catch (RecognitionException pe) {
					synPredMatched58 = false;
				}
				rewind(_m58);
inputState.guessing--;
			}
			if ( synPredMatched58 ) {
				match("{//}");
				{
				_loop61:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						{
						match(_tokenSet_0);
						}
					}
					else {
						break _loop61;
					}
					
				} while (true);
				}
				if ( inputState.guessing==0 ) {
					
								_ttype = LINE_COMMENT_TAG;
					
								if (isDebug())
								{
									print("LINE_COMMENT_TAG");
								}
							
				}
			}
			else {
				boolean synPredMatched63 = false;
				if (((LA(1)=='{') && (LA(2)=='*'))) {
					int _m63 = mark();
					synPredMatched63 = true;
					inputState.guessing++;
					try {
						{
						match("{*");
						}
					}
					catch (RecognitionException pe) {
						synPredMatched63 = false;
					}
					rewind(_m63);
inputState.guessing--;
				}
				if ( synPredMatched63 ) {
					match("{*");
					{
					_loop66:
					do {
						if (((LA(1)=='*') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff')))&&( LA(2)!='}' )) {
							match('*');
						}
						else if ((LA(1)=='\r') && (LA(2)=='\n')) {
							match('\r');
							match('\n');
							if ( inputState.guessing==0 ) {
								newline();
							}
						}
						else if ((LA(1)=='\r') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
							match('\r');
							if ( inputState.guessing==0 ) {
								newline();
							}
						}
						else if ((LA(1)=='\n')) {
							match('\n');
							if ( inputState.guessing==0 ) {
								newline();
							}
						}
						else if ((_tokenSet_1.member(LA(1)))) {
							{
							match(_tokenSet_1);
							}
						}
						else {
							break _loop66;
						}
						
					} while (true);
					}
					match("*}");
					if ( inputState.guessing==0 ) {
						
									_ttype = BLOCK_COMMENT_TAG;
						
									if (isDebug())
									{
										print("BLOCK_COMMENT_TAG");
									}
								
					}
				}
				else {
					boolean synPredMatched68 = false;
					if (((LA(1)=='{') && (true))) {
						int _m68 = mark();
						synPredMatched68 = true;
						inputState.guessing++;
						try {
							{
							match("{");
							}
						}
						catch (RecognitionException pe) {
							synPredMatched68 = false;
						}
						rewind(_m68);
inputState.guessing--;
					}
					if ( synPredMatched68 ) {
						match("{");
						if ( inputState.guessing==0 ) {
							
										if (isDebug())
										{
											print("OPEN_TAG");
										}
									
						}
					}
					else {
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}}
					}
				}
				else if ((LA(1)=='{') && (true)) {
					match("{");
					if ( inputState.guessing==0 ) {
						
									_ttype = LCURLY;
						
									if (isDebug())
									{
										print("LCURLY");
									}
								
					}
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				
				if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
					_token = makeToken(_ttype);
					_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
				}
				_returnToken = _token;
			}
			
	public final void mCLOSE_TAG2(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CLOSE_TAG2;
		int _saveIndex;
		
		if (((LA(1)=='}') && (true))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY)) {
			{
			match("}");
			if ( inputState.guessing==0 ) {
				
							if (isDebug())
							{
								print("CLOSE_TAG");
							}
						
			}
			}
		}
		else if ((LA(1)=='}') && (true)) {
			match("}");
			if ( inputState.guessing==0 ) {
				
							_ttype = RCURLY;
				
							if (isDebug())
							{
								print("RCURLY");
							}
						
			}
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mOPEN_TAG3(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OPEN_TAG3;
		int _saveIndex;
		
		if (((LA(1)=='<') && (LA(2)=='%'))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.ANGLE_PERCENT)) {
			{
			boolean synPredMatched74 = false;
			if (((LA(1)=='<') && (LA(2)=='%'))) {
				int _m74 = mark();
				synPredMatched74 = true;
				inputState.guessing++;
				try {
					{
					match("<%//%>");
					}
				}
				catch (RecognitionException pe) {
					synPredMatched74 = false;
				}
				rewind(_m74);
inputState.guessing--;
			}
			if ( synPredMatched74 ) {
				match("<%//%>");
				{
				_loop77:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						{
						match(_tokenSet_0);
						}
					}
					else {
						break _loop77;
					}
					
				} while (true);
				}
				if ( inputState.guessing==0 ) {
					
								_ttype = LINE_COMMENT_TAG;
					
								if (isDebug())
								{
									print("LINE_COMMENT_TAG");
								}
							
				}
			}
			else {
				boolean synPredMatched79 = false;
				if (((LA(1)=='<') && (LA(2)=='%'))) {
					int _m79 = mark();
					synPredMatched79 = true;
					inputState.guessing++;
					try {
						{
						match("<%*");
						}
					}
					catch (RecognitionException pe) {
						synPredMatched79 = false;
					}
					rewind(_m79);
inputState.guessing--;
				}
				if ( synPredMatched79 ) {
					match("<%*");
					{
					_loop82:
					do {
						if (((LA(1)=='*') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff')))&&( LA(2)!= '<' && LA(3) != '%')) {
							match('*');
						}
						else if ((LA(1)=='\r') && (LA(2)=='\n')) {
							match('\r');
							match('\n');
							if ( inputState.guessing==0 ) {
								newline();
							}
						}
						else if ((LA(1)=='\r') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
							match('\r');
							if ( inputState.guessing==0 ) {
								newline();
							}
						}
						else if ((LA(1)=='\n')) {
							match('\n');
							if ( inputState.guessing==0 ) {
								newline();
							}
						}
						else if ((_tokenSet_1.member(LA(1)))) {
							{
							match(_tokenSet_1);
							}
						}
						else {
							break _loop82;
						}
						
					} while (true);
					}
					match("*%>");
					if ( inputState.guessing==0 ) {
						
									_ttype = BLOCK_COMMENT_TAG;
						
									if (isDebug())
									{
										print("BLOCK_COMMENT_TAG");
									}
								
					}
				}
				else {
					boolean synPredMatched84 = false;
					if (((LA(1)=='<') && (LA(2)=='%'))) {
						int _m84 = mark();
						synPredMatched84 = true;
						inputState.guessing++;
						try {
							{
							match("<%");
							}
						}
						catch (RecognitionException pe) {
							synPredMatched84 = false;
						}
						rewind(_m84);
inputState.guessing--;
					}
					if ( synPredMatched84 ) {
						match("<%");
						if ( inputState.guessing==0 ) {
							
										if (isDebug())
										{
											print("OPEN_TAG");
										}
									
						}
					}
					else {
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}}
					}
				}
				else if ((LA(1)=='<') && (true)) {
					match("<");
					if ( inputState.guessing==0 ) {
						
									_ttype = LESS_THAN;
						
									if (isDebug())
									{
										print("LESS_THAN");
									}
								
					}
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				
				if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
					_token = makeToken(_ttype);
					_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
				}
				_returnToken = _token;
			}
			
	public final void mCLOSE_TAG3(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CLOSE_TAG3;
		int _saveIndex;
		
		if (((LA(1)=='%') && (LA(2)=='>'))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.ANGLE_PERCENT)) {
			{
			match("%>");
			if ( inputState.guessing==0 ) {
				
							if (isDebug())
							{
								print("CLOSE_TAG");
							}
						
			}
			}
		}
		else if ((LA(1)=='%') && (true)) {
			match("%");
			if ( inputState.guessing==0 ) {
				
							_ttype = PERCENT;
				
							if (isDebug())
							{
								print("PERCENT");
							}
						
			}
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDIVIDE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIVIDE;
		int _saveIndex;
		
		match('/');
		{
		if (((LA(1)=='}') && (LA(2)=='}'))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY)) {
			match("}}");
			if ( inputState.guessing==0 ) {
				
							_ttype = BLOCK_CLOSE_TAG1;
				
							if (isDebug())
							{
								print("BLOCK_CLOSE_TAG1");
							}
						
			}
		}
		else if (((LA(1)=='}') && (true))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY)) {
			match("}");
			if ( inputState.guessing==0 ) {
				
							_ttype = BLOCK_CLOSE_TAG2;
				
							if (isDebug())
							{
								print("BLOCK_CLOSE_TAG2");
							}
						
			}
		}
		else if (((LA(1)=='%'))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.ANGLE_PERCENT)) {
			match("%>");
			if ( inputState.guessing==0 ) {
				
							_ttype = BLOCK_CLOSE_TAG3;
				
							if (isDebug())
							{
								print("BLOCK_CLOSE_TAG3");
							}
						
			}
		}
		else {
			if ( inputState.guessing==0 ) {
				
							if (isDebug())
							{
								print("DIVIDE");
							}
						
			}
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long[] mk_tokenSet_0() {
		long[] data = new long[8];
		data[0]=-9217L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = new long[8];
		data[0]=-4398046520321L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	
	}
