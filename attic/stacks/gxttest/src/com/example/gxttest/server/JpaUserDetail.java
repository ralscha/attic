package com.example.gxttest.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.gxttest.entity.Role;
import com.example.gxttest.entity.User;

public class JpaUserDetail implements UserDetails {

  private List<GrantedAuthority> authorities;
  private String password;
  private String username;
  private boolean enabled;

  public JpaUserDetail(User user) {
    this.username = user.getUserName();
    this.password = user.getPassword();
    this.enabled = user.isEnabled();

    authorities = new ArrayList<GrantedAuthority>();
    Set<Role> roles = user.getRoles();
    for (Role role : roles) {
      authorities.add(new GrantedAuthorityImpl(role.getName()));
    }
  }

  @Override
  public List<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

}
