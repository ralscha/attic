package ch.rasc.dashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class PlayerDb {

	public Map<Long, Player> regular = new HashMap<>();
	public Map<Long, Player> ueberzaehlig = new HashMap<>();

	PlayerDb() {
		this.regular.put(1L, new Player(1L, 1, "Rodriquez", "Marah", "12711", 1966));
		this.regular.put(2L, new Player(2L, 2, "Vincent", "Ciaran", "1711", 1970));
		this.regular.put(3L, new Player(3L, 3, "Banks", "Halla", "1981", 1969));
		this.regular.put(4L, new Player(4L, 4, "Whitley", "Walter", "12345", 1956));
		this.regular.put(5L, new Player(5L, 5, "Stark", "Shana", "987", 1961));
		this.regular.put(6L, new Player(6L, 6, "Randolf", "John", "1301", 1957));
		this.regular.put(7L, new Player(7L, 7, "Browning", "Paul", "2508", 1965));
		this.regular.put(8L, new Player(8L, 8, "Knapp", "Claire", "3999", 1975));
		this.regular.put(9L, new Player(9L, 9, "Macias", "Steven", "8792", 1959));
		this.regular.put(10L, new Player(10L, 10, "Rush", "Amal", "7861", 1996));
		this.regular.put(11L, new Player(11L, 11, "Salas", "Sasha", "11981", 1979));
		this.regular.put(12L, new Player(12L, 12, "Frederick", "Brynne", "9837", 1978));
		this.regular.put(13L, new Player(13L, 13, "Rodriquez", "Martena", "5411", 1992));
		this.regular.put(14L, new Player(14L, 14, "Baster", "Marah", "1891", 1976));
		this.regular.put(15L, new Player(15L, 15, "Munoz", "Jescie", "761", 1972));
		this.regular.put(16L, new Player(16L, 16, "Dominiquez", "Hammett", "6038", 1959));
		this.regular.put(17L, new Player(17L, 17, "Wilcox", "Talon", "8372", 1996));
		this.regular.put(18L, new Player(18L, 18, "Jarvis", "Asher", "781", 1974));

		this.ueberzaehlig.put(19L, new Player(19L, 19, "Rush", "Claire", "1713", 1990));
	}

	public List<Player> getRegular() {
		List<Player> players = new ArrayList<>(this.regular.values());
		Collections.sort(players);
		return players;
	}

	public List<Player> getUeberzaehlige() {
		List<Player> players = new ArrayList<>(this.ueberzaehlig.values());
		Collections.sort(players);
		return players;
	}

	public void update(PunktEvent pe) {
		Player player = this.regular.get(pe.spieler());
		if (player == null) {
			player = this.ueberzaehlig.get(pe.spieler());
		}

		player.getRies()[pe.ries()] = pe.punkt();
		player.getNr()[pe.ries()] = pe.nr();
	}

	public void clear() {
		this.regular.values().forEach(Player::clear);
		this.ueberzaehlig.values().forEach(Player::clear);
	}

}
