package ch.rasc.travellog.service;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import ch.rasc.travellog.config.AppProperties;

@Service
public class TokenService {

	private static final AtomicLong COUNTER = new AtomicLong();

	private static final long RESEED = 100000L;

	private final SecureRandom random;

	private final String instanceNo;

	public TokenService(AppProperties appProperties) {
		this.random = new SecureRandom();
		this.instanceNo = appProperties.getInstanceNo();
	}

	public String createToken() {
		synchronized (this.random) {
			long r0 = this.random.nextLong();

			// random chance to reseed
			if (r0 % RESEED == 1L) {
				this.random.setSeed(this.random.generateSeed(8));
			}

			long r1 = this.random.nextLong();

			long counter = COUNTER.getAndIncrement();

			ByteBuffer bb = ByteBuffer.allocate(24);
			bb.putLong(r0);
			bb.putLong(r1);
			bb.putLong(counter);

			return this.instanceNo
					+ Base64.getUrlEncoder().withoutPadding().encodeToString(bb.array());
		}
	}

}
