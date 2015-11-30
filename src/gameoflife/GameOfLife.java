package gameoflife;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

/**
 * Diese Klasse ist die Hauptklasse von GameOfLife, welche sowohl Grafik als auch interne Berechnungen umfasst.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class GameOfLife {
	
	private JFrame frame1 = new JFrame("Game of Life");
	private String umbruch = System.getProperty("line.separator");
	private JButton buttonfolgegen = new JButton("Nächste");
	private JButton buttonreinigen = new JButton("Reinigen");
	private JButton buttonxweiter = new JButton("3 Weiter");
	private JButton buttonautomatik = new JButton("Automatik");
	private int breite = 20, hoehe= 20;
	private Zelle[][] spielfeld = new Zelle[breite][hoehe];
	private NumberFormat format = NumberFormat.getInstance(); 
	private NumberFormatter formatter = new NumberFormatter(format);
	private JFormattedTextField anzweiter = new JFormattedTextField(formatter);
	private int anzahlgen;
	private Thread thread = null;
	
	public GameOfLife() {
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(110+(breite)*20,20+(hoehe+1)*20);
		frame1.setResizable(false);
		Container cp = frame1.getContentPane();
		cp.setLayout(null);
		
		buttonfolgegen.setBounds(10+(breite)*20, 10, 100, 25);
		buttonfolgegen.setVisible(true);
		buttonfolgegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				neuegeneration();
			}
		});
		cp.add(buttonfolgegen);
		
		buttonreinigen.setBounds(10+(breite)*20, 40, 100, 25);
		buttonreinigen.setVisible(true);
		buttonreinigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonReinigen_ActionPerformed();
			}
		});
		cp.add(buttonreinigen);
		
		anzweiter.setBounds(14+(breite)*20, 70, 92, 25);
		anzweiter.setText("3");
		anzweiter.setHorizontalAlignment(JTextField.CENTER);
		anzweiter.setVisible(true);
		anzweiter.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent ke) {
	        	anzahlgen = Integer.parseInt(anzweiter.getText());
	        	buttonxweiter.setText(anzahlgen+" Weiter");
	        }
	    });
		cp.add(anzweiter);
		
		buttonxweiter.setBounds(10+(breite)*20, 100, 100, 25);
		buttonxweiter.setVisible(true);
		buttonxweiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonXweiter_ActionPerformed();
			}
		});
		cp.add(buttonxweiter);
		
		buttonautomatik.setBounds(10+(breite)*20, 130, 100, 25);
		buttonautomatik.setVisible(true);
		buttonautomatik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonAutomatik_ActionPerformed();
			}
		});
		cp.add(buttonautomatik);
		
		for(int px=0;px<breite;px++) {
			for(int py=0;py<hoehe;py++) {
				spielfeld[px][py] = new Zelle();
	    		spielfeld[px][py].setBounds(10+(px)*20,10+(py)*20,20,20);
	    		spielfeld[px][py].setBorder(BorderFactory.createLineBorder(Color.black));
	    		spielfeld[px][py].setAktiviert(false);
	    		cp.add(spielfeld[px][py]);
			}
		}
		
		format.setGroupingUsed(false); 
		format.setMaximumIntegerDigits(4);
		formatter.setAllowsInvalid(false);
		
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	/**
	 * Diese Methode findet heraus, wie viele lebende Nachbaren eine Zelle hat und gibt dies zurueck.
	 * @param x Nimmt die x-Koordinate der Zelle auf.
	 * @param y Nimmt die y-Koordinate der Zelle auf.
	 * @return Gibt die Anzahl lebender Nachbaren aus.
	 */
	private int zellnachbaren(int x, int y) {
		int anzahl = 0;
		for(int i=-1;i<2;i++) {
			for(int j=-1;j<2;j++) {
				if(!(i==j && i==0)) {
					try {
						if(spielfeld[x+i][y+j].getAktiviert() == true) {
							anzahl++;
						}
					} catch(ArrayIndexOutOfBoundsException e) { }
				}
			}
		}
		return anzahl;
	}
	
	/**
	 * Diese Methode generiert ein neues Feld, rechnet aus, welche Zellen ueberleben und stuelpt es ueber das alte Feld. Anschliessend wird das Fenster neu geladen.
	 */
	private void neuegeneration() {
		boolean[][] temp = new boolean[breite][hoehe];
		for(int i=0;i<breite;i++) {
			for(int j=0;j<hoehe;j++) {
				switch (zellnachbaren(i,j)) {
				case 0: temp[i][j] = false; break;
				case 1: temp[i][j] = false; break;
				case 2: temp[i][j] = spielfeld[i][j].getAktiviert(); break;
				case 3: temp[i][j] = true; break;
				case 4: temp[i][j] = false; break;
				case 5: temp[i][j] = false; break;
				case 6: temp[i][j] = false; break;
				case 7: temp[i][j] = false; break;
				case 8: temp[i][j] = false; break;
				default: temp[i][j] = false; break;
				}
			}
		}
		for(int px=0;px<breite;px++) {
			for(int py=0;py<hoehe;py++) {
				spielfeld[px][py].setAktiviert(temp[px][py]);
			}
		}
		frame1.repaint();
	}
  
	/**
	 * Diese Methode leert das gesamte Feld.
	 */
	private void buttonReinigen_ActionPerformed() {
		for(int px=0;px<breite;px++) {
			for(int py=0;py<hoehe;py++) {
	    		spielfeld[px][py].setAktiviert(false);
			}
		}
		frame1.repaint();
	}
  
	/**
	 * Diese Methode springt in die Nachfolgegeneration x.
	 */
	private void buttonXweiter_ActionPerformed() {
		anzahlgen = Integer.parseInt(anzweiter.getText());
		if(anzahlgen < 0) {
			JOptionPane.showMessageDialog(null, "Bitte gib eine positive Zahl neuer Generationen ein."+umbruch+"Wir können nicht in die Vergangenheit blicken.", "Ungültige Eingabe", JOptionPane.PLAIN_MESSAGE);
		} else {
			for(int n=0;n<anzahlgen;n++) {
				neuegeneration();
			}
		}
	}
	
	/**
	 * Diese Methode (de)aktiviert einen Automatikmodus, der von allein neue Zellen berechnet.
	 */
	@SuppressWarnings("deprecation")
	private void buttonAutomatik_ActionPerformed() {
		final int weiter = 1;
		if(thread == null) {
			thread = new Thread(new Runnable() {
				@SuppressWarnings("static-access")
				@Override
				public void run() {
					while(weiter > 0) {
						neuegeneration();
						try {
							thread.sleep(1000);
						} catch (InterruptedException e) { }
					}
				}
			});
			buttonfolgegen.setEnabled(false);
			buttonreinigen.setEnabled(false);
			buttonxweiter.setEnabled(false);
			anzweiter.setEnabled(false);
			buttonautomatik.setText("Stop");
			thread.start();
		} else {
			thread.stop();
			thread = null;
			buttonfolgegen.setEnabled(true);
			buttonreinigen.setEnabled(true);
			buttonxweiter.setEnabled(true);
			anzweiter.setEnabled(true);
			buttonautomatik.setText("Automatik");
		}
	}

	public static void main(String[] args) {
		new GameOfLife();
	}
}