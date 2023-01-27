package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.model.Address;
import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
    public void joinMember() {

        // Given
        Member member = new Member("test@test.com", "1234", "user1", "010-0000-0000", new Address("Seoul", "605", "123-123"));

        // When
        Long memberId = memberService.join(member);

        // Then
        assertEquals(member, memberService.findOne(memberId));
    }

    @Test(expected = IllegalStateException.class)
    public void duplicatedMember() throws Exception {

        // Given
        Member member1 = new Member("test@test.com", "1234", "user1", "010-0000-0000", new Address("Seoul", "605", "123-123"));
        Member member2 = new Member("test@test.com", "1234", "user2", "010-0000-0000", new Address("Seoul", "605", "123-123"));

        // When
        memberService.join(member1);
        memberService.join(member2);

        // Then
        fail("이미 존재하는 회원입니다.");
    }

}
