package org.immutables.samples.json.pojo;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;

public class OptionalJsonAdapterFactory implements JsonAdapter.Factory {

	@Override
	public JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations,
			Moshi moshi) {
		if (type instanceof ParameterizedType
				&& Optional.class == ((ParameterizedType) type).getRawType()) {
			return new OptionalAdapter(moshi,
					((ParameterizedType) type).getActualTypeArguments()[0]);
		}
		return null;
	}

	private static class OptionalAdapter extends JsonAdapter<Optional<?>> {
		private JsonAdapter<Object> adapter;
		private final Moshi moshi;
		private final Type type;

		public OptionalAdapter(Moshi moshi, Type type) {
			this.moshi = moshi;
			this.type = type;
		}

		@Override
		public void toJson(JsonWriter out, @Nullable Optional<?> value)
				throws IOException {
			if (value != null && value.isPresent()) {
				if (this.adapter == null) {
					this.adapter = this.moshi.adapter(this.type);
				}
				this.adapter.toJson(out, value.get());
			}
			else {
				out.nullValue();
			}
		}

		@Override
		public Optional<?> fromJson(JsonReader in) throws IOException {
			if (JsonReader.Token.NULL == in.peek()) {
				return Optional.absent();
			}
			if (this.adapter == null) {
				this.adapter = this.moshi.adapter(this.type);
			}
			return Optional.of(this.adapter.fromJson(in));
		}
	}
}
