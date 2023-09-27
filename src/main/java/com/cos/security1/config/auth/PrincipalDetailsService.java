package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Please explain the class!!
 *
 * @author : kma04
 * @fileName : PrincipalDetailsService
 * @since : 2023-09-27
 */

//시큐리티 설정에서 loginProcessingUrl("/login"); 걸어놨기때문에
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 Ioc 되어있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    //시큐리티 session(내부 Authentication(내부 UserDetalis) )
    // 즉, security session 객체의 내부에 Autentication 객체가 Authentication객체의 내부에 UserDetails객체가 들어간다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {// 파라미터 username 은 loginForm의 name=username
        System.out.println("username : " + username);
        User userEntity = userRepository.findByUsername(username);

        if(userEntity != null){ //username이 있다면
            return new PrincipalDetails(userEntity); // username을 넣어주고
        }
        return null; //없다면 null 리턴
    }
}
