package ch.ess.cal.service;



public interface ExportIcal {
  String exportIcal(String username, String password, long start, long end);  
}
