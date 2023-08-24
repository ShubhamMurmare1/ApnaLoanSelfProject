package org.bitlogic.serviceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bitlogic.enums.EnquiryStatus;
import org.bitlogic.model.Customer;
import org.bitlogic.model.Ledger;
import org.bitlogic.model.SanctionLetter;
import org.bitlogic.repository.CustomerRepository;
import org.bitlogic.repository.LedgerRepository;
import org.bitlogic.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LedgerServiceImpl implements LedgerService
{

	@Autowired
    LedgerRepository lr;	
	
	@Autowired
     CustomerRepository cr;

	
	@Override
	public Ledger addLedger(Ledger l) 
	{
		return lr.save(l);
	}

	
	@Override
	public List<Ledger> getLedger() 
	{
		return lr.findAll();
	}

	
	
	@Override
	public List<Ledger> createLedger(int customerId) 
	{
	
		Optional<Customer> oc=cr.findById(customerId);
		
		if(oc.isPresent())
		{
			Customer c=oc.get();
			SanctionLetter sl=c.getSanctionletter();
			
			for(int i=0; i< sl.getLoanTenureInYears()*12; i++)
			{
				if(c.getLedger().size()<=0)
				{
					Ledger firstEmi=new Ledger();
					firstEmi.setLedgerId(String.valueOf(c.getCustomerId())+"EmiNo-"+i);
					firstEmi.setTotalLoanAmount(sl.getLoanAmtSanctioned());
					firstEmi.setPayableAmountwithInterest(sl.getTotalAmountWithInterest());
        			firstEmi.setLoanTenureInYears(sl.getLoanTenureInYears());
        			firstEmi.setMonthlyEMI(sl.getMonthlyEmiAmount());
    
        			firstEmi.setRemainingAmount(sl.getTotalAmountWithInterest());
        			
        			Date todayDate=new Date();
        		int month=	todayDate.getMonth();
        			int year =todayDate.getYear();
        			if(month<12) {
        		    firstEmi.setNextEmiDateStart("01/"+month+1+"/"+year);
        		    firstEmi.setNextEmiDateEnd("30/"+month+1+"/"+year);
        			}else {
        				  firstEmi.setNextEmiDateStart("01/"+1+"/"+year+1);
              		    firstEmi.setNextEmiDateEnd("30/"+1+"/"+year+1);
        			}
        			firstEmi.setDefaulterCount(0);
        			DateFormat format=new SimpleDateFormat("dd-MM-YYYY");
        			     
        			firstEmi.setLoanEmiStartDate(format.format(todayDate));
        			String startDate=format.format(todayDate);
        			int lenth=startDate.length();
            char ch=			startDate.charAt(lenth-1);
             Integer lastEmiChar=(ch-'0')+sl.getLoanTenureInYears();
     			firstEmi.setLoanEmiEndDate(startDate.replace(ch, lastEmiChar.toString().charAt((0))));
     			firstEmi.setNoOfEmisPaid(0);
     			firstEmi.setTotalNoOfEmi(sl.getLoanTenureInYears()*12);
     			firstEmi.setRemainingEmi(sl.getLoanTenureInYears()*12);
     			c.getLedger().add(firstEmi);
     			
     			}
        		 else {
        			 
        			   List<Ledger> ledgerList = c.getLedger().stream().collect(Collectors.toList());
        			                 Ledger lastEmi = ledgerList.get(i-1);
        			                 
        			                 
        			                 Ledger newEmi=new Ledger();
        			                 newEmi.setLedgerId(String.valueOf(c.getCustomerId())+"EmiNo-"+i);
        		            	
        			                 newEmi.setTotalLoanAmount(sl.getLoanAmtSanctioned());
        			                 newEmi.setPayableAmountwithInterest(sl.getTotalAmountWithInterest());
        			                 newEmi.setLoanTenureInYears(sl.getLoanTenureInYears());
        			                 newEmi.setMonthlyEMI(sl.getMonthlyEmiAmount());
        		            	
        			                 newEmi.setRemainingAmount(lastEmi.getRemainingAmount()-sl.getMonthlyEmiAmount());
        			                 
        			                 
        			                 String lastEmiSartDate=lastEmi.getLoanEmiEndDate();
        			                  DateFormat dateFormat=new SimpleDateFormat("dd-MM-YYYY");
        			                    Date lastDateEnd;
										try {
											System.out.println(lastEmiSartDate);
											lastDateEnd = (Date) dateFormat.parse(lastEmiSartDate);
										     int month=  lastDateEnd.getMonth();
	            			                  int year=lastDateEnd.getYear();
	            			          		if(month<11) {
	            			          			newEmi.setNextEmiDateStart("01/"+month+1+"/"+year);
	            		            		    newEmi.setNextEmiDateEnd("30/"+month+1+"/"+year);
	            		            			}else {
	            		            				newEmi.setNextEmiDateStart("01/"+1+"/"+year+1);
	            		            				newEmi.setNextEmiDateEnd("30/"+1+"/"+year+1);
	            		            			}
	            			                 
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
        			             

        			          		newEmi.setDefaulterCount(0);
        		            			DateFormat format=new SimpleDateFormat("dd-MM-YYYY");
        		            			     
        		            			newEmi.setLoanEmiStartDate(lastEmi.getLoanEmiStartDate());
        		     
        		            			newEmi.setLoanEmiEndDate(lastEmi.getLoanEmiEndDate());
        		            			newEmi.setNoOfEmisPaid(0);
        		            			newEmi.setTotalNoOfEmi(sl.getLoanTenureInYears()*12);
        		            			newEmi.setRemainingEmi(sl.getLoanTenureInYears()*12);
        		         			c.getLedger().add(newEmi);
        		         			c.setCustomerStatus(String.valueOf(EnquiryStatus.LEDGER_CREATED));
        		         		
        			                 
        			 }
        		 
        	 }
        	 System.out.println(c.getLedger());
        	 cr.save(c);
        	 return c.getLedger() ;
         }
           
        return null;
}

}
