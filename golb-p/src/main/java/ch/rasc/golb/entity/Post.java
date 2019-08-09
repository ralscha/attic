package ch.rasc.golb.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ch.rasc.bsoncodec.annotation.BsonDocument;
import ch.rasc.bsoncodec.annotation.Id;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@BsonDocument
@Model(value = "Golb.model.Post", readMethod = "postService.read",
		createMethod = "postService.update", updateMethod = "postService.update",
		destroyMethod = "postService.destroy", rootProperty = "records",
		identifier = "uuid")
@JsonInclude(Include.NON_NULL)
public class Post {

	@ModelField(useNull = true, convert = "null")
	@Id(generator = UUIDStringGenerator.class)
	private String id;

	@NotBlank(message = "{fieldrequired}")
	private String title;

	@NotBlank(message = "{fieldrequired}")
	private String slug;

	private String body;

	private String summary;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String bodyHtml;

	@ModelField
	private List<String> tags;

	@ModelField(dateFormat = "time", persist = false)
	private Date created;

	@ModelField(dateFormat = "time", persist = false)
	private Date updated;

	@ModelField(dateFormat = "time", persist = false)
	private Date published;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlug() {
		return this.slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBodyHtml() {
		return this.bodyHtml;
	}

	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}

	public List<String> getTags() {
		return this.tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getPublished() {
		return this.published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
