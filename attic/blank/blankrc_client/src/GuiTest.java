import org.springframework.rcp.application.startup.ApplicationLauncher;

/**
 * @author sr
 */
public class GuiTest {

  public static void main(String[] args) {

    try {
      new ApplicationLauncher("gui.xml");
      
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

  }
}