package cmp.holidays;

// cmp.holidays.ArmedForcesDay
import cmp.business.BigDate;

/**
 * See Class cmp.Holiday.Holinfo for JavaDOC
 */

public class ArmedForcesDay extends HolInfo {

   public String getAuthority() {
	  return "http://www.optonline.com/comptons/ceo/07180_Q.html";
   }   
   public int getFirstYear(int base) {
	  switch ( base ) {
	  default:
	  case CELEBRATED:
		 return 1936;
	  case PROCLAIMED:
		 return 1950;
	  }
   }   
   public String getName() {
	  return "Armed Forces Day";
   }   
   public String getRule() {
	  return "Third Saturday in May\n" +
	  "Originally Army Day, celebrated on April 6 (1936-49)\n" +
	  "Proclaimed as Armed Forces day in 1950.";
   }   
   public int when( int year, boolean shift, int base ) {
	  if ( !isYearValid(year, base) ) {
		 return BigDate.NULL_ORDINAL;
	  }
	  if ( 1936 <= year && year <= 1949 ) {
		 return shiftSatToFriSunToMon(BigDate.toOrdinal(year, 4, 6), shift);
	  } else {
		 return shiftSatToFriSunToMon(BigDate.ordinalOfnthXXXDay(3 /* third */,
																 6 /* saturday */,
																 year,
																 5 /* may */),
									  shift);
	  }
   } // end when.   
} // end class ArmedForcesDay