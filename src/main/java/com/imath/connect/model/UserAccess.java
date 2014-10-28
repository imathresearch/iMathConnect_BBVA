package com.imath.connect.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
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
public class UserAccess {
	
	@Id		
	@Column(name = "uuid", unique = true)
	@GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="user"))
	private String UUID;
		
    @Size(min = 2, max = 200, message = "2-200")
    @Type(type = "encryptedString")
	private String password;
	
    @NotNull
    @NotEmpty
	private String accessSource;
	
	@OneToOne
    @PrimaryKeyJoinColumn
    private UserConnect user; 
	
	public String getUUID() {
        return this.UUID;
    }
    
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
    
	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAccessSource() {
        return accessSource;
    }

    public void setAccessSource(String access) {
        this.accessSource = access;
    }
    
    public UserConnect getUser(){
    	return user;
    }
    
    public void setUser(UserConnect user){
    	this.user = user;    	
    }
    
    
	
}
