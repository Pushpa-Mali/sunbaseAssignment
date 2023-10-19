package com.pushpa.sunbase.Controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@JsonAutoDetect
public class bearerToken {
    @JsonProperty("access_token")
    private String token;

    public bearerToken(String token) {
        this.token = token;
    }

    public bearerToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
