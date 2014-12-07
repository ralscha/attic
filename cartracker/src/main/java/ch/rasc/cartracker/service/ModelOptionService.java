package ch.rasc.cartracker.service;

import org.springframework.stereotype.Service;

import ch.rasc.cartracker.entity.option.Make;
import ch.rasc.cartracker.entity.option.Model;
import ch.rasc.edsutil.BaseCRUDService;

@Service
public class ModelOptionService extends BaseCRUDService<Model> {

	@Override
	protected void preModify(Model entity) {
		if (entity.getMakeId() != null) {
			entity.setMake(entityManager.getReference(Make.class, entity.getMakeId()));
		}
		else {
			entity.setMake(null);
		}
	}
}
