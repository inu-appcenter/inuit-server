package pj.circles.controller;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pj.circles.dto.EmailRequest;
import pj.circles.dto.EmailResponseDto;
import pj.circles.dto.VerifyRequest;
import pj.circles.repository.EmailRepository;
import pj.circles.service.EmailService;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Random;

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;
    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    @PostMapping("/email") // 이메일 인증 코드 보내기
    public EmailResponseDto emailAuth(@RequestBody @Valid EmailRequest email) throws Exception {
        sendSimpleMessage(email.getEmail());

        return new EmailResponseDto(email.getEmail());
    }

    @PostMapping("/verifyCode/{email}") // 이메일 인증 코드 검증
    public EmailResponseDto verifyCode(@PathVariable("email")String email, @RequestBody VerifyRequest code) {
        if(emailRepository.findByEmail(email).get().getCode().equals(code.getCode())) {
            emailService.isChecked(email);
            return new EmailResponseDto(code.getCode());
        }
        else{
            return new EmailResponseDto("인증코드불일치");
        }
    }


    /**
     *
     *함수
     */

    //이메일,인증번호로그/DB 저장
    private MimeMessage createMessage(String to)throws Exception{
        logger.info("보내는 대상 : "+ to);

        MimeMessage  message = emailSender.createMimeMessage();
        String ePw = createKey();
        logger.info("인증 번호 : " + ePw);
        String code = createCode(ePw);
        message.addRecipients(MimeMessage.RecipientType.TO, to); //보내는 대상
        message.setSubject("Slack 확인 코드: " + code); //제목

        if(emailRepository.findByEmail(to).isEmpty()) {
            emailService.save(to, code);
        }
        else{
            emailService.setCode(to,code);
        }

        String msg="";
        msg += "<img width=\"120\" height=\"36\" style=\"margin-top: 0; margin-right: 0; margin-bottom: 32px; margin-left: 0px; padding-right: 30px; padding-left: 30px;\" src=\"https://slack.com/x-a1607371436052/img/slack_logo_240.png\" alt=\"\" loading=\"lazy\">";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 Slack 가입 창이 있는 브라우저 창에 입력하세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += code;
        msg += "</td></tr></tbody></table></div>";
        msg += "<a href=\"https://slack.com\" style=\"text-decoration: none; color: #434245;\" rel=\"noreferrer noopener\" target=\"_blank\">Slack Clone Technologies, Inc</a>";

        message.setText(msg, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress(to,"slack-clone")); //보내는 사람

        return message;
    }

    // 인증코드 만들기

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));

        }
        return key.toString();
    }
    public String createCode(String ePw){
        return ePw.substring(0, 3) + "-" + ePw.substring(3, 6);
    }
    //전송
    public void sendSimpleMessage(String to)throws Exception {
        MimeMessage message = createMessage(to);

        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }


}