import java.io.*;
import java.net.*;

public class Mail
{
    public static void main(String[] args)
    {

    // declaration section:
    // smtpClient: our client socket
    // os: output stream
    // is: input stream

        Socket smtpSocket = null;
        DataOutputStream os = null;
        DataInputStream is = null;

    // Initialization section:
    // Try to open a socket on port 25
    // Try to open input and output streams

        try
        {
            smtpSocket = new Socket("mail.bluewin.ch", 25);
            os = new DataOutputStream(smtpSocket.getOutputStream());
            is = new DataInputStream(smtpSocket.getInputStream());
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don't know about host: hostname");
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to: hostname");
        }

    // If everything has been initialized then we want to write some data
    // to the socket we have opened a connection to on port 25

        if (smtpSocket != null && os != null && is != null)
        {
            try
            {

    // The capital string before each colon has a special meaning to SMTP
    // you may want to read the SMTP specification, RFC1822/3
            String responseLine;

                os.writeBytes("HELO bluewin.ch\n");
                responseLine = is.readLine();
                System.out.println(responseLine);
                os.writeBytes("MAIL FROM: ralph@bluewin.ch\n");
                responseLine = is.readLine();
                System.out.println(responseLine);
                os.writeBytes("RCPT To: Igor.Pezzini@ska.com\n");
                os.writeBytes("DATA\n");
                os.writeBytes("From: ralph@bluewin.ch\n");
                os.writeBytes("Subject: Gelduebergabe\n");
                os.writeBytes("Hallo\n"); // message body
                os.writeBytes("Wenn du dieses E-Mail bekommst,\n");
                os.writeBytes("dann hat die Gelduebergabe\n");
                os.writeBytes("geklappt\n");
                os.writeBytes("Gruss Al\n");
                os.writeBytes("\n.\n");
                os.writeBytes("QUIT\n");

// keep on reading from/to the socket till we receive the "Ok" from SMTP,
// once we received that then we want to break.


                while ((responseLine = is.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("Ok") != -1) {
                      break;
                    }
                }

// clean up:
// close the output stream
// close the input stream
// close the socket

                os.close();
                is.close();
                smtpSocket.close();
            } catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }
}