import java.awt.*;
import javax.swing.*;
import com.ibm.eou.swingchart.*;

public class ChartDemo extends JFrame {
	
	public ChartDemo() {
		
		super("Chart Demo");
		
		setDefaultCloseOperation(3);

		ParallelChart pc = new ParallelChart();
		
		String[] labels = {"1998", "1999", "2000"};
		double[][] values = { {100, 200, 300}} ;
		
	
		
		pc.setLabels(labels);
		pc.setValues(values);
		
		getContentPane().add(pc, BorderLayout.CENTER);
		pack();
		setVisible(true);

	
	}
	

	
	public static void main(String[] args) {
		new ChartDemo();
	}

}