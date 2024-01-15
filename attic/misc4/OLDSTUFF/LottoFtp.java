import com.oroinc.net.ftp.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class LottoFtp extends Frame
{
    private static final String path = "c:\\temp\\";
    private List listbox;
    private FileInputStream input;

    public LottoFtp()
    {
        super("Lotto Ftp");

        setLayout(new BorderLayout());
        addWindowListener(new Exit());
        listbox = new List();
        listbox.setFont(new Font("Courier", Font.PLAIN, 12));
        add("Center", listbox);

        Button exitButton  = new Button("Exit");
        exitButton.addActionListener(new ActionListener()
                                      { public void actionPerformed(ActionEvent a)
                                        { System.exit(0); }});
        add("South", exitButton);

        setSize(430,250);
        setVisible(true);

        sendFiles();

    }

    void sendFiles()
    {
        Vector lottofiles = new Vector();
        FTPClient ftp = new FTPClient();
        int reply;

        try
        {
            File t = new File(path);
            String flist[] = t.list();
            String fileName;

            ftp.connect("ftp.inm.ch");
            listbox.add(ftp.getReplyString());

            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply))
            {
                ftp.disconnect();
            	listbox.add("FTP server refused connection.");
            }
            else
            {
                if (ftp.login("_Ralph", "gurwolf"))
                {
                	listbox.add(ftp.getReplyString());
                    listbox.add("Remote system is " + ftp.getSystemName());

                	ftp.changeWorkingDirectory("/homepages/Ralph/Lotto");
                    listbox.add(ftp.getReplyString());
                    ftp.setFileType(FTP.IMAGE_FILE_TYPE);
                    listbox.add(ftp.getReplyString());
                    listbox.makeVisible(listbox.getItemCount()-1);

                    for (int i = 0; i < flist.length; i++)
                    {
                        listbox.add("Deleting "+flist[i]);
                        ftp.deleteFile(flist[i]);
                        listbox.add(ftp.getReplyString());

                        fileName = path + flist[i];
                        input = new FileInputStream(fileName);
                        listbox.add("Storing "+fileName);
                        ftp.storeFile(flist[i], input);
                        listbox.add(ftp.getReplyString());
                        input.close();
                        listbox.makeVisible(listbox.getItemCount()-1);
                    }
                    listbox.add("THE END THE END THE END");
                    listbox.makeVisible(listbox.getItemCount()-1);
                }
                else
                	listbox.add(ftp.getReplyString());
            }

        }
        catch(IOException e)
        {
            if(ftp.isConnected())
            {
            	try
            	{
                  ftp.disconnect();
            	}
            	catch(IOException f) { }
            }
            e.printStackTrace();
            System.exit(1);
        }
    }


    class Exit extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            System.exit(0);
        }
    }


    public static void main(String args[])
    {
        new LottoFtp();
    }
}
