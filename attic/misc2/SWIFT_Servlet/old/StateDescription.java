public class StateDescription {
    private String code;
    private int colour;
    private String description;

    public StateDescription() {
	    code = null;
		colour = 0;
		description = null;
    }

    public StateDescription(String code, int colour, String description) {
        this.code   = code;
        this.colour = colour;
        this.description = description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return(code);
    }

    public int getColour() {
        return(colour);
    }

    public String getDescription() {
        return(description);
    }

	public String toString()
	{
		return "#StateDescription("+code+" "+colour+" "+description+")";
	}
}