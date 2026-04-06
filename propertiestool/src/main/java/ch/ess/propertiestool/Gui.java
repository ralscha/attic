package ch.ess.propertiestool;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.bushe.swing.event.EventBus;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;

	boolean exportMode = true;

	public Gui() {

		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
			// Likely Plastic is not in the classpath; ignore it.
		}

		// Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu();
		fileMenu.setText("File");

		JMenuItem exitItem = new JMenuItem();
		exitItem.setText("Exit");
		exitItem.addActionListener(evt -> shutdown());

		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		FormLayout layout = new FormLayout("left:max(30dlu;p), 3dlu, p:grow, 3dlu, p",
				"p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 1dlu, p, 3dlu, fill:p:grow, 3dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		final JFileChooser resourceFileChooser = new JFileChooser();
		resourceFileChooser.setFileFilter(new PropertiesFileFilter());
		resourceFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		final JFileChooser excelFileChooser = new JFileChooser();
		excelFileChooser.setFileFilter(new XlsFileFilter());
		excelFileChooser.addChoosableFileFilter(new XlsxFileFilter());
		excelFileChooser.setAcceptAllFileFilterUsed(false);
		excelFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		final JTextField resourceFileField = new JTextField();
		final JTextField excelFileField = new JTextField();

		resourceFileField.setToolTipText(
				"<html>Default Sprache Resource File für den Export.<br>Die anderen Sprachen werden automatisch ermittelt.<br>Hierzu müssen dei Resource Files im selben Pfad liegen.</html>");
		excelFileField.setToolTipText(
				"<html>Excel Datei für den Export aller gefundener Sprachen.</html>");

		resourceFileField.setEnabled(true);
		excelFileField.setEnabled(true);

		final JButton showResourceFileDialogButton = new JButton("...");
		final JButton showExcelFileDialogButton = new JButton("...");

		showResourceFileDialogButton.setEnabled(true);
		showExcelFileDialogButton.setEnabled(true);

		final JButton goButton = new JButton("Export");
		goButton.setEnabled(false);

		resourceFileField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (resourceFileField.getText() == null
						|| resourceFileField.getText().trim().equals("")) {
					goButton.setEnabled(false);
				}
				else {
					if (excelFileField.getText() != null
							&& !excelFileField.getText().trim().equals("")) {
						goButton.setEnabled(true);
					}
				}
			}
		});

		excelFileField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (excelFileField.getText() == null
						|| excelFileField.getText().trim().equals("")) {
					goButton.setEnabled(false);
				}
				else {
					if (resourceFileField.getText() != null
							&& !resourceFileField.getText().trim().equals("")) {
						goButton.setEnabled(true);
					}
				}
			}
		});

		goButton.addActionListener(e -> {

			if (Gui.this.exportMode) {
				EventBus.publish("clear", null);

				String excelFileName = excelFileField.getText();
				if (!excelFileName.toLowerCase().endsWith(".xlsx")
						&& !excelFileName.toLowerCase().endsWith(".xls")) {
					excelFileName = excelFileName + ".xls";
					excelFileField.setText(excelFileName);
				}

				try {
					ExportTool.exportExcel(resourceFileField.getText(),
							excelFileField.getText());
				}
				catch (IOException e11) {
					e11.printStackTrace();
				}
			}
			else {
				EventBus.publish("clear", null);
				ImportTool.importResourceFile(resourceFileField.getText(),
						excelFileField.getText());
			}

		});

		JPanel modePanel = new JPanel();
		modePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		ButtonGroup group = new ButtonGroup();
		JRadioButton importRadioButton = new JRadioButton("Import", false);
		JRadioButton exportRadioButton = new JRadioButton("Export", true);

		group.add(exportRadioButton);
		group.add(importRadioButton);

		modePanel.add(exportRadioButton);
		modePanel.add(importRadioButton);

		showResourceFileDialogButton.addActionListener(e -> {
			resourceFileChooser
					.setCurrentDirectory(new File(resourceFileField.getText()));

			if (resourceFileChooser
					.showOpenDialog(Gui.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = resourceFileChooser.getSelectedFile();
			resourceFileField.setText(file.getPath());

			if (excelFileField.getText() != null) {
				goButton.setEnabled(true);
			}
			else {
				goButton.setEnabled(false);
			}
		});

		showExcelFileDialogButton.addActionListener(e -> {
			excelFileChooser.setCurrentDirectory(new File(excelFileField.getText()));
			if (excelFileChooser
					.showOpenDialog(Gui.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = excelFileChooser.getSelectedFile();
			excelFileField.setText(file.getPath());

			if (resourceFileField.getText() != null) {
				goButton.setEnabled(true);
			}
			else {
				goButton.setEnabled(false);
			}
		});

		importRadioButton.addActionListener(e -> {
			goButton.setText("Import");
			Gui.this.exportMode = false;
			resourceFileField.setEnabled(true);
			excelFileField.setEnabled(true);
			showResourceFileDialogButton.setEnabled(true);
			showExcelFileDialogButton.setEnabled(true);
		});

		exportRadioButton.addActionListener(e -> {
			goButton.setText("Export");
			Gui.this.exportMode = true;
			resourceFileField.setEnabled(true);
			excelFileField.setEnabled(true);
			showResourceFileDialogButton.setEnabled(true);
			showExcelFileDialogButton.setEnabled(true);
		});

		int row = 1;

		builder.addLabel("Modus: ", cc.xy(1, row));
		builder.add(modePanel, cc.xy(3, row));

		row += 2;
		builder.addLabel("Resource File: ", cc.xy(1, row));
		builder.add(resourceFileField, cc.xy(3, row));
		builder.add(showResourceFileDialogButton, cc.xy(5, row));

		row += 2;
		builder.addLabel("Excel File (Master): ", cc.xy(1, row));
		builder.add(excelFileField, cc.xy(3, row));
		builder.add(showExcelFileDialogButton, cc.xy(5, row));

		row += 2;

		row += 2;
		row += 2;
		row += 2;
		row += 2;

		StatusTextArea infoArea = new StatusTextArea();
		JScrollPane scrollPane = new JScrollPane(infoArea);
		builder.add(scrollPane, cc.xywh(1, row, 5, 1));

		row += 2;
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(goButton);

		builder.add(buttonPanel, cc.xywh(1, row, 5, 1));

		getContentPane().add(builder.getPanel(), BorderLayout.CENTER);

		setSize(600, 300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		locateOnScreenCenter(this);
		setTitle("Resource-Tool");
		// pack();

	}

	public void shutdown() {
		System.exit(0);
	}

	private static void locateOnScreenCenter(Component component) {
		Dimension paneSize = component.getSize();
		Dimension screenSize = component.getToolkit().getScreenSize();
		component.setLocation((screenSize.width - paneSize.width) / 2,
				(screenSize.height - paneSize.height) / 2);
	}

	public static void main(String[] args) {
		new Gui().setVisible(true);
	}
}