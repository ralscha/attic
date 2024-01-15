package gtf.swift.util;

import java.util.*;
import common.util.*;

public class Branch {
	private String name;
	private String htmlName;
	private List in;

	public Branch(String propName) {
		if (propName == null)
			return;

		StringTokenizer st =
  		new StringTokenizer(AppProperties.getStringProperty(propName), ",");
		name = st.nextToken();
		htmlName = st.nextToken();

		in = new ArrayList();
		while (st.hasMoreTokens()) {
			in.add(st.nextToken());
		}
	}

	public String getHTMLName() {
		return htmlName;
	}

	public List getIn() {
		return in;
	}

	public String getName() {
		return name;
	}
}