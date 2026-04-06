package ch.rasc.perf;

import java.util.Date;

/**
 * Like data.
 *
 * @author Andrey Bloschetsov
 */
public class Like {

	private String id;

	private Date date;

	public Like() {
	}

	public Like(String id, Date date) {
		super();
		this.id = id;
		this.date = date;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
