package ch.ess.lbw.web.reports;

import java.util.Date;
import java.util.Map;

import org.jfree.data.general.DefaultPieDataset;

import de.laures.cewolf.DatasetProducer;

public class ZertifizierungenProducer implements DatasetProducer {

  private DefaultPieDataset pieDataset = new DefaultPieDataset();

  public void setValue(String label, int value) {
    pieDataset.setValue(label, value);
  }

  public Object produceDataset(Map params) {
    return pieDataset;
  }

  public boolean hasExpired(Map params, Date since) {
    return false;
  }

  public String getProducerId() {
    return "Zertifizierung";
  }

}
