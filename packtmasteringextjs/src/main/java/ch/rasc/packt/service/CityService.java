package ch.rasc.packt.service;

import org.springframework.stereotype.Service;

import ch.rasc.packt.entity.City;
import ch.rasc.packt.entity.Country;

@Service
public class CityService extends BaseCRUDService<City> {

	@Override
	protected void preModify(City entity) {
		if (entity.getCountryId() != null) {
			entity.setCountry(entityManager.getReference(Country.class,
					entity.getCountryId()));
		}
		else {
			entity.setCountry(null);
		}
	}
}
