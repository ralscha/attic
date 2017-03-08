package ch.rasc.sse.config;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

public class SSEventMessageConverter implements HttpMessageConverter<Object> {

	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	private static final MediaType EVENT_STREAM_MEDIATYPE = new MediaType("text",
			"event-stream", UTF8_CHARSET);

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return (String.class.equals(clazz) || SSEvent.class.equals(clazz))
				&& EVENT_STREAM_MEDIATYPE.includes(mediaType);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(EVENT_STREAM_MEDIATYPE);
	}

	@Override
	public Object read(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		throw new UnsupportedOperationException("read not supported");
	}

	@Override
	public void write(Object t, MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		HttpHeaders headers = outputMessage.getHeaders();
		headers.setContentType(EVENT_STREAM_MEDIATYPE);

		StringBuilder sb = new StringBuilder(32);
		if (t instanceof SSEvent) {
			SSEvent event = (SSEvent) t;
			if (StringUtils.hasText(event.getComment())) {
				sb.append(":").append(event.getComment()).append("\n");
			}
			if (StringUtils.hasText(event.getId())) {
				sb.append("id:").append(event.getId()).append("\n");
			}
			if (StringUtils.hasText(event.getEvent())) {
				sb.append("event:").append(event.getEvent()).append("\n");
			}
			if (StringUtils.hasText(event.getData())) {
				sb.append("data:").append(event.getData()).append("\n");
			}
			if (event.getRetry() != null) {
				sb.append("retry:").append(event.getRetry()).append("\n");
			}
		}
		else {
			sb.append("data:").append(t).append("\n");
		}

		sb.append("\n");
		FileCopyUtils.copy(sb.toString(),
				new OutputStreamWriter(outputMessage.getBody(), UTF8_CHARSET));
	}

}
