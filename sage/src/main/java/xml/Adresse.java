package xml;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;

@ObjectCreate(pattern = "feed/entry/sdata:payload/Adresse")
public class Adresse {

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/Anrede")
	public String anrede;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/Briefanrede")
	public String briefanrede;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/Code")
	public String code;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/DruckInfo")
	public String druckInfo;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/EMail")
	public String email;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/Firma")
	public String firma;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/ID")
	public String id;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/IstIntern")
	public String istIntern;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/Land")
	public String land;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/MahnSperrCode")
	public String mahnSperrCode;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/PLZ")
	public String plz;

	@BeanPropertySetter(pattern = "feed/entry/sdata:payload/Adresse/Ort")
	public String ort;

	public String getAnrede() {
		return this.anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public String getBriefanrede() {
		return this.briefanrede;
	}

	public void setBriefanrede(String briefanrede) {
		this.briefanrede = briefanrede;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDruckInfo() {
		return this.druckInfo;
	}

	public void setDruckInfo(String druckInfo) {
		this.druckInfo = druckInfo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirma() {
		return this.firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIstIntern() {
		return this.istIntern;
	}

	public void setIstIntern(String istIntern) {
		this.istIntern = istIntern;
	}

	public String getLand() {
		return this.land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getMahnSperrCode() {
		return this.mahnSperrCode;
	}

	public void setMahnSperrCode(String mahnSperrCode) {
		this.mahnSperrCode = mahnSperrCode;
	}

	public String getPlz() {
		return this.plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return this.ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	@Override
	public String toString() {
		return "Adresse [anrede=" + this.anrede + ", briefanrede=" + this.briefanrede
				+ ", code=" + this.code + ", druckInfo=" + this.druckInfo + ", email="
				+ this.email + ", firma=" + this.firma + ", id=" + this.id
				+ ", istIntern=" + this.istIntern + ", land=" + this.land
				+ ", mahnSperrCode=" + this.mahnSperrCode + ", plz=" + this.plz + ", ort="
				+ this.ort + "]";
	}

}
