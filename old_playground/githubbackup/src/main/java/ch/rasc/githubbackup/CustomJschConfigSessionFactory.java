package ch.rasc.githubbackup;

import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class CustomJschConfigSessionFactory extends JschConfigSessionFactory {

	private final String key;

	private final String knownHosts;

	public CustomJschConfigSessionFactory(String key, String knownHosts) {
		super();
		this.key = key;
		this.knownHosts = knownHosts;
	}

	@Override
	protected void configure(OpenSshConfig.Host host, Session session) {
		// session.setConfig("StrictHostKeyChecking", "yes");
	}

	@Override
	protected JSch getJSch(Host hc, FS fs) throws JSchException {
		JSch jsch = super.getJSch(hc, fs);
		jsch.addIdentity(this.key);
		jsch.setKnownHosts(this.knownHosts);
		return jsch;
	}

}