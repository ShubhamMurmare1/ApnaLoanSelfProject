package org.bitlogic.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Customer 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private Integer customerId;
	private String customerFirstName;
	private String customerMiddleName;
	private String customerLastName;
	private String customerDateOfBirth;
	private Integer customerAge;
	private String customerGender;
	private String customerEmail;
	private Long customerMobileNumber;	
	private Long customerAdditionalMobileNumber;
	private Double customerTotalLoanRequired;
	private String customerStatus;	
	
	
	@OneToOne(cascade = CascadeType.ALL)	
	private EducationalInformation educationalInfo;
	
	@OneToOne(cascade = CascadeType.ALL)
private AllPersonalDocuments allpersonalDoc=new AllPersonalDocuments();
	
	@OneToOne(cascade = CascadeType.ALL)
	private FamilyDependentInforamtion familydependentInfo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerAddress customerAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	private MortgageDetails mortgageDetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Profession profession;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Cibil cbscore;
	
	@OneToOne(cascade = CascadeType.ALL)
	private AccountDetails accountdetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PropertyInfo propertyinfo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private GuarantorDetails gurantordetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerVerification customerverification;
	
	@OneToOne(cascade = CascadeType.ALL)
	private SanctionLetter sanctionletter;
	
	@OneToOne(cascade = CascadeType.ALL)
	private LoanDisbursement loandisbursement=new LoanDisbursement();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Ledger> ledger;
}
