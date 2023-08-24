package org.bitlogic.service;

import java.io.ByteArrayInputStream;

import org.bitlogic.model.Customer;
import org.bitlogic.model.LoanDisbursement;

import com.lowagie.text.DocumentException;

public interface LoanDisbursementService 
{

	public Customer saveLoanDisburse(LoanDisbursement ld, int cid);

	public ByteArrayInputStream createLoanDisbursePdf(int cid) throws DocumentException;

}
