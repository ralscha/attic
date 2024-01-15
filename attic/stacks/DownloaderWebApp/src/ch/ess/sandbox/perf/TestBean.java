package ch.ess.sandbox.perf;

import java.io.Serializable;
import org.jboss.seam.annotations.Name;

@Name("testBean")
public class TestBean implements Serializable {

  public final static int ROWS = 100;
  public final static int COLS = 100;
  public final static int ITEMS = 100;

  private String data[][][] = new String[ROWS][COLS][ITEMS];

  public TestBean() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        for (int k = 0; k < ITEMS; k++) {
          data[i][j][k] = "[" + i + "," + j + "," + k + "]";
        }
      }
    }
  }

  public String[][][] getData() {
    return data;
  }

  public void setData(String[][][] data) {
    this.data = data;
  }

}
