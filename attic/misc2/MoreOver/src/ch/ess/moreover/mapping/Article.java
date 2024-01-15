/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.3.9+</a>, using an
 * XML Schema.
 * $Id$
 */

package ch.ess.moreover.mapping;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.*;

/**
 * 
 * 
 * @version $Revision$ $Date$
**/
public class Article implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.lang.String _id;

    private java.lang.String _url;

    private java.lang.String _headline_text;

    private java.lang.String _source;

    private java.lang.String _media_type;

    private java.lang.String _cluster;

    private java.lang.String _tagline;

    private java.lang.String _document_url;

    private java.lang.String _harvest_time;

    private java.lang.String _access_registration;

    private java.lang.String _access_status;


      //----------------/
     //- Constructors -/
    //----------------/

    public Article() {
        super();
    } //-- ch.ess.moreover.mapping.Article()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'access_registration'.
     * 
     * @return the value of field 'access_registration'.
    **/
    public java.lang.String getAccess_registration()
    {
        return this._access_registration;
    } //-- java.lang.String getAccess_registration() 

    /**
     * Returns the value of field 'access_status'.
     * 
     * @return the value of field 'access_status'.
    **/
    public java.lang.String getAccess_status()
    {
        return this._access_status;
    } //-- java.lang.String getAccess_status() 

    /**
     * Returns the value of field 'cluster'.
     * 
     * @return the value of field 'cluster'.
    **/
    public java.lang.String getCluster()
    {
        return this._cluster;
    } //-- java.lang.String getCluster() 

    /**
     * Returns the value of field 'document_url'.
     * 
     * @return the value of field 'document_url'.
    **/
    public java.lang.String getDocument_url()
    {
        return this._document_url;
    } //-- java.lang.String getDocument_url() 

    /**
     * Returns the value of field 'harvest_time'.
     * 
     * @return the value of field 'harvest_time'.
    **/
    public java.lang.String getHarvest_time()
    {
        return this._harvest_time;
    } //-- java.lang.String getHarvest_time() 

    /**
     * Returns the value of field 'headline_text'.
     * 
     * @return the value of field 'headline_text'.
    **/
    public java.lang.String getHeadline_text()
    {
        return this._headline_text;
    } //-- java.lang.String getHeadline_text() 

    /**
     * Returns the value of field 'id'.
     * 
     * @return the value of field 'id'.
    **/
    public java.lang.String getId()
    {
        return this._id;
    } //-- java.lang.String getId() 

    /**
     * Returns the value of field 'media_type'.
     * 
     * @return the value of field 'media_type'.
    **/
    public java.lang.String getMedia_type()
    {
        return this._media_type;
    } //-- java.lang.String getMedia_type() 

    /**
     * Returns the value of field 'source'.
     * 
     * @return the value of field 'source'.
    **/
    public java.lang.String getSource()
    {
        return this._source;
    } //-- java.lang.String getSource() 

    /**
     * Returns the value of field 'tagline'.
     * 
     * @return the value of field 'tagline'.
    **/
    public java.lang.String getTagline()
    {
        return this._tagline;
    } //-- java.lang.String getTagline() 

    /**
     * Returns the value of field 'url'.
     * 
     * @return the value of field 'url'.
    **/
    public java.lang.String getUrl()
    {
        return this._url;
    } //-- java.lang.String getUrl() 

    /**
    **/
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * 
     * 
     * @param out
    **/
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * 
     * 
     * @param handler
    **/
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'access_registration'.
     * 
     * @param access_registration the value of field
     * 'access_registration'.
    **/
    public void setAccess_registration(java.lang.String access_registration)
    {
        this._access_registration = access_registration;
    } //-- void setAccess_registration(java.lang.String) 

    /**
     * Sets the value of field 'access_status'.
     * 
     * @param access_status the value of field 'access_status'.
    **/
    public void setAccess_status(java.lang.String access_status)
    {
        this._access_status = access_status;
    } //-- void setAccess_status(java.lang.String) 

    /**
     * Sets the value of field 'cluster'.
     * 
     * @param cluster the value of field 'cluster'.
    **/
    public void setCluster(java.lang.String cluster)
    {
        this._cluster = cluster;
    } //-- void setCluster(java.lang.String) 

    /**
     * Sets the value of field 'document_url'.
     * 
     * @param document_url the value of field 'document_url'.
    **/
    public void setDocument_url(java.lang.String document_url)
    {
        this._document_url = document_url;
    } //-- void setDocument_url(java.lang.String) 

    /**
     * Sets the value of field 'harvest_time'.
     * 
     * @param harvest_time the value of field 'harvest_time'.
    **/
    public void setHarvest_time(java.lang.String harvest_time)
    {
        this._harvest_time = harvest_time;
    } //-- void setHarvest_time(java.lang.String) 

    /**
     * Sets the value of field 'headline_text'.
     * 
     * @param headline_text the value of field 'headline_text'.
    **/
    public void setHeadline_text(java.lang.String headline_text)
    {
        this._headline_text = headline_text;
    } //-- void setHeadline_text(java.lang.String) 

    /**
     * Sets the value of field 'id'.
     * 
     * @param id the value of field 'id'.
    **/
    public void setId(java.lang.String id)
    {
        this._id = id;
    } //-- void setId(java.lang.String) 

    /**
     * Sets the value of field 'media_type'.
     * 
     * @param media_type the value of field 'media_type'.
    **/
    public void setMedia_type(java.lang.String media_type)
    {
        this._media_type = media_type;
    } //-- void setMedia_type(java.lang.String) 

    /**
     * Sets the value of field 'source'.
     * 
     * @param source the value of field 'source'.
    **/
    public void setSource(java.lang.String source)
    {
        this._source = source;
    } //-- void setSource(java.lang.String) 

    /**
     * Sets the value of field 'tagline'.
     * 
     * @param tagline the value of field 'tagline'.
    **/
    public void setTagline(java.lang.String tagline)
    {
        this._tagline = tagline;
    } //-- void setTagline(java.lang.String) 

    /**
     * Sets the value of field 'url'.
     * 
     * @param url the value of field 'url'.
    **/
    public void setUrl(java.lang.String url)
    {
        this._url = url;
    } //-- void setUrl(java.lang.String) 

    /**
     * 
     * 
     * @param reader
    **/
    public static ch.ess.moreover.mapping.Article unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (ch.ess.moreover.mapping.Article) Unmarshaller.unmarshal(ch.ess.moreover.mapping.Article.class, reader);
    } //-- ch.ess.moreover.mapping.Article unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
