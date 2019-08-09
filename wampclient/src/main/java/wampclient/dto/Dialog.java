package wampclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dialog {
	@JsonProperty("DialogData1")
	private int dialogData1;

	@JsonProperty("DialogData2")
	private String dialogData2;

	@JsonProperty("DialogData3")
	private String dialogData3;

	private DialogMediaProperties mediaProperties;

	public int getDialogData1() {
		return this.dialogData1;
	}

	public void setDialogData1(int dialogData1) {
		this.dialogData1 = dialogData1;
	}

	public String getDialogData2() {
		return this.dialogData2;
	}

	public void setDialogData2(String dialogData2) {
		this.dialogData2 = dialogData2;
	}

	public String getDialogData3() {
		return this.dialogData3;
	}

	public void setDialogData3(String dialogData3) {
		this.dialogData3 = dialogData3;
	}

	public DialogMediaProperties getMediaProperties() {
		return this.mediaProperties;
	}

	public void setMediaProperties(DialogMediaProperties mediaProperties) {
		this.mediaProperties = mediaProperties;
	}

}
