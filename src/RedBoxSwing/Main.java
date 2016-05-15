package RedBoxSwing;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		new LoginFrame();
		/* // ### SWING UI testing ###
		//new LoginFrame();
		//new CheckoutFrame("Thunderbolt 3", 6.40);
		new RedBoxFrame();
		*/
		/*//### JDBC testing ###
		databaseConnection jdbcCon = new databaseConnection("", "root", "127.0.0.1", "3306", "redbox");
		
		try {
			dbModel dbM = new dbModel(jdbcCon);
			dbM.getDBMovieArray();
			//dbM.query("Account", "balance", "where Email='jill@yahoo.com'");
			//dbM.updateAvailable_Copies(2, false);
			//dbM.updateBalance("jill@yahoo.com", 2);
			//dbM.query("Account", "balance", "where Email='jill@yahoo.com'");
			//dbM.query("Movie", "available_copies", "where MovieID=2");
			//dbM.query("Account", "Email", "where balance=0.00");
			//dbM.insertRental(1, "jill@yahoo.com", "2014-11-11"); //"curdate()"
			//dbM.query("Rental", "RentalID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
	}
}
