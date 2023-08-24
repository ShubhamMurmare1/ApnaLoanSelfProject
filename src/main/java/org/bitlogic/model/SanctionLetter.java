package org.bitlogic.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class SanctionLetter 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer sanctionId;
	@CreationTimestamp
	private Date sanctionDate;
	private String applicantFirstName;
	private String applicantMiddleName;
	private String applicantLastName;
	private Long mobileNo;
	private String email;
	private Double loanAmtSanctioned;
	private Float rateOfInterest;
	private Integer loanTenureInYears;
	private Double totalAmountWithInterest;
	private Double totalInterest;
	private Double monthlyEmiAmount;
	private String modeOfPayment;
	@Lob
	private byte[] sanctionLetterPdf;

}
