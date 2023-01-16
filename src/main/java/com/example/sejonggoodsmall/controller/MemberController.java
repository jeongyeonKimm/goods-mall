package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> members() {
        return memberService.findMembers();
    }
}
