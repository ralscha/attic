package ch.rasc.cartracker.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ch.rasc.cartracker.entity.Staff;
import ch.rasc.cartracker.entity.StaffUserRole;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

public class StaffUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final ImmutableSet<GrantedAuthority> authorities;

	private final String password;

	private final String username;

	private final boolean enabled;

	private final String firstName;

	private final String lastName;

	private final Long staffId;

	public StaffUserDetails(Staff staff) {
		this.staffId = staff.getId();
		this.password = staff.getPassword();
		this.username = staff.getUsername();
		this.enabled = staff.isActive();
		this.firstName = staff.getFirstName();
		this.lastName = staff.getLastName();

		Builder<GrantedAuthority> builder = ImmutableSet.builder();
		for (StaffUserRole role : staff.getStaffUserRoles()) {
			builder.add(new SimpleGrantedAuthority(role.getUserRole().getShortName()));
		}

		this.authorities = builder.build();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
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

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Long getStaffId() {
		return staffId;
	}

}
