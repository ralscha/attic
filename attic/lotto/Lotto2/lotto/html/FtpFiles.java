package lotto.html;

import lotto.util.*;
import com.oroinc.net.ftp.*;
import java.util.*;
import java.io.*;

public class FtpFiles {


    private String path;
    private FileInputStream input;
    private FtpConnection ftpconn;

    public FtpFiles(String path, FtpConnection ftpc) {
        this.path = path;
        this.ftpconn = ftpc;
    }

    // true  = OK
    // false = failure
    public boolean sendFiles() {
        Vector lottofiles = new Vector();
        FTPClient ftp = new FTPClient();
        int reply;

        try {
            File t = new File(path);
            String flist[] = t.list();
            String fileName;

            ftp.connect(ftpconn.getHostname());            
            //ftp.connect(AppProperties.getInstance().getProperty("hostName"));
            
            DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "sendFiles : " + ftp.getReplyString());

            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "sendFiles : FTP server refused connection");
              	return false;
            } else {
//                if (ftp.login(AppProperties.getInstance().getProperty("userName"), AppProperties.getInstance().getProperty("password"))) {
                if (ftp.login(ftpconn.getUsername(), ftpconn.getPassword())) {
                    DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "sendFiles : " + ftp.getReplyString());
                    DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "sendFiles : Remote system is " + ftp.getSystemName());

//                	ftp.changeWorkingDirectory(AppProperties.getInstance().getProperty("homeDirectory"));
                	ftp.changeWorkingDirectory(ftpconn.getHomedir());
                    DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "sendFiles : " + ftp.getReplyString());

                    ftp.setFileType(FTP.IMAGE_FILE_TYPE);
                    DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "sendFiles : " + ftp.getReplyString());

                    for (int i = 0; i < flist.length; i++) {
                        if (isFileOK(flist[i])) {
                            fileName = path + "\\" + flist[i];
                            input = new FileInputStream(fileName);
                            if (input != null) {
                                DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "sendFiles : deleting "+flist[i]);
                                ftp.deleteFile(flist[i]);
                                DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "sendFiles : " + ftp.getReplyString());

                                DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "sendFiles : Storing "+fileName);
                                ftp.storeFile(flist[i], input);
                                DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "sendFiles : " + ftp.getReplyString());
                                input.close();
                            }
                        }
                    }
                    return true;
                } else {
       	            DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "sendFiles : " + ftp.getReplyString());
                	return false;
                }
            }

        } catch(IOException e) {
            if(ftp.isConnected()) {
            	try {
                  ftp.disconnect();
            	} catch(IOException f) { }
            }
            DiskLog.getInstance().log(DiskLog.FATAL, getClass().getName(), "sendFiles : " + e);
            return false;
        }
    }

    private boolean isFileOK(String fileName) {
        if ( (fileName.endsWith(".html")) ||
             (fileName.endsWith(".htm"))  ||
             (fileName.endsWith(".txt"))  ||
             (fileName.endsWith(".dat"))  ||
             (fileName.endsWith(".data")))
           return true;
        else
            return false;
    }
}
