package com.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;











import com.mycompany.GDPImplService;
import com.mycompany.GDPface;
import com.mycompany.NoSuchCountryException_Exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.AccessException;
import java.util.concurrent.TimeUnit;

import jersey.repackaged.com.google.common.cache.CacheBuilder;
import jersey.repackaged.com.google.common.cache.CacheLoader;
import jersey.repackaged.com.google.common.cache.LoadingCache;
public class getloc {
public static 	LoadingCache<String, Double> gdpcache = CacheBuilder.newBuilder()
		       .maximumSize(10)
		       .expireAfterWrite((long) 2.5, TimeUnit.MINUTES)
	           .build(
		           new CacheLoader<String,Double>() {
		             public Double load(String country) throws NoSuchCountryException_Exception {
		               return GDP(country);
		             }
		           });

	public static  double GDP(String country) throws NoSuchCountryException_Exception{


		GDPImplService GDPService = new GDPImplService();
		GDPface get = GDPService.getGDPImplPort();	
		
		
	return 	get.getGdp(country);
	}
	
	public String getLocationInfo( double lat, double lng) throws ParseException {
	String s="";

		String url_address = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
				+ lat
				+ ","
				+ lng
				+ "&key=AIzaSyD1qdYjyUp9lxUQq-ol7F5-fnsEjjen1co";
        InputStream inputStream = null;
        String json = "";

        try {           
            HttpClient client = new DefaultHttpClient();  
            HttpPost post = new HttpPost(url_address);
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
        } catch(Exception e) {
        }

        try {           
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"),8);
            StringBuilder sbuild = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sbuild.append(line);
            }
            inputStream.close();
            json = sbuild.toString();               
        } catch(Exception e) {
        }


        //now parse
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json);
        JSONObject jb = (JSONObject) obj;

        //now read
        JSONArray jsonObject1 = (JSONArray) jb.get("results");
        JSONObject jsonObject2 = (JSONObject)jsonObject1.get(0);
        JSONArray recs = (JSONArray)jsonObject2.get("address_components");
       for (int i = 0; i <  recs.size(); i++) {
       	 JSONObject j = (JSONObject)recs.get(i);
       	 
           if(j.get("types").toString().contains("country")){
        	   s=(String)j.get("short_name");
           }
          
        }
       return s;
     
	}
	
}
	
  
