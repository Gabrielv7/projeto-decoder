package com.ead.course.configuration.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonIgnoreProperties(value = {"password"})
public class UserDetailsImpl implements UserDetails {

    private UUID userId;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID userId, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(UUID userId, String rolesStr) {

        List<GrantedAuthority> authorities = Arrays.stream(rolesStr.split(","))
                .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

        return new UserDetailsImpl(userId, authorities);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
