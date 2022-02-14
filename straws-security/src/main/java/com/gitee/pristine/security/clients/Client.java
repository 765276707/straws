package com.gitee.pristine.security.clients;

/**
 * 客户端
 * @author Pristine Xu
 * @date 2022/2/3 6:34
 * @description:
 */
public class Client {

    private String ip;

    private String domain;

    private String os;

    private String browser;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
