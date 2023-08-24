package org.bitlogic.serviceImpl;

import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.bitlogic.model.Customer;
import org.bitlogic.repository.CustomerRepository;
import org.bitlogic.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService 
{
	@Autowired
	JavaMailSender sender;
	
	@Autowired
	CustomerRepository cr;
	
	@Value("${spring.mail.username}")
	String fEmail;


	@Override
	public void sendEmailWithAttachment(String fEmail, int customerId) 
	{
		 Optional<Customer> ola= cr.findById(customerId);
			
			Customer la=ola.get();
			
			byte[] pdf=la.getSanctionletter().getSanctionLetterPdf();
			
					InputStreamSource input=new ByteArrayResource(pdf);
								
				MimeMessage m = sender.createMimeMessage();
				
				try {
					MimeMessageHelper helper=new MimeMessageHelper(m,true);
					helper.setTo(la.getCustomerEmail());
					helper.setFrom(fEmail);
					helper.setText("Congratulations "+la.getCustomerFirstName()+" "+la.getCustomerLastName());
					helper.setSubject("Loan proposal approved..!");
					
					helper.addAttachment(la.getSanctionletter().getApplicantFirstName()+
							la.getSanctionletter().getApplicantLastName()+
							"SanctionLetter.pdf", input);			
					sender.send(m);					
				}
				catch (Exception e) {
					e.printStackTrace();
				}				
	}		
	
	
	
	@Override
	public void sendEmailLoanDisbursement(String fEmail, int customerId) 
	{
		 Optional<Customer> ola= cr.findById(customerId);
			
			Customer la=ola.get();
			
			byte[] pdf=la.getLoandisbursement().getLoanDisbursePdf();
			
					InputStreamSource input=new ByteArrayResource(pdf);
					
				
				MimeMessage m = sender.createMimeMessage();
				
				try {
					MimeMessageHelper helper=new MimeMessageHelper(m,true);
					helper.setTo(la.getCustomerEmail());
					helper.setFrom(fEmail);
					helper.setText("Congratulations "+la.getCustomerFirstName()+" "+la.getCustomerLastName());
					helper.setSubject("Loan Disbursement approved..!");
					
					helper.addAttachment(la.getSanctionletter().getApplicantFirstName()+
							la.getSanctionletter().getApplicantLastName()+
							"LoanDisbursement.pdf", input);			
					sender.send(m);					
				}
				catch (Exception e) {
					e.printStackTrace();
				}				
	}	
}
