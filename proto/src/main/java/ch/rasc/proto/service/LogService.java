package ch.rasc.proto.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class LogService {

	private final static Logger logger = LoggerFactory.getLogger(LogService.class);

	@ExtDirectMethod
	@Async
	public void debug(String msg) {
		logger.debug(msg);
	}

	@ExtDirectMethod
	@Async
	public void info(String msg) {
		logger.info(msg);
	}

	@ExtDirectMethod
	@Async
	public void warn(String msg) {
		logger.warn(msg);
	}

	@ExtDirectMethod
	@Async
	public void error(String msg) {
		logger.error(msg);
	}

}
