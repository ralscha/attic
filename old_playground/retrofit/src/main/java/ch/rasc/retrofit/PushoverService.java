package ch.rasc.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PushoverService {

	@POST("/1/messages.json")
	@FormUrlEncoded
	Call<PushoverResponse> sendMessage(@FieldMap Map<String, Object> fields);

}
