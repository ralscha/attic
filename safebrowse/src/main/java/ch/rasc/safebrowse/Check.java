package ch.rasc.safebrowse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Check {

	public static void main(String[] args)
			throws URISyntaxException, ClientProtocolException, IOException {

		URL myJarLocationURL = Check.class.getProtectionDomain().getCodeSource()
				.getLocation();
		Path myJar = Paths.get(myJarLocationURL.toURI());
		Path myJarDir = myJar.getParent();
		Path checkUrlsFile = myJarDir.resolve("checkurls.txt");
		if (!Files.exists(checkUrlsFile)) {
			checkUrlsFile = Paths.get(".", "checkurls.txt");
		}
		List<String> urls = Files.readAllLines(checkUrlsFile, StandardCharsets.UTF_8);

		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("sb-ssl.google.com")
				.setPath("/safebrowsing/api/lookup").setParameter("client", "simplecli")
				.setParameter("apikey", args[0]).setParameter("appver", "1.0.0")
				.setParameter("pver", "3.0");
		URI uri = builder.build();
		HttpPost httppost = new HttpPost(uri);

		StringBuilder sb = new StringBuilder();

		sb.append(urls.size()).append("\n");
		for (String url : urls) {
			sb.append(url).append("\n");
		}

		httppost.setEntity(new StringEntity(sb.toString(), ContentType.TEXT_PLAIN));

		StringBuilder result = new StringBuilder();
		try (CloseableHttpClient client = HttpClientBuilder.create().build();
				CloseableHttpResponse response = client.execute(httppost)) {
			int status = response.getStatusLine().getStatusCode();

			switch (status) {
			case 200:

				result.append(
						"AT LEAST ONE of the queried URLs are matched in either the phishing or malware lists");
				result.append("\n");
				result.append("\n");
				String responseBody = EntityUtils.toString(response.getEntity());
				List<String> results = Arrays.asList(responseBody.split("\n"));
				for (int i = 0; i < urls.size(); i++) {
					result.append(
							String.format("%-30s --> %s", urls.get(i), results.get(i)));
					result.append("\n");
				}
				break;
			case 204:
				System.out.println(
						"NONE of the queried URLs matched the phishing or malware lists");
				break;
			case 400:
				result.append("Bad Request — The HTTP request was not correctly formed");
				break;
			case 401:
				result.append("Not Authorized — The apikey is not authorized");
				break;
			case 503:
				result.append("Service Unavailable");
				break;
			default:
				result.append("Status Code: " + status);
			}
		}

		if (result.length() > 0) {

			String host = args[1];
			String from = args[2];
			String to = args[3];

			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", host);
			Session session = Session.getDefaultInstance(properties);

			try {
				MimeMessage message = new MimeMessage(session);

				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				message.setSubject("ALARM: Google Safe Browsing");

				message.setText(result.toString());

				// Send message
				Transport.send(message);
			}
			catch (MessagingException mex) {
				mex.printStackTrace();
			}
		}

	}

}
