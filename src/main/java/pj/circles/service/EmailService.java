package pj.circles.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pj.circles.domain.Email;
import pj.circles.repository.EmailRepository;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Random;


@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EmailRepository emailRepository;

    @Transactional
    public Long save(String email,String code){
        Email email1=new Email(email, code);
        emailRepository.save(email1);
        return email1.getId();
    }
    @Transactional
    public void setCode(String email,String code){

        Email email1=emailRepository.findByEmail(email).get();
        email1.setCode(code);

    }

    @Transactional
    public void isChecked(String email){
        emailRepository.findByEmail(email).get().isChecked();
    }



}
