/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.gdp_service;

/**
 *
 * @author Administrator
 */
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.ws.Endpoint;

import com.mycompany.gdp_service.GDPImpl;

import org.apache.http.client.utils.URIBuilder;
public class Main {

public static void main(String[] args) throws URISyntaxException {
	
	Integer port = Integer.valueOf(System.getenv("PORT"));
	
	URIBuilder builder = new URIBuilder();
	URI address        = builder.setScheme("http")
	                        .setPath("0.0.0.0")
	                        .setPort(port)
	                        .build();
	
		   Endpoint.publish("http://0.0.0.0:"+port+"/gdp", new GDPImpl());
		   Endpoint.publish("address", new GDPImpl());
	    }

	}
