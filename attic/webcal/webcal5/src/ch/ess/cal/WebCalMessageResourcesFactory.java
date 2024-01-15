package ch.ess.cal;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

public class WebCalMessageResourcesFactory extends MessageResourcesFactory {

	private static final long serialVersionUID = 1L;

	@Override
	public MessageResources createResources(@SuppressWarnings("hiding") final String config) {
		WebCalMessageResources messageResources = new WebCalMessageResources(this, config, this.returnNull);
		String mode = null;
		if (getConfig() != null) {
			mode = getConfig().getProperty("mode");
		}
		messageResources.setMode(mode);
		return messageResources;
	}
}
