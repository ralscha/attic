package gtf.common;

import java.util.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.*;
import common.util.*;

/**
 * Class declaration
 * @author Ralph Schaer
 * @version 1.0, 4.7.1999
 */
public class ServiceCenters {
	private Map centers;
	private String selectedServiceCenter = null;
	private EventListenerList listenerList = new EventListenerList();

	public ServiceCenters() {
   		centers = new TreeMap();
		String tmpStr = AppProperties.getStringProperty(gtf.common.Constants.P_SERVICE_CENTERS);

		java.util.StringTokenizer st = new java.util.StringTokenizer(tmpStr, ",");
		while (st.hasMoreTokens()) {
			try {
				ServiceCenter sc = new ServiceCenter(st.nextToken());
				centers.put(sc.getShortName(), sc);
			} catch (ServiceCenterNotFoundException scnfe) { 
				System.err.println(scnfe);
			}
  		}
   }

	public ServiceCenter getServiceCenter(String shortName) {
		return (ServiceCenter)centers.get(shortName);
	}
	
	public ServiceCenter getSelectedServiceCenter() {
		if (selectedServiceCenter != null)
			return getServiceCenter(selectedServiceCenter);
		else
			return null;
	}

	public boolean exists(String shortName) {
		if (centers != null) {
			return (centers.containsKey(shortName));
		} else {
			return false;
		}
	}

	public void cleanUp() {
		Iterator it = centers.values().iterator();
		while(it.hasNext()) {
			ServiceCenter sc = (ServiceCenter)it.next();
			try {
				sc.closeConnection();
			} catch (SQLException sqle) {
				System.err.println(sqle);
			}
		}		
	}
	
	public ServiceCenter[] getAllServiceCenters() {
		ServiceCenter[] temp = new ServiceCenter[centers.size()];
		temp = (ServiceCenter[])centers.values().toArray(temp);
		return temp;		
	}

	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	public void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}


	private void fireActionPerformed(ActionEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i] == ActionListener.class) {
				((ActionListener)listeners[i+1]).actionPerformed(event);
			}
		}
	}

	public JComboBox getComboBox() {
		Vector ss = new Vector();

		Iterator it = centers.keySet().iterator();
		while(it.hasNext()) {
			String tmp = (String)it.next();
			if (!tmp.equalsIgnoreCase("CSBS")) 
				ss.add(tmp);
		}
		
		JComboBox combo = new JComboBox(ss);
		selectedServiceCenter = (String)combo.getSelectedItem();

		combo.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent e) {
      				JComboBox cb = (JComboBox)e.getSource();
      				selectedServiceCenter = (String)cb.getSelectedItem();
      				ServiceCenter sc = (ServiceCenter)centers.get(selectedServiceCenter);
      				ActionEvent event = new ActionEvent(sc, ActionEvent.ACTION_PERFORMED, null);
      				fireActionPerformed(event);
      			}});
		return combo;
	}

}