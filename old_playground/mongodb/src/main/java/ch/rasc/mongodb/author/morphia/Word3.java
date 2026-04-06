package ch.rasc.mongodb.author.morphia;

import org.mongodb.morphia.annotations.Entity;

@Entity(noClassnameStored = true)
public class Word3 {
	private String word;

	private int count;

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
