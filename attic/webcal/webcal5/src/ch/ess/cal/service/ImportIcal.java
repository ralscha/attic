package ch.ess.cal.service;


public interface ImportIcal {
  ImportStatus importIcal(String username, String password, String ical);  
}
