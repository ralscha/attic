package ch.rasc.portaldemos.chat;

import com.github.flowersinthesand.portal.Bean;
import com.github.flowersinthesand.portal.Data;
import com.github.flowersinthesand.portal.On;
import com.github.flowersinthesand.portal.Room;
import com.github.flowersinthesand.portal.Wire;

@Bean
public class ChatHandler {

	@Wire
	private Room hall;

	@On
	public void message(@Data ChatMessage message) {
		hall.send("message", message);
	}

}
