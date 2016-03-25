package AdSOAP.CALENDAR;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.client.utils.URIBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class calendarReader {
	public static String calendaerRea(double latOfBegin, double lngOfBegin,
			double latOfEnd, double lngofEnd) throws IOException, ParserConfigurationException, XPathExpressionException, SAXException{
		String begAdress;
		String LatOfBegin = String.valueOf(latOfBegin).substring(0, 7);
		String LngOfBegin = String.valueOf(lngOfBegin).substring(0, 7);
		String LatOfEnd = String.valueOf(latOfEnd).substring(0, 7);
		String LngofEnd = String.valueOf(lngofEnd).substring(0, 7);
		URIBuilder builder;
		InputStream stream;
		URL url ;
		// call Matka Api
//		URL url = new URL("http://api.matka.fi/?a=" + LatOfBegin + "," + LngOfBegin
//				+ "&b=" + LatOfEnd + "," +  LngofEnd
//				+ "&time=1030&show=1&timemode=2&user=SOAPYANGJIAWEI&pass=123456asd123");
//		InputStream stream = url.openStream();
		
		
		try {
			builder = new URIBuilder("http://api.matka.fi/");
		} catch (URISyntaxException e1) {
			throw new Error(e1);
		}
				
				builder.addParameter("a",  LatOfBegin + "," + LngOfBegin);
				builder.addParameter("b", LatOfEnd + "," + LngofEnd);
				builder.addParameter("time", "1030");
				builder.addParameter("show", "1");
				builder.addParameter("timemode", "2");
				builder.addParameter("user", "SOAPYANGJIAWEI");
				builder.addParameter("pass", "123456asd123");
				
				try {
					url = builder.build().toURL();
				} catch (MalformedURLException e) {
					throw new Error(e);
				} catch (URISyntaxException e) {
					throw new Error(e);
				}
				try {
					 stream = url.openStream();
				} catch (IOException e) {
					throw new Error(e);
				}
		
		// read data from XML and get time we need on the road and get start point.
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(stream);//so that it can use "stream" many times
		
		XPathFactory XPF= XPathFactory.newInstance();
		XPath XP= XPF.newXPath();
		String startTime = XP.evaluate(
				"MTRXML/ROUTE/POINT[@uid='start']/ARRIVAL/@time", document);
		String endTime = XP.evaluate(
				"MTRXML/ROUTE/POINT[@uid='dest']/ARRIVAL/@time", document);
		String pathtoInfor = "count(MTRXML/ROUTE/LINE)";
		Double lineCounter = (Double) XP.evaluate(pathtoInfor, document,
				XPathConstants.NUMBER);
		boolean Exists = (lineCounter.intValue() > 0) ? true : false;
		if (Exists == true) {
			begAdress = XP.evaluate("MTRXML/ROUTE/WALK[1]/STOP/NAME/@val",
					document);
		} else {
			begAdress = XP.evaluate(
					"MTRXML/ROUTE/WALK[1]/MAPLOC[1]/NAME/@val", document);
		}

		int time = Integer.parseInt(endTime.substring(0, 2))
				* 60
				+ Integer.parseInt(endTime.substring(2, 4))
				- (Integer.parseInt(startTime.substring(0, 2)) * 60 + Integer
						.parseInt(startTime.substring(2, 4)));
		String wayTime = String.valueOf(time);
		String info = wayTime + "," + begAdress;
		return info;

	}
		
	}


