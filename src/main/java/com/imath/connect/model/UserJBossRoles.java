package com.imath.connect.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
public class UserJBossRoles implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Size(min = 4, max = 25, message = "4 to 25 letters")
    @Pattern(regexp = "[A-Za-z]*", message = "Only letters")
    @Column(name = "username")
    private String userName;
	
	@Id
    @Column(name = "role")
    private String role;
    
    public void setUsername(String userName){
		this.userName = userName;
	}
	
	public void setRole(String role){
		this.role = role;
	}
	
	public String getUsername(){
		return this.userName;
	}
	
	public String getRole(){
		return this.role;
	}

}
