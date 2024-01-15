/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: Contact.java,v 1.1 2003/09/15 06:31:57 sr Exp $
 */

package ch.ess.addressbook.mapping.contacts;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * 
 * 
 * @version $Revision: 1.1 $ $Date: 2003/09/15 06:31:57 $
**/
public class Contact implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.util.ArrayList _attributList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Contact() {
        super();
        _attributList = new ArrayList();
    } //-- ch.ess.addressbook.mapping.contacts.Contact()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vAttribut
    **/
    public void addAttribut(Attribut vAttribut)
        throws java.lang.IndexOutOfBoundsException
    {
        _attributList.add(vAttribut);
    } //-- void addAttribut(Attribut) 

    /**
     * 
     * 
     * @param index
     * @param vAttribut
    **/
    public void addAttribut(int index, Attribut vAttribut)
        throws java.lang.IndexOutOfBoundsException
    {
        _attributList.add(index, vAttribut);
    } //-- void addAttribut(int, Attribut) 

    /**
    **/
    public void clearAttribut()
    {
        _attributList.clear();
    } //-- void clearAttribut() 

    /**
    **/
    public java.util.Enumeration enumerateAttribut()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_attributList.iterator());
    } //-- java.util.Enumeration enumerateAttribut() 

    /**
     * 
     * 
     * @param index
    **/
    public Attribut getAttribut(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _attributList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Attribut) _attributList.get(index);
    } //-- Attribut getAttribut(int) 

    /**
    **/
    public Attribut[] getAttribut()
    {
        int size = _attributList.size();
        Attribut[] mArray = new Attribut[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Attribut) _attributList.get(index);
        }
        return mArray;
    } //-- Attribut[] getAttribut() 

    /**
    **/
    public int getAttributCount()
    {
        return _attributList.size();
    } //-- int getAttributCount() 

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
     * 
     * 
     * @param vAttribut
    **/
    public boolean removeAttribut(Attribut vAttribut)
    {
        boolean removed = _attributList.remove(vAttribut);
        return removed;
    } //-- boolean removeAttribut(Attribut) 

    /**
     * 
     * 
     * @param index
     * @param vAttribut
    **/
    public void setAttribut(int index, Attribut vAttribut)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _attributList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _attributList.set(index, vAttribut);
    } //-- void setAttribut(int, Attribut) 

    /**
     * 
     * 
     * @param attributArray
    **/
    public void setAttribut(Attribut[] attributArray)
    {
        //-- copy array
        _attributList.clear();
        for (int i = 0; i < attributArray.length; i++) {
            _attributList.add(attributArray[i]);
        }
    } //-- void setAttribut(Attribut) 

    /**
     * 
     * 
     * @param reader
    **/
    public static ch.ess.addressbook.mapping.contacts.Contact unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (ch.ess.addressbook.mapping.contacts.Contact) Unmarshaller.unmarshal(ch.ess.addressbook.mapping.contacts.Contact.class, reader);
    } //-- ch.ess.addressbook.mapping.contacts.Contact unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
