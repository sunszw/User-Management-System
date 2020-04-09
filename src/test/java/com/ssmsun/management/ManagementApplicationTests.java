package com.ssmsun.management;

import com.ssmsun.management.dao.UserMapper;
import com.ssmsun.management.entity.User;
import com.ssmsun.management.util.encryption.EncryptedPassword;
import io.lettuce.core.ScriptOutputType;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class ManagementApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    EncryptedPassword encryptedPassword;

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    void contextLoads() {
    }

    @Test
    void testSHA256() {

        System.out.println(encryptedPassword.getPassword("123456", "HDGFHSDFHSDLHFKLHLKSDHFKLSHDFLK"));
    }

    @Test
    void testInsert() {
        Integer sum = 0;
        for (int i = 1; i < 10; i++) {

            User user = new User();
            user.setUsername("user" + i);

            String salt = UUID.randomUUID().toString();
            String sha256Pwd = encryptedPassword.getPassword("pwd" + i, salt);
            user.setPassword(sha256Pwd);
            user.setSalt(salt);

            int j = 1;
            if (i % 2 == 0) {
                j = 0;
            }
            user.setGender(j);
            user.setAmount(Math.random() * 100 + 1);

            StringBuilder str1 = new StringBuilder();
            for (int a = 0; a < 8; a++) {
                String ran = String.valueOf((int) (Math.random() * 9 + 1));
                str1.append(ran);
            }
            String ss = "133";
            user.setPhone(ss + str1);

            StringBuilder str2 = new StringBuilder();
            for (int a = 0; a < 8; a++) {
                String ran = String.valueOf((int) (Math.random() * 9 + 1));
                str2.append(ran);
            }

            user.setEmail(str2 + "@qq.com");

            user.setAvatar("E:\\Resources\\head portrait\\hk46564js5dk45jfs552jl.jpg");
            user.setCredate(LocalDateTime.now());
            Integer row = userMapper.addUser(user);
            System.out.println(user);
            sum = row++;
        }

        System.out.println(sum);

    }

    @Test
    void testLogin() {
        if ("5daf7fe44b1cd536879486c2ea2276d9d8ac7e38827df3e37b5402f71db57ff0".equals(encryptedPassword.getPassword("admin", "bca8fcf5-e0c2-403a-81ae-ea356e016f02"))) {
            System.out.println("密码正确");
        } else {
            System.out.println("密码错误");
        }
    }


    @Test
    public void sendMail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("SSMSUN验证邮件");
        helper.setFrom("439795561@qq.com");
        helper.setTo("1549742307@qq.com");
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
        System.out.println("发送成功！");
    }

    @Test
    void password() {
        String pwd = encryptedPassword.getPassword("123", "AA384B0A-693F-425A-9012-0F7E3714A44A");
        System.out.println(pwd);
        System.out.println("56DC3A4924CEB38A1CA5AF076C199E5116C388C871150E75F6A4BE121F127A23");
    }

    @Test
    void testDate() {
        Date now = new Date();
        Date day = new Date(now.getTime() + (1000 * 60 * 60 * 24 * 7));
        System.out.println(day);

    }

    @Test
    void testEmail() {
        StringBuilder builder = new StringBuilder();
        String charStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * charStr.length());
            builder.append(charStr.charAt(index));
        }
        System.out.println(builder.toString());
    }

}
