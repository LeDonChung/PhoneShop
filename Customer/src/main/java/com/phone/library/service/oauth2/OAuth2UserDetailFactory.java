package com.phone.library.service.oauth2;

import com.phone.library.constants.Provider;

import java.util.Map;

public class OAuth2UserDetailFactory {
    public static OAuth2UserDetails getOAuth2UserDetail(String registrationId, Map<String, Object> attributes) throws Exception {
        if (registrationId.equals(Provider.google.name())) {
            return new OAuth2GoogleUser(attributes);
        } else {
            throw new Exception(String.format("Sorry! Login with %s is not supported", registrationId));
        }
    }
}
