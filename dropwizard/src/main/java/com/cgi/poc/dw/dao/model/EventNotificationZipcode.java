package com.cgi.poc.dw.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "event_notification_zipcode")
@XmlRootElement
public class EventNotificationZipcode implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  @JsonIgnore
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  @Pattern(regexp = "\\d{5}", message = "is invalid.")
  @Column(name = "zip_code")
  private String zipCode;
  
  @JoinColumn(name = "event_notification_id", referencedColumnName = "id")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
      org.hibernate.annotations.CascadeType.PERSIST})
  @JsonIgnore
  private EventNotification eventNotificationId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public Long getEventNotificationId() {
    return eventNotificationId.getId();
  }

  public void setEventNotificationId(EventNotification eventNotificationId) {
    this.eventNotificationId = eventNotificationId;
  }
}
