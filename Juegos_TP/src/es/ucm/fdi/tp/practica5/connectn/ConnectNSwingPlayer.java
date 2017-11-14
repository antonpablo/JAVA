package es.ucm.fdi.tp.practica5.connectn;

import java.util.List;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;
/**
 * clase representa un jugador de conectaN.
 * @author Pablo-Por
 *
 */
public class ConnectNSwingPlayer extends Player{


	
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	
	/**
	 * metodo el cual sirve para guardar una casilla para poder colocar una ficha
	 * en esa posicion
	 * @param row fila de destino
	 * @param col columna de destino
	 */
	public void setMove(int row, int col) {
		this.row=row;
		this.col=col;
		
	}
/**
 * metodo el cual se encarga de crear un movimiento del tipo ConnecctaNMove.
 */

@Override
public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
	return new ConnectNMove(row,col,p);
}

}
