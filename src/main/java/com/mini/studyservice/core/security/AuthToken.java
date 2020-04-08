package com.mini.studyservice.core.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthToken {
    private String loginid;
    private String username;
    private String token;  
}
