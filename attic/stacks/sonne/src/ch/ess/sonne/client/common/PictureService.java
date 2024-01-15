package ch.ess.sonne.client.common;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

public interface PictureService extends RemoteService {
  
  /**
   * @gwt.typeArgs <ch.ess.sonne.client.common.DateItem>
   */
  List getDateItemList() ;
  
}
