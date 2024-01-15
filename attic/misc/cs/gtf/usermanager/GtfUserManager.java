package gtf.usermanager;

import java.sql.*;
import java.awt.*;
import common.swing.*;
import javax.swing.*;


public class GtfUserManager {

	public static void main(String args[]) {
		
		LoginDialog ldg = new LoginDialog(null, "Login", new GMBLoginValidator());
		ldg.setVisible(true);
		
		//wenn wir hier sind hat das login geklappt

		SplashWindow sf = new SplashWindow();
		sf.setVisible(true);
		GtfUserManagerFrame gumf = new GtfUserManagerFrame();
		
		gumf.setSize(660, 570);
		sf.setVisible(false);
		sf.dispose();
		gumf.setVisible(true);

	}
}