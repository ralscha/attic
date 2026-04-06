package ch.rasc.springmongodb;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import ch.rasc.springmongodb.domain.City;

public class QueryCities {

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
			ctx.register(AppConfig.class);
			ctx.refresh();

			final MongoTemplate mongoOps = ctx.getBean(MongoTemplate.class);

			Point location = new Point(46.933333, 7.566667);
			NearQuery query = NearQuery.near(location)
					.maxDistance(new Distance(5, Metrics.KILOMETERS));

			GeoResults<City> geoResults = mongoOps.geoNear(query, City.class);
			for (GeoResult<City> geoResult : geoResults) {
				System.out.printf("%-20s   : %-10.8f\n",
						geoResult.getContent().getCityName(),
						geoResult.getDistance().getNormalizedValue());
			}

			System.out.println("-------------------------");
			System.out.println("BOX");
			System.out.println("-------------------------");

			Box box = new Box(new Point(46.933333, 7.566667), new Point(46.95, 7.616667));
			List<City> cities = mongoOps
					.find(new Query(Criteria.where("location").within(box)), City.class);
			for (City city : cities) {
				System.out.printf("%-20s\n", city.getCityName());
			}

			System.out.println("-------------------------");
			System.out.println("CIRCLE");
			System.out.println("-------------------------");

			Circle circle = new Circle(46.933333, 7.566667, 0.05);
			cities = mongoOps.find(new Query(Criteria.where("location").within(circle)),
					City.class);
			for (City city : cities) {
				System.out.printf("%-20s\n", city.getCityName());
			}

			City city = mongoOps.findOne(
					Query.query(Criteria.where("id").is("4e70a60a32a954d3b582d9d5")),
					City.class);
			System.out.println(city);

			cities = mongoOps.find(
					Query.query(Criteria.where("cityName").regex("^Wo", "i")).limit(10),
					City.class);
			for (City c : cities) {
				System.out.println(c.getCityName());
			}

		}
	}

}
