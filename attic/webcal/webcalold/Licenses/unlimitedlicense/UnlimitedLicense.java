
public class UnlimitedLicense implements ch.ess.calendar.util.License {

	public String getLicense() {
		return "B&G Partner AG";
	}
	
	public int getLimit() {
		return Integer.MAX_VALUE;
	}	
}