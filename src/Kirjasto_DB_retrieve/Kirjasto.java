package Kirjasto_DB_retrieve;

import javax.swing.JFrame;


public class Kirjasto {

	// Alustetaan taulukko
	static Kirja kirjaHylly[];

	// Ohjelman p��metodi
	public static void main(String[] args) {

		// Ladataan kirjat tietokannasta ja sijoitetaan kirjaHylly -muuttujaan
		kirjaHylly = JDBCExample.palautaKirjat();

		// Uutta ikkunaa piirrett�ess�, v�litet��n kirjahyllyn sis�lt� mukana
		JFrame ikkuna = new GUI( kirjaHylly );
		ikkuna.setVisible(true);
		

	}

}
