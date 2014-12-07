package ch.rasc.phoenix.entity;

import javax.persistence.Entity;

import ch.rasc.extclassgenerator.Model;

@Entity
@Model(value = "Phoenix.model.RevenueImpact", readMethod = "revenueImpactService.read")
public class RevenueImpact extends BaseOption {
	// nothing here
}
