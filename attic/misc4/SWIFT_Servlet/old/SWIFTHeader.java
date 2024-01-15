import java.util.*;
import COM.odi.util.*;

public class SWIFTHeader implements java.io.Serializable {

	private String tosn;	
	private int sendDateY, sendDateM, sendDateD;	
	private String addressSender;
	private String sessionNumber;
	private String sequenceNumber;
	private String addressReceiver;
	private String procCenter;
	private String messageType;
	private String duplicate;
	private String priority;
	private int receiveDateY, receiveDateM, receiveDateD, receiveDateHH, receiveDateMM;
	
    private OSVectorList message;

	public SWIFTHeader() {
		tosn = null;
		sendDateY = sendDateM = sendDateD = 0;
		addressSender = null;
		sessionNumber = null;
		sequenceNumber = null;
		addressReceiver = null;
		procCenter = null;
		messageType = null;
		duplicate = null;
		priority = null;
		receiveDateY = receiveDateM = receiveDateD = receiveDateHH = receiveDateMM = 0;
		message = null;
	}

	public SWIFTHeader(String tosn, Calendar sendDate, String addressSender, 
	                   String sessionNumber, String sequenceNumber, 
	                   String addressReceiver, String procCenter, 
	                   String messageType, String duplicate, 
	                   String priority, Calendar receiveDate, List message) {
                
		this.tosn = tosn;
		
		this.sendDateY = sendDate.get(Calendar.YEAR);
		this.sendDateM = sendDate.get(Calendar.MONTH);
		this.sendDateD = sendDate.get(Calendar.DATE);
		
		this.addressSender = addressSender;
		this.sessionNumber = sessionNumber;
		this.sequenceNumber = sequenceNumber;
		this.addressReceiver = addressReceiver;
		this.procCenter = procCenter;
		this.messageType = messageType;
		this.duplicate = duplicate;
		this.priority = priority;
		
		this.receiveDateY = receiveDate.get(Calendar.YEAR);
		this.receiveDateM = receiveDate.get(Calendar.MONTH);
		this.receiveDateD = receiveDate.get(Calendar.DATE);
		this.receiveDateHH = receiveDate.get(Calendar.HOUR_OF_DAY);
		this.receiveDateMM = receiveDate.get(Calendar.MINUTE);
		
	    this.message = new OSVectorList();
	    Iterator it = message.iterator();
	    while (it.hasNext()) 
	        this.message.add(it.next());
	}

    public int hashCode() {
        return tosn.hashCode();
    }
    
	public void setTOSN(String val) {
		tosn = val;
	}
	
	public String getTOSN() {
		return tosn;
	}

	public void setSendDate(Calendar val) {
		this.sendDateY = val.get(Calendar.YEAR);
		this.sendDateM = val.get(Calendar.MONTH);
		this.sendDateD = val.get(Calendar.DATE);
		
	}
	
	public Calendar getSendDate() {
	    return new GregorianCalendar(sendDateY, sendDateM, sendDateD);
	}

	public void setAddressSender(String val) {
		addressSender = val;
	}
	
	public String getAddressSender() {
		return addressSender;
	}

	public void setSessionNumber(String val) {
		sessionNumber = val;
	}
	
	public String getSessionNumber() {
		return sessionNumber;
	}

	public void setSequenceNumber(String val) {
		sequenceNumber = val;
	}
	
	public String getSequenceNumber() {
		return sequenceNumber;
	}

    public void setAddressReceiver(String val) {
		addressReceiver = val;
	}
	
	public String getAddressReceiver() {
		return addressReceiver;
	}

	public void setProcCenter(String val) {
		procCenter = val;
	}
	
	public String getProcCenter() {
    	return procCenter;
	}

	public void setMessageType(String val) {
		messageType = val;
	}
	
	public String getMessageType() {
		return messageType;
	}

	public void setDuplicate(String val) {
		duplicate = val;
	}
	
	public String getDuplicate() {
		return duplicate;
	}

	public void setPriority(String val) {
		priority = val;
	}
	
	public String getPriority() {
		return priority;
	}

	public void setReceiveDate(Calendar val) {
		this.receiveDateY  = val.get(Calendar.YEAR);
		this.receiveDateM  = val.get(Calendar.MONTH);
		this.receiveDateD  = val.get(Calendar.DATE);
		this.receiveDateHH = val.get(Calendar.HOUR_OF_DAY);
		this.receiveDateMM = val.get(Calendar.MINUTE);
		
	}
	
	public Calendar getReceiveDate() {
		return (new GregorianCalendar(receiveDateY, receiveDateM, receiveDateD,
		                              receiveDateHH, receiveDateMM, 0));
	}
	
	public void setMessage(List message) {
	    this.message = new OSVectorList();
	    Iterator it = message.iterator();
	    while (it.hasNext()) 
	        this.message.add(it.next());
	}
	
	public List getMessage() {
	    return message;
	}


	public String toString() {
		return "#SWIFTHeader("  + tosn + " " + sendDateY+"."+sendDateM+"."+sendDateD + " " + 
		                     addressSender + " " + sessionNumber + " " + sequenceNumber + " " + 
		                     addressReceiver + " " + procCenter + " " + messageType + " " + 
		                     duplicate + " " + priority + " " + receiveDateY+"."+receiveDateM+"."+
		                     receiveDateD+" "+receiveDateHH+":"+receiveDateMM+")";
	}
}
