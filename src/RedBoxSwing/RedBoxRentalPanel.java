package RedBoxSwing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class RedBoxRentalPanel extends JPanel{
	
	private JButton rentBtn;
	private RentalListener rentalListener;
	private JList rentalList;
	private HashMap<Integer,String> dbMovies;
	private String email;
	
	public RedBoxRentalPanel(String email){
		this.email = email;
		Dimension dim = getPreferredSize();
		dim.width = 300; 
		dim.height = 300;
		setPreferredSize(dim);
		
		rentalList = new JList();
		
		//Get movies from DB
		String where = "where available_copies > 0";
		
		databaseConnection jdbcCon = new databaseConnection("", "root", "127.0.0.1", "3306", "redbox");
		dbModel dbM = new dbModel(jdbcCon);
		
		try {
			dbMovies = dbM.getDBMovieArray();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//Set up list box.
		DefaultListModel rentalModel = new DefaultListModel();
		
		Set set = dbMovies.entrySet();
		Iterator iterator = set.iterator();
		int mKey = 0;
		String mValue = "";
		while(iterator.hasNext()){
			Map.Entry mentry = (Map.Entry)iterator.next();
			mKey = (int) mentry.getKey();
			mValue = (String) mentry.getValue();
			rentalModel.addElement(new Movie(mKey, mValue));
			//System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
	        //System.out.println(mentry.getValue());
		}
		
		//rentalModel.addElement(new Movie(0, "Under 18"));
		//rentalModel.addElement(new Movie(1, "18 to 65"));
		//rentalModel.addElement(new Movie(2, "65 or over"));
		rentalList.setModel(rentalModel);
		
		rentalList.setPreferredSize(new Dimension(600, 250));
		rentalList.setBorder(BorderFactory.createEtchedBorder());
		rentalList.setSelectedIndex(0);
		
		rentBtn = new JButton("Rent Movie");
		
		rentBtn.addActionListener(new ActionListener() {
			//@Override
			public void actionPerformed(ActionEvent e) {
				
				Movie movie = (Movie)rentalList.getSelectedValue();
				
				//System.out.println(empCat);
				
				RentalEvent ev = new RentalEvent(this, movie.getId());
				
				if(rentalListener != null) {
					rentalListener.rentalEventOccurred(ev);
				}
			}
		});
		
		Border innerBorder = BorderFactory.createTitledBorder("Available Rentals");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		layoutComponents();
	}
	
	public void layoutComponents() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		////////////Rental row ///////////// 
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(rentalList, gc);	
		
		//////////// Button row ///////////// 
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 1.0;
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(rentBtn, gc);	
	}
	
	public void setRentalListener(RentalListener listener){
		this.rentalListener = listener;
	}
}

class Movie {
	private int id;
	private String text;
	
	public Movie(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public String toString() {
		return text;
	}
	
	public int getId() {
		return id;
	}
}