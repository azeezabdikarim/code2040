package code2040;

import java.io.*;
import org.json.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Reverse {

	public static void main(String[] args) throws IOException {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();


			json.put("token", "34ee7aec6e614caf7fccecfe4ea2eb89");
			json2.put("token", "34ee7aec6e614caf7fccecfe4ea2eb89");
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();


			HttpPost request = new HttpPost("http://challenge.code2040.org/api/reverse");
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);

			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String reverse = new String();
			//System.out.println(response.toString());
			
			String receivedString = EntityUtils.toString(entity);
			//System.out.println(receivedString);
			int length = receivedString.length();
			for( int i = length - 1 ; i >= 0 ; i-- ) {
				reverse = reverse + receivedString.charAt(i);
			}
			
			//System.out.println(reverse);
			json2.put("string" , reverse);
			HttpPost request2 = new HttpPost("http://challenge.code2040.org/api/reverse/validate");
			StringEntity params2 = new StringEntity(json2.toString());
			request2.addHeader("content-type", "application/json");
			request2.setEntity(params2);
			httpClient.execute(request2);
			





	}
}
