package ch.rasc.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

interface HttpBinService {
	@GET("/ip")
	Call<Ip> getIp();
}