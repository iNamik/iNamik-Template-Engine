/*
 * $Id: TagParser.g,v 1.18 2006-11-21 09:34:42 dave Exp $
 *
 * iNamik Template Engine
 * Copyright (C) 2003-2006 David Farrell (davidpfarrell@yahoo.com)
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
header
{
	package com.inamik.template.antlr;

	import antlr.TokenStreamSelector;

	import com.inamik.template.*;
	import com.inamik.template.tags.*;
	import com.inamik.template.tokens.*;
}

{@SuppressWarnings("all")}
class TagParser extends Parser;
options
{
	k=1;
	importVocab=TagLexer;
//	defaultErrorHandler=false;
}

/*
 * Class Code
 */
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
}

/*
 * start_tag (Start Rule)
 */
start_tag returns [Tag tag = null]
	{
		boolean closeBlock = false;
	}
	:
	(
		OPEN_TAG1
		(
			( tag=tag2 ( (BLOCK_CLOSE_TAG1 {closeBlock = true;}) | CLOSE_TAG1) { if (tag == null) tag = NullTag.getInstance(); } )
		|
			( (tag=endBlockTag CLOSE_TAG1) | (BLOCK_CLOSE_TAG1 {tag = new EndBlockTag();}) )
		)
	|
		OPEN_TAG2
		(
			( tag=tag2 ( (BLOCK_CLOSE_TAG2 {closeBlock = true;}) | CLOSE_TAG2) { if (tag == null) tag = NullTag.getInstance(); } )
		|
			( (tag=endBlockTag CLOSE_TAG2) | (BLOCK_CLOSE_TAG2 {tag = new EndBlockTag();}) )
		)
	|
		OPEN_TAG3
		(
			( tag=tag2 ( (BLOCK_CLOSE_TAG3 {closeBlock = true;}) | CLOSE_TAG3) { if (tag == null) tag = NullTag.getInstance(); } )
		|
			( (tag=endBlockTag CLOSE_TAG3) | (BLOCK_CLOSE_TAG3 {tag = new EndBlockTag();}) )
		)
	|
		( BLOCK_COMMENT_TAG { tag = BlockCommentTag.getInstance(); } )
	|
		( LINE_COMMENT_TAG  { tag = LineCommentTag.getInstance(); } )
	)
	{
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
	;
	exception
	catch [TokenStreamException tse]
	{
		if (isDebug())
		{
			print("TagParser.tag - TokenStreamException: " + tse);
		}
//		getInputState().reset();
	}
	catch [NoViableAltException nvae]
	{
		if (isDebug())
		{
			print("TagParser.tag - NoViableAltException: " + nvae);
		}
//		getInputState().reset();
	}

/*
 * tag2
 */
tag2 returns [Tag tag = null] :
		(
			// Genreal Expression Tag
			(DOLLAR | QUOTE | SQUOTE) => tag=generalExpressionTag
		|
			// Action Tag
			tag=actionTag
//		|
//			// Variable Tag
//			tag=variableExpressionTag
//		|
//			// Quoted String Tag
//			tag=quotedStringTag
//		|
//			// End Block Tag
//			tag=endBlockTag
		)
		{
			if (tag == null)
			{
				tag = NullTag.getInstance();
			}

			if (isDebug())
			{
				print("Tag2 - " + tag.getTypeName());
			}
		}
	;


/*
 * actionTag
 */
actionTag returns [ ActionTag a = null ]
	{
		FQNameToken             f  = null;
		AttributeListToken      al = null;
		ExpressionToken         e  = null;
		ExpressionListToken     el = null;
		TemplateActionConfig    ac = null;
		IdToken                 i  = null;
		IdListToken             il = null;

		TemplateActionConfig.ParmType parmType = null;
	}
	:
	f=fqname
	{
		// Get parm type for named action
		ac = engineConfig.getActionConfig(f.getFQName());
		parmType = ac.getParmType();
		if (isDebug()) print("Tag " + f.getFQName() + " has parm type: " + parmType);
	}
	(
		// Atributes
		{parmType == TemplateActionConfig.ParmType.ATTRIBUTES}? al=attributeList
		{
			assert al != null;

			a = new ActionTag(f.getFQName(), al);
		}
	|
		// Expression
		{parmType == TemplateActionConfig.ParmType.EXPRESSION}? e=generalExpression
		{
			a = new ActionTag(f.getFQName(), e);
		}
	|
		// Expression List
		{parmType == TemplateActionConfig.ParmType.EXPRESSION_LIST}? el=expressionList
		{
			assert el != null;

			a = new ActionTag(f.getFQName(), el);
		}
	|
		// ID
		{parmType == TemplateActionConfig.ParmType.ID}? i=attributeId
		{
			a = new ActionTag(f.getFQName(), i);
		}
	|
		// ID List
		{parmType == TemplateActionConfig.ParmType.ID_LIST}? il=attributeIdList
		{
			assert il != null;

			a = new ActionTag(f.getFQName(), il);
		}
	|
		// Empty
		{
			assert parmType == TemplateActionConfig.ParmType.EMPTY;

			a = new ActionTag(f.getFQName());
		}
	)
	;

///*
// * variableExpressionTag
// */
//variableExpressionTag returns [ VariableExpressionTag v = null ]
//	{
//		VariableExpressionToken vx = null;
//	}
//	:
//	vx=variableExpressionWithFilters
//	{
//		v = new VariableExpressionTag(vx);
//	}
// 	;

/*
 * generalExpressionTag
 */
generalExpressionTag returns [ ExpressionTag v = null ]
	{
		ExpressionToken x = null;
	}
	:
	x=generalExpressionWithFilters
	{
		v = new ExpressionTag(x);
	}
 	;

///*
// * quotedStringTag
// */
//quotedStringTag returns [ QuotedStringTag q = null ]
//	{
//		QuotedStringToken qs = null;
//	}
//	:
//	qs=quotedStringWithFilters
//	{
//		q = new QuotedStringTag(qs);
//	}
//	;

/*
 * endBlockTag
 */
endBlockTag returns [ EndBlockTag tag = null]
	{
		FQNameToken f = null;
	}
	:
	DIVIDE (f=fqname)?
	{
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
	;

///*
// * variableExpressionWithFilters
// */
//variableExpressionWithFilters returns [ VariableExpressionToken varEx = null ]
//	{
//		FilterListToken f = null;
//	}
//	:
//	varEx = variableExpression ( f=filterList { varEx.setFilterList(f); })?
//	{
//		if (isDebug())
//		{
//			print("VariableExpressionWithFilters: " + varEx.toString());
//		}
//	}
//	;

/*
 * generalExpressionWithFilters
 */
generalExpressionWithFilters returns [ ExpressionToken x = null ]
	{
		FilterListToken f = null;
	}
	:
	x = generalExpression ( f=filterList { x.setFilterList(f); })?
	{
		if (isDebug())
		{
			print("GeneralExpressionWithFilters: " + x.toString());
		}
	}
	;

/*
 * variableExpression
 *
 * Note: This MUST include the leading '$'
 */
variableExpression returns [VariableExpressionToken varEx = null] :
	DOLLAR
	{
		varEx = getVariableExpression();
	}
	;

///*
// * quotedStringWithFilters
// */
//quotedStringWithFilters returns [ QuotedStringToken q = null ]
//	{
//		FilterListToken f = null;
//	}
//	:
//	q=qstring ( f=filterList { q.setFilterList(f); })?
//	{
//		if (isDebug())
//		{
//			print("QuotedStringWithFilters: " + q.toString());
//		}
//	}
//	;

/*
 * qstring - Maches single quotes and double quotes
 */
qstring returns [QuotedStringToken token = null]
	:
	(
		(
			QUOTE
			{
				token = getQString();

				if (isDebug())
				{
					print("QString - " + token.toString());
				}
			}
		)
		|
		(
			SQUOTE
			{
				token = getSQString();

				if (isDebug())
				{
					print("SQString - " + token.toString());
				}
			}
		)
	)
	;

/*
 * attributeList
 */
attributeList returns [ AttributeListToken al = new AttributeListToken() ]
	{
		AttributeToken a = null;
	}
	:
//	(
//		a=attribute
//		{
//			al.addAttribute(a);
//		}
//	)*
	(
		a=attribute
		{
			al.addAttribute(a);
		}
		(
			(COMMA)? a=attribute
			{
				al.addAttribute(a);
			}
		)*
	)?
	;

/*
 * attribute
 */
attribute returns [ AttributeToken a = null ]
	{
		IdToken i = null;
		ExpressionToken e = null;
	}
	:
	i=attributeId (EQUALS|ASSOCIATE) e=generalExpression { a = new AttributeToken(i, e); }
	;

/*
 * attributeId
 * ($)? id
 */
attributeId returns [IdToken i = null ] :
	(DOLLAR)? i=id
	;

/*
 * attributeIdList
 */
attributeIdList returns [IdListToken il = new IdListToken()]
	{
		IdToken i = null;
	}
	:
	(
		i=attributeId
		{
			il.addId(i);
		}
		(
			COMMA i=attributeId
			{
				il.addId(i);
			}
		)*
	)?
	;

/*
 * filterList
 */
filterList returns [ FilterListToken fl = new FilterListToken() ]
	{
		FilterToken f = null;
	}
	:
	(
		PIPE
		f=filter
		{
			fl.addFilter(f);
		}
	)+
	;

/*
 * filter
 */
filter returns [ FilterToken f = null ]
	{
		FQNameToken         n = null;
		ExpressionListToken l = null;
	}
	:
	n=fqname ( LPAREN l=expressionList RPAREN ) ?
	{
		// Just to verify at parse-time that function is registered
		engineConfig.getFilterConfig(n.getFQName());

		if (l == null)
		{
			l = new ExpressionListToken();
		}

		f = new FilterToken(n.getFQName(), l);
	}
	;

/*
 * function
 */
function returns [ FunctionToken f = null ]
	{
		FQNameToken         n = null;
		ExpressionListToken l = null;
	}
	:
	n=fqname LPAREN l=expressionList RPAREN
	{
		// Just to verify at parse-time that function is registered
		engineConfig.getFunctionConfig(n.getFQName());

		f = new FunctionToken(n.getFQName(), l);
	}
	;

// expressionList
expressionList returns [ExpressionListToken xl = new ExpressionListToken()]
	{
		ExpressionToken x = null;
	}
	:
	(
		x=generalExpression
		{
			xl.addExpression(x);
		}
		(
			COMMA x=generalExpression
			{
				xl.addExpression(x);
			}
		)*
	)?
	;

/*
 * number
 */
number returns [NumberToken n = null] :
	n1:NUMBER (DOT n2:NUMBER)?
	{
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
	;



// expressions
// Note that most of these expressions follow the pattern
//   thisLevelExpression :
//       nextHigherPrecedenceExpression
//           (OPERATOR nextHigherPrecedenceExpression)*
// which is a standard recursive definition for a parsing an expression.
// The operators in java have the following precedences:
//    lowest  (13)  = *= /= %= += -= <<= >>= >>>= &= ^= |=
//            (12)  ?:
//            (11)  ||
//            (10)  &&
//            ( 9)  |
//            ( 8)  ^
//            ( 7)  &
//            ( 6)  == !=
//            ( 5)  < <= > >=
//            ( 4)  << >>
//            ( 3)  +(binary) -(binary)
//            ( 2)  * / %
//            ( 1)  ++ -- +(unary) -(unary)  ~  !  (type)
//                  []   () (method call)  . (dot -- identifier qualification)
//                  new   ()  (explicit parenthesis)
//
// the last two are not usually on a precedence chart; I put them in
// to point out that new has a higher precedence than '.', so you
// can validy use
//     new Frame().show()
//
// Note that the above precedence levels map to the rules below...
// Once you have a precedence chart, writing the appropriate rules as below
//   is usually very straightfoward


//expressionTag returns [ExpressionTag tag = null]
//	{
//		ExpressionToken e = null;
//	}
//	:
//	e=variableExpression
//	{
//		tag = new ExpressionTag(e);
//	}
//	;
//
//variableExpression returns [ExpressionToken x = null]
//	{
//		ExpressionToken         left  = null;
//		ExpressionToken         right = null;
//		ExpressionOperatorToken o     = null;
//		ScopeToken              scope = null;
//
//		StringBuffer idBuffer = new StringBuffer();
//
//		ExpressionListToken l = null;
//
//		QuotedStringToken q = null;
//
//		VariableIdToken v = null;
//
//		boolean testAssignment = false;
//	}
//	:
//	left=generalExpression
//	{
//		x = left;
//	}
//	;


//// increment expression
//// NOTE: This is where variable references are matched
//incrementExpression returns [ExpressionToken x = null]
//	{
//		ExpressionOperatorToken o  = null;
//		VariableIdToken         v  = null;
//		ExpressionToken         e  = null;
//		ExpressionListToken     l  = new ExpressionListToken();
//		IdToken                 i  = null;
//		ExpressionListToken     l2 = null;
//
//		ExpressionOperandToken vOperand = null;
//	}
//	:
//	(
//		v=variableId
//		(
//			LBRACKET e=generalExpression RBRACKET
//			{
//				l.addExpression(e);
//			}
//		)*
//		{
//			if (l.size() > 0)
//			{
//				vOperand = new ExpressionOperandToken(new ListElementToken(v, l));
//			}
//			else
//			{
//				vOperand = new ExpressionOperandToken(v);
//			}
//		}
//		(
//			// nothing
//			{
//				x = new ExpressionToken(vOperand);
//			}
//		)
//	)
//	;


// general expression ( logical or (||) ) (level 11)
generalExpression returns [ExpressionToken x = null]
	{
		ExpressionToken         right = null;
		ExpressionOperatorToken o     = null;
	}
	:
	x=logicalAndExpression
	(
		LOGICAL_OR right=generalExpression
		{
			o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LOGICAL_OR);
			x = new ExpressionToken(x, o, right);
		}
	)?
	;

// logical and (&&)  (level 10)
logicalAndExpression returns [ExpressionToken x = null]
	{
		ExpressionToken         right = null;
		ExpressionOperatorToken o     = null;
	}
	:
	x=equalityExpression
	(
		LOGICAL_AND right=logicalAndExpression
		{
			o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LOGICAL_AND);
			x = new ExpressionToken(x, o, right);
		}
	)?
	;

// equality/inequality (==/!=) (level 6)
equalityExpression returns [ExpressionToken x = null]
	{
		ExpressionToken         right = null;
		ExpressionOperatorToken o     = null;
	}
	:
	x=relationalExpression
	(
		o=equalityOperator right=equalityExpression
		{
			x = new ExpressionToken(x, o, right);
		}
	)?
	;

// equality operator
equalityOperator returns [ExpressionOperatorToken o = null]
	:
		NOT_EQUALS    { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.NOT_EQUALS   ); }
	|
		EQUALS_EQUALS { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.EQUALS_EQUALS); }
	;


// boolean relational expressions (level 5)
relationalExpression returns [ExpressionToken x = null]
	{
		ExpressionToken         right = null;
		ExpressionOperatorToken o     = null;
	}
	:
	x=additiveExpression
	(
		o=relationalOperator right=relationalExpression
		{
			x = new ExpressionToken(x, o, right);
		}
	)?
	;

// relational operator
relationalOperator returns [ExpressionOperatorToken o = null]
	:
		LESS_THAN           { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LESS_THAN          ); }
	|
		GREATER_THAN        { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.GREATER_THAN       ); }
	|
		LESS_THAN_EQUALS    { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LESS_THAN_EQUALS   ); }
	|
		GREATER_THAN_EQUALS { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.GREATER_THAN_EQUALS); }
	;

// binary addition/subtraction (level 3)
additiveExpression returns [ExpressionToken x = null]
	{
		ExpressionToken         right = null;
		ExpressionOperatorToken o     = null;
	}
	:
	x=multiplicativeExpression
	(
		o=additiveOperator right=additiveExpression
		{
			x = new ExpressionToken(x, o, right);
		}
	)?
	;

// additive operator
additiveOperator returns [ExpressionOperatorToken o = null]
	:
		PLUS  { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.PLUS);  }
	|
		MINUS { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.MINUS); }
	;


// multiplication/division/modulo (level 2)
multiplicativeExpression returns [ExpressionToken x = null]
	{
		ExpressionToken         right = null;
		ExpressionOperatorToken o     = null;
	}
	:
	x=unaryExpression
	(
		o=multiplicativeOperator right=multiplicativeExpression
		{
			x = new ExpressionToken(x, o, right);
		}
	)?
	;

// multiplicative operator
multiplicativeOperator returns [ExpressionOperatorToken o = null]
	:
		MULTIPLY { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.MULTIPLY); }
	|
		DIVIDE   { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.DIVIDE  ); }
	|
		PERCENT  { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.MODULO  ); }
	;


// unary expression [1.3]
unaryExpression returns [ExpressionToken x = null]
	{
		ExpressionToken         left  = null;
		ExpressionToken         right = null;
		ExpressionOperatorToken o     = null;
	}
	:
	(
		o=unaryOperator right=unaryExpression
		{
			x = new ExpressionToken(o, right);
		}
	|
		left=unaryExpressionNotPlusMinus
		{
			x = left;
		}
	)
	;

// unary operator
unaryOperator returns [ExpressionOperatorToken o = null]
	:
		MINUS { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.MINUS); }
	|
		PLUS  { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.PLUS);  }
	;

// unary expression not plus minus [1.2]
unaryExpressionNotPlusMinus returns [ExpressionToken e = null]
	{
		ExpressionToken         right = null;
		ExpressionOperatorToken o     = null;
		ExpressionOperandToken  p     = null;
	}
	:
	(
		o=unaryOperatorNotPlusMinus right=unaryExpression
		{
			e = new ExpressionToken(o, right);
		}
	|
		p=primaryExpression
		{
			e = new ExpressionToken(p);
		}
	)
	;

// unaryOperatorNotPlusMinus
unaryOperatorNotPlusMinus returns [ExpressionOperatorToken o = null]
	:
		LOGICAL_NOT { o = new ExpressionOperatorToken(ExpressionOperatorToken.Operator.LOGICAL_NOT); }
	;

// the basic element of an expression
primaryExpression returns [ExpressionOperandToken eo = null]
	{
		NumberToken             n = null;
		ExpressionToken         e = null;
		QuotedStringToken       q = null;
		VariableExpressionToken v = null;
		FunctionToken           f = null;
		ExpressionListToken    el = null;
		AttributeListToken     al = null;
		com.inamik.template.tokens.Token t = null;
	}
	:
	(
		n=number
		{
			eo = new ExpressionOperandToken(n);
		}
	|
		(DOLLAR) => v=variableExpression
		{
			eo = new ExpressionOperandToken(v);
		}
	|
		q=qstring
		{
			eo = new ExpressionOperandToken(q);
		}
	|
		// Predicate to distinguish function from simpleText
		{LA(2) == COLON || LA(2) == LPAREN}? f=function
		{
			eo = new ExpressionOperandToken(f);
		}
	|
		LPAREN e=generalExpression RPAREN
		{
			eo = new ExpressionOperandToken(e);
		}
	|	t=simpleTextOrBoolean
		{
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
	|
		LBRACKET el=expressionList RBRACKET
		{
			eo = new ExpressionOperandToken(el);
		}
	|
		(LCURLY | OPEN_TAG2) al=attributeList (RCURLY | CLOSE_TAG2)
		{
			eo = new ExpressionOperandToken(al);
		}
	)
	;

/*
 * id
 */
id returns [IdToken token = null] :
	i:ID
	{
		token = new IdToken(i.getText());

		if (isDebug())
		{
			print("id : " + token.toString());
		}
	}
	;

/*
 * fqname
 */
fqname returns [FQNameToken ft = null]
	{
		IdToken i1 = null;
		IdToken i2 = null;
	}
	:
	i1=id (COLON i2=id)?
	{
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
	;

/*
 * simpleTextOrBoolean
 */
simpleTextOrBoolean returns [ com.inamik.template.tokens.Token t = null ] :
	i:ID
	{
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
	;

/*
 * indexedGeneralExpression (Start Rule)
 * NOTE: Assumes leading '['
 */
start_indexedGeneralExpression returns [ ExpressionToken token = null ] :
	token=generalExpression RBRACKET
	{
		if (isDebug())
		{
			print("IndexedGeneralExpression - [" + token + "]");
		}
	}
	;

/*
 * bracedGeneralExpression (Start Rule)
 * NOTE: Assumes leading '{'
 */
start_bracedGeneralExpression returns [ ExpressionToken token = null ] :
	token=generalExpression ( RCURLY | CLOSE_TAG2 )
	{
		if (isDebug())
		{
			print("BracedGeneralExpression - {" + token + "}");
		}
	}
	;
