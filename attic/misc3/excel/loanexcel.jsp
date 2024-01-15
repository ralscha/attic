<%@ page language="java" contentType="application/vnd.ms-excel"%>

<%
    // This simple Java code will load a workbook, enter data into the workbook,
    // then output the workbook to an HTML page.
    
    // Create a single spreadsheet object for the servlet. This spreadsheet is
    // used to do the calculation when a request comes in
	    com.f1j.swing.JBook workbook = new com.f1j.swing.JBook();
		
    String priceParam = request.getParameter("price");
    String downParam = request.getParameter("down");
    String closingParam = request.getParameter("closing");
    String yearsParam = request.getParameter("years");
    String interestParam = request.getParameter("interest");

    workbook.getLock();

    try {
        if ((priceParam != null) && (downParam != null) && (closingParam != null) && (yearsParam != null)) {
            	/*
            	 * Read in the workbook mortgage.vts from the server. This
            	 * workbook must be in the directory from which the web server
            	 * was run for this to work.
            	 */
            	workbook.read("D:/JavaProjects/scrap/excel/mortgage.vts");
            
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
    		
			workbook.write(response.getOutputStream(), com.f1j.ss.Book.eFileExcel97 );
        }
  	}
	catch (java.io.IOException e) {
		out.println("Got exception e=" + e.getMessage());
	}
	
	catch (com.f1j.util.F1Exception e) {
		out.println("Got exception e=" + e.getMessage());
	} 
    finally {
		out.flush();
    // Some implementations of jhtml might require that you close the output stream.
		// writer.close();
    	workbook.releaseLock();
	}
	

%>



