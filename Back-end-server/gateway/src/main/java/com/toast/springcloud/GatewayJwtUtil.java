package com.toast.springcloud;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class GatewayJwtUtil  {

    //默认有效期为
    public static final Long JWT_TTL = 1800000000L;// 30 * 60 *1000  半小时  测试多加三个0=30000分钟

    //Jwt令牌信息 盐
    public static final String JWT_KEY = "itcast";
    /**
     *创建令牌
     *id：唯一表示
     *subject：主题
     *ttlMillis：有效期	传null的话，默认半小时
     **/
    public static String createJWT(String id, String subject, Long ttlMillis) {
        //指定算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //当前系统时间
        long nowMillis = System.currentTimeMillis();
        //令牌签发时间
        Date now = new Date(nowMillis);

        //如果令牌有效期为null，则默认设置有效期半小时
        if(ttlMillis==null){
            ttlMillis=GatewayJwtUtil.JWT_TTL;
        }

        //令牌过期时间设置
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        //生成秘钥
        SecretKey secretKey = generalKey();

        //封装Jwt令牌信息
        JwtBuilder builder = Jwts.builder()
                .setId(id)                    //唯一的ID
                .setSubject(subject)          // 主题  可以是JSON数据
                .setIssuer("admin")          // 签发者
                .setIssuedAt(now)             // 签发时间
                .signWith(signatureAlgorithm, secretKey) // 签名算法以及密匙
                .setExpiration(expDate);      // 设置过期时间
        return builder.compact();
    }

    /**
     * 生成加密 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(GatewayJwtUtil.JWT_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    /**
     * 解析令牌数据
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

//    public void controller(){
//        Map<String,Object> info = new HashMap<String,Object>();
//        info.put("role","USER");
//        info.put("success","SUCCESS");
//        info.put("username","username");
//        //生成令牌
//        String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(info),null);
//    }


}