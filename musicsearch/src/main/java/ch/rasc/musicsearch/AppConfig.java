package ch.rasc.musicsearch;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

	private String musicDir;

	private String indexDir;

	private String nginxSendFileContext;

	private boolean apacheSendFile = false;

	public String getMusicDir() {
		return this.musicDir;
	}

	public void setMusicDir(String musicDir) {
		this.musicDir = musicDir;
	}

	public String getIndexDir() {
		return this.indexDir;
	}

	public void setIndexDir(String indexDir) {
		this.indexDir = indexDir;
	}

	public String getNginxSendFileContext() {
		return this.nginxSendFileContext;
	}

	public void setNginxSendFileContext(String nginxSendFileContext) {
		this.nginxSendFileContext = nginxSendFileContext;
	}

	public boolean isApacheSendFile() {
		return this.apacheSendFile;
	}

	public void setApacheSendFile(boolean apacheSendFile) {
		this.apacheSendFile = apacheSendFile;
	}

}
