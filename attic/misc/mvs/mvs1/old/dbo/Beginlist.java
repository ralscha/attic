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
public class Beginlist {


      //--------------------/
     //- Member Variables -/
    //--------------------/

    public static String FOR_NAME = "mvs.dbo.Beginlist";

    public static String WORD1 = "word1";

    public static String WORD2 = "word2";

    public static String TOTAL = "total";

    private int word1;

    private int word2;

    private int total;


      //-----------/
     //- Methods -/
    //-----------/

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

}
