package ch.sr.lotto.update;

import ch.sr.lotto.util.*;

public class LottoPageUpdater {

	public static void main(String args[]) {
		
		AppProperties.setProxyProperties();
		
		LottoPageUpdateRunner myRunner = new LottoPageUpdateRunner();
		
		//myRunner.createPageAndSend();
		
		
		myRunner.start();
		try {
			myRunner.join();
		} catch (InterruptedException ie) {
      //no action  
    }
		

		System.out.println("THE END");
		myRunner.closeFrame();
	
	}
	
}