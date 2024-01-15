package com.managestar.recurrance;


import junit.framework.*;

/**
 * Unit test for yearly recurrences
 */
public class YearlyTest extends AbstractTestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public YearlyTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(YearlyTest.class);
  }



  /**
   * Test YEARLY frequency
   */
  public void testYearly() {
    
    testRule("FREQ=YEARLY",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Thu Jun 26 19:54:40 GMT 2003",
               "Sat Jun 26 19:54:40 GMT 2004",
               "Sun Jun 26 19:54:40 GMT 2005",
               "Mon Jun 26 19:54:40 GMT 2006"
             });

    testRule("FREQ=YEARLY;UNTIL=20060626",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Thu Jun 26 19:54:40 GMT 2003",
               "Sat Jun 26 19:54:40 GMT 2004",
               "Sun Jun 26 19:54:40 GMT 2005",
               "Mon Jun 26 19:54:40 GMT 2006"
             }, false);

    testRule("FREQ=YEARLY;UNTIL=20060625",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Thu Jun 26 19:54:40 GMT 2003",
               "Sat Jun 26 19:54:40 GMT 2004",
               "Sun Jun 26 19:54:40 GMT 2005"                            
             }, false);

    testRule("FREQ=YEARLY;UNTIL=20020627",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
             }, false);
      
    testRule("FREQ=YEARLY;UNTIL=20060626T195440Z",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Thu Jun 26 19:54:40 GMT 2003",
               "Sat Jun 26 19:54:40 GMT 2004",
               "Sun Jun 26 19:54:40 GMT 2005",
               "Mon Jun 26 19:54:40 GMT 2006"
             }, false);

    testRule("FREQ=YEARLY;UNTIL=20060626T195439Z",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Thu Jun 26 19:54:40 GMT 2003",
               "Sat Jun 26 19:54:40 GMT 2004",
               "Sun Jun 26 19:54:40 GMT 2005"                            
             }, false);

    testRule("FREQ=YEARLY;UNTIL=20020627T195440Z",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
             }, false);      
      
    testRule("FREQ=YEARLY;INTERVAL=1",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Thu Jun 26 19:54:40 GMT 2003",
               "Sat Jun 26 19:54:40 GMT 2004",
               "Sun Jun 26 19:54:40 GMT 2005",
               "Mon Jun 26 19:54:40 GMT 2006"
             });
    
    testRule("FREQ=YEARLY;INTERVAL=2",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Sat Jun 26 19:54:40 GMT 2004",
               "Mon Jun 26 19:54:40 GMT 2006"
             });

    testRule("FREQ=YEARLY;COUNT=1;INTERVAL=1;",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
             });

    testRule("FREQ=YEARLY;COUNT=2;INTERVAL=1;",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Thu Jun 26 19:54:40 GMT 2003",
             });
    
    testRule("FREQ=YEARLY;COUNT=1;INTERVAL=2;",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002"                 
             });
    
    testRule("FREQ=YEARLY;COUNT=3;INTERVAL=2;",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",                 
               "Sat Jun 26 19:54:40 GMT 2004",
               "Mon Jun 26 19:54:40 GMT 2006"
             });

    testRule("FREQ=YEARLY;COUNT=3;INTERVAL=1;BYMONTHDAY=7,8,12;",
             new String[] {
               "Sat Jun 07 19:54:40 GMT 2003",
               "Sun Jun 08 19:54:40 GMT 2003",
               "Thu Jun 12 19:54:40 GMT 2003"
             });
    
    testRule("FREQ=YEARLY;COUNT=3;INTERVAL=2;BYMONTHDAY=7,8,12;",
             new String[] {
               "Sat Jun 07 19:54:40 GMT 2004",
               "Sun Jun 08 19:54:40 GMT 2004",
               "Thu Jun 12 19:54:40 GMT 2004"
             });
              
    testRule("FREQ=YEARLY;COUNT=4;INTERVAL=1;BYMONTHDAY=7,8,12;",
             new String[] {
               "Sat Jun 07 19:54:40 GMT 2003",
               "Sun Jun 08 19:54:40 GMT 2003",
               "Thu Jun 12 19:54:40 GMT 2003",
               "Mon Jun 07 19:54:40 GMT 2004",
             });
    
    testRule("FREQ=YEARLY;COUNT=4;INTERVAL=2;BYMONTHDAY=7,8,12;",
             new String[] {
               "Sat Jun 07 19:54:40 GMT 2004",
               "Sun Jun 08 19:54:40 GMT 2004",
               "Thu Jun 12 19:54:40 GMT 2004",
               "Fri Jun 07 19:54:40 GMT 2006",               
             });      

    testRule("FREQ=YEARLY;COUNT=7;INTERVAL=2;BYMONTHDAY=7,8,12;",
             new String[] {
               "Sat Jun 07 19:54:40 GMT 2004",
               "Sun Jun 08 19:54:40 GMT 2004",
               "Thu Jun 12 19:54:40 GMT 2004",
               "Fri Jun 07 19:54:40 GMT 2006",               
               "Sat Jun 08 19:54:40 GMT 2006",
               "Wed Jun 12 19:54:40 GMT 2006"                              
             });      


    testRule("FREQ=YEARLY;BYMONTH=7;BYMONTHDAY=7,8,12;",
             new String[] {
               "Sun Jul 07 19:54:40 GMT 2002",
               "Mon Jul 08 19:54:40 GMT 2002",
               "Fri Jul 12 19:54:40 GMT 2002",
               "Mon Jul 07 19:54:40 GMT 2003",
               "Tue Jul 08 19:54:40 GMT 2003",
               "Sat Jul 12 19:54:40 GMT 2003",
               "Wed Jul 07 19:54:40 GMT 2004",
               "Thu Jul 08 19:54:40 GMT 2004",
               "Mon Jul 12 19:54:40 GMT 2004",
               "Thu Jul 07 19:54:40 GMT 2005",
               "Fri Jul 08 19:54:40 GMT 2005",
               "Tue Jul 12 19:54:40 GMT 2005",
               "Fri Jul 07 19:54:40 GMT 2006",               
               "Sat Jul 08 19:54:40 GMT 2006",
               "Wed Jul 12 19:54:40 GMT 2006"                              
             });  


    testRule("FREQ=YEARLY;BYMONTH=7;INTERVAL=2;BYMONTHDAY=7,8,12;",
             new String[] {
               "Sun Jul 07 19:54:40 GMT 2002",
               "Mon Jul 08 19:54:40 GMT 2002",
               "Fri Jul 12 19:54:40 GMT 2002",
               "Wed Jul 07 19:54:40 GMT 2004",
               "Thu Jul 08 19:54:40 GMT 2004",
               "Mon Jul 12 19:54:40 GMT 2004",
               "Fri Jul 07 19:54:40 GMT 2006",               
               "Sat Jul 08 19:54:40 GMT 2006",
               "Wed Jul 12 19:54:40 GMT 2006"                              
             });  


    testRule("FREQ=YEARLY;COUNT=5;BYMONTH=7;INTERVAL=2;BYMONTHDAY=7,8,12;",
             new String[] {
               "Sun Jul 07 19:54:40 GMT 2002",
               "Mon Jul 08 19:54:40 GMT 2002",
               "Fri Jul 12 19:54:40 GMT 2002",
               "Wed Jul 07 19:54:40 GMT 2004",
               "Thu Jul 08 19:54:40 GMT 2004",
             });  


    testRule("FREQ=YEARLY;COUNT=5;INTERVAL=1;BYMONTH=1,3,5;",
             new String[] {
               "Sun Jan 26 19:54:40 GMT 2003",
               "Wed Mar 26 19:54:40 GMT 2003",
               "Mon May 26 19:54:40 GMT 2003",
               "Mon Jan 26 19:54:40 GMT 2004",
               "Fri Mar 26 19:54:40 GMT 2004"
             });  
    
    

    testRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=7;BYHOUR=0,12;BYMINUTE=0;BYSECOND=0",
             new String[] {
               "Fri Jul 26 00:00:00 GMT 2002",
               "Fri Jul 26 12:00:00 GMT 2002",
               "Sat Jul 26 00:00:00 GMT 2003",
               "Sat Jul 26 12:00:00 GMT 2003",
               "Mon Jul 26 00:00:00 GMT 2004",
               "Mon Jul 26 12:00:00 GMT 2004",
               "Tue Jul 26 00:00:00 GMT 2005",
               "Tue Jul 26 12:00:00 GMT 2005",
               "Wed Jul 26 00:00:00 GMT 2006",
               "Wed Jul 26 12:00:00 GMT 2006",
             });  


    testRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=10,11,12;BYHOUR=0,12;BYMINUTE=0;BYSECOND=0",
             new String[] {
               "Sat Oct 26 00:00:00 GMT 2002",
               "Sat Oct 26 12:00:00 GMT 2002",
               "Tue Nov 26 00:00:00 GMT 2002",
               "Tue Nov 26 12:00:00 GMT 2002",
               "Thu Dec 26 00:00:00 GMT 2002",
               "Thu Dec 26 12:00:00 GMT 2002",
               "Sun Oct 26 00:00:00 GMT 2003",
               "Sun Oct 26 12:00:00 GMT 2003",
               "Wed Nov 26 00:00:00 GMT 2003",
               "Wed Nov 26 12:00:00 GMT 2003",
               "Fri Dec 26 00:00:00 GMT 2003",
               "Fri Dec 26 12:00:00 GMT 2003",
               "Tue Oct 26 00:00:00 GMT 2004",
               "Tue Oct 26 12:00:00 GMT 2004",
               "Fri Nov 26 00:00:00 GMT 2004",
               "Fri Nov 26 12:00:00 GMT 2004",
               "Sun Dec 26 00:00:00 GMT 2004",
               "Sun Dec 26 12:00:00 GMT 2004",
               "Wed Oct 26 00:00:00 GMT 2005",
               "Wed Oct 26 12:00:00 GMT 2005",
               "Sat Nov 26 00:00:00 GMT 2005",
               "Sat Nov 26 12:00:00 GMT 2005",
               "Mon Dec 26 00:00:00 GMT 2005",
               "Mon Dec 26 12:00:00 GMT 2005",
               "Thu Oct 26 00:00:00 GMT 2006",
               "Thu Oct 26 12:00:00 GMT 2006",
               "Sat Nov 26 00:00:00 GMT 2006",
               "Sat Nov 26 12:00:00 GMT 2006",
               "Tue Dec 26 00:00:00 GMT 2006",
               "Tue Dec 26 12:00:00 GMT 2006"                              
             });  


    testRule("FREQ=YEARLY;COUNT=20;INTERVAL=1;BYWEEKNO=1,10,20,30,40,50;BYDAY=TH",
             new String[] {
               "Thu Jul 25 19:54:40 GMT 2002",
               "Thu Oct 03 19:54:40 GMT 2002",
               "Thu Dec 12 19:54:40 GMT 2002",
               "Thu Jan 02 19:54:40 GMT 2003",
               "Thu Mar 06 19:54:40 GMT 2003",
               "Thu May 15 19:54:40 GMT 2003",
               "Thu Jul 24 19:54:40 GMT 2003",
               "Thu Oct 02 19:54:40 GMT 2003",
               "Thu Dec 11 19:54:40 GMT 2003",
               "Thu Jan 01 19:54:40 GMT 2004",
               "Thu Mar 04 19:54:40 GMT 2004",
               "Thu May 13 19:54:40 GMT 2004",
               "Thu Jul 22 19:54:40 GMT 2004",
               "Thu Sep 30 19:54:40 GMT 2004",               
               "Thu Dec 09 19:54:40 GMT 2004",
               "Thu Jan 6 19:54:40 GMT 2005",
               "Thu Mar 10 19:54:40 GMT 2005",
               "Thu May 19 19:54:40 GMT 2005",
               "Thu Jul 28 19:54:40 GMT 2005",
               "Thu Oct 6 19:54:40 GMT 2005"
             }); 

    testRule("FREQ=YEARLY;COUNT=20;INTERVAL=1;WKST=SU;BYWEEKNO=1,10,20,30,40,50;BYDAY=TH",
             new String[] {
               "Thu Jul 25 19:54:40 GMT 2002",
               "Thu Oct 03 19:54:40 GMT 2002",
               "Thu Dec 12 19:54:40 GMT 2002",
               "Thu Jan 02 19:54:40 GMT 2003",
               "Thu Mar 06 19:54:40 GMT 2003",
               "Thu May 15 19:54:40 GMT 2003",
               "Thu Jul 24 19:54:40 GMT 2003",
               "Thu Oct 02 19:54:40 GMT 2003",
               "Thu Dec 11 19:54:40 GMT 2003",
               "Thu Jan 08 19:54:40 GMT 2004",
               "Thu Mar 11 19:54:40 GMT 2004",
               "Thu May 20 19:54:40 GMT 2004",
               "Thu Jul 29 19:54:40 GMT 2004",
               "Thu Oct 07 19:54:40 GMT 2004",               
               "Thu Dec 16 19:54:40 GMT 2004",
               "Thu Jan 6 19:54:40 GMT 2005",
               "Thu Mar 10 19:54:40 GMT 2005",
               "Thu May 19 19:54:40 GMT 2005",
               "Thu Jul 28 19:54:40 GMT 2005",
               "Thu Oct 6 19:54:40 GMT 2005"
             }); 


    testRule("FREQ=YEARLY;COUNT=20;INTERVAL=1;BYYEARDAY=1,130,-130,-1",
             new String[] {
               "Sat Aug 24 19:54:40 GMT 2002",
               "Tue Dec 31 19:54:40 GMT 2002",
               "Wed Jan 01 19:54:40 GMT 2003",
               "Sat May 10 19:54:40 GMT 2003",
               "Sun Aug 24 19:54:40 GMT 2003",
               "Wed Dec 31 19:54:40 GMT 2003",
               "Thu Jan 01 19:54:40 GMT 2004",
               "Sun May 09 19:54:40 GMT 2004",
               "Tue Aug 24 19:54:40 GMT 2004",
               "Fri Dec 31 19:54:40 GMT 2004",
               "Sat Jan 01 19:54:40 GMT 2005",
               "Tue May 10 19:54:40 GMT 2005",
               "Wed Aug 24 19:54:40 GMT 2005",
               "Sat Dec 31 19:54:40 GMT 2005",
               "Sun Jan 01 19:54:40 GMT 2006",
               "Wed May 10 19:54:40 GMT 2006",
               "Thu Aug 24 19:54:40 GMT 2006",
               "Sun Dec 31 19:54:40 GMT 2006",
               "Mon Jan 01 19:54:40 GMT 2007",
               "Thu May 10 19:54:40 GMT 2007"
             });  



    testRule("FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=SU;BYHOUR=8,9;BYMINUTE=30",
             new String[] {
               "Sun Jan 4 08:30:40 GMT 2004",
               "Sun Jan 4 09:30:40 GMT 2004",
               "Sun Jan 1 08:30:40 GMT 2006",
               "Sun Jan 1 09:30:40 GMT 2006",
               "Sun Jan 11 08:30:40 GMT 2004",
               "Sun Jan 11 09:30:40 GMT 2004",
               "Sun Jan 8 08:30:40 GMT 2006",
               "Sun Jan 8 09:30:40 GMT 2006",  
               "Sun Jan 18 08:30:40 GMT 2004",
               "Sun Jan 18 09:30:40 GMT 2004",
               "Sun Jan 15 08:30:40 GMT 2006",
               "Sun Jan 15 09:30:40 GMT 2006",  
               "Sun Jan 22 08:30:40 GMT 2006",
               "Sun Jan 22 09:30:40 GMT 2006",
               "Sun Jan 25 08:30:40 GMT 2004",
               "Sun Jan 25 09:30:40 GMT 2004",
               "Sun Jan 29 08:30:40 GMT 2006",
               "Sun Jan 29 09:30:40 GMT 2006"               
             }); 
      
    testRule("FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=1SU;BYHOUR=8,9;BYMINUTE=30",
             new String[] {
               "Sun Jan 4 08:30:40 GMT 2004",
               "Sun Jan 4 09:30:40 GMT 2004",
               "Sun Jan 1 08:30:40 GMT 2006",
               "Sun Jan 1 09:30:40 GMT 2006"
             });   

    testRule("FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=2SU;BYHOUR=8,9;BYMINUTE=30",
             new String[] {
               "Sun Jan 11 08:30:40 GMT 2004",
               "Sun Jan 11 09:30:40 GMT 2004",
               "Sun Jan 8 08:30:40 GMT 2006",
               "Sun Jan 8 09:30:40 GMT 2006"
             });                  

    testRule("FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=3SU;BYHOUR=8,9;BYMINUTE=30",
             new String[] {
               "Sun Jan 18 08:30:40 GMT 2004",
               "Sun Jan 18 09:30:40 GMT 2004",
               "Sun Jan 15 08:30:40 GMT 2006",
               "Sun Jan 15 09:30:40 GMT 2006"
             });                  

    testRule("FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=4SU;BYHOUR=8,9;BYMINUTE=30",
             new String[] {
               "Sun Jan 25 08:30:40 GMT 2004",
               "Sun Jan 25 09:30:40 GMT 2004",
               "Sun Jan 22 08:30:40 GMT 2006",
               "Sun Jan 22 09:30:40 GMT 2006"
             });                  

      
    testRule("FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=-1SU;BYHOUR=8,9;BYMINUTE=30",
             new String[] {
               "Sun Jan 25 08:30:40 GMT 2004",
               "Sun Jan 25 09:30:40 GMT 2004",
               "Sun Jan 29 08:30:40 GMT 2006",
               "Sun Jan 29 09:30:40 GMT 2006"
             });    
             
             
    testRule("FREQ=YEARLY;INTERVAL=1;BYMONTH=1;BYDAY=SU,MO,TU,WE,TH,FR;COUNT=28",
             new String[] {
               "Wed Jan 01 19:54:40 GMT 2003",
               "Thu Jan 02 19:54:40 GMT 2003",
               "Fri Jan 03 19:54:40 GMT 2003",
               "Sun Jan 05 19:54:40 GMT 2003",
               "Mon Jan 06 19:54:40 GMT 2003",
               "Tue Jan 07 19:54:40 GMT 2003",
               "Wed Jan 08 19:54:40 GMT 2003",
               "Thu Jan 09 19:54:40 GMT 2003",
               "Fri Jan 10 19:54:40 GMT 2003",
               "Sun Jan 12 19:54:40 GMT 2003",
               "Mon Jan 13 19:54:40 GMT 2003",
               "Tue Jan 14 19:54:40 GMT 2003",
               "Wed Jan 15 19:54:40 GMT 2003",
               "Thu Jan 16 19:54:40 GMT 2003",
               "Fri Jan 17 19:54:40 GMT 2003",
               "Sun Jan 19 19:54:40 GMT 2003",
               "Mon Jan 20 19:54:40 GMT 2003",
               "Tue Jan 21 19:54:40 GMT 2003",
               "Wed Jan 22 19:54:40 GMT 2003",
               "Thu Jan 23 19:54:40 GMT 2003",
               "Fri Jan 24 19:54:40 GMT 2003",
               "Sun Jan 26 19:54:40 GMT 2003",
               "Mon Jan 27 19:54:40 GMT 2003",
               "Tue Jan 28 19:54:40 GMT 2003",
               "Wed Jan 29 19:54:40 GMT 2003",
               "Thu Jan 30 19:54:40 GMT 2003",
               "Fri Jan 31 19:54:40 GMT 2003",
               "Thu Jan 1 19:54:40 GMT 2004",               
             });                        
         
    testRule("FREQ=YEARLY;INTERVAL=3;COUNT=10;BYYEARDAY=1,100,200",
             new String[] {
               "Fri Jul 19 19:54:40 GMT 2002",
               "Sat Jan 01 19:54:40 GMT 2005",
               "Sun Apr 10 19:54:40 GMT 2005",
               "Tue Jul 19 19:54:40 GMT 2005"
             });          
       
    testRule("FREQ=YEARLY;BYDAY=20MO",
             new String[] {
               "Mon May 19 19:54:40 GMT 2003",
               "Mon May 17 19:54:40 GMT 2004",
               "Mon May 16 19:54:40 GMT 2005",
               "Mon May 15 19:54:40 GMT 2006",
               "Mon May 14 19:54:40 GMT 2007"
             });        
             
    testRule("FREQ=YEARLY;BYWEEKNO=20;BYDAY=MO;COUNT=10",
             new String[] {
               "Mon May 12 19:54:40 GMT 2003",
               "Mon May 10 19:54:40 GMT 2004",
               "Mon May 16 19:54:40 GMT 2005",
               "Mon May 15 19:54:40 GMT 2006",
               "Mon May 14 19:54:40 GMT 2007"
             });     
             

    testRule("FREQ=YEARLY;BYMONTH=3;BYDAY=TH;COUNT=10",
             new String[] {
               "Thu Mar 06 19:54:40 GMT 2003",
               "Thu Mar 13 19:54:40 GMT 2003",
               "Thu Mar 20 19:54:40 GMT 2003",
               "Thu Mar 27 19:54:40 GMT 2003",
               "Thu Mar 04 19:54:40 GMT 2004",
               "Thu Mar 11 19:54:40 GMT 2004",
               "Thu Mar 18 19:54:40 GMT 2004",
               "Thu Mar 25 19:54:40 GMT 2004",
               "Thu Mar 03 19:54:40 GMT 2005",
               "Thu Mar 10 19:54:40 GMT 2005"
             });   
             

    testRule("FREQ=YEARLY;BYDAY=TH;BYMONTH=6,7,8;COUNT=15",
             new String[] {
               "Thu Jun 27 19:54:40 GMT 2002",
               "Thu Jul 04 19:54:40 GMT 2002",
               "Thu Jul 11 19:54:40 GMT 2002",
               "Thu Jul 18 19:54:40 GMT 2002",
               "Thu Jul 25 19:54:40 GMT 2002",
               "Thu Aug 01 19:54:40 GMT 2002",
               "Thu Aug 08 19:54:40 GMT 2002",
               "Thu Aug 15 19:54:40 GMT 2002",
               "Thu Aug 22 19:54:40 GMT 2002",
               "Thu Aug 29 19:54:40 GMT 2002",
               "Thu Jun 05 19:54:40 GMT 2003",
               "Thu Jun 12 19:54:40 GMT 2003",
               "Thu Jun 19 19:54:40 GMT 2003",
               "Thu Jun 26 19:54:40 GMT 2003",
               "Thu Jul 03 19:54:40 GMT 2003"
             });   
             

    testRule("FREQ=YEARLY;INTERVAL=4;BYMONTH=11;BYDAY=TU;BYMONTHDAY=2,3,4,5,6,7,8;COUNT=30",
             new String[] {
               "Tue Nov 05 19:54:40 GMT 2002",
               "Tue Nov 07 19:54:40 GMT 2006",
             });    
             
    testRule("FREQ=YEARLY;INTERVAL=1;BYDAY=2SU;BYMONTH=5",
             new String[] {
               "Sun May 11 19:54:40 GMT 2003",
               "Sun May 09 19:54:40 GMT 2004",
               "Sun May 08 19:54:40 GMT 2005",
               "Sun May 14 19:54:40 GMT 2006",
               "Sun May 13 19:54:40 GMT 2007" 
             });    


    testRule("FREQ=YEARLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-2",
             new String[] {
               "Mon Dec 30 20:54:40 CET 2002",
               "Tue Dec 30 20:54:40 CET 2003",
               "Thu Dec 30 20:54:40 CET 2004",
               "Thu Dec 29 20:54:40 CET 2005",
               "Thu Dec 28 20:54:40 CET 2006",
               "Fri Jun 22 21:54:40 CEST 2007"
             }, false);  
    
  }
}
