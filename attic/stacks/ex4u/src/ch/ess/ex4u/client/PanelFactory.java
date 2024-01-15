package ch.ess.ex4u.client;

import ch.ess.ex4u.client.gui.MainLayout;
import com.smartgwt.client.widgets.Canvas;

public interface PanelFactory {

    Canvas create(MainLayout main);

    String getID();

    String getDescription();
}
