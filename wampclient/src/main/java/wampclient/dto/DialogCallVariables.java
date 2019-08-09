package wampclient.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DialogCallVariables {

	@JsonProperty("CallVariable")
	private List<CallVariable> callVariables;

	public List<CallVariable> getCallVariables() {
		return this.callVariables;
	}

	public void setCallVariables(List<CallVariable> callVariables) {
		this.callVariables = callVariables;
	}

}
