package ch.rasc.changelog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class SystemLogService {
	private final static Logger logger = LoggerFactory.getLogger(LogService.class);

	@ExtDirectMethod
	public void debug(String msg) {
		logger.debug(msg);
	}

	@ExtDirectMethod
	public void info(String msg) {
		logger.info(msg);
	}

	@ExtDirectMethod
	public void warn(String msg) {
		logger.warn(msg);
	}

	@ExtDirectMethod
	public void error(String msg) {
		logger.error(msg);
	}
}