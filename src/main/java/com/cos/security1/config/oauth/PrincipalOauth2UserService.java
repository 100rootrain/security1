package com.cos.security1.config.oauth;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * Please explain the class!!
 *
 * @author : kma04
 * @fileName : PrincipalOauth2UserService
 * @since : 2023-10-04
 */


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    //loadUser라는 함수에서 후처리를 함
    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration()); //registrationId로 어떤 OAuth로 로그인 했는지 확인가능
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());


        OAuth2User oAuth2User = super.loadUser(userRequest);
        //구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code로 리턴(OAuth-Client 라이브러리 ) -> AccessToken 요청
        // userRequest 정보 -> loadUser 함수 호출-> 구글로부터 회원프로필 받아준다.
        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());
        System.out.println("getAttributes : " + oAuth2User.getAttributes());

        //회원가입을 강제로 진행해볼 예정
        String provider = userRequest.getClientRegistration().getClientId(); //google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider+ "_" + providerId; //google_10123912837412 username 중복방지
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";
        //무조건회원가입시키면안됨, 이미 회원가입이 되어있을수도 있으니깐

        User userEntity =  userRepository.findByUsername(username);
        if(userEntity ==null){
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }


        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
