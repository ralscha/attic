package ch.sr.lotto.update;

import ch.sr.lotto.util.*;

public class LottoPageCreateAndSend {

	public static void main(String args[]) {
		
		AppProperties.setProxyProperties();
		
		LottoPageUpdateRunner myRunner = new LottoPageUpdateRunner();
		
		myRunner.createPageAndSend();
		
		
	
		System.out.println("THE END");
		myRunner.closeFrame();
	
	}
	
}