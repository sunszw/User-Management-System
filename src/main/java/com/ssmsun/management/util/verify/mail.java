package com.ssmsun.management.util.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class mail {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("SSMSUN验证邮件");
        helper.setFrom("439795561@qq.com");
        helper.setTo(email);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String ran = String.valueOf((int) (Math.random() * 9 + 1));
            str.append(ran);
        }
        helper.setText("<table align='center' width='50%'>" +
                "<tr><td>验证码：" + str.toString() + "</td></tr>" +
                "<tr><td align='right'>——by SUN</td></tr>" +
                "</table>", true);

        javaMailSender.send(mimeMessage);
    }

}
