package com.ecommerce.project.security.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserInfoResponse {
    private Long id;
    private String jwtToken;//=>not needed anymore as we re using cookie now.
    //cookie is sent in user response headers now.

    private String username;
    private List<String> roles;

    public UserInfoResponse(Long id, String username, List<String> roles, String jwtToken) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }

    public UserInfoResponse(Long id, List<String> roles, String username) {
        this.id = id;
        this.roles = roles;
        this.username = username;
    }
}


