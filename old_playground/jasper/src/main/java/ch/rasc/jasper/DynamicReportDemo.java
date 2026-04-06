package ch.rasc.jasper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Date;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class DynamicReportDemo {

	public static void main(String[] args) throws JRException, ColumnBuilderException,
			ClassNotFoundException, IOException {
		FastReportBuilder drb = new FastReportBuilder();
		DynamicReport dr = drb.addColumn("State", "state", String.class.getName(), 30)
				.addColumn("Branch", "branch", String.class.getName(), 30)
				.addColumn("Product Line", "productLine", String.class.getName(), 50)
				.addColumn("Item", "item", String.class.getName(), 50)
				.addColumn("Item Code", "id", Long.class.getName(), 30, true)
				.addColumn("Quantity", "quantity", Long.class.getName(), 60, true)
				.addColumn("Amount", "amount", Float.class.getName(), 70, true)
				.addGroups(2).setTitle("November 2006 sales report")
				.setSubtitle("This report was generated at " + new Date())
				.setPrintBackgroundOnOddRows(true).setUseFullPageWidth(true).build();

		JRDataSource ds = new JRBeanCollectionDataSource(
				TestRepositoryProducts.getDummyCollection());
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr,
				new ClassicLayoutManager(), ds);
		// JasperViewer.viewReport(jp); // finally display the report report

		JRPdfExporter exporter = new JRPdfExporter();
		File outputFile = new File("./dummy.pdf");
		OutputStream fos = Files.newOutputStream(outputFile.toPath());
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fos));
		exporter.setExporterInput(new SimpleExporterInput(jp));
		exporter.exportReport();

	}

}