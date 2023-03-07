package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.dto.MemberDTO;
import com.example.sejonggoodsmall.security.TokenDTO;
import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.security.Token;
import com.example.sejonggoodsmall.repository.MemberRepository;
import com.example.sejonggoodsmall.security.TokenRepository;
import com.example.sejonggoodsmall.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public Member join(Member member) {
        if (member == null || member.getEmail() == null) {
            throw new RuntimeException("유효하지 않는 값입니다.");
        }
        String email = member.getEmail();
        if (memberRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("이미 존재하는 이메일 입니다.");
        }

        return memberRepository.save(member);
    }

    public Member getByCredentials(String email, String password, final PasswordEncoder passwordEncoder) {
        final Member originalMember = memberRepository.findByEmail(email);

        if (originalMember != null && passwordEncoder.matches(password, originalMember.getPassword())) {
            return originalMember;
        }

        return null;
    }

    @Transactional
    public MemberDTO login(Member member) {
        String token = tokenProvider.create(member);
        member.setAccessToken(token);

        return MemberDTO.builder()
                .email(member.getEmail())
                .id(member.getId())
                .token(TokenDTO.builder()
                        .accessToken(token)
                        .refreshToken(createRefreshToken(member))
                        .build())
                .build();
    }

    public Member findEmail(String name, Date birth) {
        return memberRepository.findByNameAndBirth(name, birth);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public Member updatePassword(Member member) {
        Member originalMember = memberRepository.findByEmail(member.getEmail());
        originalMember.updatePassword(member.getPassword());
        return originalMember;
    }

    public String createRefreshToken(Member member) {
        Integer exp = Math.toIntExact(1000L * 60 * 24 * 14);

        Token token = tokenRepository.save(
                Token.builder()
                        .id(member.getId())
                        .refreshToken(UUID.randomUUID().toString())
                        .expiration(exp)
                        .build()
        );
        return token.getRefreshToken();
    }

    public Token validRefreshToken(Member member, String refreshToken) throws Exception {
        Token token = tokenRepository.findById(member.getId()).orElseThrow(() -> new Exception("만료된 계정입니다. 로그인을 다시 시도하세요"));
        if (token.getRefreshToken() == null) {
            return null;
        } else {
            if(token.getExpiration() < 10) {
                token.setExpiration((int) (1000L * 60 * 24 * 14));
                tokenRepository.save(token);
            }

            // 토큰이 같은지 비교
            if(!token.getRefreshToken().equals(refreshToken)) {
                return null;
            } else {
                return token;
            }
        }
    }

    public TokenDTO refreshAccessToken(TokenDTO token) throws Exception {
        String account = tokenProvider.validateAndGetUserId(token.getAccessToken());
        Member member = memberRepository.findById(Long.valueOf(account)).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));
        Token refreshToken = validRefreshToken(member, token.getRefreshToken());

        if (refreshToken != null) {
            return TokenDTO.builder()
                    .accessToken(tokenProvider.create(member))
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        } else {
            throw new Exception("로그인을 해주세요");
        }
    }

    public Member findByToken(String token) {
        return memberRepository.findByAccessToken(token);
    }
}
