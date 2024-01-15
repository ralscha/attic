import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

public class TestRead {

  public static void main(String[] args) {

    try {
      Searcher searcher = new IndexSearcher("D:/workspace/MP3Search/ix");

      String[] fields = {"album", "albumTitle", "artist", "songTitle", "title", "year", "fileName"};
      MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
      
      //QueryParser parser = new QueryParser("songTitle", new StandardAnalyzer());
      parser.setDefaultOperator(QueryParser.AND_OPERATOR);
      parser.setAllowLeadingWildcard(true);

      Query query = parser.parse("simon");

      Hits hits = searcher.search(query);
      if (hits.length() > 0) {
        for (int i = 0; i < hits.length(); i++) {
          System.out.println(hits.doc(i).get("fileName"));
        }
      } else {
        System.out.println("NOT FOUND");
      }
      
      searcher.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
