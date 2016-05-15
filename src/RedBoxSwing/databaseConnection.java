package RedBoxSwing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class databaseConnection {
	private String password = "";
	private String userName = "";
	private String dbms = "mysql";
	private String serverName = "";
	private String portNumber = "";
	private String dbName= "";

	public databaseConnection(String pw, String user, String server, String port, String db) {
	    this.password = pw;
	    this.userName = user;
	    this.serverName = server;
	    this.portNumber = port;
	    this.dbName = db;
	}
	
	public String getDBName(){
		return this.dbName;
	}
	
	public Connection getConnection() throws SQLException {

	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.setProperty("user", this.userName);
	    connectionProps.setProperty("password", this.password);

	    if (this.dbms.equals("mysql")) {
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.dbms  + "://" +
	                   this.serverName  +
	                   ":" + this.portNumber + "/",
	                   connectionProps);
	    } else if (this.dbms.equals("derby")) {
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.dbms + ":" +
	                   this.dbName +
	                   ";create=true",
	                   connectionProps);
	    }
	    System.out.println("Connected to database");
	    return conn;
	}
}
