package ch.rasc.proto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
@Lazy
public class ConfigurationurationService {

	private final MailService mailService;

	@Autowired
	public ConfigurationurationService(MailService mailService) {
		this.mailService = mailService;
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	public void sendTestEmail(String to) {
		this.mailService.sendSimpleMessage(to, "TEST EMAIL", "THIS IS A TEST MESSAGE");
	}

}
