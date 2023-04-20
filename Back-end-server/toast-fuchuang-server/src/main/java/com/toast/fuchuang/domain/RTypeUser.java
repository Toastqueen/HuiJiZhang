package com.toast.fuchuang.domain;

import java.io.Serializable;

public class RTypeUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private Boolean flag;

    private Long typeId;

    private String typeName;

    private Long parentId;

    private String contentsType;

    private String recordType;

    private Boolean visible;

    public RTypeUser(MoneyType moneyType,Long typeId,Boolean flag,Long userId) {
        this.typeName=moneyType.getTypeName();
        this.contentsType=moneyType.getContentsType();
        this.parentId=moneyType.getParentId();
        this.recordType=moneyType.getRecordType();
        this.typeId =typeId;
        this.flag=flag;
        this.userId=userId;
    }

    public RTypeUser() {
    }

    public RTypeUser(Long id, Long userId, Boolean flag, Long typeId, String typeName, Long parentId, String contentsType, String recordType, Boolean visible) {
        this.id = id;
        this.userId = userId;
        this.flag = flag;
        this.typeId = typeId;
        this.typeName = typeName;
        this.parentId = parentId;
        this.contentsType = contentsType;
        this.recordType = recordType;
        this.visible = visible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContentsType() {
        return contentsType;
    }

    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
