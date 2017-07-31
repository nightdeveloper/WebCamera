package org.onvif.ver10.accesscontrol.wsdl;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.10
 * 2017-04-04T00:56:49.280+02:00
 * Generated source version: 3.1.10
 * 
 */
@WebServiceClient(name = "PACSService", 
                  targetNamespace = "http://www.onvif.org/ver10/accesscontrol/wsdl") 
public class PACSService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.onvif.org/ver10/accesscontrol/wsdl", "PACSService");
    public final static QName PACSPort = new QName("http://www.onvif.org/ver10/accesscontrol/wsdl", "PACSPort");
    static {
        WSDL_LOCATION = null;
    }

    public PACSService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PACSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PACSService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public PACSService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public PACSService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public PACSService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns PACSPort
     */
    @WebEndpoint(name = "PACSPort")
    public PACSPort getPACSPort() {
        return super.getPort(PACSPort, PACSPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PACSPort
     */
    @WebEndpoint(name = "PACSPort")
    public PACSPort getPACSPort(WebServiceFeature... features) {
        return super.getPort(PACSPort, PACSPort.class, features);
    }

}