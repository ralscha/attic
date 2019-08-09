package ch.rasc.golb.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import ch.rasc.bsoncodec.annotation.BsonDocument;
import ch.rasc.bsoncodec.annotation.Id;
import ch.rasc.bsoncodec.annotation.Transient;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.golb.dto.View;

@BsonDocument
@Model(value = "Golb.model.Tag", readMethod = "tagService.read",
		createMethod = "tagService.update", updateMethod = "tagService.update",
		destroyMethod = "tagService.destroy", rootProperty = "records",
		identifier = "uuid")
@JsonInclude(Include.NON_NULL)
public class Tag {

	@ModelField(useNull = true, convert = "null")
	@Id(generator = UUIDStringGenerator.class)
	@JsonView(View.Short.class)
	private String id;

	@NotBlank(message = "{fieldrequired}")
	@JsonView(View.Short.class)
	private String name;

	@ModelField(persist = false)
	@Transient
	private long noOfPosts;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNoOfPosts() {
		return this.noOfPosts;
	}

	public void setNoOfPosts(long noOfPosts) {
		this.noOfPosts = noOfPosts;
	}

}
