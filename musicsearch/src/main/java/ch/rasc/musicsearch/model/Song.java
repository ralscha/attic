package ch.rasc.musicsearch.model;

import ch.rasc.extclassgenerator.Model;

@Model(value = "MusicSearch.model.Song", readMethod = "searchService.search",
		writeAllFields = false)
public class Song {

	private int id;

	private String title;

	private String album;

	private String artist;

	private String year;

	private Integer durationInSeconds;

	private Long bitrate;

	private String encoding;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return this.album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtist() {
		return this.artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getDurationInSeconds() {
		return this.durationInSeconds;
	}

	public void setDurationInSeconds(Integer durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	public Long getBitrate() {
		return this.bitrate;
	}

	public void setBitrate(Long bitrate) {
		this.bitrate = bitrate;
	}

	public String getEncoding() {
		return this.encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
