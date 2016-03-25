package fi.jyu.it.ties456.week39.group8;

import java.io.IOException;
import java.io.OutputStream;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.xpath.XPathExpressionException;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import jersey.repackaged.com.google.common.cache.CacheBuilder;
import jersey.repackaged.com.google.common.cache.CacheLoader;
import jersey.repackaged.com.google.common.cache.LoadingCache;

/**
 * localGDPService REST Service that uses the GetData class to receive the important information and offers a path to GET the GDP
 * @author Jonas
 *
 */
@Path("/gdp")
public class localGDPService {
	/*
	 * 
	 */
	private static LoadingCache<String, Double> cacheGDP;
	/**
	 * 
	 */
	public static void cache(){
		cacheGDP =  CacheBuilder.newBuilder()
				.maximumSize(10)
				.expireAfterWrite(150, TimeUnit.SECONDS)
				.refreshAfterWrite(5, TimeUnit.MINUTES)
				.build(
					new CacheLoader<String, Double>(){
						@Override
						public Double load(String countrycode) throws Exception{
							return GetData.getGDP(countrycode);
						}
					});
	}
	

	/**
	 * 
	 * @param lat latitude of the location
	 * @param lng longitude of the location
	 * @return JSON response with countrycode and GDP
	 * @throws XPathExpressionException
	 * @throws IOException
	 * @throws Exception
	 */
	@GET
	@Path("location")
	@Produces(MediaType.APPLICATION_JSON)
	public Response giveGDP(@QueryParam("lat") final Double lat, @QueryParam("lng") final Double lng)
			throws XPathExpressionException, IOException, Exception {
	    if(cacheGDP == null){
	    	cache();
	    }
		final String country = GetData.getCountryCode(new Location(lat, lng));
		final Double gdp= cacheGDP.get(country, new Callable<Double>(){
			public Double call() {
				return GetData.getGDP(country);
			}
		});
				
		
		StreamingOutput op = new StreamingOutput() {
			@Override
			public void write(OutputStream out) throws IOException, WebApplicationException {

				JsonGenerator generator = Json.createGenerator(out);
				generator.writeStartObject().write("country", country).write("GDP", gdp).writeEnd();

				generator.flush();
				generator.close();
			}
		};

		return Response.ok(op).header("Access-Control-Allow-Origin","*").type(MediaType.APPLICATION_JSON).build();
		
	}


}
