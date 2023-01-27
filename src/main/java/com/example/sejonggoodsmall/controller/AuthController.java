package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.MemberDTO;
import com.example.sejonggoodsmall.dto.ResponseDTO;
import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.model.MemberStatus;
import com.example.sejonggoodsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerMember(@RequestBody MemberDTO memberDTO) {
        try {
            Member member = Member.builder()
                    .email(memberDTO.getEmail())
                    .password(memberDTO.getPassword())
                    .name(memberDTO.getUsername())
                    .birth(memberDTO.getBirth())
                    .status(MemberStatus.ACTIVE)
                    .build();
            
            Member registeredMember = memberService.join(member);

            MemberDTO responseMemberDTO = MemberDTO.builder()
                    .id(registeredMember.getId())
                    .email(registeredMember.getEmail())
                    .password(registeredMember.getPassword())
                    .username(registeredMember.getName())
                    .birth(registeredMember.getBirth())
                    .build();

            return ResponseEntity
                    .ok()
                    .body(responseMemberDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping ("/signin")
    public ResponseEntity<?> authenticate(@RequestBody MemberDTO memberDTO) {
        Member member = memberService.getByCredentials(memberDTO.getEmail(), memberDTO.getPassword());
        
        if (member != null) {
            final MemberDTO responseMemberDTO = MemberDTO.builder()
                    .email(member.getEmail())
                    .id(member.getId())
                    .build();

            return ResponseEntity
                    .ok()
                    .body(responseMemberDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("로그인 실패")
                    .build();
            
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}
