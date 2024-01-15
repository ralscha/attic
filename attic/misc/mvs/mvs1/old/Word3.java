public class Word3 {

	private int ref;
	private int word3;
	private int hits;

	public Word3(int ref, int word3, int hits) {
		this.ref = ref;
		this.word3 = word3;
		this.hits = hits;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getWord3() {
		return word3;
	}

	public void setWord3(int word3) {
		this.word3 = word3;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}


	public String toString() {
		return "Word3("+ ref + " " + word3 + " " + hits+")";
	}
}
