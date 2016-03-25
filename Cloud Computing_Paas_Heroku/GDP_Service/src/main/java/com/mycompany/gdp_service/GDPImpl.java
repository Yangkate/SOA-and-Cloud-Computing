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
import javax.jws.WebService;

import fi.jyu.miselico.GDP;
import fi.jyu.miselico.GDP.NoSuchCountryException;


	@WebService(endpointInterface = "com.mycompany.gdp_service.GDPface")
	public class GDPImpl  implements GDPface{
	 GDP object=new GDP();
		public double getGdp(String countryCode) throws NoSuchCountryException {
			return object.getGDP(countryCode);
		}
	 
	}

