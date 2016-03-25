
package com.mycompany;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mycompany package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NoSuchCountryException_QNAME = new QName("http://gdp_service.mycompany.com/", "NoSuchCountryException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mycompany
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NoSuchCountryException }
     * 
     */
    public NoSuchCountryException createNoSuchCountryException() {
        return new NoSuchCountryException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchCountryException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gdp_service.mycompany.com/", name = "NoSuchCountryException")
    public JAXBElement<NoSuchCountryException> createNoSuchCountryException(NoSuchCountryException value) {
        return new JAXBElement<NoSuchCountryException>(_NoSuchCountryException_QNAME, NoSuchCountryException.class, null, value);
    }

}
