<%! 

 private String showKind(String kindID) throws Exception {
  SQLManager manager = SQLManager.getInstance();
  Connection conn = manager.requestConnection();
  
  DateKindTable dkTable = new DateKindTable(conn);
    
  try {
    Iterator it = dkTable.select("KindID = '" + kindID + "'");
	  if (it.hasNext()) {
  		DateKind dk = (DateKind)it.next();
		return dk.getDescription();
	  } else {
  		return "";
	  }  
  } finally {
  	manager.returnConnection(conn);
  }
 }
 
 private String formatDate(java.util.Date adate) {
	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	return formatter.format(adate);
	
 }
 
 private String formatDbDate(java.util.Date adate) {
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	return formatter.format(adate);
	
 }
 

 private String formatTime(java.util.Date adate) {
 	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
	return formatter.format(adate);	
 }
 
 
 
%>
