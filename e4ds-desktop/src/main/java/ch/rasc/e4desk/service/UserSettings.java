package ch.rasc.e4desk.service;

import java.io.Serializable;

public class UserSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	private String wallpaper;

	private String picturePos;

	private String backgroundColor;

	private Integer imageWidth;

	private Integer imageHeight;

	public UserSettings() {
		// default constructor
	}

	public UserSettings(String wallpaper, Integer width, Integer height,
			String picturePos, String backgroundColor) {
		this.wallpaper = wallpaper;
		this.imageWidth = width;
		this.imageHeight = height;
		this.picturePos = picturePos;
		this.backgroundColor = backgroundColor;
	}

	public String getWallpaper() {
		return this.wallpaper;
	}

	public String getPicturePos() {
		return this.picturePos;
	}

	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	public Integer getImageWidth() {
		return this.imageWidth;
	}

	public Integer getImageHeight() {
		return this.imageHeight;
	}

	public void setWallpaper(String wallpaper) {
		this.wallpaper = wallpaper;
	}

	public void setPicturePos(String picturePos) {
		this.picturePos = picturePos;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	@Override
	public String toString() {
		return "UserSettings [wallpaper=" + this.wallpaper + ", picturePos="
				+ this.picturePos + ", backgroundColor=" + this.backgroundColor
				+ ", imageWidth=" + this.imageWidth + ", imageHeight=" + this.imageHeight
				+ "]";
	}

}
