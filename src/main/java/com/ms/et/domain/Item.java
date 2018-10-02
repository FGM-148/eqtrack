package com.ms.et.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {

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
    private boolean inStorage;
    @ManyToOne
    @JoinColumn(name = "companies_id")
    private Company company;
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "item")
    private Set<ItemChangeLog> itemChangeLogs;
    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Project project;
//    @Lob
//    private byte[] mImage;

    public Long getId() { return id; }

    public void setId(Long _id) { id = _id; }

    public Address getSourceOfDelivery() {
        return sourceOfDelivery;
    }

    public void setSourceOfDelivery(Address _sourceOfDelivery) {
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

    public boolean isInStorage() { return inStorage; }

    public void setInStorage(boolean _disposed) { inStorage = _disposed; }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<ItemChangeLog> getItemChangeLogs() {
        return itemChangeLogs;
    }

    public void setItemChangeLogs(Set<ItemChangeLog> itemChangeLogs) {
        this.itemChangeLogs = itemChangeLogs;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    //    public byte[] getImage() {
//        return mImage;
//    }
//
//    public void setImage(byte[] image) {
//        mImage = mImage;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User _user) {
        user = _user;
    }

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

}
