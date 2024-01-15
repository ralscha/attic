package ch.sr.lotto.update;

import java.io.*;
import java.util.*;

public class FtpFile {
    
    public static final char ASCII  = 'A';
    public static final char BINARY = 'I';
    
    private String localFileName;
    private String remoteFileName;
    private String remoteDirectory;
    private char type;
    
    
    public static FtpFile[] createFtpFileArray(String localPath, String remotePath, char type) {		
		File file = new File(localPath);
		List tmpList = new ArrayList();
		
		if (file.isDirectory()) {
			File[] dirFiles = file.listFiles();
			for (int i = 0; i < dirFiles.length; i++) {
				if (dirFiles[i].isFile())
					tmpList.add(new FtpFile(dirFiles[i].getPath(), dirFiles[i].getName(), remotePath,  type));
			}
		} else {
			tmpList.add(new FtpFile(file.getPath(), file.getName(), remotePath, type));
		}

		return (FtpFile[])tmpList.toArray(new FtpFile[0]);
    }
    
    
    public FtpFile() {
    	this(null, null, null, BINARY);
    }
    
    public FtpFile(String localFileName, String remoteFileName, String remoteDir, char type) {
        this.localFileName = localFileName;
        this.remoteFileName = remoteFileName;
        this.remoteDirectory = remoteDir;
        this.type = type;
    }
    
    public String getLocalFileName() {
        return localFileName;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }
    
    public String getRemoteDirectory() {
        return remoteDirectory;
    }
    
    public char getType() {
        return type;
    }
    
    public void setLocalFileName(String localFileName) {
    	this.localFileName = localFileName;
    }

    public void setRemoteFileName(String remoteFileName) {
    	this.remoteFileName = remoteFileName;
    }
    
    public void setRemoteDirectory(String remoteDirectory) {
    	this.remoteDirectory = remoteDirectory;
    }
    
	public void setType(char type) {
		this.type = type;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(localFileName);
		sb.append(", ");
		sb.append(remoteFileName);
		sb.append(", ");
		sb.append(remoteDirectory);
		sb.append(", ");
		sb.append(type);
		return sb.toString();
	}
}