package ch.ralscha.test;

import static org.junit.Assert.assertEquals;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class TestBeanTest {

	@Inject
	private TestBean testBean;

	@Test
	public void test() {
		assertEquals("TEST", testBean.echo("test"));
	}

}
