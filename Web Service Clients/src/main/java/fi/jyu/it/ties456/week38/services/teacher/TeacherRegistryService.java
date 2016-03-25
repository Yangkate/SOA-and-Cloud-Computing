
package fi.jyu.it.ties456.week38.services.teacher;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "TeacherRegistryService", targetNamespace = "http://week38.ties456.jyu.fi/", wsdlLocation = "http://ub1.ad.jyu.fi/teacher?wsdl")
public class TeacherRegistryService
    extends Service
{

    private final static URL TEACHERREGISTRYSERVICE_WSDL_LOCATION;
    private final static WebServiceException TEACHERREGISTRYSERVICE_EXCEPTION;
    private final static QName TEACHERREGISTRYSERVICE_QNAME = new QName("http://week38.ties456.jyu.fi/", "TeacherRegistryService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://ub1.ad.jyu.fi/teacher?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TEACHERREGISTRYSERVICE_WSDL_LOCATION = url;
        TEACHERREGISTRYSERVICE_EXCEPTION = e;
    }

    public TeacherRegistryService() {
        super(__getWsdlLocation(), TEACHERREGISTRYSERVICE_QNAME);
    }

    public TeacherRegistryService(WebServiceFeature... features) {
        super(__getWsdlLocation(), TEACHERREGISTRYSERVICE_QNAME, features);
    }

    public TeacherRegistryService(URL wsdlLocation) {
        super(wsdlLocation, TEACHERREGISTRYSERVICE_QNAME);
    }

    public TeacherRegistryService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, TEACHERREGISTRYSERVICE_QNAME, features);
    }

    public TeacherRegistryService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TeacherRegistryService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns TeacherRegistry
     */
    @WebEndpoint(name = "TeacherRegistryPort")
    public TeacherRegistry getTeacherRegistryPort() {
        return super.getPort(new QName("http://week38.ties456.jyu.fi/", "TeacherRegistryPort"), TeacherRegistry.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TeacherRegistry
     */
    @WebEndpoint(name = "TeacherRegistryPort")
    public TeacherRegistry getTeacherRegistryPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://week38.ties456.jyu.fi/", "TeacherRegistryPort"), TeacherRegistry.class, features);
    }

    private static URL __getWsdlLocation() {
        if (TEACHERREGISTRYSERVICE_EXCEPTION!= null) {
            throw TEACHERREGISTRYSERVICE_EXCEPTION;
        }
        return TEACHERREGISTRYSERVICE_WSDL_LOCATION;
    }

}
