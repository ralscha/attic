
package ch.sr.bookmark;

import java.io.*;
import java.util.*;
import java.util.regex.*;


public class HTMLBookmark {

		
	private final Pattern title1 = Pattern.compile("<1>([^\\s]+)");
	private final Pattern title2 = Pattern.compile("<2>([^\\s]+)");
	private final Pattern item = Pattern.compile("\\s*([^\\s]+)\\s*([^¦]+)");
	
				
	public static void main(java.lang.String[] args) {
		AppProperties.setFileName("HTMLBookmark.props");
				
		String fileName = AppProperties.getStringProperty("url.file");
		if (fileName != null) {
			new HTMLBookmark().read(fileName);
		} else {
			System.out.println("no file");
		}		
	}
		
	
	public void read(String fileName) {
		
		try {
						
			BufferedReader dis;
			String line;

			List titles = new ArrayList();
			dis = new BufferedReader(new FileReader(fileName));
		
			while ((line = dis.readLine()) != null) {
        Matcher matcher = title1.matcher(line);
				if (matcher.find()) {
					titles.add(matcher.group(1));
				}
			}
			dis.close();
			
			String outputFileName = AppProperties.getStringProperty("html.file");
			if (outputFileName == null)
				outputFileName = "bookmark.html";
			HTMLBookmarkPage page = new HTMLBookmarkPage(outputFileName);
			page.printHeader();
			page.printContents(titles);
			
			
			
			boolean first = true;
			boolean hasItems = false;
			dis = new BufferedReader(new FileReader(fileName));

			while ((line = dis.readLine()) != null) {
        Matcher matcher = title1.matcher(line);
				if (matcher.find()) {				
					if (hasItems) {
						page.printEndList();
						page.printParagraph();
						hasItems = false;
					}
					page.printTitle1(matcher.group(1));
				} else {
          Matcher matcher2 = title2.matcher(line);
					
					if (matcher2.find()) {						
						first = true;
						if (hasItems) {							
							page.printEndList();
							page.printParagraph();						
							hasItems = false;
						}
						page.printTitle2(matcher2.group(1));
						
					} else {	
            Matcher matcher3 = item.matcher(line);					
						
						if (matcher3.find()) {
							if (first) {
								page.printStartList();
								first = false;
								hasItems = true;
							}
							page.printBookmark(matcher3.group(2).trim(), matcher3.group(1));
						}
					}
				}
			}
			
			if (hasItems)
				page.printEndList();
			
			dis.close();			
			page.printFooter();
			page.close();
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	

}