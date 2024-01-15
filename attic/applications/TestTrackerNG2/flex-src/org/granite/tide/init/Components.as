package org.granite.tide.init {
  import org.granite.tide.Tide;
  import ch.ess.testtracker.control.PrincipalCtl;
    
    
  public class Components {
    public function Components(tide:Tide):void {
      tide.clientComponents.principalCtl = PrincipalCtl;
      tide.setComponentScope("principalCtl", false);
    }
}
}