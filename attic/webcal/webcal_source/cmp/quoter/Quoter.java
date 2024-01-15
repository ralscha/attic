// Quoter.java the CMP HTML Quoter Amanuensis

/*
See quoter.use for a description of what this program does.
Performs various cleanups and conversions on the text in the clipboard.
*/

/**
 *
 * @author  copyright (c) 1998-1999 Roedy Green of Canadian Mind Products
 * may be copied and used freely for any purpose but military.
 *
 * Roedy Green
 * Canadian Mind Products
 * 5317 Barker Avenue
 * Burnaby, BC Canada V5H 2N6
 * tel: (604) 435-3052
 * mailto:roedy@mindprod.com
 * http://mindprod.com
 */


/*
 * version 1.8 1999 December 2   - high chars -> \ u xxxx
 * version 1.7 1999 September 18 - add collapse multiple spaces
 * version 1.6 1999 September 13 - clearer Choice descriptions.
 * version 1.5 1999 September 12 - allow Applet command line parameter to simulate
 *                                 applet mode when running as application.
 *                                 fix bug in translate table that was sometimes stripping \ns.
 * version 1.4 1999 September 12 - UPPER, lower and Title case conversion,
 *                               - text column alignment
 *                               - Java source alignment.
 * version 1.3 1999 September 11 - allow plain, just strip control chars
 *                               - trim lead/trail spaces.
 *                               - in Applet, choice changes are now Live and
 *                                 trigger a convert action.
 * version 1.2 1999 September 10 - add Java source code strings as target.
 *
 * version 1.1 1998 December 28 - handle empty or null clipboard better.
 *                              - handle Latin1, Windows and IBM OEM character set encodings.
 * version 1.0 1998 December 25 - initial release.
 *
 * See sample files:
 * QuoterTest.bat     - run locally as Applet/Application jar/no jar
 * QuoterTest.html    - run locally as Applet
 * QuoterNative.html  - run from a website
 * QuoterPlugIn.html  - run from a website with Java Plug-in.

 */

package cmp.quoter;

import cmp.business.Misc;
import java.applet.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.StringTokenizer;

public class Quoter extends Applet implements java.awt.datatransfer.ClipboardOwner
{

    static final boolean debugging = false;

    private static final String EmbeddedCopyright =
    "Copyright 1998 Roedy Green, Canadian Mind Products, http://mindprod.com";

    // possible encodings
    static final int LATIN1 = 0;
    static final int WIN = 1;
    static final int IBMOEM = 2;

    // possible targets
    static final int HTML = 0;
    static final int JAVA = 1;
    static final int PLAIN = 2;
    static final int COLLAPSESPACES = 3;
    static final int COLLAPSELINES = 4;
    static final int ALIGNED = 5;
    static final int ALIGNEDJAVA = 6;
    static final int UPPER = 7;
    static final int LOWER = 8;
    static final int TITLE = 9;

    // possible trims
    static final int TRIMTRAILING = 0;
    static final int TRIM = 1;
    static final int KEEPSPACES = 2;
    static final int TRIMLEADING = 3;

    /* constructors */
    public Quoter()
    {

    }

    private Quoter(boolean asApplet)
    {
        this.asApplet = asApplet;
    }

    /* true if being run as an Applet, false as an Application */
    private static  boolean asApplet = true;

    // Use our own colours so Symantec won't mess with them or create dups.
    private static final Color black = Color.black;
    private static final Color blue =  Color.blue;
    private static final Color darkGreen = new Color(0, 128, 0);
    private static final Color red =   Color.red;
    private static final Color white = Color.white;

    /**
     * Standard method to initialise the applet
     */
    public void init()
    {

        setBackground(white);

        if ( ! Misc.isJavaVersionOK(1, 1, 0) )
        {
            System.out.println("You need Java 1.1.0 or later to run this Applet.");
            System.out.println("You are running under " + System.getProperty("java.version"));
            System.exit(1);
        }
        // Applet Gridbag:
        //      0          1
        // --------title--------    0
        // --------raw----------    1
        // -------cooked--------    2
        //  Charset  Clear  Convert 3
        //  target                  4
        //  trim                    5
        // -----instructions-----   6

        // Application Gridbag:
        //      0          1
        // --------title--------    0
        //                          1
        //                          2
        //  Charset      Convert    3
        //  target                  4
        //  trim                    5
        // -----instructions-----   6

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc;

        setLayout(gridBagLayout);

        titleLabel = new java.awt.Label("CMP HTML Quoter Amanuensis 1.8",Label.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        titleLabel.setForeground(red);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        // Top, Left, Bottom, Right
        gbc.insets = new Insets(10,10,10,10);
        ((GridBagLayout)getLayout()).setConstraints(titleLabel, gbc);
        add(titleLabel);

        if ( asApplet )
        {
            // With applet we have two text windows you must manually cut/paste,
            // with Clear and Convert Button.
            // With app[ication, you have no windows, just the Convert Button.
            // When run as application, the raw and cooked textareas don't exist.
            rawTextArea = new java.awt.TextArea("", 3, 50, TextArea.SCROLLBARS_BOTH);
            rawTextArea.setEditable(true);
            rawTextArea.setFont(new Font("Dialog", Font.PLAIN, 12));
            rawTextArea.setForeground(black);
            rawTextArea.setBackground(white);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            gbc.gridheight = 1;
            gbc.weightx = 0.;
            gbc.weighty = 10.;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.fill = GridBagConstraints.BOTH;
            // Top, Left, Bottom, Right
            gbc.insets = new Insets(10,10,10,10);
            ((GridBagLayout)getLayout()).setConstraints(rawTextArea, gbc);
            add(rawTextArea);

            cookedTextArea = new java.awt.TextArea("", 3, 50, TextArea.SCROLLBARS_BOTH);
            cookedTextArea.setEditable(false);
            cookedTextArea.setFont(new Font("Dialog", Font.PLAIN, 12));
            cookedTextArea.setForeground(black);
            cookedTextArea.setBackground(white);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 3;
            gbc.gridheight = 1;
            gbc.weightx = 0.;
            gbc.weighty = 10.;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            gbc.fill = GridBagConstraints.BOTH;
            // Top, Left, Bottom, Right
            gbc.insets = new Insets(10,10,10,10);
            ((GridBagLayout)getLayout()).setConstraints(cookedTextArea, gbc);
            add(cookedTextArea);


        } // end if asApplet

        _encoding = new java.awt.Choice();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 30.;
        gbc.weighty = 0.;
        gbc.ipadx = 20;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        // Top, Left, Bottom, Right
        gbc.insets = new Insets(5,10,5,10);
        ((GridBagLayout)getLayout()).setConstraints(_encoding, gbc);
        // add legal choices to choice box LATIN1 WIN IBMOEM
        _encoding.addItem("Latin1 Unicode");
        _encoding.addItem("Windows");
        _encoding.addItem("IBM PC OEM");
        // make Latin1 the default
        _encoding.select(0);
        add(_encoding);

        _target = new java.awt.Choice();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 30.;
        gbc.weighty = 0.;
        gbc.ipadx = 20;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        // Top, Left, Bottom, Right
        gbc.insets = new Insets(5,10,5,10);
        ((GridBagLayout)getLayout()).setConstraints(_target, gbc);
        // add legal choices to choice box
        //  HTML JAVA PLAIN COLLAPSESPACE COLLAPSLINES ALIGNED ALIGNEDJAVA UPPER LOWER TITLE

        _target.addItem("to HTML");
        _target.addItem("to Java String");
        _target.addItem("Remove control chars (except tab)");
        _target.addItem("Collapse multiple spaces");
        _target.addItem("Collapse multiple blank lines");
        _target.addItem("Align text in columns");
        _target.addItem("Align Java source in columns");
        _target.addItem("TO UPPER CASE");
        _target.addItem("to lower case");
        _target.addItem("To Book Title Case");


        // make Latin1 the default
        _target.select(HTML);
        add(_target);

        _trim = new java.awt.Choice();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 30.;
        gbc.weighty = 0;
        gbc.ipadx = 20;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        // Top, Left, Bottom, Right
        gbc.insets = new Insets(5,10,5,10);
        ((GridBagLayout)getLayout()).setConstraints(_trim, gbc);
        // add legal choices to choice box  TRIMTRAILING TRIM KEEPSPACES TRIMLEADING
        _trim.addItem("trim trailing spaces");
        _trim.addItem("trim both leading and trailing spaces");
        _trim.addItem("keep spaces");
        _trim.addItem("trim leading spaces");
        // make Latin1 the default
        _trim.select(0);
        add(_trim);






        if ( asApplet )
        {
            clearButton = new java.awt.Button("Clear");
            // leave colours the default
            clearButton.setFont(new Font("Dialog", Font.BOLD, 16));
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 30.;
            gbc.weighty = 0.;
            gbc.ipadx = 20;
            gbc.ipady = 10;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;
            // Top, Left, Bottom, Right
            gbc.insets = new Insets(5,10,5,10);
            ((GridBagLayout)getLayout()).setConstraints(clearButton, gbc);
            add(clearButton);
        } // end if asApplet

        convertButton = new java.awt.Button("Convert");
        // leave colours the default
        convertButton.setFont(new Font("Dialog", Font.BOLD, 16));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 30.;
        gbc.weighty = 0.;
        gbc.ipadx = 20;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        // Top, Left, Bottom, Right
        gbc.insets = new Insets(5,10,5,10);
        ((GridBagLayout)getLayout()).setConstraints(convertButton, gbc);
        add(convertButton);


        instructions = new java.awt.Label("",Label.CENTER);
        getSelections();
        instructions.setFont(new Font("Dialog", Font.PLAIN, 9));
        instructions.setBackground(white);
        instructions.setForeground(darkGreen);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.ipadx = 0;
        gbc.ipady = 20;
        gbc.fill = GridBagConstraints.NONE;
        // Top, Left, Bottom, Right
        gbc.insets = new Insets(5,5,5,5);
        ((GridBagLayout)getLayout()).setConstraints(instructions, gbc);
        add(instructions);




        // REGISTER LISTENER

        TheListener theListener = new TheListener();
        if ( asApplet )
        {
            clearButton.addActionListener(theListener);
        }
        convertButton.addActionListener(theListener);
        _encoding.addItemListener(theListener);
        _target.addItemListener(theListener);
        _trim.addItemListener(theListener);

        this.validate();
        this.setVisible(true);

    } // end init

    // DECLARE CONTROLS
    java.awt.Label    titleLabel;
    java.awt.TextArea rawTextArea;
    java.awt.TextArea cookedTextArea;
    java.awt.Choice   _encoding;
    java.awt.Choice   _target;
    java.awt.Choice   _trim;
    java.awt.Button   clearButton;
    java.awt.Button   convertButton;
    java.awt.Label    instructions;

    // Latin1 is the default  e.g. JavaReservedChars.latin1;
    private String[] translateTable;

    /**
     * Class to handle button events
     */
    class TheListener implements java.awt.event.ActionListener,
    java.awt.event.ItemListener
    {

        public void actionPerformed(java.awt.event.ActionEvent event)
        {
            Object object = event.getSource();
            if ( object == convertButton )
                convertAction();
            else if ( object == clearButton )
            {
                rawTextArea.setText(null);
                cookedTextArea.setText(null);
            }
        } // end actionPerformed


        public void itemStateChanged (java.awt.event.ItemEvent event)
        {
            Object object = event.getSource();
            if ( object == _encoding
                 || object == _target
                 || object == _trim )
            {

                // with applet, can make Choices live, can't be in application
                // otherwise might prematurely convert the clipboard.
                // with applet, you don't lose the original input, so might as well
                // do it live.
                if ( asApplet )
                {
                    convertAction();
                }
                else
                {
                    // just update the instructions, don't actually convert.
                    getSelections();
                }
                if ( object == _target )
                {
                    // label has grown or shrunk
                    invalidate();
                    validate();
                }
            }

        }  // end itemStateChanged

    } // end TheListener

    /**
     * action when Convert button pressed
     */
    void convertAction()
    {

        /**
         * The original format of the clipboard before conversion.
         * Lines in the clipboard are separated with \n characters.
         * There may or may not be a final \n.
         * I have only tested this code under NT.  I am not sure if
         * this is universal.
         *
         */
        String raw;


        /**
         * clipboard after optional stripping done of lead/trail spaces
         */
        String parboiled;

        /**
         * The converted form of the clipboard after conversion.
         */
        String cooked;

        getSelections();
        if ( asApplet )
        {
            // as Applet, must rely on manual cut paste to raw and cooked TextAreas.
            raw = rawTextArea.getText();
        }
        else
        {
            // as application, grab clipboard automatically
            raw = ClipboardPoker.getClip(this);
        }

        if ( debugging )
        {
            // dump the raw input to view.
            char[] ca = raw.toCharArray();
            for ( int i=0; i<ca.length; i++ )
            {
                System.out.println(ca[i]+ " " + (int) ca[i]);
            } // end for
        } // end if debugging

        // optionally strip lead/trail spaces

        switch ( _trim.getSelectedIndex() )
        {

            case TRIMTRAILING:
                // break clipboard into lines delimited by \n
                if ( raw == null )
                {
                    parboiled = null;
                }
                else
                {
                    StringBuffer stripped = new StringBuffer(raw.length());
                    StringTokenizer st = new StringTokenizer(raw, "\n", true /* return delims */);

                    while ( st.hasMoreTokens() )
                    {
                        String token = st.nextToken();
                        if ( token.equals("\n") )
                        {
                            stripped.append("\n");
                        }
                        else
                        {
                            stripped.append(Misc.trimTrailing(token));
                        }
                    } // end while
                    parboiled = stripped.toString();
                }
                break;

            case TRIM: /* lead and trail */
                // break clipboard into lines delimited by \n
                if ( raw == null )
                {
                    parboiled = null;
                }
                else
                {
                    StringBuffer stripped = new StringBuffer(raw.length());
                    StringTokenizer st = new StringTokenizer(raw, "\n", true /* return delims */);

                    while ( st.hasMoreTokens() )
                    {
                        String token = st.nextToken();
                        if ( token.equals("\n") )
                        {
                            stripped.append("\n");
                        }
                        else
                        {
                            stripped.append(token.trim());
                        }
                    } // end while
                    parboiled = stripped.toString();
                }
                break;

            default:
            case KEEPSPACES:
                parboiled = raw;
                break;

            case TRIMLEADING:
                // break clipboard into lines delimited by \n
                if ( raw == null )
                {
                    parboiled = null;
                }
                else
                {
                    StringBuffer stripped = new StringBuffer(raw.length());
                    StringTokenizer st = new StringTokenizer(raw, "\n", true /* return delims */);

                    while ( st.hasMoreTokens() )
                    {
                        String token = st.nextToken();
                        if ( token.equals("\n") )
                        {
                            stripped.append("\n");
                        }
                        else
                        {
                            stripped.append(Misc.trimLeading(token));
                        }
                    } // end while
                    parboiled = stripped.toString();
                }
                break;


        } // end switch

        switch ( target )
        {

            default:
            case HTML:
                cooked = HTMLReservedChars.toQuoted(parboiled, translateTable);
                break;

            case JAVA:
                cooked = JavaReservedChars.toQuoted(parboiled, translateTable);
                break;

            case PLAIN:
                cooked = PlainReservedChars.toQuoted(parboiled, translateTable);
                break;

            case COLLAPSESPACES:
                cooked = collapseEmbeddedSpaces(PlainReservedChars.toQuoted(parboiled, translateTable));
                break;

            case COLLAPSELINES:
                cooked = collapseBlankLines(PlainReservedChars.toQuoted(parboiled, translateTable));
                break;

            case ALIGNED:
                cooked = new Align().align(PlainReservedChars.toQuoted(parboiled, translateTable));
                break;

            case ALIGNEDJAVA:
                cooked = new AlignJava().align(PlainReservedChars.toQuoted(parboiled, translateTable));
                break;

            case UPPER:
                cooked = PlainReservedChars.toQuoted(parboiled, translateTable).toUpperCase();
                break;

            case LOWER:
                cooked = PlainReservedChars.toQuoted(parboiled, translateTable).toLowerCase();
                break;

            case TITLE:
                cooked = Misc.toBookTitleCase(PlainReservedChars.toQuoted(parboiled, translateTable));
                break;



        } // end switch

        if ( asApplet )
        {
            cookedTextArea.setText(cooked);
        }
        else
        {
            ClipboardPoker.setClip(cooked, this);
        }// end else
    } // end convertButton_ActionPerformed

    int target = HTML; /* LATIN1=html or 1=Java */

    /**
     * Get the state of the various choice boxes and
     * set up corresponding internal variables.
     */
    void getSelections()
    {
        switch ( target = _target.getSelectedIndex() )
        {
            case HTML:
                {

                    if ( asApplet )
                    {
                        instructions.setText("Paste raw text to upper; click Convert; then Copy cooked HTML from lower.");
                    }
                    else
                    {
                        instructions.setText("Copy raw text to the clipboard; click Convert; then Paste cooked HTML.");
                    }

                    switch ( _encoding.getSelectedIndex() )
                    {

                        default:
                        case LATIN1:
                            translateTable = HTMLReservedChars.latin1;
                            break;

                        case WIN:
                            translateTable = HTMLReservedChars.win;
                            break;

                        case IBMOEM:
                            translateTable = HTMLReservedChars.ibmoem;
                            break;
                    } // end switch
                } // end HTML case
                break;



            case JAVA:
                {
                    if ( asApplet )
                    {
                        instructions.setText("Paste raw text to upper; click Convert; then Copy cooked Java code from lower.");
                    }
                    else
                    {
                        instructions.setText("Copy raw text to the clipboard; click Convert; then Paste cooked Java.");
                    }
                    switch ( _encoding.getSelectedIndex() )
                    {

                        default:
                        case LATIN1:
                            translateTable = JavaReservedChars.latin1;
                            break;

                        case WIN:
                            translateTable = JavaReservedChars.win;
                            break;

                        case IBMOEM:
                            translateTable = JavaReservedChars.ibmoem;
                            break;
                    } // end switch
                } // end JAVA case
                break;

            default:
            case PLAIN:
            case COLLAPSESPACES:
            case COLLAPSELINES:
            case ALIGNED:
            case ALIGNEDJAVA:
            case UPPER:
            case LOWER:
            case TITLE:
                {
                    /* Remove Control Chars */
                    if ( asApplet )
                    {
                        instructions.setText("Paste raw text to upper; click Convert; then Copy tidied text from lower.");
                    }
                    else
                    {
                        instructions.setText("Copy raw text to the clipboard; click Convert; then Paste tidied text.");
                    }

                    switch ( _encoding.getSelectedIndex() )
                    {

                        default:
                        case LATIN1:
                            translateTable = PlainReservedChars.latin1;
                            break;

                        case WIN:
                            translateTable = PlainReservedChars.win;
                            break;

                        case IBMOEM:
                            translateTable = PlainReservedChars.ibmoem;
                            break;
                    } // end switch
                } // end PLAIN case
                break;


        } // end  outer switch

        if ( asApplet )
        {
            if ( target == ALIGNED || target == ALIGNEDJAVA )
            {
                rawTextArea.setFont(monoFont);
                cookedTextArea.setFont(monoFont);
            }
            else
            {
                rawTextArea.setFont(regularFont);
                cookedTextArea.setFont(regularFont);
            }
        }
    } // end getSelections

    static Font monoFont = new Font("Monospaced", Font.PLAIN, 12);
    static Font regularFont = new Font("Dialog", Font.PLAIN, 12);

    /**
     * Collapse embedded runs of spaces to a single space.
     * Leave leading and trailing spaces on each line as is.
     * @param s String to process, may contain embedded \ns.
     * @return String with spaces collapsed.
     */
    static String collapseEmbeddedSpaces(String s)
    {
        if ( s == null )
        {
            return null;
        }
        char[] ca = new char[s.length()];
        int j = 0;
        // true if next space should be suppressed
        boolean suppress = false;
        // true if we are in the leading spaces of a line
        boolean inLeading = true;
        // count of how many spaces we recently suppressed
        int spacesSuppressed = 0;
        for ( int i=0; i<s.length(); i++ )
        {
            char c = s.charAt(i);
            switch ( c )
            {

                case '\t':
                case ' ':
                    if ( inLeading || !suppress )
                    {
                        ca[j++] = c;
                        suppress = true;
                    }
                    else
                    {
                        spacesSuppressed++;
                    }
                    break;

                case '\n':
                    // We may have suppressed some trailing spaces we should not have.
                    // put them back.
                    for ( ; spacesSuppressed > 0; spacesSuppressed-- )
                    {
                        ca[j++] = ' ';
                    }
                    ca[j++] = c;
                    inLeading = true;
                    suppress = false;
                    spacesSuppressed = 0;
                    break;

                default:
                    ca[j++] = c;
                    inLeading = false;
                    suppress = false;
                    spacesSuppressed = 0;
                    break;
            } // end switch
        } // end for

        // If there was no trailing \n,
        // we may have suppressed some trailing spaces we should not have.
        // put them back.
        for ( ; spacesSuppressed > 0; spacesSuppressed-- )
        {
            ca[j++] = ' ';
        }

        if ( j != s.length() )
        {
            s = new String (ca, 0 /* offset */, j /* count */);
        }
        return s;
    } // end collapseEmbeddedSpaces

    /**
    * Collapse runs of blank lines to a single blank line.
    * Leave leading and trailing spaces on each line as is.
    * @param s String to process, may contain embedded \ns.
    * @return String with blank lines collapsed.
    */
    static String collapseBlankLines(String s)
    {
        if ( s == null )
        {
            return null;
        }
        char[] ca = new char[s.length()];
        int j = 0;
        // true if next blank line should be suppressed
        boolean suppressBlankLines = false;
        // true if we are in the leading spaces of a line
        boolean inLeading = true;
        // count of how many spaces we recently suppressed
        int spacesSuppressed = 0;
        for ( int i=0; i<s.length(); i++ )
        {
            char c = s.charAt(i);
            switch ( c )
            {

                case '\t':
                case ' ':
                    spacesSuppressed++;
                    break;

                case '\n':
                    if ( inLeading )
                    {
                        // end of blank line
                        if ( suppressBlankLines )
                        {
                            /* throw away blank line */
                            spacesSuppressed = 0;
                        }

                        else
                        {
                            // We may have suppressed some trailing spaces we should not have.
                            // put them back.
                            for ( ; spacesSuppressed > 0; spacesSuppressed-- )
                            {
                                ca[j++] = ' ';
                            }
                            ca[j++] = c;
                            suppressBlankLines = true;
                        }
                    }
                    else
                    {
                        // end end of normal line
                        // We may have suppressed some trailing spaces we should not have.
                        // put them back.
                        for ( ; spacesSuppressed > 0; spacesSuppressed-- )
                        {
                            ca[j++] = ' ';
                        }
                        ca[j++] = c;
                        suppressBlankLines = false;

                    } // end else

                    inLeading = true;
                    break;

                default:
                    // ordinary character
                    // We may have suppressed some spaces we should not have.
                    // put them back.

                    for ( ; spacesSuppressed > 0; spacesSuppressed-- )
                    {
                        ca[j++] = ' ';
                    }

                    ca[j++] = c;
                    inLeading = false;
                    break;
            } // end switch
        } // end for

        // If there was no trailing \n,
        // we may have suppressed some trailing spaces we should not have.
        // put them back.

        if ( ! (inLeading && suppressBlankLines) )
        {
            for ( ; spacesSuppressed > 0; spacesSuppressed-- )
            {
                ca[j++] = ' ';
            }
        }
        if ( j != s.length() )
        {
            s = new String (ca, 0 /* offset */, j /* count */);
        }
        return s;
    } // end collapseEmbeddedSpaces


    /**
     * If we put data in the Clipboard, and somebody else
     * changes the clipboard, we lose ownership and are notified
     * here.
     * Satisfies the requirement of this class
     * to implement java.awt.datatransfer.ClipboardOwner.
     */
    public void lostOwnership(Clipboard clipboard,
                              Transferable contents)
    {

    }

    /**
     * Allow this applet to run as as application as well.
     *
     * @param args command line, "Applet"
     * run the application Applet style with manual clipboard and
     * raw and cooked textboxes.  Applets are automatically run
     * Applet style because of the way the default constructor is defined.
     */
    static public void main(String args[])
    {
        boolean asApplet = false;
        if ( args.length > 0 && args[0].equals("Applet") )
        {
            asApplet = true;
        }
        final Quoter applet = new Quoter(asApplet);
        Frame frame = new Frame("HTML Quoter Amanuensis");
        frame.setSize(440, 260);
        applet.init();
        frame.add(applet);
        frame.validate();
        frame.setVisible(true);
        applet.start();
        frame.addWindowListener
        (
        new java.awt.event.WindowAdapter()
        {
            /**
             * Handle request to shutdown.
             *
             * @param e event giving details of closing.
             */
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                applet.stop();
                applet.destroy();
                System.exit(0);
            } // end WindowClosing
        } // end anonymous class
        ); // end addWindowListener line
    } // end main


} // end class Quoted

/**
 * Lets us get and put the contents of the clipboard.
 * Class should never even be loaded if we are running as an Applet.
 */
class ClipboardPoker
{

    /**
     * handle to the system clipboard for cut/paste
     * Avoid init, unless method actually called.
     */
    private static Clipboard clipboard;

    /**
     * get current contents of the Clipboard as a String.
     * Returns null if any trouble, or if clip is empty.
     * Not legal in a an Applet.
     */
    public static String getClip(Applet owner)
    {
        initClipboardHook(owner);
        if ( clipboard == null )
        {
            return null;
        }
        try
        {
            try
            {
                Transferable contents = clipboard.getContents(owner);
                if ( contents == null )
                {
                    return null;
                }
                // stringFlavor is when you want an ordinary String.
                // plainTextFlavor is for when you want a StringReader instead of a String
                if ( contents.isDataFlavorSupported(DataFlavor.stringFlavor) )
                {
                    // Don't need to worry about a ClassCastException even if result is null.
                    String s = (String) contents.getTransferData(DataFlavor.stringFlavor);
                    if ( s == null )
                    {
                        return null;
                    }
                    if ( s.length() == 0 )
                    {
                        return null;
                    }
                    return s;
                } // end if
            }
            catch ( java.awt.datatransfer.UnsupportedFlavorException e )
            {

            }
        }
        catch ( java.io.IOException e )
        {

        }
        return null;
    } // end getClip

    /**
     * Copy data into the Clipboard.  We become its owner
     * until somebody else changes the value.
     * @param s new contents of clipboard, may be null or "".
     * Not legal in an Applet.
     */
    public static void setClip( String s, Applet owner)
    {
        initClipboardHook(owner);
        if ( clipboard == null )
        {
            return;
        }
        if ( s == null )
        {
            s = "";
        }
        StringSelection contents = new StringSelection(s);
        clipboard.setContents(contents, (ClipboardOwner) owner);
    } // end setClip

    /**
     * initialises hook to the system clipboard
     */
    private static void initClipboardHook (Applet owner)
    {
        if ( clipboard == null )
        {
            Toolkit t = owner.getToolkit();
            if ( t != null )
            {
                clipboard = t.getSystemClipboard();
            } // end if
        } // end if
    } // end initClipBoard

} // end class ClipBoardPoker


