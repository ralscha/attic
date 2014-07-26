package ch.rasc.e4ds.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;

import com.google.common.collect.ImmutableList;

@Document
public class LoggingEvent {

	@Id
	private String id;

	private DateTime timeStamp;

	private String message;

	private String formattedMessage;

	private String level;

	private String caller;

	private String ip;

	private String userName;

	private List<String> stacktrace = Collections.emptyList();

	public LoggingEvent() {
	}

	public LoggingEvent(ILoggingEvent eventObject) {
		this.timeStamp = new DateTime(eventObject.getTimeStamp());
		this.message = eventObject.getMessage();
		this.formattedMessage = eventObject.getFormattedMessage();
		this.level = eventObject.getLevel().toString();

		Map<String, String> mdc = eventObject.getMDCPropertyMap();
		if (mdc != null) {
			this.ip = mdc.get("ip");
			this.userName = mdc.get("userName");
		}

		StackTraceElement st = eventObject.getCallerData()[0];
		if (st != null) {
			caller = String.format("%s.%s:%d", st.getClassName(), st.getMethodName(), st.getLineNumber());
		}

		if (eventObject.getThrowableProxy() != null) {
			ImmutableList.Builder<String> listBuilder = ImmutableList.builder();

			IThrowableProxy tp = eventObject.getThrowableProxy();

			StringBuilder buf = new StringBuilder();
			ThrowableProxyUtil.subjoinFirstLine(buf, tp);
			listBuilder.add(buf.toString());

			int commonFrames = tp.getCommonFrames();
			StackTraceElementProxy[] stepArray = tp.getStackTraceElementProxyArray();
			for (int i = 0; i < stepArray.length - commonFrames; i++) {
				StringBuilder sb = new StringBuilder();
				sb.append(CoreConstants.TAB);
				ThrowableProxyUtil.subjoinSTEP(sb, stepArray[i]);
				listBuilder.add(sb.toString());
			}

			if (commonFrames > 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(CoreConstants.TAB).append("... ").append(commonFrames).append(" common frames omitted");
				listBuilder.add(sb.toString());
			}

			stacktrace = listBuilder.build();
		}

	}

	public DateTime getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getLevel() {
		return level;
	}

	public String getIp() {
		return ip;
	}

	public String getUserName() {
		return userName;
	}

	public String getId() {
		return id;
	}

	public String getFormattedMessage() {
		return formattedMessage;
	}

	public String getCaller() {
		return caller;
	}

	public List<String> getStacktrace() {
		return stacktrace;
	}

}
