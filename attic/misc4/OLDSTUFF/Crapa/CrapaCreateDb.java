
import java.sql.*;
import common.util.*;

class CrapaCreateDb {

	public static void main (String[] args) {
		try {
			CrapaDbManager.createCrapaTable();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	} 

} 