package ch.rasc.golb.config;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import ch.rasc.golb.entity.Binary;
import ch.rasc.golb.entity.BinaryCodec;
import ch.rasc.golb.entity.Feedback;
import ch.rasc.golb.entity.FeedbackCodec;
import ch.rasc.golb.entity.PersistentLogin;
import ch.rasc.golb.entity.PersistentLoginCodec;
import ch.rasc.golb.entity.Post;
import ch.rasc.golb.entity.PostCodec;
import ch.rasc.golb.entity.Tag;
import ch.rasc.golb.entity.TagCodec;
import ch.rasc.golb.entity.UUIDStringGenerator;
import ch.rasc.golb.entity.User;
import ch.rasc.golb.entity.UserCodec;

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
		if (clazz.equals(PersistentLogin.class)) {
			return (Codec<T>) new PersistentLoginCodec();
		}
		else if (clazz.equals(User.class)) {
			return (Codec<T>) new UserCodec(this.uUIDStringGenerator);
		}
		else if (clazz.equals(Post.class)) {
			return (Codec<T>) new PostCodec(this.uUIDStringGenerator);
		}
		else if (clazz.equals(Feedback.class)) {
			return (Codec<T>) new FeedbackCodec(this.uUIDStringGenerator);
		}
		else if (clazz.equals(Binary.class)) {
			return (Codec<T>) new BinaryCodec(this.uUIDStringGenerator);
		}
		else if (clazz.equals(Tag.class)) {
			return (Codec<T>) new TagCodec(this.uUIDStringGenerator);
		}
		return null;
	}
}
