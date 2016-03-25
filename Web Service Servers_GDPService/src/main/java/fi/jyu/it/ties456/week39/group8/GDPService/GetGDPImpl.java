package fi.jyu.it.ties456.week39.group8.GDPService;

import fi.jyu.miselico.*;
import fi.jyu.miselico.GDP.NoSuchCountryException;

import javax.jws.WebService;

/**
 * 
 * @author Jonas
 *
 */
@WebService(endpointInterface = "fi.jyu.it.ties456.week39.group8.GDPService.GetGDPInterface")
public class GetGDPImpl implements GetGDPInterface {

	/**
	 * 
	 */
	public Double getGDP(String countryCode) {
		GDP gdp = new GDP();
		try {
			Double result = gdp.getGDP(countryCode);
			return result;
		} catch (NoSuchCountryException e) {
			System.err.println("There is no country for given longitude and latitude!");
			throw new Error("Error: ", e);
		}
	}

}
