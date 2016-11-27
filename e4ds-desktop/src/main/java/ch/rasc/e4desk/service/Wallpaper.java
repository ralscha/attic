package ch.rasc.e4desk.service;

import ch.rasc.extclassgenerator.Model;

@Model(value = "E4desk.model.Wallpaper", readMethod = "wallpaperService.read")
public class Wallpaper {

	private final String text;

	private final String img;

	private final Integer width;

	private final Integer height;

	public Wallpaper(String text, String img, Integer width, Integer height) {
		this.text = text;
		this.img = img;
		this.width = width;
		this.height = height;
	}

	public String getText() {
		return this.text;
	}

	public String getImg() {
		return this.img;
	}

	public Integer getWidth() {
		return this.width;
	}

	public Integer getHeight() {
		return this.height;
	}

}
