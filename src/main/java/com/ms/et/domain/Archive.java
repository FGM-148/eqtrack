package com.ms.et.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "acrchives")
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address sourceOfDelivery;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    private String name;
    private String serialNumber;
    private String internalNumber;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date archiveTimestamp;
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "archive")
    private Set<ItemChangeLog> itemChangeLogs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getSourceOfDelivery() {
        return sourceOfDelivery;
    }

    public void setSourceOfDelivery(Address sourceOfDelivery) {
        this.sourceOfDelivery = sourceOfDelivery;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(String internalNumber) {
        this.internalNumber = internalNumber;
    }

    public java.util.Date getAchriveDate() {
        return archiveTimestamp;
    }

    public void setAchriveDate(java.util.Date achriveDate) {
        this.archiveTimestamp = achriveDate;
    }

    public Set<ItemChangeLog> getArchivedChangeLogs() {
        return itemChangeLogs;
    }

    public void setArchivedChangeLogs(Set<ItemChangeLog> itemChangeLogs) {
        this.itemChangeLogs = itemChangeLogs;
    }
}
