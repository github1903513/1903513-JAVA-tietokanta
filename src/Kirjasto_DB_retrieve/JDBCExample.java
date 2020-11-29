package Kirjasto_DB_retrieve;

//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;

public class JDBCExample {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/kirjat";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public static void tallennaKirja(Kirja opus) {

		String tekij‰ = opus.getTekija();
		String nimi = opus.getTeoksenNimi();
		int vuosi = opus.getJulkaisuvuosi();
		Blob kuva = opus.getKuva();

		Connection conn = null;
		Statement stmt = null;
		try {

			System.out.println("Connecting to database...");

			// Luodaan yhteys tietokantaan
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// SQL Lausekkeen esittely, k‰ytet‰‰n ? muuttuvien tietojen kohdalla
			String sql = "INSERT INTO kirja (kirjan_nimi, tekij‰, julkaisuvuosi,kuva) values (?,?,?,?)";

			// T‰ydennet‰‰n SQL-lauseisiin puuttuvat muuttujat
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			
			preparedStmt.setString(1, nimi);
			preparedStmt.setString(2, tekij‰);
			preparedStmt.setInt(3, vuosi);
			//int blobLength = (int) kuva.length();
            //byte[] blobAsBytes = kuva.getBytes(1, blobLength);
			//preparedStmt.setBinaryStream(5,kuva);
			//preparedStmt.setBlob(4, kuva);
			InputStream is = kuva.getBinaryStream();
			preparedStmt.setBinaryStream(4,is);

			// Suorittaa SQL lauseen

			preparedStmt.execute();

			System.out.println("Saving data...");

		} catch (SQLException se) {
			// K‰sitell‰‰n tietokantavirheet
			se.printStackTrace();
		} catch (Exception e) {
			// Muut virheet
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

	}// end main

	public static void lataaKirjat() {

		Connection conn = null;
		Statement stmt = null;
		try {

			// Luodaan yhteys
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			System.out.println("Haetaan dataa...");

			// Suoritetaan SQL kysely
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM KIRJA");

			// K‰yd‰‰n l‰pi tulosjoukko silmukassa
			while (rs.next()) {
				System.out.println(rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4)+ "  " + rs.getString(5));

			}
			// Otetaan mahdolliset virheet kiinni
		} catch (SQLException se) {

			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

	}// end main

	public static Kirja[] palautaKirjat() {

		Connection conn = null;
		Statement stmt = null;

		// Tuloksena saadut kirjat tallennetaan t‰h‰n

		Kirja[] hylly = null;

		try {

			// Luodaan yhteys
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			System.out.println("Haetaan dataa...");

			// Suoritetaan SQL kysely
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM KIRJA");

			// K‰yd‰‰n l‰pi tulosjoukko silmukassa
			int i = 0;
			hylly = new Kirja[5];

			while (rs.next()) {
				System.out.println(rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4)+ "  " + rs.getBlob(5).getBinaryStream());

				// Luodaan kirjahyllyyn uusi kirja joka silmukan iteraatiolla
				// , johon sijoitetaan tietokannasta ladatut arvot
				hylly[i] = new Kirja(rs.getString(2), Integer.parseInt(rs.getString(4)), rs.getString(3),rs.getBlob(5));
				i++;
			}
			// Otetaan mahdolliset virheet kiinni
		} catch (SQLException se) {

			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return hylly;

	}// end main

}