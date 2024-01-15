package ch.ess.cal.persistence.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * @author sr
 */
public abstract class EnumUserType implements UserType {

  private static final int[] SQL_TYPES = {Types.INTEGER};

  public int[] sqlTypes() {
    return SQL_TYPES;
  }

  public Class returnedClass() {
    return Enum.class;
  }

  public boolean equals(Object x, Object y) {
    return x == y;
  }

  public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws SQLException {
    int intValue = rs.getInt(names[0]);
    if (rs.wasNull()) {
      return null;
    }

    return getEnum(intValue);
  }

  public void nullSafeSet(PreparedStatement st, Object val, int index) throws SQLException {
    if (val == null) {
      st.setNull(index, Types.INTEGER);
    } else {
      st.setInt(index, getValue((Enum)val));
    }
  }

  public Object deepCopy(Object val) {
    return val;
  }

  public boolean isMutable() {
    return false;
  }

  public abstract Enum getEnum(int intValue);

  public abstract int getValue(Enum e);

  public Object assemble(Serializable cached, Object owner) throws HibernateException {
    return cached;
  }

  public Serializable disassemble(Object value) throws HibernateException {
    return (Serializable)value;
  }

  public int hashCode(Object value) throws HibernateException {
    return value.hashCode();
  }

  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return original;
  }

}
