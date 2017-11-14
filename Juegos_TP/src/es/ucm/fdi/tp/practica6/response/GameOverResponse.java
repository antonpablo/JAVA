package es.ucm.fdi.tp.practica6.response;

import com.sun.corba.se.spi.orbutil.fsm.State;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameOverResponse implements Response{

	private Board board;
	private Game.State state;
	private  Piece winner;
	
	public GameOverResponse(Board board,Game.State state, Piece winner)
	{
		this.board=board;
		this.state=state;
		this.winner=winner;
	}
	@Override
	public void run(GameObserver o) {
		// TODO Auto-generated method stub
		o.onGameOver(board, state, winner);
	}

}
