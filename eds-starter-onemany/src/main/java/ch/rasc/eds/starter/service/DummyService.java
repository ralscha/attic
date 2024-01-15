package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class DummyService {

	@ExtDirectMethod(STORE_READ)
	@PreAuthorize("hasRole('USER')")
	public List<Map<String, Object>> read() {
		Random random = new Random();

		return Arrays.asList("3M Co", "AT&T Inc", "Boeing Co.", "Citigroup, Inc.",
				"Coca-Cola", "General Motors", "IBM", "Intel", "McDonald's", "Microsoft",
				"Verizon", "Wal-Mart").stream().map(name -> {
					Map<String, Object> c = new HashMap<>();
					c.put("name", name);
					c.put("price", (random.nextInt(10000) + 1) / 100.0);
					c.put("revenue", (random.nextInt(10000) + 1) / 100.0);
					c.put("growth", (random.nextInt(10000) + 1) / 100.0);
					c.put("product", (random.nextInt(10000) + 1) / 100.0);
					c.put("market", (random.nextInt(10000) + 1) / 100.0);
					return c;
				}).collect(Collectors.toList());
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	public boolean notAllowedTest() {
		return true;
	}
}
