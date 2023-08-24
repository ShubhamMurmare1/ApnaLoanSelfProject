package org.bitlogic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class User 
{
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
	
private Integer userId;
private String userFirstName;
private String userLastName;
private String userDesignation;
private String userUserName;
private String userPassword;
private String userEmail;

@OneToOne(cascade = CascadeType.ALL)
private UserDocuments userDocs=new UserDocuments();

}
