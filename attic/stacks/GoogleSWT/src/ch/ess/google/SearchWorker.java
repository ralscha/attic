package ch.ess.google;

import java.rmi.RemoteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import EDU.oswego.cs.dl.util.concurrent.TimeoutException;
import ch.ess.google.wsdl.GoogleSearchResult;
import ch.ess.google.wsdl.ResultElement;

public class SearchWorker extends SWTWorker {

  private Mediator mediator;

  public SearchWorker(Mediator mediator) {    
    this.mediator = mediator;
  }

  protected Object construct() throws InterruptedException, RemoteException {

    GoogleSearchResult r =
      mediator.getSearchService().doGoogleSearch(
        Constants.KEY,
        mediator.getSearchText(),
        0,
        10,
        true,
        null,
        true,
        null,
        "latin1",
        "latin1");

    return r;

  }

  protected void finished() {

    try {
      GoogleSearchResult result = (GoogleSearchResult)get();
      mediator.stop();

      mediator.getTable().removeAll();

      if (result.getEstimatedTotalResultsCount() > 0) {
        ResultElement[] re = result.getResultElements();

        if (re != null) {
          for (int i = 0; i < re.length; i++) {
            TableItem ti = new TableItem(mediator.getTable(), SWT.NONE);
            ti.setText(0, re[i].getTitle());
            ti.setText(1, re[i].getURL());
          }
        }

        mediator.setStatus(
          result.getEstimatedTotalResultsCount() + " found      Time: " + result.getSearchTime());
      } else {
        mediator.setStatus("nothing found    Time: " + result.getSearchTime());
      }

      

    } catch (java.lang.reflect.InvocationTargetException e) {
      Throwable ex = e.getTargetException();
      if (ex instanceof TimeoutException) {
        mediator.stop();
        mediator.setStatus("Timed out");
      } else if (ex instanceof InterruptedException) {
        mediator.stop();
        mediator.setStatus("Cancelled...");
      } else {
        mediator.stop();
        ex.printStackTrace();
        mediator.setStatus("Exception: " + ex);
      }
    } catch (InterruptedException ex) {
      // event-dispatch thread won't be interrupted 
      throw new IllegalStateException(ex + "");
    }
  }

}