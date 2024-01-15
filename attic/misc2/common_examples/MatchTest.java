
import common.util.match.*;

public class MatchTest {


	public static void main(String[] args) {
		Wildcard wc = new Wildcard("?impl?.java");
		System.out.println(wc.match("Simple.java"));
		System.out.println(wc.match("Simple.class"));	

    
//	String pattern = "^1+2*3?[a-fA-F0-9]*456.78[^q-r]9$";
    String pattern = "12?3[456]*789";
    String test = "13456789";
    RegularExpression expr = new RegularExpression(pattern);
    System.out.println(expr.match("12344444444444444478"));
	}
}