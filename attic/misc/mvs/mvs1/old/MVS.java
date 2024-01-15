import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import common.util.*;
import common.io.*;

public class MVS {


	private RefNo refno;
	private Random r = new Random();

	private String endmarkstring = "#####";
	private Integer endmark = new Integer(endmarkstring.hashCode());

	private Word3Table word3Table;
	private Word12Table word12Table;
	private WordListTable wordListTable;
	private BeginListTable beginListTable;
	private RefNoTable refNoTable;
	
	static {
		try {
			Class.forName(AppProperties.getStringProperty("db.driver"));
		} catch (Exception e) {
			System.err.println(e);
		} 
	}
	
	private Connection con = null;

	public MVS() {
		try {
			String url = AppProperties.getStringProperty("db.url");
			String user = AppProperties.getStringProperty("db.user");
						String pw = AppProperties.getStringProperty("db.password");
	
			con = DriverManager.getConnection(url, user, pw);
			
			word3Table = new Word3Table(con);
			word12Table = new Word12Table(con);
			wordListTable = new WordListTable(con);
			beginListTable = new BeginListTable(con);
			refNoTable = new RefNoTable(con);			
			
			

			
		} catch (SQLException e) {
			System.err.println(e);
		}
		
	}
	
	public void closeConnection() {
		if (con != null) {
			try {
				con.commit();
				con.close();
			} catch (SQLException sqle) { }
		}
	}
	
	public void generate(String path, int no) {
	
		try {
			if (path != null) {
				if (no > 0) {
					for (int i = 0; i < no; i++) {
						BufferedWriter bw = new BufferedWriter(new FileWriter(path+File.separator+i+".txt"));
						generate(bw);
						bw.close();
					}				
				} else {
					BufferedWriter bw = new BufferedWriter(new FileWriter(path));
					generate(bw);
					bw.close();
				}
			} else {
				generate(new OutputStreamWriter(System.out));
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
	
	
	public void generate(Writer pw) {

		try {
	
			int total, sum, w1, w2, rnd, next;
			StringBuffer line = new StringBuffer();
			
			/* Monte-Carlo fuer BeginList */
			total = beginListTable.getTotal();
		
			sum = 0;
			w1 = 0;
			w2 = 0;
			rnd = Math.abs((r.nextInt() % total)) + 1;
	
			Iterator it = beginListTable.select();
			while (it.hasNext() && (sum < rnd)) {
				BeginList bg = (BeginList)it.next();
				w1 = bg.getWord1();
				w2 = bg.getWord2();
				sum += bg.getTotal();
			}
	
			line.append(getWord(w1)).append(" ").append(getWord(w2)).append(" ");
			next = getNext(w1, w2);
			
			while (next != endmark.hashCode()) {
				line.append(getWord(next)).append(" ");
				if (line.length() > 60) {
					pw.write(line.toString());
					pw.write(System.getProperty("line.separator"));
					line.setLength(0);
				}
				w1 = w2;
				w2 = next;
				next = getNext(w1, w2);
			}
			pw.write(line.toString());
			pw.write(System.getProperty("line.separator"));	
			
		} catch (Exception ex) {
			ex.printStackTrace ();
		}

	}
	
	public String getWord(int hash) throws SQLException {
		Iterator it = wordListTable.select("hash = "+hash);
		if (it.hasNext()) {
			WordList wl = (WordList)it.next();
			return (wl.getWord());
		} else {
			return (endmarkstring);
		}

	}		
			
	public int getNext(int w1, int w2) throws SQLException {

		int ref, total, sum, w3, rnd;

		
		Iterator it = word12Table.select("word1 = "+w1+" AND word2 = "+w2);
				
		if (it.hasNext()) {
			Word12 word12 = (Word12)it.next();
			ref = word12.getWord3ref();
			total = word12.getTotal();

			sum = 0;
			w3 = endmark.intValue();
			rnd = Math.abs((r.nextInt() % total)) + 1;

			Iterator it3 = word3Table.select("ref = "+ref);

			while (it3.hasNext() && (sum <= rnd)) {
				Word3 word3 = (Word3)it3.next();
				w3 = word3.getWord3();
				sum += word3.getHits();
			}

			return (w3);
		} else {
			return (endmark.intValue());
		}
	}


	public String convertText(String text)  {
		StringBuffer sb = new StringBuffer(text.length());
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == 'ß') sb.append("ss");
			else if (c == '«') sb.append('"');
			else if (c == '»') sb.append('"');
			else if (c == '„') sb.append('"');
			else if ((c == '´') && ((i == 0) || (i == text.length()-1))) sb.append('"');
			else if ((c == '\'') && ((i == 0) || (i == text.length()-1))) sb.append('"');			
			else
				sb.append(c);	
		}		
		return(sb.toString());
		
	}
		
		
 	public void readTextFiles(String path, boolean allFiles) {		
 		try {
			BufferedReader dis;
			List files;
			List hash, words;
			String input, help;
			int ix;
	 	
			files = new ArrayList();
	
			if (allFiles) {
				File f = new File(path);
				String lst[] = f.list();
	
				for (int i = 0; i < lst.length; i++) {
					if (lst[i].endsWith(".txt"))
						files.add(path + "\\"+lst[i]);
				}
	
			} else {
				files.add(path);
			}
	
			hash = new ArrayList();
			words = new ArrayList();

			Iterator it = refNoTable.select();
			if (it.hasNext()) {
				refno = (RefNo)it.next();
			} else {
				refno = new RefNo(1);
				refNoTable.insert(refno);
			}
			
			for (int i = 0; i < files.size(); i++) {
				hash.clear();
				words.clear();

				dis = new BufferedReader(new TagFilterReader(new FileReader((String) files.get(i))));

				System.out.println("Reading ... : " + (String) files.get(i));

				while ((input = dis.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(input);
					while (st.hasMoreTokens()) {
						help = st.nextToken();
						
						help = convertText(help);
						
						hash.add(new Integer(help.hashCode()));
						words.add(help);
						
						Iterator itw = wordListTable.select("hash = "+help.hashCode());
						if (!itw.hasNext()) {
							WordList wl = new WordList(help.hashCode(), help);
							wordListTable.insert(wl);
						}
					}
				}
				ix = 0;
				String ws;
				
				if (hash.size() >= 2)  {				
					while (ix + 2 < hash.size()) {
						addWords(((Integer)hash.get(ix)).intValue(),
	   							((Integer)hash.get(ix + 1)).intValue(),
	   							((Integer)hash.get(ix + 2)).intValue());

						if (ix == 0) {
							ws = (String) words.get(ix);
							if (Character.isUpperCase(ws.charAt(0)))
								addWordsToBeginList(((Integer)hash.get(ix)).intValue(),
	              									((Integer)hash.get(ix + 1)).intValue());
						} else {
							ws = (String) words.get(ix - 1);
							if ((Character.isUpperCase(ws.charAt(0))) && (ws.endsWith(".")))
								addWordsToBeginList(((Integer)hash.get(ix)).intValue(),
	              									((Integer) hash.get(ix + 1)).intValue());
						}

						ix++;
					}

					addWords(((Integer)hash.get(ix)).intValue(), ((Integer)hash.get(ix + 1)).intValue(), endmark.intValue());
				}
			} /* for */
			refNoTable.update(refno);
		} catch (Exception ex) {
			ex.printStackTrace ();
		}

	}

	public void addWordsToBeginList(int w1, int w2) throws SQLException {
		
		Iterator it = beginListTable.select("word1 = "+w1+" AND word2 = "+w2);
		
		if (it.hasNext()) {
			BeginList bl = (BeginList)it.next();
			//Gefunden
			bl.setTotal(bl.getTotal()+1);
			beginListTable.update(bl);
		} else {
			BeginList bl = new BeginList(w1, w2, 1);
			beginListTable.insert(bl);
		}
	}

	public void addWords(int w1, int w2, int w3) throws SQLException {
		int r, h;

		Iterator it = word12Table.select("word1 = "+w1+" AND word2 = "+w2);
		
		if (it.hasNext()) {
			Word12 w12 = (Word12)it.next();
			//Gefunden
			r = w12.getWord3ref();
			w12.setTotal(w12.getTotal()+1);
			word12Table.update(w12);
			
			//Word3 suchen
			Iterator it3 = word3Table.select("ref = "+r+" AND word3 = "+w3);
			if (it3.hasNext()) {
				//Gefunden
				Word3 word3 = (Word3)it3.next();
				word3.setHits(word3.getHits()+1);
				word3Table.update(word3);
			} else {
				Word3 word3 = new Word3(r, w3, 1);
				word3Table.insert(word3);
			}

		} else {
			Word12 w12 = new Word12(w1, w2, refno.getRefno(), 1);
			Word3 word3 = new Word3(refno.getRefno(), w3, 1);
			word12Table.insert(w12);
			word3Table.insert(word3);
			
			refno.incRefno();
		}

	}


	public static void main(String args[]) {
		MVS mvs = null;
		try {
			if (args.length >= 1) {			
				if (args[0].equalsIgnoreCase("G")) {				
					mvs = new MVS();					
					if (args.length == 2) {
						mvs.generate(args[1], 0);
					} else if (args.length == 3) {
						mvs.generate(args[1], Integer.parseInt(args[2]));
					} else
						mvs.generate(null, 0);
				} else {
					boolean allFiles = false;
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("all"))
							allFiles = true;
					}
										
					mvs = new MVS();
					mvs.readTextFiles(args[0], allFiles);
				} 
			} else {
				System.out.println("wrong arguments");
			}
		} finally {
			if (mvs != null) {
				mvs.closeConnection();
			}
		}
		
	}

}