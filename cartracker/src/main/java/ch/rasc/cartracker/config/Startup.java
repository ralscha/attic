package ch.rasc.cartracker.config;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.cartracker.entity.Car;
import ch.rasc.cartracker.entity.CarFeature;
import ch.rasc.cartracker.entity.CarStaff;
import ch.rasc.cartracker.entity.QCar;
import ch.rasc.cartracker.entity.QStaff;
import ch.rasc.cartracker.entity.Staff;
import ch.rasc.cartracker.entity.Workflow;
import ch.rasc.cartracker.entity.option.Category;
import ch.rasc.cartracker.entity.option.Color;
import ch.rasc.cartracker.entity.option.DriveTrain;
import ch.rasc.cartracker.entity.option.Feature;
import ch.rasc.cartracker.entity.option.Make;
import ch.rasc.cartracker.entity.option.Model;
import ch.rasc.cartracker.entity.option.QCategory;
import ch.rasc.cartracker.entity.option.QColor;
import ch.rasc.cartracker.entity.option.QDriveTrain;
import ch.rasc.cartracker.entity.option.QFeature;
import ch.rasc.cartracker.entity.option.QMake;
import ch.rasc.cartracker.entity.option.QModel;
import ch.rasc.cartracker.entity.option.QStatus;
import ch.rasc.cartracker.entity.option.Status;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

	private final static String loremIpsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
			+ "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat."
			+ "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi."
			+ "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat."
			+ "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis."
			+ "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur";

	private final static int loremIpsumLength = loremIpsum.length();

	@Autowired
	private Environment environment;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (new JPAQuery(entityManager).from(QCar.car).notExists()) {

			DataFactory df = new DataFactory();

			Calendar firstOfThisMonth = Calendar.getInstance();
			firstOfThisMonth.set(Calendar.DAY_OF_MONTH, 1);

			Calendar start = Calendar.getInstance();
			start.add(Calendar.MONTH, -11);
			start = new GregorianCalendar(start.get(Calendar.YEAR),
					start.get(Calendar.MONTH), 1);
			int startYear = start.get(Calendar.YEAR);

			int maxColorId = new JPAQuery(entityManager).from(QColor.color)
					.uniqueResult(QColor.color.id.max()).intValue();
			int maxCategoryId = new JPAQuery(entityManager).from(QCategory.category)
					.uniqueResult(QCategory.category.id.max()).intValue();
			int maxDriveTrainId = new JPAQuery(entityManager)
					.from(QDriveTrain.driveTrain)
					.uniqueResult(QDriveTrain.driveTrain.id.max()).intValue();

			int maxStaffId = new JPAQuery(entityManager).from(QStaff.staff)
					.uniqueResult(QStaff.staff.id.max()).intValue();
			List<Long> staffIds = new JPAQuery(entityManager).from(QStaff.staff).list(
					QStaff.staff.id);

			int maxFeatureId = new JPAQuery(entityManager).from(QFeature.feature)
					.uniqueResult(QFeature.feature.id.max()).intValue();
			List<Long> featureIds = new JPAQuery(entityManager).from(QFeature.feature)
					.list(QFeature.feature.id);

			int maxStatusId = new JPAQuery(entityManager).from(QStatus.status)
					.uniqueResult(QStatus.status.id.max()).intValue();

			HashMultimap<Long, Long> makeModelId = HashMultimap.create();
			for (Long makeId : new JPAQuery(entityManager).from(QMake.make).list(
					QMake.make.id)) {
				makeModelId.putAll(makeId, new JPAQuery(entityManager).from(QModel.model)
						.where(QModel.model.make.id.eq(makeId)).list(QModel.model.id));
			}
			List<Long> makeIds = ImmutableList.copyOf(makeModelId.keySet());

			for (int i = 0; i < 20; i++) {
				Long makeId = df.getItem(makeIds);
				Make make = entityManager.getReference(Make.class, makeId);

				for (int n = 0; n < 10; n++) {
					Model model = entityManager.getReference(Model.class,
							df.getItem(makeModelId.get(makeId).toArray()));

					Car car = new Car();
					car.setCreateDate(new Date());
					car.setActive(true);

					Date acquisitionDate = df.getDate(start.getTime(), -180, 180);
					Date saleDate = df.getDate(acquisitionDate, 4, 180);

					car.setListPrice(df.getNumberBetween(2000, 100000));

					car.setAcquisitionDate(acquisitionDate);
					if (saleDate.before(firstOfThisMonth.getTime())) {
						car.setSaleDate(saleDate);
						car.setSold(Boolean.TRUE);
						car.setSalePrice(car.getListPrice()
								+ df.getNumberBetween(-1000, 50000));
					}
					else {
						car.setSaleDate(null);
						car.setSold(Boolean.FALSE);
						car.setSalePrice(null);
					}

					car.setFuel(df.getItem(new String[] { "Regular", "Plus 88",
							"Plus 89", "Plus 90", "Premium" }));
					car.setStockNumber("N" + (1000 + i));
					car.setTransmission(df.getItem(new String[] { "Automatic", "Manual",
							"Manumatic", "CVT", "Dual-Clutch" }));
					car.setYear(df.getNumberBetween(startYear - 20, startYear));
					car.setVin(df.getRandomChars(3) + df.getNumberText(14));

					car.setMileage(df.getNumberBetween(2000, 200000));

					for (int j = 0; j < df.getNumberBetween(1, maxFeatureId + 1); j++) {
						Feature f = entityManager.getReference(Feature.class,
								df.getItem(featureIds));
						CarFeature cf = new CarFeature();
						cf.setFeature(f);
						cf.setCar(car);
						car.getCarFeatures().add(cf);
					}

					for (int j = 0; j < df.getNumberBetween(1, maxStaffId + 1); j++) {
						Staff s = entityManager.getReference(Staff.class,
								df.getItem(staffIds));
						CarStaff cs = new CarStaff();
						cs.setStaff(s);
						cs.setCar(car);
						car.getCarStaffs().add(cs);
					}

					car.setCategory(entityManager.getReference(Category.class,
							Long.valueOf(df.getNumberBetween(1, maxCategoryId + 1))));
					car.setColor(entityManager.getReference(Color.class,
							Long.valueOf(df.getNumberBetween(1, maxColorId + 1))));
					car.setDescription(loremIpsum.substring(0,
							df.getNumberBetween(300, loremIpsumLength)));
					car.setDriveTrain(entityManager.getReference(DriveTrain.class,
							Long.valueOf(df.getNumberBetween(1, maxDriveTrainId + 1))));

					car.setEngine(df.getItem(new String[] { "I3", "I4", "I5", "I6(S6)",
							"I8(S8)", "H4", "H6", "V6", "V8", "V10", "V12", "V16", "W12",
							"W16" }));

					car.setMake(make);
					car.setModel(model);
					Status status = entityManager.find(Status.class,
							Long.valueOf(df.getNumberBetween(1, maxStatusId + 1)));
					car.setStatus(status);

					entityManager.persist(car);

					createWorkflow(df, staffIds, car, null, lookupStatus("Initiated"));

					if (status.getLongName().equals("In-Audit")) {
						createWorkflow(df, staffIds, car, lookupStatus("Initiated"),
								status);
					}
					else if (status.getLongName().equals("In-Review")) {
						createWorkflow(df, staffIds, car, lookupStatus("Initiated"),
								lookupStatus("In-Audit"));
						createWorkflow(df, staffIds, car, lookupStatus("In-Audit"),
								status);
					}
					else if (status.getLongName().equals("Approved")
							|| status.getLongName().equals("Rejected")) {
						createWorkflow(df, staffIds, car, lookupStatus("Initiated"),
								lookupStatus("In-Audit"));
						createWorkflow(df, staffIds, car, lookupStatus("In-Audit"),
								lookupStatus("In-Review"));
						createWorkflow(df, staffIds, car, lookupStatus("In-Review"),
								status);
					}
				}
			}

		}

	}

	private void createWorkflow(DataFactory df, List<Long> staffIds, Car car,
			Status last, Status next) {
		Workflow workflow;
		workflow = new Workflow();
		workflow.setActive(true);
		workflow.setCreateDate(new Date());
		workflow.setApproved(true);
		workflow.setCar(car);
		workflow.setStaff(entityManager.getReference(Staff.class, df.getItem(staffIds)));
		workflow.setLastStatus(last);
		workflow.setNextStatus(next);
		workflow.setNotes(loremIpsum.substring(0, df.getNumberBetween(10, 100)));
		entityManager.persist(workflow);
	}

	private Status lookupStatus(String statusName) {
		return new JPAQuery(entityManager).from(QStatus.status)
				.where(QStatus.status.shortName.eq(statusName))
				.uniqueResult(QStatus.status);
	}

}
