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

public class Prefix{

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
//			System.out.println(receivedString);
//			System.out.println(receivedString.length());
			
		//Processing the response
			String wordInArray = new String();
			String prefix = new String();
			ArrayList list = new ArrayList();
			int position = 0;
			
			//identifying the prefix
			for(int x = 11; x < 15; x++){
				prefix += receivedString.charAt(x);	
			}
			//System.out.println(prefix);
			
			//parsing the string to create an arraylist with all the strings
			
			for( int charNum = 26; charNum < receivedString.length(); charNum++){
				if(receivedString.charAt(charNum)=='"'){
					charNum++;
					while(receivedString.charAt(charNum)!= '"'){
						wordInArray += receivedString.charAt(charNum);
						charNum++;
					}
					list.add(wordInArray);
					wordInArray = "";
				}
			}		
			
			//converting arraylist to array
			String[] array = new String[list.size()];
			array = (String[]) list.toArray(array);
			
			//creating new arrayList that will only include words without the prefix
			ArrayList list2 = new ArrayList();
			
			
			//Adding strings to list2 that DO NOT contain the prefix
			String prefixCheck = "";
			for(int x = 0; x < array.length; x++){
				for(int y = 0; y < 4; y++){
					prefixCheck += array[x].charAt(y);
				}
				if(prefix.equals(prefixCheck)){
				}
				else{
					list2.add(array[x]);
				}
				prefixCheck = "";
			}
			
			//converting list2 to array2
			String[] array2 = new String[list2.size()];
			array2 = (String[]) list2.toArray(array2);
			

			json2.put("array" , array2);
			HttpPost request2 = new HttpPost("http://challenge.code2040.org/api/prefix/validate");
			StringEntity params2 = new StringEntity(json2.toString());
			request2.addHeader("content-type", "application/json");
			request2.setEntity(params2);
			httpClient.execute(request2);
			


	}
}
