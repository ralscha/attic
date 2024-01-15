package ch.ess.google;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;

import ch.ess.google.wsdl.GoogleSearchPort;

public class Mediator {

  private Label statusLabel;
  private ProgressBar progress;
  private Button searchButton;
  private Button cancelButton;
  private String searchText;
  private Table table;
  private GoogleSearchPort searchService;
  private SearchWorker worker;

  public void setStatusLabel(Label statusLabel) {
    this.statusLabel = statusLabel;
  }

  public void setProgress(ProgressBar progress) {
    this.progress = progress;
  }

  public void setCancelButton(Button button) {
    cancelButton = button;
  }

  public void setSearchButton(Button button) {
    searchButton = button;
  }

  public void setSearchText(String text) {
    searchText = text;
  }

  public String getSearchText() {
    return searchText;
  }

  public void setTable(Table table) {
    this.table = table;
  }

  public GoogleSearchPort getSearchService() {
    return searchService;
  }

  public void setSearchService(GoogleSearchPort port) {
    searchService = port;
  }

  public Table getTable() {
    return table;
  }

  public void start() {

    if ((searchText != null) && (!searchText.trim().equals(""))) {

      worker = new SearchWorker(this);
      worker.start();

      setStatus("Searching...");

      searchButton.setEnabled(false);
      cancelButton.setEnabled(true);
      progress.setVisible(true);

    }

  }

  public void setStatus(String status) {
    statusLabel.setText(status);
    statusLabel.pack();
  }

  public void stop() {
    if (worker != null) {
      worker.interrupt();
      worker = null;
    }

    setStatus("Ready");
    searchButton.setEnabled(true);
    cancelButton.setEnabled(false);
    progress.setVisible(false);
  }


}
