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

// JDK
import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * This class uses the simple idea for representing attributes
 * from Chapter 3 of The Java Programming Language
 * by Ken Arnold and James Gosling.
 *
 * @author George Stewart
 *
 */

public class Attr {

  protected String name;
  protected Object value = null;

  public Attr(String name) {
    this.name = name;
  }

  public Attr(String name, Object value) {
    this(name);
    this.value = value;
  }


  public Attr(String name, boolean value) {
    this(name);
    this.value = new Boolean(value);
  }

  public Attr(String name, short value) {
    this(name);
    this.value = new Short(value);
  }

  public Attr(String name, int value) {
    this(name);
    this.value = new Integer(value);
  }

  public Attr(String name, long value) {
    this(name);
    this.value = new Long(value);
  }

  public Attr(String name, float value) {
    this(name);
    this.value = new Double((double) value);
  }

  public Attr(String name, double value) {
    this(name);
    this.value = new Double(value);
  }

  public String getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }

  public boolean isNull() {
    return value == null;
  }


  public boolean getBooleanValue() {
    return ((Boolean) value).booleanValue();
  }

  public short getShortValue() {
    return ((Short) value).shortValue();
  }

  public int getIntValue() {
    return ((Integer) value).intValue();
  }

  public long getLongValue() {
    return ((Long) value).longValue();
  }

  public float getFloatValue() {
    return ((Float) value).floatValue();
  }

  public double getDoubleValue() {
    return ((Double) value).doubleValue();
  }

  public String getStringValue() {
    return (String) value;
  }

  public Timestamp getTimestampValue() {
    return (Timestamp) value;
  }

  public BigDecimal getBigDecimalValue() {
    return (BigDecimal) value;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer(name).append("='");
    if (value != null)
      sb.append(value.toString());
    else
      sb.append(value);
    sb.append("'");
    return sb.toString();
  }
}
