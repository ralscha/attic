
import java.util.Date;

public class DateTest
{
	public static void main(String args[])
	{
		JulianDate d1 = new JulianDate();
		JulianDate d2 = new JulianDate(100,0,1,0,0,0);
		JulianDate d3 = new JulianDate(100,0,1,24,0,0);
		System.out.println(d2.getJulian() - d1.getJulian());
		System.out.println(d2 + "\n" + d3);

	}
}
