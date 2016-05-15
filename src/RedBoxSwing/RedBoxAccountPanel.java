package RedBoxSwing;

import java.awt.Color;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class RedBoxAccountPanel extends JPanel{
	
	private JLabel balanceLabel;
	private JLabel amountLabel;
	private JList returnList;
	private JButton returnBtn;
	private AccountListener accountListener;
	private HashMap<Integer,String> dbReturns;
	private String email;
	private String balance;
	
	public RedBoxAccountPanel(String pEmail){
		this.email = pEmail;
		Dimension dim = getPreferredSize();
		dim.width = 350; 
		setPreferredSize(dim);
		
		//Setup DB connection
		databaseConnection jdbcCon = new databaseConnection("", "root", "127.0.0.1", "3306", "redbox");
		dbModel dbM = new dbModel(jdbcCon);
		
		//Get balance from DB
		String bWhere = "where Email = '" + email + "'";
		
		//Get movies from DB
		String where = "where available_copies > 0";
										
		try {
			balance = dbModel.staticQuery("account", "balance", bWhere);
			dbReturns = dbM.getDBReturnArray(email);
		} 
		catch (SQLException e1) {
					e1.printStackTrace();
		}
		balanceLabel = new JLabel("Balance: ");
		amountLabel = new JLabel("<html>$" + balance + "</html>");
		returnList = new JList();
		
		//Set up Balance list box.
		
		DefaultListModel balanceModel = new DefaultListModel();
		balanceModel.addElement(balance);
		
		//Set up Return list box.
		DefaultListModel returnModel = new DefaultListModel();
		
		Set set = dbReturns.entrySet();
		Iterator iterator = set.iterator();
		int mKey = 0;
		String mValue = "";
		while(iterator.hasNext()){
			Map.Entry mentry = (Map.Entry)iterator.next();
			mKey = (int) mentry.getKey();
			mValue = (String) mentry.getValue();
			returnModel.addElement(new MovieReturn(mKey, mValue));
			//System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
	        //System.out.println(mentry.getValue());
		}

		returnList.setModel(returnModel);
				
		returnList.setPreferredSize(new Dimension(600, 200));
		returnList.setBorder(BorderFactory.createEtchedBorder());
		returnList.setSelectedIndex(0);
		
		returnBtn = new JButton("Return Movie");
		
		returnBtn.addActionListener(new ActionListener() {
			//@Override
			public void actionPerformed(ActionEvent e) {

				MovieReturn movieReturn = (MovieReturn)returnList.getSelectedValue();
				
				AccountEvent ev = new AccountEvent(this, movieReturn.getId());
				
				if(accountListener != null) {
					accountListener.accountEventOccurred(ev);
					
					System.out.println("Movie Id of movie being returned is " + ev.getReturnId());
				}
			}
		});
		
		Border innerBorder = BorderFactory.createTitledBorder("Account");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		layoutComponents();
	}
	
	public void layoutComponents() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		//////////// Balance row /////////////
		gc.gridy = 0;
		
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(balanceLabel, gc);
		
		gc.gridx = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 5);
		add(amountLabel, gc);
		
		////////////Return row ///////////// 
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Return a Movie: "), gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(returnList, gc);
		
		////////////Button row ///////////// 
		gc.gridy++;
	
		gc.weightx = 1;
		gc.weighty = 1.0;
	
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(returnBtn, gc);	
	}	
	
	public void setAccountListener(AccountListener listener){
		this.accountListener = listener;
	}
} 

class MovieReturn {
	private int id;
	private String text;
	
	public MovieReturn(int id, String text) {
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



