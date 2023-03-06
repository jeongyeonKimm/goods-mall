package com.example.sejonggoodsmall.security;

import com.example.sejonggoodsmall.security.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
