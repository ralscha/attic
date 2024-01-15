package test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import inetsoft.report.*;
import inetsoft.report.style.*;
import inetsoft.report.painter.*;
import inetsoft.report.io.*;
import inetsoft.report.lens.*;
import inetsoft.report.lens.*;
import inetsoft.report.filter.*;
import inetsoft.report.filter.style.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import inetsoft.report.*;
import inetsoft.report.lens.swing.*;
import inetsoft.report.pdf.*;
import com.objectmatter.bsf.*;
import ch.ess.pbroker.db.*;
import ch.ess.pbroker.resource.*;
import ch.ess.util.pool.*;

public class ReportServlet extends HttpServlet {
	
  public void init() {
    AppConfig.setSchema("pbroker.schema");
  }

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		ServletOutputStream out = res.getOutputStream();
		//PrintWriter out = res.getWriter();						

    res.setHeader( "Pragma", "no-cache" );
    res.setHeader( "Cache-Control", "no-cache" );
    res.setHeader( "Cache-Control", "no-store" );
    res.setDateHeader( "Expires", 0 );


      Database db = PoolManager.requestDatabase();
try {
      Vertraege v = (Vertraege)db.lookup(Vertraege.class, 1581);
      Mitarbeiterbeurteilungvertrag[] mbv = v.getMitarbeiterbeurteilungVertrag();

      try {

        MABReport mabReport = new MABReport(mbv[0].getMitarbeiterbeurteilung(), v);
        XStyleSheet report = mabReport.createReport();


	    String typ = req.getParameter("typ");
			if (typ != null) {
        
	      if ("excel".equals(typ)) {
			    res.setContentType("application/vnd.ms-excel");
			    res.addHeader("Content-disposition","filename=mab.xls");
					ExcelGenerator gen = new ExcelGenerator(out);
          gen.setPageSize(new Dimension(792,648));
					gen.generate(report);
	      } else if ("excel2".equals(typ)) {
	        res.setContentType("application/vnd.ms-excel");
	        res.addHeader("Content-disposition","attachment;filename=mab.xls");

	        DefaultTableModel dtm = new DefaultTableModel();	 
	        dtm.setColumnIdentifiers(new String[]{"ID", "Kandidat", "Alter", "Pensum"});

          Object[] arr = new Object[4];
          for (int i = 0; i < 10; i++) {
            arr[0] = new Integer(i);
            arr[1] = "Franz Müller";
            arr[2] = "100";
            arr[3] = "100 %";
            dtm.addRow(arr);
          }

          TableModelLens lens = new TableModelLens(dtm);
          lens.setColAutoSize(true);
          lens.setRowAutoSize(true);
          StyleSheet rep = new StyleSheet();
          rep.setMargin(new Margin(0,0,0,0));
          rep.setCurrentTableLayout(StyleSheet.TABLE_FIT_PAGE);
          rep.addTable(lens);




	        ExcelGenerator gen = new ExcelGenerator(out);
	        //gen.setPageSize(new Dimension(792,648));
	        gen.generate(rep);	      
				} else if ("pdf".equals(typ)) {			
            res.setContentType("application/pdf");
            res.setHeader("extension", "pdf");
            res.setHeader("Content-disposition", "attachment;filename=report.pdf");

					ReportEnv.setProperty("font.truetype.path", "C:/WINNT/Fonts");

          //File tempFile = File.createTempFile("pdf", "pdf");
          //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
          ByteArrayOutputStream bos = new ByteArrayOutputStream();


          PDF3Generator pdf3generator = PDF3Generator.getPDFGenerator(bos);
          pdf3generator.setPageSize(new Size(11.69,8.27));
          pdf3generator.generate(report);
          /*
					PDF3Printer pdf = new PDF3Printer(bos);
          //pdf.setAsciiOnly(true);
					pdf.setPageSize(11.69,8.27);
					report.print(pdf.getPrintJob());
					pdf.getPrintJob().end();
          bos.close();
          */
          bos.close();
          byte[] content = bos.toByteArray();
          //ByteArrayInputStream bis = new ByteArrayInputStream(content);

          //Umkopieren
          //FileInputStream source = new FileInputStream(tempFile);
          //int size = source.available();
				  //byte[] buffer = new byte[size];
				  res.setContentLength(content.length);

          out.write(content);
          out.flush();
          /*
          int bytes_read;
				  while (true) {
					  bytes_read = source.read(buffer);
					  if (bytes_read == -1)
						  break;
					  out.write(buffer, 0, bytes_read);
            System.out.println("hier");
				  }
          */
          //source.close();
          //tempFile.delete();
          //out.close();
					//report.finalize();


				} else if ("ps".equals(typ)) {	
			    res.setContentType("application/postscript");
			    res.addHeader("Content-disposition","filename=mab.ps");		
					PSPrinter ps = new PSPrinter(out);
          ps.setPageSize(11.69,8.27);
					report.print(ps.getPrintJob());
					ps.getPrintJob().end();			    			
				} else if ("csv".equals(typ)) {
			    res.setContentType("text/comma-separated-values");
			    res.addHeader("Content-disposition","filename=mab.csv");
					Builder builder = Builder.getBuilder(Builder.CSV, out);
					builder.write(report);
				} else if ("rtf".equals(typ)) {
				  res.setContentType("application/rtf");
			    res.addHeader("Content-disposition","attachment;filename=mab.rtf");
          RTFFormatter rtfformatter = new RTFFormatter(out);
          rtfformatter.setPageSize(11.69,8.27);
					Builder builder = new Builder(rtfformatter); //Builder.getBuilder(Builder.RTF, out);
					builder.write(report);
				} else { //html
					Builder builder = Builder.getBuilder(Builder.HTML, out);
					builder.write(report);
					
				}
				
			}
      } catch (IOException ioe) {
        System.err.println(ioe);
      }   

		  out.close();

    }  finally {
      PoolManager.returnDatabase(db);
    }
	}


}