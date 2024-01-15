package ch.ess.dummyws.impl;

import java.util.ArrayList;
import java.util.List;
import com.trivadis.ArrayOfMandator;
import com.trivadis.ArrayOfMandatorShort;
import com.trivadis.ArrayOfRole;
import com.trivadis.ArrayOfUser;
import com.trivadis.ArrayOfUserInfo;
import com.trivadis.LDAPWSV30Soap;
import com.trivadis.Role;
import com.trivadis.UserInfo;
import com.trivadis.VersionInfo;

public class LDAPWSV30SoapImpl implements LDAPWSV30Soap {

  @Override
  public ArrayOfUserInfo getUserInfo(String username) {
    UserInfo userInfo = new UserInfo();
    userInfo.setUid(username);
    userInfo.setGivenName(username);
    userInfo.setSureName(username);
    userInfo.setMail(username + "@ess.ch");
    userInfo.setLanguage("deCH");
    userInfo.setDn(username);

    List<UserInfo> userInfos = new ArrayList<UserInfo>();
    userInfos.add(userInfo);
    return new ArrayOfUserInfoImpl(userInfos);

  }

  @Override
  public ArrayOfRole getRoleMandator(String applicationname, String username, String mandatorname) {
    Role role = new Role();
    role.setAstraMandatorName(applicationname);
    role.setCn(username);
    role.setDn(mandatorname);

    List<Role> roles = new ArrayList<Role>();
    roles.add(role);
    return new ArrayOfRoleImpl(roles);

  }

  @Override
  public ArrayOfRole getRole(String applicationname, String username) {
    Role role = new Role();
    role.setAstraMandatorName(applicationname);
    role.setCn(username);
    role.setDn(username);

    List<Role> roles = new ArrayList<Role>();
    roles.add(role);
    return new ArrayOfRoleImpl(roles);
  }

  @Override
  public ArrayOfUser getUserForMandator(String applicationname, String mandatorname) {
    return null;
  }

  @Override
  public VersionInfo getAssemblyInfo() {
    return null;
  }

  @Override
  public ArrayOfMandatorShort getMandatorShort(String applicationname, String username) {
    return null;
  }

  @Override
  public ArrayOfMandator getMandator(String applicationname, String username) {
    return null;
  }

  @Override
  public ArrayOfUser getUserForRole(String applicationname, String rolename) {
    return null;
  }

}
