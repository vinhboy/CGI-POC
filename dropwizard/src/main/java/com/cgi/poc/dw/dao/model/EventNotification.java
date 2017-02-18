package com.cgi.poc.dw.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "event_notification")
@XmlRootElement
public class EventNotification implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  @JsonIgnore
  private Long id;

  @Basic(optional = false)
  @NotNull
  @Column(name = "isEmergency")
  private String isEmergency;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 5, max = 2048)
  @Column(name = "description")
  private String description;

  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
      org.hibernate.annotations.CascadeType.PERSIST})
  @JsonIgnore
  private User userId;

  @OneToMany(mappedBy = "eventNotificationId", fetch = FetchType.EAGER, orphanRemoval = true)
  @NotNull
  @Valid
  @Cascade({org.hibernate.annotations.CascadeType.ALL})
  private Set<EventNotificationZipcode> eventNotificationZipcodes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIsEmergency() {
    return isEmergency;
  }

  public void setIsEmergency(String isEmergency) {
    this.isEmergency = isEmergency;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUserId() {
    return userId;
  }

  public void setUserId(User userId) {
    this.userId = userId;
  }

  public Set<EventNotificationZipcode> getEventNotificationZipcodes() {
    return eventNotificationZipcodes;
  }

  public void setEventNotificationZipcodes(
      Set<EventNotificationZipcode> eventNotificationZipcodes) {
    this.eventNotificationZipcodes = eventNotificationZipcodes;
  }
}
