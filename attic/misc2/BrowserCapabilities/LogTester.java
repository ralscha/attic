
import java.io.*;
import java.text.*;
import java.util.*;

import gnu.regexp.*;


public class LogTester {


	private static void check(String userAgent) {
		BrowserDetector bd = new BrowserDetector(userAgent);
		if ("unknown".equals(bd.getBrowserPlatform()) && (bd.getBrowserVersion() != 3.01f)) {
			System.out.println(userAgent);		
			System.out.println(bd.getBrowserName());
			System.out.println(bd.getBrowserVersion());
			System.out.println("------------");
		}
	}

	public static void main(String[] args) {
		
		try {
			RE regexp = new RE("\"[^\"]*\".*\"[^\"]*\".*\"([^\"]*)\"");

			BufferedReader br = new BufferedReader(new FileReader("log199912"));
			String line;
			String lastua = null;
			
			while((line = br.readLine()) != null) {
				REMatch match = regexp.getMatch(line);
				if (match != null) {
					if (lastua != null) {
						if (!lastua.equals(match.toString(1))) {
							if (!match.toString(1).equals("-")) {
								lastua = match.toString(1);
								check(lastua);
							}
						}
					} else {
						lastua = match.toString(1);
						check(lastua);
					}
				} 
			}
		} catch (Exception ree) {
			System.err.println(ree);
		}

			
	}
}	
	