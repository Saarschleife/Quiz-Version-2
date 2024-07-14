package pd.QuizV2;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class QuizV2_GUI extends JFrame{
//Instanzvariablen für Oberfläche:
	//für Name der Anwendung u. aktuelle Frage
	JTextField textfeld;
	JTextArea textareal;
	
	//Schaltflächen u. Labels für Antwortmöglichkeiten
	JButton schaltFl_A = new JButton();
	JButton schaltFl_B = new JButton();
	JButton schaltFl_C = new JButton();
	JButton schaltFl_D = new JButton();
	JLabel antwortLabel_A = new JLabel();
	JLabel antwortLabel_B = new JLabel();
	JLabel antwortLabel_C = new JLabel();
	JLabel antwortLabel_D = new JLabel();
	int sekunden = 10;//Zeit für den Timer
		
	//Array für Fragen	
	String[] fragen = {"Wie heißt die Hauptstadt des Saarlandes?",
						"Wo sind die Schlossberghöhlen?",
						"Wo ist der Europäische Kulturpark?",
						"Wo ist der Hunnenring?",
						"In welchem Ortsteil von Blieskastel sind die Bliestal-Kliniken?",
						"Wo ist die Burg Montclair?",
						"Welches Saarländische Eisenwerk hat den Rang eines Weltkulturerbes?"
						};
	
	//zweidimensionales Array für mögliche Antworten
	//pro Frage 4 Antwortmöglichkeiten
	String[][] optionenAntwort = { {"Zweibrücken", "Saarbrücken", "Saarlouis", "Saarburg"},
								{"Trier", "Merzig", "Blieskastel", "Homburg"},
								{"Mettlach", "Völklingen", "Reinheim", "St. Wendel"},
								{"Ottweiler", "Orscholz", "Perl","Otzenhausen"},
								{"Lautzkirchen", "Aßweiler", "Webenheim", "Bierbach"},
								{"Mettlach", "Dillingen", "Marpingen", "Forbach"},
								{"Homburger Stahlbau", "Dillinger Hütte", "Völklinger Hütte","Villeroy & Boch"} };

	//Array für Buchstaben der korrekten Antworten
	char[] antwortKorrekt = {'B', 'D', 'C', 'D', 'A', 'A', 'C'};

	char antwort;//für Buchstabe der geklickten Schaltfläche
	int index;
	
	int richtig_geraten = 0;//zum Zählen richtiger Antworten
	int anz_fragen = fragen.length;
	int resultat;

	
	//Timer zählt Zeit für das Beantworten einer Frage
	Timer timer = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			sekunden--;
			if(sekunden <= 0) {
				zeigeAntworten();//Methode aufrufen
			}
		}
	});


	//innere Klasse für Ereignisverarbeitung
	class QuizLauscher implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent aktion) {
			//Schaltflächen für Auswahl der Antwort abgeschaltet
			
			schaltFl_A.setEnabled(false);
						
			schaltFl_B.setEnabled(false);
						
			schaltFl_C.setEnabled(false);
						
			schaltFl_D.setEnabled(false);
			
			
			if(aktion.getSource() == schaltFl_A) {//Quelle Schaltfläche A?
				antwort = 'A';//Antwort lautet A
				if(antwort == antwortKorrekt[index]) {//Antwort richtig?
					richtig_geraten++;//Zahl richtiger Antworten um 1 erhöhen
				}
			}
			if(aktion.getSource() == schaltFl_B) {
				antwort = 'B';
				if(antwort == antwortKorrekt[index]) {
					richtig_geraten++;
				}
			}
			if(aktion.getSource() == schaltFl_C) {
				antwort = 'C';
				if(antwort == antwortKorrekt[index]) {
					richtig_geraten++;
				}
			}
			if(aktion.getSource() == schaltFl_D) {
				antwort = 'D';
				if(antwort == antwortKorrekt[index]) {
					richtig_geraten++;
				}
			}
			zeigeAntworten();//Methode aufrufen
		}
	}

	
	//Konstruktor
	public QuizV2_GUI(String titel) {
		super(titel);
		
		//Panels über Methoden erstellen
		JPanel panelTexte, panelFragenAntworten;
		panelTexte = textPanelErzeugen();
		panelFragenAntworten= fragenAntwortenPanelErzeugen();
		
		//Layout und Größe setzen
		setLayout(new BorderLayout());
		setSize(900, 600);
		
		//Panels in Layout einfügen
		add(panelTexte, BorderLayout.NORTH);
		add(panelFragenAntworten, BorderLayout.CENTER);
					
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);
			
		naechsteFrage();//Methode aufrufen
	}
	
	//erzeugt Panel für Überschrift u. Fragen
	private JPanel textPanelErzeugen() {
		JPanel tempPanel = new JPanel();
		
		//für Nummer der aktuellen Frage
		textfeld = new JTextField();
		textfeld.setBackground(Color.PINK);
		textfeld.setForeground(Color.BLACK);
		textfeld.setFont(new Font("Times New Roman",Font.BOLD,30));
		textfeld.setEditable(false);
		
		//für Text der aktuellen Frage
		textareal = new JTextArea();
		textareal.setBackground(Color.PINK);
		textareal.setForeground(Color.BLACK);
		textareal.setFont(new Font("Times New Roman",Font.ITALIC,25));
		textareal.setEditable(false);

		tempPanel.setLayout(new GridLayout(2, 0, 0, 0));//Zeilen, Spalten, Abstand
		tempPanel.add(textfeld);//ins Panel einfügen
		tempPanel.add(textareal);

		return tempPanel;
	}
	
	
	//erzeugt Panel für Schaltflächen u. Antworten
	private JPanel fragenAntwortenPanelErzeugen() {
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(4, 2, 10, 10));//Zeilen, Spalten, Abstand
		
		//Schrift, Fokus und Text einer Schaltfläche setzen
		schaltFl_A.setFont(new Font("Times New Roman",Font.BOLD,30));
		schaltFl_A.setFocusable(false);
		schaltFl_A.setText("A");
				
		schaltFl_B.setFont(new Font("Times New Roman",Font.BOLD,30));
		schaltFl_B.setFocusable(false);
		schaltFl_B.setText("B");
		
		schaltFl_C.setFont(new Font("Times New Roman",Font.BOLD,30));
		schaltFl_C.setFocusable(false);
		schaltFl_C.setText("C");
		
		schaltFl_D.setFont(new Font("Times New Roman",Font.BOLD,30));
		schaltFl_D.setFocusable(false);
		schaltFl_D.setText("D");
		
		antwortLabel_A.setForeground(Color.GREEN);
		antwortLabel_A.setFont(new Font("Times New Roman",Font.ITALIC,30));
				
		antwortLabel_B.setForeground(Color.GREEN);
		antwortLabel_B.setFont(new Font("Times New Roman",Font.ITALIC,30));
			
		antwortLabel_C.setForeground(Color.GREEN);
		antwortLabel_C.setFont(new Font("Times New Roman",Font.ITALIC,30));
	
		antwortLabel_D.setForeground(Color.GREEN);
		antwortLabel_D.setFont(new Font("Times New Roman",Font.ITALIC,30));
		
		//alles ins Panel einfügen
		tempPanel.add(schaltFl_A);
		tempPanel.add(antwortLabel_A);
		tempPanel.add(schaltFl_B);
		tempPanel.add(antwortLabel_B);
		tempPanel.add(schaltFl_C);
		tempPanel.add(antwortLabel_C);
		tempPanel.add(schaltFl_D);
		tempPanel.add(antwortLabel_D);
			
		//Schaltflächen mitActionListener verbinden
		QuizLauscher lauscher = new QuizLauscher();
		schaltFl_A.addActionListener(lauscher);
		schaltFl_B.addActionListener(lauscher);
		schaltFl_C.addActionListener(lauscher);
		schaltFl_D.addActionListener(lauscher);
		
		return tempPanel;
	}
	
	//weiter zur nächsten Frage
	public void naechsteFrage() {
		if(index >= anz_fragen) {
			ergebnisse();//Methode aufrufen
		}
		else {
			textfeld.setText("Frage " + (index + 1));
			textareal.setText(fragen[index]);
			antwortLabel_A.setText(optionenAntwort[index][0]);
			antwortLabel_B.setText(optionenAntwort[index][1]);
			antwortLabel_C.setText(optionenAntwort[index][2]);
			antwortLabel_D.setText(optionenAntwort[index][3]);

			timer.start();
		}
	}

	//richtige u. falsche Antworten durch Farbe kennzeichnen
	public void zeigeAntworten() {
		timer.stop();

		schaltFl_A.setEnabled(false);
		schaltFl_B.setEnabled(false);
		schaltFl_C.setEnabled(false);
		schaltFl_D.setEnabled(false);
		
		//falsche Antworten werden rot gefärbt
		if(antwortKorrekt[index] != 'A')
			antwortLabel_A.setForeground(Color.RED);
		if(antwortKorrekt[index] != 'B')
			antwortLabel_B.setForeground(Color.RED);
		if(antwortKorrekt[index] != 'C')
			antwortLabel_C.setForeground(Color.RED);
		if(antwortKorrekt[index] != 'D')
			antwortLabel_D.setForeground(Color.RED);
		
		//nach Ablauf des Timers werden Antworten grün gefärbt u. Schaltflächen eingeschaltet
		Timer pause = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent timerAktion) {
				antwortLabel_A.setForeground(Color.GREEN);
				antwortLabel_B.setForeground(Color.GREEN);
				antwortLabel_C.setForeground(Color.GREEN);
				antwortLabel_D.setForeground(Color.GREEN);

				antwort = ' ';
				sekunden = 10;
				
				schaltFl_A.setEnabled(true);
				schaltFl_B.setEnabled(true);
				schaltFl_C.setEnabled(true);
				schaltFl_D.setEnabled(true);

				index++;//Anzahl Fragen um 1 erhöhen

				naechsteFrage();//Methode aufrufen
			}
		} );//Timer Ende
		
		pause.setRepeats(false);
	    pause.start();//Timer mit dem Namen "pause" starten
	}
	
	//zeigt Anzahl u. Prozent richtig beantworteter Fragen
	public void ergebnisse(){
		//Schaltflächen abschalten
		schaltFl_A.setEnabled(false);
		schaltFl_B.setEnabled(false);
		schaltFl_C.setEnabled(false);
		schaltFl_D.setEnabled(false);

		resultat = (int)((richtig_geraten / (double)anz_fragen) * 100);//Ergebnis in Prozent berechnen
		textfeld.setText("Ergebnis");
		textareal.setText(richtig_geraten + " von " + anz_fragen + " - " + resultat + "%");
		antwortLabel_A.setText("");
		antwortLabel_B.setText("");
		antwortLabel_C.setText("");
		antwortLabel_D.setText("");
		}	
}
