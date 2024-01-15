package cmp.holidays;

// cmp.holidays.AmericanThanksgiving
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class AmericanThanksgiving extends HolInfo {

   public String getAuthority() {
	  return   "http://www.serve.com/shea/germusa/thanksgv.htm\n"
 + "and http://members.aol.com/calebj/thanksgiving.html";
   }      
   public int getFirstYear(int base) {
	  return 1621; 
   }   
   public String getName() {
	  return "American Thanksgiving";
   }   
   public String getRule() {
	  return   "1621         -> first Thanksgiving, precise date unknown.\n"
 + "1622         -> was no Thanksgiving.\n"
 + "1623 .. 1675 -> precise date unknown.\n"
 + "1676 .. 1862 -> June 29.\n"
 + "1863 .. 1938 -> last Thursday of November.\n"
 + "1939 .. 1941 -> 2nd to last Thursday of November.\n"
 + "1942 .. now  -> 4th Thursday of November.";
   }      
   public int when( int year, boolean shift, int base ) {
	  if ( !isYearValid(year, base) ) {
		 return BigDate.NULL_ORDINAL;
	  }
	  if ( year < 1621 || year == 1622 ) {
return BigDate.NULL_ORDINAL;
}

if ( 1621 <= year && year <= 1675 ) {
return BigDate.NULL_ORDINAL;
}

if ( 1676 <= year && year <= 1862 ) {
return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 6, 29), shift);
}


if ( 1863 <= year && year <= 1938 ) {
return BigDate.ordinalOfnthXXXDay(5 /*last*/,
4 /* Thursday */,
year,
11 /* nov */);
}

if ( 1939 <= year && year <= 1941 ) {
return BigDate.ordinalOfnthXXXDay(5 /*last*/,
4 /* Thursday */,
year,
11 /* nov */) - 7 /* second last */;
}

if ( 1942 <= year ) {
return BigDate.ordinalOfnthXXXDay(4 /* fourth */,
4 /* Thursday */,
year,
11 /* nov */);
}

return BigDate.NULL_ORDINAL;

   } // end when.   
} // end class AmericanThanksgiving