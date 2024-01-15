/**
 * GoogleSearchResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package ch.ess.google.wsdl;

public class GoogleSearchResult  implements java.io.Serializable {
    private boolean documentFiltering;
    private java.lang.String searchComments;
    private int estimatedTotalResultsCount;
    private boolean estimateIsExact;
    private ch.ess.google.wsdl.ResultElement[] resultElements;
    private java.lang.String searchQuery;
    private int startIndex;
    private int endIndex;
    private java.lang.String searchTips;
    private ch.ess.google.wsdl.DirectoryCategory[] directoryCategories;
    private double searchTime;

    public GoogleSearchResult() {
//    no action
    }

    public boolean isDocumentFiltering() {
        return documentFiltering;
    }

    public void setDocumentFiltering(boolean documentFiltering) {
        this.documentFiltering = documentFiltering;
    }

    public java.lang.String getSearchComments() {
        return searchComments;
    }

    public void setSearchComments(java.lang.String searchComments) {
        this.searchComments = searchComments;
    }

    public int getEstimatedTotalResultsCount() {
        return estimatedTotalResultsCount;
    }

    public void setEstimatedTotalResultsCount(int estimatedTotalResultsCount) {
        this.estimatedTotalResultsCount = estimatedTotalResultsCount;
    }

    public boolean isEstimateIsExact() {
        return estimateIsExact;
    }

    public void setEstimateIsExact(boolean estimateIsExact) {
        this.estimateIsExact = estimateIsExact;
    }

    public ch.ess.google.wsdl.ResultElement[] getResultElements() {
        return resultElements;
    }

    public void setResultElements(ch.ess.google.wsdl.ResultElement[] resultElements) {
        this.resultElements = resultElements;
    }

    public java.lang.String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(java.lang.String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public java.lang.String getSearchTips() {
        return searchTips;
    }

    public void setSearchTips(java.lang.String searchTips) {
        this.searchTips = searchTips;
    }

    public ch.ess.google.wsdl.DirectoryCategory[] getDirectoryCategories() {
        return directoryCategories;
    }

    public void setDirectoryCategories(ch.ess.google.wsdl.DirectoryCategory[] directoryCategories) {
        this.directoryCategories = directoryCategories;
    }

    public double getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(double searchTime) {
        this.searchTime = searchTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GoogleSearchResult)) return false;
        GoogleSearchResult other = (GoogleSearchResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            documentFiltering == other.isDocumentFiltering() &&
            ((searchComments==null && other.getSearchComments()==null) || 
             (searchComments!=null &&
              searchComments.equals(other.getSearchComments()))) &&
            estimatedTotalResultsCount == other.getEstimatedTotalResultsCount() &&
            estimateIsExact == other.isEstimateIsExact() &&
            ((resultElements==null && other.getResultElements()==null) || 
             (resultElements!=null &&
              java.util.Arrays.equals(resultElements, other.getResultElements()))) &&
            ((searchQuery==null && other.getSearchQuery()==null) || 
             (searchQuery!=null &&
              searchQuery.equals(other.getSearchQuery()))) &&
            startIndex == other.getStartIndex() &&
            endIndex == other.getEndIndex() &&
            ((searchTips==null && other.getSearchTips()==null) || 
             (searchTips!=null &&
              searchTips.equals(other.getSearchTips()))) &&
            ((directoryCategories==null && other.getDirectoryCategories()==null) || 
             (directoryCategories!=null &&
              java.util.Arrays.equals(directoryCategories, other.getDirectoryCategories()))) &&
            searchTime == other.getSearchTime();
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
        _hashCode += new Boolean(isDocumentFiltering()).hashCode();
        if (getSearchComments() != null) {
            _hashCode += getSearchComments().hashCode();
        }
        _hashCode += getEstimatedTotalResultsCount();
        _hashCode += new Boolean(isEstimateIsExact()).hashCode();
        if (getResultElements() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResultElements());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResultElements(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSearchQuery() != null) {
            _hashCode += getSearchQuery().hashCode();
        }
        _hashCode += getStartIndex();
        _hashCode += getEndIndex();
        if (getSearchTips() != null) {
            _hashCode += getSearchTips().hashCode();
        }
        if (getDirectoryCategories() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDirectoryCategories());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDirectoryCategories(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Double(getSearchTime()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GoogleSearchResult.class);

    static {
        org.apache.axis.description.FieldDesc field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("documentFiltering");
        field.setXmlName(new javax.xml.namespace.QName("", "documentFiltering"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("searchComments");
        field.setXmlName(new javax.xml.namespace.QName("", "searchComments"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("estimatedTotalResultsCount");
        field.setXmlName(new javax.xml.namespace.QName("", "estimatedTotalResultsCount"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("estimateIsExact");
        field.setXmlName(new javax.xml.namespace.QName("", "estimateIsExact"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("resultElements");
        field.setXmlName(new javax.xml.namespace.QName("", "resultElements"));
        field.setXmlType(new javax.xml.namespace.QName("urn:GoogleSearch", "ResultElement"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("searchQuery");
        field.setXmlName(new javax.xml.namespace.QName("", "searchQuery"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("startIndex");
        field.setXmlName(new javax.xml.namespace.QName("", "startIndex"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("endIndex");
        field.setXmlName(new javax.xml.namespace.QName("", "endIndex"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("searchTips");
        field.setXmlName(new javax.xml.namespace.QName("", "searchTips"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("directoryCategories");
        field.setXmlName(new javax.xml.namespace.QName("", "directoryCategories"));
        field.setXmlType(new javax.xml.namespace.QName("urn:GoogleSearch", "DirectoryCategory"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("searchTime");
        field.setXmlName(new javax.xml.namespace.QName("", "searchTime"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
