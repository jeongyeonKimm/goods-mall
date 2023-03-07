package com.example.sejonggoodsmall.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}
