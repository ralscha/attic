package ch.rasc.changelog.util;

import java.util.Comparator;

import ch.rasc.changelog.entity.CustomerBuild;

public class CustomerBuildComparator implements Comparator<CustomerBuild> {

	@Override
	public int compare(CustomerBuild o1, CustomerBuild o2) {
		return o2.getVersionDate().compareTo(o1.getVersionDate());
	}

}
