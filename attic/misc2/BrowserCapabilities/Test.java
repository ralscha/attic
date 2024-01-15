
import java.lang.reflect.*;

public class Test {


	public static void main(String[] args) {
	

		Capability cap = BrowserCapabilities.getBrowserCapability("Mozilla/4.72 [en] (Win98; I)");
		System.out.println(cap);
	
		 cap = BrowserCapabilities.getBrowserCapability("Mozilla/4.0 (compatible; MSIE 4.01; Windows 98)");
		System.out.println(cap.getUseragent());
		System.out.println(cap);

		 cap = BrowserCapabilities.getBrowserCapability("Mozilla/4.7 [en] (WinNT; I)");
		System.out.println(cap.getUseragent());		 
		System.out.println(cap);

		 cap = BrowserCapabilities.getBrowserCapability("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT)");
		System.out.println(cap.getUseragent());		 
		System.out.println(cap);

		 cap = BrowserCapabilities.getBrowserCapability("Mozilla/4.08 [en_US] (X11; I; AIX 4.3)");
		System.out.println(cap.getUseragent());		 
		System.out.println(cap);

		 cap = BrowserCapabilities.getBrowserCapability("Mozilla/3.01 [de]-C-MACOS8 (Macintosh; I; PPC)");
		System.out.println(cap.getUseragent());		 
		System.out.println(cap);

		 cap = BrowserCapabilities.getBrowserCapability("Mozilla/4.05 [de]C-NECCK  (Win95; I)");
		System.out.println(cap.getUseragent());		 
		System.out.println(cap);

		 cap = BrowserCapabilities.getBrowserCapability("Mozilla/4.0 (compatible; MSIE 5.0; Win32)");
		System.out.println(cap.getUseragent());		 
		System.out.println(cap);

		 cap = BrowserCapabilities.getBrowserCapability("Mozilla/4.61 [en] (WinNT; I)");
		System.out.println(cap.getUseragent());		 
		System.out.println(cap);

		cap = BrowserCapabilities.getBrowserCapability("Mozilla/4.0 (compatible; MSIE 5.0; AK; Windows NT)");
		System.out.println(cap.getUseragent());		
		System.out.println(cap);
		
		/*
		Class clazz = Capability.class;
	
		try {
			Method[] m = clazz.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				System.out.println(m[i]);
			}		
		} catch (NoSuchMethodException nsme) {
			System.err.println(nsme);
		}
		*/
	}
	
}