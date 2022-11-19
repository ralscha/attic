package ch.rasc.smninfo.job;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.rasc.smninfo.Application;
import ch.rasc.smninfo.config.AppProperties;
import ch.rasc.smninfo.xodus.ExodusManager;
import jetbrains.exodus.util.CompressBackupUtil;

@Component
public class Backup {

	private final ExodusManager exodusManager;

	private final AppProperties appProperties;

	public Backup(ExodusManager exodusManager, AppProperties appProperties) {
		this.exodusManager = exodusManager;
		this.appProperties = appProperties;
	}

	@Scheduled(cron = "0 31 3 * * SUN")
	public void backup() {
		try {
			Path backupFile = Paths.get(this.appProperties.getBackupFilename());
			Files.deleteIfExists(backupFile);
			CompressBackupUtil.backup(this.exodusManager.getPersistentEntityStore(),
					backupFile.toFile(), true);
		}
		catch (Exception e) {
			Application.logger.error("backup", e);
		}
	}

}
