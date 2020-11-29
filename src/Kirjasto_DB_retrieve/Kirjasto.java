package Kirjasto_DB_retrieve;

import javax.swing.JFrame;


public class Kirjasto {

	// Alustetaan taulukko
	static Kirja kirjaHylly[];

	// Ohjelman päämetodi
	public static void main(String[] args) {

		// Ladataan kirjat tietokannasta ja sijoitetaan kirjaHylly -muuttujaan
		kirjaHylly = JDBCExample.palautaKirjat();

		// Uutta ikkunaa piirrettäessä, välitetään kirjahyllyn sisältö mukana
		JFrame ikkuna = new GUI( kirjaHylly );
		ikkuna.setVisible(true);
		

	}

}
