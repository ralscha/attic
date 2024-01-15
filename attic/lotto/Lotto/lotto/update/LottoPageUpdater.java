package lotto.update;

import lotto.*;
import common.util.*;

public class LottoPageUpdater {

	public static void main(String args[]) {
		
		AppProperties.setProxyProperties();
		
		DbManager.initialize(AppProperties.getStringProperty("file.database"), false);
		LottoPageUpdateRunner myRunner = new LottoPageUpdateRunner();
		
		//myRunner.createPageAndSend();
		
		
		myRunner.start();
		try {
			myRunner.join();
		} catch (InterruptedException ie) {}
		

		DbManager.shutdown();
		System.out.println("THE END");
		myRunner.closeFrame();
	
	}
	
}