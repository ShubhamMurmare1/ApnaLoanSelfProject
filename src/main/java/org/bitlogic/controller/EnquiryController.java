package org.bitlogic.controller;

import java.util.List;

import org.bitlogic.enums.EnquiryStatus;
import org.bitlogic.model.EnquiryForm;
import org.bitlogic.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")

@RestController
public class EnquiryController 
{
@Autowired
EnquiryService es;

//Post API for user=> http://localhost:1997/addEnquiry
	@PostMapping("/addEnquiry")
	public ResponseEntity<EnquiryForm> addEnquiry(@RequestBody EnquiryForm e) {
		
		return new ResponseEntity<EnquiryForm>(es.addEnquiry(e),HttpStatus.CREATED);
	}
	
	//Get API for RE => http://localhost:1997/getEnquiries
	@GetMapping("/getEnquiries")
	public ResponseEntity<List<EnquiryForm>> getEnquiries(){
		return new ResponseEntity<List<EnquiryForm>>(es.getEnquiries(),HttpStatus.OK);
	}
	
	//Get API for OE => http://localhost:1997/getCibilReqEnquiries
	@GetMapping("/getCibilReqEnquiries")
	public ResponseEntity<List<EnquiryForm>> getNewEnquiries() {
		return new ResponseEntity<List<EnquiryForm>>(es.getNewEnquiries(String.valueOf(EnquiryStatus.Cibil_REQUIRED)),HttpStatus.OK);
	}
	
	//Get API Request Cibil for RE => http://localhost:1997/requestCibil/{eid}
	@GetMapping("/requestCibil/{eid}")
	public ResponseEntity<EnquiryForm> requestCibil(@PathVariable int eid) {
		return new ResponseEntity<EnquiryForm>(es.requestCibil(eid),HttpStatus.OK);
	}
	
	//Get API Cibil Score update for OE => http://localhost:1997/cibilScoreUpdate/{eid}
	@GetMapping("/cibilScoreUpdate/{eid}")
	public ResponseEntity<EnquiryForm> cibilScoreUpdate(@PathVariable int eid){
		return new ResponseEntity<EnquiryForm>(es.cibilScoreUpdate(eid),HttpStatus.OK);
	}

}
