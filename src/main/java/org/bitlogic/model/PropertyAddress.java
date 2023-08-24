package org.bitlogic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class PropertyAddress 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer propertyAddressId;
	private String areaname;
	private String cityname;
	private String district;
	private String state;
	private Long pincode;
	private String landmark;

}
