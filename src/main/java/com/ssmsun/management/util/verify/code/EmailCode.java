package com.ssmsun.management.util.verify.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailCode {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(String email,String code) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("SSMSUN验证邮件");
        helper.setFrom("439795561@qq.com");
        helper.setTo(email);
        helper.setText("<table align='center' width='50%'>" +
                "<tr><td style='font-size: 30px'>验证码：" + code + "</td></tr>" +
                "<tr><td align='right' style='font-size: 20px'>——by SUN</td></tr>" +
                "</table>", true);

        javaMailSender.send(mimeMessage);
    }

}
