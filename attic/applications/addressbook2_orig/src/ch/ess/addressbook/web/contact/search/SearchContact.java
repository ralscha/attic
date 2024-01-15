package ch.ess.addressbook.web.contact.search;

import java.io.IOException;

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
public class SearchContact {

  public static String search(String searchQuery) throws org.apache.lucene.queryParser.ParseException, IOException {
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

      if ((!searchQuery.endsWith("*") && hits.length() == 0)) {
        //search with *
        query = parser.parse(searchQuery + "*");
        hits = searcher.search(query);        
      }

      if (hits.length() > 0) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < hits.length(); i++) {
          sb.append(hits.doc(i).get(Constants.SEARCH_ID));
          sb.append(",");
        }

        String result = sb.toString();
        return result.substring(0, result.length() - 1);
      }

      return null;

    } finally {
      if (searcher != null) {
        searcher.close();
      }
    }

  }

}

