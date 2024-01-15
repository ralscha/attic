import java.io.*;
import java.util.*;
import common.io.*;

public class FileReader {


	private FileNode root;
	private List fileList;
	
	private FileReader() {
		
		root = new FileNode(new File("D:\\"), null);
		//nodes.initAllChildren();
		fileList = new ArrayList();
		
	}
	
	public void collectFiles(FileNode node) {
		collectFiles(node, new FileFilter() {
										public boolean accept(File file) {
											return true;
										}
									});
	}
	
	public void collectFiles(FileNode node, FileFilter filter) {
		FileNode[] children = node.getChildren();
		
		for (int i = 0; i < children.length; i++) {
			if (children[i].isLeaf()) {
				if (filter.accept(children[i].getFile())) {
					fileList.add(children[i].getFile());
				}
			} else {
				collectFiles(children[i], filter);
			}
		}
	}
	
	private void go() {
		IncludeEndingFileFilter includeFilter = new IncludeEndingFileFilter(".java");
		collectFiles(root, includeFilter);
		
		try {
			ZipOutputFile zof = new ZipOutputFile("d:/download/java.zip");
			
			File[] fileArray = (File[])fileList.toArray(new File[fileList.size()]);
			zof.add(fileArray);
			zof.close();
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		
		/*
		Iterator it = fileList.iterator(); 
		while(it.hasNext()) {
			System.out.println(((File)it.next()).getPath());
		}
		*/
	}

	public static void main(String[] args) {
		new FileReader().go();
	}
}