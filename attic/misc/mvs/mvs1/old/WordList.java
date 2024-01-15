public class WordList {

	private int hash;
	private String word;

	public WordList(int hash, String word) {
		this.hash = hash;
		this.word = word;
	}

	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}


	public String toString() {
		return "WordList("+ hash + " " + word+")";
	}
}
