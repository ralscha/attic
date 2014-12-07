package ch.rasc.cartracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.rasc.cartracker.entity.QStaff;
import ch.rasc.cartracker.entity.Staff;
import ch.rasc.cartracker.entity.StaffUserRole;
import ch.rasc.cartracker.entity.option.Position;
import ch.rasc.cartracker.entity.option.UserRole;
import ch.rasc.edsutil.BaseCRUDService;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.mysema.query.jpa.impl.JPAQuery;

@Service
public class StaffService extends BaseCRUDService<Staff> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void preModify(final Staff entity) {
		if (entity.getPositionId() != null) {
			entity.setPosition(entityManager.getReference(Position.class,
					entity.getPositionId()));
		}
		else {
			entity.setPosition(null);
		}

		if (StringUtils.hasText(entity.getChangePassword())) {
			entity.setPassword(passwordEncoder.encode(entity.getChangePassword()));
			entity.setChangePassword(null);
		}
		else {
			if (entity.getId() != null) {
				String dbPassword = new JPAQuery(entityManager).from(QStaff.staff)
						.where(QStaff.staff.id.eq(entity.getId()))
						.singleResult(QStaff.staff.password);
				entity.setPassword(dbPassword);
			}
		}

		entity.getStaffUserRoles().clear();
		if (entity.getUserRoleIds() != null) {

			if (entity.getUserRoleIds().size() == 1
					&& entity.getUserRoleIds().iterator().next() == null) {
				entity.getUserRoleIds().clear();
			}

			entity.setStaffUserRoles(ImmutableSet.copyOf(Collections2.transform(entity
					.getUserRoleIds(), input -> {
				UserRole userRole = entityManager.getReference(UserRole.class, input);
				StaffUserRole sur = new StaffUserRole();
				sur.setStaff(entity);
				sur.setUserRole(userRole);
				return sur;
			})));
		}
	}

}
