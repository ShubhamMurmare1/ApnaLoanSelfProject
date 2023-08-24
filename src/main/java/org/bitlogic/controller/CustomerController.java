package org.bitlogic.controller;

import java.io.IOException;
import java.util.List;

import org.bitlogic.model.Customer;
import org.bitlogic.model.CustomerVerification;
import org.bitlogic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
public class CustomerController
{

	@Autowired
	CustomerService cs;

	//Post API for RE => http://localhost:1997/saveCustomer
	@PostMapping(value = "/saveCustomer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

	public ResponseEntity<Customer> saveCustomer(
		@RequestParam(value="addressProof", required = false) MultipartFile file1, 
		@RequestParam(value="panCard" , required = false) MultipartFile file2, 
		@RequestParam("incomeTax") MultipartFile file3,
		@RequestParam("addharCard") MultipartFile file4,
		@RequestParam("photo") MultipartFile file5,
		@RequestParam("signature") MultipartFile file6,
		@RequestParam("mortgagePropertyProof") MultipartFile file7,
		@RequestParam("professionsalaryslips") MultipartFile file8,
		@RequestParam("propertyDocuments") MultipartFile file9,
		@RequestParam("data") String json) throws IOException

{
		
		ObjectMapper om=new ObjectMapper();
		Customer c=om.readValue(json, Customer.class);
		
		
		return new ResponseEntity<Customer> (cs.saveCustomer(c), HttpStatus.CREATED);
		
	}
	
	// Get API for OE => http://localhost:9090/getCustomers
		@GetMapping("/getCustomers")
		public ResponseEntity<List<Customer>> getCustomers(){
			return new ResponseEntity<List<Customer>>(cs.getCustomers(),HttpStatus.OK);
		}
		
		// Get API for OE to getting all created customers => http://localhost:9090/getCreatedCustomers/{customerStatus} eg. Customer_CREATED
		@GetMapping("getCreatedCustomers/{customerStatus}")
		public ResponseEntity<List<Customer>> getCreatedCustomers(@PathVariable String customerStatus){
			return new ResponseEntity<List<Customer>>(cs.getCreatedCustomers(customerStatus),HttpStatus.OK);
		}
		
		// Get API single customer for OE Document Verification => http://localhost:9090/getCustomer/{cid}
		@GetMapping("/getCustomer/{cid}")
		public ResponseEntity<Customer> getCustomer(@PathVariable int cid){
			return new ResponseEntity<Customer>(cs.getCustomer(cid),HttpStatus.OK);
		}
		
		// Put API customer verification for OE => http://localhost:9090/customerDocVerification/{cid}
		
		@PutMapping("/customerDocVerification/{cid}")
		public ResponseEntity<Customer> customerDocVerification(@RequestBody CustomerVerification cv,@PathVariable int cid){
			return new ResponseEntity<Customer>(cs.customerDocVerification(cv,cid), HttpStatus.OK);
		}
		

}
