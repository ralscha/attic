
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Progress {
	public static final int LOOP_MAX = 60000;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Progress Demo");
		ProgressMonitor progress;
		frame.setDefaultCloseOperation(3);
	
		frame.setSize(400, 400);
		frame.setVisible(true);

		progress = new ProgressMonitor(frame, "Executing dummy loop.", "Iterating.", 0,
                               		LOOP_MAX);

		for (int i = 0; i <= LOOP_MAX; ++i) {
			// Simulate some work
			for (int j = 0; j <= LOOP_MAX; ++j)	;
			if (progress.isCanceled())
				break;
			progress.setProgress(i);
		}
	}
}