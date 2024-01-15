
import java.io.*;

public class Fuzzy {

	public Fuzzy(String fileName, String searchStr, int threshold) {

		BufferedReader dis;
		String line;
		float bestSim = 0.0f, similarity = 0.0f;

		searchStr = prepareString(searchStr);

		int nGram1Len = 3;
		int nGram2Len;
		if (searchStr.length() < 7)
			nGram2Len = 2;
		else
			nGram2Len = 5;


		try {
			dis = new BufferedReader(new FileReader(fileName));
			while ((line = dis.readLine()) != null) {
				String preparedStr = prepareString(line);
				Result r1 = nGramMatch(preparedStr, searchStr, nGram1Len);
				Result r2 = nGramMatch(preparedStr, searchStr, nGram2Len);

				similarity = 100.0f * (float)(r1.getMatchCount() + r2.getMatchCount()) /
             				(float)(r1.getMaxMatch() + r2.getMaxMatch());

				if (similarity > bestSim) {
					bestSim = similarity;
				}
					
      			if (similarity >= threshold) {
      				System.out.println(r1.getMatchCount());
      				System.out.println(r2.getMatchCount());
      				System.out.println(r1.getMaxMatch());
      				System.out.println(r2.getMaxMatch());
      				
          			System.out.println(similarity + " " +preparedStr);
      			}

			}
			dis.close();
			
			if (bestSim < threshold)
				System.out.println("Kein Treffer; Best Match war "+bestSim);
			
		} catch (IOException e) {
			System.err.println(e);
		}
	}


	private Result nGramMatch(String text, String searchStr, int nGramLen) {
		String nGram;
		int count = 0;
		int maxMatch = 0;

		int nGramCount = searchStr.length() - nGramLen + 1;

		
		for (int i = 0; i < nGramCount; i++) {
			nGram = searchStr.substring(i, i+nGramLen );
			
			if (nGram.charAt(nGramLen - 2) == ' ' && nGram.charAt(0) != ' ')
				i += nGramLen - 3;
			else {
				maxMatch += nGramLen;
				if (text.indexOf(nGram) != -1) {
					count++;
				}
			}

		}
		Result r = new Result(count * nGramLen, maxMatch);
		return r;

	}

	private String prepareString(String str) {
		StringBuffer sb = new StringBuffer(str.length());
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			ch = Character.toLowerCase(ch);
			if (ch < '0') {
				sb.append(' ');
			} else {
				switch (ch) {
					case ':' :
					case ';' :
					case '<' :
					case '>' :
					case '=' :
					case '?' :
					case '[' :
					case ']' :
						sb.append(' ');
						break;
					default :
						sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	private class Result {
		private int matchCount, maxMatch;
		public Result(int mc, int mm) {
			matchCount = mc;
			maxMatch = mm;
		}
		public void setMatchCount(int i) {
			matchCount = i;
		}
		public void setMaxMatch(int i) {
			maxMatch = i;
		}
		public int getMatchCount() {
			return matchCount;
		}
		public int getMaxMatch() {
			return maxMatch;
		}
	}

	static public void main(String args[]) {
		if (args.length >= 2) {
			
			int threshold = 50;
			
			if (args.length == 3) {
				threshold = Integer.parseInt(args[2]);
			}
			
			
			
			new Fuzzy(args[0], " " + args[1].trim() + " ", threshold);
			
		}
		else
			System.out.println("java Fuzzy <file> <searchString> [threshold]");
	}

}