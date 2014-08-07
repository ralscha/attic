package ch.rasc.controller;

import java.util.Date;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SubPreService extends SuperService {

	public Date callSubMethod() {
		return new Date();
	}
}
