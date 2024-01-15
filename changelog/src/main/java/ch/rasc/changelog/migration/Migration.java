package ch.rasc.changelog.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.changelog.entity.Change;
import ch.rasc.changelog.entity.ChangeType;
import ch.rasc.changelog.entity.Customer;
import ch.rasc.changelog.entity.CustomerBuild;
import ch.rasc.changelog.entity.CustomerChange;
import ch.rasc.changelog.entity.QChange;
import ch.rasc.changelog.entity.QCustomer;

//@Service
public class Migration {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Map<Integer, Long> customerMigration() throws IOException {
		Map<Integer, Long> oldNewIds = new HashMap<>();

		File f = new File("d:/_download/changelog_old.xls");
		try (FileInputStream fis = new FileInputStream(f)) {
			Workbook workbook = new HSSFWorkbook(fis);

			Multimap<Integer, CustomerBuild> customerBuilds = ArrayListMultimap.create();
			Sheet sheet = workbook.getSheet("CustomerBuild");
			Iterator<Row> it = sheet.rowIterator();
			// Ignore first row
			it.next();

			while (it.hasNext()) {
				Row row = it.next();

				Date versionDate = row.getCell(3).getDateCellValue();
				String versionNo = row.getCell(4).getStringCellValue();
				int customerId = (int) row.getCell(5).getNumericCellValue();

				CustomerBuild cb = new CustomerBuild();
				cb.setVersionDate(versionDate);
				cb.setVersionNumber(versionNo);
				customerBuilds.put(customerId, cb);
			}

			sheet = workbook.getSheet("Customer");
			it = sheet.rowIterator();
			// Ignore first row
			it.next();

			for (Customer customer : new JPAQuery(this.entityManager)
					.from(QCustomer.customer).list(QCustomer.customer)) {
				this.entityManager.remove(customer);
			}

			while (it.hasNext()) {
				Row row = it.next();

				int id = (int) row.getCell(0).getNumericCellValue();
				String longName = row.getCell(3).getStringCellValue();
				String shortName = row.getCell(4).getStringCellValue();

				Customer customer = new Customer();
				customer.setLongName(longName);
				customer.setShortName(shortName);

				Collection<CustomerBuild> cbs = customerBuilds.get(id);
				for (CustomerBuild cb : cbs) {
					cb.setCustomer(customer);
				}
				customer.getBuilds().addAll(cbs);
				this.entityManager.persist(customer);

				oldNewIds.put(id, customer.getId());
			}

		}

		return oldNewIds;
	}

	@Transactional
	public void changeMigration(Map<Integer, Long> oldNewCustomerIds) throws IOException {

		File f = new File("d:/_download/changelog_old.xls");
		try (FileInputStream fis = new FileInputStream(f)) {
			Workbook workbook = new HSSFWorkbook(fis);

			Multimap<Integer, CustomerChange> customerChanges = ArrayListMultimap
					.create();
			Sheet sheet = workbook.getSheet("CustomerChange");
			Iterator<Row> it = sheet.rowIterator();
			// Ignore first row
			it.next();

			while (it.hasNext()) {
				Row row = it.next();

				int changeId = (int) row.getCell(3).getNumericCellValue();
				int customerId = (int) row.getCell(4).getNumericCellValue();

				CustomerChange cc = new CustomerChange();
				cc.setCustomer(this.entityManager.find(Customer.class,
						oldNewCustomerIds.get(customerId)));

				customerChanges.put(changeId, cc);
			}

			sheet = workbook.getSheet("Change");
			it = sheet.rowIterator();
			// Ignore first row
			it.next();

			new JPADeleteClause(this.entityManager, QChange.change).execute();

			while (it.hasNext()) {
				Row row = it.next();

				int id = (int) row.getCell(0).getNumericCellValue();

				Cell bugNoCell = row.getCell(3);
				String bugNo = null;
				if (bugNoCell != null) {
					bugNo = bugNoCell.getStringCellValue();
				}

				String description = row.getCell(4).getStringCellValue();

				String subject = null;
				if (description != null) {
					int pos = description.indexOf("\r");
					if (pos != -1) {
						subject = description.substring(0, Math.min(pos, 254));
					}
					else {
						subject = description.substring(0,
								Math.min(description.length(), 254));
					}
				}

				Date implementationDate = row.getCell(5).getDateCellValue();

				Cell moduleCell = row.getCell(6);
				String module = null;
				if (moduleCell != null) {
					module = moduleCell.getStringCellValue();
				}
				String typ = row.getCell(7).getStringCellValue();

				Change change = new Change();
				change.setBugNumber(bugNo);
				change.setSubject(subject);
				change.setDescription(description);
				change.setImplementationDate(implementationDate);
				change.setModule(module);
				change.setTyp(ChangeType.valueOf(typ));

				Collection<CustomerChange> ccs = customerChanges.get(id);
				for (CustomerChange cc : ccs) {
					cc.setChange(change);
				}

				change.getCustomerChanges().addAll(ccs);
				this.entityManager.persist(change);
			}

		}

	}

	public static void main(String[] args) throws IOException {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				Config.class)) {
			Migration migration = ctx.getBean(Migration.class);
			Map<Integer, Long> oldNewIds = migration.customerMigration();
			migration.changeMigration(oldNewIds);
		}
	}

}
