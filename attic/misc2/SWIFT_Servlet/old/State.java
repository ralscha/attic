import java.util.*;

public class State implements java.io.Serializable {
    private String procCenter;
    private String orderNumber;
    private int gtfNumber;
    private String stateNum;
    private String stateTran;
    private String messageType;
	private int stateDateY, stateDateM, stateDateD, stateDateHH, stateDateMM;

    public State() {
        procCenter = null;
        orderNumber = null;
        gtfNumber = 0;
        stateNum = null;
        stateTran = null;
        messageType = null;
        stateDateY = 0;
        stateDateM = 0;
        stateDateD = 0;
        stateDateHH = 0;
        stateDateMM = 0;
    }

    public State(String procCenter, String orderNumber, int gtfNumber, String stateNum,
                 String stateTran, String messageType, Calendar stateDate) {
        this.procCenter  = procCenter;
        this.orderNumber = orderNumber;
        this.gtfNumber   = gtfNumber;
        this.stateNum    = stateNum;
        this.stateTran   = stateTran;
        this.messageType = messageType;
        
   		this.stateDateY  = stateDate.get(Calendar.YEAR);
		this.stateDateM  = stateDate.get(Calendar.MONTH);
		this.stateDateD  = stateDate.get(Calendar.DATE);
		this.stateDateHH = stateDate.get(Calendar.HOUR_OF_DAY);
		this.stateDateMM = stateDate.get(Calendar.MINUTE);

    }

    public void setProcCenter(String procCenter) {
        this.procCenter = procCenter;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setGtfNumber(int gtfNumber) {
        this.gtfNumber = gtfNumber;
    }

    public void setStateNum(String stateNum) {
        this.stateNum = stateNum;
    }

    public void setStateTran(String stateTran) {
        this.stateTran = stateTran;
    }


    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    
    public void setStateDate(Calendar stateDate) {
   		this.stateDateY  = stateDate.get(Calendar.YEAR);
		this.stateDateM  = stateDate.get(Calendar.MONTH);
		this.stateDateD  = stateDate.get(Calendar.DATE);
		this.stateDateHH = stateDate.get(Calendar.HOUR_OF_DAY);
		this.stateDateMM = stateDate.get(Calendar.MINUTE);
    }

    public String getProcCenter() {
        return(procCenter);
    }

    public int hashCode() {
        return(orderNumber.hashCode());
    }

    public String getOrderNumber() {
        return(orderNumber);
    }
    
    public int getGtfNumber() {
        return(gtfNumber);
    }

    public String getStateNum() {
        return(stateNum);
    }
    
    public String getStateTran() {
        return(stateTran);
    }

    public String getMessageType() {
        return(messageType);
    }
            
    public Calendar getStateDate() {
		return (new GregorianCalendar(stateDateY, stateDateM, stateDateD,
		                              stateDateHH, stateDateMM, 0));
	}
    

	public String toString()
	{
		return "#State("+procCenter+" "+orderNumber+" "+gtfNumber+" "+stateNum+" "+stateTran+
		       " "+messageType+" "+stateDateY+"."+stateDateM+"."+stateDateD+" "+stateDateHH+":"+
		       stateDateMM+")";

	}
}
