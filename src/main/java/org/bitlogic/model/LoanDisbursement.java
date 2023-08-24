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

public class LoanDisbursement 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer agreementId;
	private Integer loanNo;
	@CreationTimestamp
	private Date agreementDate;
	private String modeOfPayment;
	private String bankName;
	private String bankBranchName;
	private String accountHolderFirstName;
	private String accountHolderMiddleName;
	private String accountHolderLastName;
	private Long accountNumber;
	private String ifscCode;
	private String accountType;
	private Double transferAmount;
	@CreationTimestamp
	private Date amountPaidDate;
	@Lob
	private byte[] loanDisbursePdf;
}
