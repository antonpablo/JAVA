package es.ucm.fdi.tp.practica5.attt;

import java.util.List;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTMove;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * clase player de advancedttt para que un jugador del juego advanceTTT realice movim
 * @author Pablo-Por
 *
 */
public class AdvancedTTTSwingPlayer extends Player{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int inicialRow;
	private int inicialCol;
	private int finalRow;
	private int finalCol;
	/**
	 *metodo que guarda la casilla origen 
	 * @param inicialRow fila origen
	 * @param inicialCol collumna origen
	 */
	public void setMovePrimero(int inicialRow, int inicialCol) {
		this.inicialRow=inicialRow;
		this.inicialCol=inicialCol;
	}
	/**
	 * metodo que guarda casilla destino
	 * @param finalRow fila destino
	 * @param finalCol columna destino
	 */
	public void setMoveSegundo(int finalRow,int finalCol)
	{
		this.finalRow=finalRow;
		this.finalCol=finalCol;
	}
/**
 * metodo que realiza un movimiento del juego AdvanceTTTMove
 */
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
	// TODO Auto-generated method stub
	return  new AdvancedTTTMove(inicialRow, inicialCol, finalRow, finalCol, p);
}


}
