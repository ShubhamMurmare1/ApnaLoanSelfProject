package org.bitlogic.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.bitlogic.model.Customer;
import org.bitlogic.model.SanctionLetter;
import org.bitlogic.service.SanctionLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

@CrossOrigin("*")

@RestController
public class SanctionLetterController 
{
	@Autowired
	SanctionLetterService sls;

	// Get API Show File_Pending status wise customers for =>
	// http://localhost:9090/getVerifiedCustomers/{status} or File_Pending
	@GetMapping("/getVerifiedCustomers/{status}")
	public ResponseEntity<List<Customer>> getVerifiedCustomers(@PathVariable String status) {
		return new ResponseEntity<List<Customer>>(sls.getVerifiedCustomers(status), HttpStatus.OK);
	}

	// Put API fill sanction details => http://localhost:9090/addSanctionData/{cid}
	@PutMapping("/addSanctionData/{cid}")
	public ResponseEntity<Customer> addSanctionData(@RequestBody SanctionLetter san, @PathVariable int cid) {
		return new ResponseEntity<Customer>(sls.addSanctionData(san, cid), HttpStatus.CREATED);
	}

	// Get API fill in sanction Letter => http://localhost:9090/getSanctionLetterPdf/{cid}
	@GetMapping("/getSanctionLetterPdf/{cid}")
	public ResponseEntity<InputStreamResource> getSanctionLetterPdf(@PathVariable int cid) throws DocumentException {

		ByteArrayInputStream pdfArray = sls.getSanctionLetterPdf(cid);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline;filename=mypdf.pdf");
		return ResponseEntity.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdfArray));
	}

}
