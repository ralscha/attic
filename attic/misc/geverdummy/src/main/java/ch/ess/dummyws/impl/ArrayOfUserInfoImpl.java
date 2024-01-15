package ch.ess.dummyws.impl;

import java.util.List;
import com.trivadis.ArrayOfUserInfo;
import com.trivadis.UserInfo;


public class ArrayOfUserInfoImpl extends ArrayOfUserInfo {

  public ArrayOfUserInfoImpl(List<UserInfo> userInfo) {
    this.userInfo = userInfo;
  }

}
