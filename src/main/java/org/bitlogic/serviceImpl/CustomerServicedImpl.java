package org.bitlogic.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bitlogic.enums.EnquiryStatus;
import org.bitlogic.model.Customer;
import org.bitlogic.model.CustomerVerification;
import org.bitlogic.model.LoanDisbursement;
import org.bitlogic.repository.CustomerRepository;
import org.bitlogic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServicedImpl implements CustomerService 
{

	@Autowired
	CustomerRepository cr;
	
	@Override
	public Customer saveCustomer(Customer c) {
		LoanDisbursement ld=new LoanDisbursement();
        c.setLoandisbursement(ld);
        
		if(c.getCbscore().getCibilScore()>=681)
		{
			c.setCustomerStatus(String.valueOf(EnquiryStatus.Customer_CREATED));
			return cr.save(c);
		}
		else
		{
			c.setCustomerStatus(String.valueOf(EnquiryStatus.Customer_NOTCREATED));
		return null;
		}
		}
	
	

	@Override
	public List<Customer> getCustomers() {

		
		return cr.findAll();
	}
	
	

	@Override
	public Customer getCustomer(int cid) {

    Optional<Customer> op=cr.findById(cid);
    if(op.isPresent())
    {
    	return op.get();
    	
    }
    else
    {
		return null;
	}
	}
	
	
	@Override
	public Customer customerDocVerification(CustomerVerification cv, int cid) {

		Optional<Customer> op=cr.findById(cid);
		 Customer c=op.get();
		 
		 c.setCustomerStatus(String.valueOf(EnquiryStatus.File_PENDING));
		
		 cv.setVerificationDate(new Date());
		 cv.setStatus(String.valueOf(EnquiryStatus.Customer_VERIFIED));
		 cv.setRemarks("NA");

		 c.setCustomerverification(cv);
		return cr.save(c);

	}

	

	@Override
	public List<Customer> getCreatedCustomers(String customerStatus) {

		return cr.findAllByCustomerStatus(customerStatus);
	}

}	

