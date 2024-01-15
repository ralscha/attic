
import org.gjt.tw.dbobjects.*;
import lottowin.db.*;
import java.util.*;
import java.io.*;

public class Test {

	public static void main(String[] args) {
    /*
    try {
		  Users users = new Users();
		  users.setName("Ralph");
		  users.setPassword("ralph");
		  users.setEmail("ralphschaer@yahoo.com");
		
		  Users savedUsers = (Users)new UsersDomain().save(users);
    } catch (Exception e) {
      System.err.println(e);
    }
    */

    try {
      Properties properties = new Properties();
      properties.load(new FileInputStream("d:/javaprojects/private/jrfsourcegen/dbobjects.properties"));
      
      
      DBManager.init(properties);
      Users.init();
      Users users = new Users();
      users.setName("name");
      users.setPassword("password");
      users.setEmail("email");
      users.store();
      users.print();

      DBManager.destroy();

    } catch (Exception f) {
      System.err.println(f);
    }

    
	}

}