package com.cos.security1.config.oauth.provider;

/**
 * Please explain the class!!
 *
 * @author : kma04
 * @fileName : OAuth2UserInfo
 * @since : 2023-10-05
 */
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();

}
