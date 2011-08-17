// $ANTLR 2.7.7 (2006-11-01): "FileLexer.g" -> "FileLexer.java"$

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
public class FileLexer extends antlr.CharScanner implements FileLexerTokenTypes, TokenStream
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
		System.out.println("FileLexer : " + s);
	}
public FileLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public FileLexer(Reader in) {
	this(new CharBuffer(in));
}
public FileLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public FileLexer(LexerSharedInputState state) {
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
				case '%':  case '&':  case '\'':  case '(':
				case ')':  case '*':  case '+':  case ',':
				case '-':  case '.':  case '/':  case '0':
				case '1':  case '2':  case '3':  case '4':
				case '5':  case '6':  case '7':  case '8':
				case '9':  case ':':  case ';':  case '=':
				case '>':  case '?':  case '@':  case 'A':
				case 'B':  case 'C':  case 'D':  case 'E':
				case 'F':  case 'G':  case 'H':  case 'I':
				case 'J':  case 'K':  case 'L':  case 'M':
				case 'N':  case 'O':  case 'P':  case 'Q':
				case 'R':  case 'S':  case 'T':  case 'U':
				case 'V':  case 'W':  case 'X':  case 'Y':
				case 'Z':  case '[':  case '\\':  case ']':
				case '^':  case '_':  case '`':  case 'a':
				case 'b':  case 'c':  case 'd':  case 'e':
				case 'f':  case 'g':  case 'h':  case 'i':
				case 'j':  case 'k':  case 'l':  case 'm':
				case 'n':  case 'o':  case 'p':  case 'q':
				case 'r':  case 's':  case 't':  case 'u':
				case 'v':  case 'w':  case 'x':  case 'y':
				case 'z':  case '|':  case '~':  case '\u007f':
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
				case '}':
				{
					mCLOSE_TAG(true);
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
				default:
					if (((LA(1)=='{') && (LA(2)=='{'))&&(
		engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY
	)) {
						mOPEN_TAG1(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='{') && (true)) {
						mOPEN_TAG2(true);
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
		int _cnt3=0;
		_loop3:
		do {
			switch ( LA(1)) {
			case '!':  case '"':  case '#':  case '$':
			case '%':  case '&':  case '\'':  case '(':
			case ')':  case '*':  case '+':  case ',':
			case '-':  case '.':  case '/':  case '0':
			case '1':  case '2':  case '3':  case '4':
			case '5':  case '6':  case '7':  case '8':
			case '9':  case ':':  case ';':
			{
				matchRange('\u0021','\u003b');
				break;
			}
			case '=':  case '>':  case '?':  case '@':
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':  case 'G':  case 'H':
			case 'I':  case 'J':  case 'K':  case 'L':
			case 'M':  case 'N':  case 'O':  case 'P':
			case 'Q':  case 'R':  case 'S':  case 'T':
			case 'U':  case 'V':  case 'W':  case 'X':
			case 'Y':  case 'Z':  case '[':  case '\\':
			case ']':  case '^':  case '_':  case '`':
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('\u003d','\u007a');
				break;
			}
			case '|':
			{
				match('\u007c');
				break;
			}
			case '~':  case '\u007f':
			{
				matchRange('\u007e','\u007f');
				break;
			}
			default:
			{
				if ( _cnt3>=1 ) { break _loop3; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			}
			_cnt3++;
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
		
				int m = mark();
			
		
		if (!(
		engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY
	))
		  throw new SemanticException("\n\t\tengineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY\n\t");
		match("{");
		{
		int _cnt6=0;
		_loop6:
		do {
			if ((LA(1)=='{')) {
				match("{");
			}
			else {
				if ( _cnt6>=1 ) { break _loop6; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt6++;
		} while (true);
		}
		
				String t = new String(text.getBuffer(),_begin,text.length()-_begin);
		
				if (t.length() == 2)
				{
					rewind(m);
		
					// ** DEBUG **
					if (isDebug())
					{
						print("OPEN TAG - " + new String(text.getBuffer(),_begin,text.length()-_begin));
					}
		
					_ttype = OPEN_TAG;
				}
				else
				{
					commit();
		
					text.setLength(_begin); text.append(t.substring(1));
		
					_ttype = TEXT;
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
		
				int m = mark();
			
		
		if (((LA(1)=='{') && (true))&&( engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY )) {
			{
			int _cnt9=0;
			_loop9:
			do {
				if ((LA(1)=='{')) {
					match("{");
				}
				else {
					if ( _cnt9>=1 ) { break _loop9; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt9++;
			} while (true);
			}
			
						String t = new String(text.getBuffer(),_begin,text.length()-_begin);
			
						if (t.length() == 1)
						{
							rewind(m);
			
							// ** DEBUG **
							if (isDebug())
							{
								print("OPEN TAG - " + new String(text.getBuffer(),_begin,text.length()-_begin));
							}
			
							_ttype = OPEN_TAG;
						}
						else
						{
							commit();
			
							text.setLength(_begin); text.append(t.substring(1));
			
							_ttype = TEXT;
						}
					
		}
		else if ((LA(1)=='{') && (true)) {
			match("{");
			
						commit();
			
						// ** DEBUG **
						if (isDebug())
						{
							print("TEXT - " + new String(text.getBuffer(),_begin,text.length()-_begin));
						}
			
						_ttype = TEXT;
					
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
		
				int m = mark();
			
		
		if (((LA(1)=='<') && (LA(2)=='%'))&&(engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.ANGLE_PERCENT)) {
			match("<%");
			
						rewind(m);
			
						// ** DEBUG **
						if (isDebug())
						{
							print("OPEN TAG - " + new String(text.getBuffer(),_begin,text.length()-_begin));
						}
			
						_ttype = OPEN_TAG;
					
		}
		else if ((LA(1)=='<') && (true)) {
			match("<");
			
						commit();
			
						// ** DEBUG **
						if (isDebug())
						{
							print("TEXT - " + new String(text.getBuffer(),_begin,text.length()-_begin));
						}
			
						_ttype = TEXT;
					
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
	
	public final void mCLOSE_TAG(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CLOSE_TAG;
		int _saveIndex;
		
		{
		int _cnt13=0;
		_loop13:
		do {
			if ((LA(1)=='}')) {
				match("}");
			}
			else {
				if ( _cnt13>=1 ) { break _loop13; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt13++;
		} while (true);
		}
		
				String t = new String(text.getBuffer(),_begin,text.length()-_begin);
		
				if	(
						(
							( engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY )
						&&	(t.length() > 1)
						)
					||	(
							( engineConfig.getTagDelimeter() == TemplateEngineConfig.TagDelimeter.CURLY_CURLY )
						&&	(t.length() > 2)
						)
					)
				{
					text.setLength(_begin); text.append(t.substring(1));
				}
		
				_ttype = TEXT;
			
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
		int _cnt16=0;
		_loop16:
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
				if ( _cnt16>=1 ) { break _loop16; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			}
			_cnt16++;
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
	
	
	
	}
