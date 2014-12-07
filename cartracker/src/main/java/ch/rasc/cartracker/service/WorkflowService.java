package ch.rasc.cartracker.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.rasc.cartracker.config.StaffUserDetails;
import ch.rasc.cartracker.entity.Car;
import ch.rasc.cartracker.entity.QWorkflow;
import ch.rasc.cartracker.entity.Staff;
import ch.rasc.cartracker.entity.Workflow;
import ch.rasc.cartracker.entity.option.QStatus;
import ch.rasc.cartracker.entity.option.Status;

import com.google.common.collect.ImmutableMap;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class WorkflowService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	@Transactional(readOnly = true)
	public List<Workflow> read(@RequestParam(value = "carId") Long carId) {
		return new JPAQuery(entityManager).from(QWorkflow.workflow)
				.where(QWorkflow.workflow.car.id.eq(carId)).list(QWorkflow.workflow);
	}

	@SuppressWarnings({ "null", "rawtypes" })
	@Transactional
	@ExtDirectMethod
	public ImmutableMap updateStatus(Long carId, String action, String notes) {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof StaffUserDetails) {
			StaffUserDetails userDetail = (StaffUserDetails) principal;
			Car car = entityManager.find(Car.class, carId);
			Staff staff = entityManager
					.getReference(Staff.class, userDetail.getStaffId());

			Status newStatus = null;
			boolean approved = false;

			if (action.equalsIgnoreCase("Approve")) {
				approved = true;
				switch (car.getStatus().getLongName()) {
				case "Initiated":
					newStatus = lookupStatus("In-Audit");
					break;
				case "In-Audit":
					newStatus = lookupStatus("In-Review");
					break;
				default:
					newStatus = lookupStatus("Approved");
					break;
				}
			}
			else if (action.equalsIgnoreCase("Reject")) {
				switch (car.getStatus().getLongName()) {
				case "Approved":
				case "In-Review":
					newStatus = lookupStatus("Rejected");
					break;
				default:
					newStatus = lookupStatus("Initiated");
					break;
				}
			}
			else if (action.equalsIgnoreCase("Restart")) {
				newStatus = lookupStatus("Initiated");
			}

			Workflow workflow = new Workflow();
			workflow.setActive(true);
			workflow.setCreateDate(new Date());
			workflow.setApproved(approved);
			workflow.setCar(car);
			workflow.setStaff(staff);
			workflow.setLastStatus(car.getStatus());
			workflow.setNextStatus(newStatus);
			workflow.setNotes(notes);
			entityManager.persist(workflow);

			car.setStatus(newStatus);
			return ImmutableMap.of("statusId", newStatus.getId(), "statusName",
					newStatus.getLongName());

		}

		return null;
	}

	private Status lookupStatus(String statusName) {
		return new JPAQuery(entityManager).from(QStatus.status)
				.where(QStatus.status.shortName.eq(statusName))
				.uniqueResult(QStatus.status);
	}

}
