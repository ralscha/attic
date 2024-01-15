package ch.rasc.proto.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.entity.Person;
import ch.rasc.proto.entity.Project;
import ch.rasc.proto.entity.Task;

@Service
@PreAuthorize("isAuthenticated()")
public class GanttService {

	private final DbManager dbManager;

	@Autowired
	public GanttService(DbManager dbManager) {
		this.dbManager = dbManager;
	}

	@ExtDirectMethod(STORE_READ)
	public List<Map<String, Object>> readResources() {
		return this.dbManager.runInTx(db -> {
			return DbManager.getAll(db, Person.class);
		}).stream().map(p -> {
			Map<String, Object> m = new HashMap<>();
			m.put("Id", p.getId());
			m.put("Name", p.getFirstName() + " " + p.getLastName());
			return m;
		}).collect(Collectors.toList());
	}

	@ExtDirectMethod(STORE_READ)
	public List<Task> readTasks() {

		List<Project> projects = this.dbManager.runInTx(db -> {
			return DbManager.getAll(db, Project.class);
		});

		projects.sort(Comparator.comparing(Project::getMs1));

		List<Task> result = new ArrayList<>();
		for (Project project : projects) {

			Task root = new Task();
			root.setId(project.getId());
			root.setName(project.getName());
			root.setStartDate(project.getMs1());
			root.setEndDate(project.getMs6());
			root.setExpanded(true);
			root.setChildren(new ArrayList<>());
			result.add(root);

			Task p1 = new Task();
			p1.setId(project.getId() * 1_000);
			p1.setName("P1");
			p1.setLeaf(true);
			p1.setStartDate(project.getMs1());
			p1.setEndDate(project.getMs2());
			root.getChildren().add(p1);

			Task p2 = new Task();
			p2.setId(project.getId() * 1_000_001);
			p2.setName("P2");
			p2.setLeaf(true);
			p2.setStartDate(project.getMs2());
			p2.setEndDate(project.getMs3());
			root.getChildren().add(p2);

			Task p3 = new Task();
			p3.setId(project.getId() * 1_000_002);
			p3.setName("P3");
			p3.setLeaf(true);
			p3.setStartDate(project.getMs3());
			p3.setEndDate(project.getMs4());
			root.getChildren().add(p3);

			Task p4 = new Task();
			p4.setId(project.getId() * 1_000_003);
			p4.setName("P4");
			p4.setLeaf(true);
			p4.setStartDate(project.getMs4());
			p4.setEndDate(project.getMs5());
			root.getChildren().add(p4);

			Task p5 = new Task();
			p5.setId(project.getId() * 1_000_004);
			p5.setName("P5");
			p5.setLeaf(true);
			p5.setStartDate(project.getMs5());
			p5.setEndDate(project.getMs6());
			root.getChildren().add(p5);

		}

		return result;

	}
}
