
import CheckTree.*;
import java.io.*;
import java.util.*;

public class TestNode {

	public static void main(String args[]) {
	
		FileNode fn = new FileNode(new File(File.separator));
		
		Enumeration e = fn.children();
		int j = 0;
		while(e.hasMoreElements()) {
			System.out.print(j++);
			System.out.print("-->");			
			System.out.println(e.nextElement());
		}
		
		for (int i = 0; i < fn.getChildCount(); i++) {
			System.out.print(fn.getIndex(fn.getChildAt(i)));
			System.out.print("-->");
			System.out.print(fn.getChildAt(i));
			System.out.println(" " + fn.getChildAt(i).isLeaf());
		}
	}
}