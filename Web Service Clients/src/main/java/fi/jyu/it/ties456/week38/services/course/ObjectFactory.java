
package fi.jyu.it.ties456.week38.services.course;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fi.jyu.it.ties456.week38.services.course package. 
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

    private final static QName _CreateCourseResponse_QNAME = new QName("http://week38.ties456.jyu.fi/", "createCourseResponse");
    private final static QName _CreateCourse_QNAME = new QName("http://week38.ties456.jyu.fi/", "createCourse");
    private final static QName _NoSuchTeacherException_QNAME = new QName("http://week38.ties456.jyu.fi/", "NoSuchTeacherException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fi.jyu.it.ties456.week38.services.course
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NoSuchTeacherException }
     * 
     */
    public NoSuchTeacherException createNoSuchTeacherException() {
        return new NoSuchTeacherException();
    }

    /**
     * Create an instance of {@link CreateCourse }
     * 
     */
    public CreateCourse createCreateCourse() {
        return new CreateCourse();
    }

    /**
     * Create an instance of {@link CreateCourseResponse }
     * 
     */
    public CreateCourseResponse createCreateCourseResponse() {
        return new CreateCourseResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCourseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://week38.ties456.jyu.fi/", name = "createCourseResponse")
    public JAXBElement<CreateCourseResponse> createCreateCourseResponse(CreateCourseResponse value) {
        return new JAXBElement<CreateCourseResponse>(_CreateCourseResponse_QNAME, CreateCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCourse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://week38.ties456.jyu.fi/", name = "createCourse")
    public JAXBElement<CreateCourse> createCreateCourse(CreateCourse value) {
        return new JAXBElement<CreateCourse>(_CreateCourse_QNAME, CreateCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchTeacherException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://week38.ties456.jyu.fi/", name = "NoSuchTeacherException")
    public JAXBElement<NoSuchTeacherException> createNoSuchTeacherException(NoSuchTeacherException value) {
        return new JAXBElement<NoSuchTeacherException>(_NoSuchTeacherException_QNAME, NoSuchTeacherException.class, null, value);
    }

}
