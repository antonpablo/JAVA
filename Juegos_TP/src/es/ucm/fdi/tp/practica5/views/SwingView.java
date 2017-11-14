package es.ucm.fdi.tp.practica5.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.control.*;
import es.ucm.fdi.tp.practica5.settings.SettingsPanel;
/**
 * clase que impleneta la interfaz grafica de todos los juegos
 * @author Pablo-Por
 *
 */
public abstract class SwingView extends JFrame
		implements GameObserver {

	
	private static final long serialVersionUID = 1963267749452568905L;

	
	private Controller ctrl;
	private Piece localPiece; //si es null se juega en esta vista y para hacer un movimineto automatico
	private Piece turn;
	private Board board;
	private List<Piece> pieces;
	private Map<Piece, Color> pieceColors;	
	private Map<Piece, PlayerMode> playerTypes;//PlayerMode
	private BoardComponent boardArea;
	private Player randomPlayer;
	private Player aiPlayer;
	private SettingsPanel settings;
	private boolean modoRandon;
	private boolean modoInteligent;
	@SuppressWarnings("serial")
	final private static List<Color> DEFAULT_COLORS = new ArrayList<Color>() {
		{
			add(Color.green);
			add(Color.red);
			add(Color.pink);
			add(Color.blue);
			add(Color.yellow);
			add(Color.magenta);
			add(Color.CYAN);
		}
	};
		
	/**
	 * contructora de SwingView inicializa la parte grafica del tablero
	 * añade los observadores del juego y el controlador del juego
	 * @param g abservadores del juego
	 * @param c controlador del juego
	 * @param p piece si esta en multiventana sino es null
	 * @param random jugador random
	 * @param ai jugador inteligente
	 */
	public SwingView(Observable<GameObserver> g, Controller c, Piece p, Player random, Player ai) {
		ctrl = c;
		localPiece = p;
		randomPlayer = random;
		aiPlayer = ai;
		initGUI();
		pieceColors= new HashMap<Piece,Color>(); 
		playerTypes=new HashMap<Piece, PlayerMode>();
		g.addObserver(this); // register as an observer
		
  
	}
	/**
	 * metodo inicializa la parte gráfica del tablero
	 */
	private void initGUI() {
		
		setSize(700, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initBoardGui();  
		modoRandon=true;
		modoInteligent=true;
		if ( randomPlayer==null)
			modoRandon=false;
		
			
		if	(aiPlayer==null)
			modoInteligent=false;

			
			settings = new SettingsPanel(modoRandon,modoInteligent) {
			private static final long serialVersionUID = 1L;
			@Override
			public void quitGame() {
				quitPressed();
			}
			@Override
			public void restartGame() {
				restartPressed();
			}
			@Override
			public void playerMode(Piece p, PlayerMode m) {
				cambiaInfo(p,m);	
			}
			@Override
			public void movimientoAuto(boolean sw) {
				autoMove(sw);
				
			}
			@Override
			public void colorUpdate(Piece p,Color c) {
				
				setPieceColor(p,c);
			}
		};
		
		if (localPiece!=null)
			settings.noViewResrtart(); //esconder boton restart
		add(settings, BorderLayout.EAST);
		setVisible(true);
		setLocationRelativeTo(null);
		
		
		}
	/**
	 * metodo para saber el turno de un jugador
	 * @return turno del jugador
	 */
	final protected Piece getTurn(){return turn;}
	/**
	 * metodo para saber el tablero del juego
	 * @return tablero del juego(board)
	 */
	final protected Board getBoard() { return board; }
	/**
	 * metodo para saber el color de una piece
	 * @param p piece que queremos saber el color
	 * @return color de la piece
	 */
	final protected Color getPieceColor(Piece p) { return pieceColors.get(p); }
	/**
	 * metod que devuleve una lista de pieces
	 * @return lista de pieces
	 */
	final protected List<Piece> getPieces(){return pieces;}
	/**
	 * metodo para guardar un color de una piece
	 * @param p piece del jugador
	 * @param c color que quieres poner a esa piece
	 */
	final protected void setPieceColor(Piece p,Color c)
	{
			pieceColors.put(p, c);
			redrawBoard();
			settings.cambiarColorTabla(p, c);
	}
	/**
	 * metodo que inicializa en la ventala la parte del tablero
	 * @param boardComp tablero del juego
	 */
	protected void setBoardArea(BoardComponent boardComp) {
		
		boardArea=boardComp;
		JPanel jpBoard = new JPanel(new BorderLayout());
		jpBoard.add(boardArea, BorderLayout.CENTER);
		add(boardArea, BorderLayout.CENTER);
		}
	/**
	 * metodo que añade un nuevo mensaje a Status messages 
	 * @param msg mensaje para añadir
	 */
	final protected void addMsg(String msg) {
		settings.write(msg);
		}
	
	/**
	 * metodo que realiza un movimiento manual
	 * @param manualPlayer player realiza movimiento manual
	 */
	final protected void decideMakeManualMove(Player manualPlayer) {
		
		try{ctrl.makeMove(manualPlayer);}catch(Exception e){}
		if(board.getPieceCount(turn)!=null )
		{
			settings.setPiecesCount(turn, board.getPieceCount(turn));
		}
			
	}
	/**
	 * 	metodo que dedice si un movimiento se hace auntomaticamente con los modos
	 * random o inteligente.
	 */
	private void decideMakeAutomaticMove() {
		
		
		if ( (playerTypes.get(turn).getId().equals("r") || playerTypes.get(turn).getId().equals("a")))
		{
			try{
				if (playerTypes.get(turn).getId().equals("r"))
					ctrl.makeMove(randomPlayer);
				else 
					ctrl.makeMove(aiPlayer);
			}catch(Exception e){}
			
			
			if ( board.getPieceCount(turn)!=null)	
				settings.setPiecesCount(turn, board.getPieceCount(turn));
				
		}
				
	}
	/**
	 * metodo abstracto que inicializa el tablero del juego
	 */
	protected abstract void initBoardGui();
	/**
	 * metodo abstracto que activa el tablero
	 */
	protected abstract void activateBoard();
	/**
	 * metodo abstracto que desactiva el tablero
	 */
	protected abstract void deActivateBoard();
	
	/**
	 * metodo que redibuja el tablero de juego
	 */
	protected abstract void redrawBoard();
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { handleOnGameStart(board,gameDesc,pieces,turn); }
			});
	}

	/**
	 * metodo que inicializa el juego.
	 * @param board tablero del juego
	 * @param gameDesc descripcion del juego
	 * @param pieces lista de pieces del juego
	 * @param turn turno de la piece que le toque jugar
	 */
	protected void handleOnGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		// TODO Auto-gener-ated method stub
		this.pieces = pieces;
		this.board=board;
		this.turn=turn;
		settings.setPieces(pieces);
		settings.localPiece(localPiece);
		PlayerMode pm=new PlayerMode("m","Manual");
		int colorAtelaorio= Utils.randomInt(DEFAULT_COLORS.size());
		settings.modoJuego(pm.getDesc());
		if (modoRandon)
			settings.modoJuego("Random");
		if (modoInteligent)
			settings.modoJuego("Inteligent");
			
		
		for(int i=0;i<pieces.size();i++)
		{
			pieceColors.put(pieces.get(i), DEFAULT_COLORS.get((colorAtelaorio + i) % (DEFAULT_COLORS.size())));
		  	settings.cambiarColorTabla(pieces.get(i), DEFAULT_COLORS.get((colorAtelaorio + i) % (DEFAULT_COLORS.size())));		
		  	if ( board.getPieceCount(pieces.get(i))!=null)	
				settings.setPiecesCount(pieces.get(i), board.getPieceCount(pieces.get(i)));
		  	playerTypes.put(pieces.get(i),pm );
		  	if(localPiece==null)
		  		{
		  			settings.setPieceMode(pieces.get(i));
		  			settings.cambiarInfoMode(pieces.get(i), pm);
		  		}
		}

		redrawBoard();
		// Set GameStart comments
		settings.write("Starting '" + gameDesc + "'");
		String pieceTurn = turn.toString();
		if (turn.equals(localPiece))
			pieceTurn += " (You)";
		settings.write("Turn for " + pieceTurn);
		activateBoard();
		//depende de si es multiple vista o unica
		String view = "";
		if (localPiece != null)
		{
			settings.setPieceMode(localPiece);
			if (!localPiece.equals(turn))
			   {

				deActivateBoard();
				if (aiPlayer!=null && randomPlayer!=null)
					settings.playermodes(false);
				}
			view = "(" + localPiece + ")";
			settings.cambiarInfoMode(localPiece, playerTypes.get(localPiece));
		}
		  
	
		this.setTitle("Board Games: " + gameDesc + " " + view);
	}
	
	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { handleOnGameOver(board,state,winner); }
			});
		
	}
	/**
	 * metodo que se activa cuando el juego a terminado 
	 * @param board tablero del juego
	 * @param state estado del juego (ganador o empate)
	 * @param winner si hay un ganador piece del ganador
	 */
	protected void handleOnGameOver(Board board, State state, Piece winner) {
		// TODO Auto-generated method stub
		settings.write("\n Game Over!!\n");
		settings.write("Game Status: " + state);
		if (state == State.Won) 
			settings.write("Winner: " + winner);

	}
	
	@Override
	public void onMoveStart(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { handleOnMoveStart(board,turn); }
			});
		
	}
	/**
	 * metodo que se activa cuando empiza a realizarse un movimiento
	 * @param board tablero del juego
	 * @param turn turno del jugador
	 */
	protected void handleOnMoveStart(Board board, Piece turn) {
		// TODO Auto-generated method stub
		this.board=board;
		this.turn=turn;
		
		
	}
	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { handleOnMoveEnd(board,turn,success); }
			});
	}
	/**
	 *  metodo que se activa cuando el movimiento a finalizado
	 * @param board tablero del juego
	 * @param turn turno del jugador 
	 * @param success si es movimineto a sido exitoso o no.
	 */
	protected void handleOnMoveEnd(Board board, Piece turn, boolean success) {
		// TODO Auto-generated method stub
		this.board=board;
		this.turn=turn;
		botonesVisibles(true);
		
		redrawBoard();
		
	}
	@Override
	public void onChangeTurn(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { handleOnChangeTurn(board,turn); }
			});
	
		
	}
	/**
	 * metodo que se activa cuando un cambio de turno en el juego
	 * @param board tablero del juego
	 * @param turn turno del jugador
	 */
	protected void handleOnChangeTurn(Board board, Piece turn) {
		// TODO Auto-generated method stub
		this.turn=turn;
		this.board=board;
		String pieceTurn = turn.toString();
		if (turn.equals(localPiece))
			pieceTurn += " (You)";
		settings.write("Turn for " + pieceTurn);
		boolean jugadorAleatorio=false;
		
		if ( board.getPieceCount(turn)!=null)	
			settings.setPiecesCount(turn, board.getPieceCount(turn));
		
		if (playerTypes.get(turn).getId().equals("m"))
				activateBoard();
		else
			{
				deActivateBoard();
				botonesVisibles(false);
				jugadorAleatorio=true;
			}
		
		
		
		if (localPiece!=null && !turn.equals(localPiece))
			{
			deActivateBoard();
			botonesVisibles(false);
		}
		else if (localPiece!=null && turn.equals(localPiece) && jugadorAleatorio==false)
		{
			botonesVisibles(true);
		}
		decideMakeAutomaticMove();
		redrawBoard();
		
	}
	@Override
	public void onError(String msg) {
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { handleOnError(msg); }
			});
	}
	/**
	 * metodo que informa con un MessageDialog de un error del juego
	 * @param msg mensaje de error
	 */
	protected void handleOnError(String msg) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null,
				"Se a producido un error en la ejecucion del juego : \n"+msg,"Error de jugador",
				JOptionPane.WARNING_MESSAGE);
		botonesVisibles(true);
	}
	/**
	 * metodo que cambia el modo de jugador de una piece
	 * @param p piece del jugador
	 * @param m modo de juego.
	 */
	public void cambiaInfo(Piece p, PlayerMode m)
	{
		
			
			playerTypes.put(p, m);
			settings.cambiarInfoMode(p,m);
			if (p.equals(turn))
			{
	
				if(m.getId().equals("a") || m.getId().equals("r"))
					deActivateBoard();
			}
			try{
				decideMakeAutomaticMove();
			}catch(Exception e){}
		
		
		
		
	}
	/**
	 * metodo que realiza un movimiento automatico
	 * @param sw true: movimiento inteligente
	 * 			false: movimiento random
	 */			
	private void autoMove(boolean sw)
	{
		try{
			if (sw)
				ctrl.makeMove(aiPlayer);
			else 
				ctrl.makeMove(randomPlayer);
			
		}catch(Exception e){}
		
	}
	/**
	 * metodo que implenta el dialogo de quit juego
	 */
	public void quitPressed() {
		
		int response = JOptionPane.showConfirmDialog(null,
				"estas seguro de que quieres quitar el juego?", "Confirfar Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			try{ctrl.stop();}catch(Exception e){}
			System.exit(0);
		}
	}
	/**
	 * metodo que implenta el dialogo de restart juego
	 */
	public void restartPressed() {
		int response = JOptionPane.showConfirmDialog(null,
				"estas seguro de que quieres restart el juego?", "Confirmar Restart",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			settings.removeMesssage();
			try{ctrl.restart();}catch(Exception e){}
		}
	}
	/**
	 * metodo que comprueba si una casilla es valida
	 * @param row fila de la casilla
	 * @param col columna de la casilla
	 * @return true: si es una casilla del jugador
	 * 		false; sino es una casilla del jugador
	 */
	protected boolean comprobarCasillaValida(int row, int col) {
		
		botonesVisibles(false);
		if(turn.equals(board.getPosition(row, col)))
			return true;	
		else
		{
			handleOnError("error la casilla selecionada no contiene una de tus fichas");
			return false;
		}
			
	}
	/**
	 *metodo para cambiar la visibilidad de los botones  
	 * @param sw visibilidad de los botones
	 */
	protected void botonesVisibles(boolean sw) {
		
		
		if (aiPlayer!=null && randomPlayer!=null)
			settings.playermodes(sw);
		if (localPiece==null)
		{
			settings.restart(sw);
			settings.quit(sw);
		}	
	}
}
