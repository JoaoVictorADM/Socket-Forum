package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionController{

	private static final String url =  "jdbc:mysql://localhost:3306/forumdb";
	private static final String user = "root";
	private static final String password = "Jc104499@";
	
	private static Connection connection = null;
	
	public static Connection getConnection(){
		try {
			if(connection == null) {
				System.out.println("Conexão com Banco de dados criada");
				connection = DriverManager.getConnection(url, user, password);
			}
			
			return connection;
		} catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		} 
	}
}
