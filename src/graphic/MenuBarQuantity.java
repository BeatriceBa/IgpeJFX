package graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarQuantity extends JMenuBar {

	private static final long serialVersionUID = 1L;

	JMenu quantity = new JMenu("Select quantity: "); 

	JMenuItem one = new JMenuItem("1"); 
	JMenuItem two = new JMenuItem("2"); 
	JMenuItem three = new JMenuItem("3"); 
	JMenuItem four = new JMenuItem("4"); 
	JMenuItem five = new JMenuItem("5"); 
	JMenuItem six = new JMenuItem("6"); 
	JMenuItem seven = new JMenuItem("7"); 
	JMenuItem eight = new JMenuItem("8"); 
	JMenuItem nine = new JMenuItem("9"); 
	JMenuItem ten = new JMenuItem("10");
	String selectedQuantity = "0";
		
	public MenuBarQuantity() {
		super();
		
	    quantity.add(one);
	    quantity.add(two);
	    quantity.add(three);
	    quantity.add(four);
	    quantity.add(five);
	    quantity.add(six);
	    quantity.add(seven);
	    quantity.add(eight);
	    quantity.add(nine);
	    quantity.add(ten);
		    
	    this.add(quantity);
	    setQuantity();
	}
		
	public void setQuantity() {
		one.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "1";
		    	quantity.setText("1");
		    }
		});
		two.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "2";
		    	quantity.setText("2");
		    }
		});
		three.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "3";
		    	quantity.setText("3");
		    }
		});
		four.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "4";
		    	quantity.setText("4");
		    }
		});
		five.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "5";
		    	quantity.setText("5");
		    }
		});
		six.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "6";
		    	quantity.setText("6");
		    }
		});
		seven.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "7";
		    	quantity.setText("7");
		    }
		});
		eight.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "8";
		    	quantity.setText("8");
		    }
		});
		nine.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "9";
		    	quantity.setText("9");
		    }
		});
		ten.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedQuantity = "10";
		    	quantity.setText("10");
		    }
		});
	}
		
	public String getQuantity() {
		return selectedQuantity;
	}
}
