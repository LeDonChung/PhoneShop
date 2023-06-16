package com.phone.library.service.oauth2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class OAuth2UserDetails {

    protected Map<String, Object> attributes;

    public abstract String getName();

    public abstract String getEmail();
}
