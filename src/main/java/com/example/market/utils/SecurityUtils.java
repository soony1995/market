package com.example.market.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null; // 인증된 사용자가 없는 경우 null 반환
    }

    public static String getCurrentUsername() {
        UserDetails userDetails = getCurrentUserDetails();
        return userDetails != null ? userDetails.getUsername() : null;
    }
}
