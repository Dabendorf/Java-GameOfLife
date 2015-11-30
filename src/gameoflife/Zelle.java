package gameoflife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Diese Klasse repraesentiert eine Zelle des GameOfLifeProjektes.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Zelle extends JPanel {
	
	private boolean aktiviert;

	public Zelle() {
		this.addMouseListener(new MouseAdapter() {
		      public void mouseClicked(MouseEvent evt) {
		    	  klick();
		      }
		});
	}
	
	public void paintComponent(Graphics stift) {
		super.paintComponent(stift);
		if(aktiviert == true) {
			stift.setColor(Color.black);
		} else {
			stift.setColor(Color.lightGray);
		}
		stift.fillRect(0,0,this.getWidth(),this.getHeight());
	}
	
	/**
	 * Diese Methode kehrt die Aktivierung der Zelle um und laedt sie neu.
	 */
	private void klick() {
		aktiviert = !aktiviert;
		this.repaint();
	}
	
	public boolean getAktiviert() {
		return aktiviert;
	}

	public void setAktiviert(boolean aktiviert) {
		this.aktiviert = aktiviert;
	}
}