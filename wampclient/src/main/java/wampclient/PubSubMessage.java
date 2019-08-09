package wampclient;

public class PubSubMessage {
	public String messageType;
	public String dateTimeStamp;
	public String extension;
	public String loginName;
	public ReasonCode reasonCode;
	public String reasonCodeId;
	public Roles roles;
	public Settings settings;
	public String state;
	public String stateChangeTime;
	public ApiError apiError;

	@Override
	public String toString() {
		return "PubSubMessage [messageType=" + this.messageType + ", dateTimeStamp="
				+ this.dateTimeStamp + ", extension=" + this.extension + ", loginName="
				+ this.loginName + ", reasonCode=" + this.reasonCode + ", reasonCodeId="
				+ this.reasonCodeId + ", roles=" + this.roles + ", settings="
				+ this.settings + ", state=" + this.state + ", stateChangeTime="
				+ this.stateChangeTime + ", apiError=" + this.apiError + "]";
	}

}
