package es.ucm.fdi.tp.practica4.ataxx;

import java.util.ArrayList;
import java.util.List;
import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

/**
 * clase la cual implementa ls reglas del juego
 *
 */
public class AtaxxRules implements GameRules {

	// This object is returned by gameOver to indicate that the game is not
	// over. Just to avoid creating it multiple times, etc.
	//
	protected final Pair<State, Piece> gameInPlayResult = new Pair<State, Piece>(State.InPlay, null);

	private int dim;
	private int obstaculos;

	/**
	 * constructor AtaxxRules sin argumento
	 */
	public AtaxxRules() {
	}

	/**
	 * constructor AtaxxRules con argumentos
	 * 
	 * @param dim
	 *            dimension del tablero
	 * @param obs
	 *            obstaculos que hay en el tablero
	 */
	public AtaxxRules(int dim, int obs) {
		if (dim < 5 && dim % 2 == 1) {
			throw new GameError("Dimension must be at least 5: " + dim);
		} else {
			this.dim = dim;
			this.obstaculos = obs;
		}
	}

	/**
	 * información del juego y del tablero
	 */
	@Override
	public String gameDesc() {
		return "Ataxx " + dim + "x" + dim;
	}

	/**
	 * Metodo que crea el tablero inicial y coloca las piezas iniciales de cada
	 * jugador
	 */
	@Override
	public Board createBoard(List<Piece> pieces) {

		Board bb = new FiniteRectBoard(dim, dim);
		bb.setPosition(0, 0, pieces.get(0));
		bb.setPosition(dim - 1, dim - 1, pieces.get(0));

		bb.setPosition(0, dim - 1, pieces.get(1));
		bb.setPosition(dim - 1, 0, pieces.get(1));

		if (pieces.size() > 2) {
			bb.setPosition(dim / 2, 0, pieces.get(2));
			bb.setPosition(dim / 2, dim - 1, pieces.get(2));

		}
		if (pieces.size() > 3) {
			bb.setPosition(0, dim / 2, pieces.get(3));
			bb.setPosition(dim - 1, dim / 2, pieces.get(3));

		}

		int cont = 0;
		int max = 10 * dim * dim / 100;
		Piece o = getObstPiece(pieces);

		if (obstaculos <= max) {
			while (cont < obstaculos) {
				int fila = Utils.randomInt(dim);
				int columna = Utils.randomInt(dim);
				if (bb.getPosition(fila, columna) == null) {
					bb.setPosition(fila, columna, o);
					cont++;
				}
			}
		}

		return bb;

	}

	private Piece getObstPiece(List<Piece> l) {
		int i = 0;
		while (true) {
			Piece o = new Piece("*#" + i);
			if (!l.contains(o))
				return o;
			i++;
		}
	}

	/**
	 * inicializa el primer jugador de la partida
	 */

	@Override
	public Piece initialPlayer(Board board, List<Piece> playersPieces) {

		return nextPlayer(board, playersPieces, playersPieces.get(playersPieces.size() - 1));

	}

	/**
	 * minimo de jugadores que pueden jugar
	 */
	@Override
	public int minPlayers() {
		return 2;
	}

	/**
	 * maximo jugadores que pueden jugar
	 */
	@Override
	public int maxPlayers() {
		return 4;
	}

	/**
	 * metodo el cual actualiza el estado del juego y se encargar de eliminar a
	 * un jugador si se queda sin fichas o si la partida a terminado
	 */

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> playersPieces, Piece lastPlayer) {

		// falta saber si solo hay piezas de un jugador
		// termina la partida
		if (nextPlayer(board, playersPieces, lastPlayer) == null) {

			int numeroPiezas[] = new int[playersPieces.size()];

			int contMax = 0;
			int max = 0;
			int indexMax = 0;
			for (int i = 0; i < playersPieces.size(); i++) {

				numeroPiezas[i] = countPieces(board, playersPieces.get(i));

				if (max < numeroPiezas[i]) {
					max = numeroPiezas[i];
				}

			}
			for (int i = 0; i < playersPieces.size(); i++) {

				if (max == numeroPiezas[i]) {
					contMax++;
					indexMax = i;
				}

			}

			if (contMax == 1)
				return new Pair<State, Piece>(State.Won, playersPieces.get(indexMax));
			else
				return new Pair<State, Piece>(State.Draw, null);

		} else {
			return gameInPlayResult;
		}
	}

	public int countPieces(Board b, Piece p) {
		int cont = 0;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (p.equals(b.getPosition(i, j))) {
						cont++;
				}
			}
		}

		return cont;

	}

	/**
	 * metodo el cual seleciona el siguiente jugador que puede jugar
	 */
	@Override
	public Piece nextPlayer(Board board, List<Piece> playersPieces, Piece lastPlayer) {

		int pie = playersPieces.indexOf(lastPlayer);

		// Mirar que jugador puede mover y si solo tiene un jugador fichas en el
		// tablero o no quedan
		// movimientos entoncs return null

		for (int i = pie; i < playersPieces.size() + pie - 1; i++) {

			Piece siguienteJugador = playersPieces.get((i + 1) % playersPieces.size());

			if (validMoves(board, playersPieces, siguienteJugador).size() > 0) {
				return siguienteJugador;
			}

		}
		// tiene que devolver el siguiente jugador que puede jugar sino devuelve
		// null
		return null;
	}


	/**
	 * valida si un jugador tiene algun movimiento posible en el tablero
	 */
	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		List<GameMove> moves = new ArrayList<GameMove>();

		// buscar las piezas del jugador que este moviendo y guardar los
		// posibles movimentos de cada pieza
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getCols(); j++) {

				if (board.getPosition(i, j) != null) {
					if (board.getPosition(i, j).equals(turn)) {
						// recorrer todas las posiciones donde se puede mover
						// esa pieza
						for (int x = i - 2; x <= i + 2; x++) {
							for (int y = j - 2; y <= j + 2; y++) {
								if ( !(x==i && y==j) && x >= 0 && y >= 0 && x < dim && y < dim && board.getPosition(x, y) == null) {
									moves.add(new AtaxxMove(i, j, x, y, turn));
								}
							}
						}

					}
				}
			}

		}
		return moves;
	}

	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn, Piece p) {
		
	
			double m = 0;
			double n = 0;
		/*	int num
			int [] arraycoun=new int [pieces.size()];
			for (int i=0;i<pieces.size();i++)
			{
				
			}*/
					
			// 1. ’n’ contiene el numero de fichas de
			// tipo ’p’ en el tablero ’board’
			// 1. ’m’ contiene el numero de fichas de
			// tipo distinto de ’p’ en el tablero ’board’
			// (sin contar los obstaculos)
			 double total = n+m;
			 return (n / total) - (m / total);
	}

}
