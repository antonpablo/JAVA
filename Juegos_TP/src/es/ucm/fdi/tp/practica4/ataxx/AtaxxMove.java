package es.ucm.fdi.tp.practica4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * 
 * @author Pablo-Por
 *	 Clase la cual implementa un movimento de una pieza en el tablero
 */
public class AtaxxMove extends GameMove {

	
	private static final long serialVersionUID = 1L;

	
	protected int finalRow;
	
	protected int finalCol;

	protected int inicialRow;
	
	protected int inicialCol;
	
	/**
	 * contructor sin argumentos
	 */
	public AtaxxMove() {
	}

	/**
	 * metodo el cual realiza las operaciones necesaria para que un movimiento sea valido
	 * y realizar ese movimiento deacuerdo con las reglas.
	 * @param inicialrow fila de la ficha origen
	 * @param inicialcol columna de la ficha origen
	 * @param finalrow fila de la ficha destino
	 * @param finalcol columna de la ficha destino
	 * @param p pieza del jugador que le toca mover
	 */
	public AtaxxMove(int inicialrow, int inicialcol,int finalrow,int finalcol, Piece p) {
		
		
		super(p);
		this.inicialRow = inicialrow;
		this.inicialCol = inicialcol;

		this.finalRow = finalrow;
		this.finalCol = finalcol;
	}
	
/**
 * metodo el cual ejecuta un movimiento de una ficha 
 */
	@Override
	public void execute(Board board, List<Piece> pieces) {
		
		
		if (board.getPosition(finalRow, finalCol)==null ) {
			
			if ( getPiece().equals(board.getPosition(inicialRow, inicialCol)))
			{ 
				
				int dist = Math.max( Math.abs(inicialRow-finalRow), Math.abs(inicialCol-finalCol));
				
				
				//iff la distancia es 2 y si la distancia es 1
				if(dist<=2)
				{
					if (dist==2 )
						board.setPosition(inicialRow, inicialCol, null);
					
					board.setPosition(finalRow, finalCol, getPiece());	
					buscarPiezas(board,pieces);
				}
				else {
					throw new GameError("no se puede mover a esa casilla tan lejana");
				}
				
				
			}else {
				throw new GameError("error no tienes piece en la pos:("+inicialRow+","+inicialCol+")");
			}
			
		} else {
			throw new GameError("position (" + finalRow + "," + finalCol + ") is already occupied!");
		}
	}

	/**
	 * metodo que busca las fichas de alrededor del movimiento para realizar el cambio
	 * de ficha.
	 * @param board tablero del juego
	 */
	public void buscarPiezas(Board board, List<Piece> pieces)
	{
		int incrFilas []=   {-1,-1,-1, 0, 0, 1, 1, 1};
		int incrColumnas []={-1, 0, 1,-1, 1,-1, 0, 1 };	
		 for(int i=0;i<incrFilas.length;i++)
			{
				int nf=finalRow+incrFilas[i];
				int nc=finalCol+incrColumnas[i];
				
				if (nc>=0 && nc<board.getRows() && nf>=0 && nf<board.getRows())
				{
					if(board.getPosition(nf, nc)!=null && pieces.contains(board.getPosition(nf, nc)))
						{
							board.setPosition(nf, nc, getPiece());
						}
				}
			}
	}
	/**
	
	 contruir un movimiento desde consola 
	 inicialrow es la fila de la casilla origen
	 inicialcol es la columna de la casilla origen
	 finalrow es la fila de la casilla destino
	 finalcol es la columna de la casilla destino
	 */
	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 4) {
			return null;
		}

		try {
			int finalRow, finalCol , inicialrow, inicialcol  ;
			
			inicialrow=Integer.parseInt(words[0]);
			inicialcol = Integer.parseInt(words[1]);
			
			finalRow = Integer.parseInt(words[2]);
			finalCol = Integer.parseInt(words[3]);
			
			return createMove(inicialrow, inicialcol,finalRow,finalCol, p);
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * crea un nuevo movimiento en el tablero
	 * @param row fila de la casilla origen
	 * @param col columna de la casilla origen
	 * @param posrow fila de la casilla destino
	 * @param poscol es la columna de la casilla destino
	 * @param p pieza del jugador que va a realizar el movimiento
	 * @return un nuevo movimiento que se a creado
	 */
	protected GameMove createMove(int row,int col, int posrow,int poscol, Piece p) {
		return new AtaxxMove(row, col,posrow,poscol, p);
	}

	/**
	 * ayuda del juego ataxx
	 */
	@Override
	public String help() {
		return "'inicialrow inicialcolumn finalrow finalcolumn', to place a piece at the corresponding position.";
	}

	/**
	 * informacion del movimiento realizado
	 */
	@Override
	public String toString() {
		if (getPiece() == null) {
			return help();
		} else {
			return "Se move la pieza: '" + getPiece() + " de (" + inicialRow + "," + inicialCol + ")"+ " hacia (" + finalRow + "," + finalCol + ")";
		}
	}
	
}
