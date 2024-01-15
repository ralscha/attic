package ch.ess.calendar.tools;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.math.*;
import ch.ess.calendar.db.*;

public class UnLoadDatabase {
	private Connection con;
	

	public static void main(String[] args) {
	 //0 = UNLOAD/LOAD; 1 = DRIVER; 2 = DBURL

		try {
			if (args.length == 5) {
				UnLoadDatabase ct = new UnLoadDatabase();
				ct.openDb(args[1], args[2], args[3], args[4]);
				if ("UNLOAD".equalsIgnoreCase(args[0]))
					ct.unload();
				else if ("LOAD".equalsIgnoreCase(args[0]))
					ct.load();
				ct.shutDown();
			}
		} catch (Exception sqle) {
			System.err.println(sqle);
		}
	}

	void shutDown() {
		try {
			if (con != null) {
   				con.commit();
				con.close();
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
	}
	
	
	public void openDb(String driver, String dburl, String user, String pw) throws SQLException {		

		try {
			Class.forName(driver).newInstance();
		} catch (Exception e) {
			System.err.println(e);
		} 

		con = DriverManager.getConnection(dburl, user, pw);
	} 

	
	private void unload() throws Exception {
		
    List list = new ArrayList();
    ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("database.ser")));

    AppointmentsTable appTable = new AppointmentsTable();			
		Iterator it = appTable.select();
		while (it.hasNext()) {
		  	Appointments app = (Appointments)it.next();
        list.add(app);
		}
    System.out.println(list.size());
    oos.writeObject(list);

    list = new ArrayList();
    CategoriesTable catTable = new CategoriesTable();			
     it = catTable.select();
    while (it.hasNext()) {
      	Categories app = (Categories)it.next();
        list.add(app);
    }
    System.out.println(list.size());
    oos.writeObject(list);

    list = new ArrayList();
    RemindersTable remTable = new RemindersTable();			
     it = remTable.select();
    while (it.hasNext()) {
      	Reminders app = (Reminders)it.next();
        list.add(app);
    }
    System.out.println(list.size());
    oos.writeObject(list);

    list = new ArrayList();
    RepeatersTable repTable = new RepeatersTable();			
     it = repTable.select();
    while (it.hasNext()) {
      	Repeaters app = (Repeaters)it.next();
        list.add(app);
    }
    System.out.println(list.size());
    oos.writeObject(list);

    list = new ArrayList();
    UsersTable uTable = new UsersTable();			
     it = uTable.select();
    while (it.hasNext()) {
      	Users app = (Users)it.next();
        list.add(app);
    }
    System.out.println(list.size());
    oos.writeObject(list);

    oos.close();
	}

	
	private void load() throws Exception {
 
    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("database.ser")));

    List list = (ArrayList)ois.readObject();
    AppointmentsTable appTable = new AppointmentsTable();			
		Iterator it = list.iterator();
		while (it.hasNext()) {
        appTable.insert((Appointments)it.next());
		}

     list = (ArrayList)ois.readObject();
    CategoriesTable catTable = new CategoriesTable();			
     it = list.iterator();
    while (it.hasNext()) {
        catTable.insertFromUnload((Categories)it.next());
    }

     list = (ArrayList)ois.readObject();
    RemindersTable remTable = new RemindersTable();			
     it = list.iterator();
    while (it.hasNext()) {
      	remTable.insert((Reminders)it.next());
    }

     list = (ArrayList)ois.readObject();
    RepeatersTable repTable = new RepeatersTable();			
     it = list.iterator();
    while (it.hasNext()) {
      	repTable.insert((Repeaters)it.next());
    }

     list = (ArrayList)ois.readObject();
    UsersTable uTable = new UsersTable();			
     it = list.iterator();
    while (it.hasNext()) {
        uTable.insert((Users)it.next());
    }

    ois.close();
			

	}
		


}