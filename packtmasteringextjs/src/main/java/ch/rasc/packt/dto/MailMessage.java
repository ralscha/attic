package ch.rasc.packt.dto;

import java.util.Date;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Model(value = "Packt.model.mail.MailMessage", readMethod = "mailService.read",
		updateMethod = "mailService.update")
public class MailMessage {

	private boolean importance;

	private String icon;

	private boolean attachment;

	private String from;

	private String subject;

	@ModelField(dateFormat = "c")
	private Date received;

	private boolean flag;

	private String folder;

	@JsonIgnore
	private String content;

	@ModelField(convert = "null")
	private int id;

	public boolean isImportance() {
		return importance;
	}

	public void setImportance(boolean importance) {
		this.importance = importance;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isAttachment() {
		return attachment;
	}

	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getReceived() {
		return received;
	}

	public void setReceived(Date received) {
		this.received = received;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
