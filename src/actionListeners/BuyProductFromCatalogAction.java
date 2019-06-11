package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import extras.Mail;
import extras.PdfGenerator;
import manager.Menu;
import products.Product;
import products.Sale;

public class BuyProductFromCatalogAction implements ActionListener {

	Menu menu;
	Product product;
	
	public BuyProductFromCatalogAction(Menu menu, Product product) {
		this.menu = menu;
		this.product = product;
	}
	
	@Override
	public void actionPerformed(ActionEvent e ) {
		String cmd = "";
		cmd = JOptionPane.showInputDialog("Insert customer's email : ");
		
		if( cmd != null ) {
			if ( ! (cmd.equals(""))  ) {
				menu.getStorage().insertSale(product.getId(), cmd);
				menu.buyProduct(product.getId());
				//LE CHECKBOX SONO IN INFOPOPUP, DA METTERE!!!!!!
//				Sale sale = menu.getStorage().getSell(ip.getID());
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
				
				JOptionPane.showMessageDialog(null, "Sale was successful", "Sold", JOptionPane.INFORMATION_MESSAGE);
			}	
			else 
				JOptionPane.showMessageDialog(null, "Sale failed. Did you insert customer's email?");
		}
		

	}
	
	
	

}
