package com.gitee.pristine.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Pristine Xu
 * @date 2022/2/3 2:28
 * @description:
 */
@ConfigurationProperties(prefix = "straws.security")
public class SecurityProperties {

    // 令牌配置
    private TokenProperties token = new TokenProperties();

    public class TokenProperties {
        // 令牌请求头
        private String header = "Authorization";
        // 令牌签发对象类型
        private String audience = "";
        // 令牌类型
        private String type = "Brazer";
        // 授权范围
        private String scope = "Straws Server";
        // 访问令牌过期时间（单位：秒，默认30分钟）
        private Integer expire = 300 * 60;
        // 令牌RSA私钥
        private String rsaPrivateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA6K" +
                "7kQd+rTtlDfpFcFw9BBempm69CIBf1OarIB8StmDHaKk4T9Vt16PA1SjzYusIVWf5A3LQZn9qGwfT27jCU0QID" +
                "AQABAkA0pw7D0UdEiml5hI8gMLV4Dw3wStJdzM4TBJBLIbhejcrkS4GJ2Ej0xls+AUGWmA" +
                "CRB/jmXTv/DO5Gdk3DzrZ5AiEA+MKIlj1XQAtYH5F83XMrZSEo7vnSgo/zbpY7NpBWMF8CIQDvdJK+Jy" +
                "d7EbQeWLp28wt/53vm/gPmvXEirUB/QFOIzwIhAMNoRNUIAHbNsn6x0Y0/gBIj1zzKP+PR4l63YnI9NYc" +
                "vAiEApPHQA1xFPek8AYttJnLVAQ4bs0pWtaLZQ+HEA+PzptECIBfxaSFBVBUuRYm53lLNQAU8XCTXZenvbxSq4KTDZcpY";
        // 令牌RSA公钥
        private String rsaPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOiu5EHfq07ZQ36RXBcPQQXpqZuvQiAX9" +
                "TmqyAfErZgx2ipOE/VbdejwNUo82LrCFVn+QNy0GZ/ahsH09u4wlNECAwEAAQ==";

        // 允许匿名访问的接口
        private Set<String> anonymousUris = new HashSet<>();

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getAudience() {
            return audience;
        }

        public void setAudience(String audience) {
            this.audience = audience;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public Integer getExpire() {
            return expire;
        }

        public void setExpire(Integer expire) {
            this.expire = expire;
        }

        public String getRsaPrivateKey() {
            return rsaPrivateKey;
        }

        public void setRsaPrivateKey(String rsaPrivateKey) {
            this.rsaPrivateKey = rsaPrivateKey;
        }

        public String getRsaPublicKey() {
            return rsaPublicKey;
        }

        public void setRsaPublicKey(String rsaPublicKey) {
            this.rsaPublicKey = rsaPublicKey;
        }

        public Set<String> getAnonymousUris() {
            return anonymousUris;
        }

        public void setAnonymousUris(Set<String> anonymousUris) {
            this.anonymousUris = anonymousUris;
        }
    }

    public TokenProperties getToken() {
        return token;
    }

    public void setToken(TokenProperties token) {
        this.token = token;
    }
}
