package com.example.demo.jwt;

import java.util.List;

public class AuthResponse {

    private String correo;
    private String accessToken;
    private List<String> roles;

    public AuthResponse(String correo, String accessToken, List<String> roles) {
        this.correo = correo;
        this.accessToken = accessToken;
        this.roles = roles;
    }

    public AuthResponse() {

    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
