package ch.rasc.dashboard;

public class Player implements Comparable<Player> {
	private long id;
	private String nachname;
	private String vorname;
	private String lizenzNr;
	private Integer jahrgang;
	private int position;
	private Integer[] ries;
	private boolean[] nr;

	public Player(long id, int position, String nachname, String vorname, String lizenzNr,
			Integer jahrgang) {
		this.id = id;
		this.position = position;
		this.nachname = nachname;
		this.vorname = vorname;
		this.lizenzNr = lizenzNr;
		this.jahrgang = jahrgang;
		this.ries = new Integer[4];
		this.nr = new boolean[4];
	}

	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNachname() {
		return this.nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getLizenzNr() {
		return this.lizenzNr;
	}

	public void setLizenzNr(String lizenzNr) {
		this.lizenzNr = lizenzNr;
	}

	public Integer getJahrgang() {
		return this.jahrgang;
	}

	public void setJahrgang(Integer jahrgang) {
		this.jahrgang = jahrgang;
	}

	public Integer[] getRies() {
		return this.ries;
	}

	public void setRies(Integer[] ries) {
		this.ries = ries;
	}

	public boolean[] getNr() {
		return this.nr;
	}

	public void setNr(boolean[] nr) {
		this.nr = nr;
	}

	public void clear() {
		this.ries = new Integer[4];
		this.nr = new boolean[4];
	}
	
	@Override
	public int compareTo(Player o) {
		return this.position - o.position;
	}

}
