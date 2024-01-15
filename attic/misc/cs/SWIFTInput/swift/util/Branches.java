package gtf.swift.util;


import java.util.*;
import common.util.*;

public class Branches {
	private Map branches;

	public Branches() {
		branches = new HashMap();
		String b = AppProperties.getStringProperty("branches");
		java.util.StringTokenizer st = new java.util.StringTokenizer(b, ",");
		while (st.hasMoreTokens()) {
			String br = st.nextToken();
			Branch branch = new Branch(br);
			branches.put(branch.getName(), branch);
		}

		//All Branch
		AllBranch ab = new AllBranch();
		branches.put(ab.getName(), ab);
	}
	public Branch getBranch(String branchStr) {
		return (Branch) branches.get(branchStr);
	}
	public Iterator iterator() {
		return branches.values().iterator();
	}
}