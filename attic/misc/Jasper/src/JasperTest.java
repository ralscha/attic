
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class JasperTest {

  public static void main(String[] args) {
    try {

      
      JasperReport report = JasperCompileManager.compileReport(JasperTest.class.getResourceAsStream("/new_report2.jrxml"));

      
      report.
      Map m = new HashMap();
      m.put("name", "Ich");
      JasperPrint print = JasperFillManager.fillReport(report, m, new MyJRDataSource());

      
      
      JasperExportManager.exportReportToPdfFile(print, "c:/temp/testjasper.pdf");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}