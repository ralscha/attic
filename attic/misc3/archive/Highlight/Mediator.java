import java.util.*;

public class Mediator {
//This class hides the structure of the word list
//and provides access to it simply
    private ArrayList words;        
    private int offset;
    private int i;

    public Mediator() {
        words = new ArrayList();
        offset = 0;   
    }
    //add a word to the list
    public void add(Word w) {
        w.setOffset (offset);
        words.add (w);
        offset += w.length ();
    }
    //get the word at index i
    public Word get(int i) {
        return(Word) words.get (i);
    }
    //find the word at the offset specified
    public Word findWord(int offs) {
        i = 0;
        Word w = get(i++);
        while (w.getOffset () < offs && i < words.size ()) {
            w = get(i++);
        }
        w= get(i - 1);
        return w;
    }
    //find the word before the specified offset
    public Word findLastWord(int offs) {
        findWord(offs);
        return get(i-2);
    }
    //get the size of the word list
    public int size() {
        return words.size ();
    }
}
