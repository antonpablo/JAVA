package es.ucm.fdi.tp.practica5.connectn;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNFactory;
import es.ucm.fdi.tp.practica5.ataxx.AtaxxSwingView;

/**
 * clase connectaNFactory extendida para la interfaz grafica
 * @author Pablo-Por
 *
 */
public class ConnectNFactoryExt extends ConnectNFactory{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * contructor por defecto d connecta n
	 */
	public ConnectNFactoryExt() {
		super();
	}
	/**
	 * contructor que adminte como parametro la dimensión del tablero
	 * @param dim
	 */
	public ConnectNFactoryExt(int dim) {
		super(dim);
	}
	/**
	 * metodo el cual crea una interfaz grafica para el juego connectaN.
	 */
	@Override
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				 new ConnectNSwingView(g,c,viewPiece,random,ai );
			}
			});
			} catch (InvocationTargetException | InterruptedException e) {
			throw new GameError("error vista juego");
			}
		
	}

}
