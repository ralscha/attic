import kasper.net.ftp.*;
import kasper.net.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class LottoFtp extends Frame
{
    private static final String path = "c:\\temp\\";
    private List listbox;

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

        FTPClient client = new FTPClient();
        try
        {
            File t = new File(path);
            String flist[] = t.list();
            String fileName;

            listbox.add(client.connect("ftp.inm.ch").toString());
            listbox.add(client.user("_Ralph").toString());
            listbox.add(client.pass("gurwolf").toString());
            listbox.add(client.cwd("/homepages/Ralph/Lotto").toString());
            listbox.makeVisible(listbox.getItemCount()-1);
            /*
            System.out.println(client.port());
            FTPServerResponse currentResponses[] = client.nlst();
            System.out.println(currentResponses[0]);
            System.out.println(currentResponses[1]);
            RemoteFile remoteFiles[] = currentResponses[0].getRemoteFiles();
            for (int i = 0; i < remoteFiles.length; i++)
                System.out.println(remoteFiles[i]);
            */

            listbox.add(client.type(FTPClient.IMAGE).toString());

            FTPServerResponse tempResponses[];
            for (int i = 0; i < flist.length; i++)
            {
                listbox.add("Deleting "+flist[i]);
                listbox.add(client.dele(flist[i]).toString());

                listbox.add(client.port().toString());
                fileName = path + flist[i];
                listbox.add("Storing "+fileName);
                tempResponses = client.stor(new File(fileName));
                listbox.add(tempResponses[0].toString());
                listbox.add(tempResponses[1].toString());
                listbox.makeVisible(listbox.getItemCount()-1);
            }
            listbox.add("THE END THE END THE END");
        }
        catch (Exception e)
        {
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
