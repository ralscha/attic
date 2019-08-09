package wampclient;

public class ApiError {
	public String ErrorType;
	public String ErrorMessage;
	public String ErrorData;

	@Override
	public String toString() {
		return "ApiError [ErrorType=" + this.ErrorType + ", ErrorMessage="
				+ this.ErrorMessage + ", ErrorData=" + this.ErrorData + "]";
	}

}
