package com.imath.connect.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.NotEmpty;
import org.jasypt.hibernate4.type.EncryptedStringType;


@TypeDef(
        name = "encryptedString",
        typeClass = EncryptedStringType.class,
        parameters = { @Parameter(name = "encryptorRegisteredName", value = "STRING_ENCRYPTOR")
        }
      )

@Entity
public class Project implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String UUID;
    
    @Pattern(regexp = "[A-Za-z_]*", message = "Only letters and _")
    @NotNull
    @NotEmpty
    private String name;
    
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 200, message = "2-200")
    @Type(type = "encryptedString")
    private String key;
    
    @NotNull
    private String description;
    
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date creationDate;
    
    @ManyToOne(optional=false) 
    @JoinColumn(name="owner", nullable=false, updatable=true)
    private UserConnect owner;
    
    @JoinTable(name="collaborators")
    @ManyToMany   
    private Set<UserConnect> collaborators;
    
    @ManyToOne(optional=false) 
    @JoinColumn(name="instance", nullable=false, updatable=true)
    private Instance instance;
    
    public Set<UserConnect> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(Set<UserConnect> collaborators) {
        this.collaborators = collaborators;
    }

    public UserConnect getOwner() {
        return owner;
    }

    public void setOwner(UserConnect owner) {
        this.owner = owner;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String uUID) {
        UUID = uUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

}
