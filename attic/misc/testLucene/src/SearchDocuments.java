import java.io.IOException;
import java.sql.Connection;
import net.sourceforge.jtds.jdbcx.JtdsDataSource;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.datasource.DataSourceUtils;
import org.apache.lucene.store.jdbc.dialect.SQLServerDialect;

public class SearchDocuments {

  public static void main(String[] args) throws CorruptIndexException, IOException, ParseException {
    
    JtdsDataSource ds = new JtdsDataSource();
    ds.setUser("sa");
    ds.setDatabaseName("lucene");
    ds.setServerName("localhost");
//
//    //Connection conn = DataSourceUtils.getConnection(ds);

    
    JdbcDirectory jdbcDir = new JdbcDirectory(ds, new SQLServerDialect(), "indexTable");
    
    long start = System.currentTimeMillis();
    int count = 0;
    for (int i = 0; i < 50; i++) {
      
      Searcher searcher = new IndexSearcher(jdbcDir /*"D:/workspace/TestLucene/ix"*/);
  
      QueryParser parser = new QueryParser("text", new SimpleAnalyzer());
      parser.setDefaultOperator(QueryParser.AND_OPERATOR);
      parser.setAllowLeadingWildcard(true);
  
      Query query = parser.parse("+seam -spring");
  
      Hits hits = searcher.search(query);
  
      
      for (int n = 0; n < hits.length(); n++) {
        //System.out.println(hits.doc(n).get("fileName"));
        count++;
      }
      //DataSourceUtils.commitConnectionIfPossible(conn); 
    }
    
    
    
    System.out.println((System.currentTimeMillis()-start) + " ms");
    //DataSourceUtils.releaseConnection(conn);

    jdbcDir.close();

    
    System.out.println("FOUND: " + count);
  }

}
