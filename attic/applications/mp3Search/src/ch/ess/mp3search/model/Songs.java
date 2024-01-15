package ch.ess.mp3search.model;

public class Songs {

  private String fileName;
  private String directory;
  private String album;
  private String artist;
  private String title;
  private String year;
  private String genre;
  private Integer duration;

  public String getDirectory() {
    return directory;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String name) {
    this.fileName = name;
  }

  
  public Integer getDuration() {
    return duration;
  }

  
  public void setDuration(Integer duration) {
    this.duration = duration;
  }

}
