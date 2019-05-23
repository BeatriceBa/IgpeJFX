package graphic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarCategory extends JMenuBar {
	
	JMenu category = new JMenu("Select category: "); 

	JMenuItem cellulari = new JMenuItem("Cellulari"); 
	JMenuItem telefoniaFissa = new JMenuItem("Telefonia Fissa"); 
	JMenuItem accessoriCellulari = new JMenuItem("Accessori per Cellulari"); 
	JMenuItem computer = new JMenuItem("Computer"); 
	JMenuItem tablet = new JMenuItem("Tablet"); 
	JMenuItem eBook = new JMenuItem("EBook"); 
	JMenuItem smartHome = new JMenuItem("Smart Home"); 
	JMenuItem console = new JMenuItem("Console"); 
	JMenuItem televisori = new JMenuItem("Televisori"); 
	JMenuItem audio = new JMenuItem("Audio"); 
	JMenuItem lavatrici = new JMenuItem("Lavatrici"); 
	JMenuItem climatizzatori = new JMenuItem("Climatizzatori"); 
	JMenuItem accessoriCasa = new JMenuItem("Accessori per la Casa"); 
	JMenuItem pulizia = new JMenuItem("Pulizia"); 
	JMenuItem saluteBenessere = new JMenuItem("Salute e Benessere"); 
	String selectedCategory = "None";
	
	public MenuBarCategory() {
		super();
		
		category.add(cellulari); 
	    category.add(telefoniaFissa); 
	    category.add(accessoriCellulari); 
	    category.add(computer); 
	    category.add(tablet) ;
	    category.add(eBook); 
	    category.add(smartHome);
	    category.add(console); 
	    category.add(televisori);
	    category.add(audio);
	    category.add(lavatrici); 
	    category.add(climatizzatori); 
	    category.add(accessoriCasa) ;
	    category.add(pulizia);
	    category.add(saluteBenessere);
	    
	    this.add(category);
	    setCategory();
	}
	
	public void setCategory() {
		cellulari.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Cellulari";
		    	category.setText("Cellulari");
		    }
		});
		telefoniaFissa.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Telefonia Fissa";
		    	category.setText("Telefonia Fissa");
		    }
		});
		accessoriCellulari.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Accessori Cellulari";
		    	category.setText("Accessori Cellulari");
		    }
		});
		computer.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Computer";
		    	category.setText("Computer");
		    }
		});
		tablet.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Tablet";
		    	category.setText("Tablet");
		    }	
		});
		eBook.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "EBook";
		    	category.setText("EBook");
	    	}
		});
		smartHome.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Smart Home";
		    	category.setText("Smart Home");
			}
		});
		console.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
	    		selectedCategory = "Console";
	    		category.setText("Console");
			}
		});
		televisori.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Televisori";
		    	category.setText("Televisori");
			}
		});
		audio.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Audio";
		    	category.setText("Audio");
			}
		});
		lavatrici.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Lavatrici";
		    	category.setText("Lavatrici");
			}
		});
		climatizzatori.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Climatizzatori";
		    	category.setText("Climatizzatori");
			}
		});
		accessoriCasa.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Accessori per la Casa";
		    	category.setText("Accessori per la Casa");			
		    }
		});
		pulizia.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Pulizia";
		    	category.setText("Pulizia");
			}
		});
		saluteBenessere.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent ev) {
		    	selectedCategory = "Salute e Benessere";
		    	category.setText("Salute e Benessere");
		    }
		});
	}
	
	public String getCategory() {
		return selectedCategory;
	}
	
	
}

