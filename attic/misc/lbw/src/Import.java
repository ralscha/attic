import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ch.ess.lbw.dao.LieferantDao;
import ch.ess.lbw.dao.WerkDao;
import ch.ess.lbw.model.Lieferant;
import ch.ess.lbw.model.LieferantWerk;
import ch.ess.lbw.model.Werk;

/**
 * @author sr
 */
public class Import {

  public static void main(String[] args) {

    try {
      File excelFile = new File("C:\\Documents and Settings\\Administrator\\Desktop\\2006-Q1_Entwurf.xls");

      ApplicationContext ap = new ClassPathXmlApplicationContext(new String[]{"/spring-datasource-local.xml",
          "/spring.xml", "/spring-mail.xml", "/spring-hibernate.xml", "/spring-init.xml"});

      SessionFactory sf = (SessionFactory)ap.getBean("sessionFactory");


      Session session = SessionFactoryUtils.getSession(sf, true);
      session.setFlushMode(FlushMode.NEVER);

      TransactionSynchronizationManager.bindResource(sf, new SessionHolder(session));

      LieferantDao lieferantDao = (LieferantDao)ap.getBean("lieferantDao");
      WerkDao werkDao = (WerkDao)ap.getBean("werkDao");

      FileInputStream fis = new FileInputStream(excelFile);
      Workbook workbook = Workbook.getWorkbook(fis);

      Sheet sheet = workbook.getSheet(0);

      int rowCount = sheet.getRows();

      for (int i = 4; i < rowCount; i++) {

        //id
        Cell cell = sheet.getCell(0, i);
        String nr = cell.getContents();

        if (StringUtils.isNumeric(nr)) {
          
          
          
          Lieferant lieferant = lieferantDao.find(nr);
          if (lieferant == null) {
            lieferant = new Lieferant();
          }
          
          lieferant.setNr(nr);
          
          cell = sheet.getCell(1, i);
          lieferant.setKurz(cell.getContents());
          
          cell = sheet.getCell(2, i);
          lieferant.setName(cell.getContents());
          
          cell = sheet.getCell(7, i);
          lieferant.setStrasse(cell.getContents());
          
          cell = sheet.getCell(6, i);
          lieferant.setOrt(cell.getContents());
          
          cell = sheet.getCell(5, i);
          lieferant.setPlz(cell.getContents());
          
          lieferantDao.save(lieferant);
        }

      }
      
      
      
      sheet = workbook.getSheet(8);

      rowCount = sheet.getRows();

      for (int i = 4; i < rowCount; i++) {
        Cell cell = sheet.getCell(0, i);
        String nr = cell.getContents();
        if (StringUtils.isNumeric(nr)) {
          Lieferant lieferant = lieferantDao.find(nr);
          if (lieferant != null) {
            
            //iso
            cell = sheet.getCell(2, i);
            lieferant.setIso(cell.getContents());
            
            cell = sheet.getCell(3, i);
            lieferant.setIsoGesellschaft(cell.getContents());

            cell = sheet.getCell(4, i);
            lieferant.setIsoDatum(cell.getContents());

            if (StringUtils.isNotBlank(lieferant.getIso())) {
              lieferant.setIsoPunkte(new BigDecimal("5.0"));
            } else {
              lieferant.setIsoPunkte(new BigDecimal("3.0"));
            }
                   
            //vda
            cell = sheet.getCell(6, i);
            if ("j".equalsIgnoreCase(cell.getContents())) {
              lieferant.setVda(true);
            } else {
              lieferant.setVda(false);
            }
            lieferant.setVdaBemerkung(sheet.getCell(7, i).getContents());
            
            if (lieferant.getVda()) {
              lieferant.setVdaPunkte(new BigDecimal("0.5"));
            } else {
              lieferant.setVdaPunkte(new BigDecimal("0.0"));
            }
            
            
            //qs
            cell = sheet.getCell(9, i);
            if ("j".equalsIgnoreCase(cell.getContents())) {
              lieferant.setQs(true);
            } else {
              lieferant.setQs(false);
            }
            lieferant.setQsBemerkung(sheet.getCell(10, i).getContents());
            
            if (lieferant.getQs()) {
              lieferant.setQsPunkte(new BigDecimal("0.5"));
            } else {
              lieferant.setQsPunkte(new BigDecimal("0.0"));
            }
            
            
            //poly
            cell = sheet.getCell(12, i);
            if ("j".equalsIgnoreCase(cell.getContents())) {
              lieferant.setPolytec(true);
            } else {
              lieferant.setPolytec(false);
            }
            lieferant.setPolytecBemerkung(sheet.getCell(13, i).getContents());
            
            if (lieferant.getPolytec()) {
              lieferant.setPolytecPunkte(new BigDecimal("5.0"));
            } else {
              lieferant.setPolytecPunkte(new BigDecimal("0.0"));
            }
            
            //other
            cell = sheet.getCell(19, i);
            if ("j".equalsIgnoreCase(cell.getContents())) {
              lieferant.setIso14001(true);
              lieferant.setIso14001Bemerkung(null);
            } else {
              lieferant.setIso14001(false);
              lieferant.setIso14001Bemerkung(cell.getContents());
            }

            cell = sheet.getCell(20, i);
            if ("j".equalsIgnoreCase(cell.getContents())) {
              lieferant.setOekoAudit(true);
              lieferant.setOekoAuditBemerkung(null);
            } else {
              lieferant.setOekoAudit(false);
              lieferant.setOekoAuditBemerkung(cell.getContents());
            }
            
            cell = sheet.getCell(21, i);
            if ("j".equalsIgnoreCase(cell.getContents())) {
              lieferant.setIsoTs16949(true);
              lieferant.setIsoTs16949Bemerkung(null);
            } else {
              lieferant.setIsoTs16949(false);
              lieferant.setIsoTs16949Bemerkung(cell.getContents());
            }
            
            
            lieferantDao.save(lieferant);
            
          
          }
        }
      }      
      
      importLieferantWerk(lieferantDao, werkDao, workbook, 3, "Geretsried");
      importLieferantWerk(lieferantDao, werkDao, workbook, 4, "Ebersdorf");
      importLieferantWerk(lieferantDao, werkDao, workbook, 5, "Nordhalben");
      importLieferantWerk(lieferantDao, werkDao, workbook, 6, "Wackersdorf");
      
      

      TransactionSynchronizationManager.unbindResource(sf);
      SessionFactoryUtils.releaseSession(session, sf);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private static void importLieferantWerk(LieferantDao lieferantDao, WerkDao werkDao, Workbook workbook, int sheetno, String werks) {
    Sheet sheet;
    int rowCount;
    List<Werk> werke = werkDao.find(werks);
    if (!werke.isEmpty()) {
      Werk werk = werke.iterator().next();
      sheet = workbook.getSheet(sheetno);
 
      rowCount = sheet.getRows();
 
      for (int i = 4; i < rowCount; i++) {
        Cell cell = sheet.getCell(0, i);
        String ja = cell.getContents();
        if ("j".equals(ja)) {
          String liefs = sheet.getCell(1, i).getContents();
          List<Lieferant> l = lieferantDao.findByCriteria(Restrictions.eq("kurz", liefs));
          if (!l.isEmpty()) {
            Lieferant lief = l.iterator().next();
            
            LieferantWerk userWerk = new LieferantWerk();
            userWerk.setLieferant(lief);
            userWerk.setWerk(werk);

            lief.getLieferantWerke().add(userWerk);
            werk.getLieferantWerke().add(userWerk);
            
            lieferantDao.save(lief);
          }
        }
      }
    }
  }
}