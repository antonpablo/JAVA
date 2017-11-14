package es.ucm.fdi.tp.practica4.ataxx;

import java.util.List;
import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * A random player for ConnectN.
 * 
 * <p>
 * Un jugador aleatorio para ConnectN.
 *
 */
public class AtaxxRandomPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  metodo el cual crea un movimiento de una ficha aleatoriamente
	 */

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		List<GameMove> moves = rules.validMoves(board, pieces, p);

		if (moves.size() > 0) {
			return moves.get(Utils.randomInt(moves.size()));
		} else {
			throw new GameError("The board is full, cannot make a random move!!");
		}
	}

	

}
