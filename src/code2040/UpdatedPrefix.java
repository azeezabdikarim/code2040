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

public class UpdatedPrefix{

	public static void main(String[] args) throws IOException {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();

		
			json.put("token", "34ee7aec6e614caf7fccecfe4ea2eb89");
			json2.put("token", "34ee7aec6e614caf7fccecfe4ea2eb89");
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();

			//pulling dictionary 
			HttpPost request = new HttpPost("http://challenge.code2040.org/api/prefix");
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			//System.out.println(response.toString());
			String receivedString = EntityUtils.toString(entity);
			System.out.println(receivedString);
//			System.out.println(receivedString.length());
			
			//Identify the Prefix
			JSONObject obj = new JSONObject(receivedString);
			String prefix = obj.getString("prefix");
			System.out.println(prefix);
			
			//Make Array and response ArrayList
			JSONArray array = obj.getJSONArray("array");
			ArrayList list = new ArrayList();
			
			//Add words without prefix to ArrayList
			for(int x = 0; x < array.length(); x++){
				String s  = (String) array.get(x);
				if(!s.startsWith(prefix)){
					list.add(s);
				}
			}
			
			//Convert ArrayList to normal String Array
			String[] responseArray  = new String[list.size()];
			responseArray = (String[]) list.toArray(responseArray);
			
			/*
			 * I was able to improve this code by using JSONObjects to parse the information rather than manually creating the array and identifying
			 * the prefix by parsing characters. I also improved how I compared the prefix to each string by using string.startsWith rather than
			 * by comparing each individual character. 
			 */
			

			json2.put(" array" , responseArray);
			HttpPost request2 = new HttpPost("http://challenge.code2040.org/api/prefix/validate");
			StringEntity params2 = new 
					StringEntity(json2.toString());
			request2.addHeader("content-type", "application/json");
			request2.setEntity(params2);
			httpClient.execute(request2);
			


	}
}
