package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Please explain the class!!
 *
 * @author : kma04
 * @fileName : IndexController
 * @since : 2023-09-27
 */

@Controller // View를 리턴하겠다.
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin
            (Authentication authentication,
             @AuthenticationPrincipal PrincipalDetails userDetails) { //DI(의존성주입)
        System.out.println("test/login =================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        //리턴타입이 오브젝트이기 때문에, 다운캐스팅받아서 getUser을 호출하면
        System.out.println("authentication : " + principalDetails.getUser());

        System.out.println("userDetails : " + userDetails.getUser());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin
            (Authentication authentication,
             @AuthenticationPrincipal OAuth2User oAuth) { //DI(의존성주입)
        System.out.println("test/oauth/login =================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        //리턴타입이 오브젝트이기 때문에, 다운캐스팅받아서 getUser을 호출하면
        System.out.println("authentication : " + oAuth2User.getAttributes());
        System.out.println("oAuth2User : " + oAuth.getAttributes());

        return "OAuth 세션 정보 확인하기";
    }

    //localhost:8080/
    //localhost:8080
    @GetMapping({"","/"}) //주소를 공백과 /로 두개 넣어준거임
    public String index(){
        //머스테치 기본폴더 src/main/resources/
        return "index"; // src/main/resources/templates/index.mustache
    }


    //OAuth 로그인을 해도 PrincipalDetails 받을 수 있고,
    //일반로그인을 해도 PrincipalDetails를 받을 수 있다.
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails : " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }


    //스프링 시큐리티가 해당주소를 낚아챔 - SecurityConfig 파일 생성 후 작동안함
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);// 회원가입잘됨. 비밀번호:1234 => 시큐리티로 로그인할수없음. 이유는 패스워드가 암호아 안되어 있음.
        return "redirect:/loginForm"; //redirect를 붙이면 @GetMapping("/loginForm") 호출
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }


}
