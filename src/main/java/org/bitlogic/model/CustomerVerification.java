package org.bitlogic.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class CustomerVerification 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer verificationID;
	@CreationTimestamp
	private Date verificationDate;
	private String status;	
	private String remarks;
}
