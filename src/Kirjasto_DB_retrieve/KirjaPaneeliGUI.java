package Kirjasto_DB_retrieve;

import javax.swing.JLabel;
import javax.swing.JFileChooser;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

import java.lang.Object;

import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import java.awt.Font;
import java.awt.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

//import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class KirjaPaneeliGUI extends JFrame {
	private static final String IOUtils = null;
	private JTextField nimiKentta;
	private JTextField tekijaKentta;
	private JTextField julkaisuKentta;
	private JLabel kuvaKentta;
	private String kuvapath;
	private Blob b;


	/**
	 * Create the panel.
	 *
	 */
	public KirjaPaneeliGUI() throws ParseException {
		setTitle("Kirjan sy\u00F6tt\u00F6-ohjelma");
		setSize(640, 480);
		getContentPane().setLayout(null);

		JLabel lblKirjanSytt = new JLabel("Kirjan hallinta");
		lblKirjanSytt.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblKirjanSytt.setBounds(34, 27, 195, 20);
		getContentPane().add(lblKirjanSytt);

		JLabel lblKirjanNimi = new JLabel("Kirjan nimi:");
		lblKirjanNimi.setBounds(34, 94, 111, 20);
		getContentPane().add(lblKirjanNimi);

		JLabel lblTekij = new JLabel("Tekij\u00E4:");
		lblTekij.setBounds(34, 139, 111, 20);
		getContentPane().add(lblTekij);

		JLabel lblJulkaisuvuosi = new JLabel("Julkaisuvuosi:");
		lblJulkaisuvuosi.setBounds(34, 189, 111, 20);
		getContentPane().add(lblJulkaisuvuosi);

		nimiKentta = new JTextField();
		nimiKentta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		nimiKentta.setBounds(148, 91, 146, 26);
		getContentPane().add(nimiKentta);
		nimiKentta.setColumns(10);

		tekijaKentta = new JTextField();
		tekijaKentta.setColumns(10);
		tekijaKentta.setBounds(148, 136, 146, 26);

		getContentPane().add(tekijaKentta);

		julkaisuKentta = new JTextField();
		julkaisuKentta.setColumns(10);
		julkaisuKentta.setBounds(148, 186, 146, 26);
		getContentPane().add(julkaisuKentta);

		JButton lisaaNappi = new JButton("Lis\u00E4\u00E4 kirja");
		lisaaNappi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					tallennaKirja();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		lisaaNappi.setBounds(34, 244, 115, 29);
		getContentPane().add(lisaaNappi);

		JButton tyhjennaNappi = new JButton("Tyhjenn\u00E4");
		tyhjennaNappi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nimiKentta.setText("");
				tekijaKentta.setText("");
				julkaisuKentta.setText("");
				kuvaKentta.setIcon(new ImageIcon("img\\old-book.jpg"));
				kuvaKentta.setBounds(315, 75, 111, 142);
				getContentPane().add(kuvaKentta);
				
			}
		});
		tyhjennaNappi.setBounds(192, 244, 115, 29);
		getContentPane().add(tyhjennaNappi);
		JFormattedTextField formattedTextField = null;

		
		kuvaKentta = new JLabel();
		//kuvaKentta.setIcon(new ImageIcon("C:\\Zhang\\Laurea\\Java\\KT4-kirjasto\\Kirjasto_DB_insert_Project\\img\\old-book.jpg"));
		kuvaKentta.setIcon(new ImageIcon("img\\old-book.jpg"));
		kuvaKentta.setBounds(315, 75, 111, 142);
		getContentPane().add(kuvaKentta);
		
		JButton ValitsekuvaButton = new JButton("Valitse Kuva");
		ValitsekuvaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "JPG & GIF Images", "jpg", "gif");
				jfc.setFileFilter(filter);
				
				if (jfc.showOpenDialog(KirjaPaneeliGUI.this) == JFileChooser.APPROVE_OPTION) { 
					System.out.println(jfc.getSelectedFile().getAbsolutePath());
					ImageIcon imageIcon = new ImageIcon(jfc.getSelectedFile().getAbsolutePath());
					kuvaKentta.setIcon(imageIcon);
					kuvapath  = jfc.getSelectedFile().getAbsolutePath();
					//kuvaKentta = new JLabel(imageIcon);
					kuvaKentta.setBounds(315, 75, imageIcon.getIconWidth(), imageIcon.getIconHeight());
					getContentPane().add(kuvaKentta);
				}
			}
		});
		ValitsekuvaButton.setBounds(335, 235, 89, 23);
		getContentPane().add(ValitsekuvaButton);

	}

	protected void tallennaKirja() throws IOException {

		String nimi = nimiKentta.getText();
		String tekija = tekijaKentta.getText();
		String vuosi = julkaisuKentta.getText();
		Blob kuva = (Blob) null ;
		
		 try {
		File ImgPath = new File(kuvapath);
	    FileInputStream fileinput = new FileInputStream(ImgPath);
	    byte[] cadenaBytes =fileinput.readAllBytes();
	   
			kuva= new SerialBlob(cadenaBytes);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e)
		 {
			e.printStackTrace();
		 }
	    
	    if(nimi=="" || tekija== "" || vuosi== "")
	    
	    {
	    	JOptionPane.showMessageDialog(this, "please fill in all of the book's info!");
	    	return;
	    }
		
		Kirja uusiKirja = new Kirja(nimi, Integer.parseInt(vuosi), tekija,kuva);

		JDBCExample.tallennaKirja(uusiKirja);
		JOptionPane.showMessageDialog(this, "Tallennettu");
		
		Kirja[] kirjaHylly = JDBCExample.palautaKirjat();
		
		
		//JFrame ikkuna = new GUI( kirjaHylly );
		//ikkuna.setVisible(true);
		
		//GUI.(Kirja[] kirjahylly);
		//JDBCExample.lataaKirjat();
		//System.exit(0);
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.dispose();
		

	}

	public static void main(String[] args) {

		try {
			JFrame uusi = new KirjaPaneeliGUI();
			uusi.setSize(800,400);
			uusi.setVisible(true);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
