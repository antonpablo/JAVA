package es.ucm.fdi.tp.practica5.connectn;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.views.RectBoardSwingView;
/**
 * clase que crea la interfaz grafica del juego connectaN
 * @author Pablo-Por
 *
 */
public class ConnectNSwingView extends RectBoardSwingView{
	

	private static final long serialVersionUID = 1L;
		private ConnectNSwingPlayer player;
		/**
		 * contructora de la parte grafica del juego de connectaN
		 * @param g observadores del juego
		 * @param c controlador del juego
		 * @param p piece si esta en multiventa sino es null.
		 * @param random player para realizar movimientos random
		 * @param ai player para realizar movimiento inteligentes.
		 */
		public ConnectNSwingView(Observable<GameObserver> g, Controller c, Piece p, Player random, Player ai) {
		super(g, c, p,random,ai);
		player = new ConnectNSwingPlayer();
		}
		/**
		 * motodo que recoje los eventos del raton del tablero 
		 * y realiza un movimiento del juego connnectaN
		 */
		@Override
		protected void handleMouseClick(int row, int col, int mouseButton) {
		// do nothing if the board is not active
		/**/
		player.setMove(row, col);
		decideMakeManualMove(player);
		
		}
		
	/**
	 * activa el tablero del juego
	 */
		@Override
		protected void activateBoard() {

			pantallaMouse(true);
			addMsg("selecione una cell para mover");
			
		}
		/**
		 * desativa el tablero del juego
		 */
		@Override
		protected void deActivateBoard() {
			pantallaMouse(false);
		}
		/**
		 * no se utilzia en este juego porque el movimiento 
		 * es simple
		 */
	
	
	
		
		
	

}
