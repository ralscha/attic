package wampclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DialogResponse {
	@JsonProperty("Dialog")
	private Dialog dialog;

	@JsonProperty("DialogData6")
	private String dialogData6;

	@JsonProperty("DialogData7")
	private String dialogData7;

	@JsonProperty("DialogData8")
	private String dialogData8;

	@JsonProperty("DialogData9")
	private String dialogData9;

	@JsonProperty("DialogData10")
	private String dialogData10;

	public Dialog getDialog() {
		return this.dialog;
	}

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

	public String getDialogData6() {
		return this.dialogData6;
	}

	public void setDialogData6(String dialogData6) {
		this.dialogData6 = dialogData6;
	}

	public String getDialogData7() {
		return this.dialogData7;
	}

	public void setDialogData7(String dialogData7) {
		this.dialogData7 = dialogData7;
	}

	public String getDialogData8() {
		return this.dialogData8;
	}

	public void setDialogData8(String dialogData8) {
		this.dialogData8 = dialogData8;
	}

	public String getDialogData9() {
		return this.dialogData9;
	}

	public void setDialogData9(String dialogData9) {
		this.dialogData9 = dialogData9;
	}

	public String getDialogData10() {
		return this.dialogData10;
	}

	public void setDialogData10(String dialogData10) {
		this.dialogData10 = dialogData10;
	}

}
