package com.example.majorLink.global.oauth2;

import com.example.majorLink.domain.Social;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final OAuth2User delegate;
    private final Social socialInfo;
    private final OAuthAttributes oAuthAttributes;
    private final String jwtToken;

    @Override
    public Map<String, Object> getAttributes() {
        // Return the attributes directly if provided, otherwise return delegate's attributes
        return delegate.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegate.getAuthorities();
    }

    @Override
    public String getName() {
        // Here, the name could be set from socialInfo or attributes
        return delegate.getName();
    }

}
