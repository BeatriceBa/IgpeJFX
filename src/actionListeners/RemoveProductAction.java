package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import manager.Menu;


public class RemoveProductAction implements ActionListener {

	Menu menu;
	
	public RemoveProductAction(Menu menu) {
		this.menu = menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = " ";
		cmd = JOptionPane.showInputDialog("Insert product ID : ");
		
		//If i did not click cancel
		if (cmd != null)
			//Try&Catch needed to check if the id inserted can be converted to int
			try {
				if ( !(cmd.equals("")) && menu.getStorage().deleteFromProduct(Integer.parseInt(cmd))) {
					menu.deleteProduct(Integer.parseInt(cmd));
					JOptionPane.showMessageDialog(null, "Product has been deleted succesfully", "Deleted", JOptionPane.INFORMATION_MESSAGE );
				}
				else
					JOptionPane.showMessageDialog(null, "Product has not been deleted", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		    catch(NumberFormatException exc ) {
				JOptionPane.showMessageDialog(null, "Deletion failed. (Did you insert a numeric ID)", "Error", JOptionPane.ERROR_MESSAGE);
		    }	
	}

}
