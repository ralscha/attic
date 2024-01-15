/**
 * ResultElement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package ch.ess.google.wsdl;

public class ResultElement  implements java.io.Serializable {
    private java.lang.String summary;
    private java.lang.String URL;
    private java.lang.String snippet;
    private java.lang.String title;
    private java.lang.String cachedSize;
    private boolean relatedInformationPresent;
    private java.lang.String hostName;
    private ch.ess.google.wsdl.DirectoryCategory directoryCategory;
    private java.lang.String directoryTitle;

    public ResultElement() {
//    no action
    }

    public java.lang.String getSummary() {
        return summary;
    }

    public void setSummary(java.lang.String summary) {
        this.summary = summary;
    }

    public java.lang.String getURL() {
        return URL;
    }

    public void setURL(java.lang.String URL) {
        this.URL = URL;
    }

    public java.lang.String getSnippet() {
        return snippet;
    }

    public void setSnippet(java.lang.String snippet) {
        this.snippet = snippet;
    }

    public java.lang.String getTitle() {
        return title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public java.lang.String getCachedSize() {
        return cachedSize;
    }

    public void setCachedSize(java.lang.String cachedSize) {
        this.cachedSize = cachedSize;
    }

    public boolean isRelatedInformationPresent() {
        return relatedInformationPresent;
    }

    public void setRelatedInformationPresent(boolean relatedInformationPresent) {
        this.relatedInformationPresent = relatedInformationPresent;
    }

    public java.lang.String getHostName() {
        return hostName;
    }

    public void setHostName(java.lang.String hostName) {
        this.hostName = hostName;
    }

    public ch.ess.google.wsdl.DirectoryCategory getDirectoryCategory() {
        return directoryCategory;
    }

    public void setDirectoryCategory(ch.ess.google.wsdl.DirectoryCategory directoryCategory) {
        this.directoryCategory = directoryCategory;
    }

    public java.lang.String getDirectoryTitle() {
        return directoryTitle;
    }

    public void setDirectoryTitle(java.lang.String directoryTitle) {
        this.directoryTitle = directoryTitle;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultElement)) return false;
        ResultElement other = (ResultElement) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((summary==null && other.getSummary()==null) || 
             (summary!=null &&
              summary.equals(other.getSummary()))) &&
            ((URL==null && other.getURL()==null) || 
             (URL!=null &&
              URL.equals(other.getURL()))) &&
            ((snippet==null && other.getSnippet()==null) || 
             (snippet!=null &&
              snippet.equals(other.getSnippet()))) &&
            ((title==null && other.getTitle()==null) || 
             (title!=null &&
              title.equals(other.getTitle()))) &&
            ((cachedSize==null && other.getCachedSize()==null) || 
             (cachedSize!=null &&
              cachedSize.equals(other.getCachedSize()))) &&
            relatedInformationPresent == other.isRelatedInformationPresent() &&
            ((hostName==null && other.getHostName()==null) || 
             (hostName!=null &&
              hostName.equals(other.getHostName()))) &&
            ((directoryCategory==null && other.getDirectoryCategory()==null) || 
             (directoryCategory!=null &&
              directoryCategory.equals(other.getDirectoryCategory()))) &&
            ((directoryTitle==null && other.getDirectoryTitle()==null) || 
             (directoryTitle!=null &&
              directoryTitle.equals(other.getDirectoryTitle())));
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
        if (getSummary() != null) {
            _hashCode += getSummary().hashCode();
        }
        if (getURL() != null) {
            _hashCode += getURL().hashCode();
        }
        if (getSnippet() != null) {
            _hashCode += getSnippet().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getCachedSize() != null) {
            _hashCode += getCachedSize().hashCode();
        }
        _hashCode += new Boolean(isRelatedInformationPresent()).hashCode();
        if (getHostName() != null) {
            _hashCode += getHostName().hashCode();
        }
        if (getDirectoryCategory() != null) {
            _hashCode += getDirectoryCategory().hashCode();
        }
        if (getDirectoryTitle() != null) {
            _hashCode += getDirectoryTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultElement.class);

    static {
        org.apache.axis.description.FieldDesc field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("summary");
        field.setXmlName(new javax.xml.namespace.QName("", "summary"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("URL");
        field.setXmlName(new javax.xml.namespace.QName("", "URL"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("snippet");
        field.setXmlName(new javax.xml.namespace.QName("", "snippet"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("title");
        field.setXmlName(new javax.xml.namespace.QName("", "title"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("cachedSize");
        field.setXmlName(new javax.xml.namespace.QName("", "cachedSize"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("relatedInformationPresent");
        field.setXmlName(new javax.xml.namespace.QName("", "relatedInformationPresent"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("hostName");
        field.setXmlName(new javax.xml.namespace.QName("", "hostName"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("directoryCategory");
        field.setXmlName(new javax.xml.namespace.QName("", "directoryCategory"));
        field.setXmlType(new javax.xml.namespace.QName("urn:GoogleSearch", "DirectoryCategory"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("directoryTitle");
        field.setXmlName(new javax.xml.namespace.QName("", "directoryTitle"));
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
