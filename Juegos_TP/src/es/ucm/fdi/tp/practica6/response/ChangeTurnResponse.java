package es.ucm.fdi.tp.practica6.response;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class ChangeTurnResponse implements Response{
	
	Board board;
	Piece turn;
	
	public ChangeTurnResponse(Board board, Piece turn)
	{
		this.board=board;
		this.turn=turn;
		
	}

	@Override
	public void run(GameObserver o) {
	o.onChangeTurn(board, turn);
	}

}
