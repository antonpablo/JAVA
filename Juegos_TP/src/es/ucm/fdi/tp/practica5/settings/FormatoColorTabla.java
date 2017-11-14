package es.ucm.fdi.tp.practica5.settings;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * clase que coloca en PlayerInformation la información del color de la piece
 * @author Pablo-Por
 *
 */
public class FormatoColorTabla extends DefaultTableCellRenderer
	{ 
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Component componente;
		private List<Color> color;
	
		/**
		 * constructor que inicializa un lista de colores
		 */
		public FormatoColorTabla() {
			color=new ArrayList<Color>();
			for(int i=0;i<4;i++)
				color.add(i,Color.WHITE);
		}
		
		/**
		 * metodo que se encarga de añadir el color de una piece a la tabla de informacion
		 * @param pieceIndex indice de la piece
		 * @param c color de la piece
		 */
		public void getColores(int pieceIndex,Color c)
		{
			color.remove(pieceIndex);
			color.add(pieceIndex,c);
		}

		/**
		 * metodo que se encarga de dibujar en la tabla la lista de colores.
		 */
	    @Override
	    public Component getTableCellRendererComponent(JTable jTable1,Object value,boolean selected, boolean focused, int row, int column){
	    {
	    	componente=super.getTableCellRendererComponent(jTable1, value, selected, focused, row, column);
	       setEnabled(jTable1 == null || jTable1.isEnabled());
	        {
	        	for(int i=0;i<4;i++)
	        	{
	        		if (row==i)
	        		   componente.setBackground(color.get(i));
	        	}
	        }
	        super.getTableCellRendererComponent(jTable1, value, selected, focused, row, column);     
	        return this;
	    }
	    }

}
