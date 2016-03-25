package fi.jyu.it.ties456.week39.group8;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.client.utils.URIBuilder;
import org.xml.sax.InputSource;

import fi.jyu.it.ties456.week39.group8.GDPService.GetGDPInterface;

/**
 * class to make calls to Google API and own soap service to receive countrycode and gdp
 * @author Jonas
 *
 */
public class GetData {

	/**
	 * makes an call to gdp soap service
	 * @param countryCode
	 * @return returns gdp for given countrycode
	 */
	public static Double getGDP (String countryCode){
		URL url;
		try {
			url = new URL("http://localhost:8082/gdp/getGDP?wsdl");
			QName qname = new QName("http://GDPService.group8.week39.ties456.it.jyu.fi/", "GetGDPImplService");

			Service service = Service.create(url, qname);

			GetGDPInterface gdp = service.getPort(GetGDPInterface.class);

			return gdp.getGDP(countryCode);
		} catch (MalformedURLException e) {
			System.err.println("An error occurred while getting GDP from GetGDP Service!");
			e.printStackTrace();
		}
		// Yes, I know. I will change it for next week if needed.
		return null;

	}
	
	/**
	 * makes api call to google api and extracts countrycode from given xml answer
	 * @param location location where want to get the countrycode from
	 * @return countrycode
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static String getCountryCode(Location location) throws IOException, XPathExpressionException {

		try {
			URIBuilder builduri = new URIBuilder().setScheme("https").setHost("maps.googleapis.com")
					.setPath("/maps/api/geocode/xml")
					.addParameter("latlng", location.getLatitude() + "," + location.getLongitude());
			URL countryCodeURL = builduri.build().toURL();

			InputStream ccStream = countryCodeURL.openStream();
			InputStreamReader ccReader = new InputStreamReader(ccStream, StandardCharsets.UTF_8);
			XPathFactory getCC = XPathFactory.newInstance();
			XPath ccPath = getCC.newXPath();

			return ccPath.evaluate("/GeocodeResponse/result/address_component[type='country']/short_name",
					new InputSource(ccReader));
		} catch (Exception e) {
			System.err.println("An error occured while getting countrycode!");
			throw new Error(e);
		}
	}
}
