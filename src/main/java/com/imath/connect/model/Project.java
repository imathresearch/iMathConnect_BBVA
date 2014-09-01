package com.imath.connect.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jasypt.hibernate4.type.EncryptedStringType;


@TypeDef(
        name = "encryptedString",
        typeClass = EncryptedStringType.class,
        parameters = { @Parameter(name = "encryptorRegisteredName", value = "STRING_ENCRYPTOR")
        }
      )

public class Project implements Serializable{
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String UUID;
    
    @Pattern(regexp = "[A-Za-z_]*", message = "Only letters and _")
    
    @NotNull
    private String name;
    
    @NotNull
    private String key;
    
    @NotNull
    private String description;
    
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date creationDate;
    

}
