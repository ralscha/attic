package ch.rasc.jasper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		JasperPrint jasperPrint = null;

		// JasperCompileManager.compileReportToFile("report.jrxml");
		try (InputStream is = Main.class.getResourceAsStream("/report.jasper")) {

			jasperPrint = JasperFillManager.fillReport(is, new HashMap<>(),
					new JRTableModelDataSource(tableModelData()));

			// JasperViewer jasperViewer = new JasperViewer(jasperPrint);
			// jasperViewer.setVisible(true);

			try (OutputStream out = Files.newOutputStream(Paths.get("./test.pdf"))) {
				JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			}

			File destFile = new File("test.xls");

			JRXlsExporter exporter = new JRXlsExporter();

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(false);
			exporter.setConfiguration(configuration);

			exporter.exportReport();

		}
		catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	private static DefaultTableModel tableModelData() {
		String[] columnNames = { "Id", "Name", "Department", "Email" };
		String[][] data = { { "111", "G Conger", " Orthopaedic", "jim@wheremail.com" },
				{ "222", "A Date", "ENT", "adate@somemail.com" },
				{ "333", "R Linz", "Paedriatics", "rlinz@heremail.com" },
				{ "444", "V Sethi", "Nephrology", "vsethi@whomail.com" },
				{ "555", "K Rao", "Orthopaedics", "krao@whatmail.com" },
				{ "666", "V Santana", "Nephrology", "vsan@whenmail.com" },
				{ "777", "J Pollock", "Nephrology", "jpol@domail.com" },
				{ "888", "H David", "Nephrology", "hdavid@donemail.com" },
				{ "999", "P Patel", "Nephrology", "ppatel@gomail.com" },
				{ "101", "C Comer", "Nephrology", "ccomer@whymail.com" } };
		return new DefaultTableModel(data, columnNames);
	}
}
