package Kirjasto_DB_retrieve;

import java.util.Scanner;
import java.sql.Blob;



public class Kirja {

	private String teoksenNimi;
	private int julkaisuvuosi;
	private String tekija;
	private Blob kuva;

	public Kirja(String teoksenNimi, int julkaisuvuosi, String tekija, Blob kuva) {
		super();
		this.teoksenNimi = teoksenNimi;
		this.julkaisuvuosi = julkaisuvuosi;
		this.tekija = tekija;
		this.kuva = kuva;
	}

	public int getJulkaisuvuosi() {
		return julkaisuvuosi;
	}

	public void setJulkaisuvuosi(int vuosi) {
		this.julkaisuvuosi = vuosi;
	}

	public String getTeoksenNimi() {
		return teoksenNimi;
	}

	public void setTeoksenNimi(String nimi) {
		this.teoksenNimi = nimi;
	}

	public String getTekija() {
		return tekija;
	}

	public void setTekija(String tekija) {
		this.tekija = tekija;
	}
	
	public Blob getKuva() {
		return kuva;
	}

	public void setKuva(Blob kuva) {
		this.kuva = kuva;
	}

	public String toString() {
		String tiedot = "--------------------------------\n" + "- Kirjan nimi: " + this.getTeoksenNimi() + "\n"
				+ "- Tekijä: " + this.getTekija() + "\n" + "- Julkaisuvuosi: " + this.getJulkaisuvuosi() + "\n"
				+ "- Kuva: " + this.getKuva() + "\n" + "--------------------------------";
		return tiedot;
	}


//	public static void main(String[] args) {
//
//		Kirja[] kirjaHylly = new Kirja[2];
//		Scanner lukija = new Scanner(System.in);
//
//		String nimi;
//		String tekija;
//		int vuosi;
//
//		for (int i = 0; i < kirjaHylly.length; i++) {
//
//			Kirja kirja = new Kirja();
//			kirjaHylly[i] = kirja;
//
//			System.out.println("Anna kirjan nimi: ");
//			nimi = lukija.nextLine();
//			kirja.setTeoksenNimi(nimi);
//
//			System.out.println("Anna kirjan tekijä: ");
//			tekija = lukija.nextLine();
//			kirja.setTekija(tekija);
//
//			System.out.println("Anna kirjan julkaisuvuosi: ");
//			vuosi = lukija.nextInt();
//			lukija.nextLine();
//			kirja.setJulkaisuvuosi(vuosi);
//
//		}
//
//		for (int i = 0; i < kirjaHylly.length; i++) {
//			System.out.println(kirjaHylly[i]);
//
//			JDBCExample.tallennaKirja(kirjaHylly[i]);
//
//		}
//
//		JDBCExample.lataaKirjat();
//		
//		GUI test = new GUI(kirjaHylly);
//		test.setVisible(true);
//
//	}

}
