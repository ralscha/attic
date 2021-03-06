package ch.rasc.springjpa.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.rasc.springjpa.Application;
import ch.rasc.springjpa.repository.RoleRepository;
import ch.rasc.springjpa.repository.UserRepository;

import com.mysema.query.types.expr.BooleanExpression;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EntityTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testUserDao() {
		assertEquals(2, this.userRepository.findAll().size());

		User adminUser = this.userRepository.findByUserName("admin");
		assertNotNull(adminUser);
		assertEquals("admin", adminUser.getUserName());
		assertEquals("test@test.ch", adminUser.getEmail());
		assertEquals("admin", adminUser.getFirstName());
		assertEquals("admin", adminUser.getName());
		assertTrue(this.passwordEncoder.matches("admin", adminUser.getPasswordHash()));
		assertTrue(adminUser.isEnabled());
		assertEquals("en_US", adminUser.getLocale());
		assertNotNull(adminUser.getCreateDate());

		User normalUser = this.userRepository.findByUserName("user");
		assertNotNull(normalUser);
		assertEquals("user", normalUser.getUserName());
		assertEquals("user@test.ch", normalUser.getEmail());
		assertEquals("user", normalUser.getFirstName());
		assertEquals("user", normalUser.getName());
		assertTrue(this.passwordEncoder.matches("user", normalUser.getPasswordHash()));
		assertTrue(normalUser.isEnabled());
		assertEquals("de_CH", normalUser.getLocale());
		assertNotNull(normalUser.getCreateDate());

	}

	@Test
	public void testUserCrud() {
		User newUser = new User();
		newUser.setUserName("new");
		newUser.setEmail("new@new.ch");
		newUser.setFirstName("newfirst");
		newUser.setName("new");
		newUser.setPasswordHash(this.passwordEncoder.encode("new"));
		newUser.setEnabled(false);
		newUser.setLocale("new");
		newUser.setCreateDate(new Date());

		this.userRepository.save(newUser);
		this.userRepository.flush();
		assertEquals(3, this.userRepository.findAll().size());

		newUser = this.userRepository.findByUserName("new");
		assertNotNull(newUser);
		assertEquals("new", newUser.getUserName());
		assertEquals("new@new.ch", newUser.getEmail());
		assertEquals("newfirst", newUser.getFirstName());
		assertEquals("new", newUser.getName());
		assertTrue(this.passwordEncoder.matches("new", newUser.getPasswordHash()));
		assertFalse(newUser.isEnabled());
		assertEquals("new", newUser.getLocale());
		assertNotNull(newUser.getCreateDate());

		this.userRepository.delete(newUser);
		assertEquals(2, this.userRepository.findAll().size());

		newUser = this.userRepository.findByUserName("new");
		assertEquals(null, newUser);
	}

	@Test
	public void testRoleDao() {
		assertEquals(2, this.roleRepository.findAll().size());

		Role adminRole = this.roleRepository.findByName("ROLE_ADMIN");
		assertNotNull(adminRole);
		assertEquals("ROLE_ADMIN", adminRole.getName());

		Role userRole = this.roleRepository.findByName("ROLE_USER");
		assertNotNull(userRole);
		assertEquals("ROLE_USER", userRole.getName());
	}

	@Test
	public void testRoleCrud() {
		Role newRole = new Role();
		newRole.setName("ROLE_NEW");
		this.roleRepository.saveAndFlush(newRole);
		assertEquals(3, this.roleRepository.findAll().size());

		newRole = this.roleRepository.findByName("ROLE_NEW");
		assertNotNull(newRole);
		assertEquals("ROLE_NEW", newRole.getName());

		this.roleRepository.delete(newRole);
		assertEquals(2, this.roleRepository.findAll().size());

		newRole = this.roleRepository.findByName("ROLE_NEW");
		assertEquals(null, newRole);
	}

	@Test
	public void testFilter() {
		List<User> users = this.userRepository.filter("admin", null, null);
		assertEquals(1, users.size());

		users = this.userRepository.filter("admin", "admin", null);
		assertEquals(1, users.size());

		users = this.userRepository.filter("admin", "admin", "test@test.ch");
		assertEquals(1, users.size());

		users = this.userRepository.filter("xy", "admin", "test@test.ch");
		assertEquals(0, users.size());
	}

	@Test
	public void testSpecification() {
		BooleanExpression nameEqAdmin = QUser.user.name.eq("admin");
		BooleanExpression firstNameEqAdmin = QUser.user.firstName.eq("admin");
		BooleanExpression emailEqTest = QUser.user.email.eq("test@test.ch");

		Iterable<User> users = this.userRepository.findAll(nameEqAdmin);
		assertEquals(1, StreamSupport.stream(users.spliterator(), false).count());

		users = this.userRepository.findAll(nameEqAdmin.and(firstNameEqAdmin));
		assertEquals(1, StreamSupport.stream(users.spliterator(), false).count());

		users = this.userRepository.findAll(
				BooleanExpression.allOf(nameEqAdmin, firstNameEqAdmin, emailEqTest));
		assertEquals(1, StreamSupport.stream(users.spliterator(), false).count());

		users = this.userRepository.findAll(BooleanExpression
				.allOf(QUser.user.name.eq("xy"), firstNameEqAdmin, emailEqTest));
		assertEquals(0, StreamSupport.stream(users.spliterator(), false).count());
	}

}
