package ch.rasc.mongodb.author;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

@Named
public class TextExtractor {

	public List<String> extractWords(Path file) {
		List<String> words = new ArrayList<>();

		try {
			String text = IOUtils.toString(Files.newBufferedReader(file));

			if (text != null) {
				for (String word : text.split("\\s")) {
					word = word.replaceAll(";|:|,|\\.", "");
					if (!word.trim().isEmpty()) {
						words.add(word);
					}
				}
			}
		}
		catch (IOException e) {
			LoggerFactory.getLogger(TextExtractor.class).error("extract words", e);
		}

		return words;
	}

}
