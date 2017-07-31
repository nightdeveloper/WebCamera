package com.github.nightdeveloper.webcamera.soap;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.Calendar;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapBindingConfiguration;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.onvif.ver10.device.wsdl.Device;
import org.onvif.ver10.device.wsdl.DeviceService;
import org.onvif.ver10.media.wsdl.Media;
import org.onvif.ver10.media.wsdl.MediaService;
import org.onvif.ver10.schema.*;
import org.onvif.ver20.ptz.wsdl.PTZ;
import org.onvif.ver20.ptz.wsdl.PtzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PTZService {

    private static final Logger logger = LoggerFactory.getLogger(PTZService.class);

    public static final String webCamUrl = "http://192.168.1.128/onvif/device_service";

    public static final String profileToken = "MainStreamTooken";

    private boolean isInitted = false;

    private Device device;
    private Media media;
    private PTZ ptz;

    private String cachedSnapshotURI = null;

    public static JaxWsProxyFactoryBean getServiceProxy(BindingProvider servicePort, String serviceAddr) {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setAddress(webCamUrl);
        proxyFactory.setServiceClass(servicePort.getClass());

        /*
        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
        loggingInInterceptor.setPrettyLogging(true);
        LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
        loggingOutInterceptor.setPrettyLogging(true);
        proxyFactory.getInInterceptors().add(loggingInInterceptor);
        proxyFactory.getOutInterceptors().add(loggingOutInterceptor);
        */

        SoapBindingConfiguration config = new SoapBindingConfiguration();
        config.setVersion(Soap12.getInstance());
        proxyFactory.setBindingConfig(config);
        Client deviceClient = ClientProxy.getClient(servicePort);

        HTTPConduit http = (HTTPConduit) deviceClient.getConduit();

        HTTPClientPolicy httpClientPolicy = http.getClient();
        httpClientPolicy.setConnectionTimeout(36000);
        httpClientPolicy.setReceiveTimeout(32000);
        httpClientPolicy.setAllowChunking(false);
        return proxyFactory;
    }

    private void lazyInit() throws Exception {

        if (isInitted) {
            return;
        }

        logger.info("initializing...");

        BindingProvider deviceServicePort = (BindingProvider) new DeviceService().getDevicePort();

        this.device = getServiceProxy(deviceServicePort, webCamUrl).create(Device.class);
        Capabilities capabilities = this.device.getCapabilities(Arrays.asList(CapabilityCategory.ALL));
        if (capabilities == null) {
            throw new ConnectException("Capabilities not reachable.");
        }

        this.media = new MediaService().getMediaPort();
        this.media = getServiceProxy((BindingProvider) media, capabilities.getMedia().getXAddr()).create(Media.class);

        this.ptz = new PtzService().getPtzPort();
        this.ptz = getServiceProxy((BindingProvider) ptz, capabilities.getPTZ().getXAddr()).create(PTZ.class);

        this.isInitted = true;
    }

    public void stop() throws Exception {

        lazyInit();

        logger.info("stop");

        ptz.stop(profileToken, true, true);

        logger.info("stopped");
    }

    public String getSnapshotUri() throws Exception {

        lazyInit();

        logger.info("get snapshot uri");

        if (cachedSnapshotURI == null) {
            MediaUri uri = media.getSnapshotUri(profileToken);

            cachedSnapshotURI = uri.getUri();

            logger.info("real get from camera");
        }

        String ts = "ts=" + Calendar.getInstance().getTimeInMillis();

        return cachedSnapshotURI.indexOf("?") > 0 ? cachedSnapshotURI + "&" + ts : cachedSnapshotURI + "?" + ts;
    }

    public void move(String direction, Long durationMs) throws Exception {

        lazyInit();

        Long duration = durationMs;

        if (duration == null) {
            duration = 1000L;
        }

        Vector2D vector = new Vector2D();

        vector.setX(0);
        vector.setY(0);

        if ("left".equals(direction)) {
            vector.setX(0.1f);
        } else if ("right".equals(direction)) {
            vector.setX(-0.1f);
        } else if ("up".equals(direction)) {
            vector.setY(0.1f);
        } else if ("down".equals(direction)) {
            vector.setY(-0.1f);
        } else {
            logger.info("empty direction");
            return;
        }

        PTZSpeed velocity = new PTZSpeed();
        velocity.setPanTilt(vector);

        logger.info("continuous move " + direction + " {" + vector.getX() + ", " + vector.getY());

        ptz.continuousMove(profileToken, velocity, null);

        logger.info("sleeping " + duration);

        Thread.sleep(duration);

        stop();
    }
}
