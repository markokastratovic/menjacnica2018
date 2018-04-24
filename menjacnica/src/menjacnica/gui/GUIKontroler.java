package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	public static Menjacnica sistem;
	public static MenjacnicaGUI gp;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistem = new Menjacnica();
					GUIKontroler.gp = new MenjacnicaGUI();
					GUIKontroler.gp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(null,
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(null,
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(null);
		prozor.setVisible(true);
	}

	public static void prikaziObrisiKursGUI() {
		JTable table=gp.getTable();
		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(
					model.vratiValutu(table.getSelectedRow()));
			prozor.setLocationRelativeTo(null);
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI() {
		JTable table=gp.getTable();
		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(
					model.vratiValutu(table.getSelectedRow()));
			prozor.setLocationRelativeTo(null);
			prozor.setVisible(true);
		}
	}
	public static void prikaziSveValute() {
		JTable table=gp.getTable();
		MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
		model.staviSveValuteUModel(GUIKontroler.sistem.vratiKursnuListu());

	}
	public static void obrisiValutu(Valuta valuta) {
		try{
			GUIKontroler.sistem.obrisiValutu(valuta);
			
			GUIKontroler.prikaziSveValute();
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void unesiKurs(String naziv, String skraceniNaziv, Object sifra, String prodajni, String srednji, String kupovni) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra((Integer)sifra);
			valuta.setProdajni(Double.parseDouble(prodajni));
			valuta.setKupovni(Double.parseDouble(kupovni));
			valuta.setSrednji(Double.parseDouble(srednji));
			
			// Dodavanje valute u kursnu listu
			GUIKontroler.sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			GUIKontroler.prikaziSveValute();
			

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
}
