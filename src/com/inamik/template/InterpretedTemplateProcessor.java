/*
 * $Id: InterpretedTemplateProcessor.java,v 1.14 2006-11-21 09:34:42 dave Exp $
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
package com.inamik.template;

import com.inamik.template.TemplateActionConfig.BodyContent;
import com.inamik.template.TemplateActionTag.ExecuteResult;
import com.inamik.template.tags.*;
import com.inamik.template.tokens.*;
import com.inamik.template.util.ClassLoaderUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * InterpretedTemplateProcessor
 * Created on Jul 12, 2006
 * @author Dave
 */
public final class InterpretedTemplateProcessor implements TemplateProcessor
{
	private TokenizedTemplate compileUnit = null;
	private TemplateContext   context     = null;

	private static interface MathFunctor
	{
		double execute(double leftOp, double rightOp);
	}

	private static interface RelationFunctor
	{
		boolean execute(final int compare);
	}

	/**
	 * Constructor
	 */
	public InterpretedTemplateProcessor(final TokenizedTemplate compileUnit)
	{
		super();

		if (compileUnit == null)
		{
			throw new NullPointerException("compileUnit");
		}

		this.compileUnit = compileUnit;
	}

	/**
	 * process
	 */
	public void process(final TemplateContext aContext) throws TemplateException
	{
		if (aContext == null)
		{
			throw new NullPointerException("context");
		}

		this.context = aContext;

		Tag tag = null;

		try
		{
			for (int i = 0, n = compileUnit.size(); i < n; ++i)
			{
				tag = compileUnit.get(i);

				assert tag != null;

				try
				{
					processTag(tag);
				}
				catch (TemplateException te)
				{
					te.setFilename(tag.getFilename());
					te.setLine(tag.getLine());
					te.setColumn(tag.getColumn());
					throw te;
				}
				catch (Exception e)
				{
					TemplateException te = new TemplateException(e);
					te.setFilename(tag.getFilename());
					te.setLine(tag.getLine());
					te.setColumn(tag.getColumn());
					throw te;
				}
			}
		}
		finally
		{
			context.flush();
		}
	}

	/**
	 * processTag
	 */
	private void processTag(final Tag tag) throws Exception
	{
		if (context == null)
		{
			throw new IllegalStateException("context == null");
		}

		switch (tag.getType())
		{
			// action
			case ACTION :
			{
				process((ActionTag)tag);

				break;
			}

			// body-action
			case BODY_ACTION :
			{
				process((BodyActionTag)tag);

				break;
			}

			// endl
			case ENDL:
			{
				process((EndlTag)tag);

				break;
			}

			// eof
			case EOF :
			{
				process((EofTag)tag);

				break;
			}

//			// quoted-string
//			case QUOTED_STRING :
//			{
//				process((QuotedStringTag)tag);
//
//				break;
//			}

			// space
			case SPACE :
			{
				process((SpaceTag)tag);

				break;
			}

			// leading-space
			case LEADING_SPACE:
			{
				process((LeadingSpaceTag)tag);

				break;
			}

			// text
			case TEXT :
			{
				process((TextTag)tag);

				break;
			}

//			// variable-expression
//			case VARIABLE_EXPRESSION :
//			{
//				process((VariableExpressionTag)tag);
//
//				break;
//			}

			// expression
			case EXPRESSION :
			{
				process((ExpressionTag)tag);

				break;
			}

			default :
				System.err.println("Unknown Tag Type : " + tag.getTypeName());
		}
	}

	/**
	 * process w/BodyActionTag
	 */
	private void process(final BodyActionTag tag) throws Exception
	{
		context.getOut().beginBlockActionTag(tag.getAction().isInline());

//		try
//		{
			TemplateEngineConfig config = context.getConfig();

			int index = -1;

			boolean quit = false;

			while (quit == false)
			{
				BodyActionTag bat;

				// Hack so we can use the same loop for the initial body
				// and for the alt-bodies
				if (index == -1)
				{
					bat = tag;
				}
				else
				if (index < tag.getAltBodySize())
				{
					bat = tag.getAltBodyElement(index);
				}
				else
				{
					bat  = null;
					quit = true;
				}

				index++;

				if (bat != null)
				{
					ActionTag actionTag = bat.getAction();

					TemplateTagName actionName = actionTag.getFQName();

					TemplateActionConfig actionConfig = config.getActionConfig(actionName);

					Class clazz = Class.forName(actionConfig.getClazz());

					TemplateActionTag action = (TemplateActionTag)clazz.newInstance();

					action.setContext(context);

					// TODO Create an action pool so these lifecycle methods
					// have meaning
					action.init();

					action.reset();

					boolean processAction = true;

					while (processAction == true)
					{
						context.pushAction(action);

						TemplateActionTag.ExecuteResult executeResult = callActionExecute(action, actionTag.getParm());

						boolean processBody = true;

						while (processBody == true)
						{
							TemplateBodyContent bodyContent;
							TemplateMacro       macro;

							switch (executeResult)
							{
								case OK :
								{
									if (actionConfig.getBodyContent() == BodyContent.MACRO)
									{
										TokenizedTemplate macroCompileUnit =
											new TokenizedTemplate();

										MultipleTagsTag macroMtt = bat.getBody();

										for (int i = 0, n = macroMtt.size(); i < n; ++i)
										{
											macroCompileUnit.add(macroMtt.get(i));
										}

										TemplateProcessor macroProcessor =
											new InterpretedTemplateProcessor(macroCompileUnit);

										macro = new TemplateMacro(macroProcessor);

										bodyContent = null;
									}
									else
									{
										assert	(
													(actionConfig.getBodyContent() == BodyContent.TEMPLATE)
												||	(actionConfig.getBodyContent() == BodyContent.TEXT)
												);

										context.startCapture();
										process(bat.getBody());
										bodyContent = context.stopCapture();
										macro = null;
									}
									quit = true;
									break;
								}

								case SKIP_ALL :
								{
									bodyContent = null;
									macro       = null;
									quit = true;
									break;
								}

								case SKIP_BODY :
								{
									bodyContent = null;
									macro       = null;
									break;
								}

								default :
								{
									throw new RuntimeException("Unknown TemplateActionTag ExecuteResult Type: " + executeResult);
								}
							}

							TemplateActionTag.AfterBodyResult afterBodyResult;

							if (bodyContent != null)
							{
								assert	(
										(actionConfig.getBodyContent() == BodyContent.TEMPLATE)
									||	(actionConfig.getBodyContent() == BodyContent.TEXT)
									);

								assert macro == null;

								afterBodyResult = action.afterBody(bodyContent);

//								String body = bodyContent.getBodyContent();
//
//								if (body != null)
//								{
//									context.getOut().print(body);
//								}
							}
							else
							if (macro != null)
							{
								assert actionConfig.getBodyContent() == BodyContent.MACRO;
								assert bodyContent == null;

								afterBodyResult = action.afterBody(macro);
							}
							else
							{
								assert macro == null;
								assert	(
											(executeResult == ExecuteResult.SKIP_BODY)
										||	(executeResult == ExecuteResult.SKIP_ALL)
										);

								bodyContent = null;

								afterBodyResult = action.afterBody();

//								String body = bodyContent.getBodyContent();
//
//								if (body != null)
//								{
//									context.getOut().print(body);
//								}
							}

							context.getOut().flush();

							processBody   = (afterBodyResult == TemplateActionTag.AfterBodyResult.REPEAT_BODY);
							processAction = (afterBodyResult == TemplateActionTag.AfterBodyResult.REPEAT_ACTION);
						}

						TemplateActionTag poppedAction = context.popAction();

						assert poppedAction == action;
					}

					action.destroy();
				}
			}
//		}
//		catch (RuntimeException e)
//		{
//			throw e;
//		}
//		catch (Exception e)
//		{
//			throw new RuntimeException(e);
//		}

		context.getOut().endBlockActionTag();
	}

	/**
	 * process w/MultipleTagsTag
	 */
	private void process(final MultipleTagsTag tag) throws Exception
	{
		for (int i = 0, n = tag.size(); i < n; ++i)
		{
			Tag t = tag.get(i);
			processTag(t);
		}
	}

	/**
	 * process w/ActionTag
	 * @throws TemplateException
	 */
	private void process(final ActionTag tag) throws TemplateException
	{
//		try
//		{
			TemplateEngineConfig config = context.getConfig();

			TemplateTagName actionName = tag.getFQName();

			TemplateActionConfig actionConfig = config.getActionConfig(actionName);

			if	(
					(actionConfig.getBlockType()   != TemplateActionConfig.BlockType.EMPTY)
				||	(actionConfig.getBodyContent() != TemplateActionConfig.BodyContent.EMPTY)
				)
			{
				context.getPrintBuffer().beginActionTag();
			}

			TemplateActionTag action = (TemplateActionTag)ClassLoaderUtil.createNewInstance(actionConfig.getClazz());

			if (action == null)
			{
				throw new RuntimeException("Unable to instantiate ActionTag class '" + actionConfig.getClazz() + "'");
			}

			action.setContext(context);

			action.init();

			action.reset();

			callActionExecute(action, tag.getParm());

			action.afterBody();

			action.destroy();

			if	(
					(actionConfig.getBlockType()   != TemplateActionConfig.BlockType.EMPTY)
				||	(actionConfig.getBodyContent() != TemplateActionConfig.BodyContent.EMPTY)
				)
			{
				context.getPrintBuffer().endActionTag();
			}

//		}
//		catch (RuntimeException e)
//		{
//			throw e;
//		}
//		catch (Exception e)
//		{
//			throw new RuntimeException(e);
//		}
	}

	/**
	 * callActionExecute
	 * @throws TemplateException
	 */
	private ExecuteResult callActionExecute(final TemplateActionTag action, final Token token) throws TemplateException
	{
		/*
		 * ExpressionToken
		 * VariableExpressionToken
		 * AttributeListToken
		 * ExpressionListToken
		 * EmptyToken
		 */

		ExecuteResult executeResult;

		switch (token.getType())
		{
			case EXPRESSION :
			{
				TemplateVariable expression = evaluate((ExpressionToken)token);

				executeResult = action.execute(expression);

				break;
			}

			case VARIABLE_EXPRESSION :
			{
				TemplateVariable expression = evaluate((VariableExpressionToken)token);

				executeResult = action.execute(expression);

				break;
			}

			case ATTRIBUTE_LIST :
			{
				Map<String, TemplateVariable> attributes = evaluate((AttributeListToken)token);

				executeResult = action.execute(attributes);

				break;
			}

			case EXPRESSION_LIST :
			{
				TemplateVariable[] expressionList = evaluate((ExpressionListToken)token);

				executeResult = action.execute(expressionList);

				break;
			}

			case ID :
			{
				String id = evaluate((IdToken)token);

				executeResult = action.execute(id);

				break;
			}

			case ID_LIST :
			{
				String[] idList = evaluate((IdListToken)token);

				executeResult = action.execute(idList);

				break;
			}

			case EMPTY :
			{
				executeResult = action.execute();

				break;
			}

			default :
			{
				throw new RuntimeException("Unknown TemplateActionTag Parm Type: " + token.getTypeName());
			}
		}

		return executeResult;
	}

	/**
	 * evaluate w/ExpressionToken
	 * @throws TemplateException
	 */
	private TemplateVariable evaluate(final ExpressionToken token) throws TemplateException
	{

		TemplateVariable result;

		TemplateVariable leftOp  = null;
		TemplateVariable rightOp = null;

		ExpressionOperatorToken operator = token.getOperator();

		if (token.getLeftOperand() == null)
		{
			if (operator == null)
			{
				throw new RuntimeException("Illegal Expression Token: leftOp == null && operator == null");
			}
			else
			if  (token.getRightOperand() == null)
			{
				throw new RuntimeException("Illegal Expression Token: leftOp == null && rightOp == null");
			}

			rightOp = evaluate(token.getRightOperand());
		}
		else
		{
			if (operator != null)
			{
				if (token.getRightOperand() == null)
				{
					throw new RuntimeException("Illegal Expression Token: leftOp != null && operator != null && rightOp == null");
				}

				rightOp = evaluate(token.getRightOperand());
			}
			else
			{
				if (token.getRightOperand() != null)
				{
					throw new RuntimeException("Illegal Expression Token: leftOp != null && rightOp != null && operator == null");
				}
			}

			leftOp  = evaluate(token.getLeftOperand());
		}

		if (operator != null)
		{
			switch (operator.getOperator())
			{
				case DIVIDE :
				{
					result = evaluateDivide(leftOp, rightOp);
					break;
				}
				case EQUALS_EQUALS :
				{
					result = evaluateEqualsEquals(leftOp, rightOp);
					break;
				}
				case GREATER_THAN :
				{
					result = evaluateGreaterThan(leftOp, rightOp);
					break;
				}
				case GREATER_THAN_EQUALS :
				{
					result = evaluateGreaterThanEquals(leftOp, rightOp);
					break;
				}
				case LESS_THAN :
				{
					result = evaluateLessThan(leftOp, rightOp);
					break;
				}
				case LESS_THAN_EQUALS :
				{
					result = evaluateLessThanEquals(leftOp, rightOp);
					break;
				}
				case LOGICAL_AND :
				{
					result = evaluateLogicalAnd(leftOp, rightOp);
					break;
				}
				case LOGICAL_NOT :
				{
					assert leftOp == null;
					result = evaluateLogicalNot(rightOp);
					break;
				}
				case LOGICAL_OR :
				{
					result = evaluateLogicalOr(leftOp, rightOp);
					break;
				}
				case MINUS :
				{
					if (leftOp == null)
					{
						result = evaluateUnaryMinus(rightOp);
					}
					else
					{
						result = evaluateMinus(leftOp, rightOp);
					}
					break;
				}
				case MODULO :
				{
					result = evaluateModulo(leftOp, rightOp);
					break;
				}
				case MULTIPLY :
				{
					result = evaluateMultiply(leftOp, rightOp);
					break;
				}
				case NOT_EQUALS :
				{
					result = evaluateNotEquals(leftOp, rightOp);
					break;
				}
				case PLUS :
				{
					if (leftOp == null)
					{
						result = evaluateUnaryPlus(rightOp);
					}
					else
					{
						result = evaluatePlus(leftOp, rightOp);
					}
					break;
				}
				default :
				{
					throw new TemplateException("Unknown Expression Operator Type: " + operator);
				}
			}
		}
		else
		{
			result = leftOp;
		}

		FilterListToken filterList = token.getFilterList();

		if (filterList != null)
		{
			result = evaluate(filterList, TemplateVariable.newInstance(result));
		}

		if (result == null)
		{
			result = TemplateVariable.NULL;
		}

		return result;
	}

	/**
	 * evaluate w/ExpressionListToken
	 * @throws TemplateException
	 */
	private TemplateVariable[] evaluate(final ExpressionListToken token) throws TemplateException
	{
		return evaluate(token, 0);
	}

	/**
	 * evaluate w/ExpressionListToken + pad
	 * @throws TemplateException
	 */
	private TemplateVariable[] evaluate(final ExpressionListToken token, final int pad) throws TemplateException
	{
		TemplateVariable[] vars = new TemplateVariable[token.size() + pad];

		for (int i = 0, n = token.size(); i < n; ++i)
		{
			vars[pad + i] = evaluate(token.get(i));
		}

		return vars;
	}


	/**
	 * evaluate w/ExpressionOperandToken
	 * @throws TemplateException
	 */
	private TemplateVariable evaluate(final ExpressionOperandToken token) throws TemplateException
	{
		Token t = token.getToken();

		//	VariableExpression
		//	Number
		//  QString
		//	Expression

		switch (t.getType())
		{
			case VARIABLE_EXPRESSION :
			{
				return evaluate((VariableExpressionToken)t);
			}

			case NUMBER :
			{
				return TemplateVariable.newInstance(evaluate((NumberToken)t));
			}

			case QUOTED_STRING :
			{
				return TemplateVariable.newInstance(evaluate((QuotedStringToken)t));
			}

			case EXPRESSION :
			{
				return TemplateVariable.newInstance(evaluate((ExpressionToken)t));
			}

			case FUNCTION :
			{
				return evaluate((FunctionToken)t);
			}

			case EXPRESSION_LIST :
			{
				return TemplateVariable.newInstance(evaluate((ExpressionListToken)t));
			}

			case ATTRIBUTE_LIST :
			{
				return TemplateVariable.newInstance(evaluate((AttributeListToken)t));
			}

			case BOOLEAN :
			{
				return TemplateVariable.valueOf( ((BooleanToken)t).getValue());
			}

			default :
			{
				throw new TemplateException("Unknown Expression Operand Type: " + t.getTypeName());
			}
		}

//		return TemplateVariable.NULL;
	}

	/**
	 * evaluate w/FunctionToken
	 */
	private TemplateVariable evaluate(final FunctionToken token)
	{
		try
		{
			TemplateEngineConfig config = context.getConfig();

			TemplateTagName              functionName  = token.getName();
			ExpressionListToken functionParms = token.getParms();

			TemplateFunctionConfig functionConfig = config.getFunctionConfig(functionName);

			TemplateFunctionTag function = (TemplateFunctionTag)ClassLoaderUtil.createNewInstance(functionConfig.getClazz());

			if (function == null)
			{
				throw new RuntimeException("Unable to instantiate FunctionTag class '" + functionConfig.getClazz() + "'");
			}

			TemplateVariable[] parms = evaluate(functionParms);

			return function.execute(parms);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * evaluate w/FilterToken + Parm
	 */
	private TemplateVariable evaluate(final FilterToken token, final TemplateVariable parm)
	{
		try
		{
			TemplateEngineConfig config = context.getConfig();

			TemplateTagName              functionName  = token.getName();
			ExpressionListToken functionParms = token.getParms();

			TemplateFilterConfig filterConfig = config.getFilterConfig(functionName);

			TemplateFilterTag filter = (TemplateFilterTag)ClassLoaderUtil.createNewInstance(filterConfig.getClazz());

			if (filter == null)
			{
				throw new RuntimeException("Unable to instantiate FilterTag class '" + filterConfig.getClazz() + "'");
			}

			TemplateVariable[] parms = evaluate(functionParms, 1);

			parms[0] = parm;

			return filter.execute(parms);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * evaluate w/NumberToken
	 */
	private Number evaluate(final NumberToken token)
	{
		Number result;

		if (token.getN2() == null)
		{
			result = new Integer(token.getN1());
		}
		else
		{
			result = new Double(token.getN1() + "." + token.getN2());
		}

		return result;
	}

	/**
	 * process w/EndlTag
	 */
	private void process(final EndlTag tag)
	{
		// We'll use println() over tag.getText()
		context.getOut().println();
	}

	/**
	 * process w/EofTag
	 */
	private void process(final EofTag tag)
	{
		; // Nothing to do
	}

//	/**
//	 * process w/QuotedStringTag
//	 * @throws TemplateException
//	 * @deprecated
//	 */
//	private void process(final QuotedStringTag tag) throws TemplateException
//	{
//		QuotedStringToken token = tag.getQuotedString();
//
//		String s = evaluate(token);
//
//		context.getOut().print(s);
//	}

	/**
	 * process w/SpaceTag
	 */
	private void process(final SpaceTag tag)
	{
		context.getOut().print(tag.getText());
	}

	/**
	 * process w/LeadingSpaceTag
	 */
	private void process(final LeadingSpaceTag tag)
	{
		context.getOut().printLeadingSpace(tag.getText());
	}

	/**
	 * process w/TextTag
	 */
	private void process(final TextTag tag)
	{
		context.getOut().print(tag.getText());
	}

//	/**
//	 * process w/VariableExpressionTag
//	 * @throws TemplateException
//	 * @deprecated
//	 */
//	private void process(final VariableExpressionTag tag) throws TemplateException
//	{
//		VariableExpressionToken vet = tag.getVarExpression();
//
//		TemplateVariable v = evaluate(vet);
//
//		context.getOut().print(v.toString());
//	}

	/**
	 * process w/ExpressionTag
	 * @throws TemplateException
	 */
	private void process(final ExpressionTag tag) throws TemplateException
	{
		ExpressionToken et = tag.getExpression();

		TemplateVariable v = evaluate(et);

		final TemplatePrintBuffer out = context.getOut();

		if (v.isTemplateBodyContent() == true)
		{
			out.print((TemplateBodyContent)v.asObject());
		}
		else
		{
			out.print(v.toString());
		}
	}

	/**
	 * evaluate w/EscapeCharacterToken
	 */
	private String evaluate(final EscapeCharacterToken token)
	{
		String result;

		String text = token.getText();

		EscapeCharacterToken.EscapeCode ec = EscapeCharacterToken.EscapeCode.findByCode(text);

		if (ec != null)
		{
			result = ec.getSequence();
		}
		else
		{
			result = text;
		}

		return result;
	}

	/**
	 * evaluate w/QuotedStringToken
	 * @throws TemplateException
	 */
	private String evaluate(final QuotedStringToken token) throws TemplateException
	{
		StringBuffer buffer = new StringBuffer();

		for (int i = 0, n = token.size(); i < n; ++i)
		{
			QuotedStringElementToken element = token.get(i);
			Token t = element.getToken();

			switch (t.getType())
			{
				case TEXT :
				{
					TextToken tt = (TextToken)t;

					buffer.append(tt.getText());

					break;
				}

				case VARIABLE_EXPRESSION :
				{
					VariableExpressionToken vet = (VariableExpressionToken)t;

					buffer.append(evaluate(vet));

					break;
				}

				case ESCAPE_CHARACTER :
				{
					EscapeCharacterToken ect = (EscapeCharacterToken)t;

					buffer.append(evaluate(ect));

					break;
				}

				case EXPRESSION :
				{
					ExpressionToken et = (ExpressionToken)t;

					buffer.append(evaluate(et));

					break;
				}

				default :
				{
					throw new RuntimeException("Unknown QuotedStringElement VariableType : " + element.getToken().getTypeName());
				}
			}
		}

		String result = buffer.toString();

		FilterListToken filterList = token.getFilterList();

		if (filterList != null)
		{
			result = evaluate(filterList, TemplateVariable.newInstance(result)).toString();
		}

		return result;
	}

	/**
	 * evaluate w/FilterListToken + TemplateVariable
	 */
	private TemplateVariable evaluate(final FilterListToken filterList, final TemplateVariable parm)
	{
		TemplateVariable result =  parm;

		for (int i = 0, n = filterList.size(); i < n; ++i)
		{
			FilterToken filter = filterList.get(i);
			result = evaluate(filter, result);
		}

		return result;
	}

	/**
	 * evaluate w/VariableExpressionToken
	 * @throws TemplateException
	 */
	private TemplateVariable evaluate(final VariableExpressionToken token) throws TemplateException
	{
		TemplateVariable bean = null;

		for (int i = 0, n = token.size(); i < n; ++i)
		{
			VariableExpressionElementToken element = token.get(i);

			Token t = element.getToken();

			switch (t.getType())
			{
				case ID :
				{
					assert bean != null;

					String property = ((IdToken)t).getText();

					bean = evaluateBeanPropertyOrMapKey(bean, property);

					break;
				}

				case VARIABLE_ID :
				{
					VariableIdToken vit = (VariableIdToken)t;

					if (bean == null)
					{
						final String id = evaluate(vit.getId());

						// Variable or Macro?
						if (context.variableExists(id))
						{
							bean = context.getVariable(id);
						}
						else
						if (context.macroExists(id))
						{
							TemplateMacro macro = context.getMacro(id);

							context.startCapture();
							context.processMacro(macro);
							bean = TemplateVariable.newInstance(context.stopCapture());
						}
					}
					else
					{
						final String id = evaluate(vit.getId());

						String property = context.getVariable(id).toString();

						bean = evaluateBeanPropertyOrMapKey(bean, property);
					}

					break;
				}

				case INDEX :
				{
					assert bean != null;

					IndexToken it = (IndexToken)t;

					String index = evaluate(it);

					bean = evaluateBeanArrayIndexOrMapKey(bean, index);

					break;
				}

				default :
				{
					System.err.println("Unknown VariableExpressionElement VariableType : " + element.getToken().getTypeName());
				}
			}
		}

		FilterListToken filterList = token.getFilterList();

		if (filterList != null)
		{
			bean = evaluate(filterList, bean);
		}

		if (bean == null)
		{
			bean = TemplateVariable.NULL;
		}

		return bean;
	}

	/**
	 * evaluate w/IdToken
	 */
	private String evaluate(final IdToken token)
	{
		return token.getText();
	}

	/**
	 * evaluate w/IdListToken
	 * @throws TemplateException
	 */
	private String[] evaluate(final IdListToken token)
	{
		String[] vars = new String[token.size()];

		for (int i = 0, n = token.size(); i < n; ++i)
		{
			vars[i] = evaluate(token.get(i));
		}

		return vars;
	}

	//	/**
//	 * evaluate w/VariableIdToken
//	 * @deprecated
//	 */
//	private TemplateVariable evaluate(final VariableIdToken token)
//	{
//		return context.getVariable(token.getId().getText());
//	}

	/**
	 * evaluate w/IndexToken
	 * @throws TemplateException
	 */
	private String evaluate(final IndexToken token) throws TemplateException
	{
		String result;

		Token t = token.getToken();

		assert t.getType() == TokenType.EXPRESSION;

		result = evaluate((ExpressionToken)t).toString();

//		//	TextToken
//		//	VariableExpressionToken
//		//	QuotedStringToken
//
//		switch (t.getType())
//		{
//			case QUOTED_STRING :
//			{
//				result = evaluate((QuotedStringToken)t);
//			}
//
//			case TEXT :
//			{
//				result = evaluate((TextToken)t);
//			}
//
//			case VARIABLE_EXPRESSION :
//			{
//				result = evaluate((VariableExpressionToken)t).toString();
//			}
//
//			default :
//			{
//				result = null;
//				System.err.println("Unknown IndexToken VariableType : " + t.getTypeName());
//			}
//		}

		return result;
	}

//	/**
//	 * evaluate w/TextToken
//	 * @deprecated
//	 */
//	private String evaluate(final TextToken token)
//	{
//		return token.getText();
//	}

	/**
	 * evaluateBeanPropertyOrMapKey
	 * @throws TemplateException
	 */
	private TemplateVariable evaluateBeanPropertyOrMapKey(final TemplateVariable bean, final String property) throws TemplateException
	{
		TemplateVariable result;

		if (bean.isMappable())
		{
			result = bean.getMappedProperty(property);
		}
		else
		{
			result = bean.getNamedProperty(property);
		}

		return result;
	}

	/**
	 * evaluateBeanArrayIndexOrMapKey
	 * @throws TemplateException
	 */
	private TemplateVariable evaluateBeanArrayIndexOrMapKey(final TemplateVariable bean, final String property) throws TemplateException
	{
		TemplateVariable result;

		if (bean.isMappable())
		{
			result = bean.getMappedProperty(property);
		}
		else
		if (bean.isIndexable())
		{
			result = bean.getIndexProperty(Integer.parseInt(property));
		}
		else
		{
			result = bean.getNamedProperty(property);
		}

		return result;
	}

	/**
	 * evaluate w/MathFunctor + 2 Variables
	 */
	private TemplateVariable evaluate(final MathFunctor functor, final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
// Not giving me the results I want
// For instance, fails when one of the ops is a String representation of a number
//		if (leftOp.isNumber() == false)
//		{
//			if (rightOp.isNumber() == false)
//			{
//				return TemplateVariable.ZERO;
//			}
//			else
//			{
//				return rightOp;
//			}
//		}
//		else
//		if (rightOp.isNumber() == false)
//		{
//			return leftOp;
//		}
//		else
		{
			double d1 = leftOp.asDouble();
			double d2 = rightOp.asDouble();

			double d = functor.execute(d1, d2);

			int i = (int)d;

			double id = i;

			if (d == id)
			{
				return TemplateVariable.newInstance(new Integer(i));
			}

			return TemplateVariable.newInstance(new Double(d));
		}
	}

	/**
	 * evaluateDivide
	 */
	private TemplateVariable evaluateDivide(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		MathFunctor functor = new MathFunctor()
		{
			public double execute(double d1, double d2)
			{
				if (d2 == 0)
				{
					return 0;
				}

				return d1 / d2;
			}
		};

		return evaluate(functor, leftOp, rightOp);
	}

	/**
	 * evaluateMultiply
	 */
	private TemplateVariable evaluateMultiply(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		MathFunctor functor = new MathFunctor()
		{
			public double execute(double d1, double d2)
			{
				return d1 * d2;
			}
		};

		return evaluate(functor, leftOp, rightOp);
	}

	/**
	 * evaluateModulo
	 */
	private TemplateVariable evaluateModulo(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		MathFunctor functor = new MathFunctor()
		{
			public double execute(double d1, double d2)
			{
				return d1 % d2;
			}
		};

		return evaluate(functor, leftOp, rightOp);
	}

	/**
	 * evaluatePlus
	 */
	private TemplateVariable evaluatePlus(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		MathFunctor functor = new MathFunctor()
		{
			public double execute(double d1, double d2)
			{
				return d1 + d2;
			}
		};

		return evaluate(functor, leftOp, rightOp);
	}

	/**
	 * evaluateUnaryPlus
	 */
	private TemplateVariable evaluateUnaryPlus(final TemplateVariable op)
	{
		double d = op.asDouble();

		int i = (int)d;

		double id = i;

		if (d == id)
		{
			return TemplateVariable.newInstance(new Integer(i));
		}

		return TemplateVariable.newInstance(new Double(d));
	}

	/**
	 * evaluateMinus
	 */
	private TemplateVariable evaluateMinus(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		MathFunctor functor = new MathFunctor()
		{
			public double execute(double d1, double d2)
			{
				return d1 - d2;
			}
		};

		return evaluate(functor, leftOp, rightOp);
	}

	/**
	 * evaluateUnaryMinus
	 */
	private TemplateVariable evaluateUnaryMinus(final TemplateVariable op)
	{
		double d = -op.asDouble();

		int i = (int)d;

		double id = i;

		if (d == id)
		{
			return TemplateVariable.newInstance(new Integer(i));
		}

		return TemplateVariable.newInstance(new Double(d));
	}

	/**
	 * evaluate w/RaltionFunctor + 2 Variables
	 */
	private TemplateVariable evaluate(final RelationFunctor functor, final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		TemplateVariable result;

		if (leftOp.isNull() == true)
		{
			if (rightOp.isNull() == true)
			{
				result = TemplateVariable.TRUE;
			}
			else
			{
				result = TemplateVariable.FALSE;
			}
		}
		else
		if (rightOp.isNull() == true)
		{
			result = TemplateVariable.FALSE;
		}
		else
		{
			double d1 = leftOp.asDouble();
			double d2 = rightOp.asDouble();

			result = TemplateVariable.valueOf(functor.execute(Double.compare(d1, d2)));
		}

		return result;
	}

	/**
	 * evaluateLessThan
	 */
	private TemplateVariable evaluateLessThan(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		RelationFunctor functor = new RelationFunctor()
		{
			public boolean execute(int compare) { return compare < 0; };
		};

		return evaluate(functor, leftOp, rightOp);
	}

	/**
	 * evaluateLessThanEquals
	 */
	private TemplateVariable evaluateLessThanEquals(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		RelationFunctor functor = new RelationFunctor()
		{
			public boolean execute(int compare) { return compare <= 0; };
		};

		return evaluate(functor, leftOp, rightOp);
	}

	/**
	 * evaluateGreaterThan
	 */
	private TemplateVariable evaluateGreaterThan(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		RelationFunctor functor = new RelationFunctor()
		{
			public boolean execute(int compare) { return compare > 0; };
		};

		return evaluate(functor, leftOp, rightOp);
	}

	/**
	 * evaluateGreaterThanEquals
	 */
	private TemplateVariable evaluateGreaterThanEquals(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		RelationFunctor functor = new RelationFunctor()
		{
			public boolean execute(int compare) { return compare >= 0; };
		};

		return evaluate(functor, leftOp, rightOp);
	}

	/**
	 * evaluateEqualsEquals
	 */
	private TemplateVariable evaluateEqualsEquals(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		TemplateVariable result;

		// Are we referring to the same TemplateVariable?
		if (leftOp == rightOp)
		{
			result = TemplateVariable.TRUE;
		}
		else
		// Do the variables wrap the same object?
		// Note: This should include the case where both operands
		// are TemplateVariable.NULL
		if (leftOp.asObject() == rightOp.asObject())
		{
			result = TemplateVariable.TRUE;
		}
// This should be handled by the default case
//		else
//		// Is on of the operands null?
//		if(leftOp.isNull() == true || rightOp.isNull() == true)
//		{
//			result = TemplateVariable.FALSE;
//		}
		else
		// Are we dealing with two Strings?
		if (leftOp.isString() == true && rightOp.isString() == true)
		{
			String s1 = (String)leftOp.asObject();
			String s2 = (String)rightOp.asObject();

			result = TemplateVariable.valueOf(s1.equals(s2));
		}
		else
		// Is one of the operands a boolean?
		if (leftOp.isBoolean() == true || rightOp.isBoolean() == true)
		{
			result = TemplateVariable.valueOf(leftOp.asBoolean() == rightOp.asBoolean());
		}
		else
		if	(
				(
					(leftOp.isNumber()   == true)
				||	(leftOp.isString()   == true)
				||	(leftOp.isSizeable() == true)
				)
			&&	(
					(rightOp.isNumber()   == true)
				||	(rightOp.isString()   == true)
				||	(rightOp.isSizeable() == true)
				)
			)
		{
			RelationFunctor functor = new RelationFunctor()
			{
				public boolean execute(int compare) { return compare == 0; };
			};

			result = evaluate(functor, leftOp, rightOp);
		}
		else
		{
			result = TemplateVariable.FALSE;
		}

		assert result.isBoolean() == true;

		return result;
	}

	/**
	 * evaluateNotEquals
	 */
	private TemplateVariable evaluateNotEquals(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		TemplateVariable v = evaluateEqualsEquals(leftOp, rightOp);

		assert v.isBoolean() == true;

		return TemplateVariable.valueOf(v.asBoolean() == false);
	}

	/**
	 * evaluateLogicalAnd
	 */
	private TemplateVariable evaluateLogicalAnd(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		return TemplateVariable.valueOf(leftOp.asBoolean() && rightOp.asBoolean());
	}

	/**
	 * evaluateLogicalOr
	 */
	private TemplateVariable evaluateLogicalOr(final TemplateVariable leftOp, final TemplateVariable rightOp)
	{
		return leftOp.asBoolean() ? leftOp : rightOp.asBoolean() ? rightOp : TemplateVariable.FALSE;
//!		return TemplateVariable.valueOf(leftOp.asBoolean() || rightOp.asBoolean());
	}

	/**
	 * evaluateLogicalNot
	 */
	private TemplateVariable evaluateLogicalNot(final TemplateVariable op)
	{
		return TemplateVariable.valueOf(!op.asBoolean());
	}

	/**
	 * evaluate w/AttributeListToken
	 * @throws TemplateException
	 */
	private Map<String, TemplateVariable> evaluate(final AttributeListToken token) throws TemplateException
	{
		Map<String, TemplateVariable> result = new HashMap<String, TemplateVariable>();

		for (int i = 0, n = token.size(); i < n; ++i)
		{
			AttributeToken t = token.get(i);

			String           id    = evaluate(t.getId());
			TemplateVariable value = evaluate(t.getValue());

			result.put(id, value);
		}

		return result;
	}
}