package xml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.annotations.FromAnnotationsRuleModule;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.xml.sax.SAXException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main {

	public static final MediaType MEDIA_TYPE_ATOM_XML = MediaType
			.parse("application/atom+xml;vnd.sage=sdata");

	public static final MediaType MEDIA_TYPE_ATOM_ENTRY = MediaType
			.parse("application/atom+xml; type=entry");

	public static void main(String[] args) throws IOException, SAXException {
		// System.setProperty("http.proxyHost", "127.0.0.1");
		// System.setProperty("http.proxyPort", "8888");

		String url = "http://10.27.0.114:5493/sdata/Sage50/Sage50/SageDemo14/Adressen";
		String format = "?format=application/atom+xml;vnd.sage=sdata";

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url + format).get().build();
		Response response = client.newCall(request).execute();
		String content = response.body().string();

		// System.out.println(content);
		char firstChar = content.charAt(0);
		if (firstChar == '\ufeff') {
			content = content.substring(1);
		}

		FromAnnotationsRuleModule module = new FromAnnotationsRuleModule() {
			@Override
			protected void configureRules() {
				forPattern("feed").createObject().ofType(ArrayList.class);
				bindRulesFrom(Adresse.class);
				forPattern("feed/entry/sdata:payload/Adresse").setNext("add");
			}
		};

		DigesterLoader loader = DigesterLoader.newLoader(module);
		Digester digester = loader.newDigester();

		List<Adresse> a = digester.parse(new StringReader(content));
		System.out.println(a);

		StringBuilder out = readResource("/update.xml");
		RequestBody body = RequestBody.create(MEDIA_TYPE_ATOM_ENTRY, out.toString());
		request = new Request.Builder()
				.url("http://10.27.0.114:5493/sdata/Sage50/Sage50/SageDemo14/Adressen('10000')")
				.put(body).build();
		response = client.newCall(request).execute();
		content = response.body().string();
		System.out.println("UPDATE");
		System.out.println(content);

		out = readResource("/insert.xml");
		body = RequestBody.create(MEDIA_TYPE_ATOM_ENTRY, out.toString());
		request = new Request.Builder()
				.url("http://10.27.0.114:5493/sdata/Sage50/Sage50/SageDemo14/Adressen")
				.post(body).build();
		response = client.newCall(request).execute();
		content = response.body().string();
		System.out.println("INSERT");
		System.out.println(content);

	}

	private static StringBuilder readResource(String resource) throws IOException {
		StringBuilder out = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(
				Main.class.getResourceAsStream(resource), StandardCharsets.UTF_8);
		char[] buffer = new char[1024];
		int bytesRead = -1;
		while ((bytesRead = reader.read(buffer)) != -1) {
			out.append(buffer, 0, bytesRead);
		}
		return out;
	}

}
