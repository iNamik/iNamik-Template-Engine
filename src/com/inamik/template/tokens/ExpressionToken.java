/*
 * $Id: ExpressionToken.java,v 1.7 2006-08-14 22:31:00 dave Exp $
 *
 * iNamik Template Engine
 * Copyright (C) 2006 David Farrell (davidpfarrell@yahoo.com)
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
package com.inamik.template.tokens;

import java.io.Serializable;

/**
 * ExpressionToken
 */
public class ExpressionToken extends AbstractToken implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = -4053973105464544094L;
	private ExpressionOperandToken  leftOperand  = null;
	private ExpressionOperandToken  rightOperand = null;
	private ExpressionOperatorToken operator     = null;
	private FilterListToken filterList = null;

	/**
	 * Constructor w/Operand
	 */
	public ExpressionToken(ExpressionOperandToken leftOperand)
	{
		this();

		// Normalize
		if (leftOperand.getTokenType() == TokenType.EXPRESSION)
		{
			ExpressionToken expression =
				(ExpressionToken)leftOperand.getToken();

			this.leftOperand	  = expression.getLeftOperand();
			this.rightOperand     = expression.getRightOperand();
			this.operator		  = expression.getOperator();
		}
		else
		{
			this.leftOperand = leftOperand;
		}
	}

	/**
	 * Constructor w/Operand Operator Operand
	 */
	public ExpressionToken
	(
		ExpressionOperandToken leftOperand,
		ExpressionOperatorToken operator,
		ExpressionOperandToken rightOperand
	)
	{
		this();

		this.leftOperand	  = leftOperand;
		this.rightOperand     = rightOperand;
		this.operator		  = operator;
	}

//	/**
//	 * Constructor w/Operand Operator
//	 */
//	public ExpressionToken
//	(
//		ExpressionOperandToken leftOperand,
//		ExpressionOperatorToken operator
//	)
//	{
//		this();
//
//		this.leftOperand     = leftOperand;
//		this.operator		 = operator;
//	}

	/**
	 * Constructor w/Operator Operand
	 */
	public ExpressionToken
	(
		ExpressionOperatorToken operator,
		ExpressionOperandToken rightOperand
	)
	{
		this();

		this.operator		  = operator;
		this.rightOperand     = rightOperand;
	}


	/**
	 * Constructor w/Expression Operator Expression
	 */
	public ExpressionToken
	(
		ExpressionToken leftExpression,
		ExpressionOperatorToken operator,
		ExpressionToken rightExpression
	)
	{
		this();

		// Is left expression a single expression?
		if (leftExpression.isSingleOperand() == true)
		{
			this.leftOperand = leftExpression.getLeftOperand();
		}
		else
		{
			this.leftOperand = new ExpressionOperandToken(leftExpression);
		}

		this.operator = operator;

		// Is right expression a single expression?
		if (rightExpression.isSingleOperand() == true)
		{
			this.rightOperand = rightExpression.getLeftOperand();
		}
		else
		{
			this.rightOperand = new ExpressionOperandToken(rightExpression);
		}
	}

//	/**
//	 * Constructor w/Expression Operator
//	 */
//	public ExpressionToken
//	(
//		ExpressionToken leftExpression,
//		ExpressionOperatorToken operator
//	)
//	{
//		this();
//
//		// Is left expression a single expression?
//		if (leftExpression.isSingleOperand() == true)
//		{
//			this.leftOperand = leftExpression.getLeftOperand();
//		}
//		else
//		{
//			this.leftOperand = new ExpressionOperandToken(leftExpression);
//		}
//
//		this.operator = operator;
//	}

	/**
	 * Constructor w/Operator Expression
	 */
	public ExpressionToken
	(
		ExpressionOperatorToken operator,
		ExpressionToken rightExpression
	)
	{
		this();

		this.operator = operator;

		// Is right expression a single expression?
		if (rightExpression.isSingleOperand() == true)
		{
			this.rightOperand = rightExpression.getLeftOperand();
		}
		else
		{
			this.rightOperand = new ExpressionOperandToken(rightExpression);
		}
	}

	/**
	 * Constructor (Default, Private)
	 */
	private ExpressionToken()
	{
		super();
	}

	/**
	 * isSingleOperand (Package-Private) - This is a simple helper method
	 * for normalizing ExpressionOperandTokens
	 */
	boolean isSingleOperand()
	{
		if (
			(leftOperand != null)
				&& (operator == null)
				&& (rightOperand == null)
		)
		{
			return true;
		}

		return false;
	}

	/**
	 * getLeftOperand
	 */
	public ExpressionOperandToken getLeftOperand()
	{
		return leftOperand;
	}

	/**
	 * getRightOperand
	 */
	public ExpressionOperandToken getRightOperand()
	{
		return rightOperand;
	}

	/**
	 * getOperator
	 */
	public ExpressionOperatorToken getOperator()
	{
		return operator;
	}

	/**
	 * setFilterList
	 */
	public void setFilterList(FilterListToken filterList)
	{
		this.filterList = filterList;
	}

	/**
	 * getFilterList
	 */
	public FilterListToken getFilterList()
	{
		return this.filterList;
	}

	public TokenType getType()
	{
		return TokenType.EXPRESSION;
	}

	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer();

		if (leftOperand != null)
		{
			result.append(leftOperand.toString());
		}

		if (operator != null)
		{
			if (leftOperand != null && rightOperand != null)
			{
				result.append(" ");
			}

			result.append(operator.toString());

			if (leftOperand != null && rightOperand != null)
			{
				result.append(" ");
			}
		}

		if (rightOperand != null)
		{
			result.append(rightOperand.toString());
		}

		return result.toString();
	}
}
