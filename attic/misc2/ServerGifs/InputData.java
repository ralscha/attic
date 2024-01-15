
import java.util.*;

public class InputData {

	private int width;
	private int height;
	private double[] values;
  private boolean mode3d;
  private boolean mode3dset;
  private String type;

  public final static String BAR  = "bar";
  public final static String PIE  = "pie";
  public final static String LINE = "line";

  public InputData() {
    width = 300;
    height = 200;

    values = new double[] {100, 200, 300, 400, 500};
    mode3d = false;
    mode3dset = false;
    type = InputData.BAR;
  }

  public void reset() {
    mode3dset = false;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setMode3d(String[] flag) {
    if ((flag.length == 1) && ("1".equals(flag[0])))
      mode3d = true;
    else
      mode3d = false;

    mode3dset = true;    
  }

  public boolean is3D() {
    if (mode3dset)
      return mode3d;
    else
      return false;
  }

  
  public String getCommaSeparatedValues() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < values.length; i++) {
      sb.append(values[i]);
      if (i < values.length-1)
        sb.append(", ");
    }
    return sb.toString();
  }

  public void setCommaSeparatedValues(String str) {
    StringTokenizer st = new StringTokenizer(str, ",");
    List tmpList = new ArrayList();

    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (!token.trim().equals("")) {
        try {
          Double d = new Double(token);
          tmpList.add(d);
        } catch (NumberFormatException nfe) { }
      }
    }

    values = new double[tmpList.size()];
    for (int i = 0; i < tmpList.size(); i++) {
      values[i] = ((Double)tmpList.get(i)).doubleValue();
    }
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public double[] getValues() {
    return values;
  }

}