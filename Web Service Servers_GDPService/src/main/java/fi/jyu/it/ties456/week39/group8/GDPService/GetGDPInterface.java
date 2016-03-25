package fi.jyu.it.ties456.week39.group8.GDPService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * Webservice interface
 * @author Jonas
 *
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface GetGDPInterface {

	@WebMethod
	Double getGDP(String countryCode);

}