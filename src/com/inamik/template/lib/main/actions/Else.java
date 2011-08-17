/*
 * $Id: Else.java,v 1.4 2006-08-21 03:07:12 dave Exp $
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

/**
 * Else - A final (default) body-alt for body tags that suppot body-alts.
 * <p>
 * Although most typically used in if-else constructs, this body-alt tag
 * can be used as a final body-alt tag for any body action tag that supports
 * body-alts.
 * <p>
 * ParmType: Empty
 * <br>
 * BodyType: Body
 * <br>
 * BodyContent: Template
 * <p>
 * Example:
 * <p>
 * <code>
 * &nbsp;&nbsp; {if $foo}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; {$foo}
 * <br>
 * &nbsp;&nbsp; {else}
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Foo not defined
 * <br>
 * &nbsp;&nbsp; {/if}
 * </code>
 * <p>
 * Created on Jul 21, 2006
 * @author Dave
 */
public final class Else extends AbstractTemplateActionTag
{

	/**
	 * Constructor
	 */
	public Else()
	{
		super();
	}

	@Override
	public ExecuteResult execute()
	{
		return ExecuteResult.OK;
	}

}
