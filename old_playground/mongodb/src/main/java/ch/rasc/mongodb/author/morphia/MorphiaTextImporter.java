package ch.rasc.mongodb.author.morphia;

import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import ch.rasc.mongodb.author.TextExtractor;
import ch.rasc.mongodb.author.TextImporter;

@Named
public class MorphiaTextImporter implements TextImporter {

	@Inject
	private Datastore datastore;

	@Inject
	private TextExtractor extractor;

	@Override
	public void doImport(Path file) {
		List<String> words = this.extractor.extractWords(file);

		for (int i = 0; i < words.size() - 3; i++) {
			String w1 = words.get(i);
			String w2 = words.get(i + 1);
			// String w3 = words.get(i + 2);

			// Upsert base document. If exists increment count, otherwise insert
			// document
			Query<Word12> query = this.datastore.createQuery(Word12.class);
			query.field("word1").equal(w1);
			query.field("word2").equal(w2);
			UpdateOperations<Word12> op = this.datastore
					.createUpdateOperations(Word12.class).inc("count");
			this.datastore.update(query, op, true);

			// // update count in embedded document
			// query = new Document();
			// query.append("word1", w1);
			// query.append("word2", w2);
			// query.append("word3.word", w3);
			// update = new Document("$inc", new
			// Document("word3.$.count", 1));
			//
			// WriteResult result = collection.update(query, update, false,
			// false);
			// if (result.getN() == 0) {
			//
			// // add embedded word3 document to the array
			// query = new Document();
			// query.append("word1", w1);
			// query.append("word2", w2);
			//
			// Document word3 = new Document();
			// word3.append("word", w3);
			// word3.append("count", 1);
			//
			// collection.update(query, new Document("$push", new
			// Document("word3", word3)));
			// }

		}

	}

}
