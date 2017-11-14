package es.ucm.fdi.tp.practica6.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.practica6.cliente.Connection;
import es.ucm.fdi.tp.practica6.response.ChangeTurnResponse;
import es.ucm.fdi.tp.practica6.response.ErrorResponse;
import es.ucm.fdi.tp.practica6.response.GameOverResponse;
import es.ucm.fdi.tp.practica6.response.GameStartResponse;
import es.ucm.fdi.tp.practica6.response.MoveEndResponse;
import es.ucm.fdi.tp.practica6.response.MoveStartResponse;
import es.ucm.fdi.tp.practica6.response.Response;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameServer extends Controller implements GameObserver{
	
	private int numPlayers;
	private int numOfConnectedPlayers;
	private GameFactory gameFactory;
	private List<Connection> clients;
	volatile private ServerSocket server;
	volatile private boolean stopped;
	volatile private boolean gameOver;
	private int port;
	private JTextArea jtaScreen;
	private boolean primerJuego;
	volatile private ThreadServer tt; 

	public GameServer(GameFactory gameFactory, List<Piece> pieces, int port) {
	super(new Game(gameFactory.gameRules()), pieces);
	this.port=port;
	this.primerJuego=true;
	this.numPlayers=pieces.size();
	this.numOfConnectedPlayers=0;
	clients=new ArrayList<Connection>();
	this.gameFactory=gameFactory;
	game.addObserver(this);
	
	
	}
	
	
	public synchronized void makeMove(Player player) {
		try {super.makeMove(player);
			} catch (GameError e) { }
		}
		@Override
		public synchronized void stop() {
		try { super.stop(); } catch (GameError e) { }
		}
		@Override
		public synchronized void restart() {
		try { super.restart(); } catch (GameError e) { }
		}
		@Override
		public void start()  {
		controlGUI();
		startServer();	
		
		
		}
		
		private void controlGUI() {
			
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
				@Override
					public void run() { constructGUI(); }
				});
			} catch (Exception e) {
			throw new GameError("Something went wrong when constructing the GUI");
			}
			}
			private void constructGUI() {
			JFrame window = new JFrame("Game Server");
			JPanel ser=new JPanel();
			
			jtaScreen = new JTextArea();
			jtaScreen.setEditable(false);
			jtaScreen.setPreferredSize(new Dimension(700, 350));
			JScrollPane jspScroll = new JScrollPane(jtaScreen, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			ser.add(jspScroll);
			
			
			// quit button
			JButton quitButton = new JButton("Stop Sever");
			quitButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					pararServidor();
					
				}			
			});
			quitButton.setSize(100, 50);
			ser.add(quitButton,BorderLayout.CENTER);

			window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			window.add(ser, BorderLayout.CENTER);
			window.setPreferredSize(new Dimension(750, 450));
			window.pack();
			window.setVisible(true);
			}
			private void log(String msg) {
			
				SwingUtilities.invokeLater(new Runnable() {
					public void run() { jtaScreen.append(msg + "\n");  }
					});
			}
			private void startServer() {
				try{
					server = new ServerSocket(port);
					stopped = false;
					while (!stopped) {
					try {
						if (numOfConnectedPlayers< numPlayers)
						{
							// 1. accept a connection into a socket s
							Socket conecta=server.accept();
							// 2. log a corresponding message
						
							log("Conectado a "+conecta.getInetAddress()+":"+conecta.getPort());
							// 3. call handleRequest(s) to handle the request
							handleRequest(conecta);
						}
					
					} catch (Exception e) {
					if (!stopped) {
					log("error while waiting for a connection: " + e.getMessage());
					}
				}
			}
			}catch(Exception e){System.err.println("error servidor");}
				
				
		}
		//manejar peticiones del cliente
		private void handleRequest(Socket s) throws Exception {
				try {
				Connection c = new Connection(s);
				Object clientRequest = c.getObject();
				log((String)clientRequest);
				if ( !(clientRequest instanceof String) &&
				!((String) clientRequest).equalsIgnoreCase("Connect") ) {
					c.sendObject(new GameError("Invalid Request"));
					c.stop();
					return;
				}
				
					numOfConnectedPlayers++;
					clients.add(c);
				
					// enviar al cliente String OK.. la gamefactory y la piece a usar
					c.sendObject((String)"OK");
					c.sendObject((GameFactory)gameFactory);
					c.sendObject((Piece)pieces.get(numOfConnectedPlayers-1));
					
					// 4. …iniciar el juego x primera vez start
					//y si es una segunda llamar a restart
					if (numOfConnectedPlayers== numPlayers)
					{
						if(primerJuego)
						{
							primerJuego=false;
							super.start();
						}
						else
							restart();			
					}
					startClientListener(c);
				
				} catch (IOException | ClassNotFoundException _e) { }
		}
		//recibir comandos del cliente
		private void startClientListener(Connection c) {
			gameOver = false;
			new Thread(new Runnable() {
				public void run() {
					while (!stopped && !gameOver) {
						try {
						Command cmd;
						// 1. read a Command
					   cmd =(Command) c.getObject();
						// 2. execute the command
						cmd.execute(GameServer.this);
						
						} catch (ClassNotFoundException | IOException e) {
						if (!stopped && !gameOver) {
						// stop the game (not the server)
							try {
								c.stop();
								log("se ha desconectado el cliente"+c.toString());
								clients.remove(c);
								numOfConnectedPlayers--;
								gameOver=true;
								
							} catch (Exception e1) {}
						}
					} catch (Exception e) {} 
					}
				}
				}).start();
				
}
		
	private void pararServidor()
	{
		
			stopped=true;
			pararJuego();
			try{server.close();}catch(Exception e){}
			System.exit(0);
			
	}
	private void pararJuego()
	{
		stop();
		numOfConnectedPlayers=0;
		jtaScreen.setText(null);
		gameOver=true;
		for(int i=0;i<clients.size();i++)
			try{
				clients.get(i).stop();
				clients.remove(i);
			}catch(Exception e){}
		
	}
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		// TODO Auto-generated method stub
		forwardNotification(new GameStartResponse(board, gameDesc, pieces, turn));
		
	}
	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		forwardNotification(new GameOverResponse(board,state,winner)); 
		pararJuego();
		
	}
	@Override
	public void onMoveStart(Board board, Piece turn) {
		forwardNotification(new MoveStartResponse(board,turn)); 
		
	}
	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		// TODO Auto-generated method stub
		forwardNotification(new MoveEndResponse(board,turn,success));
	}
	@Override
	public void onChangeTurn(Board board, Piece turn) {
		// TODO Auto-generated method stub
		forwardNotification(new ChangeTurnResponse(board, turn));
		
	}
	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		forwardNotification(new ErrorResponse(msg));
	}
	
	private void forwardNotification(Response r) {
		 
		for (Connection c:clients)
			try {c.sendObject(r);}catch(Exception e){	}				
		
	}
}
