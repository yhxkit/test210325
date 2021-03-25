package com.example.demo21032501;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedTomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {


    /**
     * In case of using external tomcat, this project needs to be set relaxedQueryChars at local tomcat server.xml as below
     * <Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" relaxedQueryChars='^/' />
     **/
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "^/"));

    }
}
