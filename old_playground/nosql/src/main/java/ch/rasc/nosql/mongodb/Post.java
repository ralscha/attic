package ch.rasc.nosql.mongodb;

import java.util.Date;
import java.util.List;

import ch.rasc.bsoncodec.annotation.BsonDocument;
import ch.rasc.bsoncodec.annotation.Id;

@BsonDocument
public class Post {

	@Id(generator = UUIDStringGenerator.class)
	private String id;
	private long stackexchangeId;
	private long postTypeId;
	private long acceptedAnswerId;
	private Date creationDate;
	private long stackexchangeScore;
	private long viewCount;
	private String body;
	private String title;
	private Float score;
	private List<String> tags;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getStackexchangeId() {
		return this.stackexchangeId;
	}

	public void setStackexchangeId(long stackexchangeId) {
		this.stackexchangeId = stackexchangeId;
	}

	public long getPostTypeId() {
		return this.postTypeId;
	}

	public void setPostTypeId(long postTypeId) {
		this.postTypeId = postTypeId;
	}

	public long getAcceptedAnswerId() {
		return this.acceptedAnswerId;
	}

	public void setAcceptedAnswerId(long acceptedAnswerId) {
		this.acceptedAnswerId = acceptedAnswerId;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public long getStackexchangeScore() {
		return this.stackexchangeScore;
	}

	public void setStackexchangeScore(long stackexchangeScore) {
		this.stackexchangeScore = stackexchangeScore;
	}

	public long getViewCount() {
		return this.viewCount;
	}

	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Post [id=" + this.id + ", stackexchangeId=" + this.stackexchangeId
				+ ", postTypeId=" + this.postTypeId + ", acceptedAnswerId="
				+ this.acceptedAnswerId + ", creationDate=" + this.creationDate
				+ ", stackexchangeScore=" + this.stackexchangeScore + ", viewCount="
				+ this.viewCount + ", body=" + this.body + ", title=" + this.title
				+ ", score=" + this.score + ", tags=" + this.tags + "]";
	}

}
