package @packageProject@.control.exception {

    import flash.utils.flash_proxy;
    
    import mx.collections.IList;
    import mx.collections.errors.ItemPendingError;
    import mx.messaging.messages.ErrorMessage;
    import mx.rpc.events.ResultEvent;
    import mx.utils.ObjectProxy;
    import mx.utils.ObjectUtil;
    import mx.utils.object_proxy;
    import mx.controls.Alert;
    
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;
    import org.granite.tide.BaseContext;
    import org.granite.tide.IComponent;
    import org.granite.tide.IEntity;
    import org.granite.tide.IEntityManager;
    import org.granite.tide.IExceptionHandler;
    import org.granite.tide.IPropertyHolder;
    import org.granite.tide.events.TideValidatorEvent;

    use namespace flash_proxy;
    use namespace object_proxy;
    

    /**
     * @author William DRAI
     */
    public class SecurityExceptionHandler implements IExceptionHandler {
        
        public static const SECURITY:String = "Server.Security."; 
        
        
        public function accepts(emsg:ErrorMessage):Boolean {
            return emsg.faultCode.search(SECURITY) == 0;
        }

        public function handle(context:BaseContext, emsg:ErrorMessage):void {
            // Ignore for now
        }
    }
}
