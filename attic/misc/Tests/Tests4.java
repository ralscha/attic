
import java.io.*;
import java.util.*;
import com.hothouseobjects.tags.*;
import common.io.*;

public class Tests4 {

	public static void main(String[] args) {
		System.out.println((int)'ö');
		System.out.println((char)161);
	}
	
	/*
	public static void main(String[] args) {
		
		MultiFileFilter mff = new MultiFileFilter();
		
		ExcludeEndingFileFilter edf = new ExcludeEndingFileFilter();
		edf.addEnding(".log");
		
		ExcludeDirFilter eff = new ExcludeDirFilter();
		eff.addDir("d:\\javaprojects\\output");
		
		mff.addFilter(edf);
		mff.addFilter(eff);
		
		File pathFile = new File("d:/javaprojects/");
		File[] files = pathFile.listFiles(mff);
		for (int i = 0; i < files.length; i++) {
			//if (files[i].isFile())
				System.out.println(files[i]);
		}
		
	}
	*/
	
	
	/*
	public static void main(String[] args) {
		try {
			if (args.length == 1) {
				File pathFile = new File(args[0]);
				File[] files = pathFile.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						System.out.println(files[i].getPath());
						FileReader fr = new FileReader(files[i]);
						TagTiller tiller = new TagTiller(fr);
						Tag theParsedPage = tiller.getTilledTags();
						
						String[] attr = new String[3];
						attr[0] = "COLOR";
						attr[1] = "BGCOLOR";
						attr[2] = "TEXT";
						
						for(int j = 0; j < attr.length; j++) {
							Vector vect = theParsedPage.collectByAttribute(attr[j]);
							Iterator it = vect.iterator();
							while(it.hasNext()) {
								Tag t = (Tag)it.next();
								Tag newT = (Tag)t.copy();
								newT.removeAllAttributes();
								t.replaceSelf(newT);
							}
						}
						
						files[i].delete();
						FileWriter fw = new FileWriter(files[i]);
						theParsedPage.toHTML(fw);
						fw.close();
						fr.close();
						
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
	}
	*/
}