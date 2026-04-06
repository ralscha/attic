package ch.rasc.mongodb.geolite;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

@Entity(noClassnameStored = true)
// @Indexes({ @Index(fields= {@Field("startIpNum"), @Field("endIpNum")}) })
public class Geolite {

	@Id
	private ObjectId id;

	@Indexed(unique = true, value = IndexDirection.DESC)
	private long startIpNum;

	private long endIpNum;

	private String country;

	private String region;

	private String city;

	private String postalCode;

	private double latitude;

	private double longitude;

	private String metroCode;

	private String areaCode;

	public long getStartIpNum() {
		return this.startIpNum;
	}

	public void setStartIpNum(long startIpNum) {
		this.startIpNum = startIpNum;
	}

	public long getEndIpNum() {
		return this.endIpNum;
	}

	public void setEndIpNum(long endIpNum) {
		this.endIpNum = endIpNum;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getMetroCode() {
		return this.metroCode;
	}

	public void setMetroCode(String metroCode) {
		this.metroCode = metroCode;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public ObjectId getId() {
		return this.id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Geolite [id=" + this.id + ", startIpNum=" + this.startIpNum
				+ ", endIpNum=" + this.endIpNum + ", country=" + this.country
				+ ", region=" + this.region + ", city=" + this.city + ", postalCode="
				+ this.postalCode + ", latitude=" + this.latitude + ", longitude="
				+ this.longitude + ", metroCode=" + this.metroCode + ", areaCode="
				+ this.areaCode + "]";
	}

}
