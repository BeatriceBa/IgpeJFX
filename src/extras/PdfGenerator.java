package extras;

//import java.awt.Image;
//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import products.Sale;

public class PdfGenerator {
	
	private String filepath = " ";
    
	private Document document = new Document();
	
	private static Sale sale = new Sale();
    
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    
	private static Paragraph dealerDetails = new Paragraph("Chiara Passarelli \n" +  "Beatrice Baldassarre \n" + "Rende, CS 87036",  smallBold);
	private Paragraph customerDetails;
	private Paragraph paymentDetails;

   
	public PdfGenerator ( Sale _sale ) {
		try {
			sale = _sale;
			filepath = "./receipts/" + sale.getId() + "ID.pdf";
			
    		customerDetails = new Paragraph("Receipt details:\n Date: " + sale.getDate() + "\nCustomer: " + sale.getCustomer());
    		paymentDetails = new Paragraph ("Price: " + sale.getPrice());
			
    		PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.open();
            
			addTitlePage();
			addCustomerInfo();
			createTable();
			addPaymentInfo();
			addEndPage();
            
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
        }
    }
	
	public static void main (String[] args) {
		Sale esse = new Sale();
		PdfGenerator provazza = new PdfGenerator(esse);
	}

	private void addTitlePage() throws DocumentException, FileNotFoundException {
		Paragraph preface = new Paragraph();
        
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Receipt", catFont));
		addEmptyLine(preface, 1);
 
        //As the dealer details are a class field, they just need to be added
		dealerDetails.setAlignment(Element.ALIGN_LEFT);
		preface.add(dealerDetails);
		addEmptyLine(preface, 1);
		
		document.add(preface);
	}

	private void addCustomerInfo() throws DocumentException, FileNotFoundException {
		Paragraph paragraph = new Paragraph();
 
        //As the dealer details are a class field, they just need to be added
		customerDetails.setAlignment(Element.ALIGN_RIGHT);
		paragraph.add(customerDetails);
		//addEmptyLine(paragraph, 1);
		
		addEmptyLine(paragraph,2);
		document.add(paragraph);
	}
	
	private void addPaymentInfo() throws DocumentException, FileNotFoundException {
		
		Paragraph paragraph = new Paragraph();
		addEmptyLine(paragraph, 2);
        //As the dealer details are a class field, they just need to be added
		paymentDetails.setAlignment(Element.ALIGN_RIGHT);
		paragraph.add(paymentDetails);
		//addEmptyLine(paragraph, 1);
		
		addEmptyLine(paragraph,15);
		document.add(paragraph);
	}
	
	
	private void createTable () throws DocumentException, FileNotFoundException {

        PdfPTable table = new PdfPTable(3); // 3 columns.

        PdfPCell c1 = new PdfPCell(new Phrase("Product ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Category"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Model"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell(Integer.toString(sale.getId()));
        table.addCell(sale.getCategory());
        table.addCell(sale.getModel()); 

        document.add(table);
        
        
	}
	
	public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
	    Image img = Image.getInstance(path);
	    PdfPCell cell = new PdfPCell(img, true);
	    cell.setBorder(Rectangle.NO_BORDER);
	    return cell;
	}
	
	public static PdfPCell createTextCell(String text) throws DocumentException, IOException {
	    PdfPCell cell = new PdfPCell();
	    Paragraph p = new Paragraph(text);
	    p.setAlignment(Element.ALIGN_RIGHT);
	    cell.addElement(p);
	    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
	    cell.setBorder(Rectangle.NO_BORDER);
	    return cell;
	}
	
	public void addEndPage() throws IOException, DocumentException {
		Paragraph paragraph = new Paragraph();
		
		String imagePath = "./pictures/Thankyou.png";
		Image img = Image.getInstance(imagePath);
		
		PdfPTable table = new PdfPTable(2);
	    table.setWidthPercentage(100);
	    table.setWidths(new int[]{1, 2});
	    table.addCell(createImageCell(imagePath));
	    table.addCell(createTextCell("TERMS & CONDITIONS \nWe do not provide any refund. \nFor any complaint we do not provide tissues."));
	    document.add(table);
		
	}
	

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
