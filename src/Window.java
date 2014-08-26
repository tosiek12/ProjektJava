import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JEditorPane;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BoxLayout;
import javax.swing.JProgressBar;

import java.awt.ComponentOrientation;

import javax.swing.ScrollPaneConstants;

import org.apache.log4j.Logger;


public class Window {

	private JFrame frmWiadomoci;
	private JTextField txtDzisiejszeWiadomociTo;
	private JTextField textPageAddres;
	private JTextPane txtWiadomosci;
	private JTextField txtOstatniaAktualizacja;
	private JProgressBar progressBar;
	private AtomicInteger whatToDo;
	private JTextPane txtPages;
	private JTextField txtFileName;
	private JButton btnAddPage;
	private Logger log = Logger.getLogger("Project"); 
	/*
	log.trace("This is a TRACE example", new Exception("NOT OK"));
	log.info("This is a INFO example");
	log.debug("This is a DEBUG example");
	log.warn("This is a WARN example");
	log.error("This is a ERROR example");
	log.fatal("This is a FATAL example");
	 */
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmWiadomoci.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Launch the application.
	 */
	public void createWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmWiadomoci.setVisible(true);
				} catch (Exception e) {
					log.error("Blad w ladowaniu JFrame - okna glownego");
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Window() {
		log.debug("Created Object: Window");
		initialize();
	}
	
	public Window(AtomicInteger whatToDo) {
		this();
		this.whatToDo = whatToDo;
		log.debug("Window: whatToDo = "+ whatToDo);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWiadomoci = new JFrame();
		frmWiadomoci.setTitle("Wiadomo\u015Bci");
		frmWiadomoci.setResizable(false);
		frmWiadomoci.setBounds(100, 100, 400, 343);
		frmWiadomoci.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWiadomoci.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 384, 284);
		frmWiadomoci.getContentPane().add(tabbedPane);
		MutableAttributeSet standard = new SimpleAttributeSet();

		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_JUSTIFIED);
		StyleConstants.setLeftIndent(standard, 5);
		StyleConstants.setRightIndent(standard, 5);


		
		JPanel panel = new JPanel();
		panel.setAutoscrolls(true);
		tabbedPane.addTab("Wiadomo\u015Bci", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setVisible(false);
		panel_2.add(progressBar);
		
		txtOstatniaAktualizacja = new JTextField();
		txtOstatniaAktualizacja.setHorizontalAlignment(SwingConstants.RIGHT);
		txtOstatniaAktualizacja.setEditable(false);
		txtOstatniaAktualizacja.setColumns(10);
		panel_2.add(txtOstatniaAktualizacja);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		txtWiadomosci = new JTextPane();
		scrollPane.setViewportView(txtWiadomosci);
		txtWiadomosci.setAutoscrolls(false);
		txtWiadomosci.setDoubleBuffered(true);
		txtWiadomosci.setDragEnabled(true);
		txtWiadomosci.setEditable(false);
		//txtWiadomosci.setContentType("text/html");
		
		StyledDocument doc = txtWiadomosci.getStyledDocument();
		doc.setParagraphAttributes(0, 0, standard, true);
		txtWiadomosci.setBackground(new Color(224, 226, 235)); //Szary kolor
		txtWiadomosci.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
		
		txtDzisiejszeWiadomociTo = new JTextField();
		scrollPane.setColumnHeaderView(txtDzisiejszeWiadomociTo);
		txtDzisiejszeWiadomociTo.setHorizontalAlignment(SwingConstants.CENTER);
		txtDzisiejszeWiadomociTo.setEditable(false);
		txtDzisiejszeWiadomociTo.setText("Dzisiejsze wiadomo\u015Bci to:");
		txtDzisiejszeWiadomociTo.setColumns(10);
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Opcje", null, panel_1, null);
		panel_1.setLayout(null);
		
		textPageAddres = new JTextField();
		textPageAddres.setBounds(86, 28, 163, 20);
		panel_1.add(textPageAddres);
		textPageAddres.setColumns(10);
		textPageAddres.setVisible(false);
		
		btnAddPage = new JButton("Dodaj Strone");
		btnAddPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				log.debug("Obsluga przycisku: \"Dodaj Strone\"");
				BufferedWriter buffer = null;
				try {
					Writer writer = new FileWriter(txtFileName.getText(), true); // Write to end of the file.
					buffer = new BufferedWriter(writer);
					
					buffer.write(textPageAddres.getText() + "##" + textPageAddres.getText() + "\n");
					
					log.info("\"Dodaj Strone\": Pomyslnie dodano stronê do pliku.");	
					appendToPane(txtPages, textPageAddres.getText() + " " + textPageAddres.getText() +"\n", Color.DARK_GRAY);					
				} catch (FileNotFoundException e) {
					log.error("\"Dodaj Strone\": Blad przy otwieraniu pliku do zapisu.");
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("\"Dodaj Strone\": Nieznany Blad");
					e.printStackTrace();
				}finally {
					if(buffer != null){
						try {
							buffer.close();
						} catch (IOException e) {
							log.error("\"Dodaj Strone\": Blad przy zamykaniu buffora");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				
				
			}
		});
		btnAddPage.setBounds(259, 25, 123, 23);
		btnAddPage.setVisible(false);
		panel_1.add(btnAddPage);
		
		final JLabel lblAdresStrony = new JLabel("Adres Strony:");
		lblAdresStrony.setBounds(10, 31, 79, 14);
		lblAdresStrony.setVisible(false);
		panel_1.add(lblAdresStrony);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 55, 372, 147);
		panel_1.add(scrollPane_1);
		
		JLabel lblNazwyIAdresy = new JLabel("Nazwy i adresy stron z wiadomo\u015Bciami.");
		scrollPane_1.setColumnHeaderView(lblNazwyIAdresy);
		lblNazwyIAdresy.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtPages = new JTextPane();
		txtPages.setAutoscrolls(false);
		txtPages.setDoubleBuffered(true);
		txtPages.setDragEnabled(true);
		txtPages.setEditable(false);
		
		doc = txtPages.getStyledDocument();
		doc.setParagraphAttributes(0, 0, standard, true);
		txtPages.setBackground(new Color(224, 226, 235));
		txtPages.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
		txtPages.setText("");
		scrollPane_1.setViewportView(txtPages);
		
		JButton btnWczytajStrony = new JButton("Wczytaj Strony");
		btnWczytajStrony.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				log.debug("Obsluga przycisku: \"Wczytaj Strony\"");
				txtPages.setText("");
				
				String fileName = txtFileName.getText();
				if(fileName.length() == 0) {
					fileName = "pages.txt";
					txtFileName.setText(fileName);
				}
				
				BufferedReader buffer = null;		
				try {
					Reader reader = new FileReader(fileName);
					buffer = new BufferedReader(reader);
					String s;
					int cnt = 0;
					while ((s = buffer.readLine()) != null) {
						String a[] = s.split("##");
						if(a.length == 2) {
							appendToPane(txtPages,a[0] + " ", Color.DARK_GRAY);
							appendToPane(txtPages,a[1] + "\n", Color.DARK_GRAY);
							cnt++;
						}
					}
					btnAddPage.setVisible(true);
					lblAdresStrony.setVisible(true);
					textPageAddres.setVisible(true);
					
					log.info("\"Wczytaj Strony\": Pomyslnie zaimportowano "+cnt+" adresow.");	
					JOptionPane.showMessageDialog(null, "Wczytywanie zakoñczone.");
					
				} catch (FileNotFoundException e) {
					log.error("\"Wczytaj Strony\": Blad przy otwieraniu pliku");
					log.info("\"Wczytaj Strony\": Przywrócono domyœln¹ nazwê pliku");
					btnAddPage.setVisible(false);
					textPageAddres.setVisible(false);
					lblAdresStrony.setVisible(false);
					
					JOptionPane.showMessageDialog(null, "Bledna nazwa pliku - taki plik nie istnieje.\nPrzywrócono domyœln¹ nazwê.");
					txtFileName.setText("pages.txt");

					
					//e.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					log.error("\"Wczytaj Strony\": Blad przy pobieraniu linijki z pliku");
					e1.printStackTrace();
				} finally {
					if(buffer != null){
						try {
							buffer.close();
						} catch (IOException e) {
							log.error("\"Wczytaj Strony\": Blad przy zamykaniu buffora");
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}	    
			}
		});
		btnWczytajStrony.setBounds(260, 0, 122, 23);
		panel_1.add(btnWczytajStrony);
		
		txtFileName = new JTextField();
		txtFileName.setText("pliki.txt");
		txtFileName.setColumns(10);
		txtFileName.setBounds(86, 3, 163, 20);
		panel_1.add(txtFileName);
		
		JLabel lblNazwaPliku = new JLabel("Nazwa pliku:");
		lblNazwaPliku.setBounds(10, 6, 79, 14);
		panel_1.add(lblNazwaPliku);
		
		JMenuBar menuBar = new JMenuBar();
		frmWiadomoci.setJMenuBar(menuBar);
		
		JMenu mnPlik = new JMenu("Plik");
		menuBar.add(mnPlik);
		
		JMenuItem mntmZakocz = new JMenuItem("Zako\u0144cz");
		mntmZakocz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				log.debug("\"Zakoncz\": Obsluga przycisku z menu");
				frmWiadomoci.setVisible(false);
				if(whatToDo != null){
					while(whatToDo.get() != 0) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	//ms

					}
					log.debug("\"Zakoncz\": WhatToDo = "+ whatToDo);
					whatToDo.set(2);
				}
				frmWiadomoci.dispose();
				log.debug("\"Zakoncz\": Zamknieto okno.");
			}
		});
		mnPlik.add(mntmZakocz);
		
		JMenuItem mntmOdwie = new JMenuItem("Od\u015Bwie\u017C");
		mntmOdwie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				log.debug("\"Odswiez\": Obsluga przycisku z menu");
				if(whatToDo != null){
					whatToDo.set(1);
					log.debug("\"Odswiez\": WhatToDo = "+ whatToDo);
					txtWiadomosci.setText("");
				}
				progressBar.setVisible(true);
				log.debug("\"Odswiez\": Zainicjalizowano odswiezanie");
			}
		});
		mnPlik.add(mntmOdwie);
		
		JMenuItem mntmInfo = new JMenuItem("Info");
		mntmInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "Wykonane w ramach projektu na przedmiot Introdution to Java Programing\nAutor: Antoni Tr¹d");
			}
		});
		mnPlik.add(mntmInfo);
	}
	public JTextPane getTxtWiadomosci() {
		return txtWiadomosci;
	}
	
	public static void appendToPane(JTextPane pan, String msg, Color c)
    {
		Logger log = Logger.getLogger("Project");
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        StyledDocument document = (StyledDocument) pan.getDocument();
        try {
			document.insertString(document.getLength(), msg, aset);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			log.error("Window.appendToPane - BadLocationException");
			e1.printStackTrace();
		} finally {
			int a = pan.getCaretPosition();
			
			//pan.setCaretPosition(pan.getCaretPosition()+msg.length());
		}
    }

	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public JTextField getTxtOstatniaAktualizacja() {
		return txtOstatniaAktualizacja;
	}
	public JTextPane getTxtPages() {
		return txtPages;
	}
}
