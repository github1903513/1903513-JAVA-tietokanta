package Kirjasto_DB_retrieve;

import java.sql.Blob;
import java.text.ParseException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	// Sarakkeiden nimet

	String[] sarakkeet = { "Teoksen nimi", "Tekij‰n nimi", "Julkaisuvuosi","Kuva" };
	Object[][] data = {};

	/**
	 * Create the frame.
	 */

	public GUI(Kirja[] kirjahylly) {

		table = new JTable();
		
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		setTitle("Kirjasto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 647, 605);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JScrollPane scrollPane = new JScrollPane(table);

		contentPane.add(scrollPane);

		// Taulukon alustaminen

		table.setModel(new DefaultTableModel(data, sarakkeet)
				
		{ //table cell image render
            @Override
            public Class<?> getColumnClass(int column) {
                if (column==3) return ImageIcon.class;
                return Object.class;
            }
        }
				
				);

		if (kirjahylly != null)
			tiedotTaulukkoon(kirjahylly);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("Lis\u00E4\u00E4 rivi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Lis‰‰ kirjan taulukkoon
				lisaaKirja();

			}
		});
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Poista valittu rivi");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Poistaa kirjan taulukosta
				poistaValitutRivit(table);

			}
		});
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("P\u00E4ivit\u00E4 taulukko");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tiedotTaulukkoon(kirjahylly);
			}
		});
		panel.add(btnNewButton_2);

		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);

		JMenu mnNewMenu_1 = new JMenu("Tiedosto");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem = new JMenuItem("Avaa");
		mnNewMenu_1.add(mntmNewMenuItem);

		JMenu mnNewMenu = new JMenu("Tallenna");
		mnNewMenu_1.add(mnNewMenu);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Poistu");
		mnNewMenu_1.add(mntmNewMenuItem_1);

		JMenu mnNewMenu_2 = new JMenu("Tietoja ohjelmasta");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Versio");
		mnNewMenu_2.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("P\u00E4ivitykset");
		mnNewMenu_2.add(mntmNewMenuItem_3);

	}

	protected  void tiedotTaulukkoon(Kirja[] kirjahylly) {

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		// K‰yd‰‰n kirjahylly l‰pi 
		for (int row = 0; row < kirjahylly.length; row++) {

			if (kirjahylly[row] != null) {
				// poimitaan tiedot muuttujiin
				String nimi = kirjahylly[row].getTeoksenNimi();
				String tekija = kirjahylly[row].getTekija();
				int vuosi = kirjahylly[row].getJulkaisuvuosi();
				Blob kuva = kirjahylly[row].getKuva();
				System.out.println(kuva);
				BufferedImage bufferedImage = null;
				
				/*handle blob */
				try {
				int blobLength = (int) kuva.length();
	            byte[] blobAsBytes = kuva.getBytes(1, blobLength);
	            bufferedImage = ImageIO.read(new ByteArrayInputStream(blobAsBytes));
	            
				}
				catch (Exception e)
				 {   e.printStackTrace();
				  }
				ImageIcon imageIcon = new ImageIcon(bufferedImage);
				//imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 100,Image.SCALE_DEFAULT));
				// Lis‰t‰‰n tiedot taulukkoon
				model.addRow(new Object[] { nimi, tekija, "" + vuosi, imageIcon  });
				table.setRowHeight(row, 100);
				
				table.setModel(model); 
				System.out.println("Lis‰ttiin: "+nimi);
			}

		}

	}
       
	// Lis‰t‰‰n kirja taulukkoon
	protected void lisaaKirja() {

		JFrame ikkuna2;
		try {
			ikkuna2 = new KirjaPaneeliGUI();
			ikkuna2.setVisible(true);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Poistaa valitut rivit
	private void poistaValitutRivit(JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int[] rows = table.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			model.removeRow(rows[i] - i);
		}
	}

}
