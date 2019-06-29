package graphic;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.itextpdf.awt.geom.Dimension;

public abstract class InfoPopup {
	
	JTextField textField1 = new JTextField(20);
	
	JOptionPane tmpPanel = new JOptionPane();
	JDialog dialog = new JDialog();
	
	BoxLayout b = new BoxLayout(tmpPanel, BoxLayout.PAGE_AXIS);
	
	int confirm = 0;
	
	public int getConfirm () {
		return confirm;
	}
}
