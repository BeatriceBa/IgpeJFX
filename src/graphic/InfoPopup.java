package graphic;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class InfoPopup {
	
	JTextField textField1 = new JTextField(20);
	
	JPanel tmpPanel = new JPanel();
	
	BoxLayout b = new BoxLayout(tmpPanel, BoxLayout.PAGE_AXIS);
	
	int confirm = 0;
	
	public int getConfirm () {
		return confirm;
	}
}
