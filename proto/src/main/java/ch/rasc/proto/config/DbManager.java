package ch.rasc.proto.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.mapdb.BTreeKeySerializer;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Fun;
import org.mapdb.TxBlock;
import org.mapdb.TxMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ch.rasc.proto.entity.AbstractPersistable;
import ch.rasc.proto.entity.Configuration;
import ch.rasc.proto.entity.ConfigurationKey;
import ch.rasc.proto.entity.PersistentLogin;

@Component
public class DbManager {

	private static final String ID_GEN_PREFIX = ".Id";

	private final TxMaker txMaker;

	@Autowired
	public DbManager(AppProperties appProperties) {
		Path dbPath = Paths.get(appProperties.getDb());
		this.txMaker = DBMaker.newFileDB(dbPath.toFile()).closeOnJvmShutdown()
				.compressionEnable().makeTxMaker();

		initDb();
	}

	private void initDb() {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
				false);
		provider.addIncludeFilter(new AssignableTypeFilter(AbstractPersistable.class));
		Set<BeanDefinition> bd = provider
				.findCandidateComponents(AbstractPersistable.class.getPackage().getName());

		DB tx = startTx();
		if (!tx.exists(Configuration.class.getName())) {
			tx.createTreeMap(Configuration.class.getName()).make();
			tx.createTreeMap(PersistentLogin.class.getName()).make();

			for (BeanDefinition b : bd) {
				tx.getAtomicLong(b.getBeanClassName() + ID_GEN_PREFIX);
				tx.createTreeMap(b.getBeanClassName()).counterEnable()
						.keySerializer(BTreeKeySerializer.ZERO_OR_POSITIVE_LONG).make();
			}

			tx.commit();
		}
		tx.close();
	}

	@PreDestroy
	public void close() {
		if (this.txMaker != null) {
			this.txMaker.close();
		}
	}

	public DB startTx() {
		return this.txMaker.makeTx();
	}

	public void runInTxWithoutResult(TxBlock txBlock) {
		this.txMaker.execute(txBlock);
	}

	public <A> A runInTx(Fun.Function1<A, DB> txBlock) {
		return this.txMaker.execute(txBlock);
	}

	public TxMaker getTxMaker() {
		return this.txMaker;
	}

	public static void put(DB db, AbstractPersistable entity) {
		String className = entity.getClass().getName();
		if (entity.getId() == null || entity.getId() <= 0) {
			entity.setId(db.getAtomicLong(className + ID_GEN_PREFIX).incrementAndGet());
		}
		db.getTreeMap(className).put(entity.getId(), entity);
	}

	public void put(AbstractPersistable entity) {
		runInTxWithoutResult(db -> put(db, entity));
	}

	public void put(Object entity, Object key) {
		runInTxWithoutResult(db -> {
			db.getTreeMap(entity.getClass().getName()).put(key, entity);
		});
	}

	public static void remove(DB db, AbstractPersistable entity) {
		db.getTreeMap(entity.getClass().getName()).remove(entity.getId());
	}

	public void remove(AbstractPersistable entity) {
		runInTxWithoutResult(db -> remove(db, entity));
	}

	public <T> void remove(Class<T> clazz, Object key) {
		runInTxWithoutResult(db -> db.getTreeMap(clazz.getName()).remove(key));
	}

	public static <T extends AbstractPersistable> ArrayList<T> getAll(DB db,
			Class<T> entityClass) {
		BTreeMap<Long, T> map = db.getTreeMap(entityClass.getName());
		return new ArrayList<>(map.values());
	}

	public <T> ArrayList<T> getAll(Class<T> entityClass) {
		return runInTx(db -> {
			BTreeMap<Object, T> map = db.getTreeMap(entityClass.getName());
			return new ArrayList<>(map.values());
		});
	}

	public static int count(DB db, Class<? extends AbstractPersistable> entityClass) {
		return db.getTreeMap(entityClass.getName()).size();
	}

	public static <T> T get(DB db, Class<T> entityClass, Object key) {
		BTreeMap<Object, T> map = db.getTreeMap(entityClass.getName());
		return map.get(key);
	}

	public <T> T get(Class<T> entityClass, Object key) {
		return runInTx(db -> get(db, entityClass, key));
	}

	public String readConfig(final ConfigurationKey key) {
		return runInTx(db -> {
			BTreeMap<String, String> map = db.getTreeMap(Configuration.class.getName());
			return map.get(key.name());
		});
	}

	public void storeConfig(final ConfigurationKey key, final String value) {
		runInTxWithoutResult(db -> {
			BTreeMap<String, String> map = db.getTreeMap(Configuration.class.getName());
			if (StringUtils.hasText(value)) {
				map.put(key.name(), value);
			}
			else {
				map.remove(key.name());
			}
		});
	}

}
