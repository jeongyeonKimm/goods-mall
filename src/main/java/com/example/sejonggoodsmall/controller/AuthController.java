package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.*;
import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.model.MemberStatus;
import com.example.sejonggoodsmall.service.MailService;
import com.example.sejonggoodsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {

    private final MemberService memberService;
    private final MailService mailService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/signup")
    public ResponseEntity<?> registerMember(@RequestBody MemberDTO memberDTO) {
        try {
            Member member = Member.builder()
                    .email(memberDTO.getEmail())
                    .password(memberDTO.getPassword())
                    .name(memberDTO.getUsername())
                    .password(passwordEncoder.encode(memberDTO.getPassword()))
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
        Member member = memberService.getByCredentials(memberDTO.getEmail(), memberDTO.getPassword(), passwordEncoder);
        
        if (member != null) {
            final MemberDTO responseMemberDTO = memberService.login(member);

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

    @PostMapping ("/find/email")
    public ResponseEntity<?> findEmail(@RequestBody MemberDTO memberDTO) {
        Member member = memberService.findEmail(memberDTO.getUsername(), memberDTO.getBirth());

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
                    .error("이메일 찾기 실패")
                    .build();

            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/find/password")
    public ResponseEntity<?> findPassword(@RequestBody FindPwDTO findPwDTO) {
        Member member = memberService.findByEmail(findPwDTO.getEmail());

        try {
            if (member != null) {
                if (member.getName().equals(findPwDTO.getName())) {
                    MailDTO mailDTO = mailService.createMailAndChangePassword(member);
                    mailService.sendMail(mailDTO);
                }
            } else {
                throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
            }

            FindPwDTO responseDTO = FindPwDTO.of(member);

            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            String error = e.getMessage();

            return ResponseEntity
                    .badRequest()
                    .body(error);
        }
    }

    @PostMapping("/check/authNumber")
    public ResponseEntity<?> checkAuthNumber(@RequestBody FindPwDTO findPwDTO) {
        Member member = memberService.findByEmail(findPwDTO.getEmail());
        int authNum = member.getAuthNumber();

        if (findPwDTO.getInputNum() == authNum) {
            FindPwDTO responseDTO = FindPwDTO.builder()
                    .authNumber(authNum)
                    .inputNum(findPwDTO.getInputNum())
                    .build();

            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("인증번호가 일치하지 않습니다.")
                    .build();

            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/update/password")
    public ResponseEntity<?> updatePassword(@RequestBody MemberDTO memberDTO) {

        try {
            Member member = Member.builder()
                    .email(memberDTO.getEmail())
                    .password(passwordEncoder.encode(memberDTO.getPassword()))
                    .build();

            Member updated = memberService.updatePassword(member);
            MemberDTO responseMember = MemberDTO.builder()
                    .id(updated.getId())
                    .email(updated.getEmail())
                    .password(updated.getPassword())
                    .build();

            return ResponseEntity
                    .ok()
                    .body(responseMember);
        } catch (Exception e) {
            String error = e.getMessage();

            return ResponseEntity
                    .badRequest()
                    .body(error);
        }
    }

    @PostMapping("/check/token")
    public ResponseEntity<?> checkToken(@RequestBody TokenCheckDTO tokenCheckDTO) {
        Member member = memberService.findByToken(tokenCheckDTO.getAccessToken());

        TokenCheckDTO responseDTO = TokenCheckDTO.builder()
                .memberId(member.getId())
                .build();

        return ResponseEntity
                .ok()
                .body(responseDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMember(@AuthenticationPrincipal Long memberId) {
        try {
            Member member = memberService.findById(memberId);
            if (member == null) {
                throw new IllegalArgumentException("존재하지 않는 회원입니다.");
            }

            MemberDTO responseDTO = MemberDTO.builder()
                    .id(memberId)
                    .email(member.getEmail())
                    .build();

            memberService.delete(member);

            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            String error = e.getMessage();

            return ResponseEntity
                    .badRequest()
                    .body(error);
        }
    }
}
