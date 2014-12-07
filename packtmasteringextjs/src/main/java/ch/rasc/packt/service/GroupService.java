package ch.rasc.packt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.packt.entity.AppGroup;
import ch.rasc.packt.entity.Menu;
import ch.rasc.packt.entity.Permission;
import ch.rasc.packt.entity.QPermission;

import com.mysema.query.jpa.impl.JPADeleteClause;

@Service
public class GroupService extends BaseCRUDService<AppGroup> {

	@Override
	@Transactional
	public ExtDirectStoreResult<AppGroup> destroy(Long id) {
		new JPADeleteClause(entityManager, QPermission.permission).where(
				QPermission.permission.appGroup.id.eq(id)).execute();
		return super.destroy(id);
	}

	@Override
	protected void postModify(AppGroup entity) {
		new JPADeleteClause(entityManager, QPermission.permission).where(
				QPermission.permission.appGroup.eq(entity)).execute();

		if (entity.getPermissions() != null) {
			for (Long menuId : entity.getPermissions()) {
				Permission permission = new Permission();
				permission.setAppGroup(entity);
				permission.setMenu(entityManager.getReference(Menu.class, menuId));
				entityManager.persist(permission);
			}

		}

	}

}
