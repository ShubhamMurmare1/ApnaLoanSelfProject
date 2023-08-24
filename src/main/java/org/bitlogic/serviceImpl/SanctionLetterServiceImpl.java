package org.bitlogic.serviceImpl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.bitlogic.enums.EnquiryStatus;
import org.bitlogic.model.Customer;
import org.bitlogic.model.SanctionLetter;
import org.bitlogic.repository.CustomerRepository;
import org.bitlogic.service.SanctionLetterService;
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
public class SanctionLetterServiceImpl implements SanctionLetterService {

	@Autowired
	CustomerRepository cr;

	@Override
	public List<Customer> getVerifiedCustomers(String status) {

		return cr.findAllByCustomerStatus(status);
	}

	@Override
	public Customer addSanctionData(SanctionLetter san, int cid) {

		Optional<Customer> op = cr.findById(cid);
		Customer c = op.get();

		// EMI Calculation
		// Principal or Loan Sanctioned Amount = P, Rate of Interest = R (R =
		// 8.75/12/100 = 0.00729166666666667)
		// Repayment Period (Total no. of months Or No. of installments) = N

		// Formula => [P*R*(1+R)^N]/[(1+R)^N-1]

		double p = san.getLoanAmtSanctioned(); // Principal
		float rate = san.getRateOfInterest(); // Rate of Interest
		float r = rate / 12 / 100; // ROI for EMIs
		int years = san.getLoanTenureInYears(); // tenure in years
		int n = years * 12; // tenure in months

		double emi = (p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1); // EMI calculation formula
		// System.out.println(emi);

		double totalAmt = emi * n;
		double totalInt = totalAmt - p;

		san.setMonthlyEmiAmount((double) Math.round(emi));
		san.setTotalAmountWithInterest((double) Math.round(totalAmt));
		san.setTotalInterest((double) Math.round(totalInt));

		c.setCustomerStatus(String.valueOf(EnquiryStatus.File_SANCTIONED));
		c.setSanctionletter(san);

		return cr.save(c);
	}

	@Override
	public ByteArrayInputStream getSanctionLetterPdf(int cid) throws DocumentException 
	{

		Optional<Customer> opsan1 = cr.findById(cid);

		Customer customer = opsan1.get();

		String title = "Loan Sanction Letter";
		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		String formatedDate = dateFormat.format(date);

		String to = "Date: " + formatedDate + "\n " + "To: "+customer.getCustomerFirstName()+" "+customer.getSanctionletter().getApplicantMiddleName()+" "+customer.getCustomerLastName();

		String sub = "Subject: Sanction Letter Approved";
		
		String dear = "Dear "+customer.getSanctionletter().getApplicantFirstName();
		
		String body = "Congratulations! Apna Loan Bank is Happy to informed you that your loan application has been approved.";
		
		String tableTitle="Loan Sanction Details";
		
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

		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
		//titleFont.setColor(CMYKColor.red);
		Paragraph titlePara = new Paragraph(title, titleFont);
		titlePara.setAlignment(Element.ALIGN_CENTER);
		document.add(titlePara);

		// -----------To---------------
		Font toFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		Paragraph toPara = new Paragraph(to, toFont);
		toPara.setSpacingBefore(20);
		document.add(toPara);
		
		// ----------Subject-----------
		Font subFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		Paragraph subPara = new Paragraph(sub, subFont);
		subPara.setSpacingBefore(15);
		subPara.setAlignment(Element.ALIGN_CENTER);
		document.add(subPara);
		
		// ----------dear-----------
		Font dearFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		Paragraph dearPara = new Paragraph(dear, dearFont);
		dearPara.setSpacingBefore(15);
		document.add(dearPara);
		
		// ----------body-----------
		Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA);
		Paragraph bodyPara = new Paragraph(body, bodyFont);
		bodyPara.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(bodyPara);
		
		// ----------body-----------
		Font tableTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,15);
		Paragraph tableTitlePara = new Paragraph(tableTitle, tableTitleFont);
		tableTitlePara.setSpacingBefore(15);
		tableTitlePara.setAlignment(Element.ALIGN_CENTER);
		document.add(tableTitlePara);


		// ------------1.Table For Loan Sanction Letter Details---------------

		PdfPTable table = new PdfPTable(2);
		table.setWidths(new int[] {5,5});
		table.setWidthPercentage(100F);
		table.setSpacingBefore(15);

		// -------------Cell--------
		PdfPCell headCell = new PdfPCell();
		headCell.setPadding(5);
		headCell.setPaddingLeft(10);
		Font headCellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		Font headCellFont2 = FontFactory.getFont(FontFactory.HELVETICA);

		// -------Adding Phrase--
		headCell.setPhrase(new Phrase("Sanction Id", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getSanctionletter().getSanctionId()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Date", headCellFont));
		table.addCell(headCell);
		DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
			String sanDate=dateFormat2.format(customer.getSanctionletter().getSanctionDate());
		headCell.setPhrase(new Phrase(sanDate,headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Applicant's First Name", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getSanctionletter().getApplicantFirstName(),headCellFont2));
		table.addCell(headCell);
		
		headCell.setPhrase(new Phrase("Applicant's Middle Name", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getSanctionletter().getApplicantMiddleName(),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Applicant's Last Name", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getSanctionletter().getApplicantLastName(),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Mobile Number", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getSanctionletter().getMobileNo()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Email ID", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getSanctionletter().getEmail(),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Loan Sanctioned Amount", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getSanctionletter().getLoanAmtSanctioned()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Rate Of Interest(%)", headCellFont));
		table.addCell(headCell);
		    System.out.println(String.valueOf(customer.getSanctionletter().getRateOfInterest()));
		headCell.setPhrase(new Phrase(String.valueOf(customer.getSanctionletter().getRateOfInterest()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Loan Tenure In Years", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getSanctionletter().getLoanTenureInYears()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Total Loan Amount With Interest", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getSanctionletter().getTotalAmountWithInterest()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Total Interest", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getSanctionletter().getTotalInterest()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Monthly EMI Amount", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(String.valueOf(customer.getSanctionletter().getMonthlyEmiAmount()),headCellFont2));
		table.addCell(headCell);

		headCell.setPhrase(new Phrase("Mode Of Payment", headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase(customer.getSanctionletter().getModeOfPayment(),headCellFont2));
		table.addCell(headCell);

		document.add(table);

		// -----------terms and condition Title----------
		Font termsnconditionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		Paragraph termsnconditionTitlePara = new Paragraph(termsnconditionTitle, termsnconditionTitleFont);
		termsnconditionTitlePara.setSpacingBefore(20);
		document.add(termsnconditionTitlePara);
				
		// -----------terms and conditions Points----------
		Font termsnconditionFont = FontFactory.getFont(FontFactory.HELVETICA);
		Paragraph termsnconditionPara = new Paragraph(termsncondition, termsnconditionFont);
		document.add(termsnconditionPara);
		
		// -----------Thanks And Regards----------
		Font thanksFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		Paragraph thanksPara = new Paragraph(thanksText, thanksFont);
		thanksPara.setSpacingBefore(15);
		thanksPara.setAlignment(Element.ALIGN_RIGHT);
		document.add(thanksPara);

		document.setMarginMirroringTopBottom(true);
		
		JPanel pane =new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		
		
				
		
		document.close();
		
		customer.getSanctionletter().setSanctionLetterPdf(out.toByteArray());
		cr.save(customer);

		return new ByteArrayInputStream(out.toByteArray());
	}

	
}