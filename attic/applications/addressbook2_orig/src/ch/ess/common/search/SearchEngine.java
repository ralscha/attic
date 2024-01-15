package ch.ess.common.search;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import EDU.oswego.cs.dl.util.concurrent.QueuedExecutor;

/**
 * Search engine methods for Lucene.
 *
 * @author  Ted Husted
 * @version 1.1 2003/07/10 09:26:21
 */
public final class SearchEngine {

  private static final Log LOG = LogFactory.getLog(SearchEngine.class);

  private QueuedExecutor executor;
  private static SearchEngine instance = new SearchEngine();

  private String indexPath;
  private Analyzer analyzer;

  private SearchEngine() {
    executor = new QueuedExecutor();
  }

  public static void updateIndex(Runnable job) throws InterruptedException {
    instance.executor.execute(job);
  }

  /**
   * Return path for index.
   */
  public static String getIndexPath() {
    return instance.indexPath;
  }

  /**
   * Set path for index.
   * MUST be set at startup before using other methods.
   */
  public static void init(String indexPath, Analyzer analyzer) {
    instance.indexPath = indexPath;
    instance.analyzer = analyzer;
  }

  public static void init(String indexPath) {
    init(indexPath, new StandardAnalyzer());
  }

  /**
   * Return default analyzer for application
   * A non-English site should use a different analyzer
   *
   * @return The default analyzer
   * @exception Throws ResourceException on IO error
   */
  public static final Analyzer getAnalyzer() {
    return instance.analyzer;
  }

  /**
   * Return default writer for application.
   *
   * @param Set to true to create a new index; usually false.
   * @return default writer
   * @exception Throws IOException on IO error
   */
  public static final IndexWriter getIndexWriter(boolean create) throws IOException {

    return (new IndexWriter(getIndexPath(), getAnalyzer(), create));

  } // end getIndexWriter()

  /**
   * Object for synchronizing deletions to index
   */
  private static final Object INDEX_LOCK = new Object();

  /**
   * Delete record matching term from default index.
   * Access to the index is synchronized.
   *
   * @param term The term matching the entry to delete
   * @exception Throws ResourceException on IO error
   */
  public static final int deleteTerm(Term term) throws IOException {

    int result = 0;
    synchronized (INDEX_LOCK) {
      IndexReader reader = null;
      try {
        reader = IndexReader.open(getIndexPath());
        result = reader.delete(term);

      } finally {
        try {
          if (reader != null) {
            reader.close();
          }
        } catch (IOException e) {
          // do nothing
          LOG.error("delete term", e);
        }
      }
    }
    return result;

  } // end deleteTerm()

  //Utility methods

  public static String removeTermChars(String s) {

    StringBuffer sb = new StringBuffer();
    char[] arr = s.toCharArray();

    for (int i = 0; i < arr.length; i++) {
      if ((arr[i] != '+')
        && (arr[i] != '-')
        && (arr[i] != '/')
        && (arr[i] != '\\')
        && (arr[i] != '?')
        && (arr[i] != '*')) {
        sb.append(arr[i]);
      }
    }

    return sb.toString();
  }

  public static String removeQueryTermChars(String s) {

    StringBuffer sb = new StringBuffer();
    char[] arr = s.toCharArray();

    for (int i = 0; i < arr.length; i++) {
      if ((arr[i] != '+') && (arr[i] != '-') && (arr[i] != '/') && (arr[i] != '\\')) {
        sb.append(arr[i]);
      }
    }

    return sb.toString();
  }

  public static String cleanTerm(String s) {

    StringTokenizer st = new StringTokenizer(s, " ", true);
    StringBuffer sb = new StringBuffer();

    while (st.hasMoreTokens()) {
      String token = st.nextToken();

      if (!" ".equals(token)) {
        if (token.startsWith("+")) {
          token = "+" + removeQueryTermChars(token.substring(1));
        } else {
          if (token.startsWith("-")) {
            token = "-" + removeQueryTermChars(token.substring(1));
          } else {
            token = removeQueryTermChars(token);
          }
        }
      }

      sb.append(token);
    }

    return sb.toString();
  }

  public static void shutDown() {
    instance.executor.shutdownAfterProcessingCurrentlyQueuedTasks();
  }

}
