package org.bitlogic.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.bitlogic.model.Customer;
import org.bitlogic.model.SanctionLetter;

import com.lowagie.text.DocumentException;

public interface SanctionLetterService {

	public List<Customer> getVerifiedCustomers(String status);

	public Customer addSanctionData(SanctionLetter san, int cid);

	public ByteArrayInputStream getSanctionLetterPdf(int cid) throws DocumentException;

}
