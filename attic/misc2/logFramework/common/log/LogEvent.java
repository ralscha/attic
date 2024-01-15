//0.7
package common.log;

import java.util.*;


public class LogEvent implements java.io.Serializable {

	String hostname;
	String virtualMachineName;
	String type;
	Date time;
	long number;
	StackTrace position;
	String message;
	Object object;
	List threadNames;
	
	private static long nextNumber = 1;	
	
	public LogEvent(String type, String msg, StackTrace pos) {
		this(type, msg, pos, null);
	}
		
	public LogEvent(String type, String msg, StackTrace pos, Object obj) {
		this.type = type;
		time = new Date();
		number = nextNumber++;
		threadNames = createThreadNames();
		message = msg;
		position = pos;
		object = obj;	
	}
	
	public void setHostname(String hostname) { 
		this.hostname = hostname; 
	}
    
	public void setVirtualMachineName(String name) {
		this.virtualMachineName = name;
	}	
	
	public void setPosition(StackTrace position) {
		this.position = position;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getHostname() { 
		return hostname; 
	}
    
	public String getVirtualMachineName() { 
		return virtualMachineName; 
	}
	
	public String getType() {
		return type;
	}

	public Date getTime() {
		return time;
	}

	public long getNumber() {
		return number;
	}

	public StackTrace getPosition() {
		return position;
	}

	public String getMessage() {
		return message;
	}

	public Object getObject() {
		return object;
	}

	public boolean hasObject() {
		return (object != null);
	}

	protected void appendGroupName(List names, ThreadGroup group) {
		ThreadGroup parent = group.getParent();
		if (parent != null)
			appendGroupName(names, parent);
		names.add(group.getName());
	}

	protected List createThreadNames() {
		List names = new ArrayList();
		Thread current = Thread.currentThread();
		ThreadGroup parent = current.getThreadGroup();
		if (parent != null)
			appendGroupName(names, parent);
		names.add(current.getName());
		return names;
	}

}
    