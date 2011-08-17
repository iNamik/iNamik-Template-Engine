/*
 * $Id: LeadingSpaceFilter.java,v 1.1 2006-11-21 09:34:43 dave Exp $
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

package com.inamik.template.tagfilters;

/**
 * LeadingSpaceFilter
 * @author Dave
 */
public final class LeadingSpaceFilter extends AbstractLineFilter
{

	/**
	 * Default Constructor
	 */
	public LeadingSpaceFilter()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.inamik.template.tagfilters.AbstractLineFilter#processLine()
	 */
	@Override
	protected void processLine()
	{
		if	(
				(isSpace_AltBlock_EndL())
			||	(isSpace_EndBlock_EndL())
			||	(isSpace_EmptyAction_EndL())
			)
		{
			remove(0);
		}
		putAll();
		clear();
	}
}
