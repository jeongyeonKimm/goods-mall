package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.dto.MemberDTO;
import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.repository.MemberRepository;
import com.example.sejonggoodsmall.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
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
                .token(token)
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

    public Member findByToken(String token) {
        return memberRepository.findByAccessToken(token);
    }
}
