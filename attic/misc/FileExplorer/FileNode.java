

import java.io.*;
import javax.swing.tree.*;
import java.util.*;

public class FileNode implements SelectableNode {
	private File file;
	private FileNode[] children = null;
	private FileNode parent = null;
	private int state;
	
	private final static List tmpDirList = new ArrayList();
	private final static List tmpFileList = new ArrayList();
	private final static Comparator comparator = new FileCaseInsensitiveComparator();

	private TreeTableMediator mediator;
		
	public FileNode(File file, TreeTableMediator ttm) {
		this(file, null, ttm);
	}

	private FileNode(File file, FileNode parent, TreeTableMediator ttm) {
		this.file = file;
		this.parent = parent;
		this.state = JTriStateCheckBox.DESELECTED;		
		this.mediator = ttm;
	}
	
	public void setState(int state) {
		this.state = state;
		mediator.updateTable();
	}
	
	public int getState() {
		return state;
	}	
	
	public TreeNode getChildAt(int childIndex) {
		initChildren();
		return children[childIndex];
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
							tmpFileList.add(new FileNode(childFile, this, mediator));
						else
							tmpDirList.add(new FileNode(childFile, this, mediator));

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