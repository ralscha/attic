<%@ page import="ch.ess.pbroker.db.*,java.util.*,ch.ess.pbroker.session.*,java.sql.*,com.codestudio.util.*" info="PBroker Detail Search" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<% 

   try {
     List kandidaten = Db.getKandidatenTable().select();
	 Random rand = new Random();
	 for (int i = 0; i < kandidaten.size(); i++){
	 	Kandidaten kand = (Kandidaten)kandidaten.get(i);
		GregorianCalendar gc = new GregorianCalendar();

		gc.add(Calendar.YEAR, 0-(rand.nextInt(40)+20));
		gc.add(Calendar.DATE, 0-rand.nextInt(100));
		gc.add(Calendar.MONTH, 0-rand.nextInt(12));
		
		kand.setStdsatz(new java.math.BigDecimal(rand.nextInt(100)+100));
		kand.setGeburtsdatum(new java.sql.Timestamp(gc.getTime().getTime()));
		Db.getKandidatenTable().update(kand);
	 }
    } catch (SQLException sqle) {
      throw new ServletException(sqle); 
    } catch (PoolPropsException ppe) {
      throw new ServletException(ppe);
    }
%>

