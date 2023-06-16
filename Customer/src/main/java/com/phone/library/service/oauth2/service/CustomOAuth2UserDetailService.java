package com.phone.library.service.oauth2.service;

import com.phone.library.entity.CustomerEntity;
import com.phone.library.repository.CustomerRepository;
import com.phone.library.repository.RoleRepository;
import com.phone.library.service.oauth2.OAuth2GoogleUser;
import com.phone.library.service.oauth2.OAuth2UserDetailCustom;
import com.phone.library.service.oauth2.OAuth2UserDetailFactory;
import com.phone.library.service.oauth2.OAuth2UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserDetailService extends DefaultOAuth2UserService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return checkingOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }

    }

    private OAuth2User checkingOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) throws Exception {
        OAuth2UserDetails oAuth2UserDetails = OAuth2UserDetailFactory.getOAuth2UserDetail(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (ObjectUtils.isEmpty(oAuth2UserDetails)) {
            throw new Exception("Can not found user from properties");
        }
        Optional<CustomerEntity> user = customerRepository.findByUsernameAndProviderId(oAuth2UserDetails.getEmail(), userRequest.getClientRegistration().getRegistrationId());
        CustomerEntity oAuth2UserDetail = null;
        if (user.isPresent()) {
            oAuth2UserDetail = user.get();
            if (!oAuth2UserDetail.getProviderId().equals(userRequest.getClientRegistration().getRegistrationId())) {
                throw new Exception("Invalid site login with " + oAuth2UserDetail.getProviderId());
            }

            oAuth2UserDetail = updateOAuth2UserDetail(oAuth2UserDetail, oAuth2UserDetails);
        } else {
            oAuth2UserDetail = registerOAuth2Detail(userRequest, oAuth2UserDetails);
        }


        return new OAuth2UserDetailCustom(oAuth2UserDetail.getId(),
                oAuth2UserDetail.getUsername(),
                oAuth2UserDetail.getPassword(),
                oAuth2UserDetail.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()));
    }

    private CustomerEntity updateOAuth2UserDetail(CustomerEntity oAuth2UserDetail, OAuth2UserDetails oAuth2UserDetails) {
        oAuth2UserDetail.setUsername(oAuth2UserDetails.getEmail());
        return customerRepository.save(oAuth2UserDetail);
    }

    private CustomerEntity registerOAuth2Detail(OAuth2UserRequest userRequest, OAuth2UserDetails oAuth2UserDetails) {
        CustomerEntity user = new CustomerEntity();
        user.setUsername(oAuth2UserDetails.getEmail());
        user.setEmail(oAuth2UserDetails.getEmail());
        if(oAuth2UserDetails instanceof OAuth2GoogleUser) {
            user.setFirstName(((OAuth2GoogleUser) oAuth2UserDetails).getFamilyName());
            user.setLastName(((OAuth2GoogleUser) oAuth2UserDetails).getGivenName());
        }
        user.setProviderId(userRequest.getClientRegistration().getRegistrationId());
        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findByName("CUSTOMER"));
        return customerRepository.save(user);
    }
}
