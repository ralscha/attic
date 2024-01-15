package ch.ess.base.web;

import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;

import ch.ess.base.Util;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.CrumbsControl;
import com.cc.framework.ui.model.ClientEvent;
import com.cc.framework.ui.model.imp.CrumbModelImp;
import com.cc.framework.ui.model.imp.CrumbsDesignModelImp;


public class CrumbsUtil {
  
  public static CrumbsControl getCrumbs(ActionContext ctx) {
    
    CrumbsControl crumbsControl = (CrumbsControl)ctx.session().getAttribute(WebConstants.SESSION_CRUMBS_CONTROL);
    if (crumbsControl == null) {
      crumbsControl = new CrumbsControl();
      ctx.session().setAttribute(WebConstants.SESSION_CRUMBS_CONTROL, crumbsControl);
    }
    
    return crumbsControl;
  }


  @SuppressWarnings("unchecked")
  public static Stack<CallStackObject> getCallStack(ActionContext ctx) {
    
    Stack<CallStackObject> callStack = (Stack<CallStackObject>)ctx.session().getAttribute(WebConstants.SESSION_CALL_STACK);
    if (callStack == null) {
      callStack = new Stack<CallStackObject>();
      ctx.session().setAttribute(WebConstants.SESSION_CALL_STACK, callStack);
    }
    
    return callStack;    
  }

  public static void updateCallStack(final Action action, final ActionContext ctx) {
    updateCallStack(action, ctx, ctx.mapping().getPath());
  }
  
  public static void updateCallStack(final Action action, final ActionContext ctx, String path) {
    
    // process callstack
    Stack<CallStackObject> callstack = getCallStack(ctx);
    
    String startCrumbPath = ctx.request().getParameter("startCrumbPath"); 
    if (startCrumbPath != null) {
      CallStackObject callStackObject = new CallStackObject();    
      callStackObject.setPath(startCrumbPath);
      callstack.push(callStackObject);
    }

    
    CallStackObject callStackObject = new CallStackObject();    
    
    callStackObject.setPath(path);

    String param = ctx.request().getParameter("id");
    if (StringUtils.isNotBlank(param)) {
      callStackObject.setId(param);
    } else {
      param = (String)ctx.session().getAttribute("id");
      if (StringUtils.isNotBlank(param)) {
        callStackObject.setId(param);
      }
    }
        
    if (action instanceof CrumbInfo) {
      callStackObject.setCrumbInfo((CrumbInfo)action);
    }
    
    int stackPos = callstack.indexOf(callStackObject); // search
    if (stackPos == -1) { // not found -> add
      callstack.push(callStackObject);
    } else { // crop upper elements
      callstack.setSize(stackPos + 1);
    }
  }
    
  public static void updateCrumbs(final ActionContext ctx) {  
    // process crumbs
    Stack<CallStackObject> callstack = getCallStack(ctx);
    
    
    CrumbsControl crumbsControl = getCrumbs(ctx);
    CrumbsDesignModelImp crumbsDesignModelImp = new CrumbsDesignModelImp();
    crumbsDesignModelImp.setId("crumbsDetail");
    crumbsControl.setDesignModel(crumbsDesignModelImp);
    
    String lastCrumbId = "";
    CrumbModelImp lastCrumbModelImp = null;
    CallStackObject parentCallStackObject = null;
    
    for (CallStackObject callStackObject : callstack) {
      
      CrumbModelImp crumbModelImp = new CrumbModelImp();
      String path = callStackObject.getPath();

      String title = null;
      
      crumbModelImp.setLocaleName("false");
      
      if (parentCallStackObject != null) {
        if ((parentCallStackObject.getCrumbInfo() != null) && (callStackObject.getId() != null)) {
          title = parentCallStackObject.getCrumbInfo().getTitle(callStackObject.getId(), ctx);
        }
      }

      crumbModelImp.setTitle(Util.translate(ctx.request(), path.substring(1).toLowerCase() + ".title") + ((title == null) ? "" : " '" + title + "'"));
      
      if (StringUtils.isNotBlank(callStackObject.getId())) {
        path = path + "?id=" + callStackObject.getId();
      }
      
      lastCrumbId = callStackObject.getPath();
      lastCrumbModelImp = crumbModelImp;
      
      crumbModelImp.setCrumbId(lastCrumbId);
      crumbModelImp.setAction(path);
      
            
      crumbsDesignModelImp.addCrumb(crumbModelImp);
       
      parentCallStackObject = callStackObject;
    }
    crumbsDesignModelImp.setName("crumbs");   
    
    if (lastCrumbModelImp != null) {
      lastCrumbModelImp.setHandler(ClientEvent.ONCLICK, "return false;");
    }
    crumbsControl.setSelectedCrumb(lastCrumbId);
    
  }
  
  public static CallStackObject getCallStackTopObject(final ActionContext ctx) {
  
    Stack<CallStackObject> callstack = getCallStack(ctx);
    if (!callstack.isEmpty()) {   
      return callstack.peek();
    }
    return null;
  }
}
