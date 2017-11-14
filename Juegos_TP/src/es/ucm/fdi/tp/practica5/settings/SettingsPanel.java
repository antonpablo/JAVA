package es.ucm.fdi.tp.practica5.settings;

import java.awt.Color;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.sun.media.sound.ModelAbstractOscillator;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.control.PlayerMode;
/**
 * clase encargada de las opciones de todos los juegos
 * @author Pablo-Por
 *
 */
public abstract class SettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private StatusMessages textArea;
	private PlayerInformation table;
	private PieceColorChooser colorChooser;
	private PlayerModes modeSelector;
	private AutomaticMoves autoMovPane;
	private QuitPanel quitPanel;
	private boolean random;
	private boolean inteligente;
	/**
	 * contructor de las opciones del juego
	 * @param p lista de pieces del juego
	 * @param b board del juego
	 * @param sw boolean para saber si los modos de juego random y inteligente 
	 * se pueden realizar o no
	 */
	public SettingsPanel(boolean ran,boolean in) {
	
		initComponents(ran, in);
	}
/**
 * inicializa la parte visual de las opciones del juego
 * @param pieces lista de pieces del juego
 * @param board tablero del juego
 */
	private void initComponents(boolean ran,boolean in){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		textArea = new StatusMessages(); // Revisar esta clase
		add(textArea);

		// Player Information Table
		table = new PlayerInformation();
		add(table);

		// Piece Colors Chooser
		colorChooser = new PieceColorChooser() {
			
			@Override
			public void cambiarColor(Piece p,Color c) {
				colorUpdate(p,c);
			}
		};
		colorChooser
				.setBorder(new TitledBorder(null, "Piece Colors", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(colorChooser);

		
			// Player Mode Selector
			modeSelector = new PlayerModes() {
				
				@Override
				public void changeMode(Piece p,PlayerMode m) {
					playerMode(p, m);
					
					
				}

			};
			add(modeSelector);
		
			// Automatic Moves Panel
			autoMovPane = new AutomaticMoves(ran,in) {
				
				@Override
				public void movimientoAutomatico(boolean sw) {
					movimientoAuto(sw);
					
				}
			};
			add(autoMovPane);

		
		
		// Quit Selection Panel
		quitPanel = new QuitPanel() {
			
			@Override
			public void tipoSalir() {
				quitGame();
				
			}

			@Override
			public void tipoRestart() {
				restartGame();
				
			}
		};
		add(quitPanel);
	}
	/**
	 * metodo que se encargar de la visibilidad del boton restart
	 * @param sw visibilidad del boton
	 */
	public void setPieces(List<Piece> p)
	{
		table.setPieces(p);
		colorChooser.setPieces(p);
	}
	public void setPieceMode(Piece p)
	{
		modeSelector.setPieces(p);
	}
	public void modoJuego(String mode)
	{
		modeSelector.setModo(mode);
	}
	
	public void restart(boolean sw)
	{
		quitPanel.enableRestart(sw);
	}
	/**
	 * metodo que se encargar de la visibilidad del boton quit
	 * @param sw visibilidad del boton
	 */
	public void quit(boolean sw)
	{
		quitPanel.enableQuit(sw);
	}
	/**
	 * metodo que se encargar de la visibilidad de los botones random y inteligent
	 * @param sw visibilidad de los 2 botones
	 */
	public void playermodes(boolean sw)
	{
		autoMovPane.enablePlayerModes(sw);
	}
	/**
	 * metodo que se encargar de escribir la informacion en status messages
	 * @param text testo que quieres mostrar
	 */
	public void write(String text) {
		textArea.showMessage(text);
	}
	/**
	 * metodo encargado de modificar el mode del juegador
	 * @param p piece que quieres modificar
	 * @param m modo que quieres modificar
	 */
	public void cambiarInfoMode(Piece p,PlayerMode m){
		table.updateModes(p, m);
	}
	/**
	 * meotod que se encarga de cambiar el color en la tabla de player information
	 * @param p piece que quieres cambiar el color
	 * @param c color que quieres cambiar
	 */
	public void cambiarColorTabla(Piece p,Color c)
	{
		table.colorUpdat(p, c);
	}
	/**
	 * metodo que desactiva el boton restart para el modo miltiventana
	 */
	public void noViewResrtart()
	{
		quitPanel.destivarRestart();
	}
	/**
	 * metodo que se encarga de actualizar en player information la 
	 * informacion del numero de pieces 
	 * @param p piece del jugador.
	 * @param num numero de pieces del jugador
	 */
	public void setPiecesCount(Piece p,int num)
	{
		table.setNumPieces(p,num);
	}
	public void localPiece(Piece loPiece)
	{
		modeSelector.setLocalPiece(loPiece);
	}
	public void removeMesssage()
	{
		textArea.removeMessage();
	}
	/**
	 * metodo abstracto encargado de quitar el juego
	 */
	protected abstract void quitGame();
	/**
	 * metodo abstracto encargado de restart el juego
	 */
	protected abstract void restartGame();
	/**
	 * metodo abstracto el cual cambia el modo de un jugador
	 * @param p piece del jugador
	 * @param m modo del jugador a cambiar
	 */
	protected abstract void playerMode(Piece p,PlayerMode m);
	/**
	 * metodo que se encarga de hacer un movimiento random o inteligente.
	 * @param sw true: movimiento inteligente
	 * 			false: movimiento random
	 */			
	protected abstract void movimientoAuto(boolean sw);
	/**
	 * metodo abstracto modifica el color de una piece
	 * @param p piece del jugador
	 * @param c color para modificar
	 */
	protected abstract void colorUpdate(Piece p,Color c);

}
