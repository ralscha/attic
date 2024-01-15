/**
 * DirectoryCategory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package ch.ess.google.wsdl;

public class DirectoryCategory  implements java.io.Serializable {
    private java.lang.String fullViewableName;
    private java.lang.String specialEncoding;

    public DirectoryCategory() {
//    no action
    }

    public java.lang.String getFullViewableName() {
        return fullViewableName;
    }

    public void setFullViewableName(java.lang.String fullViewableName) {
        this.fullViewableName = fullViewableName;
    }

    public java.lang.String getSpecialEncoding() {
        return specialEncoding;
    }

    public void setSpecialEncoding(java.lang.String specialEncoding) {
        this.specialEncoding = specialEncoding;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DirectoryCategory)) return false;
        DirectoryCategory other = (DirectoryCategory) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((fullViewableName==null && other.getFullViewableName()==null) || 
             (fullViewableName!=null &&
              fullViewableName.equals(other.getFullViewableName()))) &&
            ((specialEncoding==null && other.getSpecialEncoding()==null) || 
             (specialEncoding!=null &&
              specialEncoding.equals(other.getSpecialEncoding())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getFullViewableName() != null) {
            _hashCode += getFullViewableName().hashCode();
        }
        if (getSpecialEncoding() != null) {
            _hashCode += getSpecialEncoding().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DirectoryCategory.class);

    static {
        org.apache.axis.description.FieldDesc field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("fullViewableName");
        field.setXmlName(new javax.xml.namespace.QName("", "fullViewableName"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("specialEncoding");
        field.setXmlName(new javax.xml.namespace.QName("", "specialEncoding"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
