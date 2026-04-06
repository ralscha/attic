package ch.rasc.session.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.rasc.session.entity.Address;
import ch.rasc.session.repository.AddressRepository;

@RestController
public class AddressService {

	private final LoginManager loginManager;

	private final AddressRepository addressRepository;

	@Autowired
	public AddressService(LoginManager loginManager,
			AddressRepository addressRepository) {
		this.loginManager = loginManager;
		this.addressRepository = addressRepository;
	}

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<Address> findAll(@RequestParam("sessionid") String sessionid,
			HttpServletResponse response) {

		if (this.loginManager.isSessionValid(sessionid)) {
			return this.addressRepository.findAll();
		}

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}

	@RequestMapping(value = "/findOne", method = RequestMethod.GET)
	public Address findOne(@RequestParam("sessionid") String sessionid,
			@RequestParam("id") String id, HttpServletResponse response) {

		if (this.loginManager.isSessionValid(sessionid)) {
			return this.addressRepository.findOne(Long.valueOf(id));
		}

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}

}
