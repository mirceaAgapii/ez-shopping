package com.ezshopping.mail.service;

import com.ezshopping.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSender {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(User user) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(user.getEmail());

        helper.setSubject("Registration confirmation");


        helper.setText("<h1>Welcome " + user.getUsername() + "</h1>" +
                "<h3>Thank you for choosing EZ-Shopping app</h3>", true);


        javaMailSender.send(msg);

    }
}
