package com.website.admin.utils;

import com.website.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JWT工具类
 */
public class JWTUtils {

    //失效时间18个小时
    private static final long EXPIRE = 60000*60*18;

    private static final String SECRET = "wechat-mall-ims-20210420";

    private static final String PREFIX = "IMS";

    private static final String SUBJECT = "DEPRT2-IMS";

    //生成token
    public static String geneToken(User user){
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("userId",user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();
        return PREFIX + token;
    }

    //token 解密
    public static Claims checkToken(String token){
        try{
            final Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(PREFIX,""))
                    .getBody();
            return claims;
        }catch (Exception e){
            return null;//解密失败,密钥无效，token失效
        }
    }

    //token失效
    public static String updateTokenInvalid(Claims claims){
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("userId",claims.get("userId"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()-EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();
        return PREFIX + token;
    }


}
