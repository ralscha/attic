package ch.ralscha.mycustomers.server;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SpringGwtRemoteServiceServlet extends RemoteServiceServlet {

  final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void init() {
		if (log.isDebugEnabled()) {
			log.debug("Spring GWT service exporter deployed");
		}
  }

  @Override
  public String processCall(String payload) throws SerializationException {
    try {
      Object handler = getBean(getThreadLocalRequest());
      RPCRequest rpcRequest = RPC.decodeRequest(payload, handler.getClass(), this);
      onAfterRequestDeserialized(rpcRequest);
			if (log.isDebugEnabled()) {
				log.debug("Invoking " + handler.getClass().getName() + "." + rpcRequest.getMethod().getName());
			}
      return RPC.invokeAndEncodeResponse(handler, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest.getSerializationPolicy());
    } catch (IncompatibleRemoteServiceException ex) {
      log.error("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
      return RPC.encodeResponseForFailure(null, ex);
    }
  }

  /**
   * Determine Spring bean to handle request based on request URL, e.g. a
   * request ending in /myService will be handled by bean with name
   * "myService".
   * 
   * @param request
   * @return handler bean
   */
  protected Object getBean(HttpServletRequest request) {
    String service = getService(request);
    Object bean = getBean(service);
    if (!(bean instanceof RemoteService)) {
      String errorMsg = "Spring bean is not a GWT RemoteService: " + service + " (" + bean + ")";
      log.error(errorMsg);
      throw new IllegalArgumentException(errorMsg);
    }
		if (log.isDebugEnabled()) {
			log.debug("Bean for service " + service + " is " + bean);
		}
    return bean;
  }

  /**
   * Parse the service name from the request URL.
   * 
   * @param request
   * @return bean name
   */
  protected String getService(HttpServletRequest request) {
    String url = request.getRequestURI();
    String service = url.substring(url.lastIndexOf("/") + 1);
		if (log.isDebugEnabled()) {
			log.debug("Service for URL " + url + " is " + service);
		}
    return service;
  }

  /**
   * Look up a spring bean with the specified name in the current web
   * application context.
   * 
   * @param name
   *            bean name
   * @return the bean
   */
  protected Object getBean(String name) {
    WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    if (applicationContext == null) {
      String errorMsg = "No Spring web application context found";
      log.error(errorMsg);
      throw new IllegalStateException(errorMsg);
    }
    if (!applicationContext.containsBean(name)) {
      String errorMsg = "Spring bean not found: " + name;
      log.error(errorMsg);
      throw new IllegalArgumentException(errorMsg);
    }
    return applicationContext.getBean(name);
  }
}
