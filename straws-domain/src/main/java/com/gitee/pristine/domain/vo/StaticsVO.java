package com.gitee.pristine.domain.vo;

/**
 * @author Pristine Xu
 * @date 2022/2/5 13:23
 * @description:
 */
public class StaticsVO {

    private String icon;
    private String title;
    private String value;
    private String prefix;
    private String suffix;
    private String desc;

    public StaticsVO() {
    }

    public StaticsVO(String icon, String title, String value) {
        this.icon = icon;
        this.title = title;
        this.value = value;
    }

    public StaticsVO(String icon, String title, String value, String prefix, String suffix, String desc) {
        this.icon = icon;
        this.title = title;
        this.value = value;
        this.prefix = prefix;
        this.suffix = suffix;
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
