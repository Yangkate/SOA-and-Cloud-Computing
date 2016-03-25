/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.gdp_service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import fi.jyu.miselico.GDP.NoSuchCountryException;

	@WebService
	@SOAPBinding(style = Style.RPC)
	public interface GDPface{
	 
		@WebMethod double getGdp(String countryCode) throws NoSuchCountryException;

	}
