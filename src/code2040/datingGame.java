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

public class datingGame{

	public static void main(String[] args) throws IOException {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();


			json.put("token", "34ee7aec6e614caf7fccecfe4ea2eb89");
			json2.put("token", "34ee7aec6e614caf7fccecfe4ea2eb89");
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();

			//pulling dictionary 
			HttpPost request = new HttpPost("http://challenge.code2040.org/api/dating");
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			//System.out.println(response.toString());
			String receivedString = EntityUtils.toString(entity);
			//System.out.println(receivedString);
			
			// Parse data for ISO8601
			
		//Processing the response
			String intervalString = new String();

		
		//decoding the datestamp and creating integers representing the different datestamp elements  
			String Sdyears = "";
			String Sdmonths = "";
			String Sddays = "";
			String Sdhours = "";
			String Sdmins = "";
			String Sdseconds = "";
			
			//System.out.println(receivedString.charAt(15));
			for(int r = 14; r < 18; r++){
				Sdyears += receivedString.charAt(r);
			}
			int dyears = Integer.parseInt(Sdyears);
			for(int r = 19; r < 21; r++){
				Sdmonths += receivedString.charAt(r);
			}
			int dmonths = Integer.parseInt(Sdmonths);
			for(int r = 22; r < 24; r++){
				Sddays += receivedString.charAt(r);
			}
			int ddays = Integer.parseInt(Sddays);
			for(int r = 25; r < 27; r++){
				Sdhours += receivedString.charAt(r);
			}
			int dhours = Integer.parseInt(Sdhours);
			for(int r = 28; r < 30; r++){
				Sdmins += receivedString.charAt(r);
			}
			int dmins = Integer.parseInt(Sdmins);
			for(int r = 31; r < 33; r++){
				Sdseconds += receivedString.charAt(r);
			}
			int dseconds = Integer.parseInt(Sdseconds);
			//System.out.println(dyears + "  " + dmonths + "  " + ddays + "  " + dhours + "  " + dmins + "  " + dseconds);
			
			//identifying the interval and setting an int to its value
			int x = 47;
			while(receivedString.charAt(x) != '}'){
				intervalString += receivedString.charAt(x);
				x++;
			}
			
			int interval = Integer.parseInt(intervalString);
			
			//Sort interval in to days/hours/mins/seconds
			int idays = (interval/86400);
			int ihours = ((interval -(idays*86400))/3600);
			int imins = ((interval - (idays*86400) - (ihours*3600))/60);
			int iseconds  = (interval - (idays*86400) - (ihours*3600) - (imins*60));
			//System.out.println(idays + "  " + ihours + "  " + imins + "  " + iseconds);
			
			//Combining datestamp and interval
			int fyear = dyears;
			int fmonth = dmonths;
			int fday = 0;
			if((ddays + idays)>31){
				fmonth++;
				 fday = (ddays+idays)-31;
			}
			else{
				 fday = ddays + idays;
			}
			int fhour = 0;
			if((dhours+ihours)>24){
				fday++;
				fhour = ((dhours+ihours)-24);
			}
			else{
				fhour = dhours+ihours;
			}
			int fmin = 0;
			if((dmins+imins)>60){
				fhour++;
				fmin = ((dmins+imins)-60);
			}
			else{
				fmin = dmins + imins;
			}
			int fsecond = 0;
			if((dseconds+iseconds)>60){
				fmin++;
				fsecond = ((dseconds+iseconds)-60);
			}
			else{
				fsecond = dseconds + iseconds;
			}
			
			//converting ints back to string so that numbers <10 can be appended with a 0
			String fyearS = ""+fyear;
			String fmonthS = ""+fmonth;
			String fdayS = ""+fday;
			String fhourS = ""+fhour;
			String fminS = ""+fmin;
			String fsecondS = ""+fsecond;
			
			if(fmonth<10){
				fmonthS = "0"+fmonth;
			}
			if(fday < 10){
				fdayS = "0" + fday;
			}
			if(fhour < 10){
				fhourS = "0"+fhour;
			}
			if(fmin < 10){
				fminS = "0"+fmin;
			}
			if(fsecond < 10){
				fsecondS = "0"+fsecond;
			}
			
			
			
			//packaging the datestamp interval combination
			String result = fyearS + "-" + fmonthS + "-" + fdayS + "T" + fhourS + ":" + fminS + ":" + fsecondS + "Z";
			
			json2.put("datestamp" , result);
			HttpPost request2 = new HttpPost("http://challenge.code2040.org/api/dating/validate");
			StringEntity params2 = new StringEntity(json2.toString());
			request2.addHeader("content-type", "application/json");
			request2.setEntity(params2);
			httpClient.execute(request2);
			

	}
}
