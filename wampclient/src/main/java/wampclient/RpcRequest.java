package wampclient;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class RpcRequest {
	String[] args;
	Map<String, ? extends JsonNode> kwargs;
}
