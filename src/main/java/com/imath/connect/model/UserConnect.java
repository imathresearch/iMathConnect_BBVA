package com.imath.connect.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class UserConnect implements Serializable {

    // The ID. We use UUIDs
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String UUID;
    
    @Size(min = 4, max = 25, message = "4 to 25 letters")
    @Pattern(regexp = "[A-Za-z]*", message = "Only letters")
    @Column(name = "username", unique=true)
    private String userName;
    
    @Size(min = 9, max = 15, message = "9-15 Numbers")
    @Digits(fraction = 0, integer = 15, message = "Not valid")
    private String phone1;
    
    @Size(min = 9, max = 15, message = "9-15 Numbers")
    @Digits(fraction = 0, integer = 15, message = "Not valid")
    private String phone2;
    
    // The TIMESTAMP of the last connection
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentConnection;
    
    // The TIMESTAMP of the last connection
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastConnection;
    
    // The TIMESTAMP of the last connection
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date creationDate;
    
    @NotNull
    @NotEmpty
    @Email(message = "Invalid format")
    @Column(unique = true)
    private String eMail;
    
    @NotNull
    private String organization;
    
    @ManyToMany(mappedBy="collaborators")
    private Set<Project> projects;
    
    @Column(name = "photo", unique = false, nullable = true, length = 10000000)
    private byte[] photo;
    
    @OneToOne(mappedBy="user", cascade=CascadeType.ALL)
    private UserAccess access;
    
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUUID() {
        return this.UUID;
    }
    
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public Date getLastConnection() {
        return this.lastConnection;
    }
    
    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }
    
    public Date getCurrentConnection() {
        return this.currentConnection;
    }
    
    public void setCurrentConnection(Date currentConnection) {
        this.currentConnection = currentConnection;
    }
    
    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    
    public UserAccess getUserAccess(){
    	return access;
    }
    
    public void setUserAccess(UserAccess access){
    	this.access = access;
    	
    }
    private static final long serialVersionUID = 1L;
}
