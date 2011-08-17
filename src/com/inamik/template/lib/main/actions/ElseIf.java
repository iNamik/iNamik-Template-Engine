/*
 * $Id: ElseIf.java,v 1.5 2006-08-21 08:09:40 dave Exp $
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
package com.inamik.template.lib.main.actions;

import com.inamik.template.AbstractTemplateActionTag;
import com.inamik.template.TemplateVariable;

/**
 * ElseIf - Allows you to chain an {if} statement as a body-alt.
 * <p>
 * Although most typically used in if-elseif-else constructs, this action
 * tag can be used anywherea a body-alt is accepted.
 * <p>
 * Takes a single expression as a parameter and includes the template body
 * if the expression resolves to <code>true</code>.
 * <p>
 * If the expression resolves to <code>false</code> then the body is skipped
 * and the next body-alt is executed, if present.
 * <p>
 * ParmType: Expression
 * <br>
 * BodyType: Body-Alt
 * <br>
 * bodyContent: Template
 * <p>
 * Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {if $foo}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; {$foo}
 * <br>
 * &nbsp;&nbsp; {elseif $bar}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; {$bar}
 * <br>
 * &nbsp;&nbsp; {else}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Neither foo nor bar are defined
 * <br>
 * &nbsp;&nbsp; {/if}
 * </code>
 * <p>
 * Created on Jul 21, 2006
 * @author Dave
 */
public final class ElseIf extends AbstractTemplateActionTag
{

	/**
	 * Constructor
	 */
	public ElseIf()
	{
		super();
	}

	/* (non-Javadoc)
	 * @see com.inamik.template.TemplateActionTag#execute(com.inamik.template.TemplateVariable)
	 */
	@Override
	public ExecuteResult execute(TemplateVariable expression)
	{
		if (expression.asBoolean() == true)
		{
			return ExecuteResult.OK;
		}

		return ExecuteResult.SKIP_BODY;
	}

}
