package ch.ess.calendar.db;

import java.sql.*;
import java.util.*;
import java.text.*;


public class AppointmentsTable {

	private final static String deleteSQL = "DELETE FROM Appointments";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT appointmentid,userid,categoryid,startdate,enddate,body,location,subject,alldayevent,importance,private FROM Appointments";
	private final static String orderSQL  = " ORDER BY ";
  private final static String maxidSQL  = "SELECT max(appointmentid) as maxid from Appointments";
	private final static String insertSQL = 
		"INSERT INTO Appointments(appointmentid,userid,categoryid,startdate,enddate,body,location,subject,alldayevent,importance,private) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE Appointments SET userid=?, categoryid=?, startdate=?, enddate=?, body=?, location=?, subject=?, alldayevent=?, importance=?, private=? WHERE appointmentid=?";


  public int getMaxid() throws SQLException {

    Connection conn = DriverManager.getConnection("jdbc:poolman");
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(maxidSQL);

    if (rs.next()) {
      return rs.getInt("maxid") + 1;
    } else {
      return 0;
    }
  }

	public int delete() throws SQLException {
		return (delete((String)null));
	}

	public int delete(Appointments appointment) throws SQLException {
		return delete("appointmentid = "+appointment.getAppointmentid());
	}

	public int delete(Appointments appointment, Connection conn) throws SQLException {
		return delete("appointmentid = "+appointment.getAppointmentid(), conn);
	}


	public int delete(String whereClause) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {
      return delete(whereClause, conn);
    } finally {
      conn.close();
    }
  }

	public int delete(String whereClause, Connection conn) throws SQLException {
		RemindersTable rt = new RemindersTable();
		RepeatersTable rept = new RepeatersTable();
		rt.delete(whereClause, conn);
		rept.delete(whereClause, conn);

		Statement stmt = conn.createStatement();
		if (whereClause == null) {
			return stmt.executeUpdate(deleteSQL);
		} else {
			return stmt.executeUpdate(deleteSQL+whereSQL+whereClause);
		}		

	}
	
	public void deleteAllfromUser(String userid) throws SQLException {
		if (!ch.ess.calendar.util.CheckLicense.isDemo()) {

      Connection conn = DriverManager.getConnection("jdbc:poolman"); 
      try {
			  List appList = new ArrayList();
			  Iterator it = select("userid = '"+userid+"'", null, conn);
			  while (it.hasNext()) {
				  appList.add((Appointments)it.next());
			  }
		
			  it = appList.iterator();
			  while (it.hasNext()) {
				  Appointments app = (Appointments)it.next();
				  deleteRepeaters(app, conn);
				  deleteReminders(app, conn);	
				  delete(app, conn);
			  }		
      } finally {
        conn.close();
      }
		}	
	}
	
  //public void deleteAllUntil(String date) throws SQLException {
  //  deleteAllUntil(date, null);
  //}

	public void deleteAllUntil(String date, String userid) throws SQLException {
		java.util.Date untilDate;
		try {
			untilDate = ch.ess.calendar.util.Constants.dateFormat.parse(date);
		} catch (ParseException pe) {
			return;
		}
	
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {
		  List appList = new ArrayList();
      Iterator it = null;
      if (userid != null)
        it = select("userid = '"+userid+"'", null, conn);
		  else
		    it = select(null, null, conn);

		  while (it.hasNext()) {
			  appList.add((Appointments)it.next());
		  }
	
		  it = appList.iterator();
		  while (it.hasNext()) {
			  Appointments app = (Appointments)it.next();
		
			  List repeatersList = app.getRepeaters();
			  if ((repeatersList != null) && (!repeatersList.isEmpty())) {
				  Repeaters repeater = (Repeaters)repeatersList.get(0);
				  if (!repeater.isAlways()) {
					  if (repeater.getUntil().getTime() < untilDate.getTime()) {						
						  deleteRepeaters(app, conn);
						  deleteReminders(app, conn);	
						  delete(app, conn);
					  }	
				  }
			  } else {
				  // kein Repeater, enddate prüfen
				  if (app.getEnddate().getTime() < untilDate.getTime()) {					
					  deleteRepeaters(app, conn);
					  deleteReminders(app, conn);	
					  delete(app, conn);
				  }	
			
			  }

		  }
    } finally {
      conn.close();
    }
	}

	public Iterator select() throws SQLException {
		return select(null, null);
	}

	public Iterator select(String whereClause) throws SQLException {
		return select(whereClause, null);
	}

  public Iterator select(String whereClause, String orderClause) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    return select(whereClause, orderClause, conn);
  }

	public Iterator select(String whereClause, String orderClause, Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer(selectSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);
		if (orderClause != null)
			sb.append(orderSQL).append(orderClause);

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sb.toString());

    List tmpList = new ArrayList();
    while(rs.next()) {
      tmpList.add(makeObject(rs));
    }

    Iterator it = tmpList.iterator();
    RemindersTable rt = new RemindersTable();
    RepeatersTable rept = new RepeatersTable();
    
    while(it.hasNext()) {
      Appointments appointment = (Appointments)it.next();

      Iterator it2 = rt.select("appointmentid = " + appointment.getAppointmentid(), null, conn);
      while (it2.hasNext()) {
      	appointment.addReminder((Reminders)it2.next());
      }
        
      it2 = rept.select("appointmentid = " + appointment.getAppointmentid(), null, conn);
      while (it2.hasNext()) {
      	appointment.addRepeater((Repeaters)it2.next());
      }		      
    }

    return tmpList.iterator();

	}

	public int insert(Appointments appointments) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {
		  insertReminders(appointments, conn);
		  insertRepeaters(appointments, conn);
    
		  PreparedStatement insertPS = conn.prepareStatement(insertSQL);

		  prepareInsertStatement(insertPS, appointments);
		  return insertPS.executeUpdate();
    } finally {
      conn.close();
    }
	}

	public int update(Appointments appointments) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {
		  deleteReminders(appointments, conn);
		  insertReminders(appointments, conn);
	
		  deleteRepeaters(appointments, conn);
		  insertRepeaters(appointments, conn);
    
		  PreparedStatement updatePS = conn.prepareStatement(updateSQL);
		  prepareUpdateStatement(updatePS, appointments);
		  int rowCount = updatePS.executeUpdate();
		  if (rowCount <= 0) {
			  return insert(appointments);
		  } else {
			  return rowCount;
		  }
    } finally {
      conn.close();
    }
	}

	private void deleteReminders(Appointments appointment, Connection conn) throws SQLException {		
		RemindersTable rt = new RemindersTable();
		rt.delete("appointmentid = " + appointment.getAppointmentid(), conn);
	}

	private void deleteRepeaters(Appointments appointment, Connection conn) throws SQLException {		
		RepeatersTable rt = new RepeatersTable();
		rt.delete("appointmentid = " + appointment.getAppointmentid(), conn);
	}

	private void insertReminders(Appointments appointments, Connection conn) throws SQLException {
		List reminderList = appointments.getReminders();
		if ((reminderList != null) && !reminderList.isEmpty()) {

			RemindersTable rt = new RemindersTable();
			Iterator it = reminderList.iterator();
			while(it.hasNext()) {
				Reminders reminder = (Reminders)it.next();
				//set appointment id
				reminder.setAppointmentid(appointments.getAppointmentid());
				//insert reminder
				rt.insert(reminder, conn);
			}			
		}
	}

	private void insertRepeaters(Appointments appointments, Connection conn) throws SQLException {
		List repeaterList = appointments.getRepeaters();
		if ((repeaterList != null) && !repeaterList.isEmpty()) {

			RepeatersTable rt = new RepeatersTable();
			Iterator it = repeaterList.iterator();
			while(it.hasNext()) {
				Repeaters repeater = (Repeaters)it.next();
				//set appointment id
				repeater.setAppointmentid(appointments.getAppointmentid());
				//insert reminder
				rt.insert(repeater, conn);
			}			
		}
	}

	public Appointments makeObject(ResultSet rs) throws SQLException {
		return new Appointments(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getString(11));
	}

	private void prepareInsertStatement(PreparedStatement ps, Appointments appointments) throws SQLException {
		ps.setInt(1, appointments.getAppointmentid());
		ps.setString(2, appointments.getUserid());
		ps.setInt(3, appointments.getCategoryid());
		ps.setTimestamp(4, appointments.getStartdate());
		ps.setTimestamp(5, appointments.getEnddate());
		
		if (appointments.getBody() != null)
			ps.setString(6, appointments.getBody());
		else
			ps.setNull(6, java.sql.Types.CHAR);
				
		ps.setString(7, appointments.getLocation());
		ps.setString(8, appointments.getSubject());
		ps.setString(9, appointments.getAlldayevent());
		ps.setInt(10, appointments.getImportance());
		ps.setString(11, appointments.getPrivate());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Appointments appointments) throws SQLException {
		ps.setString(1, appointments.getUserid());
		ps.setInt(2, appointments.getCategoryid());
		ps.setTimestamp(3, appointments.getStartdate());
		ps.setTimestamp(4, appointments.getEnddate());

		if (appointments.getBody() != null)
			ps.setString(5, appointments.getBody());
		else
			ps.setNull(5, java.sql.Types.CHAR);
		
		ps.setString(6, appointments.getLocation());
		ps.setString(7, appointments.getSubject());
		ps.setString(8, appointments.getAlldayevent());
		ps.setInt(9, appointments.getImportance());
		ps.setString(10, appointments.getPrivate());
		ps.setInt(11, appointments.getAppointmentid());
	}
}
