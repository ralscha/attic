package ch.rasc.musicsearch.model;

public class Info {

	private final Integer noOfSongs;

	private final Integer totalDuration;

	public Info(Integer noOfSongs, Integer totalDuration) {
		this.noOfSongs = noOfSongs;
		this.totalDuration = totalDuration;
	}

	public Integer getNoOfSongs() {
		return this.noOfSongs;
	}

	public Integer getTotalDuration() {
		return this.totalDuration;
	}

}
