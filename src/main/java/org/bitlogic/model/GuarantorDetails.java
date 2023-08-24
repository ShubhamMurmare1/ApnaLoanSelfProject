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

public class GuarantorDetails 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer guarantorId;
	private String guarantorFirstName;
	private String guarantorMiddleName;
	private String guarantorLastName;
	private Long guarantorMobileNumber;
	private Long guarantorAdharCardNo;
	private String areaname;
	private String cityname;
	private String district;
	private String state;
	private Long pincode;
	private Integer houseNumber;
	private String landmark;

}
