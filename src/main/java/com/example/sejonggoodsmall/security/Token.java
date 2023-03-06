package com.example.sejonggoodsmall.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;
import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("refreshToken")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @JsonIgnore
    private Long id;

    private String refreshToken;

    @TimeToLive
    private Integer expiration;

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}
