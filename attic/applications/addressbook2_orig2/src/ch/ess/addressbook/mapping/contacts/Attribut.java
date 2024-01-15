/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: Attribut.java,v 1.1 2003/09/15 06:31:57 sr Exp $
 */

package ch.ess.addressbook.mapping.contacts;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;


  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/


/**
 * 
 * 
 * @version $Revision: 1.1 $ $Date: 2003/09/15 06:31:57 $
**/
public class Attribut implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * internal content storage
    **/
    private java.lang.String _content = "";

    private ch.ess.addressbook.mapping.contacts.types.AttributNames _name;


      //----------------/
     //- Constructors -/
    //----------------/

    public Attribut() {
        super();
        setContent("");
    } //-- ch.ess.addressbook.mapping.contacts.Attribut()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'content'. The field 'content'
     * has the following description: internal content storage
     * 
     * @return the value of field 'content'.
    **/
    public java.lang.String getContent()
    {
        return this._content;
    } //-- java.lang.String getContent() 

    /**
     * Returns the value of field 'name'.
     * 
     * @return the value of field 'name'.
    **/
    public ch.ess.addressbook.mapping.contacts.types.AttributNames getName()
    {
        return this._name;
    } //-- ch.ess.addressbook.mapping.contacts.types.AttributNames getName() 

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
     * Sets the value of field 'content'. The field 'content' has
     * the following description: internal content storage
     * 
     * @param content the value of field 'content'.
    **/
    public void setContent(java.lang.String content)
    {
        this._content = content;
    } //-- void setContent(java.lang.String) 

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
    **/
    public void setName(ch.ess.addressbook.mapping.contacts.types.AttributNames name)
    {
        this._name = name;
    } //-- void setName(ch.ess.addressbook.mapping.contacts.types.AttributNames) 

    /**
     * 
     * 
     * @param reader
    **/
    public static ch.ess.addressbook.mapping.contacts.Attribut unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (ch.ess.addressbook.mapping.contacts.Attribut) Unmarshaller.unmarshal(ch.ess.addressbook.mapping.contacts.Attribut.class, reader);
    } //-- ch.ess.addressbook.mapping.contacts.Attribut unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
