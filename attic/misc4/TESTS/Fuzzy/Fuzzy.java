
import java.io.*;

public class Fuzzy {

	public Fuzzy(String fileName, String searchStr) {
	    System.out.println(searchStr);
	    BufferedReader dis;
	    String line;
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
                
                System.out.println("R1 = "+r1.getMatchCount() + "  " + r1.getMaxMatch());
                System.out.println("R2 = "+r2.getMatchCount() + "  " + r2.getMaxMatch());
                
                float similarity = 100.0f * (float)(r1.getMatchCount() + r2.getMatchCount()) 
                                         / (float)(r1.getMaxMatch() + r2.getMaxMatch());

                System.out.println(line + " ----> " + similarity);                         
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
             System.err.println(e);
        }
	}


    private Result nGramMatch(String text, String searchStr, int nGramLen) {
        String nGram;
        int count = 0;
        int maxMatch = 0;
        searchStr = prepareString(searchStr);
        int nGramCount = searchStr.length() - nGramLen + 1;
        for (int i = 0; i < nGramCount; i++) {
            nGram = searchStr.substring(i, i+nGramLen);
            if (nGram.charAt(nGramLen-2) == ' ' && nGram.charAt(0) != ' ')
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
            if ((int)ch < (int)'0') {
                sb.append(' ');
            } else {
                switch(ch) {
                    case ':' : sb.append(' '); break;
                    case ';' : sb.append(' '); break;
                    case '<' : sb.append(' '); break;
                    case '>' : sb.append(' '); break;
                    case '=' : sb.append(' '); break;
                    case '?' : sb.append(' '); break;
                    case '[' : sb.append(' '); break;
                    case ']' : sb.append(' '); break;                
                    default  : sb.append(ch);
                }     
            }
        }
        return sb.toString();
    }

    private class Result {
        private int matchCount, maxMatch;
        public Result(int mc, int mm) { matchCount = mc; maxMatch = mm; }
        public void setMatchCount(int i) { matchCount = i; }
        public void setMaxMatch(int i) { maxMatch = i; }        
        public int getMatchCount() { return matchCount; }
        public int getMaxMatch() { return maxMatch; }
    }
    
    static public void main(String args[]) {
        if (args.length == 2) 
            new Fuzzy(args[0], " "+args[1]+" ");
        else
            System.out.println("java Fuzzy <file> <searchString>");
    }

}

