/*
 *
 * Keywords: java, development, JDBC
 *
 * Copyright (C) 2000 George Stewart
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * This is one of a set of classes that make up Osage - Persistence
 * Plus XML, a framework for object-to-relational persistence.
 *
 * The latest version of Osage is available at
 * <URL:http://osage.sourceforge.net>.
 *
 * Please send any comments, bugs, or enhancement requests to
 * George Stewart at georgestewartiv@yahoo.com
 *
 */

package net.sourceforge.osage.attributed;

/**
 *
 * @author  George Stewart
 */

public class AttributedFactory {
  private AttributedFactory() {}

  public static Attributed getAttributed() {
    return new AttributedImpl();
  }
}
