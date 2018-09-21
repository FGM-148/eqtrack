package com.ms.et.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sourceOfDelivery;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    private String name;
    private String serialNumber;
    private String internalNumber;
//    @Lob
//    private byte[] mImage;
//    private HardwareOwner mHardwareOwner;
//    private Receipt mReceipt;
//    private HardwareType mHardwareType;
//    private Person mAssignee;
//    private HardwareHistory mHardwareHistory;

    public Long getId() { return id; }

    public void setId(Long _id) { id = _id; }

    public String getSourceOfDelivery() {
        return sourceOfDelivery;
    }

    public void setSourceOfDelivery(String _sourceOfDelivery) {
        sourceOfDelivery = _sourceOfDelivery;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date _deliveryDate) {
        deliveryDate = _deliveryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getSerialNumber() { return serialNumber; }

    public void setSerialNumber(String _serialNumber) {
        serialNumber = _serialNumber;
    }

    public String getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(String _internalNumber) {
        internalNumber = _internalNumber;
    }

//    public byte[] getImage() {
//        return mImage;
//    }
//
//    public void setImage(byte[] image) {
//        mImage = mImage;
//    }

}
