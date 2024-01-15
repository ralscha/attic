import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.jtds.jdbcx.JtdsDataSource;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.datasource.DataSourceUtils;
import org.apache.lucene.store.jdbc.dialect.SQLServerDialect;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

public class IndexDocuments {

  public static void main(String[] args) throws IOException {

//    JtdsDataSource ds = new JtdsDataSource();
//    ds.setUser("sa");
//    ds.setDatabaseName("lucene");
//    ds.setServerName("localhost");
//
//    Connection conn = DataSourceUtils.getConnection(ds);
//    
//    JdbcDirectory jdbcDir = new JdbcDirectory(ds, new SQLServerDialect(), "indexTable");
//    jdbcDir.create();

    long start = System.currentTimeMillis();
    
    IndexWriter ix = new IndexWriter(/*jdbcDir*/ "D:/workspace/TestLucene/ix", new StandardAnalyzer());
    ix.setUseCompoundFile(false);

    File docDir = new File("C:/movies/books");
    //File docDir = new File("C:\\Documents and Settings\\sr\\Desktop\\DOCS\\seam_dallen_meap_revised.pdf");
    List<File> pdfFileList = new ArrayList<File>();

    listPdfFiles(pdfFileList, docDir);

    for (File file : pdfFileList) {
      System.out.println(file.getName());

      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
      PDDocument pdDoc = null;
      try {
        pdDoc = PDDocument.load(bis);
        if (!pdDoc.isEncrypted()) {
          PDFTextStripper stripper = new PDFTextStripper();
          String docText = stripper.getText(pdDoc);

          Document doc = new Document();

          doc.add(new Field("text", docText, Field.Store.NO, Field.Index.TOKENIZED));
          doc.add(new Field("fileName", file.getPath(), Field.Store.YES, Field.Index.UN_TOKENIZED));

          ix.addDocument(doc);
          //DataSourceUtils.commitConnectionIfPossible(conn);

        } else {
          System.out.println(file.getName() + " is encrypted");
        }
      } catch (IOException ioe) {
        System.out.println(ioe.toString());
      } finally {
        if (pdDoc != null) {
          pdDoc.close();
        }
        bis.close();
      }

      
      
    }

    //ix.optimize();
    ix.close();
    
    System.out.println((System.currentTimeMillis()-start) + " ms");

    
    //DataSourceUtils.releaseConnection(conn);

  }

  private static void listPdfFiles(List<File> pdfFileList, File docDir) {
    if (docDir.isDirectory()) {
      File files[] = docDir.listFiles(new PdfFilter());
      for (File file : files) {
        if (file.isDirectory()) {
          listPdfFiles(pdfFileList, file);
        } else {
          pdfFileList.add(file);
        }
      }
    } else {
      pdfFileList.add(docDir);
    }
  }

}
