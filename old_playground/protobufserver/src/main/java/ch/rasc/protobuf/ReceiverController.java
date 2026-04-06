package ch.rasc.protobuf;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.InvalidProtocolBufferException;

import ch.rasc.protobuf.SensorDataOuterClass.SensorData;

@RestController
public class ReceiverController {

	@PostMapping("/receiver")
	public void receiver(@RequestBody byte[] data) throws InvalidProtocolBufferException {
		SensorData sd = SensorData.parseFrom(data);
		System.out.println(sd.getId());
		System.out.println(sd.getTx());
		System.out.println(sd.getRx());
	}

}
