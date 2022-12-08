package ch.rasc.sqrldemo.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ch.rasc.sqrl.SqrlManager;
import ch.rasc.sqrl.SqrlRequest;
import ch.rasc.sqrl.SqrlResponse;
import ch.rasc.sqrl.cache.NutCacheEntry;
import ch.rasc.sse.eventbus.SseEvent;
import ch.rasc.sse.eventbus.SseEventBus;

@RestController
public class AuthController {

	private final SqrlManager sqrlManager;

	private final SseEventBus sseEventBus;

	public AuthController(SqrlManager sqrlManager, SseEventBus sseEventBus) {
		this.sqrlManager = sqrlManager;
		this.sseEventBus = sseEventBus;
	}

	@GetMapping("/authenticate")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void authenticate() {
		// nothing here
	}

	@GetMapping("/sqrl-nut")
	public Map<String, String> getSqrlNut(HttpServletRequest request) {
		NutCacheEntry entry = this.sqrlManager.createAndSaveNut(request.getRemoteAddr());
		return Map.of("nut", entry.getNut(), "pollingNut", entry.getPollingNut());
	}

	@GetMapping("/sqrl-polling/{pollingNut}")
	public SseEmitter sqrlPolling(
			@PathVariable("pollingNut") String pollingNut) {
		return this.sseEventBus.createSseEmitter(pollingNut, SseEvent.DEFAULT_EVENT);
	}

	@PostMapping("/sqrl")
	public void sqrl(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(name = "nut", required = false) String nut,
			@RequestParam(name = "client", required = false) String client,
			@RequestParam(name = "server", required = false) String server,
			@RequestParam(name = "ids", required = false) String ids,
			@RequestParam(name = "pids", required = false) String pids,
			@RequestParam(name = "urs", required = false) String urs) throws IOException {

		SqrlRequest sqrlRequest = new SqrlRequest(request.getRemoteAddr(), nut, client, server, ids, pids, urs);
		SqrlResponse sqrlResponse = this.sqrlManager.handleRequest(sqrlRequest);
System.out.println(sqrlResponse.getSqrlClientParameters());
System.out.println(sqrlResponse.getSqrlServerParameters());
System.out.println(sqrlResponse.getPollingNut());
		if (sqrlResponse.getPollingNut() != null) {
			this.sseEventBus.handleEvent(SseEvent.builder()
					.addClientId(sqrlResponse.getPollingNut())
					.data("OK").build());
		}
		response.getWriter().write(sqrlResponse.getSqrlServerParametersEncoded());
	}
}
