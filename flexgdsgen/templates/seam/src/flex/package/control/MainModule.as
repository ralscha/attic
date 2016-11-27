package @packageProject@.control {
  import @packageProject@.control.exception.AccessDeniedExceptionHandler;
  import @packageProject@.control.exception.DefaultExceptionHandler;
  import @packageProject@.control.exception.OptimisticLockExceptionHandler;
  import @packageProject@.control.exception.SecurityExceptionHandler;
  import @packageProject@.control.exception.InvalidCredentialsExceptionHandler;

  import mx.logging.Log;
  import mx.logging.LogEventLevel;
  import mx.logging.targets.TraceTarget;
  
  import org.granite.tide.ITideModule;
  import org.granite.tide.Tide;
  import org.granite.tide.seam.framework.PagedQuery;
  import org.granite.tide.validators.ValidatorExceptionHandler;
  
  
  [Bindable]
  public class MainModule implements ITideModule {
  
    public function init(tide:Tide):void {
      var logTarget:TraceTarget = new TraceTarget();
      logTarget.filters = ["@packageProject@.*"];
      logTarget.level = LogEventLevel.ALL;      
      logTarget.includeDate = true;
      logTarget.includeTime = true;
      logTarget.includeCategory = true;
      logTarget.includeLevel = true;

      Log.addTarget(logTarget);
      
      tide.addExceptionHandler(DefaultExceptionHandler);
      tide.addExceptionHandler(ValidatorExceptionHandler);
      tide.addExceptionHandler(AccessDeniedExceptionHandler);
      tide.addExceptionHandler(InvalidCredentialsExceptionHandler);
      tide.addExceptionHandler(SecurityExceptionHandler);
      tide.addExceptionHandler(OptimisticLockExceptionHandler);
      
      tide.addComponents([UserCtl, LoginCtl]);   
      tide.addComponent("users", PagedQuery, false, true, Tide.RESTRICT_YES);
      
      tide.setComponentAutoCreate("exampleUser", true);
      
    }
  }
}
