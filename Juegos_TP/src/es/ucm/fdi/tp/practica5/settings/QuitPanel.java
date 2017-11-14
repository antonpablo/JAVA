package es.ucm.fdi.tp.practica5.settings;

import java.awt.event.*;
import javax.swing.*;
/**
 * clase la cual tiene los botones Quit y Restart del juego
 * @author Pablo-Por
 *
 */
public abstract class QuitPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton jbRestart;
	private JButton jbQuit;
	private boolean botonesActivados;
	/**
	 * contructor de QuitPanel
	 */
	public QuitPanel() {
		
		
		super();

		botonesActivados=true;
		initialize();
	}

	/**
	 * metodo que inicializa la parte grafica de los botones de Quit y Restart
	 */
	private void initialize() {
		jbQuit = new JButton("Quit");
		jbQuit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
			if (botonesActivados)	
				tipoSalir();
			}			
		});	
		add(jbQuit);	
		
		jbRestart = new JButton("Restart");
		jbRestart.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (botonesActivados)	
					tipoRestart();
			}			
		});	
		add(jbRestart);
	}
/**
 * motod que se encarga de quitar de la vista el boton Restart en el 
 * modo multiventana 
 */
	public void destivarRestart()
	{
		jbRestart.setVisible(false);
	}
/**
 * metodo que se encarga de poner o quitar la visibilidad del boton Restart
 * @param sw true: activar boton
 * 			false: desactivar boton
 */			
	public void enableRestart(boolean sw)
	{
		jbRestart.setEnabled(sw);
		botonesActivados=sw;	
	}
	/**
	 * metodo que se encarga de poner o quitar la visibilidad del boton Quit
	 * @param sw true: activar boton
	 * 			false: desactivar boton
	 */	
	public void enableQuit(boolean sw)
	{
		jbQuit.setEnabled(sw);
		botonesActivados=sw;
	}
	
/**
 * metodo abstracto que se encarga de salir del juego
 */
	public abstract void tipoSalir();
	
	/*
	 * metodo abstracto que se encargar de restart el juego
	 */
	public abstract void tipoRestart();
}
