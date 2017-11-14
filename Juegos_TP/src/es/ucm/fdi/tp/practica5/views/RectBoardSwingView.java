package es.ucm.fdi.tp.practica5.views;

import java.awt.Color;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * clase que implementa un tablero rectangulo para los diferentes juego 
 * @author Pablo-Por
 *
 */
public abstract class RectBoardSwingView extends SwingView{
		
	
	private static final long serialVersionUID = 1L;
	private BoardComponent boardComp;
	private boolean tableroActiDes;
	private boolean pantallaMouse;
		/**
		 * contructor inicializa la parte de SwingView de la interfaz grafica
		 * @param g
		 * @param c
		 * @param p
		 * @param random
		 * @param ai
		 */
		public RectBoardSwingView(Observable<GameObserver> g, Controller c, Piece p, Player random, Player ai) {
			
			
			super(g, c, p,random,ai);
			pantallaMouse=true;
		}
		
		/**
		 * metodo que inicializa las componentes del tablero
		 */
		@Override
		protected void initBoardGui() {
			
		
		boardComp = new BoardComponent() {
			
			private static final long serialVersionUID = 1L;
			@Override
			protected void mouseClicked(int row, int col, int mouseButton) {
				if (pantallaMouse)	
					handleMouseClick(row, col, mouseButton);
			}
			@Override
			protected Color getPiece(Piece p) {
				return getPieceColor(p);
			}
		
		};
		 setBoardArea(boardComp);  // install the board in the view
		
		}
		/**
		 * metodo que activa o desactiva el tablero
		 * @param sw true: activa el tablero
		 * 			false: desactiva el tablero
		 */
		protected void pantallaMouse(boolean sw)
		{
			pantallaMouse=sw;
		}
		
		/**
		 * metodo que redibuja el tablero de juego
		 */
		@Override
		protected void redrawBoard() {
		
			boardComp.redraw(getBoard());
		}
		/**
		 * metodo abstrato que recojo los eventos del raton
		 * @param row fila del evento
		 * @param col columaa del evento
		 * @param mouseButton boton que se ha selecionado.
		 */
		protected abstract void handleMouseClick(int row, int col, int mouseButton);
	
}
