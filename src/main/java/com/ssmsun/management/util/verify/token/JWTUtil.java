package com.ssmsun.management.util.verify.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    //密钥
    public static final String SECRET = "GSDGUHSDSDBFJKSHASHFASHDIOASUJIO";

    //过期时间:毫秒
    public static final long EXPIRE = 1000 * 60 * 10;

    /**
     * 生成Token
     *
     * @param subject
     * @return
     * @throws Exception
     */
    public String createToken(String subject) {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Date now = new Date();
        String token = Jwts.builder()
                .setHeader(map)
                .setSubject(subject)
                .setIssuedAt(now)//签发时间
                .setExpiration(new Date(now.getTime() + EXPIRE))//有效期
                .signWith(SignatureAlgorithm.HS512, SECRET)//签名
                .compact();

        return token;
    }

    /**
     * 解析Token
     *
     * @param token
     * @return
     */
    public String parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }


}
