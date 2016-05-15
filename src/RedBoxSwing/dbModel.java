package RedBoxSwing;

import java.sql.*;
import java.util.HashMap;

public class dbModel {
	private databaseConnection jdbcCon = null;
	private String dbName = "";
	
	
	public dbModel(databaseConnection jdbcConnection) {
		this.jdbcCon = jdbcConnection;
		this.dbName = jdbcCon.getDBName();
	}

	public String query(String tableName, String fieldName, String where) throws SQLException {
		Connection con = jdbcCon.getConnection();
		Statement stmt = null;
		String queryOutput = "";
		
		String query = "select " + fieldName +
		               " from " + dbName + "." + tableName + 
		               " " + where + ";";
		try {
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    while (rs.next()) {
		    	queryOutput = rs.getString(fieldName);   
		        //System.out.println("queryOutput = " + queryOutput);
		    }
		    } 
		catch (SQLException e) {
		    	System.out.println(e);
		} 
		finally {
			if (stmt != null) { 
				stmt.close(); 
			}
			if (con != null){
				con.close();
			}
		}
		return queryOutput;
	}
	
	public void delete(String tableName, String where) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		String sql = "delete from " + dbName + "." + tableName + 
	            " " + where + ";";
		
		try {
			con = jdbcCon.getConnection();
			stmt = con.createStatement();
			
			stmt.executeUpdate(sql);		        
		}
		catch (SQLException e) {
		    System.out.println(e);
		} 
		finally {
		    if (stmt != null) { 
		    	stmt.close(); 
		    	}
			if (con != null){
				con.close();
			}
		}
	}
	
	public void insertRental(int rentalId, String emailValue) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		String sql = "insert into " + dbName + ".Rental (MovieID, Email, duedate)" + 
	              "values (" + rentalId + ", '" + emailValue + "', CURDATE() + INTERVAL 1 DAY);";
		
		try {
			con = jdbcCon.getConnection();
			stmt = con.createStatement();
			
			stmt.executeUpdate(sql);		        
		}
		catch (SQLException e) {
		    System.out.println(e);
		} 
		finally {
		    if (stmt != null) { 
		    	stmt.close(); 
		    	}
		}
		if (con != null){
			con.close();
		}
	}
	
	public void updateAvailable_Copies(int rentalId, boolean isReturn) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		String copiesWhere = "where MovieID=" + rentalId;
		String copies = query("Movie", "available_copies", copiesWhere); 
		int intCopies = Integer.parseInt(copies);
		if (isReturn == true){
			intCopies += 1;
		}
		else{
			intCopies -= 1;		
		}
		String sql = "update " + dbName + ".Movie set available_copies=" + intCopies +
				" where MovieID=" + rentalId +";";

		
		try {
			con = jdbcCon.getConnection();
			stmt = con.createStatement();
			
			stmt.executeUpdate(sql);		        
		}
		catch (SQLException e) {
		    System.out.println(e);
		} 
		finally {
		    if (stmt != null) { 
		    	stmt.close(); 
		    	}
			if (con != null){
				con.close();
			}
		}
	}
	
	public void updateBalance(String emailValue, int rentalId) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		double dblCost = 0;
		double dblBalance = 0;
		
		String balanceWhere = "where Email='" + emailValue + "'";
		String costWhere = "where MovieID=" + rentalId;
		
		String cost = query("Movie", "rental_cost", costWhere); 
		dblCost = Double.parseDouble(cost);
		
		String balance = query("Account", "balance", balanceWhere); 
		dblBalance = Double.parseDouble(balance);
		double updateBalance = dblCost + dblBalance;
		System.out.println("dblCost = " + dblCost + ", dblBalance = " + dblBalance + ", updateBalance = " + updateBalance);
		String sql = "update " + dbName + ".Account set balance=" + updateBalance +
				" where Email='" + emailValue +"';";

		
		try {
			con = jdbcCon.getConnection();
			stmt = con.createStatement();
			
			stmt.executeUpdate(sql);		        
		}
		catch (SQLException e) {
		    System.out.println(e);
		} 
		finally {
		    if (stmt != null) { 
		    	stmt.close(); 
		    	}
			if (con != null){
				con.close();
			}
		}
	}
	
	public HashMap<Integer,String> getDBMovieArray() throws SQLException {
		Connection con = jdbcCon.getConnection();
		Statement stmt = null;
		int queryOutputId = 0;
		String queryOutputName = "";
		String queryOutputType = "";
		String queryOutputAvail = "";
		String mapValue = "";
		HashMap<Integer,String> queryMap = new HashMap<Integer,String>();
		
		String query = "select MovieID, moviename, movietype, available_copies from " + dbName + ".movie where available_copies > 0";
		try {
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    while (rs.next()) {
		    	queryOutputId = rs.getInt("MovieID"); 
		    	queryOutputName = rs.getString("moviename");
		    	queryOutputType = rs.getString("movietype");
		    	queryOutputAvail = rs.getString("available_copies");
		    	//queryOutputAvail = queryOutputAvail.toString();
		    	//System.out.println("MovieID = " +queryOutputId+ ", moviename = " +queryOutputName+ ", movietype = " + queryOutputType);
		    	//System.out.println("queryOutputAvail = " + queryOutputAvail);
		    	mapValue = queryOutputName + " - " + queryOutputType + "  (" + queryOutputAvail + ")";
		    	queryMap.put(queryOutputId, mapValue);
		    }
		} 
		catch (SQLException e) {
		    	System.out.println(e);
		} 
		finally {
			if (stmt != null) { 
				stmt.close(); 
			}
			if (con != null){
				con.close();
			}
		}
		return queryMap;
	}
	
	public HashMap<Integer,String> getDBReturnArray(String email) throws SQLException {
		Connection con = jdbcCon.getConnection();
		Statement stmt = null;
		int queryOutputId = 0;
		String queryOutputName = "";
		String queryOutputDate = "";
		String mapValue = "";
		HashMap<Integer,String> queryMap = new HashMap<Integer,String>();
		
		String query = "select rental.RentalID, movie.moviename, rental.duedate from " + dbName + ".rental inner join " + dbName 
				+ ".movie on rental.MovieId=movie.MovieID where rental.Email = '" + email + "'";
		try {
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    while (rs.next()) {
		    	queryOutputId = rs.getInt("rental.RentalID"); 
		    	queryOutputName = rs.getString("movie.moviename");
		    	queryOutputDate = rs.getString("rental.duedate");
		    	//System.out.println("MovieID = " +queryOutputId+ ", moviename = " +queryOutputName+ ", movietype = " + queryOutputDate);
		    	mapValue = queryOutputName + " - " + queryOutputDate;
		    	queryMap.put(queryOutputId, mapValue);
		    }
		} 
		catch (SQLException e) {
		    	System.out.println(e);
		} 
		finally {
			if (stmt != null) { 
				stmt.close(); 
			}
			if (con != null){
				con.close();
			}
		}
		return queryMap;
	}
	
	public static String staticQuery(String tableName, String fieldName, String where) throws SQLException {
		databaseConnection jdbcConn = new databaseConnection("", "root", "127.0.0.1", "3306", "redbox");
		Connection con = jdbcConn.getConnection();
		Statement stmt = null;
		String queryOutput = "";
		String query = "select " + fieldName +
		               " from redbox." + tableName + 
		               " " + where + ";";
		try {
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    while (rs.next()) {
		    	queryOutput = rs.getString(fieldName);   
		    }
		} 
		catch (SQLException e) {
		    	System.out.println(e);
		    	return queryOutput;
		} 
		finally {
			if (stmt != null) { 
				stmt.close(); 
			}
			if (con != null){
				con.close();
			}
		}
		//System.out.println("queryOutput at ret= " + queryOutput);
		return queryOutput;
	}
	
	/* bug: emails are not equaling
	public boolean checkEmail(String email) throws SQLException {
		Connection con = jdbcCon.getConnection();
		Statement stmt = null;
		String queryOutput = "";
		String query = "select Email from " + dbName + ".account ";
		boolean isValidEmail = false;
		
		try {
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    while (rs.next()) {
		    	queryOutput = rs.getString("Email");
		    	System.out.println("queryOutput = " + queryOutput);
		    	if(email.toLowerCase() == queryOutput.toLowerCase()){
		    		System.out.println("email == queryOutput");
		    		isValidEmail = true;
		    		break;
		    	}
		        //System.out.println(queryOutput);
		    }
		} 
		catch (SQLException e) {
		    	System.out.println(e);
		} 
		finally {
			if (stmt != null) { 
				stmt.close(); 
			}
		}
		
		return isValidEmail;
	}
	*/
}
