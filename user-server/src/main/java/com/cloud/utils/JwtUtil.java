package com.cloud.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cloud.common.constants.CommonConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author： Zhou Shuai
 * @Date： 15:57 2019/1/7
 * @Description：
 * @Version:  01
 */
public class JwtUtil {

    /**
     * token 默认过期时间，这里设为5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 生成token签名
     * @param map 存放用户信息数据
     * @param subject 主题；代表这个JWT的主体，即它的所有人，
     * @param expireTime token过期时间，单位分钟
     * @return 加密后的token
     */
    public static String sign(Map<String, Object> map,String subject,Long expireTime) {
        long expMillis = System.currentTimeMillis();
        if (expireTime != null) {
            expMillis += expireTime * 60 * 1000;
        }else {
            expMillis += EXPIRE_TIME;
        }
        Date date = new Date(expMillis);

        Map<String, Object> header = new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");

        //使用HS256算法
        Algorithm algorithm = Algorithm.HMAC256(CommonConstant.jwtToken.JWT_SECRET);
        //创建令牌实例
        String token = JWT.create()
                .withHeader(header)
                //指定自定义声明，保存一些信息
                .withClaim("userInfo", map)
                //信息直接放在这里也行
                .withSubject(subject)
                //过期时间
                .withExpiresAt(date)
                //iat: jwt的签发时间
                .withIssuedAt(new Date())
                //签名
                .sign(algorithm);
        return token;
    }

    /**
     * 校验token是否正确
     * @param token 令牌
     * @return 是否正确
     */
    public static Map<String, Object> verify(String token) throws Exception {
        try{
            Algorithm algorithm = Algorithm.HMAC256(CommonConstant.jwtToken.JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userInfo").asMap();
        } catch (TokenExpiredException e){
            throw new TokenExpiredException("token已过期，请重新登录");
        }catch (Exception e){
            throw new Exception("无效的token");
        }
    }

    /**
     * 获得token中的信息
     * @return token中包含的用户信息
     */
    public static Map<String, Object> getUserInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userInfo").asMap();
        }catch(Exception e){
            return null;
        }
    }

}
