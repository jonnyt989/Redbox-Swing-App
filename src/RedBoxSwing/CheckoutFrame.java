package RedBoxSwing;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JFrame;

public class CheckoutFrame extends JFrame {
	private CheckoutPanel checkoutPanel;
	
	public CheckoutFrame(final String email, int rentalId, String movieName, String moviePrice) {
		super("RedBox Checkout");
		
		checkoutPanel = new CheckoutPanel(email, rentalId, movieName, moviePrice);
		
		checkoutPanel.setCancelListener(new CancelListener() {
			public void cancelEventOccurred(CancelEvent e) {
				new RedBoxFrame(email);
				setVisible(false);
				dispose();
			}
		});
		
		checkoutPanel.setCheckoutListener(new CheckoutListener() {
			public void checkoutEventOccurred(CheckoutEvent e) {
				databaseConnection jdbcCon = new databaseConnection("", "root", "127.0.0.1", "3306", "redbox");
				dbModel dbM1 = new dbModel(jdbcCon);
				try {
					dbM1.updateAvailable_Copies(rentalId, false);
					dbM1.insertRental(rentalId, email);
					dbM1.updateBalance(email, rentalId);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				new RedBoxFrame(email);
				setVisible(false);
				dispose();
			}
		});
		
		add(checkoutPanel, BorderLayout.WEST);
		
		setSize(300,165);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}