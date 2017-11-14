package es.ucm.fdi.tp.practica5.ataxx;

import java.util.List;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxMove;

/**
 * clase player ataxx para crear un movimineto del juego ataxx
 * @author Pablo-Por
 *
 */
public class AtaxxSwingPlayer extends Player{

	
	private static final long serialVersionUID = 1L;
	private int inicialRow;
	private int inicialCol;
	private int finalRow;
	private int finalCol;
	
	/**
	 * metodo que guarda la casilla origen
	 * @param inicialRow fila de origen ficha.
	 * @param inicialCol columna de origen ficha.
	 */
	public void setMovePrimero(int inicialRow, int inicialCol) {
		this.inicialRow=inicialRow;
		this.inicialCol=inicialCol;
	}
	
	/**
	 * metodo para guaradar las casilla destino
	 * @param finalRow fila de destino
	 * @param finalCol columan de destino
	 */
	public void setMoveSegundo(int finalRow,int finalCol)
	{
		this.finalRow=finalRow;
		this.finalCol=finalCol;
	}

	/**
	 * metodo que crea y devuelve un movimiento de ataxx de la clase AtaxxMove.
	 */
@Override
public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
	// TODO Auto-generated method stub
	return new AtaxxMove(inicialRow, inicialCol, finalRow, finalCol, p);
}


}
