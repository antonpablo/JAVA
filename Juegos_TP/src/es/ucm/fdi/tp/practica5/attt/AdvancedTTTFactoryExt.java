package es.ucm.fdi.tp.practica5.attt;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.basecode.attt.AdvancedTTTFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * clase AcvenceTTFActory extendida para la interfaz grafica
 * @author Pablo-Por
 *
 */
public class AdvancedTTTFactoryExt extends AdvancedTTTFactory{
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * contructora por defecto de Attt.
	 */
	public AdvancedTTTFactoryExt()
	{
		super();
	}
	/**
	 * creacion de una nueva vista de AdvanceTT para la interfaza grafica del juegeo.
	 */
	@Override
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
			try {
			SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				new AdvancedTTTSwingView(g, c, viewPiece, random, ai);
			}
			});
			} catch (InvocationTargetException | InterruptedException e) {
			throw new GameError("error vista juego");
			}
	}
	

}
