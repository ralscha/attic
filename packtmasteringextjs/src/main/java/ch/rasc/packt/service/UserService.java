package ch.rasc.packt.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.packt.entity.AppGroup;
import ch.rasc.packt.entity.QUser;
import ch.rasc.packt.entity.User;

import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class UserService extends BaseCRUDService<User> {

	@PreAuthorize("isAuthenticated()")
	@ExtDirectMethod(STORE_READ)
	@Transactional(readOnly = true)
	public ExtDirectStoreResult<User> readUser(ExtDirectStoreReadRequest request,
			@RequestParam(required = false) Long group) {
		if (group != null) {
			List<User> groupUsers = new JPAQuery(entityManager).from(QUser.user)
					.where(QUser.user.appGroup.id.eq(group)).list(QUser.user);
			return new ExtDirectStoreResult<>(groupUsers);
		}

		return super.read(request);
	}

	@Override
	protected void preModify(User entity) {
		if (entity.getId() != null) {
			User dbUser = entityManager.find(User.class, entity.getId());
			entity.setPassword(dbUser.getPassword());
		}

		if (entity.getAppGroupId() != null) {
			entity.setAppGroup(entityManager.getReference(AppGroup.class,
					entity.getAppGroupId()));
		}
		else {
			entity.setAppGroup(null);
		}
	}

}
