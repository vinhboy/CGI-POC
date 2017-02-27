/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.dao.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author dawna.floyd
 */
@Entity
@Table(name = "event_notification_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventNotificationUser.findAll", query = "SELECT e FROM EventNotificationUser e"),
    @NamedQuery(name = "EventNotificationUser.findById", query = "SELECT e FROM EventNotificationUser e WHERE e.id = :id")})
public class EventNotificationUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "event_notification_id", referencedColumnName = "id")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private EventNotification eventNotificationId;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false,  fetch = FetchType.LAZY)
    private User userId;

    public EventNotificationUser() {
    }

    public EventNotificationUser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventNotificationId() {
        return eventNotificationId.getId();
    }

    public void setEventNotificationId(EventNotification eventNotificationId) {
        this.eventNotificationId = eventNotificationId;
    }

    public Long getUserId() {
        return userId.getId();
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    @Override
    public String toString() {
        return "com.cgi.poc.dw.dao.model.EventNotificationUser[ id=" + id + " ]";
    }
    
}
