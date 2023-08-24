package org.bitlogic.controller;

import org.bitlogic.service.EmailSenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class EmailSenderController
{

	@Autowired
	EmailSenderService ess;

@Value("${spring.mail.username}")
String fEmail;	


//Post API Sanction letter send for CM => http://localhost:1997/emailsendsanctionletter/{customerId}

@GetMapping(value="/emailsendsanctionletter/{customerId}")
	public String sendEmailWithAttachment(@PathVariable ("customerId") int customerId)
	{
		ess.sendEmailWithAttachment(fEmail,customerId);
		
		return "Email Sent Successfully";
		
	}


//Post API Loan Disbursement letter send for CM => http://localhost:1997/emailsendsanctionletter/{customerId}

@GetMapping(value="/emailsendloandisbursement/{customerId}")
	public String sendEmailLoanDisbursement(@PathVariable("customerId") int customerId) 
	{  	   	
    
   		ess.sendEmailLoanDisbursement(fEmail,customerId);
   	return "Email Send Successfully";
	}	
}

