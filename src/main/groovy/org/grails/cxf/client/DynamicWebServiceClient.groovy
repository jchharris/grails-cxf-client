package org.grails.cxf.client

import org.springframework.aop.framework.ProxyFactoryBean
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.FactoryBeanNotInitializedException

/**
 * Provides web service clients.  Supports dynamically changing the wsdl document url
 * at runtime, as well as initializing the url from system settings.
 */
class DynamicWebServiceClient extends ProxyFactoryBean implements FactoryBean<Object> {

    String wsdlURL
    String wsdlServiceName
    String wsdlEndpointName
    Class<?> clientInterface
    Boolean enableDefaultLoggingInterceptors
    def clientPolicyMap = [:]
    String serviceEndpointAddress
    String serviceName
    WebServiceClientFactory webServiceClientFactory
    def outInterceptors = []
    def inInterceptors = []
    def inFaultInterceptors = []
    def outFaultInterceptors = []
    def httpClientPolicy
    def authorizationPolicy
    String proxyFactoryBindingId
    Boolean mtomEnabled = false
    String secureSocketProtocol
    Map requestContext
    Map tlsClientParameters = [:]

    Object getObject() throws FactoryBeanNotInitializedException, MalformedURLException {
        if (!clientInterface || !serviceEndpointAddress) {
            throw new FactoryBeanNotInitializedException("""Web service client cannot be created
before setting the clientInterface=${clientInterface} and
serviceEndpointAddress=${serviceEndpointAddress} properties""")
        }
        webServiceClientFactory.getWebServiceClient(wsdlURL,
                wsdlServiceName,
                wsdlEndpointName,
                clientInterface,
                serviceName,
                serviceEndpointAddress,
                enableDefaultLoggingInterceptors,
                clientPolicyMap,
                outInterceptors,
                inInterceptors,
                inFaultInterceptors,
                outFaultInterceptors,
                httpClientPolicy,
                authorizationPolicy,
                proxyFactoryBindingId,
                mtomEnabled,
                secureSocketProtocol,
                requestContext,
                tlsClientParameters)
    }

    Class<?> getObjectType() {
        clientInterface
    }

    boolean isSingleton() {
        true
    }
}
