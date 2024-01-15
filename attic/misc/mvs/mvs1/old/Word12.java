public class Word12 {

	private int word1;
	private int word2;
	private int word3ref;
	private int total;

	public Word12(int word1, int word2, int word3ref, int total) {
		this.word1 = word1;
		this.word2 = word2;
		this.word3ref = word3ref;
		this.total = total;
	}

	public int getWord1() {
		return word1;
	}

	public void setWord1(int word1) {
		this.word1 = word1;
	}

	public int getWord2() {
		return word2;
	}

	public void setWord2(int word2) {
		this.word2 = word2;
	}

	public int getWord3ref() {
		return word3ref;
	}

	public void setWord3ref(int word3ref) {
		this.word3ref = word3ref;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	public String toString() {
		return "Word12("+ word1 + " " + word2 + " " + word3ref + " " + total+")";
	}
}
