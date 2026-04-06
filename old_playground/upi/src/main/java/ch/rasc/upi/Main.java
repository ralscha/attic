package ch.rasc.upi;

import java.net.MalformedURLException;

import javax.xml.ws.BindingProvider;

import ch.admin.ws.zas.regcent.upi._0.UPIQueryService;
import ch.admin.ws.zas.regcent.upi._0.UPIQueryServicePortType;
import ch.ech.xmlns.ech_0044._1.NamedPersonIdType;
import ch.ech.xmlns.ech_0085._1.GetAhvvnRequestType;
import ch.ech.xmlns.ech_0085._1.GetAhvvnResponseType;
import ch.ech.xmlns.ech_0085._1.PersonIdentificationType;

public class Main {

	public static void main(String[] args) throws MalformedURLException {
		new Main().start();
	}

	private void start() throws MalformedURLException {
		GetAhvvnRequestType req = new GetAhvvnRequestType();
		PersonIdentificationType pi = new PersonIdentificationType();

		NamedPersonIdType n = new NamedPersonIdType();
		n.setPersonId("804.71.285.114");
		n.setPersonIdCategory("CH.AHV");
		pi.setLocalPersonId(n);

		req.setPersonIdentification(pi);

		GetAhvvnResponseType res = getQueryService().getAhvvn(req);
		if (res.getAccepted() != null) {
			System.out.println(res.getAccepted().getLatestAhvvn());
		}

		if (res.getRefused() != null) {
			System.out.println(res.getRefused().getDetailedReason());
			System.out.println(res.getRefused().getReason());
		}

		// ListOfSearchPersonRequest req = new ListOfSearchPersonRequest();
		// Item item = new Item();
		// SearchPersonRequestType search = new SearchPersonRequestType();
		// Nationality nat = new Nationality();
		// nat.setCountryId(1);
		// search.setNationality(nat);
		// search.setFirstNames("Ralph");
		// search.setOfficialName("Schär");
		// search.setOriginalName("Müller");
		// search.setSex("m");
		// item.setSearchPersonRequest(search);
		// req.getItem().add(item);
		// ListOfSearchPersonResponse response =
		// getQueryService().listOfSearchPerson(req);
		// System.out.println(response.getItem());
		// System.out.println(response.getAllRefused().getDetailedReason());
		// System.out.println(response.getAllRefused().getReason());
	}

	private UPIQueryServicePortType getQueryService() throws MalformedURLException {

		UPIQueryService upiQueryService = new UPIQueryService();

		UPIQueryServicePortType portType = upiQueryService.getUPIQueryServicePort();

		((BindingProvider) portType).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				"https://www.wupi-test.zas.admin.ch/wupi/UPIQueryService");
		((BindingProvider) portType).getRequestContext()
				.put(BindingProvider.USERNAME_PROPERTY, "ZUP17263");
		((BindingProvider) portType).getRequestContext()
				.put(BindingProvider.PASSWORD_PROPERTY, "079Om3SO");

		return portType;
	}

}
