package es.ucm.fdi.tp.practica6.cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.PlayCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.QuitCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.RestartCommand;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.practica6.response.ChangeTurnResponse;
import es.ucm.fdi.tp.practica6.response.ErrorResponse;
import es.ucm.fdi.tp.practica6.response.GameOverResponse;
import es.ucm.fdi.tp.practica6.response.GameStartResponse;
import es.ucm.fdi.tp.practica6.response.MoveEndResponse;
import es.ucm.fdi.tp.practica6.response.MoveStartResponse;
import es.ucm.fdi.tp.practica6.response.Response;
import es.ucm.fdi.tp.practica6.server.GameServer;

public class GameClient extends Controller implements Observable<GameObserver>{
	
	private int port;
	private String host;
	private List<GameObserver> observers;
	private Piece localPiece;
	private GameFactory gameFactory;
	private Connection connectioToServer;
	private boolean gameOver;
	
	
	public GameClient(String host, int port) throws Exception {
	super(null, null);
	// initialise the fields
	this.host=host;
	this.port=port;
	observers=new ArrayList<GameObserver>();
	connect();
	}
	public GameFactory getGameFactoty() { return gameFactory;}
	public Piece getPlayerPiece() {return localPiece; }
	
	@Override
	public void addObserver(GameObserver o) {
		// TODO Auto-generated method stub
		observers.add(o);
	
	}
	@Override
	public void removeObserver(GameObserver o) { 
		observers.remove(o);
	}

	@Override
	public void makeMove(Player p) {
	forwardCommand(new PlayCommand(p));
	}
	@Override
	public void stop() {
	forwardCommand(new QuitCommand());
	}
	@Override
	public void restart() {
	forwardCommand(new RestartCommand());
	}
	
	private void forwardCommand(Command cmd) {
	// if the game is over do nothing, otherwise
		try{
			connectioToServer.sendObject((Command)cmd);
		}catch(Exception e){}
		
	//
	}
	private void connect() throws Exception {
		connectioToServer = new Connection(new Socket(host, port));
		connectioToServer.sendObject((String)"connect");
		Object response = connectioToServer.getObject();
	
		if (response instanceof Exception) {
		throw (Exception) response;
		}
		try {
		gameFactory=(GameFactory)connectioToServer.getObject();
		localPiece =(Piece)connectioToServer.getObject();
		
		} catch (Exception e) {
		throw new GameError("Unknown server response: "+e.getMessage());
		}
		

	}
	public void start() {

		this.observers.add(new GameObserver() {
			
			@Override
			public void onMoveStart(Board board, Piece turn) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMoveEnd(Board board, Piece turn, boolean success) {
				// TODO Auto-generated method stub
			
			}
			
			@Override
			public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
				// TODO Auto-generated method stub
			
			}
			
			@Override
			public void onGameOver(Board board, State state, Piece winner) {
			 try {connectioToServer.stop();} catch (Exception e) {	}
			}
			
			@Override
			public void onError(String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChangeTurn(Board board, Piece turn) {
				// TODO Auto-generated method stub
				
			}
		});

		gameOver = false;
		while (!gameOver) {
				try {
					Response res = (Response)connectioToServer.getObject();
				
					for (GameObserver o : observers) {
						// execute the response on the observer o
						res.run(o);
					}
					
			} catch (ClassNotFoundException | IOException e) {}
		catch(Exception e){}
		}
		try {
			connectioToServer.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

}
