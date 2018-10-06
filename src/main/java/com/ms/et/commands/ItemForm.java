package com.ms.et.commands;

import com.ms.et.domain.Company;

import java.sql.Date;

public class ItemForm {
    private Long id;
    private Date mDeliveryDate;
    private String mName;
    private String mSerialNumber;
    private String mInternalNumber;
    private Company company;
    private AddressForm sourceOfDelivery;
    private Boolean inStorage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AddressForm getSourceOfDelivery() {
        return sourceOfDelivery;
    }

    public void setSourceOfDelivery(AddressForm sourceOfDelivery) {
        this.sourceOfDelivery = sourceOfDelivery;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Boolean getInStorage() {
        return inStorage;
    }

    public void setInStorage(Boolean inStorage) {
        this.inStorage = inStorage;
    }
}
