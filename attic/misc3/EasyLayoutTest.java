import com.hss.easylay.*;
import javax.swing.*;

public class EasyLayoutTest extends JFrame {

	public EasyLayoutTest() {
   		setDefaultCloseOperation(3);
   		
		EasyLayout easyLayout = new EasyLayout(getContentPane());
		getContentPane().setLayout(easyLayout);
		
		
		JButton centerComponent = new JButton("<html><b>Center</b><br>Grösser</html>");;
		
		//the add method will add the component to the applet
		easyLayout.add(centerComponent, new ELR(50, 50)); //anchor the first component
		
		easyLayout.add(new JButton("A"), new ELR(centerComponent, ELR.North, ELR.Left, 10));
		easyLayout.add(new JButton("B"), new ELR(centerComponent, ELR.North, ELR.Right, 10));
		easyLayout.add(new JButton("C"), new ELR(centerComponent, ELR.East, ELR.Top, 10));
		easyLayout.add(new JButton("D"), new ELR(centerComponent, ELR.South, ELR.Center, 10));
		easyLayout.add(new JButton("E"), new ELR(centerComponent, ELR.West, ELR.Bottom, 10));
     
		pack();
     	setVisible(true);
	}


	public static void main(String[] args) {
		new EasyLayoutTest();
	}
}