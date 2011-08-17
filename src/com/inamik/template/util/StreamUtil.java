/*
 * $Id: StreamUtil.java,v 1.1 2006-08-21 03:07:12 dave Exp $
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
package com.inamik.template.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * StreamUtil - Utility class for dealing with Streams.
 * <p>
 * Currently, this utility class only has methods for helping you
 * close streams without have to catch the IOException.
 * <p>
 * Created on Aug 16, 2006
 * @author Dave
 */
public final class StreamUtil
{
	private StreamUtil() {}

	/**
	 * close - Close an InputStream, catching the IOException and
	 * returning a value indicating if the exception was thrown.
	 *
	 * @param stream The InputStream to close
	 * @return A value indicating if the IOException was thrown
	 */
	public static boolean close(final InputStream stream)
	{
		boolean result;

		try
		{
			stream.close();
			result = false;
		}
		catch (IOException e)
		{
			result = true;
		}

		return result;
	}

	/**
	 * close - Close an OutputStream, catching the IOException and
	 * returning a value indicating if the exception was thrown.
	 *
	 * @param stream The InputStream to close
	 * @return A value indicating if the IOException was thrown
	 */
	public static boolean close(final OutputStream stream)
	{
		boolean result;

		try
		{
			stream.close();
			result = false;
		}
		catch (IOException e)
		{
			result = true;
		}

		return result;
	}

}
