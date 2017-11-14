package es.ucm.fdi.tp.practica5.ttt;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.ttt.TicTacToeFactory;
import es.ucm.fdi.tp.practica5.ataxx.AtaxxSwingView;
import es.ucm.fdi.tp.practica5.connectn.ConnectNSwingView;
/**
 * clase TicTacToeFactory extendida para la crearción de la interfaz grafica
 * del juego TicTacToe
 * @author Pablo-Por
 *
 */

public class TicTacToeFactoryExt  extends TicTacToeFactory{

	private static final long serialVersionUID = 1L;
	/**
	 * contructor sin argumentos del juego
	 */
	public TicTacToeFactoryExt()
	{
		super();
	}
	/**
	 * meotod 
	 */
	@Override
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				 new ConnectNSwingView (g,c,viewPiece,random,ai );
			}
			});
			} catch (InvocationTargetException | InterruptedException e) {
			throw new GameError("error vista juego");
			}
		
	}


}
