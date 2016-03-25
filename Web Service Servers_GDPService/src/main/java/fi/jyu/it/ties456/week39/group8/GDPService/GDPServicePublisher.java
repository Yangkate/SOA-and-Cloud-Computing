package fi.jyu.it.ties456.week39.group8.GDPService;

import javax.xml.ws.Endpoint;
import fi.jyu.it.ties456.week39.group8.GDPService.GetGDPImpl;

/**
 * Webservice publisher
 * @author Jonas
 *
 */
public class GDPServicePublisher {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8082/gdp/getGDP", new GetGDPImpl());
	}

}
