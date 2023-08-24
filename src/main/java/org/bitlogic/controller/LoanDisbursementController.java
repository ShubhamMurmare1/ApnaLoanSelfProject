package org.bitlogic.controller;

import java.io.ByteArrayInputStream;

import org.bitlogic.model.Customer;
import org.bitlogic.model.LoanDisbursement;
import org.bitlogic.service.LoanDisbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

@CrossOrigin("*")

@RestController
public class LoanDisbursementController 
{

	@Autowired
	LoanDisbursementService ls;
	
	//Get API Loan Disbursement for CM => http://localhost:1997/saveLoanDisburse/{cid}
		@PostMapping("/saveLoanDisburse/{cid}")
		public ResponseEntity<Customer> saveLoanDisburse(@RequestBody LoanDisbursement ld,@PathVariable int cid){
			return new ResponseEntity<Customer>(ls.saveLoanDisburse(ld,cid),HttpStatus.OK);
		}
		
		//Get API Loan disbursed pdf for CM => http://localhost:1997/getLoanDisbursePdf/{cid}
		@GetMapping("/getLoanDisbursePdf/{cid}")
		public ResponseEntity<InputStreamResource> getLoanDisbursePdf(@PathVariable int cid) throws DocumentException{
			
			ByteArrayInputStream pdfArray = ls.createLoanDisbursePdf(cid);
			
			HttpHeaders headers=new HttpHeaders();
			headers.add("Content-Disposition", "Inline;filename=mypdf.pdf");
			
			return ResponseEntity.ok()
					.headers(headers)
					.contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(pdfArray));
		}
		
}
