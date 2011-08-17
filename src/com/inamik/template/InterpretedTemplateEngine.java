/*
 * $Id: InterpretedTemplateEngine.java,v 1.6 2006-08-16 22:41:51 dave Exp $
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

import java.io.Serializable;


//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * InterpretedTemplateEngine
 * Created on Jul 28, 2006
 * @author Dave
 */
final class InterpretedTemplateEngine extends TemplateEngine
{
//    private static final Log LOG = LogFactory.getLog(InterpretedTemplateEngine.class.getName());

	/**
	 * Constructor
	 */
	InterpretedTemplateEngine(TemplateEngineConfig config)
	{
		super(config);
	}

	@Override
	protected Serializable getCacheableObject(TokenizedTemplate cu)
	{
		assert cu != null;

		return cu;
	}

	@Override
	protected TemplateProcessor getProcessorFromCachedObject(Serializable object)
	{
		assert object != null;
		assert object instanceof TokenizedTemplate;

		return new InterpretedTemplateProcessor((TokenizedTemplate)object);
	}
}
