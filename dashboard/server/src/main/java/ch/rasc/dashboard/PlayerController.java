package ch.rasc.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.rasc.sse.eventbus.SseEvent;
import ch.rasc.sse.eventbus.SseEventBus;

@RestController
public class PlayerController {
	private final SseEventBus eventBus;

	private final PlayerDb playerDb;

	public PlayerController(PlayerDb playerDb, SseEventBus eventBus) {
		this.playerDb = playerDb;
		this.eventBus = eventBus;
	}

	@GetMapping("/spieler")
	@CrossOrigin
	public List<List<Player>> getSpieler() {
		List<List<Player>> players = new ArrayList<>();
		players.add(this.playerDb.getRegular());
		players.add(this.playerDb.getUeberzaehlige());
		return players;
	}

	@GetMapping("/clear")
	@CrossOrigin
	public void clear() {
		this.playerDb.clear();
		this.eventBus.handleEvent(SseEvent.ofEvent("clear"));
	}

	@PostMapping("/postPunkte")
	@CrossOrigin
	public void postPunkte(@RequestBody PunktEvent pe) {
		this.playerDb.update(pe);
		this.eventBus.handleEvent(SseEvent.ofData(pe));
	}

}
