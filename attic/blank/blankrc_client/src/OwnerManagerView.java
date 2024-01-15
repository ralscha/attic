
import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.rcp.application.AbstractView;

/**
 * @author sr
 */
public class OwnerManagerView extends AbstractView implements ApplicationListener {

  protected JComponent createControl() {
    JPanel view = new JPanel(new BorderLayout());
    view.add(new JLabel("test"), BorderLayout.CENTER);
    return view;
  }

  public void onApplicationEvent(ApplicationEvent event) {
    System.out.println(event);
  }

}