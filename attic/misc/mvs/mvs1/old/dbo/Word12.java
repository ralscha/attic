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
public class Word12 {


      //--------------------/
     //- Member Variables -/
    //--------------------/

    public static String FOR_NAME = "mvs.dbo.Word12";

    public static String WORD1 = "word1";

    public static String WORD2 = "word2";

    public static String TOTAL = "total";

    public static String WORDS3 = "words3";

    private int word1;

    private int word2;

    private int total;

    private Collection words3;


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * @param word3
    **/
    public void addWords3(mvs.dbo.Word3 word3)
    {
        if (words3 == null) {
      this.words3 = new ArrayList();
        }
        this.words3.add(word3);
    } //-- void addWords3(mvs.dbo.Word3) 

    /**
    **/
    public int getTotal()
    {
    return total;
    } //-- int getTotal() 

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
    public Collection getWords3()
    {
    return words3;
    } //-- Collection getWords3() 

    /**
     * 
     * @param total
    **/
    public void setTotal(int total)
    {
    this.total = total;
    } //-- void setTotal(int) 

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
     * @param words3
    **/
    public void setWords3(Collection words3)
    {
    this.words3 = words3;
    } //-- void setWords3(Collection) 

}
