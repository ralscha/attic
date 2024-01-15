import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class BitTestBean {
  private List idList;
  private BitSet distribution;
  

  
  public BitSet getDistribution() {
    if (distribution == null) {
      distribution = new BitSet(24);
    }
    return distribution;
  }

  public List getIdList() {
    if (idList == null) {
      idList = new ArrayList();
    }
    return idList;
  }

  public void setDistribution(BitSet set) {
    distribution = set;
  }

  public void setIdList(List list) {
    idList = list;
  }


  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
