package es.ucm.fdi.tp.practica5.attt;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.views.RectBoardSwingView;
/**
 * clase para crear la interfaz grafica del juego AdvanceTTT.
 * @author Pablo-Por
 *
 */
public class AdvancedTTTSwingView extends RectBoardSwingView{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AdvancedTTTSwingPlayer player;
	boolean moveOk;
	
	/**
	 * contructora del juego de AdvancedTT que inicializa la parte grafica del juego
	 * @param g observadores del juego
	 * @param c controlador del juego
	 * @param p piece si esta en modo multiventa para crear las ventanas, se es venta unica esta a null
	 * @param random jugador que realiza un movimiento random
	 * @param ai juegador que realiza un movimiento inteligente
	 */
	public AdvancedTTTSwingView(Observable<GameObserver> g, Controller c, Piece p, Player random, Player ai) {
		super(g, c, p, random, ai);
		player=new AdvancedTTTSwingPlayer();
		
	 
	}
/**
 * metodo que recoje los eventos del raton y realiza un movimiento manual
 * del juego advancedTTT.
 */
	@Override
	protected void handleMouseClick(int row, int col, int mouseButton) {
		// TODO Auto-generated method stub

			
			if (0<getBoard().getPieceCount(getTurn()))
			{
				player.setMovePrimero(-1, -1);
				player.setMoveSegundo(row, col);
				decideMakeManualMove(player);
			}
			else
			{
					if (mouseButton==3)
					{
						moveOk=false;
						botonesVisibles(true);
						addMsg("selecione una piece de origen");
					}
				
					else if(mouseButton==1)
					{
						if (moveOk==false)
						{
							if (comprobarCasillaValida(row,col))
							{
								player.setMovePrimero(row, col);
								moveOk=true;
								addMsg("selecione casilla destino");
							}
						
						}	
						else
						{
							player.setMoveSegundo(row, col);
							moveOk=false;
							decideMakeManualMove(player);
						}
					}
		
		}
			
	
		
	}
		
	
/**
 * metodo que activa el tablero del juego
 */
	

	@Override
	protected void activateBoard() {
		// TODO Auto-generated method stub
		pantallaMouse(true);
		addMsg("selecione una piece de origen");
	}
/**
 * metodo que desactiva el tablero del juegdo
 */
	@Override
	protected void deActivateBoard() {
		// TODO Auto-generated method stub
		pantallaMouse(false);
	}
/**
 * cambiar movimiento en caso de que se un movimiento el cual
 * requiera escojer piece destino.
 */



}
