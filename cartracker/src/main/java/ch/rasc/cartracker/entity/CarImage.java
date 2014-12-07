package ch.rasc.cartracker.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import ch.rasc.extclassgenerator.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Model(value = "CarTracker.model.CarImage", destroyMethod = "carImageService.destroy")
public class CarImage extends BaseEntity {

	@Lob
	@JsonIgnore
	private byte[] imagedata;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "carId")
	@JsonIgnore
	private Car car;

	@JsonIgnore
	private String etag;

	@JsonIgnore
	private String contentType;

	public byte[] getImagedata() {
		return imagedata;
	}

	public void setImagedata(byte[] imagedata) {
		this.imagedata = imagedata;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
