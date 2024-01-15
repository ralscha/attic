package ch.ess.calendar.db;

public class Categories implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private int categoryid;
	private String description;
	private String color;

  //if true the application should only show the name of category for other users
  //the owner of the appointment sees all information
  private boolean limited;

	public Categories() {
		this.categoryid = 0;
		this.description = null;
		this.color = null;
    this.limited = false;
	}

	public Categories(int categoryid, String description, String color) {
		this.categoryid = categoryid;
    setDescription(description);
		this.color = color;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;

		//Hack. Limited should be an property in database
		if ( (description != null) && 
		     (description.toLowerCase().startsWith("privat")) ) {
		  limited = true;
		}

	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

  public boolean isLimited() {
    return limited;
  }

  public void setLimited(boolean limited) {
    this.limited = limited;
  }

	public String toString() {
		return "Categories("+ categoryid + " " + description + " " + color+")";
	}
}
