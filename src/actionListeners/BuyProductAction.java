package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import extras.Mail;
import extras.PdfGenerator;
import graphic.InfoPopupSell;
import manager.Menu;
import products.Sale;

public class BuyProductAction implements ActionListener {

	Menu menu;
	
	public BuyProductAction(Menu menu) {
		this.menu = menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e ) {
		InfoPopupSell ip = new InfoPopupSell();
		
		if (ip.getConfirm() == 1) {
			if (menu.getStorage().insertSale(ip.getID(), ip.getCustomer())) {
				Sale sale = menu.getStorage().getSell(ip.getID());
				if(ip.getResult().equals("mail")) {
					PdfGenerator pdfg = new PdfGenerator(sale);
					Mail mail = new Mail();
					String receiver = sale.getCustomer();
					mail.mailWithAttachment("beatricebaldassarre86@gmail.com","lisistrata1998",receiver,"Subject","./receipts/"+sale.getId()+"ID.pdf");
				    //mail.addAttachment("beatricebaldassarre86@gmail.com","lisistrata1998",receiver,"oooooooooooooo","Plz funziona");  
				}
				else if(ip.getResult().equals("receipt")) {
					PdfGenerator pdfg = new PdfGenerator(sale);
				}
				JOptionPane.showMessageDialog(null, "Sale was successful", "Sold", JOptionPane.INFORMATION_MESSAGE);
				menu.buyProduct(ip.getID());			
			}
			else {
				JOptionPane.showMessageDialog(null, "Sale failed. (" + ip.getCustomer() + " are you sure the product existed?) ", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
