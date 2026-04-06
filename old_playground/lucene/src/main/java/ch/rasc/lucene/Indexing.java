package ch.rasc.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;

public class Indexing {

	public static void main(String[] args)
			throws CorruptIndexException, LockObtainFailedException, IOException {

		try (Directory directory = new RAMDirectory();
				WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer();
				IndexWriter writer = new IndexWriter(directory,
						new IndexWriterConfig(analyzer))) {

			String[] ids = { "1", "2" };
			String[] unindexed = { "Netherlands", "Italy" };
			String[] unstored = { "Amsterdam has lots of bridges",
					"Venice has lots of canals" };
			String[] text = { "Amsterdam", "Venice" };

			for (int i = 0; i < ids.length; i++) {
				Document doc = new Document();
				doc.add(new Field("id", ids[i], StringField.TYPE_STORED));
				doc.add(new StoredField("country", unindexed[i]));
				doc.add(new TextField("contents", unstored[i], Field.Store.NO));
				doc.add(new TextField("city", text[i], Field.Store.YES));
				writer.addDocument(doc);
			}

			try (IndexReader reader = DirectoryReader.open(writer, true, true)) {
				IndexSearcher searcher = new IndexSearcher(reader);
				Term t = new Term("city", "Amsterdam");
				Query query = new TermQuery(t);
				TopDocs topDocs = searcher.search(query, 1000);
				for (ScoreDoc doc : topDocs.scoreDocs) {
					Document d = searcher.doc(doc.doc);
					System.out.println(d.get("id"));
				}
			}

		}

	}

}
