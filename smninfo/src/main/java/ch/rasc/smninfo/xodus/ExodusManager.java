package ch.rasc.smninfo.xodus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

import ch.rasc.smninfo.config.AppProperties;
import ch.rasc.smninfo.domain.DataPoint;
import ch.rasc.smninfo.domain.ImmutableTimeDouble;
import ch.rasc.smninfo.domain.Station;
import ch.rasc.smninfo.domain.TimeDouble;
import jetbrains.exodus.ArrayByteIterable;
import jetbrains.exodus.ByteIterable;
import jetbrains.exodus.bindings.StringBinding;
import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;
import jetbrains.exodus.entitystore.StoreTransaction;
import jetbrains.exodus.env.Cursor;
import jetbrains.exodus.env.Environment;
import jetbrains.exodus.env.Environments;
import jetbrains.exodus.env.Store;
import jetbrains.exodus.env.StoreConfig;

@Component
public class ExodusManager {

	public static final String CODE = "code";
	public static final String EPOCH_SECONDS = "epochSeconds";
	public static final String DATAPOINT = "datapoint";

	private static final String STATIONS_STORE = "stations";

	private final Environment environment;

	private final PersistentEntityStore persistentEntityStore;

	private final KryoPool kryoPool;

	@Autowired
	public ExodusManager(AppProperties appProperties) {
		this.environment = Environments.newInstance(appProperties.getXodusPath());
		this.persistentEntityStore = PersistentEntityStores.newInstance(this.environment);

		KryoFactory factory = () -> {
			Kryo kryo = new Kryo();
			kryo.register(Station.class);
			return kryo;
		};
		this.kryoPool = new KryoPool.Builder(factory).softReferences().build();
	}

	@PreDestroy
	public void destroy() {
		if (this.persistentEntityStore != null) {
			this.persistentEntityStore.close();
		}
	}

	public void deleteAllStations() {
		this.environment.executeInTransaction(txn -> {
			if (this.environment.storeExists(STATIONS_STORE, txn)) {
				this.environment.removeStore(STATIONS_STORE, txn);
			}
		});
	}

	public void insertStations(List<Station> stations) {
		this.environment.executeInTransaction(txn -> {

			Store store = this.environment.openStore(STATIONS_STORE,
					StoreConfig.WITHOUT_DUPLICATES, txn);

			Kryo kryo = this.kryoPool.borrow();
			try {
				for (Station station : stations) {
					@SuppressWarnings("resource")
					Output output = new Output(32, -1);
					kryo.writeObject(output, station);
					output.close();
					store.put(txn, StringBinding.stringToEntry(station.getCode()),
							new ArrayByteIterable(output.toBytes()));
				}
			}
			finally {
				this.kryoPool.release(kryo);
			}

		});
	}

	public Station readStation(final String code) {
		return this.environment.computeInReadonlyTransaction(txn -> {
			if (this.environment.storeExists(STATIONS_STORE, txn)) {
				Store store = this.environment.openStore(STATIONS_STORE,
						StoreConfig.WITHOUT_DUPLICATES, txn);
				try (Cursor cursor = store.openCursor(txn)) {
					ByteIterable value = cursor
							.getSearchKey(StringBinding.stringToEntry(code));
					if (value != null) {
						ArrayByteIterable abi = new ArrayByteIterable(value);
						return this.kryoPool.run(kryo -> {
							try (Input input = new Input(abi.getBytesUnsafe(), 0,
									abi.getLength())) {
								return kryo.readObject(input, Station.class);
							}
						});
					}
				}
			}
			return null;
		});
	}

	public List<Station> readAllStations() {
		return this.environment.computeInReadonlyTransaction(txn -> {
			if (this.environment.storeExists(STATIONS_STORE, txn)) {
				Store store = this.environment.openStore(STATIONS_STORE,
						StoreConfig.WITHOUT_DUPLICATES, txn);
				List<Station> result = new ArrayList<>();
				try (Cursor cursor = store.openCursor(txn)) {
					while (cursor.getNext()) {
						ByteIterable value = cursor.getValue();
						Station station = this.kryoPool.run(kryo -> {
							ArrayByteIterable abi = new ArrayByteIterable(value);
							try (Input input = new Input(abi.getBytesUnsafe(), 0,
									abi.getLength())) {
								return kryo.readObject(input, Station.class);
							}
						});
						result.add(station);
					}
				}
				return result;
			}
			return Collections.emptyList();
		});
	}

	public void importDataPoints(List<DataPoint> dataPoints) {
		this.persistentEntityStore.executeInTransaction(txn -> {
			int c = 0;
			for (DataPoint dataPoint : dataPoints) {
				Entity dp = txn.newEntity(DATAPOINT);
				dataPoint.toEntity(dp);

				c++;
				if (c % 10_000 == 0) {
					txn.flush();
				}
			}
		});
	}

	public void insertDataPoints(List<DataPoint> dataPoints) {
		this.persistentEntityStore.executeInTransaction(txn -> {
			for (DataPoint dataPoint : dataPoints) {
				insertInternally(dataPoint, txn);
			}
		});
	}

	private static void insertInternally(final DataPoint dataPoint,
			StoreTransaction txn) {
		if (txn.find(DATAPOINT, CODE, dataPoint.getCode())
				.intersect(
						txn.find(DATAPOINT, EPOCH_SECONDS, dataPoint.getEpochSeconds()))
				.isEmpty()) {

			Entity dp = txn.newEntity(DATAPOINT);
			dataPoint.toEntity(dp);
		}
	}

	public List<DataPoint> readDataPoints(final String code, long startEpochSeconds,
			long endEpochSeconds) {
		return this.persistentEntityStore.computeInReadonlyTransaction(txn -> {
			EntityIterable dps = txn.find(DATAPOINT, CODE, code).intersect(txn
					.find(DATAPOINT, EPOCH_SECONDS, startEpochSeconds, endEpochSeconds));
			List<DataPoint> result = new ArrayList<>();
			dps.forEach(e -> result.add(new DataPoint(e)));
			return result;
		});
	}

	public List<TimeDouble> readDataPointProperty(final String code,
			final String property, long startEpochSeconds, long endEpochSeconds) {

		return this.persistentEntityStore.computeInReadonlyTransaction(txn -> {
			EntityIterable dps = txn.find(DATAPOINT, CODE, code).intersect(txn
					.find(DATAPOINT, EPOCH_SECONDS, startEpochSeconds, endEpochSeconds));
			List<TimeDouble> result = new ArrayList<>();

			for (Entity entity : dps) {
				Comparable<?> value = entity.getProperty(property);
				if (value != null) {
					long epochSeconds = (long) entity.getProperty(EPOCH_SECONDS);
					result.add(ImmutableTimeDouble.of(epochSeconds, (Double) value));
				}
			}

			return result;
		});
	}

	public PersistentEntityStore getPersistentEntityStore() {
		return this.persistentEntityStore;
	}

}
