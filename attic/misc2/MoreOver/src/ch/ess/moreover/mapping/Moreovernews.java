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

import java.util.*;

import org.exolab.castor.xml.*;

/**
 * 
 * 
 * @version $Revision$ $Date$
**/
public class Moreovernews implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    private java.util.ArrayList _articleList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Moreovernews() {
        super();
        _articleList = new ArrayList();
    } //-- ch.ess.moreover.mapping.Moreovernews()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vArticle
    **/
    public void addArticle(Article vArticle)
        throws java.lang.IndexOutOfBoundsException
    {
        _articleList.add(vArticle);
    } //-- void addArticle(Article) 

    /**
     * 
     * 
     * @param index
     * @param vArticle
    **/
    public void addArticle(int index, Article vArticle)
        throws java.lang.IndexOutOfBoundsException
    {
        _articleList.add(index, vArticle);
    } //-- void addArticle(int, Article) 

    /**
    **/
    public void clearArticle()
    {
        _articleList.clear();
    } //-- void clearArticle() 

    /**
    **/
    public java.util.Enumeration enumerateArticle()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_articleList.iterator());
    } //-- java.util.Enumeration enumerateArticle() 

    /**
     * 
     * 
     * @param index
    **/
    public Article getArticle(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _articleList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (Article) _articleList.get(index);
    } //-- Article getArticle(int) 

    /**
    **/
    public Article[] getArticle()
    {
        int size = _articleList.size();
        Article[] mArray = new Article[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (Article) _articleList.get(index);
        }
        return mArray;
    } //-- Article[] getArticle() 

    /**
    **/
    public int getArticleCount()
    {
        return _articleList.size();
    } //-- int getArticleCount() 

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
     * @param vArticle
    **/
    public boolean removeArticle(Article vArticle)
    {
        boolean removed = _articleList.remove(vArticle);
        return removed;
    } //-- boolean removeArticle(Article) 

    /**
     * 
     * 
     * @param index
     * @param vArticle
    **/
    public void setArticle(int index, Article vArticle)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _articleList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _articleList.set(index, vArticle);
    } //-- void setArticle(int, Article) 

    /**
     * 
     * 
     * @param articleArray
    **/
    public void setArticle(Article[] articleArray)
    {
        //-- copy array
        _articleList.clear();
        for (int i = 0; i < articleArray.length; i++) {
            _articleList.add(articleArray[i]);
        }
    } //-- void setArticle(Article) 

    /**
     * 
     * 
     * @param reader
    **/
    public static ch.ess.moreover.mapping.Moreovernews unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (ch.ess.moreover.mapping.Moreovernews) Unmarshaller.unmarshal(ch.ess.moreover.mapping.Moreovernews.class, reader);
    } //-- ch.ess.moreover.mapping.Moreovernews unmarshal(java.io.Reader) 

    /**
    **/
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
