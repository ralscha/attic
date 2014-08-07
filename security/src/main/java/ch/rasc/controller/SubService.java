package ch.rasc.controller;

import java.util.Date;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@Secured("ROLE_ADMIN")
public class SubService extends SuperService {

	public Date callSubMethod() {
		return new Date();
	}
}
