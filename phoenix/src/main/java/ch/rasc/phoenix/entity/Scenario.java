package ch.rasc.phoenix.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelAssociation;
import ch.rasc.extclassgenerator.ModelAssociationType;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Model(value = "Phoenix.model.Scenario", createMethod = "scenarioService.create",
		readMethod = "scenarioService.read", updateMethod = "scenarioService.update",
		destroyMethod = "scenarioService.destroy")
public class Scenario extends AbstractPersistable {

	private String name;

	private String description;

	@ModelField(dateFormat = "c")
	private Date dateUpdated;

	@ManyToOne
	@JoinColumn(name = "probabilityId")
	@JsonIgnore
	private Probability probability;

	@Transient
	@ModelField(useNull = true, convert = "null")
	private Long probabilityId;

	private BigDecimal planCost;

	private BigDecimal impactCost;

	private BigDecimal impactLength;

	private BigDecimal totalImpact;

	private String planEffectiveness;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "scenario", orphanRemoval = true)
	@ModelAssociation(value = ModelAssociationType.HAS_MANY, model = ScenarioItem.class,
			foreignKey = "scenarioId")
	private Set<ScenarioItem> scenarioItems = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public Probability getProbability() {
		return probability;
	}

	public void setProbability(Probability probability) {
		this.probability = probability;
	}

	public BigDecimal getPlanCost() {
		return planCost;
	}

	public void setPlanCost(BigDecimal planCost) {
		this.planCost = planCost;
	}

	public BigDecimal getImpactCost() {
		return impactCost;
	}

	public void setImpactCost(BigDecimal impactCost) {
		this.impactCost = impactCost;
	}

	public BigDecimal getImpactLength() {
		return impactLength;
	}

	public void setImpactLength(BigDecimal impactLength) {
		this.impactLength = impactLength;
	}

	public BigDecimal getTotalImpact() {
		return totalImpact;
	}

	public void setTotalImpact(BigDecimal totalImpact) {
		this.totalImpact = totalImpact;
	}

	public String getPlanEffectiveness() {
		return planEffectiveness;
	}

	public void setPlanEffectiveness(String planEffectiveness) {
		this.planEffectiveness = planEffectiveness;
	}

	public Long getProbabilityId() {
		return probabilityId;
	}

	public void setProbabilityId(Long probabilityId) {
		this.probabilityId = probabilityId;
	}

	public Set<ScenarioItem> getScenarioItems() {
		return scenarioItems;
	}

	public void setScenarioItems(Set<ScenarioItem> scenarioItems) {
		this.scenarioItems = scenarioItems;
	}

	@PostLoad
	private void populate() {
		if (probability != null) {
			probabilityId = probability.getId();
		}
		else {
			probabilityId = null;
		}
	}
}
