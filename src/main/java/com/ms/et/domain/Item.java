package com.ms.et.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;
    @Column(name = "SourceOfDelivery")
    private String mSourceOfDelivery;
    @Column(name = "DeliveryDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date mDeliveryDate;
    @Column(name = "Name")
    private String mName;
    @Column(name = "SerialNumber")
    private String mSerialNumber;
    @Column(name = "InternalNumber")
    private String mInternalNumber;
//    @Lob
//    private byte[] mImage;
//    private HardwareOwner mHardwareOwner;
//    private Receipt mReceipt;
//    private HardwareType mHardwareType;
//    private Person mAssignee;
//    private HardwareHistory mHardwareHistory;

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
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

//    public byte[] getImage() {
//        return mImage;
//    }
//
//    public void setImage(byte[] image) {
//        mImage = mImage;
//    }

}
