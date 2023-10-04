package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Map;

/**
 * Please explain the class!!
 *
 * @author : kma04
 * @fileName : PrincipalDetails
 * @since : 2023-09-27
 */

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행시킴
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어줌. (Security ContextHolder)
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야됨.
// User 오브젝트 타입 => UserDetails 타입 객체

// Security Session => Autentication => UserDetails(PrincipalDetails)
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;// 콤포지션
    private Map<String, Object> attributes;


    //일반 로그인 할때 사용하는 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }

    //OAuth 로그인 할때 사용하는 생성자
    public PrincipalDetails(User user, Map<String,Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }


    //해당유저의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayDeque<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }

        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }//니 계정 만료됐니? 아니요true

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }//니 계정 잠겼니? 아니요true

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }// 니 비밀번호 너무 오래사용한거 아니니? 아니요 true

    @Override
    public boolean isEnabled() {

        //우리사이트!! 1년동안 회원이 로그인을 안하면!!휴먼 계정으로 하기로 함.
        //현재시간 - 로긴시간 => 1년을 초과하면 return false;

        return true;
    }// 니 계정이 활성화 돼있니? 아니요 true

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getName() {
        return null;
    }
}
