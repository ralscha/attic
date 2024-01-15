import java.io.*;
import java.net.*;
import java.util.Properties;

public final class HttpProxyServer extends Thread {

	private int localPort;
	private int remoteProxyPort;
	private boolean bRemoteProxy;
	private String remoteProxy;
	private ServerSocket ss;
	private boolean bStop;
	
	


	public HttpProxyServer() {
		localPort = -1;
		bStop = false;

	}

	public void run() {
		if (localPort < 0) return;			

		try {
			ss = new ServerSocket(localPort);
			ss.setSoTimeout(0x927c0);
		} catch (Exception exception) {
			System.err.println(exception);
			return;		}
		Object obj = null;

		System.out.println(getClass().getName() + " started at " + localPort);
		
		try {
			do {
				while (!bStop) {
					Socket socket;
					for (socket = null; !bStop && socket == null;)
						try {
							socket = ss.accept();
						} catch (InterruptedIOException _ex) { }

					if (!bStop) {
						Client client = new Client();
						if (client.set(socket)) {
							client.start();
						} else {
							try {
								socket.close();
							} catch (Exception _ex) { }
						}
					} else {
						try {
							socket.close();
						} catch (Exception _ex) { }
					}
				}

				try {
					Thread.sleep(3000L);
				} catch (Exception _ex) { }
			} while (true)
				;
		} catch (Exception exception1) {
			if (!bStop) {
				shutdown();
				
				System.err.println(exception1);
			}
		}
	}

	public void set(int i, boolean flag, String s, int j) throws Exception {
		localPort = i;
		remoteProxyPort = j;
		bRemoteProxy = flag;
		remoteProxy = s;
	}

	public void shutdown() {
		bStop = true;
		try {
			ss.close();
		} catch (Exception exception) {
			System.err.println(exception);
		}
	};

	public static void main(String[] args) {
		try {
			HttpProxyServer hps = new HttpProxyServer();
			hps.set(8080, true, "192.168.20.200", 8080);
			hps.start();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
	
	private class Client extends Thread {

	
		private Socket client;
		private Socket server;
		private InputStream from_client;
		private OutputStream to_client;
		private InputStream from_server;
		private OutputStream to_server;
		private String firstString;
		private String host;
		private int port;
		private byte client_buffer[];
		private int bytes_read_from_client;
		private int ifirst;
		private int ilast;
		private String buffer;



		public Client() {
			client = null;
			server = null;
			firstString = null;
			client_buffer = new byte[2048];
		
		}
		private void close() {
			try {
				to_client.close();
			} catch (Exception _ex) { }
			try {
				to_server.close();
			} catch (Exception _ex) { }
			try {
				from_client.close();
			} catch (Exception _ex) { }
			try {
				from_server.close();
			} catch (Exception _ex) { }
			try {
				client.close();
			} catch (Exception _ex) { }
			try {
				server.close();
			} catch (Exception _ex) { }
		}

		public void run() {
			boolean flag = true;
			try {
				if (bRemoteProxy)
					server = new Socket(remoteProxy, remoteProxyPort);
				else
					server = new Socket(host, port);
				from_server = server.getInputStream();
				to_server = server.getOutputStream();
			} catch (Exception exception) {
				close();
				System.err.println(exception);
				return;
			}
			Thread thread = new Thread() {

			public void run() {
				byte abyte1[] = new byte[2048];
				boolean flag2 = true;
				int k;
				try {
				
					k = from_server.read(abyte1);
					
					while (k != -1) {
						System.out.println(new String(abyte1));
								to_client.write(abyte1, 0, k);
								to_client.flush();
						k = from_server.read(abyte1);								
					}

				} catch (IOException _ex) { }
						close();
				}

			};
			
			if (flag) {
				thread.start();
				boolean flag1 = false;
				try {
					if (bRemoteProxy) {
						to_server.write(client_buffer, 0, bytes_read_from_client);
					} else {
						int i = firstString.length();
						byte abyte0[] = firstString.substring(0, ifirst + 1).toUpperCase().getBytes();

						to_server.write(abyte0);
						int j = firstString.indexOf(host) + host.length();
						if (firstString.charAt(j) != '/')
							for (; firstString.charAt(j) != '/' &&	firstString.charAt(j) != ' ' && j < i; j++)  ;

						abyte0 = firstString.substring(0, j).getBytes();
												
						j = abyte0.length;
						to_server.write(client_buffer, j, bytes_read_from_client - j);
					}
					to_server.flush();
					while ((bytes_read_from_client = from_client.read(client_buffer)) != -1) {
						to_server.write(client_buffer, 0, bytes_read_from_client);
						to_server.flush();
					}

				} catch (IOException _ex) { }
				close();
			}
		}

		public boolean set(Socket socket) {
			try {
				from_client = socket.getInputStream();
				to_client = socket.getOutputStream();
				client = socket;

				bytes_read_from_client = from_client.read(client_buffer);

				if (bytes_read_from_client < 0)
					return false;
				
				System.out.println(new String(client_buffer));

					
				buffer = (new String(client_buffer)).toLowerCase();
							
				ifirst = buffer.indexOf(" ");
				ilast = buffer.indexOf(" ", ifirst + 2);
				if (ilast < 0 || ifirst < 0)
					return false;
				firstString = buffer.substring(0, ilast);
				URL url = new URL(firstString.substring(ifirst));
				host = url.getHost();

				port = url.getPort();
				if (port < 0)
					port = 80;
				
				System.out.println(client.getInetAddress().getHostAddress() + ":" + firstString + ":host:" + host);
				return true;
			} catch (Exception exception) {
				System.err.println(exception);
			}
			firstString = null;
			return false;
		}

	}

	
}
