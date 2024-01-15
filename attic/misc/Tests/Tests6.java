
import common.swing.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import com.klg.jclass.util.swing.*;

public class Tests6 extends JExitFrame {

	public Tests6() {
		super("Progress Tests");
		
		
		JCSpinNumberBox float_spin = new JCSpinNumberBox();
		float_spin.setName("FloatingPointSpinBox");
		float_spin.setValue(new Integer(0));
		float_spin.setValueRange(new JCSpinNumberBox.Range(new Integer(0),
		new Integer(12)));
		float_spin.setSpinStep(new Double(1.5));
		float_spin.setOperation(float_spin.FLOATING_POINT);
		getContentPane().add(float_spin, BorderLayout.CENTER);
		getContentPane().add(new JLabel("TEST"), BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}
	
	public void run() {
		JCProgressHelper jpr = new JCProgressHelper(this,
				"Here is a progress message", 0, 100,
				true, true, true);
		
		for (int i = 1; i <= 100; i++) {
			jpr.updateProgress(i);
			try {
				Thread.sleep(1000);
			} catch(InterruptedException ie) {
			}
			jpr.setDynamicMessage(""+i);
		}
		
		jpr.completeProgress();

	}
	
	public static void main(String args[]) {
   		new Tests6()/*.run()*/;
	
	}

}