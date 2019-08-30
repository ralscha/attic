package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.POLL;

import java.util.Arrays;
import java.util.Random;

import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class PollService {

	private final static Random random = new Random();

	@ExtDirectMethod(value = POLL, event = "chart")
	public int[] currentTime() {
		int[] result = new int[12];
		Arrays.setAll(result, i -> random.nextInt(200));
		return result;
	}

}