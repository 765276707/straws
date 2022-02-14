package com.gitee.pristine.security.context;

import com.gitee.pristine.security.jwts.JwtType;

import java.io.Serializable;

/**
 * 令牌
 * @author Pristine Xu
 * @date 2022/2/3 6:34
 * @description:
 */
public class Token implements Serializable {

    private String accessToken;

    private String tokenType;

    private String refreshToken;

    private String scope;

    private Long expiresIn;


    public Token(String accessToken, String tokenType, String refreshToken, String scope, Long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.expiresIn = expiresIn;
    }

    public Builder builder() {
        return new Builder();
    }

    /**
     * <h1>建造者</h1>
     * @author xzb
     */
    public static class Builder {
        private String accessToken;
        private String tokenType;
        private String refreshToken;
        private String scope;
        private Long expiresIn;

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder expiresIn(Long expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }

        public Token build() {
            return new Token(this.accessToken, this.tokenType, this.refreshToken, this.scope, this.expiresIn);
        }

    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }
}
