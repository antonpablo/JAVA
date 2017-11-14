package es.ucm.fdi.tp.practica5.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
/**
 * clase que dibuja la parte gráfica del tablero
 * @author Pablo-Por
 *
 */
@SuppressWarnings("serial")
public abstract class BoardComponent extends JComponent {

	private int _CELL_HEIGHT = 50;
	private int _CELL_WIDTH = 50;

	private int rows;
	private int cols;
	private Board b;
	/**
	 * contructor que inicializa los eventos del raton
	 * y el boolean si esta activada o desactivada la pantalla
	 */
	public  BoardComponent()
	{
		initGUIMouse();
	}

/**
 * metodo que pinta las pieces del tablero
 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(cols!=0 && rows!=0)
		{
			_CELL_WIDTH = this.getWidth() / cols;
			_CELL_HEIGHT = this.getHeight() / rows;

			for (int i = 0; i < rows; i++)
				for (int j = 0; j < cols; j++)
					drawCell(i, j, g);
		}

		
	}

	/**
	 * metodo que dibuja las pieces en el tablero
	 * @param row fila de la piece
	 * @param col columna de la piece
	 * @param g parte grafica 
	 */
	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);

		if (b.getPosition(row, col)!=null)
		{
			if (getPiece(b.getPosition(row, col))!=null)
			{
				g.setColor(getPiece(b.getPosition(row, col)));
			}
			
			else
				g.setColor(Color.black);
			g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);	
			g.setColor(Color.black);
			g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
		}	
 
	}
/**
 * metodo que redibuja el tablero
 * @param b tablero del juego
 */
	public void redraw(Board b) {
		this.b=b;
		this.rows=b.getRows();
		this.cols=b.getCols();
		repaint();
	}

/**
 * metodo que inicia los eventos del raton en el tablero
 */
	
	private void initGUIMouse() {

		addMouseListener(new MouseListener() {

			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				mouse( e.getY()/_CELL_HEIGHT,e.getX()/_CELL_WIDTH, e.getButton());
			}

			

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			
		});
		
	}
	/**
	 * metodo que determina si una pantalla esta activada o desactivada
	 * @param x fila del ration
	 * @param y columna del raton
	 * @param m evento del raton (numero del evento)
	 */
	private void mouse(int x ,int y,int m)
	{//desactivar la pantalla cuando este en modo random o inteligent
	//y cuando este en multiwindo

			mouseClicked(x, y, m);
	}


	/**
	 * metodo abstracto que devuelve el color de una piece para mostrarla en el tablero
	 * @param p piece del jugador
	 * @return color de la piece del jugador
	 */
	protected abstract Color getPiece(Piece p);
	/**
	 * metodo abstracto que recoje los eventos del raton en el tablero
	 * @param row fila del evento
	 * @param col columna del evento
	 * @param mouseButton boton que se ha selecionado
	 */
	protected abstract void mouseClicked(int row, int col, int mouseButton);



	

}
