package ch.rasc.phoenix.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Model("Phoenix.model.ScenarioItem")
public class ScenarioItem extends AbstractPersistable {

	@ManyToOne
	@JoinColumn(name = "affectedItemId")
	@JsonIgnore
	private AffectedItem affectedItem;

	@Transient
	@ModelField(useNull = true, convert = "null")
	private Long affectedItemId;

	private String itemDescription;

	private BigDecimal timeToRecover;

	private BigDecimal cost;

	@ManyToOne
	@JoinColumn(name = "revenueImpactId")
	@JsonIgnore
	private RevenueImpact revenueImpact;

	@Transient
	@ModelField(useNull = true, convert = "null")
	private Long revenueImpactId;

	@ManyToOne
	@JoinColumn(name = "scenarioId")
	@JsonIgnore
	private Scenario scenario;

	@Transient
	@ModelField(useNull = true, convert = "null")
	private Long scenarioId;

	public AffectedItem getAffectedItem() {
		return affectedItem;
	}

	public void setAffectedItem(AffectedItem affectedItem) {
		this.affectedItem = affectedItem;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public BigDecimal getTimeToRecover() {
		return timeToRecover;
	}

	public void setTimeToRecover(BigDecimal timeToRecover) {
		this.timeToRecover = timeToRecover;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public RevenueImpact getRevenueImpact() {
		return revenueImpact;
	}

	public void setRevenueImpact(RevenueImpact revenueImpact) {
		this.revenueImpact = revenueImpact;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public Long getAffectedItemId() {
		return affectedItemId;
	}

	public void setAffectedItemId(Long affectedItemId) {
		this.affectedItemId = affectedItemId;
	}

	public Long getRevenueImpactId() {
		return revenueImpactId;
	}

	public void setRevenueImpactId(Long revenueImpactId) {
		this.revenueImpactId = revenueImpactId;
	}

	public Long getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(Long scenarioId) {
		this.scenarioId = scenarioId;
	}

	@PostLoad
	@PostUpdate
	@PostPersist
	private void populate() {
		if (scenario != null) {
			scenarioId = scenario.getId();
		}
		else {
			scenarioId = null;
		}

		if (revenueImpact != null) {
			revenueImpactId = revenueImpact.getId();
		}
		else {
			revenueImpactId = null;
		}

		if (affectedItem != null) {
			affectedItemId = affectedItem.getId();
		}
		else {
			affectedItemId = null;
		}
	}
}
