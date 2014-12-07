package ch.rasc.cartracker.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.rasc.cartracker.entity.QCar;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class ReportService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly = true)
	public List<Make> readMakeReport(@RequestParam(value = "detail",
			defaultValue = "true") boolean detail) {

		List<Make> result = new ArrayList<>();
		if (detail) {
			List<Tuple> queryResult = new JPAQuery(entityManager)
					.from(QCar.car)
					.where(QCar.car.saleDate.isNotNull())
					.groupBy(QCar.car.make.longName, QCar.car.model.longName)
					.list(QCar.car.make.longName, QCar.car.model.longName,
							QCar.car.salePrice.sum());
			for (Tuple tuple : queryResult) {
				Make m = new Make();
				m.setMake(tuple.get(QCar.car.make.longName));
				m.setModel(tuple.get(QCar.car.model.longName));
				m.setTotalSales(tuple.get(QCar.car.salePrice.sum()));
				result.add(m);
			}

		}
		else {
			List<Tuple> queryResult = new JPAQuery(entityManager).from(QCar.car)
					.where(QCar.car.saleDate.isNotNull()).groupBy(QCar.car.make.longName)
					.list(QCar.car.make.longName, QCar.car.salePrice.sum());
			for (Tuple tuple : queryResult) {
				Make m = new Make();
				m.setMake(tuple.get(QCar.car.make.longName));
				m.setModel(null);
				m.setTotalSales(tuple.get(QCar.car.salePrice.sum()));
				result.add(m);
			}
		}

		return result;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	public List<Month> readMonthReport() {

		List<Tuple> queryResult = new JPAQuery(entityManager)
				.from(QCar.car)
				.where(QCar.car.saleDate.isNotNull())
				.groupBy(QCar.car.saleDate.year(), QCar.car.saleDate.month())
				.list(QCar.car.saleDate.year(), QCar.car.saleDate.month(),
						QCar.car.salePrice.count(), QCar.car.salePrice.sum());

		List<Month> result = new ArrayList<>();
		for (Tuple tuple : queryResult) {
			Month m = new Month();
			Integer month = tuple.get(QCar.car.saleDate.month());
			m.setMonth(tuple.get(QCar.car.saleDate.year()) + "."
					+ (month < 10 ? "0" : "") + month);
			m.setTotalSold(tuple.get(QCar.car.salePrice.count()).intValue());
			m.setTotalSales(tuple.get(QCar.car.salePrice.sum()).intValue());
			result.add(m);
		}

		Collections.sort(result);
		return result;
	}

}
