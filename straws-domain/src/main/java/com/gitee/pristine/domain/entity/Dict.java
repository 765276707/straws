package com.gitee.pristine.domain.entity;

import javax.persistence.*;

@Table(name = "sys_dict")
public class Dict {
    @Id
    private Long id;

    @Column(name = "dict_type")
    private String dictType;

    @Column(name = "dict_name")
    private String dictName;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_value")
    private String itemValue;

    /**
     * 是否可用
     */
    private Boolean enabled;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return dict_type
     */
    public String getDictType() {
        return dictType;
    }

    /**
     * @param dictType
     */
    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    /**
     * @return dict_name
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * @param dictName
     */
    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    /**
     * @return item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    /**
     * @return item_value
     */
    public String getItemValue() {
        return itemValue;
    }

    /**
     * @param itemValue
     */
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue == null ? null : itemValue.trim();
    }

    /**
     * 获取是否可用
     *
     * @return enabled - 是否可用
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置是否可用
     *
     * @param enabled 是否可用
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}