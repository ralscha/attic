

import javax.swing.*;
import java.awt.event.*;

public class ServerFrame extends JFrame {
	AuctioneerImpl fAuctioneer;
	

	public ServerFrame(AuctioneerImpl auctioneer) {
		fAuctioneer = auctioneer;
		JButton stopButton = new JButton("Stop Server");
		stopButton.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent ae) {
												shutDown();
											}
										 });
		getContentPane().add(stopButton);

		setSize(200, 100);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
                  			public void windowClosing(WindowEvent e) {
                  				shutDown();
                  			}});
	}
	
	private void shutDown() {
		fAuctioneer.closing();
		System.exit(0);
	}
}