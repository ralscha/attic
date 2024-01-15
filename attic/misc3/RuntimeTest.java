import java.util.*; 
import java.io.*; 

public class RuntimeTest 
{ 
    public static void main(String args[]) 
    { 
        try 
        { 
            Runtime rt = Runtime.getRuntime(); 

            String[] cmd = new String[3]; 
    
            cmd[0] = "cmd.exe" ; 
            cmd[1] = "/C" ; 
            cmd[2] = "avnt.exe -z -q eicar.zip"; 
            
            Process proc = rt.exec("d:/download/avnt/disk_1/avnt.exe -z -q d:/download/avnt/disk_1/eicar2.zip"); 

            StreamGobbler errorGobbler = new 
                StreamGobbler(proc.getErrorStream(), "ERROR"); 

            StreamGobbler outputGobbler = new 
                StreamGobbler(proc.getInputStream(), "OUTPUT"); 

            // kick them off 
            errorGobbler.start(); 
            outputGobbler.start(); 
            
            
            int exitVal = proc.waitFor(); 
            System.out.println("Process exitValue: " + exitVal); 
        } catch (Throwable t) 
          { 
            t.printStackTrace(); 
          } 
    } 
} 


class StreamGobbler extends Thread 
{ 
    InputStream is; 
    String type; 

    StreamGobbler(InputStream is, String type) 
    { 
        this.is = is; 
        this.type = type; 
    } 

    public void run() 
    { 
        try 
        { 
            InputStreamReader isr = new InputStreamReader(is); 
            BufferedReader br = new BufferedReader(isr); 
            String line=null; 
            while ( (line = br.readLine()) != null) 
                System.out.println(type + ">" + line); 
                
                System.out.println("FINITO");
            } catch (IOException ioe) 
              { 
                ioe.printStackTrace(); 
              } 
    } 
} 


