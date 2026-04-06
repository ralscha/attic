package ch.rasc.antlr;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExpressionParserTest {
	private ExpressionParser _parser;
	@Rule
	public ExpectedException _expected = ExpectedException.none();

	@Before
	public void setup() {
		this._parser = new ExpressionParser();
	}

	@Test
	public void testNumber() {
		assertThat(this._parser.parse("42"), equalTo(42));
		assertThat(this._parser.parse("-42"), equalTo(-42));
	}

	@Test
	public void testNumberBrackets() {
		assertThat(this._parser.parse("(42)"), equalTo(42));
	}

	@Test
	public void testTimes() {
		assertThat(this._parser.parse("21 * 2"), equalTo(42));
		assertThat(this._parser.parse("21 times 2"), equalTo(42));
	}

	@Test
	public void testDiv() {
		assertThat(this._parser.parse("84 / 2"), equalTo(42));
		assertThat(this._parser.parse("84 div 2"), equalTo(42));
	}

	@Test
	public void testPlus() {
		assertThat(this._parser.parse("40 + 2"), equalTo(42));
		assertThat(this._parser.parse("40 plus 2"), equalTo(42));
	}

	@Test
	public void testMinus() {
		assertThat(this._parser.parse("53 - 11"), equalTo(42));
		assertThat(this._parser.parse("53 minus 11"), equalTo(42));
	}

	@Test
	public void testComplex() {
		assertThat(this._parser.parse("(21 * (8 / 2 - (1 + 1)))"), equalTo(42));
	}

	@Test
	public void testInvalid() {
		this._expected.expect(IllegalArgumentException.class);
		this._expected.expectMessage(containsString("token recognition error at: '#'"));

		assertThat(this._parser.parse("(21 # 2)"), equalTo(42));
	}

}