package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import extras.Mail;
import extras.PdfGenerator;
import graphic.InfoPopupSellFromCatalog;
import manager.Menu;
import products.Product;
import products.Sale;

public class BuyCartElements implements ActionListener {

	Menu menu;
	
	public BuyCartElements(Menu menu) {
		this.menu = menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		InfoPopupSellFromCatalog ip = new InfoPopupSellFromCatalog();
		Mail mail = new Mail();
		ArrayList <Sale> sales = new ArrayList<Sale>();
		
		if( ip.getConfirm() == 1 ) {
			if (menu.getCart().size() == 0 ) {
				JOptionPane.showMessageDialog(null, "Sale failed. Are you sure the cart had any element?");
			}
				
			for (int i=0; i<menu.getCart().size(); i++) {
				int id = menu.getCart().get(i).getId();
				//If the sale can be executed, it creates a Sale object (to create the pdf)
				//and keeps the info about how many and which receipts need to be sent via mail 
				//(if the option is available).
				if (menu.getStorage().insertSale(id, ip.getCustomer())) {
					
					menu.buyProduct(id);

					Sale sale = menu.getStorage().getSell(id);
					sales.add(sale);
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Sale failed. Did you insert customer's email?");
					return;
				}
			}
			
			String filepath = "./receipts/"+sales.get(0).getId()+"ID.pdf";
			if (ip.getResult().equals("receipt")) {
				PdfGenerator pdfg = new PdfGenerator();
				pdfg.createMultipleSalePdf(sales);
			}
			else if (ip.getResult().equals("mail")) {
				PdfGenerator pdfg = new PdfGenerator();
				pdfg.createMultipleSalePdf(sales);
				mail.mailWithAttachment("beatricebaldassarre86@gmail.com","lisistrata1998",ip.getCustomer(),"Subject",filepath);
			}
			
			JOptionPane.showMessageDialog(null, "Sale was successful", "Sold", JOptionPane.INFORMATION_MESSAGE);
			menu.emptyCart();
			
		}
		
	}

}
