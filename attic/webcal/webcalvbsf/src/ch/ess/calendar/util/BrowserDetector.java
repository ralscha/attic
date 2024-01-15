package ch.ess.calendar.util;

/**
 * This class parses the user agent string and sets javasciptOK
 * and cssOK following the rules described below.  If you want to
 * check for specific browsers/versions then use this class to
 * parse the user agent string and use the accessor methods in
 * this class.
 * 
 * JavaScriptOK means that the browser understands JavaScript on       
 * the same level the Navigator 3 does.  Specifically, it can use  
 * named images.  This allows easier rollovers.  If a browser doesn't
 * do this (Nav 2 or MSIE 3), then we just assume it can't do any 
 * JavaScript.  Referencing images by load order is too hard to maintain.                                 
 *
 * CSSOK is kind of sketchy in that Nav 4 and MSIE work differently,   
 * but they do seem to have most of the functionality.  MSIE 4 for the 
 * Mac has buggy CSS support, so we let it do JavaScript, but no CSS.
 *
 * Ported from Leon's PHP code at http://www.working-dogs.com/freetrade
 * by Frank.
 *
 * @author Leon Atkisnon <leon@clearink.com>
 * with contributions from:
 *     Chris Mospaw <mospaw@polk-county.com>
 *     Benjamin Elijah Griffin <bgriffin@cddb.com>
 * @author Frank Y. Kim <frank.kim@clearink.com>
 */
import javax.servlet.http.*; 
 
public class BrowserDetector
{
    /** The user agent string. */
    private String userAgentString = "";
    /** The browser name specified in the user agent string. */
    private String browserName = "";
    /** The browser version specified in the user agent string. 
        If we can't parse the version just assume an old browser. */
    private float browserVersion = (float)1.0;
    /** The browser platform specified in the user agent string. */
    private String browserPlatform = "unknown";
    
    /** Whether or not javascript works in this browser. */
    private boolean javascriptOK = false;
    /** Whether or not CSS works in this browser. */
    private boolean cssOK = false;
    /** Whether or not file upload works in this browser. */
    private boolean fileUploadOK = false;
    
    /** Constants used by this class. */
    public static final String MSIE = "MSIE";
    public static final String OPERA = "Opera";
    public static final String MOZILLA = "Mozilla";
    public static final String WINDOWS = "Windows";
    public static final String UNIX = "Unix";
    public static final String MACINTOSH = "Macintosh";
	 public static final String OS2 = "OS/2";
        
    /**
     * Constructor used to initialize this class.
     */
    public BrowserDetector(String userAgentString)
    {
        this.userAgentString = userAgentString;
			try {
				parse();
			} catch (StringIndexOutOfBoundsException e) {
				this.browserName = this.userAgentString;
			}
    }
    /**
     * Constructor used to initialize this class.
     */
    public BrowserDetector(HttpServletRequest req)
    {
        this.userAgentString = req.getHeader("User-Agent");
			try {
				parse();
			} catch (StringIndexOutOfBoundsException e) {
				this.browserName = this.userAgentString;
			}
    }
    
    /**
     * Whether or not CSS works in this browser.
     */
    public boolean isCssOK()
    {
        return cssOK;
    }
    /**
     * Whether or not file upload works in this browser.
     */
    public boolean isFileUploadOK()
    {
        return fileUploadOK;
    }
    /**
     * Whether or not javascript works in this browser.
     */
    public boolean isJavascriptOK()
    {
        return javascriptOK;
    }
    /**
     * The browser name specified in the user agent string.
     */
    public String getBrowserName()
    {
        return browserName;
    }
    /**
     * The browser platform specified in the user agent string.
     */
    public String getBrowserPlatform()
    {
        return browserPlatform;
    }
    /**
     * The browser version specified in the user agent string.
     */
    public float getBrowserVersion()
    {
        return browserVersion;
    }
    /**
     * The user agent string for this class.
     */
    public String getUserAgentString()
    {
        return userAgentString;
    }
    /**
     * Helper method to initialize this class.
     */
    private void parse() throws StringIndexOutOfBoundsException
    {	 
      int versionStartIndex = userAgentString.indexOf("/");
      int versionEndIndex = userAgentString.indexOf(" ");
        		  
      // get the browser name and version
      browserName = userAgentString.substring( 0, versionStartIndex );
      try
      {
      	browserVersion = Float.parseFloat( userAgentString.substring( versionStartIndex+1, versionEndIndex ) );
      }
		catch (NumberFormatException e)
		{
		    // just use the default value
		}
		
		// MSIE lies about its name
		if ( userAgentString.indexOf(MSIE) != -1 )
		{
		    // ex: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)
		    versionStartIndex = userAgentString.indexOf(MSIE) + MSIE.length()+1;
		    versionEndIndex = userAgentString.indexOf( ";", versionStartIndex );
		    
		    browserName = MSIE;
		    try
		    {
		        browserVersion = Float.parseFloat( userAgentString.substring(versionStartIndex, versionEndIndex) );
		    }
		    catch (NumberFormatException e)
		    {
		        // just use the default value
		    }
		    
		    // PHP code
			//$Browser_Name = "MSIE";
			//$Browser_Version = strtok("MSIE");
			//$Browser_Version = strtok(" ");
			//$Browser_Version = strtok(";");
		}
		
	    // Opera isn't completely honest, either ...
		// Modificaton by Chris Mospaw <mospaw@polk-county.com>
		if ( userAgentString.indexOf(OPERA) != -1 )
		{
		    //ex: Mozilla/4.0 (Windows NT 4.0;US) Opera 3.61  [en]
		    versionStartIndex = userAgentString.indexOf(OPERA) + OPERA.length()+1;;
		    versionEndIndex = userAgentString.indexOf( " ", versionStartIndex );
		    
		    browserName = OPERA;
		    try
		    {
		        browserVersion = Float.parseFloat( userAgentString.substring(versionStartIndex, versionEndIndex) );
		    }
		    catch (NumberFormatException e)
		    {
		        // just use the default value
		    }
		    
		    // PHP code
			//$Browser_Name = "Opera";
			//$Browser_Version = strtok("Opera");
			//$Browser_Version = strtok("/");
			//$Browser_Version = strtok(";");
		}
	
	
		// try to figure out what platform
		if ( (userAgentString.indexOf("Windows") != -1) ||
		     (userAgentString.indexOf("WinNT") != -1) ||
		     (userAgentString.indexOf("Win98") != -1) ||
		     (userAgentString.indexOf("Win95") != -1) ||
			  (userAgentString.indexOf("Win32") != -1) || 
			  (userAgentString.indexOf("Win16") != -1) )
		{		
			browserPlatform = WINDOWS;
		}
		
		if (userAgentString.indexOf("OS/2") != -1) {
			browserPlatform = OS2;
		}
		
		if ( (userAgentString.indexOf("Mac") != -1 ) ||
		     (userAgentString.indexOf("MC") != -1) )
		{		
			browserPlatform = MACINTOSH;
		}
	
	    if ( (userAgentString.indexOf("X11") != -1 ) ||
			   (userAgentString.indexOf("linux") != -1) )
		{			 
			browserPlatform =  UNIX; 
		} 
	
		if (browserPlatform == WINDOWS)
		{
			if (browserName.equals(MOZILLA))
			{
				if (browserVersion >= 3.0)
				{
					javascriptOK = true;
					fileUploadOK = true;
				}		
				if (browserVersion >= 4.0)
				{
					cssOK = true;
				}
			}
			else if (browserName == MSIE)
			{
				if (browserVersion >= 4.0)
				{
					javascriptOK = true;
					fileUploadOK = true;
					cssOK = true;
				}		
			}
			else if (browserName == OPERA)
			{
				if (browserVersion >= 3.0)
				{
					javascriptOK = true;
					fileUploadOK = true;
					cssOK = true;
				}		
			}
		}
		else if (browserPlatform == MACINTOSH)
		{
            if (browserName.equals(MOZILLA))
			{
				if (browserVersion >= 3.0)
				{
					javascriptOK = true;
					fileUploadOK = true;
				}		
				if( browserVersion >= 4.0)
				{
					cssOK = true;
				}
			}
			else if (browserName == MSIE)
			{
				if (browserVersion >= 4.0)
				{
					javascriptOK = true ;
					fileUploadOK = true;
				}		
                if (browserVersion > 4.0)
                {
                    cssOK = true;
                }        
			}
		}
		else if (browserPlatform ==  UNIX) 
		{ 
            if (browserName.equals(MOZILLA))
			{ 
				if (browserVersion >= 3.0) 
				{ 
					javascriptOK = true; 
					fileUploadOK = true; 
				}         
				if (browserVersion >= 4.0) 
				{ 
					cssOK = true; 
				} 
			} 
		}
    }
    
}
