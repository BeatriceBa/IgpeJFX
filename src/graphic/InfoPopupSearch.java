package graphic;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class InfoPopupSearch extends InfoPopup {

	JMenuBar menuBar = new JMenuBar();
	JMenu searchFor = new JMenu("Search for: ");
	
	JMenuItem price = new JMenuItem("Price");
	JMenuItem category = new JMenuItem("Category");
	JMenuItem model = new JMenuItem("Model");
	JMenuItem id = new JMenuItem("Id");
	
	JLabel label = new JLabel("   ");
	
	String selectedResearch = "";

	MenuBarCategory mbc = new MenuBarCategory();
	
	public InfoPopupSearch() {
		tmpPanel.setLayout(b);
				
        searchFor.add(category); 
    	searchFor.add(price); 
    	searchFor.add(model); 
    	searchFor.add(id); 
        
    	setSearch();
    		
        menuBar.add(searchFor); 

        menuBar.setMinimumSize(new Dimension(500,20));
        menuBar.setMaximumSize(new Dimension(500,20));
        
        mbc.setMinimumSize(new Dimension(500,20));
        mbc.setMaximumSize(new Dimension(500,20));
        
        tmpPanel.add(menuBar);

        tmpPanel.add(label);
		tmpPanel.add(textField1);
		
		int result = JOptionPane.showConfirmDialog(null, tmpPanel, "Search", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			confirm = 1;
		} else {
			confirm = 0;
		}
		

	}
	
	public void setSearch() {
		category.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				selectedResearch = "category";
				label.setText("Category : ");
				searchFor.setText("Category");
				tmpPanel.remove(textField1);
				tmpPanel.add(mbc);
				tmpPanel.revalidate();
				tmpPanel.repaint();
			}
		});
		price.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				tmpPanel.remove(mbc);
				tmpPanel.add(textField1);
				selectedResearch = "price";
				label.setText("Price : ");
				searchFor.setText("Price");
				tmpPanel.revalidate();
				tmpPanel.repaint();
			}
		});
		model.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				tmpPanel.remove(mbc);
				tmpPanel.add(textField1);
				selectedResearch = "model";
				label.setText("Model : ");
				searchFor.setText("Model");
				tmpPanel.revalidate();
				tmpPanel.repaint();
			}
		});
		id.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				tmpPanel.remove(mbc);
				tmpPanel.add(textField1);
				selectedResearch = "id";
				label.setText("Id : ");
				searchFor.setText("Id");
				tmpPanel.revalidate();
				tmpPanel.repaint();
			}
		});
	}
	
	public String getSelectedResearch() {
		return selectedResearch;
	}
	
	public double getPrice() {
		//0.0 is considered as a not valid price
		if (!textField1.getText().equals(null)) 
			try {
				if( Double.parseDouble(textField1.getText()) > 0 )
					return Double.parseDouble(textField1.getText());
			}
			catch (NumberFormatException exc) {
				return 0.0;
			}
		return 0.0;	
	}
	
	public int getId() {
		if (!textField1.getText().equals(null)) 
			try {
				if( Integer.parseInt(textField1.getText()) > 0 )
					return Integer.parseInt(textField1.getText());
			}
			catch (NumberFormatException exc) {
				return 0;
			}
		return 0;
	}
	
	public String getString() {
		if (selectedResearch.equals("category"))
			return mbc.getCategory();
		return textField1.getText();
	}
}
