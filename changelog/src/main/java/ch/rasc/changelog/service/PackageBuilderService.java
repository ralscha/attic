package ch.rasc.changelog.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc2.SvnCheckout;
import org.tmatesoft.svn.core.wc2.SvnCopySource;
import org.tmatesoft.svn.core.wc2.SvnList;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnRemoteCopy;
import org.tmatesoft.svn.core.wc2.SvnRemoteMkDir;
import org.tmatesoft.svn.core.wc2.SvnTarget;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.changelog.entity.InstallationType;
import ch.rasc.changelog.util.DeleteDirectory;

@Service
public class PackageBuilderService {

	@Autowired
	private Environment environment;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	private String buildingCustomer = "";

	private volatile boolean running = false;

	private List<String> currentOutput = Collections.emptyList();

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	public Map<String, Object> isBuilding() {
		return ImmutableMap.of("customer", this.buildingCustomer, "output",
				this.currentOutput);
	}

	@ExtDirectMethod
	@Async
	public void startBuild(String customer, boolean createSetupPackage,
			boolean internalBuild) throws InterruptedException {

		if (this.running) {
			return;
		}

		if (StringUtils.isBlank(customer)) {
			return;
		}

		this.running = true;

		this.currentOutput = new ArrayList<>();
		this.buildingCustomer = customer;

		try {
			String version = this.customerService
					.getNextVersionNumber(this.buildingCustomer);
			broadcast(ImmutableList.of(
					"START BUILD: " + this.buildingCustomer + ":" + version,
					"Preparing working directory"));
			Path workingDir = createWorkingDirectory();

			broadcast("Checking out source from subversion....");
			subversionCheckout(workingDir);
			broadcast("Project checked out");

			Path versionPath = workingDir.resolve("src/main/resources/version");
			Files.write(versionPath, version.getBytes(StandardCharsets.UTF_8));

			broadcast("Starting build...");
			int exitValue = build(this.environment.getProperty("build.command"));

			String standaloneCustomersString = this.environment
					.getProperty("build.standalone.customer");
			if (standaloneCustomersString == null) {
				standaloneCustomersString = StringUtils.EMPTY;
			}
			Set<String> standaloneCustomers = ImmutableSet
					.copyOf(Splitter.on(",").split(standaloneCustomersString));
			int exitValueStandaloneBuild = 0;
			boolean hasStandalone = false;
			if (standaloneCustomers.contains(this.buildingCustomer)) {
				hasStandalone = true;
				broadcast("Starting build standalone...");
				exitValueStandaloneBuild = build(
						this.environment.getProperty("build.command.standalone"));
			}

			Path jarPath = workingDir.resolve("target/ct.jar");
			if (exitValue == 0 && exitValueStandaloneBuild == 0
					&& Files.exists(jarPath)) {
				broadcast("Copying files to FTP Server");

				boolean success = sendFileToFtp(version, workingDir, createSetupPackage,
						hasStandalone);

				if (success) {
					broadcast("Tagging");
					tag(version);
					this.customerService.addCustomerBuild(this.buildingCustomer, version,
							internalBuild);

					copyIntoTest(workingDir);

					broadcast("SUCCESS SUCCESS SUCCESS SUCCESS SUCCESS SUCCESS");
				}
				else {
					broadcast("BUILD FAILED BUILD FAILED BUILD FAILED");
				}
			}
			else {
				broadcast(ImmutableList.of("BUILD FAILED BUILD FAILED BUILD FAILED",
						"exitValue: " + exitValue, jarPath.toString()));
			}
		}
		catch (IOException e) {
			LoggerFactory.getLogger(PackageBuilderService.class).error("build", e);
			broadcast(ImmutableList.of("BUILD FAILED BUILD FAILED BUILD FAILED",
					e.getMessage()));
		}
		catch (SVNException e) {
			LoggerFactory.getLogger(PackageBuilderService.class).error("build", e);
			broadcast(ImmutableList.of("BUILD FAILED BUILD FAILED BUILD FAILED",
					e.getMessage()));
		}
		finally {
			broadcast("THE_END");
			this.buildingCustomer = "";
			this.running = false;
		}

	}

	private void copyIntoTest(Path workingDir) {
		String testdirectory = this.environment.getProperty("build.testdirectory");
		Path destPath = Paths.get(testdirectory)
				.resolve("ct_" + this.buildingCustomer + ".war");
		Path sourcePath = workingDir
				.resolve("target/ct-" + getBaseVersionNumber() + ".war");

		try {
			Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			// just for fun do it again.
			try {
				Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (IOException e1) {
				broadcast(e1.getMessage());
			}
		}

	}

	private Path createWorkingDirectory() throws IOException {
		String workingDirectory = this.environment.getProperty("build.workingdirectory");
		Path workingDir = Paths.get(workingDirectory);
		if (Files.exists(workingDir)) {
			Files.walkFileTree(workingDir, new DeleteDirectory());
		}
		Files.createDirectories(workingDir);
		return workingDir;
	}

	private void subversionCheckout(Path workingDir) throws SVNException {
		String svnUser = this.environment.getProperty("build.svn.user");
		String svnPassword = this.environment.getProperty("build.svn.password");
		String svnHost = this.environment.getProperty("build.svn.host");
		String trunkUrl = this.environment.getProperty("build.svn.url") + "/trunk";

		final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
		svnOperationFactory.setAuthenticationManager(
				SVNWCUtil.createDefaultAuthenticationManager(svnUser, svnPassword));
		final SvnCheckout checkout = svnOperationFactory.createCheckout();

		checkout.setSingleTarget(SvnTarget.fromFile(workingDir.toFile()));

		SVNURL url = SVNURL.create("https", svnUser, svnHost, 443, trunkUrl, true);

		checkout.setSource(SvnTarget.fromURL(url));
		checkout.run();
	}

	private int build(String buildCommand) throws IOException, InterruptedException {
		List<String> commands = Splitter.on(";").splitToList(buildCommand);
		commands.add(this.buildingCustomer);
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.redirectErrorStream(true);
		Process p = pb.start();

		try (InputStreamReader inputStreamReader = new InputStreamReader(
				p.getInputStream());
				BufferedReader bis = new BufferedReader(inputStreamReader)) {
			String line;
			while ((line = bis.readLine()) != null) {
				broadcast(line);
			}
			p.waitFor();
		}
		return p.exitValue();
	}

	private boolean sendFileToFtp(String version, Path workingDir,
			boolean createSetupPackage, boolean hasStandalone) {

		String ftpHost = this.environment.getProperty("build.ftp.host");
		String ftpPassword = this.environment.getProperty("build.ftp.password");

		FTPClient ftp = new FTPClient();
		boolean error = false;
		try {
			int reply;
			ftp.connect(ftpHost);
			ftp.login(this.customerService.getFtpUserName(this.buildingCustomer),
					ftpPassword);

			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				broadcast("FTP server refused connection");
				return false;
			}

			ftp.makeDirectory(version);
			ftp.changeWorkingDirectory(version);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			if (createSetupPackage) {
				try (InputStream is = Files
						.newInputStream(workingDir.resolve("target/ct_setup.zip"))) {
					ftp.storeFile("ct_setup.zip", is);
				}
			}
			else {
				if (this.customerService
						.getInstallation(this.buildingCustomer) == InstallationType.JAR) {
					try (InputStream is = Files
							.newInputStream(workingDir.resolve("target/ct.jar"))) {
						ftp.storeFile("ct.jar", is);
					}
				}
				else {
					try (InputStream is = Files.newInputStream(workingDir
							.resolve("target/ct-" + getBaseVersionNumber() + ".war"))) {
						ftp.storeFile("ct.war", is);
					}
				}
			}

			if (hasStandalone) {
				try (InputStream is = Files
						.newInputStream(workingDir.resolve("target/ct_standalone.jar"))) {
					ftp.storeFile("ct_standalone.jar", is);
				}
			}

			ftp.logout();
		}
		catch (IOException e) {
			error = true;
			LoggerFactory.getLogger(PackageBuilderService.class).error("ftp", e);
			broadcast(e.getMessage());
		}
		finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				}
				catch (final IOException ioe) {
					// do nothing
				}
			}
		}

		if (error) {
			return false;
		}

		return true;

	}

	private String getBaseVersionNumber() {
		int nextMajor = Integer
				.valueOf(this.environment.getProperty("next.version.major"));
		int nextMinor = Integer
				.valueOf(this.environment.getProperty("next.version.minor"));
		return String.format("%s.%s.0", nextMajor, nextMinor);
	}

	private void tag(String buildVersion) throws SVNException {
		String svnUser = this.environment.getProperty("build.svn.user");
		String svnPassword = this.environment.getProperty("build.svn.password");
		String svnHost = this.environment.getProperty("build.svn.host");
		String svnUrl = this.environment.getProperty("build.svn.url");

		final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
		svnOperationFactory.setAuthenticationManager(
				SVNWCUtil.createDefaultAuthenticationManager(svnUser, svnPassword));

		final Set<String> objects = new HashSet<>();
		SvnList list = svnOperationFactory.createList();
		list.addTarget(SvnTarget.fromURL(
				SVNURL.create("https", svnUser, svnHost, 443, svnUrl + "/tags", true)));
		list.setReceiver((target, object) -> objects.add(object.getName()));
		list.run();

		if (!objects.contains(this.buildingCustomer)) {
			SvnRemoteMkDir mkDir = svnOperationFactory.createMkDir();
			mkDir.addTarget(SvnTarget.fromURL(SVNURL.create("https", svnUser, svnHost,
					443, svnUrl + "/tags/" + this.buildingCustomer, true)));
			mkDir.run();
		}

		SvnRemoteCopy copy = svnOperationFactory.createRemoteCopy();
		final SvnTarget trunk = SvnTarget.fromURL(
				SVNURL.create("https", svnUser, svnHost, 443, "/repos/ct/trunk", true));
		copy.addCopySource(SvnCopySource.create(trunk, SVNRevision.HEAD));
		copy.addTarget(SvnTarget.fromURL(SVNURL.create("https", svnUser, svnHost, 443,
				svnUrl + "/tags/" + this.buildingCustomer + "/" + buildVersion, true)));
		copy.run();
	}

	private void broadcast(String msg) {
		broadcast(Collections.singletonList(msg));
	}

	private void broadcast(List<String> msgs) {
		this.messagingTemplate.convertAndSend("/queue/build", msgs);
		this.currentOutput.addAll(msgs);
	}

}
