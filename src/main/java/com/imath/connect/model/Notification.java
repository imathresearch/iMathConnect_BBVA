package com.imath.connect.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String UUID;
	
	@NotNull
	@NotEmpty
	@Size(min = 2, max = 200, message = "2-200")
	private String subject;
	
	@NotNull
	@NotEmpty
	@Size(min = 2, max = 500, message = "2-200")
	private String text;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	/**
	 * type=0: Public; type = 1: Private 
	 */
	@NotNull
	private Integer type;
	
    @ManyToMany   
    @JoinTable(name="notifications_user")
    private Set<UserConnect> notificationUsers;

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Set<UserConnect> getNotificationUsers() {
		return notificationUsers;
	}

	public void setNotificationUsers(Set<UserConnect> notificationUsers) {
		this.notificationUsers = notificationUsers;
	}
    
}
