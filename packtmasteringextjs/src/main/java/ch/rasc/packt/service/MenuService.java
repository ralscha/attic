package ch.rasc.packt.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.rasc.packt.dto.Item;
import ch.rasc.packt.dto.Root;
import ch.rasc.packt.entity.Menu;
import ch.rasc.packt.entity.QMenu;
import ch.rasc.packt.entity.QPermission;
import ch.rasc.packt.entity.User;
import ch.rasc.packt.util.Util;

import com.google.common.collect.ImmutableList;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class MenuService {

	@PersistenceContext
	private EntityManager entityManager;

	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	@ExtDirectMethod(value = ExtDirectMethodType.TREE_LOAD)
	public List<Root> loadPermission(@RequestParam(required = false) Long group,
			@SuppressWarnings("unused") String node) {

		ImmutableList.Builder<Root> builder = ImmutableList.builder();

		List<Menu> rootMenus = new JPAQuery(entityManager).from(QMenu.menu)
				.where(QMenu.menu.parent.isNull()).list(QMenu.menu);

		for (Menu root : rootMenus) {

			Root rootObj = new Root();
			rootObj.setIconCls(root.getIconCls());
			rootObj.setId(root.getId());
			rootObj.setText(root.getMenuText());

			if (group != null) {
				rootObj.setChecked(new JPAQuery(entityManager)
						.from(QPermission.permission)
						.where(QPermission.permission.appGroup.id.eq(group).and(
								QPermission.permission.menu.eq(root))).exists());
			}
			else {
				rootObj.setChecked(Boolean.FALSE);
			}
			builder.add(rootObj);

			List<Menu> childMenus = new JPAQuery(entityManager).from(QMenu.menu)
					.where(QMenu.menu.parent.eq(root)).list(QMenu.menu);

			if (!childMenus.isEmpty()) {
				rootObj.setExpanded(Boolean.TRUE);

				ImmutableList.Builder<Item> itemBuilder = ImmutableList.builder();
				for (Menu child : childMenus) {
					Item item = new Item();
					item.setClassName(child.getClassName());
					item.setIconCls(child.getIconCls());
					item.setId(child.getId());
					item.setRoot(rootObj);
					item.setRoot_id(rootObj.getId());
					item.setText(child.getMenuText());
					if (group != null) {
						item.setChecked(new JPAQuery(entityManager)
								.from(QPermission.permission)
								.where(QPermission.permission.appGroup.id.eq(group).and(
										QPermission.permission.menu.eq(child))).exists());
					}
					else {
						item.setChecked(Boolean.FALSE);
					}
					itemBuilder.add(item);
				}

				rootObj.setChildren(itemBuilder.build());

			}
			else {
				rootObj.setLeaf(true);
			}
		}

		return builder.build();

	}

	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ)
	public List<Root> read() {
		ImmutableList.Builder<Root> builder = ImmutableList.builder();

		User user = Util.getLoggedInUser(entityManager);
		if (user != null) {

			if (user.getAppGroup() != null) {
				List<Menu> rootMenus = new JPAQuery(entityManager)
						.from(QMenu.menu)
						.innerJoin(QMenu.menu.permissions, QPermission.permission)
						.where(QMenu.menu.parent.isNull().and(
								QPermission.permission.appGroup.in(user.getAppGroup())))
						.list(QMenu.menu);

				for (Menu root : rootMenus) {
					List<Menu> childMenus = new JPAQuery(entityManager)
							.from(QMenu.menu)
							.innerJoin(QMenu.menu.permissions, QPermission.permission)
							.where(QMenu.menu.parent.eq(root)
									.and(QPermission.permission.appGroup.in(user
											.getAppGroup()))).list(QMenu.menu);
					if (!childMenus.isEmpty()) {
						Root rootObj = new Root();
						rootObj.setIconCls(root.getIconCls());
						rootObj.setId(root.getId());
						rootObj.setText(root.getMenuText());

						ImmutableList.Builder<Item> itemBuilder = ImmutableList.builder();
						for (Menu child : childMenus) {
							Item item = new Item();
							item.setClassName(child.getClassName());
							item.setIconCls(child.getIconCls());
							item.setId(child.getId());
							item.setRoot(rootObj);
							item.setRoot_id(rootObj.getId());
							item.setText(child.getMenuText());
							itemBuilder.add(item);
						}

						rootObj.setChildren(itemBuilder.build());
						builder.add(rootObj);
					}
				}

			}

		}

		return builder.build();
	}
}
