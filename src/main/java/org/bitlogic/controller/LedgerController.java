package org.bitlogic.controller;

import java.util.List;

import org.bitlogic.model.Ledger;
import org.bitlogic.service.LedgerService;
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
public class LedgerController 
{
@Autowired
LedgerService ls;

//Post API for Ledger=> http://localhost:1997/addLedger
	@PostMapping("/addLedger")
public ResponseEntity<Ledger> saveLedger(@RequestBody Ledger l)
{
	return new ResponseEntity<Ledger>(ls.addLedger(l),HttpStatus.CREATED);
		
}
	
	
//Get API for Ledger => http://localhost:1997/getLedger
		@GetMapping("/getLedger")
		public ResponseEntity<List<Ledger>> getLedger()
		{
			return new ResponseEntity<List<Ledger>>(ls.getLedger(),HttpStatus.OK);
		}
	
	// Get API for AH to create ledger => http://localhost:1997/generateLedger/{customerId} or eg. /generateLedger/1
      @GetMapping("/generateLedger/{customerId}")
      public List<Ledger> generateLedger(@PathVariable int customerId)
      {
      	List<Ledger> ledgers=	ls.createLedger(customerId);
      	
      	
      	return ledgers;
      }

}
