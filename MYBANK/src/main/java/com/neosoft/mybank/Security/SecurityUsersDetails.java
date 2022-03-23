package com.neosoft.mybank.Security;

import com.neosoft.mybank.Model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUsersDetails implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String userName;
    private final String password;
    private boolean active;
    private final List<GrantedAuthority> authorities;
    
    public SecurityUsersDetails(Users users) {
        this.userName = users.getUserName();
        this.password = users.getPassword();
        this.active = users.isActive();
        this.authorities = Arrays.stream(users.getRoles().split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
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
        return active;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return userName;
    }

}
