package Kirjasto_DB_retrieve;

import java.sql.*;

public class MysqlConnection {

	public static void main(String args[]) {

		try {

			// Luodaan tietokantayhteys
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kirjat", "root", "");

			if (con != null) {
				System.out.println("Yhteys on muodostettu!");
			}

			con.close();

			// Varaudutaan virheisiin
			
		} catch (Exception e) {
			
			System.out.println("Tapahtui Virhe!");
			System.out.println(e);
		}

	}
}