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
					PdfGenerator pdfg = new PdfGenerator();
					pdfg.createSingleSalePdf(sale);
					Mail mail = new Mail();
					String receiver = sale.getCustomer();
					mail.mailWithAttachment("---","---",receiver,"Subject","./receipts/"+sale.getId()+"ID.pdf");
				}
				else if(ip.getResult().equals("receipt")) {
					PdfGenerator pdfg = new PdfGenerator();
					pdfg.createSingleSalePdf(sale);
				}
				JOptionPane.showMessageDialog(null, "Sale was successful", "Sold", JOptionPane.INFORMATION_MESSAGE);
				menu.buyProduct(ip.getID(),"single");			
			}
			else {
				JOptionPane.showMessageDialog(null, "Sale failed.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
