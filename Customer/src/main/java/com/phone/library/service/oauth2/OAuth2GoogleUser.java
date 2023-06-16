package com.phone.library.service.oauth2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class OAuth2GoogleUser extends OAuth2UserDetails{

    public OAuth2GoogleUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
    public String getGivenName() {
        return (String) attributes.get("given_name");
    }
    public String getFamilyName() {
        return (String) attributes.get("family_name");
    }

}
