package ch.ess.task.web.task.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

import ch.ess.common.Constants;
import ch.ess.common.search.SearchEngine;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class SearchTask {

  public static List search(String searchQuery) throws org.apache.lucene.queryParser.ParseException, IOException {
    if (searchQuery == null) {
      return null;
    }

    searchQuery = SearchEngine.cleanTerm(searchQuery);
    searchQuery = searchQuery.toLowerCase();

    Searcher searcher = new IndexSearcher(SearchEngine.getIndexPath());
    try {

      QueryParser parser = new QueryParser(Constants.SEARCH_TEXT, SearchEngine.getAnalyzer());
      parser.setOperator(QueryParser.DEFAULT_OPERATOR_AND);

      Query query = parser.parse(searchQuery);

      Hits hits = searcher.search(query);
      
      

      if (hits.length() > 0) {
        
        List result = new ArrayList();  

        for (int i = 0; i < hits.length(); i++) {
          result.add(new Long(hits.doc(i).get(Constants.SEARCH_ID)));
        }
        
        return result;
      }

      return null;

    } finally {
      if (searcher != null) {
        searcher.close();
      }
    }

  }

}
