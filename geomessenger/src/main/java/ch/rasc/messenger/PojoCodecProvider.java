package ch.rasc.messenger;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import ch.rasc.messenger.domain.Message;
import ch.rasc.messenger.domain.MessageCodec;
import ch.rasc.messenger.domain.UUIDStringGenerator;
import ch.rasc.messenger.domain.User;
import ch.rasc.messenger.domain.UserCodec;

public final class PojoCodecProvider implements CodecProvider {
	private final UUIDStringGenerator uUIDStringGenerator;

	public PojoCodecProvider() {
		this(new UUIDStringGenerator());
	}

	public PojoCodecProvider(final UUIDStringGenerator uUIDStringGenerator) {
		this.uUIDStringGenerator = uUIDStringGenerator;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Codec<T> get(final Class<T> clazz, final CodecRegistry registry) {
		if (clazz.equals(Message.class)) {
			return (Codec<T>) new MessageCodec(registry, this.uUIDStringGenerator);
		}
		if (clazz.equals(User.class)) {
			return (Codec<T>) new UserCodec(registry, this.uUIDStringGenerator);
		}
		return null;
	}
}
