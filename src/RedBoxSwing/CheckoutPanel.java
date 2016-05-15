package RedBoxSwing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckoutPanel extends JPanel {
	private JLabel emailLabel;
	private JButton confirmBtn;
	private JButton goBackBtn;
	private CheckoutListener checkoutListener;
	private CancelListener cancelListener;
	
	public CheckoutPanel(final String email, final int rentalId, String movieName, String moviePrice){
		Dimension dim = getPreferredSize();
		dim.width = 285; 
		setPreferredSize(dim);
		
		
		emailLabel = new JLabel("<html>Are you sure you want to rent <br>" + movieName + " for $" + moviePrice +"?</html>");
		
		confirmBtn = new JButton("Confirm");
		goBackBtn = new JButton("Go Back");
		
		goBackBtn.addActionListener(new ActionListener() {
			//@Override
			public void actionPerformed(ActionEvent e) {
				
				CancelEvent ev = new CancelEvent(this);
				
				if(cancelListener != null) {
					//System.out.println("here 1");
					cancelListener.cancelEventOccurred(ev);
				}
			}	
		});
		
		confirmBtn.addActionListener(new ActionListener() {
			//@Override
			public void actionPerformed(ActionEvent e) {
				
				CheckoutEvent ev = new CheckoutEvent(this);
				
				if(checkoutListener != null) {
					/*
					databaseConnection jdbcCon = new databaseConnection("", "root", "127.0.0.1", "3306", "redbox");
					dbModel dbM = new dbModel(jdbcCon);
					//System.out.println("here 2");
					try {
						dbM.insertRental(rentalId, email);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					*/
					
					checkoutListener.checkoutEventOccurred(ev);
				}
			}	
		});
		
		layoutComponents();
	}
	
	public void layoutComponents() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		//////////// Field row /////////////
		gc.gridy = 1;
		
		gc.weightx = 1;
		gc.weighty = 1.0;
		
		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 5);
		add(emailLabel, gc);	
		
		//////////// Confirm Button row ///////////// 
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 1.0;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(confirmBtn, gc);	
		
		//////////// Go Back Button row ///////////// 
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 1.0;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(goBackBtn, gc);
	}
	
	public void setCheckoutListener(CheckoutListener listener){
		this.checkoutListener = listener;
	}
	
	public void setCancelListener(CancelListener listener){
		this.cancelListener = listener;
	}
}
