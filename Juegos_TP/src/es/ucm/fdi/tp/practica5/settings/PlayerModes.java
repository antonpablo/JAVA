package es.ucm.fdi.tp.practica5.settings;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.control.PlayerMode;
/**
 * clase la cual puedes cambiar el modo de juego de un piece
 * @author Pablo-Por
 *
 */
public abstract class PlayerModes extends JPanel {

	
	private List<Piece> pieces;
	private static final long serialVersionUID = 1L;
	JComboBox<Piece> jcbPlayer;
	private Piece localPiece;
	JComboBox<String> jcbMode;
	/**
	 * contructor de PlayerMode 
	 * @param pieces
	 */
	public PlayerModes() {
		pieces=new ArrayList<>();
		initialize();
	}

	/**
	 * metodo que se encarga de inicializar la parte grafica de PlayerMode
	 * para poder cambiar el modo de un jugador
	 */
	private void initialize() {
		setBorder(new TitledBorder(null, "Player Modes", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		jcbPlayer = new JComboBox<Piece>();
		add(jcbPlayer);

		 jcbMode = new JComboBox<String>();
		add(jcbMode);

		JButton jbSet = new JButton("Set");
		
		
		jbSet.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				Piece piece = (Piece) jcbPlayer.getSelectedItem();
				String mode = (String) jcbMode.getSelectedItem();
				Piece pp=null;
				if (localPiece==null)
					pp=piece;
				else
					pp=localPiece;
				PlayerMode mm=null;
				if (mode.compareToIgnoreCase("Random")==0)
					mm= new PlayerMode("r","Random");
				else if  (mode.compareToIgnoreCase("Inteligent")==0)
					mm= new PlayerMode("a","Inteligent");
				else
					mm= new PlayerMode("m","Manual");
				changeMode(pp, mm);
				;
			}
		});
		add(jbSet);
	}
	
	public void setPieces(Piece p)
	{
		pieces.add(p);
		jcbPlayer.addItem(p);
	}
	
	public void setModo(String res)
	{
		jcbMode.addItem(res);
	}
	public void setLocalPiece(Piece p)
	{
		localPiece=p;
	}
/**
 * metodo abstracto que se encarga de cambiar en swingView el modo de un jugador
 * @param p piece la cual se quiere cambiar el modo
 * @param m modo al que se queire cambiar
 */
public abstract void changeMode(Piece p,PlayerMode m);
}
