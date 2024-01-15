package ch.ess.map;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.remoting.WebRemote;

@Name("markerUpdater")
public class MarkerUpdater {

  @In
  Session hibernateSession;  
  
  @WebRemote
  @Transactional
  public Result update(String lng, String lat, String found, String left, String icon) {

    Marker marker = new Marker();
    marker.setFound(found);
    marker.setLeftStr(left);
    System.out.println(lng);
    System.out.println(lat);
    marker.setLng(new BigDecimal(lng));
    marker.setLat(new BigDecimal(lat));
    marker.setIcon(icon);

    hibernateSession.save(marker);
    
    String info = "<div><strong>found </strong>" + found;
    info += "</div><div><strong>left </strong>"+left+"</div>";
    
    Result r = new Result();
    r.info = info;
    r.lat = lat;
    r.lng = lng;
    return r;
  }
  
  @WebRemote
  @Transactional
  public List<Result> list() {
    List<Result> resultList = new ArrayList<Result>();
    
    List<Marker> markerList = hibernateSession.createCriteria(Marker.class).list();
    for (Marker marker : markerList) {
      Result r = new Result();
      String info = "<div><strong>found </strong>" + marker.getFound();
      info += "</div><div><strong>left </strong>"+marker.getLeftStr()+"</div>";
      r.info = info;
      r.lat = marker.getLat().toString();
      r.lng = marker.getLng().toString();
      r.icon = marker.getIcon();
      resultList.add(r);
    }    
    
    return resultList; 
    
  }
  

}
