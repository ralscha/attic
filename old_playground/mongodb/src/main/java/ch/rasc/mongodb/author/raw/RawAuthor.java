package ch.rasc.mongodb.author.raw;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import org.bson.Document;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import ch.rasc.mongodb.author.Author;

@Named
public class RawAuthor implements Author {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				"ch.rasc.mongodb.author")) {
			RawAuthor author = ctx.getBean("author", RawAuthor.class);
			author.writeText(1000);
		}
	}

	@Inject
	private MongoCollection<Document> collection;

	private final Random random;

	public RawAuthor() {
		this.random = new Random();
	}

	@Override
	public String writeText(int maxWords) {

		int skip = (int) (Math.random() * Math.min(100, this.collection.count()));
		Document start = this.collection.find().skip(skip).first();

		StringBuilder sb = new StringBuilder();
		String w1 = (String) start.get("word1");
		sb.append(w1);
		sb.append(" ");
		String w2 = (String) start.get("word2");
		sb.append(w2);
		sb.append(" ");

		int count = 2;
		int length = sb.length();
		String next = getNext(w1, w2);
		while (next != null && count < maxWords) {
			sb.append(next).append(" ");
			length = length + next.length() + 1;
			if (length > 60) {
				sb.append("\n");
				length = 0;
			}

			w1 = w2;
			w2 = next;
			next = getNext(w1, w2);

			count++;
		}

		return sb.toString();

	}

	private String getNext(String w1, String w2) {
		Document result = this.collection
				.find(Filters.and(Filters.eq("word1", w1), Filters.eq("word2", w2)))
				.first();

		if (result != null) {
			@SuppressWarnings("unchecked")
			List<Document> word3 = (List<Document>) result.get("word3");

			int total = (Integer) result.get("count");
			int rnd = this.random.nextInt(total) + 1;
			int sum = 0;

			Document dbObj = null;
			for (int i = 0; i < word3.size() && sum <= rnd; i++) {
				dbObj = word3.get(i);
				sum += (Integer) dbObj.get("count");
			}

			if (dbObj != null) {
				return (String) dbObj.get("word");
			}

		}

		return null;

	}
}
