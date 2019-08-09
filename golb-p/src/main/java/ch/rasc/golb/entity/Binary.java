package ch.rasc.golb.entity;

import java.util.Date;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.rasc.bsoncodec.annotation.BsonDocument;
import ch.rasc.bsoncodec.annotation.Id;
import ch.rasc.bsoncodec.annotation.Transient;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@BsonDocument
@Model(value = "Golb.model.Binary", readMethod = "binaryService.read",
		createMethod = "binaryService.update", updateMethod = "binaryService.update",
		destroyMethod = "binaryService.destroy", rootProperty = "records",
		identifier = "uuid")
@JsonInclude(Include.NON_NULL)
public class Binary {

	@ModelField(useNull = true, convert = "null")
	@Id(generator = UUIDStringGenerator.class)
	private String id;

	private String name;

	private String type;

	private long size;

	@Transient
	private String data;

	@JsonIgnore
	private ObjectId fileId;

	@ModelField(dateFormat = "time")
	private Date lastModifiedDate;

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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return this.size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ObjectId getFileId() {
		return this.fileId;
	}

	public void setFileId(ObjectId fileId) {
		this.fileId = fileId;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
