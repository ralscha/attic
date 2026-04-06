package ch.rasc.retrofit;

import java.util.Optional;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
public interface PushoverMessage {

	String token();

	String user();

	String message();

	Optional<String> device();

	@Nullable
	String title();

	@Nullable
	String url();

	@Nullable
	String url_title();

	@Nullable
	Integer priority();

	@Nullable
	Long timestamp();

	@Nullable
	String sound();

}