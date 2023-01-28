package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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

    public Member getByCredentials(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    public Member findEmail(String name, Date birth) {
        return memberRepository.findByNameAndBirth(name, birth);
    }
}
