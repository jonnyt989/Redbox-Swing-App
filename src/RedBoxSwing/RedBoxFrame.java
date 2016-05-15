package RedBoxSwing;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class RedBoxFrame extends JFrame {
	private RedBoxAccountPanel accountPanel;
	private RedBoxRentalPanel rentalPanel;
	private String email;
	
	public RedBoxFrame(final String email) {
		super("RedBox");
		this.email = email;
		
		setLayout(new BorderLayout());
		
		accountPanel = new RedBoxAccountPanel(email);
		rentalPanel = new RedBoxRentalPanel(email);
		
		accountPanel.setAccountListener(new AccountListener() {
			public void accountEventOccurred(AccountEvent e) {
					
				databaseConnection jdbcCon = new databaseConnection("", "root", "127.0.0.1", "3306", "redbox");
				dbModel dbM1 = new dbModel(jdbcCon);
				
				int returnId = e.getReturnId();
				int movieId = 0;
				String movieIdString = "";
				
				String deleteWhere = "where RentalId = " + returnId;
				String updateWhere = "where RentalId = " + returnId;
				try {
					movieIdString = dbM1.query("rental", "MovieID", updateWhere);
					movieId = Integer.parseInt(movieIdString);
					dbM1.updateAvailable_Copies(movieId, true);
					dbM1.delete("rental", deleteWhere);
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				new LoginFrame();
				setVisible(false);
				dispose();
				//update DB here
				//textPanel.appendText(name + ": " + occupation + ": " + ageCat + "\n");
			}
		});
		
		rentalPanel.setRentalListener(new RentalListener() {
			public void rentalEventOccurred(RentalEvent e) {
				
				databaseConnection jdbcCon = new databaseConnection("", "root", "127.0.0.1", "3306", "redbox");
				dbModel dbM0 = new dbModel(jdbcCon);
				
				int rentalId = e.getMovie();
				String nameWhere = "where MovieId=" + rentalId;
				String rentalName = "";
				String rentalCost = "";
	
				try {
					rentalName = dbM0.query("movie", "moviename", nameWhere);
					rentalCost = dbM0.query("movie", "rental_cost", nameWhere);
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				new CheckoutFrame(email, rentalId, rentalName, rentalCost);
				setVisible(false);
				dispose();
			}
		});
		
		add(accountPanel, BorderLayout.WEST);
		add(rentalPanel, BorderLayout.EAST);
		
		setSize(700,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
