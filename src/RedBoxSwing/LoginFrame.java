package RedBoxSwing;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JFrame;

public class LoginFrame extends JFrame {
	
	private LoginPanel loginPanel;
	
	public LoginFrame() {
		super("RedBox Login");
		
		loginPanel = new LoginPanel();
		loginPanel.setLoginListener(new LoginListener() {
			public void loginEventOccurred(LoginEvent e) {
				String email = e.getEmail();	
				String where = "where Email = '" + email + "'";
				
				databaseConnection jdbcCon = new databaseConnection("", "root", "127.0.0.1", "3306", "redbox");
				dbModel dbM = new dbModel(jdbcCon);
				
				try {
					if(dbM.query("account", "balance", where) == null ||
							dbM.query("account", "balance", where) == "") {
						
						System.out.println("Email not found in DB");
						
					}
					else{
						new RedBoxFrame(email);
						setVisible(false);
						dispose();
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
					
				//check database here
				//if email exists - launch RedBox frame here, else error msg
				//textPanel.appendText(name + ": " + occupation + ": " + ageCat + "\n");
			}
		});
		
		add(loginPanel, BorderLayout.WEST);
		
		setSize(400,165);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}	
}
