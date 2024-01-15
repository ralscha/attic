package ch.rasc.bitprototype.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.bitprototype.service.OfferteStatus;
import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.edsutil.entity.DateTimeConverter;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelType;

@Entity
@Model(value = "BitP.model.Offerte", readMethod = "offerteService.read",
		createMethod = "offerteService.create", updateMethod = "offerteService.update",
		destroyMethod = "offerteService.destroy", paging = true)
public class Offerte extends AbstractPersistable {
	private String name;

	private String vorname;

	@ModelField(useNull = true)
	private Integer jahrgang;

	private String bemerkungen;

	@Convert(converter = DateTimeConverter.class)
	@ModelField(dateFormat = "c")
	private LocalDate zurverfuegungStellungBeginn;

	@Convert(converter = DateTimeConverter.class)
	@ModelField(dateFormat = "c")
	private LocalDate zurverfuegungStellungEnde;

	@ModelField(useNull = true)
	private Integer pensum;

	@ModelField(useNull = true)
	private BigDecimal stundensatz;

	@ModelField(type = ModelType.STRING)
	private OfferteStatus status;

	@ManyToOne
	@JoinColumn(name = "lieferantId", nullable = false, updatable = false)
	private Lieferant lieferant;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "offerte", orphanRemoval = true)
	private Set<OfferteAttachment> attachments = new HashSet<>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Integer getJahrgang() {
		return this.jahrgang;
	}

	public void setJahrgang(Integer jahrgang) {
		this.jahrgang = jahrgang;
	}

	public String getBemerkungen() {
		return this.bemerkungen;
	}

	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}

	public LocalDate getZurverfuegungStellungBeginn() {
		return this.zurverfuegungStellungBeginn;
	}

	public void setZurverfuegungStellungBeginn(LocalDate zurverfuegungStellungBeginn) {
		this.zurverfuegungStellungBeginn = zurverfuegungStellungBeginn;
	}

	public LocalDate getZurverfuegungStellungEnde() {
		return this.zurverfuegungStellungEnde;
	}

	public void setZurverfuegungStellungEnde(LocalDate zurverfuegungStellungEnde) {
		this.zurverfuegungStellungEnde = zurverfuegungStellungEnde;
	}

	public Integer getPensum() {
		return this.pensum;
	}

	public void setPensum(Integer pensum) {
		this.pensum = pensum;
	}

	public BigDecimal getStundensatz() {
		return this.stundensatz;
	}

	public void setStundensatz(BigDecimal stundensatz) {
		this.stundensatz = stundensatz;
	}

	public OfferteStatus getStatus() {
		return this.status;
	}

	public void setStatus(OfferteStatus status) {
		this.status = status;
	}

	public Lieferant getLieferant() {
		return this.lieferant;
	}

	public void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}

	public Set<OfferteAttachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<OfferteAttachment> attachments) {
		this.attachments = attachments;
	}

}
