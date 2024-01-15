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
public class Word3 {


      //--------------------/
     //- Member Variables -/
    //--------------------/

    public static String FOR_NAME = "mvs.dbo.Word3";

    public static String WORD1 = "word1";

    public static String WORD2 = "word2";

    public static String WORD3 = "word3";

    public static String HITS = "hits";

    private int word1;

    private int word2;

    private int word3;

    private int hits;


      //-----------/
     //- Methods -/
    //-----------/

    /**
    **/
    public int getHits()
    {
    return hits;
    } //-- int getHits() 

    /**
    **/
    public int getWord1()
    {
    return word1;
    } //-- int getWord1() 

    /**
    **/
    public int getWord2()
    {
    return word2;
    } //-- int getWord2() 

    /**
    **/
    public int getWord3()
    {
    return word3;
    } //-- int getWord3() 

    /**
     * 
     * @param hits
    **/
    public void setHits(int hits)
    {
    this.hits = hits;
    } //-- void setHits(int) 

    /**
     * 
     * @param word1
    **/
    public void setWord1(int word1)
    {
    this.word1 = word1;
    } //-- void setWord1(int) 

    /**
     * 
     * @param word2
    **/
    public void setWord2(int word2)
    {
    this.word2 = word2;
    } //-- void setWord2(int) 

    /**
     * 
     * @param word3
    **/
    public void setWord3(int word3)
    {
    this.word3 = word3;
    } //-- void setWord3(int) 

}
