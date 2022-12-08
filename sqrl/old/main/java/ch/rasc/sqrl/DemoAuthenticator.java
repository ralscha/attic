package ch.rasc.sqrl;

import org.springframework.stereotype.Component;

import ch.rasc.sqrl.auth.SqrlIdentity;

@Component
public class DemoAuthenticator implements Authenticator {

	private final AppProperties appProperties;

	public DemoAuthenticator(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	@Override
	public String authenticateIdentity(SqrlIdentity identity) {
		return this.appProperties.getAppUrl() + "/success.html?idk=" + identity.getIdk()
				+ "&btn=" + identity.getBtn();
	}

	@Override
	public void swapIdentities(SqrlIdentity previousIdentity, SqrlIdentity newIdentity) {

	}

	@Override
	public void removeIdentity(SqrlIdentity identity) {

	}

	@Override
	public Ask askResponse(SqrlIdentity identity) {
		Ask ask = new Ask();
		ask.setMessage("Do you really really want to login here?");
		ask.setButton1("Sure");
		ask.setButton2("Nope");
		return ask;
	}

}
