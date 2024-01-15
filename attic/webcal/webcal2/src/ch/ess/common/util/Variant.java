package ch.ess.common.util;

import java.util.Date;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:47 $ 
  */
public final class Variant {

  private Object value = null;
  private Class type = null;

  public Variant() {
    //no action
  }

  public Variant(Variant value) {
    this.value = value.value;
    type = value.type;
  }

  public Variant(String value) {
    this.value = value;
    type = String.class;
  }

  public Variant(boolean value) {
    this.value = new Boolean(value);
    type = Boolean.TYPE;
  }

  public Variant(Boolean value) {

    if (value != null) {
      this.value = (value.booleanValue()) ? Boolean.TRUE : Boolean.FALSE;
      this.type = Boolean.class;
    }
  }

  public Variant(Date value) {

    if (value != null) {
      this.value = new Date(value.getTime());
      this.type = Date.class;
    }
  }

  public Variant(byte value) {
    this.value = new Byte(value);
    type = Byte.TYPE;
  }

  public Variant(Byte value) {

    if (value != null) {
      this.value = new Byte(value.byteValue());
      this.type = Byte.class;
    }
  }

  public Variant(char value) {
    this.value = new Character(value);
    type = Character.TYPE;
  }

  public Variant(Character value) {

    if (value != null) {
      this.value = new Character(value.charValue());
      this.type = Character.class;
    }
  }

  public Variant(short value) {
    this.value = new Short(value);
    type = Short.TYPE;
  }

  public Variant(Short value) {

    if (value != null) {
      this.value = new Short(value.shortValue());
      this.type = Short.class;
    }
  }

  public Variant(int value) {
    this.value = new Integer(value);
    type = Integer.TYPE;
  }

  public Variant(Integer value) {

    if (value != null) {
      this.value = new Integer(value.intValue());
      this.type = Integer.class;
    }
  }

  public Variant(long value) {
    this.value = new Long(value);
    type = Long.TYPE;
  }

  public Variant(Long value) {

    if (value != null) {
      this.value = new Long(value.longValue());
      this.type = Long.class;
    }
  }

  public Variant(float value) {
    this.value = new Float(value);
    type = Float.TYPE;
  }

  public Variant(Float value) {

    if (value != null) {
      this.value = new Float(value.floatValue());
      this.type = Float.class;
    }
  }

  public Variant(double value) {
    this.value = new Double(value);
    type = Double.TYPE;
  }

  public Variant(Double value) {

    if (value != null) {
      this.value = new Double(value.doubleValue());
      this.type = Double.class;
    }
  }

  public Variant(Object value) {
    this.value = value;
    type = Object.class;
  }

  /**
   *  getType returns the type of the variable being stored in the Variant.
   *
   *  @return The variable type
   */
  public Class getType() {
    return type;
  }

  /**
   *  isNull returns whether or not the variable is storing a null value.
   */
  public boolean isNull() {
    return value == null;
  }

  /**
   *  set sets the value as a String.
   *
   *  @param value The value
   */
  public void set(String value) {
    this.value = value;
    type = String.class;
  }

  public void set(Date value) {

    if (value != null) {
      this.value = new Date(value.getTime());
      this.type = Date.class;
    } else {
      this.value = null;
      this.type = null;
    }
  }

  /**
   *  set sets the value as a boolean.
   *
   *  @param value The value
   */
  public void set(boolean value) {
    this.value = new Boolean(value);
    type = Boolean.TYPE;
  }

  public void set(Boolean value) {

    if (value != null) {
      this.value = (value.booleanValue()) ? Boolean.TRUE : Boolean.FALSE;
      this.type = Boolean.class;
    } else {
      this.value = null;
      this.type = null;
    }
  }

  /**
   *  set sets the value as a byte.
   *
   *  @param value The value
   */
  public void set(byte value) {
    this.value = new Byte(value);
    type = Byte.TYPE;
  }

  public void set(Byte value) {

    if (value != null) {
      this.value = new Byte(value.byteValue());
      this.type = Byte.class;
    } else {
      this.value = null;
      this.type = null;
    }
  }

  /**
   *  set sets the value as a char.
   *
   *  @param value The value
   */
  public void set(char value) {
    this.value = new Character(value);
    type = Character.TYPE;
  }

  public void set(Character value) {

    if (value != null) {
      this.value = new Character(value.charValue());
      this.type = Character.class;
    } else {
      this.value = null;
      this.type = null;
    }
  }

  /**
   *  set sets the value as a short
   *
   *  @param value The value
   */
  public void set(short value) {
    this.value = new Short(value);
    type = Short.TYPE;
  }

  public void set(Short value) {

    if (value != null) {
      this.value = new Short(value.shortValue());
      this.type = Short.class;
    } else {
      this.value = null;
      this.type = null;
    }
  }

  /**
   *  set sets the value as an int.
   *
   *  @param value The value
   */
  public void set(int value) {
    this.value = new Integer(value);
    type = Integer.TYPE;
  }

  public void set(Integer value) {

    if (value != null) {
      this.value = new Integer(value.intValue());
      this.type = Integer.class;
    } else {
      this.value = null;
      this.type = null;
    }
  }

  /**
   *  set sets the value as a long.
   *
   *  @param value The value
   */
  public void set(long value) {
    this.value = new Long(value);
    type = Long.TYPE;
  }

  public void set(Long value) {

    if (value != null) {
      this.value = new Long(value.longValue());
      this.type = Long.class;
    } else {
      this.value = null;
      this.type = null;
    }
  }

  /**
   *  set sets the value as a float.
   *
   *  @param value The value
   */
  public void set(float value) {
    this.value = new Float(value);
    type = Float.TYPE;
  }

  public void set(Float value) {

    if (value != null) {
      this.value = new Float(value.floatValue());
      this.type = Float.class;
    } else {
      this.value = null;
      this.type = null;
    }
  }

  /**
   *  set sets the value as a double.
   *
   *  @param value The value
   */
  public void set(double value) {
    this.value = new Double(value);
    type = Double.TYPE;
  }

  public void set(Double value) {

    if (value != null) {
      this.value = new Double(value.doubleValue());
      this.type = Double.class;
    } else {
      this.value = null;
      this.type = null;
    }
  }

  /**
   *  set sets the value as an Object.
   *
   *  @param value The value
   */
  public void set(Object object) {
    this.value = object;
    type = Object.class;
  }

  /**
   *  getString returns the value as a String.
   *
   *  @return The value
   */
  public String getString() {

    if (type == String.class) {
      return (String)value;
    } else {
      return value.toString();
    }
  }

  /**
   *  getBoolean returns the value as a date.
   *
   *  @return The value
   */
  public Date getDate() {

    if (type == Date.class) {
      return (Date)value;
    } else {
      return null;
    }
  }

  /**
   *  getBoolean returns the value as a boolean.
   *
   *  @return The value
   */
  public boolean getBoolean() {

    if (type == Boolean.TYPE) {
      return ((Boolean)value).booleanValue();
    } else {
      return "[true][yes][1][y][on]".indexOf("[" + value.toString().toLowerCase() + "]") != -1;
    }
  }

  /**
   *  getByte returns the value as a byte.
   *
   *  @return The value
   */
  public byte getByte() {

    if (type == Byte.TYPE) {
      return ((Byte)value).byteValue();
    } else {
      return Byte.parseByte(value.toString());
    }
  }

  /**
   *  getChar returns the value as a char.
   *
   *  @return The value
   */
  public char getChar() {

    if (type == Character.TYPE) {
      return ((Character)value).charValue();
    } else {
      return value.toString().charAt(0);
    }
  }

  /**
   *  getShort returns the value as a short.
   *
   *  @return The value
   */
  public short getShort() {

    if (type == Short.TYPE) {
      return ((Short)value).shortValue();
    } else {
      return Short.parseShort(value.toString());
    }
  }

  /**
   *  getInt returns the value as an int.
   *
   *  @return The value
   */
  public int getInt() {

    if (type == Integer.TYPE) {
      return ((Integer)value).intValue();
    } else {
      return Integer.parseInt(value.toString());
    }
  }

  /**
   *  getLong returns the value as a long.
   *
   *  @return The value
   */
  public long getLong() {

    if (type == Long.TYPE) {
      return ((Long)value).longValue();
    } else {
      return Long.parseLong(value.toString());
    }
  }

  /**
   *  getFloat returns the value as a float.
   *
   *  @return The value
   */
  public float getFloat() {

    if (type == Float.TYPE) {
      return ((Float)value).floatValue();
    } else {
      return Float.parseFloat(value.toString());
    }
  }

  /**
   *  getDouble returns the value as a double.
   *
   *  @return The value
   */
  public double getDouble() {

    if (type == Double.TYPE) {
      return ((Double)value).doubleValue();
    } else {
      return Double.parseDouble(value.toString());
    }
  }

  /**
   *  getObject returns the value as an Object.
   *
   *  @return The value
   */
  public Object getObject() {
    return value;
  }

  /**
   *  toString returns the value as a Stringified representation.
   *
   *  @return The stringified value
   */
  public String toString() {
    return value.toString();
  }
}
