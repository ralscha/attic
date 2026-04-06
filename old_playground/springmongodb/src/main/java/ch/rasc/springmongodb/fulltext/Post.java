package ch.rasc.springmongodb.fulltext;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

@Document
@TypeAlias("p")
public class Post {

	@Id
	private String id;

	@Indexed
	private long stackexchangeId;
	private long postTypeId;
	private long acceptedAnswerId;
	private LocalDateTime creationDate;
	private long stackexchangeScore;
	private long viewCount;

	@TextIndexed(weight = 1)
	private String body;

	@TextIndexed(weight = 2)
	private String title;

	@TextScore
	private Float score;

	public Float getScore() {
		return this.score;
	}

	private List<String> tags;

	public String getId() {
		return this.id;
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

	public LocalDateTime getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
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
				+ ", tags=" + this.tags + "]";
	}

}
