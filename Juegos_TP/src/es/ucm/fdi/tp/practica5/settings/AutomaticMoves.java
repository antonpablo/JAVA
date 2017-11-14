package es.ucm.fdi.tp.practica5.settings;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * clase la cual representa los botones para hacer un movimiento random o inteligente.
 * @author Pablo-Por
 *
 */
public abstract class AutomaticMoves extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton jbIntelligent;
	private JButton jbRandom;
	private boolean botonesActivados;
	/**
	 * Contructor que iniliziza los botones y los activa. 
	 */
	public AutomaticMoves(boolean ran,boolean in) {
		initialize(ran,in);
		botonesActivados=true;
	}

	/**
	 * metodo que crear los botones de Random y intelingete.
	 */
	private void initialize(boolean ran,boolean in) {
		setBorder(new TitledBorder(null, "Automatic Moves", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		if (ran)
		{
			jbRandom = new JButton("Random");
			jbRandom.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					if (botonesActivados)
						movimientoAutomatico(false);
				}
			});
			add(jbRandom);
		}
		
		if (in)
		{
			jbIntelligent = new JButton("Intelligent");
			jbIntelligent.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					if (botonesActivados)
						movimientoAutomatico(true);
				}
			});
			add(jbIntelligent);
		}

		

	}
	
	/**
	 * metodo que se encarga de activa y destivar los botones según 
	 * sea necesario
	 * @param sw true: signifca que los botones tienes que estar visibles
	 * 			false: los botones tienes que estar escondidos.
	 */			
	public void enablePlayerModes(boolean sw)
	{
		botonesActivados=sw;
		jbIntelligent.setEnabled(sw);
		jbRandom.setEnabled(sw);
	}
	/**
	 * metodo abstracto que realiza un movimiento random o inteligente.
	 * @param sw true: movimiento inteligente
	 * 			false: movimiento random
	 */			
  abstract public void movimientoAutomatico(boolean sw);
 
}
