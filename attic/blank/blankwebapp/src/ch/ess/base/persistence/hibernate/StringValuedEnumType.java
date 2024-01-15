package ch.ess.base.persistence.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

import ch.ess.base.enums.StringValuedEnum;
import ch.ess.base.enums.StringValuedEnumReflect;

public class StringValuedEnumType<T extends Enum<T> & StringValuedEnum> implements EnhancedUserType, ParameterizedType {

  private Class<T> enumClass;

  /** Creates a new instance of ActiveStateEnumType */
  public StringValuedEnumType() {
    //no default action
  }

  @SuppressWarnings("unchecked")
  public void setParameterValues(Properties parameters) {
    String enumClassName = parameters.getProperty("enum");
    try {
      enumClass = (Class<T>)Class.forName(enumClassName).asSubclass(Enum.class).asSubclass(StringValuedEnum.class); 
      //Validates the class but does not eliminate the cast
    } catch (ClassNotFoundException cnfe) {
      throw new HibernateException("Enum class not found", cnfe);
    }
  }

  public Object assemble(Serializable cached, Object owner) throws HibernateException {
    return cached;
  }

  public Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  public Serializable disassemble(Object value) throws HibernateException {
    return (Enum)value;
  }

  public boolean equals(Object x, Object y) throws HibernateException {
    return x == y;
  }

  public int hashCode(Object x) throws HibernateException {
    return x.hashCode();
  }

  public boolean isMutable() {
    return false;
  }

  public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
    String value = rs.getString(names[0]);
    
    if (!rs.wasNull()) {
      String name = StringValuedEnumReflect.getNameFromValue(enumClass, value);
      return Enum.valueOf(enumClass, name);
    }
    
    return null;
  }

  @SuppressWarnings("unchecked")
  public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
    if (value == null) {
      st.setNull(index, Types.VARCHAR);
    } else {
      st.setString(index, ((T)value).getValue());
    }
  }

  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return original;
  }

  public Class returnedClass() {
    return enumClass;
  }

  public int[] sqlTypes() {
    return new int[]{Types.VARCHAR};
  }

  public Object fromXMLString(String xmlValue) {
    String name = StringValuedEnumReflect.getNameFromValue(enumClass, xmlValue);
    return Enum.valueOf(enumClass, name);
  }

  @SuppressWarnings("unchecked")
  public String objectToSQLString(Object value) {
    return '\'' + ((T)value).getValue() + '\'';
  }

  @SuppressWarnings("unchecked")
  public String toXMLString(Object value) {
    return ((T)value).getValue();
  }

}
