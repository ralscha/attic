/*
 * $Id
*/

package mvs.dbo;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * This file was generated
 * @version $Revision$ $Date$
**/
public class Wordlist {


      //--------------------/
     //- Member Variables -/
    //--------------------/

    public static String FOR_NAME = "mvs.dbo.Wordlist";

    public static String HASH = "hash";

    public static String WORD = "word";

    private int hash;

    private String word;


      //-----------/
     //- Methods -/
    //-----------/

    /**
    **/
    public int getHash()
    {
    return hash;
    } //-- int getHash() 

    /**
    **/
    public String getWord()
    {
    return word;
    } //-- String getWord() 

    /**
     * 
     * @param hash
    **/
    public void setHash(int hash)
    {
    this.hash = hash;
    } //-- void setHash(int) 

    /**
     * 
     * @param word
    **/
    public void setWord(String word)
    {
    this.word = word;
    } //-- void setWord(String) 

}
