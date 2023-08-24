package org.bitlogic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class PropertyInfo 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer propertyId;	
	private String propertyType;
	private String propertyArea;		
	private Double propertyPrice;
	@Lob
	private byte[] propertyDocuments; // including property price proofs or noc or tax docs
	@OneToOne(cascade = CascadeType.ALL)
	private PropertyAddress propertyAddress;	

}
