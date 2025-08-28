package ch.rasc.travellog.dto;

public class PhotoDto {

	private final long id;

	private final String storage;

	private final String name;

	private final long size;

	private final String mimeType;

	private final String thumbnailDataUrl;

	private final long updated;

	public PhotoDto(long id, String storage, String name, long size, String mimeType,
			String thumbnailDataUrl, long updated) {
		this.id = id;
		this.storage = storage;
		this.name = name;
		this.size = size;
		this.mimeType = mimeType;
		this.thumbnailDataUrl = thumbnailDataUrl;
		this.updated = updated;
	}

	public long getId() {
		return this.id;
	}

	public String getStorage() {
		return this.storage;
	}

	public String getName() {
		return this.name;
	}

	public long getSize() {
		return this.size;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public String getThumbnailDataUrl() {
		return this.thumbnailDataUrl;
	}

	public long getUpdated() {
		return this.updated;
	}

}
