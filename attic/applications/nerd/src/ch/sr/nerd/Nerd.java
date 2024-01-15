


package ch.sr.nerd;
public class Nerd {
	private String name = null;
	private int points = 0;
	
	public void decPoints() {
		points--;
	}

	public void incPoints() {
		points++;
	}

	public boolean equals(Object obj) {
		return name.equals(obj);
	}
	
	public String getName() {
		return name;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int hashCode() {
		return name.hashCode();
	}
	
	public void setName(String newValue) {
		this.name = newValue;
	}
	
	public void setPoints(int newValue) {
		this.points = newValue;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.setLength(0);
		sb.append(name).append(';').append(points);
		return sb.toString();
	}
}