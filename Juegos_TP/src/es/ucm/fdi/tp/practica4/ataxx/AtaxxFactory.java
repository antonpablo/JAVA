package es.ucm.fdi.tp.practica4.ataxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.ConsolePlayer;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.DummyAIPlayer;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.views.GenericConsoleView;

/**
clase que implementa una factoria del juego ataxx
 */
public class AtaxxFactory implements GameFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dim;
	private int obstaculos;
	
	/**
	 * contructor sin argumentos con un tablero d 7x7 y 3 obstaculos
	 */
	public AtaxxFactory() {
		this(7,3);
	}

	/**
	 * constructor AttaxFactory el cual inicializa la dimension del tablero
	 * y los obstaculos
	 * @param dim dimension del tablero
	 * @param obs obstaculos del juego
	 */
	public AtaxxFactory(int dim,int obs) {
		if (dim < 5 || dim%2==0) {
			throw new GameError("Dimension must be at least 3: " + dim);
		} else {
			this.dim = dim;
			this.obstaculos=obs;
		}
	}
/**
 * metodo que inicializa las reglas del juego 
 */
	@Override
	public GameRules gameRules() {
		return new AtaxxRules(dim,obstaculos);
	}

	/**
	 * jugador por consola 
	 */
	@Override
	public Player createConsolePlayer() {
		ArrayList<GameMove> possibleMoves = new ArrayList<GameMove>();
		possibleMoves.add(new AtaxxMove());
		return new ConsolePlayer(new Scanner(System.in), possibleMoves);
	}
/**
 * jugador random
 */
	@Override
	public Player createRandomPlayer() {
		return new AtaxxRandomPlayer();
	}

	@Override
	public Player createAIPlayer(AIAlgorithm alg) {
		return new DummyAIPlayer(createRandomPlayer(), 1000);
	}

	/**
		lista de piezas por defecto. 2 piezas por defecto
	 */
	@Override
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("X"));
		pieces.add(new Piece("O"));
		return pieces;
	}

	@Override
	public void createConsoleView(Observable<GameObserver> g, Controller c) {
		new GenericConsoleView(g, c);
	}

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer,
			Player aiPlayer) {
		// TODO Auto-generated method stub
		
	}

	

}
