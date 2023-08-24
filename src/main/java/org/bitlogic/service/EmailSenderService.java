package org.bitlogic.service;

public interface EmailSenderService {

public	void sendEmailWithAttachment(String fEmail, int customerId);

public void sendEmailLoanDisbursement(String fEmail, int customerId);

}
