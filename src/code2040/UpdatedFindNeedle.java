package code2040;

import java.io.*;
import java.util.ArrayList;
import org.json.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class UpdatedFindNeedle{

	public static void main(String[] args) throws IOException {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();

		
			json.put("token", "34ee7aec6e614caf7fccecfe4ea2eb89");
			json2.put("token", "34ee7aec6e614caf7fccecfe4ea2eb89");
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();

			//pulling dictionary 
			HttpPost request = new HttpPost("http://challenge.code2040.org/api/haystack");
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			//System.out.println(response.toString());
			String receivedString = EntityUtils.toString(entity);
//			System.out.println(receivedString);

			
			/*
			 * This is an updated approach to solving the same problem of finding the location of a needle within a haystack.
			 * This approach is much cleaner and easier to understand than my last approach because I use the JSON tools instead of parsing through  
			 * each character of the JSON string in order to identify the needle and then create my own array. 
			 */
			
			
			//Create JSONObject to help parse the JSON
			JSONObject obj = new JSONObject(receivedString);
			String needle = obj.getString("needle");
//			System.out.println(needle);
			
			JSONArray array = obj.getJSONArray("haystack");
//			System.out.println(array.get(0));
			int location = -1;
			
			//Linearly searching through the JSONArray to find the needle
			for(int x = 0; x < array.length(); x++){
				if(needle.equals(array.get(x))){
					location = x;
				}
			}
			
//			if(location != -1){
//				System.out.println(location);
//			}
//			else{
//				System.out.println("The needle was not found.");
//			}
			
			

			json2.put("needle" , location);
			HttpPost request2 = new HttpPost("http://challenge.code2040.org/api/haystack/validate");
			StringEntity params2 = new StringEntity(json2.toString());
			request2.addHeader("content-type", "application/json");
			request2.setEntity(params2);
			httpClient.execute(request2);



	}
}
