<%@ page language="java"%>
<HTML>
<HEAD>
<TITLE>Southcreek Realty Co. - Instant Amortization</TITLE>
</HEAD>

<BODY>

<BR> <BR> <BR>

<CENTER><IMG SRC="topbannr.gif" WIDTH=400 HEIGHT=45 BORDER=0 ALT="Southcreek Realty"></CENTER><BR> <BR>

<CENTER>
<font point-size=8>
<table><tr><td valign=top>

<%
    // This simple Java code will load a workbook, enter data into the workbook,
    // then output the workbook to an HTML page.
    
    // Create a single spreadsheet object for the servlet. This spreadsheet is
    // used to do the calculation when a request comes in.
    com.f1j.swing.JBook workbook = new com.f1j.swing.JBook();
    boolean needsReadWorkbook = true;
    com.f1j.ss.HTMLWriter htmlWriter = new com.f1j.ss.HTMLWriter();

    String priceParam = request.getParameter("price");
    String downParam = request.getParameter("down");
    String closingParam = request.getParameter("closing");
    String yearsParam = request.getParameter("years");
    String interestParam = request.getParameter("interest");

    workbook.getLock();
	java.io.Writer writer = new java.io.PrintWriter(new java.io.BufferedWriter(out));

    try {
        if ((priceParam != null) && (downParam != null) && (closingParam != null) && (yearsParam != null)) {
		   if (needsReadWorkbook) {
            	htmlWriter.setFlags(htmlWriter.ALIGN_TAG |
            	        htmlWriter.VALUE_FORMATS |
            	        htmlWriter.BORDER_TAG);
            	
            	/*
            	 * Read in the workbook mortgage.vts from the server. This
            	 * workbook must be in the directory from which the web server
            	 * was run for this to work.
            	 */
            	workbook.read("D:/JavaProjects/scrap/excel/mortgage.vts");
            	needsReadWorkbook = false;
            }
            
            com.f1j.ss.Sheet sheet = workbook.getBook().getSheet(0);
    		sheet.setEntry(1, 2, priceParam);
    		if (!downParam.endsWith("%"))
    		    downParam = downParam + "%";
    		sheet.setEntry(2, 1, downParam);
    		if (!closingParam.endsWith("%"))
    		    closingParam = closingParam + "%";
    		sheet.setEntry(4, 1, closingParam);
    		sheet.setEntry(6, 2, yearsParam);
    		sheet.setEntry(10, 3, interestParam);
    		workbook.recalc();
    		
    		//output for the first table. 
   			htmlWriter.write(workbook.getBook(), 0, 0, 0, 0, 19, 2, writer);
            writer.write("</td><td valign=top>");
            
            //output the amortization table
   			htmlWriter.write(workbook.getBook(), 0, 31, 7, 0, yearsParam.equals("15") ? 46 : 61, 11, writer);
        }
  	}
	catch (java.io.IOException e) {
		writer.write("Got exception e=" + e.getMessage());
	}
	
	catch (com.f1j.util.F1Exception e) {
		writer.write("Got exception e=" + e.getMessage());
	} 
    finally {
		writer.flush();
    // Some implementations of jhtml might require that you close the output stream.
		// writer.close();
    	workbook.releaseLock();
	}
	

%>
</td></tr></table>

</CENTER>

</BODY>
</HTML>


