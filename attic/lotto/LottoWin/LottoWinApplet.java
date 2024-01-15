import java.awt.*;
import java.util.*;
import java.applet.Applet;

public class LottoWinApplet extends Applet implements Runnable {
    Thread windowThread;

    public void start() {
        if (windowThread == null) {
            windowThread = new Thread(this);
            windowThread.start();
        }
    }

    public synchronized void run() {
		LottoWin lw = new LottoWin();
		lw.setTitle("LottoWin V1.1");
		lw.init(true, getDocumentBase());
		lw.setVisible(true);
		lw.start();
	}
}