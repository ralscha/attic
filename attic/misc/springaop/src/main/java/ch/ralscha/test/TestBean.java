package ch.ralscha.test;

import javax.inject.Named;
import ch.ralscha.aspect.Important;

@Named
public class TestBean {

	@Important("two")
	public String echo(String str) {
		return str.toUpperCase();
	}

}
