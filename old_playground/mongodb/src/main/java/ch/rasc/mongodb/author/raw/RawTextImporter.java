package ch.rasc.mongodb.author.raw;

import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

import ch.rasc.mongodb.author.TextExtractor;
import ch.rasc.mongodb.author.TextImporter;

@Named
public class RawTextImporter implements TextImporter {

	@Inject
	private MongoCollection<Document> collection;

	@Inject
	private TextExtractor extractor;

	@Override
	public void doImport(Path file) {

		List<String> words = this.extractor.extractWords(file);

		Document indexes = new Document("word1", 1).append("word2", 1);
		this.collection.createIndex(indexes);

		for (int i = 0; i < words.size() - 3; i++) {
			String w1 = words.get(i);
			String w2 = words.get(i + 1);
			String w3 = words.get(i + 2);

			// Upsert base document. If exists increment count, otherwise insert
			// document
			this.collection.updateOne(
					Filters.and(Filters.eq("word1", w1), Filters.eq("word2", w2)),
					new Document("$inc", new Document("count", 1)),
					new UpdateOptions().upsert(true));

			// update count in embedded document
			Document d = this.collection.findOneAndUpdate(
					Filters.and(Filters.eq("word1", w1), Filters.eq("word2", w2),
							Filters.eq("word3.word", w3)),
					new Document("$inc", new Document("word3.$.count", 1)));
			if (d == null) {

				Document word3 = new Document();
				word3.append("word", w3);
				word3.append("count", 1);

				this.collection.updateOne(
						Filters.and(Filters.eq("word1", w1), Filters.eq("word2", w2)),
						new Document("$push", new Document("word3", word3)));
			}

		}

	}

}
