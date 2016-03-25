package com.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;
import com.thoughtworks.xstream.XStream;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("latlng")
public class MyResource {
  @GET
    @Produces(MediaType.TEXT_PLAIN)
    public StreamingOutput getDGP(@QueryParam("lat") final double latitude, @QueryParam("lng") final  double longitude) throws IOException, ParserConfigurationException, SAXException {
    	  final XStream xs = new XStream();
    	//final gdpService GDP=new gdpService();
    	final getloc GDP=new getloc();
        StreamingOutput op = new StreamingOutput() {
            public void write(OutputStream out) throws IOException, WebApplicationException {
            	
                Writer writer = new BufferedWriter(new OutputStreamWriter(out));
               
          String countryCode = null;
           double G = 0 ;
           Object result=null;
          try {
        	  countryCode =  GDP.getLocationInfo(latitude,longitude);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   try {
						G = getloc.gdpcache.get(countryCode);
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
		   result=countryCode+G;
		    
			xs.toXML(result, writer);
			 writer.flush();
          }
      };
      return op;
  }
}
	
			

            
        
        


         
