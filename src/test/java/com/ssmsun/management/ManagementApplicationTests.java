package com.ssmsun.management;

import com.ssmsun.management.dao.UserMapper;
import com.ssmsun.management.entity.User;
import com.ssmsun.management.util.encryption.EncryptedPassword;
import com.ssmsun.management.util.verify.token.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JWTUtil jwtUtil;

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
        String pwd = encryptedPassword.getPassword("admin", "AB2D206C-37BD-4D2E-A988-2CE4A7B28F54");
        System.out.println(pwd);
        System.out.println("7E0E835650010FCA13A26438B88546E52A3A73C7F279FC7F37C514A7915B3080");
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

    @Test
    void testUInsert() {
        int sum = 0;
        for (int i = 151; i <= 2000; i++) {
            User user = new User();
            String saltUUID = UUID.randomUUID().toString().toUpperCase();
            user.setUsername("user" + i);
            String SHA256Pwd = encryptedPassword.getPassword("123", saltUUID);
            user.setPassword(SHA256Pwd);
            user.setSalt(saltUUID);
            int num = 0;
            if (i % 2 == 0) {
                num = 1;
            }
            user.setGender(num);
            StringBuilder str1 = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                String ran = String.valueOf((int) (Math.random() * 9 + 1));
                str1.append(ran);
            }
            user.setPhone("186" + str1.toString());
            StringBuilder str2 = new StringBuilder();
            for (int j = 0; j < 10; j++) {
                String ran = String.valueOf((int) (Math.random() * 9 + 1));
                str2.append(ran);
            }
            user.setEmail(str2.toString() + "@qq.com");
            user.setAmount(Math.random() * 10000);
            user.setAvatar("d5061dba0c9a45efb83abbab34981cfc.jpg");
            user.setCredate(LocalDateTime.now());
            user.setConfirm(num);
            user.setVip((int) (Math.random() * 5));
            userMapper.addUser(user);
            System.out.println(user);
            sum++;
        }
        System.out.println(sum);
    }


    @Test
    void token() {
        Object value =  redisTemplate.opsForValue().get("69ff6d71-60be-4301-be25-7e6afa1ed862");
        String id = jwtUtil.parseToken(value.toString());
        System.out.println(value);
        System.out.println(id);

    }

    @Test
    void keys() {

        Set<String> keys = redisTemplate.keys("*");

        System.out.println(keys.size());
    }

}
