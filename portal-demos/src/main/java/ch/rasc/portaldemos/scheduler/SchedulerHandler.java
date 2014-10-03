package ch.rasc.portaldemos.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.flowersinthesand.portal.Bean;
import com.github.flowersinthesand.portal.Data;
import com.github.flowersinthesand.portal.On;
import com.github.flowersinthesand.portal.Room;
import com.github.flowersinthesand.portal.Socket;
import com.github.flowersinthesand.portal.Wire;
import com.google.common.collect.ImmutableMap;

@Bean
public class SchedulerHandler {

	private final static ObjectMapper mapper = new ObjectMapper();

	@Wire
	Room hall;

	@On
	public void client_doInitialLoad(Socket socket) {
		socket.send("server_doInitialLoad", ImmutableMap.of("data", CustomEventDb.list()));
	}

	@On
	public void client_doUpdate(Socket socket, @Data CustomEvent record) {
		CustomEventDb.update(record);
		hall.out(socket).send("server_doUpdate", record);
	}

	@On
	public void client_doAdd(Socket socket, @Data List<Map<String, Object>> records) {
		List<Object> updatedRecords = new ArrayList<>();
		List<ImmutableMap<String, ?>> ids = new ArrayList<>();

		for (Map<String, Object> r : records) {
			Map<String, Object> record = (Map<String, Object>) r.get("data");
			String internalId = (String) r.get("internalId");

			CustomEvent event = mapper.convertValue(record, CustomEvent.class);
			CustomEventDb.create(event);
			updatedRecords.add(event);

			ids.add(ImmutableMap.of("internalId", internalId, "record", event));
		}

		hall.out(socket).send("server_doAdd", ImmutableMap.of("records", updatedRecords));
		socket.send("server_syncId", ImmutableMap.of("records", ids));
	}

	@On
	public void client_doRemove(Socket socket, @Data("ids") List<Integer> ids) {
		CustomEventDb.delete(ids);

		hall.out(socket).send("server_doRemove", ImmutableMap.of("ids", ids));
	}

}
