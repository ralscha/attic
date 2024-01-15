package ch.rasc.todo.config;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class BuildSearchIndex implements ApplicationListener<ContextRefreshedEvent> {

	private final Db db;

	public BuildSearchIndex(Db db) {
		super();
		this.db = db;
	}

	/**
	 * Create an initial Lucene index for the data already present in the database. This
	 * method is called during Spring's startup.
	 * 
	 * @param event Event raised when an ApplicationContext gets initialized or refreshed.
	 */
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		try {
			FullTextEntityManager fullTextEntityManager = Search
					.getFullTextEntityManager(db.em());
			fullTextEntityManager.createIndexer().startAndWait();
		}
		catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the serach index: "
					+ e.toString());
		}
		return;
	}

}