package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.dto.MailDTO;
import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "sejonggoodsmall2@gmail.com";

    @Transactional
    public MailDTO createMailAndChangePassword(Member member) {
        int authNum = getAuthNumber();
        member.updateAuthNumber(authNum);

        MailDTO mailDTO = MailDTO.builder()
                .address(member.getEmail())
                .title("[세종이의 집] 비밀번호 찾기 인증 이메일 입니다.")
                .message("안녕하세요. [세종이의 집] 비밀번호 찾기 인증 이메일 입니다.\n" + "[" + member.getName() + "]님의 인증번호는 " + authNum + " 입니다.")
                .build();

        return mailDTO;
    }

    public void sendMail(MailDTO mailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(mailDTO.getAddress());
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getMessage());

        javaMailSender.send(message);
    }

    public int getAuthNumber() {
        Random r = new Random();
        int authNum = r.nextInt(888888) + 111111;
        return authNum;
    }
}
