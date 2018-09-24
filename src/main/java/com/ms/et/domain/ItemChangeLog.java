package com.ms.et.domain;

import javax.persistence.*;

@Entity
@Table(name = "itemChangeLogs")
public class ItemChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String event;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date eventTimestamp;
    @ManyToOne
    @JoinColumn(name = "items_id")
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public java.util.Date getEventDate() {
        return eventTimestamp;
    }

    public void setEventDate(java.util.Date eventDate) {
        this.eventTimestamp = eventDate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
