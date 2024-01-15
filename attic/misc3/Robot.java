
import java.net.*;
import java.io.*;
import java.util.*;
import gnu.regexp.*;

public class Robot {
	private Hashtable m_visitedUrl = new Hashtable();
	private RE m_regexp;

	public Robot() throws REException {
		m_regexp = new RE("<A[^>]*HREF=([^>\\s]*)[^>]*>[^<]*</A>", RE.REG_ICASE);
	}

	public static void main(String[] args) {
		try {
			URL u = new URL(args[0]);
			Robot r = new Robot();
			r.visit(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void visit(URL u) {
		// visit code would go here
		m_visitedUrl.put(u.toString(), u);
		URL[] childNode = getChildNodes(u);
		for (int i = 0; i < childNode.length; i++)
			if (! m_visitedUrl.containsKey(childNode[i].toString()))
				visit(childNode[i]);
	}

	private URL[] getChildNodes(URL u) {
		URL result[] = null;
		try {
			String content = getURLContent(u);
			REMatch[] matches = m_regexp.getAllMatches(content);
			result = new URL[matches.length];
			for (int i = 0; i < matches.length; i++) {
				String urlCandidate = matches[i].toString(i);
				urlCandidate = urlCandidate.replace('"',' ').trim();
				result[i] = new URL(u, urlCandidate);
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		return result;
	}

	private String getURLContent(URL u) throws IOException {
		URLConnection conn = u.openConnection();
		InputStreamReader isr = new InputStreamReader(conn.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		StringBuffer buff = new StringBuffer();
		String s;
		while ((s = br.readLine()) != null)
			buff.append(s);
		return buff.toString();
	}


}