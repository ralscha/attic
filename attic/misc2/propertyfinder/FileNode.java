

import java.io.*;
import javax.swing.tree.*;
import java.util.*;

public class FileNode {
	private File file;
	private FileNode[] children = null;
	private FileNode parent = null;
	
	private final static List tmpDirList = new ArrayList();
	private final static List tmpFileList = new ArrayList();
	
		
	public FileNode(File file) {
		this(file, null);
	}

	private FileNode(File file, FileNode parent) {
		this.file = file;
		this.parent = parent;
	}
	


	public int getChildCount() {
		initChildren();
		return children.length;
	}

	public void initAllChildren() {
		initChildren();
		for (int i = 0; i < children.length; i++) {
			if (!children[i].isLeaf())
				children[i].initAllChildren();
		}
	}
	
	
	public boolean isLeaf() {
		return ((file != null) && (file.isFile()));
	}


	public Enumeration children() {

		initChildren();

		return new Enumeration() {
       			private int ix = 0;

       			public boolean hasMoreElements() {
       				return (ix < children.length);
       			}
       			public Object nextElement() {
       				return (children[ix++]);
       			}
       		};
	}

	public FileNode[] getChildren() {
		initChildren();
		return children;
	}

	public String toString() {
		if (file != null) {
			if (file.getName().length() != 0)
				return file.getName();
			else
				return file.getPath();
		} else {
			return "null";
		}
	}

	public File getFile() {
		return file;
	}

	protected void initChildren() {

		if (children == null) {
			try {
				String[] files = file.list();
				if (files != null) {
					String path = file.getPath();

					tmpDirList.clear();
					tmpFileList.clear();

					for (int i = 0; i < files.length; i++) {
						File childFile = new File(path, files[i]);
						if (childFile.isFile())
							tmpFileList.add(new FileNode(childFile, this));
						else
							tmpDirList.add(new FileNode(childFile, this));

					}
					tmpDirList.addAll(tmpFileList);
					children = (FileNode[]) tmpDirList.toArray(new FileNode[tmpDirList.size()]);
				} else {
					children = new FileNode[0];
				}
			} catch (SecurityException se) {}
		}
	}
}