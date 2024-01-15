package ch.rasc.todo.controller;

import java.util.ArrayList;
import java.util.List;

import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.rasc.todo.config.Db;
import ch.rasc.todo.dto.Audit;
import ch.rasc.todo.dto.AuditType;
import ch.rasc.todo.dto.EntityChangeEvent;
import ch.rasc.todo.dto.EntityChangeType;
import ch.rasc.todo.entity.Todo;
import ch.rasc.todo.eventbus.EventBusEvent;

@RestController
@RequestMapping(value = "/audit")
public class AuditController {

	private final Db db;

	private final ApplicationEventPublisher publisher;

	public AuditController(Db db, ApplicationEventPublisher publisher) {
		this.db = db;
		this.publisher = publisher;
	}

	@EventListener
	@Async
	public void handleEvent(EntityChangeEvent event) {
		if (event.type() != EntityChangeType.DELETE) {
			this.db.javers().commit(event.author(), event.entity());
		}
		else {
			this.db.javers().commitShallowDelete(event.author(), event.entity());
		}

		this.publisher.publishEvent(EventBusEvent.of("historychange"));
	}

	@RequestMapping("/todo")
	public List<Audit> getTodoChanges() {
		QueryBuilder jqlQuery = QueryBuilder.byClass(Todo.class).withNewObjectChanges();

		List<Change> changes = this.db.javers().findChanges(jqlQuery.build());
		List<Audit> audits = new ArrayList<>();
		for (Change change : changes) {
			Audit audit = new Audit();
			audit.setTodoId((long) change.getAffectedLocalId());
			audit.setTimestamp(change.getCommitMetadata().get().getCommitDate()
					.toDateTime().getMillis());
			if (change instanceof NewObject) {
				audit.setType(AuditType.NEW);
			}
			else if (change instanceof ValueChange) {
				audit.setType(AuditType.UPDATE);
				ValueChange pc = (ValueChange) change;
				audit.setProperty(pc.getPropertyName());
				audit.setLeft(pc.getLeft());
				audit.setRight(pc.getRight());
			}
			else if (change instanceof ObjectRemoved) {
				audit.setType(AuditType.DELETE);
			}
			audits.add(audit);
		}
		return audits;
	}
}
