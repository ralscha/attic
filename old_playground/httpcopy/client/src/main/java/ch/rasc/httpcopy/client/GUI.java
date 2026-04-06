package ch.rasc.httpcopy.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUI {

	private JFrame frmSync;
	private JTextField syncDirTf;
	private JTextField serverUrlTf;
	private boolean watching = false;

	private final Uploader uploader = new Uploader();
	private final Config config = new Config();

	public static void main(String[] args) {

		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}

		EventQueue.invokeLater(() -> {
			try {
				GUI window = new GUI();
				window.frmSync.setVisible(true);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public GUI() {
		initialize();
	}

	private void initialize() {
		this.frmSync = new JFrame();
		this.frmSync.setTitle("Sync");
		this.frmSync.setBounds(100, 100, 554, 215);
		this.frmSync.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.frmSync.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUI.this.uploader.shutdown();
			}
		});
		// addWindowListener();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 92, 307, 30, 30 };
		gridBagLayout.rowHeights = new int[] { 10, 0, 30, 0, 10, 0, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		this.frmSync.getContentPane().setLayout(gridBagLayout);

		JLabel lblServerUrl = new JLabel("Server URL:");
		GridBagConstraints gbc_lblServerUrl = new GridBagConstraints();
		gbc_lblServerUrl.anchor = GridBagConstraints.WEST;
		gbc_lblServerUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerUrl.gridx = 1;
		gbc_lblServerUrl.gridy = 1;
		this.frmSync.getContentPane().add(lblServerUrl, gbc_lblServerUrl);

		this.serverUrlTf = new JTextField("http://localhost:8080/upload");
		GridBagConstraints gbc_serverUrlTf = new GridBagConstraints();
		gbc_serverUrlTf.insets = new Insets(0, 0, 5, 5);
		gbc_serverUrlTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_serverUrlTf.gridx = 2;
		gbc_serverUrlTf.gridy = 1;
		this.frmSync.getContentPane().add(this.serverUrlTf, gbc_serverUrlTf);
		this.serverUrlTf.setColumns(10);

		JLabel lblSelectADirectory = new JLabel("Sync Directory:");
		GridBagConstraints gbc_lblSelectADirectory = new GridBagConstraints();
		gbc_lblSelectADirectory.anchor = GridBagConstraints.WEST;
		gbc_lblSelectADirectory.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectADirectory.gridx = 1;
		gbc_lblSelectADirectory.gridy = 2;
		this.frmSync.getContentPane().add(lblSelectADirectory, gbc_lblSelectADirectory);

		this.syncDirTf = new JTextField();
		GridBagConstraints gbc_syncDirTf = new GridBagConstraints();
		gbc_syncDirTf.insets = new Insets(0, 0, 5, 5);
		gbc_syncDirTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_syncDirTf.gridx = 2;
		gbc_syncDirTf.gridy = 2;
		this.frmSync.getContentPane().add(this.syncDirTf, gbc_syncDirTf);
		this.syncDirTf.setColumns(10);

		JButton btnSelectDirectory = new JButton("Select...");
		btnSelectDirectory.addActionListener(actionEvent -> {
			final JFileChooser fc = new JFileChooser();
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setDialogTitle("Select a directory");
			int returnVal = fc.showOpenDialog(btnSelectDirectory);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File selectedDir = fc.getSelectedFile();
				GUI.this.syncDirTf.setText(selectedDir.getAbsolutePath());
			}
		});
		GridBagConstraints gbc_btnSelectDirectory = new GridBagConstraints();
		gbc_btnSelectDirectory.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectDirectory.gridx = 3;
		gbc_btnSelectDirectory.gridy = 2;
		this.frmSync.getContentPane().add(btnSelectDirectory, gbc_btnSelectDirectory);

		JLabel lblOverwrite = new JLabel("Overwrite:");
		GridBagConstraints gbc_lblOverwrite = new GridBagConstraints();
		gbc_lblOverwrite.anchor = GridBagConstraints.WEST;
		gbc_lblOverwrite.insets = new Insets(0, 0, 5, 5);
		gbc_lblOverwrite.gridx = 1;
		gbc_lblOverwrite.gridy = 3;
		this.frmSync.getContentPane().add(lblOverwrite, gbc_lblOverwrite);

		JLabel statusLabel = new JLabel("Status: Not watching");
		statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.WEST;
		gbc_statusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_statusLabel.gridx = 2;
		gbc_statusLabel.gridy = 5;
		this.frmSync.getContentPane().add(statusLabel, gbc_statusLabel);

		JCheckBox overwriteCb = new JCheckBox("");
		overwriteCb.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_overwriteCb = new GridBagConstraints();
		gbc_overwriteCb.anchor = GridBagConstraints.WEST;
		gbc_overwriteCb.insets = new Insets(0, 0, 5, 5);
		gbc_overwriteCb.gridx = 2;
		gbc_overwriteCb.gridy = 3;
		this.frmSync.getContentPane().add(overwriteCb, gbc_overwriteCb);

		JButton watchButton = new JButton("Watch");
		watchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		watchButton.addActionListener(actionEvent -> {
			if (!GUI.this.watching) {
				String serverUrl = GUI.this.serverUrlTf.getText();
				String watchDir = GUI.this.syncDirTf.getText();
				boolean overwrite = overwriteCb.isSelected();

				if (serverUrl.trim().isEmpty() || watchDir.trim().isEmpty()) {
					return;
				}

				this.config.setServerEndpoint(serverUrl);
				this.config.setWatchDir(watchDir);
				this.config.setOverwrite(overwrite);
				this.config.store();

				GUI.this.uploader.start(this.config);

				btnSelectDirectory.setEnabled(false);
				overwriteCb.setEnabled(false);
				GUI.this.serverUrlTf.setEnabled(false);
				GUI.this.syncDirTf.setEnabled(false);
				watchButton.setText("Stop");
				statusLabel.setText("Status: Watching...");
				GUI.this.watching = true;
			}
			else {
				GUI.this.uploader.stop();

				btnSelectDirectory.setEnabled(true);
				overwriteCb.setEnabled(true);
				GUI.this.serverUrlTf.setEnabled(true);
				GUI.this.syncDirTf.setEnabled(true);
				watchButton.setText("Watch");
				statusLabel.setText("Status: Not watching");
				GUI.this.watching = false;
			}

		});

		GridBagConstraints gbc_watchButton = new GridBagConstraints();
		gbc_watchButton.anchor = GridBagConstraints.WEST;
		gbc_watchButton.insets = new Insets(0, 0, 5, 5);
		gbc_watchButton.gridx = 2;
		gbc_watchButton.gridy = 4;
		this.frmSync.getContentPane().add(watchButton, gbc_watchButton);

		overwriteCb.setSelected(this.config.isOverwrite());
		this.serverUrlTf.setText(this.config.getServerEndpoint());
		Path wd = this.config.getWatchDir();
		if (wd != null) {
			this.syncDirTf.setText(wd.toString());
		}
	}

}
