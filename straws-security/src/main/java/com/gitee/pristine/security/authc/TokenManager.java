package com.gitee.pristine.security.authc;

import cn.hutool.core.date.DateUtil;
import com.gitee.pristine.common.utils.RsaUtil;
import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.security.config.SecurityProperties;
import com.gitee.pristine.security.context.Token;
import com.gitee.pristine.security.jwts.JwtClaims;
import com.gitee.pristine.security.jwts.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.UUID;

/**
 * @author Pristine Xu
 * @date 2022/2/3 6:32
 * @description:
 */
public class TokenManager {

    private final Logger log = LoggerFactory.getLogger(TokenManager.class);

    @Resource
    private SecurityProperties securityProperties;

    /**
     * 创建令牌
     * @param user
     * @return
     */
    public Token createToken(@NonNull User user) {
        // 获取属性
        SecurityProperties.TokenProperties tokenProp = securityProperties.getToken();
        // 密钥
        PrivateKey privateKey = null;
        try {
            privateKey = this.getPrivateKey();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            if (log.isDebugEnabled()) {
                log.debug("create application authentication token failure, cause by : {}", e.getMessage());
            }
            throw new JwtException("error: create application authentication token failure.");
        }
        // 生成访问令牌
        JwtClaims accessClaims = this.buildClaims(user, tokenProp);
        String accessToken = JwtUtil.createToken(accessClaims, privateKey);
        // 返回令牌
        return new Token(accessToken,
                tokenProp.getType(),
                null,
                tokenProp.getScope(),
                (long) tokenProp.getExpire());
    }

    /**
     * 刷新令牌
     * @param user
     * @return
     */
    public Token refreshToken(User user) {
        return this.createToken(user);
    }


    private JwtClaims buildClaims(User user, SecurityProperties.TokenProperties tokenProp) {
        // 计算时间
        Date issuedAt = new Date();
        Date expiration = DateUtil.offsetSecond(issuedAt, tokenProp.getExpire());
        return new JwtClaims(
                UUID.randomUUID().toString(),
                user.getUsername(),
                tokenProp.getAudience(),
                issuedAt,
                expiration
        );
    }

    /**
     * 获取令牌公钥
     * @return
     */
    private PublicKey getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PublicKey(securityProperties.getToken().getRsaPublicKey());
    }

    /**
     * 获取令牌私钥
     * @return
     */
    private PrivateKey getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PrivateKey(securityProperties.getToken().getRsaPrivateKey());
    }

}
