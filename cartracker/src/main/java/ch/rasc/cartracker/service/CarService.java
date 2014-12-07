package ch.rasc.cartracker.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.cartracker.Views;
import ch.rasc.cartracker.entity.Car;
import ch.rasc.cartracker.entity.CarFeature;
import ch.rasc.cartracker.entity.CarImage;
import ch.rasc.cartracker.entity.CarStaff;
import ch.rasc.cartracker.entity.QCar;
import ch.rasc.cartracker.entity.QCarFeature;
import ch.rasc.cartracker.entity.QCarStaff;
import ch.rasc.cartracker.entity.QStaff;
import ch.rasc.cartracker.entity.Staff;
import ch.rasc.cartracker.entity.option.Category;
import ch.rasc.cartracker.entity.option.Color;
import ch.rasc.cartracker.entity.option.DriveTrain;
import ch.rasc.cartracker.entity.option.Feature;
import ch.rasc.cartracker.entity.option.Make;
import ch.rasc.cartracker.entity.option.Model;
import ch.rasc.cartracker.entity.option.Status;
import ch.rasc.edsutil.BaseCRUDService;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.PathBuilder;

@Service
public class CarService extends BaseCRUDService<Car> {

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<?> readCars(@RequestParam(required = false) Number id,
			ExtDirectStoreReadRequest request) {

		if (id != null) {
			Car c = entityManager.find(Car.class, id.longValue());
			c.populateDetail();
			return new ExtDirectStoreResult<>(1L, Collections.singletonList(c),
					Views.Detail.class);
		}

		JPQLQuery query = new JPAQuery(entityManager).from(QCar.car);
		query.leftJoin(QCar.car.category).fetch();
		query.leftJoin(QCar.car.color).fetch();
		query.leftJoin(QCar.car.make).fetch();
		query.leftJoin(QCar.car.model).fetch();
		query.leftJoin(QCar.car.status).fetch();
		query.leftJoin(QCar.car.driveTrain).fetch();

		if (!request.getFilters().isEmpty()) {

			List<Long> filter = extractNumbers(request.getFirstFilterForField("makeId"));
			if (!filter.isEmpty()) {
				query.where(QCar.car.make.id.in(filter));
			}

			filter = extractNumbers(request.getFirstFilterForField("modelId"));
			if (!filter.isEmpty()) {
				query.where(QCar.car.model.id.in(filter));
			}

			filter = extractNumbers(request.getFirstFilterForField("statusId"));
			if (!filter.isEmpty()) {
				query.where(QCar.car.status.id.in(filter));
			}

			filter = extractNumbers(request.getFirstFilterForField("categoryId"));
			if (!filter.isEmpty()) {
				query.where(QCar.car.category.id.in(filter));
			}

			filter = extractNumbers(request.getFirstFilterForField("colorId"));
			if (!filter.isEmpty()) {
				query.where(QCar.car.color.id.in(filter));
			}

			filter = extractNumbers(request.getFirstFilterForField("featuresId"));
			if (!filter.isEmpty()) {
				query.innerJoin(QCar.car.carFeatures, QCarFeature.carFeature);
				query.where(QCarFeature.carFeature.id.in(filter));
			}

			boolean carStaffInnerJoin = false;
			filter = extractNumbers(request.getFirstFilterForField("salesPeopleId"));
			if (!filter.isEmpty()) {
				carStaffInnerJoin = true;
				query.innerJoin(QCar.car.carStaffs, QCarStaff.carStaff);
				query.where(QCarStaff.carStaff.staff.id.in(filter));
			}

			filter = extractNumbers(request.getFirstFilterForField("positionId"));
			if (!filter.isEmpty()) {
				if (!carStaffInnerJoin) {
					query.innerJoin(QCar.car.carStaffs, QCarStaff.carStaff);
				}
				query.innerJoin(QCarStaff.carStaff.staff, QStaff.staff);
				query.where(QStaff.staff.position.id.in(filter));
			}

			filter = extractNumbers(request.getFirstFilterForField("listPrice"));
			if (!filter.isEmpty()) {
				query.where(QCar.car.listPrice.between(filter.get(0), filter.get(1)));
			}

			filter = extractNumbers(request.getFirstFilterForField("salePrice"));
			if (!filter.isEmpty()) {
				query.where(QCar.car.salePrice.between(filter.get(0), filter.get(1)));
			}

			Date acquisitionStartDate = extractDate(request
					.getFirstFilterForField("acquisitionStartDate"));
			if (acquisitionStartDate != null) {
				query.where(QCar.car.acquisitionDate.goe(acquisitionStartDate));
			}

			Date acquisitionEndDate = extractDate(request
					.getFirstFilterForField("acquisitionEndDate"));
			if (acquisitionEndDate != null) {
				query.where(QCar.car.acquisitionDate.loe(acquisitionEndDate));
			}

			Date saleStartDateFilter = extractDate(request
					.getFirstFilterForField("saleStartDate"));
			if (saleStartDateFilter != null) {
				query.where(QCar.car.saleDate.goe(saleStartDateFilter));
			}

			Date saleEndDateFilter = extractDate(request
					.getFirstFilterForField("saleEndDate"));
			if (saleEndDateFilter != null) {
				query.where(QCar.car.saleDate.loe(saleEndDateFilter));
			}

		}

		addPagingAndSorting(request, query, new PathBuilder<>(Car.class, "car"));

		SearchResults<Car> result = query.listResults(QCar.car);
		ExtDirectStoreResult<Car> cars = new ExtDirectStoreResult<>(result.getTotal(),
				result.getResults());
		cars.setJsonView(Views.Summary.class);
		return cars;
	}

	private static Date extractDate(Filter filter) {
		if (filter != null) {
			String value = ((StringFilter) filter).getValue();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			try {
				return format.parse(value);
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static ImmutableList<Long> extractNumbers(Filter filter) {
		ImmutableList.Builder<Long> numbers = ImmutableList.builder();
		if (filter != null) {
			String value = ((StringFilter) filter).getValue();
			if (StringUtils.hasText(value)) {
				value = value.substring(1, value.length() - 1);
				for (String token : value.split(",")) {
					numbers.add(Long.valueOf(token.trim()));
				}
			}
		}
		return numbers.build();
	}

	@Override
	protected void preModify(final Car entity) {
		if (entity.getMakeId() != null) {
			entity.setMake(entityManager.getReference(Make.class, entity.getMakeId()));
		}
		else {
			entity.setMake(null);
		}

		if (entity.getModelId() != null) {
			entity.setModel(entityManager.getReference(Model.class, entity.getModelId()));
		}
		else {
			entity.setModel(null);
		}

		if (entity.getCategoryId() != null) {
			entity.setCategory(entityManager.getReference(Category.class,
					entity.getCategoryId()));
		}
		else {
			entity.setCategory(null);
		}

		if (entity.getStatusId() != null) {
			entity.setStatus(entityManager.getReference(Status.class,
					entity.getStatusId()));
		}
		else {
			entity.setStatus(null);
		}

		if (entity.getColorId() != null) {
			entity.setColor(entityManager.getReference(Color.class, entity.getColorId()));
		}
		else {
			entity.setColor(null);
		}

		if (entity.getDriveTrainId() != null) {
			entity.setDriveTrain(entityManager.getReference(DriveTrain.class,
					entity.getDriveTrainId()));
		}
		else {
			entity.setDriveTrain(null);
		}

		if (entity.getCarImageIds() != null) {
			for (Long id : entity.getCarImageIds()) {
				CarImage carImage = entityManager.getReference(CarImage.class, id);
				carImage.setCar(entity);
				entity.getCarImages().add(carImage);
			}
		}

		Car dbCar = null;
		if (entity.getId() != null) {
			dbCar = entityManager.find(Car.class, entity.getId());
		}

		if (entity.getFeatureIds() != null) {
			Set<Long> dbFeatureIds = new HashSet<>();
			if (dbCar != null) {
				entity.setCarFeatures(dbCar.getCarFeatures());
			}

			for (CarFeature carFeature : entity.getCarFeatures()) {
				dbFeatureIds.add(carFeature.getFeature().getId());
			}

			Set<Long> featureIds = new HashSet<>();
			for (Long featureId : entity.getFeatureIds()) {
				if (featureId != null) {
					featureIds.add(featureId);
				}
			}

			SetView<Long> removeFeatureIds = Sets.difference(dbFeatureIds, featureIds);
			SetView<Long> newFeatureIds = Sets.difference(featureIds, dbFeatureIds);

			for (Long newFeatureId : newFeatureIds) {
				Feature feature = entityManager.getReference(Feature.class, newFeatureId);
				CarFeature cf = new CarFeature();
				cf.setCar(entity);
				cf.setFeature(feature);
				entity.getCarFeatures().add(cf);
			}

			for (Long removeFeatureId : removeFeatureIds) {
				Feature feature = entityManager.getReference(Feature.class,
						removeFeatureId);
				entity.getCarFeatures().remove(feature);
			}
		}

		if (entity.getSalesPeopleIds() != null) {
			Set<Long> dbStaffIds = new HashSet<>();

			if (dbCar != null) {
				entity.setCarStaffs(dbCar.getCarStaffs());
			}

			for (CarStaff carStaff : entity.getCarStaffs()) {
				dbStaffIds.add(carStaff.getStaff().getId());
			}

			Set<Long> staffIds = new HashSet<>();
			for (Long staffId : entity.getSalesPeopleIds()) {
				if (staffId != null) {
					staffIds.add(staffId);
				}
			}

			SetView<Long> removeStaffIds = Sets.difference(dbStaffIds, staffIds);
			SetView<Long> newStaffIds = Sets.difference(staffIds, dbStaffIds);

			for (Long newStaffId : newStaffIds) {
				Staff staff = entityManager.getReference(Staff.class, newStaffId);
				CarStaff cs = new CarStaff();
				cs.setCar(entity);
				cs.setStaff(staff);
				entity.getCarStaffs().add(cs);
			}

			for (Long removeStaffId : removeStaffIds) {
				Staff staff = entityManager.getReference(Staff.class, removeStaffId);
				entity.getCarStaffs().remove(staff);
			}
		}

	}

}
