package ch.rasc.maildeleter;

import java.util.Properties;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class GmapTest {

	public static void main(String[] args) throws NoSuchProviderException {
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);

		Store store = session.getStore("gimap");
		System.out.println(store);

	}

}
