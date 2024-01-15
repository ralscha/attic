

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.RAMDirectory;




public class LuceneTest {

  public static void main(String[] args) {
    
    try {
      
      RAMDirectory rd = new RAMDirectory();
      IndexWriter writer = new IndexWriter(rd, new SimpleAnalyzer(), true);
      Document doc = new Document();
      doc.add(Field.Text("txt", "one"));
      doc.add(Field.Text("txt", "two"));
      doc.add(Field.UnIndexed("id", "1"));
      writer.addDocument(doc);
      writer.optimize();
      writer.close();    
      Searcher searcher = new IndexSearcher(rd);          
      Query query = QueryParser.parse("one NOT two", "txt", new SimpleAnalyzer());     
      
      Hits hits = searcher.search(query); 
      for (int i = 0; i < hits.length(); i++) {
        System.out.println(hits.doc(i).get("id"));      
      }

    } catch (Exception e) {
      e.printStackTrace();
    } 
    
    
  }
}
