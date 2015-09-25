#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity;

public enum ConfigurationKey {
	SMTP_SENDER("sender"), SMTP_SERVER("server"), SMTP_PORT("port"), SMTP_USERNAME(
			"username"), SMTP_PASSWORD("password"), LOG_LEVEL("logLevel");

	private final String jsonName;

	private ConfigurationKey(String jsonName) {
		this.jsonName = jsonName;
	}

	public String getJsonName() {
		return jsonName;
	}

}
