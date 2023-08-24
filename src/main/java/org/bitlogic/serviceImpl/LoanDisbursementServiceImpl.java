package org.bitlogic.serviceImpl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.bitlogic.enums.EnquiryStatus;
import org.bitlogic.model.Customer;
import org.bitlogic.model.LoanDisbursement;
import org.bitlogic.repository.CustomerRepository;
import org.bitlogic.service.LoanDisbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class LoanDisbursementServiceImpl implements LoanDisbursementService
{
	@Autowired
	CustomerRepository cr;
	
	
	@Override
	public Customer saveLoanDisburse(LoanDisbursement ld,int cid) {
		
		Optional<Customer> op=cr.findById(cid);
				Customer c=op.get();
				
				ld.setLoanNo(c.getLoandisbursement().getAgreementId());
				//ld.setModeOfPayment(c.getSanctionletter().getModeOfPayment()); // need to give input by postman
				ld.setBankName(c.getAccountdetails().getBankName());
				ld.setBankBranchName(c.getAccountdetails().getBankBranchName());
				ld.setAccountHolderFirstName(c.getSanctionletter().getApplicantFirstName());
				ld.setAccountHolderMiddleName(c.getSanctionletter().getApplicantMiddleName());
				ld.setAccountHolderLastName(c.getSanctionletter().getApplicantLastName());
				ld.setAccountNumber(c.getAccountdetails().getAccountNumber());
				ld.setIfscCode(c.getAccountdetails().getAccountIfscNumber());
				ld.setAccountType(c.getAccountdetails().getAccounType());
				ld.setTransferAmount(c.getSanctionletter().getLoanAmtSanctioned());
				
//				c.getLoandisbursement().setLoanNo(c.getLoandisbursement().getAgreementId());
//				//ld.setModeOfPayment(c.getSanctionletter().getModeOfPayment()); // need to give input by postman
//				c.getLoandisbursement().setBankName(c.getAccountdetails().getBankName());
//				c.getLoandisbursement().setBankBranchName(c.getAccountdetails().getBankBranchName());
//				c.getLoandisbursement().setAccountHolderFirstName(c.getSanctionletter().getApplicantFirstName());
//				c.getLoandisbursement().setAccountHolderMiddleName(c.getSanctionletter().getApplicantMiddleName());
//				c.getLoandisbursement().setAccountHolderLastName(c.getSanctionletter().getApplicantLastName());
//				c.getLoandisbursement().setAccountNumber(c.getAccountdetails().getAccountNumber());
//				c.getLoandisbursement().setIfscCode(c.getAccountdetails().getAccountIfscNumber());
//				c.getLoandisbursement().setAccountType(c.getAccountdetails().getAccounType());
//				c.getLoandisbursement().setTransferAmount(c.getSanctionletter().getLoanAmtSanctioned());
				
				c.setCustomerStatus(String.valueOf(EnquiryStatus.LOAN_DISBURSED));
				c.setLoandisbursement(ld);
				
				cr.save(c);
				
				c.getLoandisbursement().setLoanNo(c.getLoandisbursement().getAgreementId());
		
		return cr.save(c);
	}

	@Override
	public ByteArrayInputStream createLoanDisbursePdf(int cid) throws DocumentException {
		Optional<Customer> opsan1 = cr.findById(cid);

		Customer customer = opsan1.get();

		String title = "Loan Disbursement";
		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		String formatedDate = dateFormat.format(date);

		String to = "Date: " + formatedDate + "\n " + "To: "+customer.getCustomerFirstName()+" "+customer.getCustomerMiddleName()+" "+customer.getCustomerLastName();

		String sub = "Subject: About loan disbursement";
		
		String dear = "Dear "+customer.getSanctionletter().getApplicantFirstName();
		
		String body = "Congratulations! Apna Loan Bank is Happy to informed you that your loan has been disbursed.";
		
		String tableTitle="Loan Disbursement Details";
		
		String termsnconditionTitle = "Additional condition to comply prior to disbursal:";
		
		String termsncondition =
				"1.Repayment from Apna Loan bank. \n "
				+ "2.Confirmation form Official ID and Copy of ID required. \n "
				+ "3.The Borrower will be required to reply within 21 days from date of issue of said Notice. \n "
				+ "4. Legal vetting & Search to be done.\n"
				+ "5. NOC from tenant at offered collateral.\n"
				+ "6. Positive Residential & Office CPV Initiated.\n";

		String thanksText = "Thank you,\n Regards Apna Loan!";

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Document document = new Document();

		PdfWriter.getInstance(document, out);
		document.open();

		// -------Title---------------

		Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 25);
		//titleFont.setColor(CMYKColor.red);
		Paragraph titlePara = new Paragraph(title, titleFont);
		titlePara.setAlignment(Element.ALIGN_CENTER);
		document.add(titlePara);

		// -----------To---------------
		Font toFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
		Paragraph toPara = new Paragraph(to, toFont);
		toPara.setSpacingBefore(20);
		document.add(toPara);
		
		// ----------Subject-----------
		Font subFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
		Paragraph subPara = new Paragraph(sub, subFont);
		subPara.setSpacingBefore(15);
		subPara.setAlignment(Element.ALIGN_CENTER);
		document.add(subPara);
		
		// ----------dear-----------
		Font dearFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
		Paragraph dearPara = new Paragraph(dear, dearFont);
		dearPara.setSpacingBefore(15);
		document.add(dearPara);
		
		// ----------body-----------
		Font bodyFont = FontFactory.getFont(FontFactory.TIMES_ITALIC);
		Paragraph bodyPara = new Paragraph(body, bodyFont);
		bodyPara.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		document.add(bodyPara);
		
		// ----------body-----------
		Font tableTitleFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,15);
		Paragraph tableTitlePara = new Paragraph(tableTitle, tableTitleFont);
		tableTitlePara.setSpacingBefore(15);
		tableTitlePara.setAlignment(Element.ALIGN_CENTER);
		document.add(tableTitlePara);


		// ------------1.Table For Loan Disbursement Details---------------

		PdfPTable table = new PdfPTable(2);
		table.setWidths(new int[] {5,5});
		table.setWidthPercentage(100F);
		table.setSpacingBefore(15);

		// -------------Cell--------
		PdfPCell headCell = new PdfPCell();
		headCell.setPadding(5);
		headCell.setPaddingLeft(10);
		Font headCellFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
		Font headCellFont2 = FontFactory.getFont(FontFactory.TIMES_ITALIC);

		// -------Adding Phrase--
		headCell.setPhrase(new Phrase("Loan No", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getLoandisbursement().getLoanNo()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Account Holder's First Name", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getLoandisbursement().getAccountHolderFirstName(),headCellFont2));
		table.addCell(headCell);
		
		headCell.setPhrase(new Phrase("Account Holder's Middle Name", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getLoandisbursement().getAccountHolderMiddleName(),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Account Holder's Last Name", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getLoandisbursement().getAccountHolderLastName(),headCellFont2));
		table.addCell(headCell);
		
		headCell.setPhrase(new Phrase("Agreement Date", headCellFont));
		table.addCell(headCell);
		DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
			String sanDate=dateFormat2.format(customer.getLoandisbursement().getAgreementDate());
		headCell.setPhrase(new Phrase(sanDate,headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Bank Name", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getLoandisbursement().getBankName(),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Bank Branch Name", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getLoandisbursement().getBankBranchName(),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Account Number", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getLoandisbursement().getAccountNumber()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("IFSC Code", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getLoandisbursement().getIfscCode(),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Account Type", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getLoandisbursement().getAccountType(),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Transfered Amount", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getLoandisbursement().getTransferAmount()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Amount Paid Date", headCellFont));
		table.addCell(headCell);
		DateFormat dateFormat3 = new SimpleDateFormat("dd-MM-yyyy");
			String sanDate2=dateFormat3.format(customer.getLoandisbursement().getAmountPaidDate());
		headCell.setPhrase(new Phrase(sanDate2,headCellFont2));
		table.addCell(headCell);

		document.add(table);

		// -----------terms and condition Title----------
		Font termsnconditionTitleFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
		Paragraph termsnconditionTitlePara = new Paragraph(termsnconditionTitle, termsnconditionTitleFont);
		termsnconditionTitlePara.setSpacingBefore(20);
		document.add(termsnconditionTitlePara);
				
		// -----------terms and conditions Points----------
		Font termsnconditionFont = FontFactory.getFont(FontFactory.TIMES_ITALIC);
		Paragraph termsnconditionPara = new Paragraph(termsncondition, termsnconditionFont);
		document.add(termsnconditionPara);
		
		// -----------Thanks And Regards----------
		Font thanksFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
		Paragraph thanksPara = new Paragraph(thanksText, thanksFont);
		thanksPara.setSpacingBefore(15);
		thanksPara.setAlignment(Element.ALIGN_RIGHT);
		document.add(thanksPara);

		document.setMarginMirroringTopBottom(true);
		
		JPanel pane =new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		
		
		document.close();
		
		customer.getLoandisbursement().setLoanDisbursePdf(out.toByteArray());
		cr.save(customer);

		return new ByteArrayInputStream(out.toByteArray());
	}
}