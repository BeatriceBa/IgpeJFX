package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		if( ip.getConfirm() == 1 ) {
			for (int i=0; i<menu.getCart().size(); i++) {
				if (menu.getStorage().insertSale(menu.getCart().get(i).getId(), ip.getCustomer()))
					menu.buyProduct(menu.getCart().get(i).getId());
				else {
					JOptionPane.showMessageDialog(null, "Sale failed. Did you insert customer's email?");
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "Sale was successful", "Sold", JOptionPane.INFORMATION_MESSAGE);
			menu.emptyCart();
			
//			if ( menu.getStorage().insertSale(product.getId(), ip.getCustomer())  ) {
//				menu.buyProduct(product.getId());
//				Sale sale = menu.getStorage().getSell(product.getId());
//				if(ip.getResult().equals("mail")) {
//					PdfGenerator pdfg = new PdfGenerator(sale);
//					Mail mail = new Mail();
//					String receiver = sale.getCustomer();
//					mail.mailWithAttachment("beatricebaldassarre86@gmail.com","lisistrata1998",receiver,"Subject","./receipts/"+sale.getId()+"ID.pdf");
//				    //mail.addAttachment("beatricebaldassarre86@gmail.com","lisistrata1998",receiver,"oooooooooooooo","Plz funziona");  
//				}
//				else if(ip.getResult().equals("receipt")) {
//					PdfGenerator pdfg = new PdfGenerator(sale);
//				}
//				
//				JOptionPane.showMessageDialog(null, "Sale was successful", "Sold", JOptionPane.INFORMATION_MESSAGE);
//			}	
//			else 
//				JOptionPane.showMessageDialog(null, "Sale failed. Did you insert customer's email?");
		}
		
	}

}
