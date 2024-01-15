
package ch.ess.blankrc.remote.support;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.RemoteAccessException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;

/**
 * @author sr
 */
public class HessianClientTicketFactory implements MethodInterceptor, FactoryBean, InitializingBean {

  private final Log logger = LogFactory.getLog(getClass());

  private final HessianProxyFactory proxyFactory = new HessianProxyFactory();

  private String username;
  private String password;
  private String serviceUrl;
  private Class serviceInterface;
  private Object hessianProxy;
  private TicketHolder ticketHolder;

  private Object serviceProxy;

  private String getServiceUrlWithTicket() {
    if (ticketHolder != null) {
      if (getServiceUrl().indexOf('?') < 0) {
        return getServiceUrl() + "?ticket=" + ticketHolder.getTicket();
      }
      return getServiceUrl() + "&ticket=" + ticketHolder.getTicket();
    }
    return getServiceUrl();
  }

  public Object invoke(MethodInvocation invocation) throws Throwable {

    if (this.hessianProxy == null) {
      this.hessianProxy = this.proxyFactory.create(getServiceInterface(), getServiceUrlWithTicket());
    }
    
    try {
      return invocation.getMethod().invoke(this.hessianProxy, invocation.getArguments());
    } catch (UndeclaredThrowableException ex) {
      throw new RemoteAccessException("Cannot access Hessian service", ex.getUndeclaredThrowable());
    } catch (InvocationTargetException ex) {
      logger.debug("Hessian service [" + getServiceUrl() + "] threw exception", ex.getTargetException());
      if (ex.getTargetException() instanceof HessianRuntimeException) {
        HessianRuntimeException hre = (HessianRuntimeException)ex.getTargetException();
        Throwable rootCause = (hre.getRootCause() != null) ? hre.getRootCause() : hre;
        throw new RemoteAccessException("Cannot access Hessian service", rootCause);
      } else if (ex.getTargetException() instanceof UndeclaredThrowableException) {
        UndeclaredThrowableException utex = (UndeclaredThrowableException)ex.getTargetException();
        throw new RemoteAccessException("Cannot access Hessian service", utex.getUndeclaredThrowable());
      }
      throw ex.getTargetException();
    }
  }

  public void setOverloadEnabled(boolean overloadEnabled) {
    this.proxyFactory.setOverloadEnabled(overloadEnabled);
  }
  
  public void setServiceUrl(String serviceUrl) {
    this.serviceUrl = serviceUrl;
  }

  protected String getServiceUrl() {
    return serviceUrl;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setServiceInterface(Class serviceInterface) {
    if (!serviceInterface.isInterface()) {
      throw new IllegalArgumentException("serviceInterface must be an interface");
    }
    this.serviceInterface = serviceInterface;
  }

  protected Class getServiceInterface() {
    return serviceInterface;
  }

  public TicketHolder getTicketHolder() {
    return ticketHolder;
  }

  public void setTicketHolder(TicketHolder ticketHolder) {
    this.ticketHolder = ticketHolder;
  }
  
  
  public void afterPropertiesSet() {
    
    if (getServiceInterface() == null) {
      throw new IllegalArgumentException("serviceInterface is required");
    }
    if (getServiceUrl() == null) {
      throw new IllegalArgumentException("serviceUrl is required");
    }


  }

  public Object getObject() {
    this.hessianProxy = null;
    this.serviceProxy = ProxyFactory.getProxy(getServiceInterface(), this);

    return this.serviceProxy;
  }

  public Class getObjectType() {
    return (this.serviceProxy != null) ? this.serviceProxy.getClass() : getServiceInterface();
  }
  
  public boolean isSingleton() {
    return false;
  }

}