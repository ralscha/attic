package ch.ess.calendar.session;

import java.util.*;
import java.text.*;
import java.sql.*;
import ch.ess.calendar.db.*;
import ch.ess.calendar.*;
import com.holub.asynch.*;

public class AppointmentsCache {

	public final static int APPOINTMENTS_ALL = 0;
	public final static int APPOINTMENTS_ALLDAY = 1;
	public final static int APPOINTMENTS_TIMED = 2;
  public final static int APPOINTMENTS_NOT_REPEATING = 3;
  public final static int APPOINTMENTS_REPEATING = 4;
	
  private CategoriesMap categoriesMap;
	private Map userMap;
	private Map appointmentsMap;

  private Reader_writer lock = new Reader_writer();

	public AppointmentsCache() {
		userMap = null;
		appointmentsMap = null;
    categoriesMap = null;
	}

	public List getUserAppointments(String userid, Calendar cal, String loginuserid, int mode) throws InterruptedException {
	
		lock.request_read();
    try {
	
			if (userMap == null) return null;
			
			List allAppList = (List)userMap.get(userid);
			if (allAppList != null) {
				//Durchsuchen
				List dayAppList = new ArrayList();
				
				Iterator it = allAppList.iterator();
				while(it.hasNext()) {
					Appointments app = (Appointments)it.next();

					if (userid.equals(loginuserid)) {
						if ((cal == null) || (app.inRange(cal))) {
							if ((mode == APPOINTMENTS_ALLDAY) && (app.isAlldayevent()))
								dayAppList.add(app);
							else if ((mode == APPOINTMENTS_TIMED) && (!app.isAlldayevent()))	
								dayAppList.add(app);
							else if ((mode == APPOINTMENTS_NOT_REPEATING) && (!app.hasRepeaters()))	
								dayAppList.add(app);
							else if ((mode == APPOINTMENTS_REPEATING) && (app.hasRepeaters()))	
								dayAppList.add(app);
							else if (mode == APPOINTMENTS_ALL)
								dayAppList.add(app);	
						}
					} else {
						if ( ((cal == null) || app.inRange(cal)) && !app.isPrivate()) {
              Categories cat = categoriesMap.getCategory(app.getCategoryid());
						  if (cat.isLimited()) {
						    app.setSubject(cat.getDescription());
						    app.setBody(null);
						  }
							if ((mode == APPOINTMENTS_ALLDAY) && (app.isAlldayevent()))
								dayAppList.add(app);
							else if ((mode == APPOINTMENTS_TIMED) && (!app.isAlldayevent()))	
								dayAppList.add(app);
							else if ((mode == APPOINTMENTS_NOT_REPEATING) && (!app.hasRepeaters()))	
								dayAppList.add(app);
							else if ((mode == APPOINTMENTS_REPEATING) && (app.hasRepeaters()))	
								dayAppList.add(app);
							else if (mode == APPOINTMENTS_ALL)
								dayAppList.add(app);	
						}					
					}
				}
				
				if (!dayAppList.isEmpty()) {
					return dayAppList;	
				} else {
					return null;
				}
			} else {
				return null;	
			}
    } finally {
      lock.read_accomplished();
		}
	}
	
	public Appointments getAppointment(int id) throws InterruptedException {
		lock.request_read();
		try {
			return (Appointments)appointmentsMap.get(new Integer(id));
		} finally {
			lock.read_accomplished();
		}

	}
	
	public List getUserAppointments(String userid, Calendar cal, String loginuserid) throws InterruptedException {
		return getUserAppointments(userid, cal, loginuserid, APPOINTMENTS_ALL);
	}	
	
	public List getUserAppointments(String userid, Calendar cal) throws InterruptedException {
		return getUserAppointments(userid, cal, null, APPOINTMENTS_ALL);
	}
	
	public List getUserAppointments(String userid, Calendar cal, int mode) throws InterruptedException {
		return getUserAppointments(userid, cal, null, mode);
	}

	public void init(CategoriesMap cm) throws SQLException, InterruptedException {
    this.categoriesMap = cm;
    
		lock.request_write();
    Connection conn = DriverManager.getConnection("jdbc:poolman");
	  try {
			UsersTable ut = new UsersTable();
			Iterator userit = ut.select(null, "firstname", conn);
			
			AppointmentsTable appTable = new AppointmentsTable();
			userMap = new HashMap();
			appointmentsMap = new HashMap();
			
			List groupAppointmentsList = new ArrayList();
			Iterator groupIt = appTable.select("userid = 'group'", "enddate", conn);
			while(groupIt.hasNext()) {
				Appointments app = (Appointments)groupIt.next();
				groupAppointmentsList.add(app);
				appointmentsMap.put(new Integer(app.getAppointmentid()), app);
			}
			
			while (userit.hasNext()) {
				Users user = (Users)userit.next();
				
				List appointmentList = new ArrayList();
				
				Iterator it = appTable.select("userid = '"+user.getUserid()+"'", "enddate", conn);			
				while (it.hasNext()) {
					Appointments app = (Appointments)it.next();
					appointmentList.add(app);
					appointmentsMap.put(new Integer(app.getAppointmentid()), app);
				}
				appointmentList.addAll(groupAppointmentsList);
				userMap.put(user.getUserid(), appointmentList);		
			}	
		} finally {
			lock.write_accomplished();
		}
	}


  public static Multi[] compute(List appList, CategoriesMap categoriesMap) {
    List amList = new ArrayList();
    List pmList = new ArrayList();
    Multi[] multi = new Multi[4];

    for (int i = 0; i < appList.size(); i++) {
      Appointments app = (Appointments)appList.get(i);
		  String startTime = app.getFormatedStartTime();
      String endTime   = app.getFormatedEndTime();

      boolean start = ( (startTime.compareTo("00:00") >= 0) && (startTime.compareTo("11:59") <= 0) );
      boolean end = ( (endTime.compareTo("00:00") >= 0) && (endTime.compareTo("12:00") <= 0) );
		
		  if (start && end)
        amList.add(app);
      else if (!start && !end)
        pmList.add(app);
      else {
        pmList.add(app);
        amList.add(app);
      }
    }

    if (amList.size() == 0) {
      Multi m = new Multi();
      m.gif = "images/small/FFFFFF.gif";
      m.description = "";
      multi[0] = m;
      multi[1] = m;
    } else if ((amList.size() == 1) || (amList.size() == 2)) {
      for (int i = 0; i < amList.size(); i++) {
        Appointments app = (Appointments)amList.get(i);
        Multi m = new Multi();
        m.gif = "images/small/" + categoriesMap.getColor(app.getCategoryid()) + ".gif";
        m.description = app.getFormatedStartTime() + "-"+ app.getFormatedEndTime() + " " + app.getSubject();
        multi[i] = m;
      }
      if (amList.size() == 1) {
        Multi m = new Multi();
        m.gif = "images/small/FFFFFF.gif";
        m.description = "";
        multi[1] = m;
      }
    } else {
      String descriptiontext = "";
      for (int i = 0; i < amList.size(); i++) {
        Appointments app = (Appointments)amList.get(i);
        descriptiontext += app.getFormatedStartTime() + "-"+ app.getFormatedEndTime() + " " + app.getSubject();

        if (i+1 < amList.size()) {
          descriptiontext += "; ";
        }
      }

      Multi m = new Multi();
      m.gif = "images/small/000000.gif";
      m.description = descriptiontext;
      multi[0] = m;
      multi[1] = m;
    }

    if (pmList.size() == 0) {
      Multi m = new Multi();
      m.gif = "images/small/FFFFFF.gif";
      m.description = "";
      multi[2] = m;
      multi[3] = m;    
    } else if ((pmList.size() == 1) || (pmList.size() == 2)) {
       for (int i = 0; i < pmList.size(); i++) {
        Appointments app = (Appointments)pmList.get(i);
        Multi m = new Multi();
        m.gif = "images/small/" + categoriesMap.getColor(app.getCategoryid()) + ".gif";
        m.description = app.getFormatedStartTime() + "-"+ app.getFormatedEndTime() + " " + app.getSubject();
        multi[i+2] = m;
      }   
      if (pmList.size() == 1) {
        Multi m = new Multi();
        m.gif = "images/small/FFFFFF.gif";
        m.description = "";
        multi[3] = m;
      }
    } else {
      String descriptiontext = "";
      for (int i = 0; i < pmList.size(); i++) {
        Appointments app = (Appointments)pmList.get(i);
        descriptiontext += app.getFormatedStartTime() + "-"+ app.getFormatedEndTime() + " " + app.getSubject();

        if (i+1 < pmList.size()) {
          descriptiontext += "; ";
        }
      }

      Multi m = new Multi();
      m.gif = "images/small/000000.gif";
      m.description = descriptiontext;
      multi[2] = m;
      multi[3] = m;
    }
    
    return multi;
  }

}