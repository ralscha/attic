package ch.rasc.golb.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.rasc.bsoncodec.annotation.BsonDocument;
import ch.rasc.bsoncodec.annotation.Id;
import ch.rasc.bsoncodec.annotation.Transient;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@BsonDocument
@Model(value = "Golb.model.Feedback", readMethod = "feedbackService.read",
		destroyMethod = "feedbackService.destroy", rootProperty = "records",
		identifier = "uuid")
@JsonInclude(Include.NON_NULL)
public class Feedback {

	@ModelField(useNull = true, convert = "null")
	@Id(generator = UUIDStringGenerator.class)
	private String id;

	private String postId;

	private String body;

	private String replyEmailAddress;

	@Transient
	private String postTitle;

	@ModelField(dateFormat = "time")
	private Date created;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPostId() {
		return this.postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getReplyEmailAddress() {
		return this.replyEmailAddress;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setReplyEmailAddress(String replyEmailAddress) {
		this.replyEmailAddress = replyEmailAddress;
	}

	public String getPostTitle() {
		return this.postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

}
