package ch.rasc.messenger;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import ch.rasc.messenger.domain.Location;

public final class LocationCodec implements Codec<Location> {
	@Override
	public void encode(BsonWriter writer, Location value, EncoderContext encoderContext) {
		writer.writeStartArray();
		writer.writeDouble(value.getLongitude());
		writer.writeDouble(value.getLatitude());
		writer.writeEndArray();
	}

	@Override
	public Location decode(BsonReader reader, DecoderContext decoderContext) {
		Location value = new Location();
		reader.readStartArray();
		value.setLongitude(reader.readDouble());
		value.setLatitude(reader.readDouble());
		reader.readEndArray();
		return value;
	}

	@Override
	public Class<Location> getEncoderClass() {
		return Location.class;
	}
}
