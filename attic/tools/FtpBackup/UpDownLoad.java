import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import common.util.*;
import common.swing.*;
import com.ibm.webrunner.net.FTPSession;  

public class UpDownLoad extends JExitFrame {
		
	UpDownLoadTableModel model = new UpDownLoadTableModel();	
	
	public UpDownLoad() {
		super("UpDownLoad");
		
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new BorderLayout());
		rootPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		getContentPane().add(rootPanel, BorderLayout.CENTER);
				
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		
		Box box = Box.createVerticalBox();
		
		p.add(box, BorderLayout.CENTER);
		
		JButton upLoadButton = new JButton("Upload");
		JButton downLoadButton = new JButton("Download");
		JButton exitButton = new JButton("Exit");
		
		upLoadButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent event) {
									startTransfer(true);
								}});

		downLoadButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent event) {
									startTransfer(false);
								}});
		exitButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent event) {
									System.exit(0);
								}});
		
		box.add(upLoadButton);
		box.add(Box.createVerticalStrut(10));
		box.add(downLoadButton);
		box.add(Box.createVerticalStrut(20));
		box.add(exitButton);
		rootPanel.add(p, BorderLayout.EAST);
		
		getContentPane().add(Status.getStatusBarComponent(), BorderLayout.SOUTH);
		
		rootPanel.add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
	}
	
	
	
	public static void main(String args[]) {
		
		PlafPanel.setNativeLookAndFeel(true);	
		setProxySettings();
			
		UpDownLoad udl = new UpDownLoad();
		udl.setSize(400, 200);
		udl.setVisible(true);		
		Status.setText("Ready");

	}
	
	private void startTransfer(boolean up) {
		final boolean upload = up;
		new Thread(	new Runnable() {
			public void run() {
				ftpFiles(upload);
			}
		}).start();
		
	}
	
	private void ftpFiles(boolean upload) {
		
		if (!model.isFileInclude())
			return;
		
		Status.setBusy(true);
		
		FTPSession session = null;	
		try {
			session = new FTPSession(); 
					
			session.setUser(AppProperties.getStringProperty("ftp.user"));
			session.setPassword(AppProperties.getStringProperty("ftp.password"));
			session.setServer(AppProperties.getStringProperty("ftp.host"));
			
			Status.setText("log on ..." + session.getServer());
			session.logon();
			
			String remoteDirectory = AppProperties.getStringProperty("ftp.directory");
			if ((remoteDirectory != null) && (!remoteDirectory.trim().equals(""))) {
				Status.setText("change directory ... " + remoteDirectory);
				session.changeDirectory(remoteDirectory);
			}
			
			for (int i = 0; i < model.getRowCount(); i++) {
				if (model.isInclude(i)) {
					if (upload) {
						Status.setText("store " + model.getFileName(i));
						session.storeFile(model.getPath(i)+model.getFileName(i), model.getFileName(i), 'I');
					} else {
						Status.setText("retrieve " + model.getFileName(i));
						session.retrieveFile(model.getFileName(i), model.getPath(i)+model.getFileName(i), 'I');
					}
				}
			}
				
			Status.setText("logoff");
			session.logoff(); 
			Status.setText("Ready");
		} catch (IOException ioe) {
			if (session.getLoggedOn()) {
				try {
					session.logoff();
				} catch (IOException io) { }
			}
			
			System.err.println(ioe);
			Status.setText(ioe.toString());
		}
		
		Status.setBusy(false);

	}

	
	private static void setProxySettings() {
		String proxyHost = AppProperties.getStringProperty("proxy.host");
		String proxyPort = AppProperties.getStringProperty("proxy.port");
		
		if ((proxyHost != null) && !(proxyHost.trim().length() > 0)) {
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("proxyHost", proxyHost);
			System.getProperties().put("proxyPort", proxyPort); 
		}
	}
	
}