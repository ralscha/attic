package org.apache.commons.http;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache Turbine" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache Turbine", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

/**
 * This class parses the user agent string and sets javasciptOK and
 * cssOK following the rules described below.  If you want to check
 * for specific browsers/versions then use this class to parse the
 * user agent string and use the accessor methods in this class.
 *
 * JavaScriptOK means that the browser understands JavaScript on the
 * same level the Navigator 3 does.  Specifically, it can use named
 * images.  This allows easier rollovers.  If a browser doesn't do
 * this (Nav 2 or MSIE 3), then we just assume it can't do any
 * JavaScript.  Referencing images by load order is too hard to
 * maintain.
 *
 * CSSOK is kind of sketchy in that Nav 4 and MSIE work differently,
 * but they do seem to have most of the functionality.  MSIE 4 for the
 * Mac has buggy CSS support, so we let it do JavaScript, but no CSS.
 *
 * Ported from Leon's PHP code at
 * http://www.working-dogs.com/freetrade by Frank.
 *
 * @author <a href="mailto:frank.kim@clearink.com">Frank Y. Kim</a>
 * @author <a href="mailto:leon@clearink.com">Leon Atkisnon</a>
 * @author <a href="mailto:mospaw@polk-county.com">Chris Mospaw</a>
 * @author <a href="mailto:bgriffin@cddb.com">Benjamin Elijah Griffin</a>
 * @version $Id: BrowserDetector.java,v 1.1 2002/02/22 18:14:31 sanders Exp $
 */
public class BrowserDetector
{
    protected static final String MSIE = "MSIE";
    protected static final String OPERA = "Opera";
    protected static final String MOZILLA = "Mozilla";

    protected static final String WINDOWS = "Windows";
    protected static final String UNIX = "Unix";
    protected static final String MACINTOSH = "Macintosh";

    /** The user agent string. */
    private String userAgentString = "";

    /** The browser name specified in the user agent string. */
    private String browserName = "";

    /**
     * The browser version specified in the user agent string.  If we
     * can't parse the version just assume an old browser.
     */
    private float browserVersion = (float)1.0;

    /**
     * The browser platform specified in the user agent string.
     */
    private String browserPlatform = "unknown";

    /** Whether or not javascript works in this browser. */
    private boolean javascriptOK = false;

    /** Whether or not CSS works in this browser. */
    private boolean cssOK = false;

    /** Whether or not file upload works in this browser. */
    private boolean fileUploadOK = false;

    /**
     * Constructor used to initialize this class.
     *
     * @param userAgentString A String with the user agent field.
     */
    public BrowserDetector(String userAgentString)
    {
        this.userAgentString = userAgentString;
        parse();
    }

    /**
     * Whether or not CSS works in this browser.
     *
     * @return True if CSS works in this browser.
     */
    public boolean isCssOK()
    {
        return cssOK;
    }

    /**
     * Whether or not file upload works in this browser.
     *
     * @return True if file upload works in this browser.
     */
    public boolean isFileUploadOK()
    {
        return fileUploadOK;
    }

    /**
     * Whether or not Javascript works in this browser.
     *
     * @return True if Javascript works in this browser.
     */
    public boolean isJavascriptOK()
    {
        return javascriptOK;
    }

    /**
     * The browser name specified in the user agent string.
     *
     * @return A String with the browser name.
     */
    public String getBrowserName()
    {
        return browserName;
    }

    /**
     * The browser platform specified in the user agent string.
     *
     * @return A String with the browser platform.
     */
    public String getBrowserPlatform()
    {
        return browserPlatform;
    }

    /**
     * The browser version specified in the user agent string.
     *
     * @return A String with the browser version.
     */
    public float getBrowserVersion()
    {
        return browserVersion;
    }

    /**
     * The user agent string for this class.
     *
     * @return A String with the user agent.
     */
    public String getUserAgentString()
    {
        return userAgentString;
    }

    /**
     * Helper method to initialize this class.
     */
    private void parse()
    {
        int versionStartIndex = userAgentString.indexOf("/");
        int versionEndIndex = userAgentString.indexOf(" ");

        // Get the browser name and version.
        browserName = userAgentString.substring(0, versionStartIndex);
        try
        {
            // Not all user agents will have a space in the reported
            // string.
            String agentSubstring = null;
            if (versionEndIndex < 0)
            {
                agentSubstring =
                    userAgentString.substring(versionStartIndex + 1);
            }
            else
            {
                agentSubstring = userAgentString.substring
                    (versionStartIndex + 1, versionEndIndex);
            }
            browserVersion = toFloat( agentSubstring );
        }
        catch (NumberFormatException e)
        {
            // Just use the default value.
        }

        // MSIE lies about its name.  Of course...
        if (userAgentString.indexOf(MSIE) != -1)
        {
            // Ex: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)
            versionStartIndex = (userAgentString.indexOf(MSIE) +
                                 MSIE.length() + 1);
            versionEndIndex = userAgentString.indexOf(";", versionStartIndex);

            browserName = MSIE;
            try
            {
                browserVersion = toFloat(userAgentString.substring
                                         (versionStartIndex, versionEndIndex));
            }
            catch (NumberFormatException e)
            {
                // Just use the default value.
            }

            // PHP code
            // $Browser_Name = "MSIE";
            // $Browser_Version = strtok("MSIE");
            // $Browser_Version = strtok(" ");
            // $Browser_Version = strtok(";");
        }

        // Opera isn't completely honest, either...
        // Modificaton by Chris Mospaw <mospaw@polk-county.com>
        if (userAgentString.indexOf(OPERA) != -1)
        {
            //Ex: Mozilla/4.0 (Windows NT 4.0;US) Opera 3.61  [en]
            versionStartIndex = (userAgentString.indexOf(OPERA) +
                                 OPERA.length() + 1);
            versionEndIndex = userAgentString.indexOf( " ",
                                                       versionStartIndex );

            browserName = OPERA;
            try
            {
                browserVersion = toFloat(userAgentString.substring
                                         (versionStartIndex, versionEndIndex));
            }
            catch (NumberFormatException e)
            {
                // Just use the default value.
            }

            // PHP code
            // $Browser_Name = "Opera";
            // $Browser_Version = strtok("Opera");
            // $Browser_Version = strtok("/");
            // $Browser_Version = strtok(";");
        }


        // Try to figure out what platform.
        if ( (userAgentString.indexOf("Windows") != -1) ||
             (userAgentString.indexOf("WinNT") != -1) ||
             (userAgentString.indexOf("Win98") != -1) ||
             (userAgentString.indexOf("Win95") != -1) )
        {
            browserPlatform = WINDOWS;
        }

        if (userAgentString.indexOf("Mac") != -1)
        {
            browserPlatform = MACINTOSH;
        }

        if (userAgentString.indexOf("X11") != -1)
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

    /**
     * Helper method to conver String to a float.
     *
     * @param s A String.
     * @return The String converted to float.
     */
    private static final float toFloat(String s)
    {
        return Float.valueOf(s).floatValue();
    }
}
