package org.bitlogic.service;

import java.util.List;

import org.bitlogic.model.EnquiryForm;

public interface EnquiryService 
{

	public EnquiryForm addEnquiry(EnquiryForm e);

	public List<EnquiryForm> getEnquiries();

	public List<EnquiryForm> getNewEnquiries(String status);

	public EnquiryForm requestCibil(int eid);

	public EnquiryForm cibilScoreUpdate(int eid);

}
