package gtf.swift.state;

import java.sql.*;

public class StateDescription {
	private String code;
	private short colour;
	private String description;

	public StateDescription() {
		code = null;
		colour = 0;
		description = null;
	}
	public StateDescription(String code, short colour, String description) {
		this.code = code;
		this.colour = colour;
		this.description = description;
	}
	public String getCode() {
		return(code);
	}
	public short getColour() {
		return(colour);
	}
	public String getDescription() {
		return(description);
	}

	public static StateDescription makeObject(ResultSet rs) throws SQLException {
		return new StateDescription(rs.getString(1), rs.getShort(2), rs.getString(3));
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setColour(short colour) {
		this.colour = colour;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString() {
		return "#StateDescription(" + code + " " + colour + " " + description + ")";
	}
}