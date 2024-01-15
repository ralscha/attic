import java.util.Date;

public class JulianDate extends Date
{
	private long julian;

	final private String[] monthsShort = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
			 "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		  
	final private String[] monthsLong = { "January", "February", "March", "April",
			  "May", "June", "July", "August", "September",
			  "October", "November", "December" };

	public JulianDate() 
	{
		super();
		calculateJulian();
	}
	
	/*
	public JulianDate(long date) 
	{
		super(date);
		calculateJulian();
	}*/
	public JulianDate(long j)
	{
		setJulian(j);
	}

	public JulianDate(int year, int month, int date) 
	{
		this(year, month, date, 0, 0, 0);
	}

	public JulianDate(int year, int month, int date, int hrs, int min) 
	{
		this(year, month, date, hrs, min, 0);
	}

	public JulianDate(int year, int month, int date, int hrs, int min, int sec) 
	{
		super(year, month, date, hrs, min, sec);
		calculateJulian();
	}

	public JulianDate(String s) 
	{
		super(s);
		calculateJulian();
	}

	public long getJulian()
	{
		return julian;
	}

	public void setJulian(long j)
	{
		julian = j;
		calculateNormalDate();	
	}

	public static boolean isLeapYear(int y)
	{
		if ((y % 400) == 0) return true;
		if ((y % 100) != 0) return false;
		return ((y % 4) == 0);
	}


 /* Format	Description  
    %b Abbreviated month name                           
    %B Full month name
    %d Day of the month as a decimal number (1-31)
    %D Day of the month as a decimal number (01-31)
    %m Month as a decimal number (1-12)
    %M Month as a decimal number (01-12)
    %y Year without the century as a decimal number (00-99)
    %Y Year with the century as a decimal number 
    %h Hours as a decimal number (0-23)
    %H Hours as a decimal number (00-23)
    %i Minutes as a decimal number (0-59)
    %I Minutes as a decimal number (00-59)
    %s Seconds as a decimal number (0-59)
    %S Seconds as a decimal number (00-59) 
  */

	public String format(String formatString)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < formatString.length(); i++)
		{	
			if (formatString.charAt(i) == '%')
			{
				i++;
				switch(formatString.charAt(i))
				{
					case 'b': sb.append(monthsShort[getMonth()]); break;				
					case 'B': sb.append(monthsLong[getMonth()]); break;				
					case 'd': sb.append(getDate()); break;				
					case 'D': if (getDate() < 10)
							 sb.append("0");
						    sb.append(getDate()); 
						    break;				
					case 'm': sb.append(getMonth()+1); break;			
					case 'M': if ((getMonth()+1) < 10)
							 sb.append("0");
						    sb.append(getMonth()+1);
                 	      	          break;	
		        	      case 'y': sb.append(getYear()); break;
		                  case 'Y': sb.append(getYear()+1900); break;    

					case 'h': sb.append(getHours()); break;			
					case 'H': if (getHours() < 10)
							 sb.append("0");
						    sb.append(getHours());
                 	      	          break;						
					case 'i': sb.append(getMinutes()); break;			
					case 'I': if (getMinutes() < 10)
							 sb.append("0");
						    sb.append(getMinutes());
                 	      	          break;			
					case 's': sb.append(getSeconds()); break;			
					case 'S': if (getSeconds() < 10)
							 sb.append("0");
						    sb.append(getSeconds());
                 	      	          break;									
       
 	      	            default:  sb.append(formatString.charAt(i));
				}
			}
			else
			  sb.append(formatString.charAt(i));
		}
		return sb.toString();
	}                       

	private void calculateJulian() 
	{
		int tl = 0;
		if ((getMonth()+1) < 3)
			tl--;

		julian = (long)getDate() - 32075L + ((1461L * (long)(getYear() + 4800 + tl)) >> 2)
		+ (long)(367 * ((getMonth()+1) - 2 - tl * 12) / 12)
		- (3 * ((getYear() + 4900 + tl) / 100) >> 2);
	}

	private void calculateNormalDate() 
	{
		int t2, t4, mo, yr;
		long tl;

		tl = julian + 68569L;
 		t2 = (int)((tl << 2) / 146097L);
		tl = tl - ((146097L * (long)t2 + 3L) >> 2);
		yr = (int)(4000L * (tl + 1L) / 1461001L);
		t4 = (int)(tl - (1461L * (long)yr >> 2) + 31);
		mo = 80 * t4 / 2447;
		setDate((int)(t4 - 2447 * mo / 80));
		t4 = mo / 11;
		setMonth(((int)(mo + 2 - 12 * t4))-1);
		setYear(100 * (t2 - 49) + yr + t4);
	}

}

