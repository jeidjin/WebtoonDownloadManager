package com.nodestory.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;

public class ApplicationAPI {

	@SuppressWarnings("unchecked")
	public Map<String, String> appCheck(String apiKey) {
		URL url = null;
		URLConnection connection = null;
		StringBuilder responseBody = new StringBuilder();
		Map<String, String> resultMap = null;
		try {
			// url = new URL("Your Server Checks");
			url = new URL("http://nodestory.com/api/v0.1/appVersionCheck");
			connection = url.openConnection();
			connection.addRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			connection.setDoInput(true);

			JSONObject jsonBody = new JSONObject();
			jsonBody.put("apiKey", apiKey);

			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
			bos.write(jsonBody.toJSONString().getBytes());
			bos.flush();
			bos.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				responseBody.append(line);
			}
			br.close();

			ObjectMapper om = new ObjectMapper();
			resultMap = om.readValue(responseBody.toString(), new TypeReference<Map<String, Object>>() {
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

}
