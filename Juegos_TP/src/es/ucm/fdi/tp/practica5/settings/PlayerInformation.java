package es.ucm.fdi.tp.practica5.settings;


import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.control.PlayerMode;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.practica5.settings.FormatoColorTabla;

/**
 * clase que muestra Player Information en la parte gráfica de settings del juego 
 * @author Pablo-Por
 *
 */
public class PlayerInformation extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTable jtTable;
	private Object[][] data;
	private FormatoColorTabla coloresTabla;
	private List<Piece> pp;

	/**
	 * contructor que inicializa la tabla con el nombre de cada piece
	 * @param b board del juego
	 * @param pieces lista de pieces del juego
	 */
	public PlayerInformation() {
		this.setBorder(
				new TitledBorder(null, "Player Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		data = new Object[4][3];
		coloresTabla= new FormatoColorTabla();
		String[] columnNames = { "Player", "Mode", "#Pieces" };
		jtTable = new JTable(data, columnNames) {
			private static final long serialVersionUID = 1L;
		};
		coloresTabla=new FormatoColorTabla();
		jtTable.setPreferredScrollableViewportSize(jtTable.getPreferredSize());
		jtTable.setFillsViewportHeight(true); // Fills the empty space with white
		JScrollPane jspScroll = new JScrollPane(jtTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(jspScroll);
		jtTable.setDefaultRenderer (Object.class,coloresTabla );
		
	}

	

/**
 * metodo el cual se encarga de guardar el nombre de las pieces.
 * @param pieces
 */
	protected void setPieces(List<Piece> pieces) {
		pp=pieces;
		for (int i = 0; i < pieces.size(); i++) {
			data[i][0] = pieces.get(i);
		}
	}

/**
 * metodo que se encargar de mostrat las cuenta de pieces de un juego
 * @param p piece que se quiere modificar 
 * @param num numero de pieces.
 */
	protected void setNumPieces(Piece p,int num) {
		
		jtTable.setValueAt(num,pp.indexOf(p),2);
		repaint();
	}
	
	/**
	 * metodo que se encarga de actulizar el modo de jugador
	 * @param p jugador que quieres actualizar el modo
	 * @param m modo de jugador que quieres actualizar
	 */
	protected void updateModes(Piece p,PlayerMode m) {
		int pieceIndex=pp.indexOf(p);
		String modePlayer=m.getDesc();
		jtTable.setValueAt(modePlayer, pieceIndex, 1);
		repaint();
	
	}

	/**
	 * metodo que se encargar de dibujar en la tabla los colores de las pieces
	 * @param p piece que quiere poner o cambiar color 
	 * @param c color que quieres poner a esa piece.
	 */
	protected void colorUpdat(Piece p,Color c) {
		int pieceIndex=pp.indexOf(p);
		coloresTabla.getColores(pieceIndex, c);
		jtTable.setDefaultRenderer (Object.class,coloresTabla );
		repaint();
	}

}
