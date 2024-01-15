package simpletree;

import java.io.*;
import javax.swing.tree.*;
import java.util.*;

public class FileNode implements TreeNode {
	private File file;
	private FileNode[] children = null;
	private FileNode parent = null;
	
	private final static List tmpDirList = new ArrayList();
	private final static List tmpFileList = new ArrayList();
	private final static Comparator comparator = new FileCaseInsensitiveComparator();

	public FileNode(File file) {
		this(file, null);
	}

	private FileNode(File file, FileNode parent) {
		this.file = file;
		this.parent = parent;
	}
		
	public TreeNode getChildAt(int childIndex) {
		initChildren();
		return children[childIndex];
	}

	public int getChildCount() {
		initChildren();
		return children.length;
	}

	public TreeNode getParent() {
		return parent;
	}
	
	public int getIndex(TreeNode node) {
		initChildren();

		for (int i = 0; i < children.length; i++) {
			if (node == children[i])
				return i;
		}
		return -1;
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return file.isFile();
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
		if (file.getName().length() != 0)
			return file.getName();
		else
			return file.getPath();
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
					Collections.sort(tmpDirList, comparator);
					Collections.sort(tmpFileList, comparator);
					tmpDirList.addAll(tmpFileList);
					children = (FileNode[]) tmpDirList.toArray(new FileNode[tmpDirList.size()]);
				} else {
					children = new FileNode[0];
				}
			} catch (SecurityException se) {}
		}
	}
}