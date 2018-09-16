package com.ms.et.commands;

import java.sql.Date;

public class ItemForm {
    private Long id;
    private String mSourceOfDelivery;
    private Date mDeliveryDate;
    private String mName;
    private String mSerialNumber;
    private String mInternalNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceOfDelivery() {
        return mSourceOfDelivery;
    }

    public void setSourceOfDelivery(String sourceOfDelivery) {
        mSourceOfDelivery = sourceOfDelivery;
    }

    public Date getDeliveryDate() {
        return mDeliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        mDeliveryDate = deliveryDate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSerialNumber() { return mSerialNumber; }

    public void setSerialNumber(String serialNumber) {
        mSerialNumber = serialNumber;
    }

    public String getInternalNumber() {
        return mInternalNumber;
    }

    public void setInternalNumber(String internalNumber) {
        mInternalNumber = internalNumber;
    }
}
