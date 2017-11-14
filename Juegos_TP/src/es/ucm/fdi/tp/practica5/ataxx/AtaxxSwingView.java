package es.ucm.fdi.tp.practica5.ataxx;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.views.RectBoardSwingView;

/**
 * clase para crear la interfaz grafica del juego ataxx.
 * @author Pablo-Por
 *
 */
public class AtaxxSwingView extends RectBoardSwingView{
	
	
	private static final long serialVersionUID = 1L;
	private AtaxxSwingPlayer player;
	private boolean moveOk;
/**
 * contrutora de la vista del juego ataxx que inicializa la vista grafica del juego
 * @param g lista de observadores del juego.
 * @param c controlador del juego
 * @param p piece para la vista multiventana. Si esta null es unica ventana
 * @param random jugador random realiza un movimiento random
 * @param ai jugador inteligente que realiza un movimiento inteligente
 */
	public AtaxxSwingView(Observable<GameObserver> g, Controller c, Piece p, Player random, Player ai) {
		super(g, c, p, random, ai);
		// TODO Auto-generated constructor stub
		player=new AtaxxSwingPlayer();
		moveOk=false;
	}

	/**
	 * metodo que recoje los eventos del raton realizados en en el tablero del juego.
	 * El cual nos permite realizar un movimiento manual
	 */
	@Override
	protected void handleMouseClick(int row, int col, int mouseButton) {
		// TODO Auto-generated method stub
		
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
		
	

	
/**
 * activar el tablero del juego  para realizar movimientos
 */
	@Override
	protected void activateBoard() {
		// TODO Auto-generated method stub
		pantallaMouse(true);
		addMsg("selecione una piece de origen");
	}
/**
 * desactivar tablero para que no se puedan realizar movimientos.
 */
	@Override
	protected void deActivateBoard() {
		// TODO Auto-generated method stub
		pantallaMouse(false);
	}

	

}
