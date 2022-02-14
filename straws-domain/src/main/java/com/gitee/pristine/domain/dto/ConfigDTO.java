package com.gitee.pristine.domain.dto;


/**
 * @author Pristine Xu
 * @date 2022/2/7 13:36
 * @description:
 */
public class ConfigDTO {

    private Boolean forceUpdatePassword;

    private Integer forceUpdateInterval;

    private Boolean encDspwdInDb;

    public Boolean getForceUpdatePassword() {
        return forceUpdatePassword;
    }

    public void setForceUpdatePassword(Boolean forceUpdatePassword) {
        this.forceUpdatePassword = forceUpdatePassword;
    }

    public Integer getForceUpdateInterval() {
        return forceUpdateInterval;
    }

    public void setForceUpdateInterval(Integer forceUpdateInterval) {
        this.forceUpdateInterval = forceUpdateInterval;
    }

    public Boolean getEncDspwdInDb() {
        return encDspwdInDb;
    }

    public void setEncDspwdInDb(Boolean encDspwdInDb) {
        this.encDspwdInDb = encDspwdInDb;
    }

    @Override
    public String toString() {
        return "ConfigDTO{" +
                "forceUpdatePassword=" + forceUpdatePassword +
                ", forceUpdateInterval=" + forceUpdateInterval +
                ", encDspwdInDb=" + encDspwdInDb +
                '}';
    }
}
