package ch.ess.cal.service.search;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Required;

import ch.ess.base.model.User;
import ch.ess.cal.dao.ContactDao;
import ch.ess.cal.model.Contact;
import ch.ess.spring.annotation.SpringBean;
import ch.ess.spring.annotation.SpringProperty;

@SpringBean(id = "searchEngine", initMethod = "init", destroyMethod = "shutDown")
public final class SearchEngine {

  private ExecutorService executor;
  private ContactDao contactDao;

  Directory directory;
  Analyzer analyzer;

  private SearchEngine() {
    executor = Executors.newSingleThreadExecutor();
  }

  @SpringProperty(ref = "contactDao")
  @Required
  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }

  private void updateIndex(Runnable job) {
    executor.execute(job);
  }

  public void init() throws IOException, NamingException {
    init(new StandardAnalyzer());
  }

  public void init(Analyzer luceneAnalyzer) throws IOException, NamingException {
    Context ctx = new InitialContext();
    String indexDir = null;    
    try {
      indexDir = (String)ctx.lookup("java:comp/env/app/indexDir");
    } catch (NameNotFoundException e) {
      //no action
    }    
    File tmpDir;
    if (StringUtils.isBlank(indexDir)) {
      tmpDir = SystemUtils.getJavaIoTmpDir();
      tmpDir = new File(tmpDir, "bug");
    } else {
      tmpDir = new File(indexDir);
    }
    
    
    boolean create = !tmpDir.exists();

    this.directory = FSDirectory.getDirectory(tmpDir);

    if (create) {      
      indexAll();
    }

    this.analyzer = luceneAnalyzer;
  }

  @SuppressWarnings("unchecked")
  public Set<String> search(String searchQuery, String showMode, User user) throws IOException, org.apache.lucene.queryParser.ParseException {
    if (StringUtils.isBlank(searchQuery)) {
      return Collections.EMPTY_SET;
    }

    Set<String> results = new HashSet<String>();

    Searcher searcher = new IndexSearcher(directory);
    try {
      
      String modSearchQuery;

      QueryParser parser = new QueryParser("text", analyzer);
      parser.setDefaultOperator(Operator.AND);

      if ("privateOnly".equals(showMode)) {
        modSearchQuery = "userId:" + user.getId().toString() + " AND " + searchQuery;
      } else if ("publicOnly".equals(showMode)) {
        modSearchQuery = "userId:public AND " + searchQuery;
      } else {
        modSearchQuery = "(userId:public OR userId:" + user.getId().toString() + ")";
        modSearchQuery = modSearchQuery + " AND " + searchQuery;
      }
      
      System.out.println(modSearchQuery);
      
      Query query = parser.parse(modSearchQuery);
      Hits hits = searcher.search(query);

      if ((!modSearchQuery.endsWith("*") && hits.length() == 0)) {
        //search with *
        query = parser.parse(modSearchQuery + "*");
        hits = searcher.search(query);
      }

      if (hits.length() > 0) {
        for (int i = 0; i < hits.length(); i++) {
          String idStr = hits.doc(i).get("id");
          results.add(idStr);
        }
      }

      return results;

    } finally {
      if (searcher != null) {
        searcher.close();
      }
    }

  }

  public void indexAll() {
    final List<Document> documentList = contactDao.listAllContact();
      
    Runnable job = new Runnable() {

      public void run() {
        IndexWriter writer = null;
        try {
          writer = new IndexWriter(directory, analyzer, true);
          
          for (Document document : documentList) {
            writer.addDocument(document);
          }
          
        } catch (IOException e) {
          LogFactory.getLog(getClass()).error("createNewIndex", e);
        } finally {
          if (writer != null) {
            try {
              writer.close();
            } catch (IOException e) {
              LogFactory.getLog(getClass()).error("ocreateNewIndexptimize", e);
            }
          }
        }

      }

    };

    updateIndex(job);
  
  }

  public void createNewIndex() {
    Runnable job = new Runnable() {

      public void run() {
        IndexWriter writer = null;
        try {
          writer = new IndexWriter(directory, analyzer, true);
        } catch (IOException e) {
          LogFactory.getLog(getClass()).error("createNewIndex", e);
        } finally {
          if (writer != null) {
            try {
              writer.close();
            } catch (IOException e) {
              LogFactory.getLog(getClass()).error("ocreateNewIndexptimize", e);
            }
          }
        }

      }

    };

    updateIndex(job);

  }

  public void optimize() {
    Runnable job = new Runnable() {

      public void run() {
        IndexWriter writer = null;
        try {
          writer = new IndexWriter(directory, analyzer, false);
          writer.optimize();
        } catch (IOException e) {
          LogFactory.getLog(getClass()).error("optimize", e);
        } finally {
          if (writer != null) {
            try {
              writer.close();
            } catch (IOException e) {
              LogFactory.getLog(getClass()).error("optimize", e);
            }
          }
        }

      }

    };

    updateIndex(job);

  }

  public void addContact(Contact contact) {
    ContactDocument contactDocument = new ContactDocument(contact);
    final Document document = contactDocument.getDocument();

    deleteContactDocument(contact.getId().toString());

    Runnable job = new Runnable() {

      public void run() {
        IndexWriter writer = null;
        try {
          writer = new IndexWriter(directory, analyzer, false);
          writer.addDocument(document);
        } catch (IOException e) {
          LogFactory.getLog(getClass()).error("addContact", e);
        } finally {
          if (writer != null) {
            try {
              writer.close();
            } catch (IOException e) {
              LogFactory.getLog(getClass()).error("addContact", e);
            }
          }
        }

      }

    };

    updateIndex(job);

  }

  public void deleteContactDocument(final String id) {
    Runnable job = new Runnable() {

      public void run() {
        IndexReader reader = null;
        try {
          reader = IndexReader.open(directory);
          Term term = new Term("id", id);
          reader.deleteDocuments(term);
        } catch (IOException e) {
          LogFactory.getLog(getClass()).error("delete term", e);
        } finally {
          if (reader != null) {
            try {
              reader.close();
            } catch (IOException e) {
              LogFactory.getLog(getClass()).error("delete term", e);
            }
          }
        }

      }

    };

    updateIndex(job);
  }
  
  
  public void deleteContactDocumentSync(final String id) {
   
    IndexReader reader = null;
    try {
      reader = IndexReader.open(directory);
      Term term = new Term("id", id);
      reader.deleteDocuments(term);
    } catch (IOException e) {
      LogFactory.getLog(getClass()).error("delete term", e);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          LogFactory.getLog(getClass()).error("delete term", e);
        }
      }
    }

  }

  //  public static String removeTermChars(String s) {
  //
  //    StringBuffer sb = new StringBuffer();
  //    char[] arr = s.toCharArray();
  //
  //    for (int i = 0; i < arr.length; i++) {
  //      if ((arr[i] != '+') && (arr[i] != '-') && (arr[i] != '/') && (arr[i] != '\\') && (arr[i] != '?')
  //          && (arr[i] != '*')) {
  //        sb.append(arr[i]);
  //      }
  //    }
  //
  //    return sb.toString();
  //  }

  //    public static String removeQueryTermChars(String s) {
  //
  //      StringBuffer sb = new StringBuffer();
  //      char[] arr = s.toCharArray();
  //
  //      for (int i = 0; i < arr.length; i++) {
  //        if ((arr[i] != '+') && (arr[i] != '-') && (arr[i] != '/') && (arr[i] != '\\')) {
  //          sb.append(arr[i]);
  //        }
  //      }
  //
  //      return sb.toString();
  //    }
  //
  //    public static String cleanTerm(String s) {
  //
  //      StringTokenizer st = new StringTokenizer(s, " ", true);
  //      StringBuffer sb = new StringBuffer();
  //
  //      while (st.hasMoreTokens()) {
  //        String token = st.nextToken();
  //
  //        if (!" ".equals(token)) {
  //          if (token.startsWith("+")) {
  //            token = "+" + removeQueryTermChars(token.substring(1));
  //          } else {
  //            if (token.startsWith("-")) {
  //              token = "-" + removeQueryTermChars(token.substring(1));
  //            } else {
  //              token = removeQueryTermChars(token);
  //            }
  //          }
  //        }
  //
  //        sb.append(token);
  //      }
  //
  //      return sb.toString();
  //    }

  public void shutDown() {
    executor.shutdown();
  }

}
