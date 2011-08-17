// $ANTLR 2.7.7 (2006-11-01): "TextBodyLexer.g" -> "TextBodyLexer.java"$

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
public class TextBodyLexer extends antlr.CharScanner implements TextBodyLexerTokenTypes, TokenStream
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
		System.out.println("TextBodyLexer : " + s);
	}
public TextBodyLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public TextBodyLexer(Reader in) {
	this(new CharBuffer(in));
}
public TextBodyLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public TextBodyLexer(LexerSharedInputState state) {
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
				case '!':  case '"':  case '#':  case '$':
				case '&':  case '\'':  case '(':  case ')':
				case '*':  case '+':  case ',':  case '-':
				case '.':  case ';':  case '=':  case '?':
				case '@':  case '[':  case '\\':  case ']':
				case '^':  case '`':  case '|':  case '~':
				case '\u007f':
				{
					mTEXT(true);
					theRetToken=_returnToken;
					break;
				}
				case '<':
				{
					mOPEN_TAG3(true);
					theRetToken=_returnToken;
					break;
				}
				case '%':
				{
					mCLOSE_TAG3(true);
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
				case '/':
				{
					mSLASH(true);
					theRetToken=_returnToken;
					break;
				}
				case ':':
				{
					mCOLON(true);
					theRetToken=_returnToken;
					break;
				}
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
				default:
					if (((LA(1)=='{') && (LA(2)=='{'))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY)) {
						mOPEN_TAG1(true);
						theRetToken=_returnToken;
					}
					else if (((LA(1)=='}') && (LA(2)=='}'))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY)) {
						mCLOSE_TAG1(true);
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

	public final void mTEXT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TEXT;
		int _saveIndex;
		
		{
		int _cnt4=0;
		_loop4:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				{
				match(_tokenSet_0);
				}
			}
			else {
				if ( _cnt4>=1 ) { break _loop4; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt4++;
		} while (true);
		}
		
				// ** DEBUG **
				if (isDebug())
				{
					print("TEXT - " + new String(text.getBuffer(),_begin,text.length()-_begin));
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
		match("{{");
		
					_ttype = OPEN_TAG;
		
					if (isDebug())
					{
						print("OPEN_TAG1");
					}
				
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
		
					_ttype = CLOSE_TAG;
		
					if (isDebug())
					{
						print("CLOSE_TAG1");
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
			match("{");
			
						_ttype = OPEN_TAG;
			
						if (isDebug())
						{
							print("OPEN_TAG");
						}
					
			}
		}
		else if ((LA(1)=='{') && (true)) {
			match("{");
			
						_ttype = TEXT;
			
						if (isDebug())
						{
							print("TEXT - " + new String(text.getBuffer(),_begin,text.length()-_begin));
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
			
						_ttype = CLOSE_TAG;
			
						if (isDebug())
						{
							print("CLOSE_TAG");
						}
					
			}
		}
		else if ((LA(1)=='}') && (true)) {
			match("}");
			
						_ttype = TEXT;
			
						if (isDebug())
						{
							print("TEXT - " + new String(text.getBuffer(),_begin,text.length()-_begin));
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
			match("<%");
			
						_ttype = OPEN_TAG;
			
						if (isDebug())
						{
							print("OPEN_TAG");
						}
					
			}
		}
		else if ((LA(1)=='<') && (true)) {
			match("<");
			
						_ttype = TEXT;
			
						if (isDebug())
						{
							print("TEXT - " + new String(text.getBuffer(),_begin,text.length()-_begin));
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
			
						_ttype = CLOSE_TAG;
			
						if (isDebug())
						{
							print("CLOSE_TAG");
						}
					
			}
		}
		else if ((LA(1)=='%') && (true)) {
			match("%");
			
						_ttype = TEXT;
			
						if (isDebug())
						{
							print("TEXT - " + new String(text.getBuffer(),_begin,text.length()-_begin));
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
	
	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		int _saveIndex;
		
		{
		int _cnt19=0;
		_loop19:
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
				if ( _cnt19>=1 ) { break _loop19; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			}
			_cnt19++;
		} while (true);
		}
		
				// ** DEBUG **
				if (isDebug())
				{
					print("WS");
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
			match('\r');
			{
			if ((LA(1)=='\n')) {
				match('\n');
			}
			else {
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
		
				newline();
		
				// ** DEBUG **
				if (isDebug())
				{
					print("ENDL");
				}
			
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SLASH;
		int _saveIndex;
		
		match('/');
		
				// ** DEBUG **
				if (isDebug())
				{
					print("SLASH");
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
		
				// ** DEBUG **
				if (isDebug())
				{
					print("COLON");
				}
			
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
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
		_loop28:
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
				break _loop28;
			}
			}
		} while (true);
		}
		
				if (isDebug())
				{
					print("ID - " + new String(text.getBuffer(),_begin,text.length()-_begin));
				}
			
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long[] mk_tokenSet_0() {
		long[] data = new long[8];
		data[0]=-6340927683878191104L;
		data[1]=-3458764507512307711L;
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	
	}
