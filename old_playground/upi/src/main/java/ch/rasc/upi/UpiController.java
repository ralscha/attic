package ch.rasc.upi;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ch.admin.ws.zas.regcent.upi._0.UPIQueryServicePortType;
import ch.ech.xmlns.ech_0044._1.DatePartiallyKnownType;
import ch.ech.xmlns.ech_0044._1.NamedPersonIdType;
import ch.ech.xmlns.ech_0084._1.PlaceOfBirthType;
import ch.ech.xmlns.ech_0084._1.PlaceOfBirthType.SwissTown;
import ch.ech.xmlns.ech_0085._1.GetAhvvnRequestType;
import ch.ech.xmlns.ech_0085._1.GetAhvvnResponseType;
import ch.ech.xmlns.ech_0085._1.PersonIdentificationType;
import ch.ech.xmlns.ech_0085._1.SearchPersonRequestType;
import ch.ech.xmlns.ech_0085._1.SearchPersonRequestType.Nationality;
import ch.ech.xmlns.ech_0085._1.SearchPersonResponseType;

@RestController
public class UpiController {

	private final UPIQueryServicePortType upiQueryServicePortType;

	public UpiController(UPIQueryServicePortType upiQueryServicePortType) {
		this.upiQueryServicePortType = upiQueryServicePortType;
	}

	@GetMapping("getAhvvn/{ahv}")
	public AhvvnResponse getAhvvn(@PathVariable("ahv") String ahv) {
		GetAhvvnRequestType req = new GetAhvvnRequestType();
		PersonIdentificationType pi = new PersonIdentificationType();

		NamedPersonIdType n = new NamedPersonIdType();
		n.setPersonId(ahv);
		n.setPersonIdCategory("CH.AHV");
		pi.setLocalPersonId(n);

		req.setPersonIdentification(pi);

		GetAhvvnResponseType res = this.upiQueryServicePortType.getAhvvn(req);
		if (res.getAccepted() != null) {
			AhvvnResponse response = new AhvvnResponse();
			response.setLatestAhvvn(res.getAccepted().getLatestAhvvn());
			return response;
		}

		if (res.getRefused() != null) {
			AhvvnResponse response = new AhvvnResponse();
			response.setRefusedDetailedReason(res.getRefused().getDetailedReason());
			response.setRefusedReason(res.getRefused().getReason());
			return response;
		}

		return null;
	}

	@GetMapping("search")
	public String listOfSearchPerson() throws DatatypeConfigurationException {
		// Namen, Vornamen, das Geburtsdatum, die Natio-
		// nalität sowie den Geburtsort umfassen

		SearchPersonRequestType search = new SearchPersonRequestType();
		Nationality nationality = new Nationality();
		nationality.setCountryId(8100);
		nationality.setNationalityStatus("2");
		search.setNationality(nationality);
		search.setFirstNames("Ralph");
		search.setOfficialName("Schär");
		DatePartiallyKnownType dob = new DatePartiallyKnownType();
		GregorianCalendar c = new GregorianCalendar(1971, Calendar.JUNE, 23);
		XMLGregorianCalendar gc = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c);
		dob.setYearMonthDay(gc);
		search.setDateOfBirth(dob);

		PlaceOfBirthType pob = new PlaceOfBirthType();
		SwissTown st = new SwissTown();
		st.setMunicipalityName("Biel");
		pob.setSwissTown(st);
		search.setPlaceOfBirth(pob);

		SearchPersonResponseType response = this.upiQueryServicePortType
				.searchPerson(search);
		if (response.getAccepted() != null) {
			System.out.println(response.getAccepted().getFound().getAhvvn());
		}
		if (response.getRefused() != null) {
			System.out.println(response.getRefused().getDetailedReason());
			System.out.println(response.getRefused().getReason());
		}
		return null;
	}

}
