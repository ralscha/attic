import java.io.InputStream;
import java.io.IOException;
import javax.swing.JProgressBar;

public class ProgressInputStream extends InputStream {
	InputStream in;
	JProgressBar progress;
	int count = 0;
	boolean terminate = false;

	public ProgressInputStream(InputStream in, JProgressBar progress) {
		this.in = in;
		this.progress = progress;
		this.progress.setMinimum(0);
		try {
			this.progress.setMaximum(in.available());
		} catch (IOException ioe) {
			this.progress.setMaximum(0);
		}
		makeProgressThread().start();
	}

	public int read() throws IOException {
		count++;
		int c = in.read();
		if (c < 0)
			terminate = true;
		return c;
	}

	public void close() throws IOException {
		terminate = true;
	}

	Thread makeProgressThread() {
		return new Thread() {
       			public void run() {
   				while (!terminate) {
   					try {
       						Thread.sleep(100);
   					} catch (InterruptedException e) {}
       					progress.setValue(count);
       				}
       			}
       		};
	}
}