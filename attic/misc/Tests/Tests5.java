import java.io.*;
import com.hothouseobjects.tags.*;
import java.util.*;
import java.net.*;

public class Tests5 {


	public static void main(String[] args) {
		try {
			URL goURL = new URL(args[0]);
	
			Reader reader = new InputStreamReader(goURL.openStream());
			TagTiller tiller = new TagTiller(reader);
			Tag theParsedPage = tiller.getTilledTags();
	
			Vector vect = theParsedPage.collectByType("A");
			Iterator it = vect.iterator();
			while (it.hasNext()) {
				Tag t = (Tag) it.next();
				Vector v = t.getContents();
				Iterator yt = v.iterator();
				while(yt.hasNext()) {
					Item i = (Item)yt.next();
					if (i instanceof Text) {
						Text txt = (Text)i;
						System.out.println(txt.toHTML());
					}
				}
				
	
			}
		} catch(Exception e) {
			System.err.println(e);
		}
	}
}
