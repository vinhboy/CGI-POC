/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.dao.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dawna.floyd
 */
@Entity
@Table(name = "event_hurricanes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventHurricanes.findAll", query = "SELECT e FROM EventHurricanes e"),
    @NamedQuery(name = "EventHurricanes.findById", query = "SELECT e FROM EventHurricanes e WHERE e.eventHurricanesPK.id = :id"),
    @NamedQuery(name = "EventHurricanes.findByPubdate", query = "SELECT e FROM EventHurricanes e WHERE e.eventHurricanesPK.pubdate = :pubdate"),
    @NamedQuery(name = "EventHurricanes.findByLink", query = "SELECT e FROM EventHurricanes e WHERE e.link = :link"),
    @NamedQuery(name = "EventHurricanes.findByObjectid", query = "SELECT e FROM EventHurricanes e WHERE e.objectid = :objectid"),
    @NamedQuery(name = "EventHurricanes.findByName", query = "SELECT e FROM EventHurricanes e WHERE e.name = :name"),
    @NamedQuery(name = "EventHurricanes.findByType", query = "SELECT e FROM EventHurricanes e WHERE e.type = :type"),
    @NamedQuery(name = "EventHurricanes.findByMovement", query = "SELECT e FROM EventHurricanes e WHERE e.movement = :movement"),
    @NamedQuery(name = "EventHurricanes.findByWind", query = "SELECT e FROM EventHurricanes e WHERE e.wind = :wind"),
    @NamedQuery(name = "EventHurricanes.findByLastModified", query = "SELECT e FROM EventHurricanes e WHERE e.lastModified = :lastModified"),
    @NamedQuery(name = "EventHurricanes.findByNotificationId", query = "SELECT e FROM EventHurricanes e WHERE e.notificationId = :notificationId")})
public class EventHurricanes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EventHurricanesPK eventHurricanesPK;
    @Size(max = 80)
    @Column(name = "link")
    private String link;
    @Column(name = "geometry")
    private String geometry;
    @Column(name = "objectid")
    private Integer objectid;
    @Lob
    @Column(name = "shape")
    private byte[] shape;
    @Size(max = 20)
    @Column(name = "name")
    private String name;
    @Size(max = 25)
    @Column(name = "type")
    private String type;
    @Size(max = 30)
    @Column(name = "movement")
    private String movement;
    @Size(max = 10)
    @Column(name = "wind")
    private String wind;
    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Column(name = "notification_id")
    private Integer notificationId;

    public EventHurricanes() {
    }

    public EventHurricanes(EventHurricanesPK eventHurricanesPK) {
        this.eventHurricanesPK = eventHurricanesPK;
    }

    public EventHurricanes(String id, Date pubdate) {
        this.eventHurricanesPK = new EventHurricanesPK(id, pubdate);
    }

    public EventHurricanesPK getEventHurricanesPK() {
        return eventHurricanesPK;
    }

    public void setEventHurricanesPK(EventHurricanesPK eventHurricanesPK) {
        this.eventHurricanesPK = eventHurricanesPK;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public Integer getObjectid() {
        return objectid;
    }

    public void setObjectid(Integer objectid) {
        this.objectid = objectid;
    }

    public byte[] getShape() {
        return shape;
    }

    public void setShape(byte[] shape) {
        this.shape = shape;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventHurricanesPK != null ? eventHurricanesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventHurricanes)) {
            return false;
        }
        EventHurricanes other = (EventHurricanes) object;
        if ((this.eventHurricanesPK == null && other.eventHurricanesPK != null) || (this.eventHurricanesPK != null && !this.eventHurricanesPK.equals(other.eventHurricanesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cgi.poc.dw.dao.model.EventHurricanes[ eventHurricanesPK=" + eventHurricanesPK + " ]";
    }
    
}
