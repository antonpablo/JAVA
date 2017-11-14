package es.ucm.fdi.tp.practica5.settings;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
/**
 * clase que cambia el color de una piece
 * @author Pablo-Por
 *
 */
public abstract class PieceColorChooser extends JPanel {



	private List<Piece> pieces;
	JComboBox<Piece> jcbPieces ;
	private static final long serialVersionUID = 1L;
/**
 * contrucot que utiliza la lista de pieces para el comboBox, para poder escojer 
 * una de las pieces del juego.
 * @param piece piece que quieres poner o modificar color
 */
	public PieceColorChooser() {
		jcbPieces = new <Piece>JComboBox();
		setLayout(new FlowLayout());
		add(jcbPieces);
		JButton jbChange = new JButton("Change Color");
		jbChange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				
				Color c = JColorChooser.showDialog(PieceColorChooser.this, "Color chooser", null);
				Piece piece= (Piece) jcbPieces.getSelectedItem();
				if (c!=null)
					cambiarColor(piece, c);
			}
		});
		this.add(jbChange);
	}
	
	public void setPieces(List<Piece> p)
	{
		pieces=p;
		for (Piece pp: p)
		jcbPieces.addItem(pp);
	}
	/**
	 * metodo abstracto el cual se encargar d cambiar el Map<Piece,Color>
	 * de swingView
	 * @param p piece que quieres cambiar color
	 * @param c color que quieres cambiar
	 */
	public abstract void cambiarColor(Piece p,Color c);
}
