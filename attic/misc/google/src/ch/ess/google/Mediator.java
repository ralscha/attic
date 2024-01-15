
package ch.ess.google;

import java.io.*;
import java.net.*;

import javax.swing.*;

import ch.ess.google.wsdl.*;
import ch.ess.util.*;

public class Mediator {

  private JTable resultTable;  
  private JLabel status;
  private Blip blip;
  private JButton cancelButton;
  private JButton startButton;
  private String queryString;
  private GoogleSearchPort searchService;
  private SearchResultTableModel tableModel;
  private JEditorPane htmlPane;
  private URL url;

  public void start() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    blip.start();
  }
  
  public void stop() {
    blip.stop();
    cancelButton.setEnabled(false);
    startButton.setEnabled(true);  
  }
  
  public void setStatus(String str) {
    status.setText(str);
  }
  
  
  public void setResultTable(JTable resultTable) {
    this.resultTable = resultTable; 
  }

  public void setStatus(JLabel status) {
    this.status = status; 
  }

  public void setBlip(Blip blip) {
    this.blip = blip; 
  }

  public void setCancelButton(JButton cancelButton) {
    this.cancelButton = cancelButton; 
  }

  public void setStartButton(JButton startButton) {
    this.startButton = startButton; 
  }
  public JTable getResultTable() {
    return (this.resultTable); 
  }

  public JLabel getStatus() {
    return (this.status); 
  }

  public Blip getBlip() {
    return (this.blip); 
  }

  public JButton getCancelButton() {
    return (this.cancelButton); 
  }

  public JButton getStartButton() {
    return (this.startButton); 
  }

  
  public void setQueryString(String queryString) {
    this.queryString = queryString; 
  }
  public String getQueryString() {
    return (this.queryString); 
  }

  
  public void setSearchService(GoogleSearchPort searchService) {
    this.searchService = searchService; 
  }
  public GoogleSearchPort getSearchService() {
    return (this.searchService); 
  }

  
  public void setTableModel(SearchResultTableModel tableModel) {
    this.tableModel = tableModel; 
  }
  public SearchResultTableModel getTableModel() {
    return (this.tableModel); 
  }

  
  public void setHtmlPane(JEditorPane htmlPane) {
    this.htmlPane = htmlPane; 
  }

  public void setUrl(URL url) {
    this.url = url; 
  }
  public JEditorPane getHtmlPane() {
    return (this.htmlPane); 
  }

  public URL getUrl() {
    return (this.url); 
  }
  
  public void loadPage() {
    try {
      htmlPane.setPage(url);
    } catch (IOException e) {
      System.err.println(e);
    }
  }
  
  

}