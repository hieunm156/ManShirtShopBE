package com.ManShirtShop.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {
    
    private String accessToken;
    private Long expires_in; // Thời gian hết hạn tính bằng giây
    private List<String> roles; // Thay đổi từ String thành List<String>
    
    public LoginResponse(String accessToken, long expirationTimeInSeconds) {
        this.accessToken = accessToken;
        this.expires_in = expirationTimeInSeconds;
    }

    public LoginResponse(String accessToken, long expirationTimeInSeconds, List<String> roles) {
        this.accessToken = accessToken;
        this.expires_in = expirationTimeInSeconds;
        this.roles = roles;
    }

    // Manual getters and setters to resolve Lombok compilation issues
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}