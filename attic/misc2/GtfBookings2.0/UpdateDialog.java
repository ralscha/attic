
import com.sun.java.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.border.*;
import com.sun.java.swing.event.*;
import java.util.*;
import java.io.*;
import com.oroinc.net.ftp.*;

public class UpdateDialog extends JDialog implements Runnable
{

    JTextArea textArea;
    JTextField userField;
    JPasswordField pwField;
    JButton startButton;
    JButton closeButton;
 

	public UpdateDialog(JFrame parent) {
        super(parent, "Update Bookings", false);
        
        
   	    try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) { }

        JPanel panel = new JPanel() {
                        public Insets getInsets() {
                            return (new Insets(5,5,5,5));
                        }
                        public Dimension getPreferredSize() {
                            return (getMinimumSize());
                        }
                        public Dimension getMinimumSize() {
                            return (new Dimension(500,300));
                        }};

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel userLabel = new JLabel("User:");
        JLabel pwLabel   = new JLabel("Password:");
        userField = new JTextField(10);
        userField.setText("gtfftp");
        pwField = new JPasswordField(15);
        startButton = new JButton("Start");
        closeButton = new JButton("Close");

        startButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            startFtpSession();
                                        }});

        closeButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            closeWindow();
                                        }});
        northPanel.add(userLabel);
        northPanel.add(userField);
        northPanel.add(pwLabel);
        northPanel.add(pwField);
        northPanel.add(startButton);
        northPanel.add(closeButton);


        textArea = new JTextArea();

        textArea.setBackground(SystemColor.control);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED));


        panel.setLayout(new BorderLayout());
        panel.add("North", northPanel);
        panel.add("Center", scrollPane);
        getContentPane().add(panel);

		pack();
		setVisible(true);
		pwField.requestFocus();
		
    }

    void closeWindow() {
        setVisible(false);    
    }
    
    
    void startFtpSession() {
        startButton.setEnabled(false);
        closeButton.setEnabled(false);
        pwField.setEnabled(false);
        userField.setEnabled(false);

        Thread runner = new Thread(this);
        runner.start();
    }

    public void run() {
        if (retrieveFiles("192.168.1.2", userField.getText(), pwField.getText()))
            textArea.append("\nOKAY");
        else
            textArea.append("\nFAIL");

        startButton.setEnabled(true);
        closeButton.setEnabled(true);
        pwField.setEnabled(true);
        userField.setEnabled(true);

    }

    boolean retrieveFiles(String server, String user, String pw) {
        Vector contFiles = new Vector();
        Vector firmFiles = new Vector();
        boolean ok = true;
        FTPClient ftp;

        ftp = new FTPClient();
        //ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        try {
            int reply;
            ftp.connect(server);
            textArea.append("\nConnected to " + server + "\n");
            reply = ftp.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
        	    textArea.append("\nFTP server refused connection.\n");
        	    return (false);
            }
        } catch(IOException e) {
            if(ftp.isConnected()) {
                try {
	                ftp.disconnect();
            	} catch(IOException f) { }
            }
            textArea.append("\nCould not connect to server.\n");
            return(false);
        }

        try {
            if(ftp.login(user, pw)) {
                FTPFile[] files = ftp.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().endsWith(".del"))
                        if (files[i].getName().startsWith("cont_"))
                            contFiles.addElement(files[i].getName());
                        else if (files[i].getName().startsWith("firm_"))
                            firmFiles.addElement(files[i].getName());
                }

                ftp.setFileType(FTP.ASCII_FILE_TYPE);

                if (contFiles.size() > 0 || firmFiles.size() > 0) {
                    
                    InputStream is;
                    for (int i = 0; ok && i < contFiles.size(); i++) {
                        textArea.append("reading ... "+(String)contFiles.elementAt(i)+"...");
                        is = ftp.retrieveFileStream((String)contFiles.elementAt(i));
                        readContFile(new BufferedReader(new InputStreamReader(is)));
                        is.close();
                        if(!ftp.completePendingCommand()) {
                            textArea.append("fail\n");
                            ok = false;
                        }
                        else {
                            textArea.append("ok\n");
                        }
                    }

                    for (int i = 0; ok && i < firmFiles.size(); i++) {
                        textArea.append("reading ... "+(String)firmFiles.elementAt(i)+"...");
                        is = ftp.retrieveFileStream((String)firmFiles.elementAt(i));
                        readFirmFile(new BufferedReader(new InputStreamReader(is)));
                        is.close();
                        if(!ftp.completePendingCommand()) {
                            textArea.append("fail\n");
                            ok = false;
                        }
                        else {
                            textArea.append("ok\n");
                        }
                    }

                    // Delete Files
                    if (ok) {
                        for (int i = 0; i < contFiles.size(); i++) {
                            textArea.append("deleting ... "+(String)contFiles.elementAt(i)+"...");
                            if (ftp.deleteFile((String)contFiles.elementAt(i)))
                                textArea.append("ok\n");
                            else
                                textArea.append("fail\n");
                        }
                        for (int i = 0; i < firmFiles.size(); i++) {
                            textArea.append("deleting ... "+(String)firmFiles.elementAt(i)+"...");
                            if (ftp.deleteFile((String)firmFiles.elementAt(i)))
                                textArea.append("ok\n");
                            else
                                textArea.append("fail\n");
                        }

                    }
             	}
            }
            else {
                textArea.append("\nLogin failed");
                ok = false;
            }
           	ftp.quit();
        } catch(FTPConnectionClosedException e) {
            ok = false;
        } catch(IOException e) {
            ok = false;
        } finally {
            if(ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch(IOException f) { }
            }
        }

        return(ok);
    }


    void readContFile(BufferedReader dis) throws IOException, NumberFormatException {
        StreamTokenizer st;
        String line = null;
        String dateStr = null;
        int type;
        Vector elem = new Vector();

        ContingentLiability cl = new ContingentLiability();

        while ((line = dis.readLine()) != null) {
            if (line.length() > 200) {
                elem.removeAllElements();
                st = createStreamTokenizer(line);

                while((type = st.nextToken()) != StreamTokenizer.TT_EOF) {
                    if ((type == StreamTokenizer.TT_WORD) ||
                        (type == '"'))
                        elem.addElement(st.sval);
                }

                cl.setSequence_number(Integer.parseInt((String)elem.elementAt(1)));
                cl.setType((String)elem.elementAt(2));
                cl.setGtf_number(Integer.parseInt(((String)elem.elementAt(3)).trim()));
                cl.setCurrency((String)elem.elementAt(4));
                cl.setAmount(Double.valueOf((String)elem.elementAt(5)).doubleValue());
                cl.setAcct_mgmt_unit((String)elem.elementAt(6));
                cl.setAccount_number((String)elem.elementAt(7));

                cl.setExpiry_date((String)elem.elementAt(8));

                cl.setExchange_rate(Double.valueOf((String)elem.elementAt(9)).doubleValue());
                cl.setGtf_proc_center((String)elem.elementAt(10));
                cl.setCustomer_ref((String)elem.elementAt(11));
                cl.setGtf_type((String)elem.elementAt(12));
                cl.setDossier_item((String)elem.elementAt(13));
                cl.setBu_code((String)elem.elementAt(14));

 //               driver.insert(cl);
            }
        }
        dis.close();
    }

    void readFirmFile(BufferedReader dis) throws IOException, NumberFormatException {
        String line = null;
        String dateStr = null;
        StreamTokenizer st;
        int type;
        Vector elem = new Vector();
        FirmLiability fl = new FirmLiability();

        while ((line = dis.readLine()) != null) {
            if (line.length() > 200) {

                elem.removeAllElements();
                st = createStreamTokenizer(line);

                while((type = st.nextToken()) != StreamTokenizer.TT_EOF) {
                    if ((type == StreamTokenizer.TT_WORD) ||
                        (type == '"'))
                        elem.addElement(st.sval);
                }

                fl.setSequence_number(Integer.parseInt((String)elem.elementAt(1)));
                fl.setGtf_number(Integer.parseInt(((String)elem.elementAt(2)).trim()));
                fl.setCurrency((String)elem.elementAt(3));
                fl.setAmount(Double.valueOf((String)elem.elementAt(4)).doubleValue());
                fl.setDeb_acct_mgmt_unit((String)elem.elementAt(5));
                fl.setDeb_acct_number((String)elem.elementAt(6));
                fl.setCre_acct_mgmt_unit((String)elem.elementAt(7));
                fl.setCre_acct_number((String)elem.elementAt(8));

                fl.setValue_date((String)elem.elementAt(9));
                fl.setGtf_proc_center((String)elem.elementAt(11));
                fl.setCustomer_ref((String)elem.elementAt(12));
                fl.setGtf_type((String)elem.elementAt(13));
                fl.setDossier_item((String)elem.elementAt(14));
                fl.setBu_code((String)elem.elementAt(15));

   //             driver.insert(fl);
            }
        }
        dis.close();
    }

    StreamTokenizer createStreamTokenizer(String str) {
        StreamTokenizer st = new StreamTokenizer(new StringReader(str));
        st.resetSyntax();
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        st.wordChars(128 + 32, 255);
        st.wordChars('0', '9');
        st.wordChars('+', '+');
        st.wordChars('-', '-');
        st.wordChars('.', '.');
        st.whitespaceChars(0, ' ');
        st.commentChar('/');
        st.quoteChar('"');
        st.quoteChar('\'');
        return(st);
    }

}
