package com.cooks.demo.security;

import com.cooks.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * handles authentication and authorization throughout the application
 */
public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private Integer userId;
    private String displayName;
    private List<GrantedAuthority> authorities;
    private String userRole;
    public MyUserDetails(User user){
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.userId = user.getId();
        this.displayName = user.getName();
        this.authorities = Arrays.stream(user.getUserType().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        System.out.println("granted authorities="+ this.authorities);
        this.userRole = user.getUserType();
    }
    public MyUserDetails(){
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
    public String getUsername() {
        return userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
    public String getUserRole(){
        return userRole;
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
        return true;
    }
}
