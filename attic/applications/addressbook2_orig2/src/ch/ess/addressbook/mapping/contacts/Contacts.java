/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: Contacts.java,v 1.1 2003/09/15 06:31:57 sr Exp $
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
public class Contacts implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.util.ArrayList _contactList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Contacts() {
        super();
        _contactList = new ArrayList();
    } //-- ch.ess.addressbook.mapping.contacts.Contacts()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vContact
    **/
    public void addContact(Contact vContact)
        throws java.lang.IndexOutOfBoundsException
    {
        _contactList.add(vContact);
    } //-- void addContact(Contact) 

    /**
     * 
     * 
     * @param index
     * @param vContact
    **/
    public void addContact(int index, Contact vContact)
        throws java.lang.IndexOutOfBoundsException
    {
        _contactList.add(index, vContact);
    } //-- void addContact(int, Contact) 

    /**
    **/
    public void clearContact()
    {
        _contactList.clear();
    } //-- void clearContact() 

    /**
    **/
    public java.util.Enumeration enumerateContact()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_contactList.iterator());
    } //-- java.util.Enumeration enumerateContact() 

    /**
     * 
     * 
     * @param index
    **/
    public Contact getContact(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contactList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Contact) _contactList.get(index);
    } //-- Contact getContact(int) 

    /**
    **/
    public Contact[] getContact()
    {
        int size = _contactList.size();
        Contact[] mArray = new Contact[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Contact) _contactList.get(index);
        }
        return mArray;
    } //-- Contact[] getContact() 

    /**
    **/
    public int getContactCount()
    {
        return _contactList.size();
    } //-- int getContactCount() 

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
     * @param vContact
    **/
    public boolean removeContact(Contact vContact)
    {
        boolean removed = _contactList.remove(vContact);
        return removed;
    } //-- boolean removeContact(Contact) 

    /**
     * 
     * 
     * @param index
     * @param vContact
    **/
    public void setContact(int index, Contact vContact)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contactList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _contactList.set(index, vContact);
    } //-- void setContact(int, Contact) 

    /**
     * 
     * 
     * @param contactArray
    **/
    public void setContact(Contact[] contactArray)
    {
        //-- copy array
        _contactList.clear();
        for (int i = 0; i < contactArray.length; i++) {
            _contactList.add(contactArray[i]);
        }
    } //-- void setContact(Contact) 

    /**
     * 
     * 
     * @param reader
    **/
    public static ch.ess.addressbook.mapping.contacts.Contacts unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (ch.ess.addressbook.mapping.contacts.Contacts) Unmarshaller.unmarshal(ch.ess.addressbook.mapping.contacts.Contacts.class, reader);
    } //-- ch.ess.addressbook.mapping.contacts.Contacts unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
