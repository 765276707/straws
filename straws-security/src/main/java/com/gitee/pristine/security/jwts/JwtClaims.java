package com.gitee.pristine.security.jwts;

import io.jsonwebtoken.Claims;

import java.util.Date;

/**
 * Jwt的Claims
 * @author Pristine Xu
 * @date 2022/2/3 2:41
 * @description:
 */
public class JwtClaims {

    private String id;
    private String subject;
    private String audience;
    private Date issuedAt;
    private Date expiration;

    public JwtClaims() {
    }

    public JwtClaims(String id, String subject, String audience, Date issuedAt, Date expiration) {
        this.id = id;
        this.subject = subject;
        this.audience = audience;
        this.issuedAt = issuedAt;
        this.expiration = expiration;
    }

    public JwtClaims(Claims claims) {
        this.id = claims.getId();
        this.subject = claims.getSubject();
        this.audience = claims.getAudience();
        this.issuedAt = claims.getIssuedAt();
        this.expiration = claims.getExpiration();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
