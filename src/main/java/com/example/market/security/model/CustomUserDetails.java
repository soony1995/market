package com.example.market.security.model;

import com.example.market.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.example.market.type.Constant.COMMA_SEPARATOR;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean enables = true;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Member member) {
        this.username = member.getEmail();
        this.password = member.getPassword();
        this.authorities =
                Arrays.stream(member.getRoles().split(COMMA_SEPARATOR))
                        .map(role -> new SimpleGrantedAuthority(username))
                        .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enables;
    }
}
