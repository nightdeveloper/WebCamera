
package org.oasis_open.docs.wsn.bw_2;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.10
 * 2017-04-04T00:56:48.762+02:00
 * Generated source version: 3.1.10
 */

@WebFault(name = "NotifyMessageNotSupportedFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class NotifyMessageNotSupportedFault extends Exception {
    
    private org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType notifyMessageNotSupportedFault;

    public NotifyMessageNotSupportedFault() {
        super();
    }
    
    public NotifyMessageNotSupportedFault(String message) {
        super(message);
    }
    
    public NotifyMessageNotSupportedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public NotifyMessageNotSupportedFault(String message, org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType notifyMessageNotSupportedFault) {
        super(message);
        this.notifyMessageNotSupportedFault = notifyMessageNotSupportedFault;
    }

    public NotifyMessageNotSupportedFault(String message, org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType notifyMessageNotSupportedFault, Throwable cause) {
        super(message, cause);
        this.notifyMessageNotSupportedFault = notifyMessageNotSupportedFault;
    }

    public org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType getFaultInfo() {
        return this.notifyMessageNotSupportedFault;
    }
}
