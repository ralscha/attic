package ch.ess.cal.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Attachment implements Serializable {

    /** identifier field */
    private Long attachmentId;

    /** persistent field */
    private String fileName;

    /** persistent field */
    private long fileSize;

    /** persistent field */
    private String contentType;

    /** nullable persistent field */
    private String description;

    /** persistent field */
    private ch.ess.cal.db.Event event;

    /** full constructor */
    public Attachment(java.lang.String fileName, long fileSize, java.lang.String contentType, java.lang.String description, ch.ess.cal.db.Event event) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.description = description;
        this.event = event;
    }

    /** default constructor */
    public Attachment() {
    }

    /** minimal constructor */
    public Attachment(java.lang.String fileName, long fileSize, java.lang.String contentType, ch.ess.cal.db.Event event) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.event = event;
    }

    public java.lang.Long getAttachmentId() {
        return this.attachmentId;
    }

    public void setAttachmentId(java.lang.Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public java.lang.String getFileName() {
        return this.fileName;
    }

    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public java.lang.String getContentType() {
        return this.contentType;
    }

    public void setContentType(java.lang.String contentType) {
        this.contentType = contentType;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public ch.ess.cal.db.Event getEvent() {
        return this.event;
    }

    public void setEvent(ch.ess.cal.db.Event event) {
        this.event = event;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("attachmentId", getAttachmentId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Attachment) ) return false;
        Attachment castOther = (Attachment) other;
        return new EqualsBuilder()
            .append(this.getAttachmentId(), castOther.getAttachmentId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAttachmentId())
            .toHashCode();
    }

}
