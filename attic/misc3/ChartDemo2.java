import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import javax.swing.*;
import com.ibm.eou.swingchart.*;


public class ChartDemo2 extends JFrame implements Runnable {

	// Constants
	final static int ROLL_COUNT = 10;

	// Thread related instance data
	private Thread iTicker;
	private boolean iRunning = false;

	// Label
	private JLabel iMainLabel = new JLabel("Pressure gauge");

	// Chart
	private PlotChart iChart = new PlotChart();

	// Aggregator
	private ChartDataAggregator iAggregator = new ChartDataAggregator();

	// Data source
	private final PressureData iPressureData = new PressureData();

	/**
	 Called at applet initialization to initialize the chart and other components.
	 */

	 public static void main(String[] args) {
	 	ChartDemo2 cd = new ChartDemo2();
	 	cd.setDefaultCloseOperation(3);
	 	cd.init();
	 	cd.setVisible(true);
	 	cd.start();
	 }
	 
	 
	public void init() {

		// Setup and size the applet

		getContentPane().setBackground(Color.white);
		setSize(400, 350);
		getContentPane().setLayout(null);

		// Position, customize, and add the chart component

		iChart.setBounds(10 , 40, 380, 300);

		// Set legends

		iChart.setLegends(new String []{"Safety-limit", "Maximum", "Minimum", "Now"});

		// Set colors

		iChart.setColors(new Color []{Color.blue, Color.gray, Color.gray, Color.green});

		// Customize Y axis to hide values and scale to an absolute value

		iChart.setShowYAxisValues(false);
		iChart.setYScaleAuto(false);
		iChart.setYScaleMaximum(100);

		// Add the chart

		getContentPane().add(iChart);

		// Customize the aggregator to roll values
		// automatically after 10 groups

		iAggregator.setRollActive(true);
		iAggregator.setRollAfter(ROLL_COUNT);

		// Setup the main label

		iMainLabel.setBounds(10, 10, 300, 30);
		iMainLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		getContentPane().add(iMainLabel);


	}

	/**
	   Called at Applet start to start the thread.
	   */

	public synchronized void start() {
		if ((iTicker == null) || !iTicker.isAlive()) {
			iRunning = true;
			iTicker = new Thread(this);
			iTicker.setPriority(Thread.MIN_PRIORITY + 1);
			iTicker.start();
		}
	}

	/**
	   Called at Applet stop to stop the thread.
	   */

	public synchronized void stop() {
		iRunning = false;
	}

	//
	// Implementation of imterface Runnable
	//

	/**
	  Called to run the thread
	  */

	public void run() {
		while (iRunning) {

			// Get pressure now and safety limit

			double pressureNow = iPressureData.getPressureNow();
			double safetyLimit = iPressureData.getPressureSafetyLimit();

			// Dynamically set color of pressure line to show if within
			// safety limits

			if (pressureNow > safetyLimit) {
				iChart.setColors(3, Color.red);
			} else {
				iChart.setColors(3, Color.green);
			}

			// Update the aggregator to add a new group and values for
			// safety limit, max, min, and pressure now.

			iAggregator.addGroupAndValue(safetyLimit);
			iAggregator.addValue(iPressureData.getPressureMaximum());
			iAggregator.addValue(iPressureData.getPressureMinimum());
			iAggregator.addValue(pressureNow);

			// Update the chart values from the data organized by the aggregator

			iChart.setValues(iAggregator.getValues());

			// Get current time

			Date now = new Date();


			// Sleep for 2 seconds

			try {
				iTicker.sleep(2000);
			} catch (InterruptedException e) {}
		}
	}

}

/**
  Simple class used to simulate a real pressure sensor.
  */

class PressureData {

	// Instance data

	private double iMaximum;
	private double iMinimum;
	private boolean iInitialized = false;

	private Random iRand = new Random();

	/**
	  Returns current pressure.
	  */

	double getPressureNow() {

		double pressure = iRand.nextDouble() * 100;

		if (!iInitialized) {
			iMaximum = pressure;
			iMinimum = pressure;
			iInitialized = true;
		} else {
			iMaximum = Math.max(iMaximum, pressure);
			iMinimum = Math.min(iMinimum, pressure);
		}

		return pressure;
	}

	/**
	   Returns safety limit for this device
	   */

	double getPressureSafetyLimit() {
		return 90;
	}

	/**
	   Returns maximum pressure measured since start
	   */

	double getPressureMaximum() {
		return iMaximum;
	}

	/**
	   Returns minimum pressure measured since start
	   */

	double getPressureMinimum() {
		return iMinimum;
	}

}
