package ch.rasc.jasper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class Main2 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		JasperPrint jasperPrint = null;

		try (InputStream is = Main2.class.getResourceAsStream("/hgt.jasper")) {

			Map<String, Object> params = new HashMap<>();

			params.put("datum", "05.04.2016");
			params.put("art", "Meisterschaft");
			params.put("gegner", "Heimisberg");

			jasperPrint = JasperFillManager.fillReport(is, params,
					new JREmptyDataSource());

			try (OutputStream out = Files.newOutputStream(Paths.get("./hgt.pdf"))) {
				JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			}

		}
		catch (JRException ex) {
			ex.printStackTrace();
		}
	}

}
