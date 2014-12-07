package ch.rasc.phoenix.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.phoenix.entity.AffectedItem;
import ch.rasc.phoenix.entity.QAffectedItem;

import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class AffectedItemService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public List<AffectedItem> read() {
		return new JPAQuery(entityManager).from(QAffectedItem.affectedItem)
				.orderBy(QAffectedItem.affectedItem.id.asc())
				.list(QAffectedItem.affectedItem);
	}

}
