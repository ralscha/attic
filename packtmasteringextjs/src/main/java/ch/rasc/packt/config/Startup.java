package ch.rasc.packt.config;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.packt.entity.AppGroup;
import ch.rasc.packt.entity.Menu;
import ch.rasc.packt.entity.Permission;
import ch.rasc.packt.entity.QUser;
import ch.rasc.packt.entity.User;

import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (new JPAQuery(entityManager).from(QUser.user).count() == 0) {

			AppGroup group = new AppGroup();
			group.setName("admin");
			entityManager.persist(group);

			User user = new User();
			user.setName("The Administrator");
			user.setUserName("admin");
			user.setPassword(passwordEncoder.encode("admin"));
			user.setEmail("admin@company.com");
			user.setAppGroup(group);
			entityManager.persist(user);

			Menu cmsMenu = new Menu();
			cmsMenu.setMenuText("cms");
			cmsMenu.setIconCls("menu_cms");
			cmsMenu.setParent(null);
			entityManager.persist(cmsMenu);

			Menu menuFilms = new Menu();
			menuFilms.setMenuText("films");
			menuFilms.setIconCls("menu_films");
			menuFilms.setParent(cmsMenu);
			menuFilms.setClassName("filmsgrid");
			entityManager.persist(menuFilms);

			Menu adminMenu = new Menu();
			adminMenu.setMenuText("menu1");
			adminMenu.setIconCls("menu_admin");
			adminMenu.setParent(null);
			entityManager.persist(adminMenu);

			Menu menu11 = new Menu();
			menu11.setMenuText("menu11");
			menu11.setIconCls("menu_groups");
			menu11.setParent(adminMenu);
			menu11.setClassName("groups");
			entityManager.persist(menu11);

			Menu menu12 = new Menu();
			menu12.setMenuText("menu12");
			menu12.setIconCls("menu_users");
			menu12.setParent(adminMenu);
			menu12.setClassName("users");
			entityManager.persist(menu12);

			Menu staticDataMenu = new Menu();
			staticDataMenu.setMenuText("staticData");
			staticDataMenu.setIconCls("menu_staticdata");
			staticDataMenu.setParent(null);
			entityManager.persist(staticDataMenu);

			Menu staticMenuActor = new Menu();
			staticMenuActor.setMenuText("actors");
			staticMenuActor.setIconCls("menu_actor");
			staticMenuActor.setParent(staticDataMenu);
			staticMenuActor.setClassName("actorsgrid");
			entityManager.persist(staticMenuActor);

			Menu staticMenuCategory = new Menu();
			staticMenuCategory.setMenuText("categories");
			staticMenuCategory.setIconCls("menu_category");
			staticMenuCategory.setParent(staticDataMenu);
			staticMenuCategory.setClassName("categoriesgrid");
			entityManager.persist(staticMenuCategory);

			Menu staticMenuLanguage = new Menu();
			staticMenuLanguage.setMenuText("languages");
			staticMenuLanguage.setIconCls("menu_language");
			staticMenuLanguage.setParent(staticDataMenu);
			staticMenuLanguage.setClassName("languagesgrid");
			entityManager.persist(staticMenuLanguage);

			Menu staticMenuCity = new Menu();
			staticMenuCity.setMenuText("cities");
			staticMenuCity.setIconCls("menu_city");
			staticMenuCity.setParent(staticDataMenu);
			staticMenuCity.setClassName("citiesgrid");
			entityManager.persist(staticMenuCity);

			Menu staticMenuCountry = new Menu();
			staticMenuCountry.setMenuText("countries");
			staticMenuCountry.setIconCls("menu_country");
			staticMenuCountry.setParent(staticDataMenu);
			staticMenuCountry.setClassName("countriesgrid");
			entityManager.persist(staticMenuCountry);

			Menu reportsMenu = new Menu();
			reportsMenu.setMenuText("reports");
			reportsMenu.setIconCls("menu_reports");
			reportsMenu.setParent(null);
			entityManager.persist(reportsMenu);

			Menu menuSales = new Menu();
			menuSales.setMenuText("salesfilmcategory");
			menuSales.setIconCls("menu_salesfilmc");
			menuSales.setParent(reportsMenu);
			menuSales.setClassName("salesfilmcategory");
			entityManager.persist(menuSales);

			Menu mailClientMenu = new Menu();
			mailClientMenu.setMenuText("mailclient");
			mailClientMenu.setIconCls("menu_mailclient");
			mailClientMenu.setParent(null);
			entityManager.persist(mailClientMenu);

			Menu mailMenu = new Menu();
			mailMenu.setMenuText("mail");
			mailMenu.setIconCls("menu_mail");
			mailMenu.setParent(mailClientMenu);
			mailMenu.setClassName("mailcontainer");
			entityManager.persist(mailMenu);

			for (Menu menu : Arrays.asList(cmsMenu, menuFilms, adminMenu, menu11, menu12,
					staticDataMenu, staticMenuActor, staticMenuCategory,
					staticMenuLanguage, staticMenuCity, staticMenuCountry, reportsMenu,
					menuSales, mailClientMenu, mailMenu)) {
				Permission permission = new Permission();
				permission.setAppGroup(group);
				permission.setMenu(menu);
				entityManager.persist(permission);
			}

		}

	}

}
