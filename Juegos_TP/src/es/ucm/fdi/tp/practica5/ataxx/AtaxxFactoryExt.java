package es.ucm.fdi.tp.practica5.ataxx;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxFactory;
import es.ucm.fdi.tp.practica5.attt.AdvancedTTTSwingView;

/**
 * Clase ataxx factory extendida para crear la vista del juego.
 * @author Pablo-Por
 *
 */
public class AtaxxFactoryExt extends AtaxxFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor del juego por defecto
	 * tablero e 7x7 con 3 obstacutos
	 */
	public AtaxxFactoryExt() {
		super();
	}
	
	/**
	 * contructor que recibe la dimensión y 
	 * el numero de obstaculos
	 * @param dim
	 * @param obstacles
	 */
	public AtaxxFactoryExt(int dim, int obstacles) {
		super(dim,obstacles);
	}
	
	/**
	 * metodo para crear la vista del juego con swingview
	 */
	@Override
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				 new AtaxxSwingView(g,c,viewPiece,random,ai );
			}
			});
			} catch (InvocationTargetException | InterruptedException e) {
			throw new GameError("error vista juego");
			}
		
	}

}
