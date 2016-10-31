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

public class findNeedle{

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
//			System.out.println(receivedString.length());
			
		//Processing the response
			String wordInArray = new String();
			String needle = new String();
			ArrayList haystackList = new ArrayList();
			int position = 0;
			
			//identifying the needle
			for(int x = 11; x < 19; x++){
				needle += receivedString.charAt(x);	
			}
			//System.out.println(needle);
			
			//parsing the string to create an array that represents the haystack
			
			for( int charNum = 31; charNum < receivedString.length(); charNum++){
				if(receivedString.charAt(charNum)=='"'){
					charNum++;
					while(receivedString.charAt(charNum)!= '"'){
						wordInArray += receivedString.charAt(charNum);
						charNum++;
					}
					haystackList.add(wordInArray);
					wordInArray = "";
				}
			}
			
			//find the position of the needle in the haystack
			String[] haystackArray = new String[haystackList.size()];
			haystackArray = (String[]) haystackList.toArray(haystackArray);
			for(int x = 0; x < haystackArray.length; x++){
				if(needle.equals(haystackArray[x])){
					position  = x;
				}
			}
			//System.out.println(position);
			//System.out.println(haystackArray[position]);
			

			json2.put("needle" , position);
			HttpPost request2 = new HttpPost("http://challenge.code2040.org/api/haystack/validate");
			StringEntity params2 = new StringEntity(json2.toString());
			request2.addHeader("content-type", "application/json");
			request2.setEntity(params2);
			httpClient.execute(request2);



	}
}
