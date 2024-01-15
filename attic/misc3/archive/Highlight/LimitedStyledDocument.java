import javax.swing.*; 
import javax.swing.text.*; 
import java.awt.Toolkit;

public class LimitedStyledDocument extends DefaultStyledDocument {
    int maxCharacters;
    int offset;
    HilitePainter hlp;
    JTextPane textPane;

    public LimitedStyledDocument(int maxChars, Mediator md) {
        maxCharacters = maxChars;
        offset = 0;
        hlp = new HilitePainter(md);
    }
    //save text pane object
    public void setTextPane(JTextPane txp) {
        textPane = txp;
    }
    //insert string into StyledDocument
    public void insertString(Word word) {
        if ((getLength() + word.length()) <= maxCharacters) {
            try {
                super.insertString(offset, word.getWord(), word.getAttributes());
                //if highlighted, add to highlight list
                if (word.isHighlighted ()) {
                    Highlighter hlt = textPane.getHighlighter ();
                    hlt.addHighlight (offset, word.length() + offset, hlp);
                }
                offset += word.length();
            } catch (BadLocationException e) {
            }
        }
    }
}
