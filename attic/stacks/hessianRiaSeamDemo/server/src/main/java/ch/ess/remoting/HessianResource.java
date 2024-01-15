package ch.ess.remoting;

import static org.jboss.seam.ScopeType.APPLICATION;
import static org.jboss.seam.annotations.Install.BUILT_IN;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.contexts.RemotingLifecycle;
import org.jboss.seam.core.ConversationPropagation;
import org.jboss.seam.core.Manager;
import org.jboss.seam.remoting.RequestContext;
import org.jboss.seam.servlet.ContextualHttpServletRequest;
import org.jboss.seam.web.AbstractResource;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.services.server.AbstractSkeleton;
import com.caucho.services.server.ServiceContext;

@Startup
@Scope(APPLICATION)
@Name("org.jboss.seam.remoting.hessian")
@Install(precedence = BUILT_IN)
@BypassInterceptors
public class HessianResource extends AbstractResource {

  @Override
  public void getResource(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
      IOException {

    //code from com.caucho.hessian.server.HessianServlet
    if (!request.getMethod().equals("POST")) {
      response.sendError(500, "Hessian Requires POST");
      return;
    }

    final String componentName = request.getPathInfo().substring(getResourcePath().length()+1);
    
    System.out.println(componentName);

    //todo what is that
    String objectId = request.getParameter("id");
    if (objectId == null) {
      objectId = request.getParameter("ejbid");
    }
    
    ServiceContext.begin(request, componentName, objectId);

    try {
      InputStream is = request.getInputStream();
      

      final Hessian2Input in = new Hessian2Input(is);
      

      //read first character. 
      int code = in.read();      
      if (code != 'c') {
        throw new IOException("expected 'c' in hessian input at " + code);
      }
      
      //read major and minor version
      final int major = in.read();
      in.read(); //minor version is not used
      
      //read headers
      
      String header;
      while ((header = in.readHeader()) != null) {
        Object value = in.readObject();
        ServiceContext.getContext().addHeader(header, value);
      }
      
      //code from org.jboss.seam.remoting.ExecutionHandler
      final RequestContext ctx = buildRequestContext(ServiceContext.getContext());
      
      RemotingLifecycle.restorePageContext();

      new ContextualHttpServletRequest(request) {

        @Override
        public void process() throws Exception {
          //look for the OutputStream
          AbstractHessianOutput out;
          OutputStream os = response.getOutputStream();
          if (major >= 2) {
            out = new Hessian2Output(os);
          } else {
            out = new HessianOutput(os);
          }          
          
          
          //look for the component
          Component component = Component.forName(componentName);

          if (component == null) {
            out.startReply();
            out.writeFault("NoSuchMethodException", "No such component: " + componentName, null);
            out.completeReply();
            return;
          }

          // Create an instance of the component
          Object instance = Component.getInstance(componentName, true);

          if (instance == null) {
            out.startReply();
            out.writeFault("RuntimeException", String.format("Could not create instance of component %s", componentName), null);
            out.completeReply();
            return;
          }          
          
          
          //look for the method
          //todo it would be better to cache the methodMap
          String mangledName = in.readMethod();
          Map<String,Method> methodMap = buildMethodMap(instance);
          Method method = methodMap.get(mangledName);
          if (method == null) {
            out.startReply();
            out.writeFault("NoSuchMethodException",
               "The component has no method named: " + in.getMethod(),
               null);
            out.completeReply();
            return;
          }          
          
          
          //call the method
          Class<?>[] args = method.getParameterTypes();
          Object[] values = new Object[args.length];

          for (int i = 0; i < args.length; i++) {
            values[i] = in.readObject(args[i]);
          }

          Object result = null;
          
          try {
            result = method.invoke(instance, values);
          } catch (Throwable e) {
            if (e instanceof InvocationTargetException)
              e = ((InvocationTargetException) e).getTargetException();

            out.startReply();
            out.writeFault("ServiceException", e.getMessage(), e);
            out.completeReply();
            return;
          }

          // The complete call needs to be after the invoke to handle a
          // trailing InputStream
          in.completeCall();
          
          
          // Store the conversation ID in the outgoing context
          ctx.setConversationId(Manager.instance().getCurrentConversationId());

          // Write the response          
          writeResponse(result, ctx, out);
        }

        @Override
        protected void restoreConversationId() {
          ConversationPropagation.instance().setConversationId(ctx.getConversationId());
        }

        @Override
        protected void handleConversationPropagation() {
          //no action            
        }

      }.run();

    } catch (Throwable e) {
      throw new ServletException(e);
    } finally {
      ServiceContext.end();
    }

  }


  Map<String, Method> buildMethodMap(Object instance) {
    Method[] methodList = instance.getClass().getMethods();

    Map<String, Method> methodMap = new HashMap<String, Method>();
    for (int i = 0; i < methodList.length; i++) {
      Method method = methodList[i];

      if (methodMap.get(method.getName()) == null) {
        methodMap.put(method.getName(), methodList[i]);
      }

      Class<?>[] param = method.getParameterTypes();
      String mangledName = method.getName() + "__" + param.length;
      methodMap.put(mangledName, methodList[i]);
      
      methodMap.put(AbstractSkeleton.mangleName(method, false), methodList[i]);
    }
    
    return methodMap;
  }


  void writeResponse(Object result, RequestContext ctx, AbstractHessianOutput out) throws IOException {
    out.startReply();
    
    /* TODO com.caucho.hessian.client.HessianProxy has a problem when we do that
     * looks like there is no header processing in this class.
    if (ctx.getConversationId() != null) {
      out.writeHeader("conversationId");
      out.writeObject(ctx.getConversationId());
    }
    */
    
    out.writeObject(result);    
    out.completeReply();
    out.close();    
  }
  
  private RequestContext buildRequestContext(ServiceContext context) {
    RequestContext ctx = new RequestContext();
    Object headerObject = context.getHeader("conversationId");
    if (headerObject != null) {
      ctx.setConversationId(headerObject.toString());
    } 
    return ctx;
  }

  @Override
  public String getResourcePath() {
    return "/hessian";
  }

}
