


import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.text.*;


/**
  This class catch document change before change, and give chance to modify change to DocumentChangingListener
*/
public class DocumentCatchChanging extends PlainDocument {

	private DocumentChangingListener listener;

	public DocumentCatchChanging() {
		super();
	}

	public void setDocumentChangingListener(DocumentChangingListener listener) {
		this.listener = listener;
	}
	public DocumentChangingListener getDocumentChangingListener() {
		return listener;
	}

	public void insertString(int offs, java.lang.String str,
                         	AttributeSet a) throws BadLocationException {

		// chance to modify 'str' to one listener
		if (listener != null) {
			str = listener.gointToUpdate(offs, str, a);
		}

		super.insertString(offs, str, a);
	}
}