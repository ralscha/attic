package wampclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DialogMediaProperties {
	@JsonProperty("DialogData4")
	private String dialogData4;

	@JsonProperty("DialogData5")
	private String dialogData5;

	@JsonProperty("callvariables")
	private DialogCallVariables callVariables;

	public String getDialogData4() {
		return this.dialogData4;
	}

	public void setDialogData4(String dialogData4) {
		this.dialogData4 = dialogData4;
	}

	public String getDialogData5() {
		return this.dialogData5;
	}

	public void setDialogData5(String dialogData5) {
		this.dialogData5 = dialogData5;
	}

	public DialogCallVariables getCallVariables() {
		return this.callVariables;
	}

	public void setCallVariables(DialogCallVariables callVariables) {
		this.callVariables = callVariables;
	}

}
