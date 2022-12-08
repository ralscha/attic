package ch.rasc.sqrl;

public class SqrlResponse {

	private final String pollingNut;

	private final SqrlClientParameters sqrlClientParameters;

	private final SqrlServerParameters sqrlServerParameters;

	private final String sqrlServerParametersEncoded;

	public SqrlResponse(SqrlServerParameters sqrlServerParameters) {
		this.pollingNut = null;
		this.sqrlClientParameters = null;
		this.sqrlServerParameters = sqrlServerParameters;
		this.sqrlServerParametersEncoded = sqrlServerParameters.encode();
	}

	public SqrlResponse(SqrlServerParameters sqrlServerParameters,
			String sqrlServerParametersEncoded) {
		this.pollingNut = null;
		this.sqrlClientParameters = null;
		this.sqrlServerParameters = sqrlServerParameters;
		this.sqrlServerParametersEncoded = sqrlServerParametersEncoded;
	}

	public SqrlResponse(SqrlClientParameters sqrlClientParameters,
			SqrlServerParameters sqrlServerParameters,
			String sqrlServerParametersEncoded) {
		this.pollingNut = null;
		this.sqrlClientParameters = sqrlClientParameters;
		this.sqrlServerParameters = sqrlServerParameters;
		this.sqrlServerParametersEncoded = sqrlServerParametersEncoded;
	}

	public SqrlResponse(String pollingNut, SqrlClientParameters sqrlClientParameters,
			SqrlServerParameters sqrlServerParameters,
			String sqrlServerParametersEncoded) {
		this.pollingNut = pollingNut;
		this.sqrlClientParameters = sqrlClientParameters;
		this.sqrlServerParameters = sqrlServerParameters;
		this.sqrlServerParametersEncoded = sqrlServerParametersEncoded;
	}

	public String getPollingNut() {
		return this.pollingNut;
	}

	public SqrlClientParameters getSqrlClientParameters() {
		return this.sqrlClientParameters;
	}

	public SqrlServerParameters getSqrlServerParameters() {
		return this.sqrlServerParameters;
	}

	public String getSqrlServerParametersEncoded() {
		return this.sqrlServerParametersEncoded;
	}

}
