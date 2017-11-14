package es.ucm.fdi.tp.practica5.settings;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.TitledBorder;
/**
 * clase que muestra en settings los status messages del juego
 * @author Pablo-Por
 *
 */
public class StatusMessages extends JPanel {

	
	private static final long serialVersionUID = 1L;

	private JTextArea jtaScreen;
	private String message;
/**
 * contructor se status messages
 */
	StatusMessages() {
		initUI();
	}
/**
 * metodo que inicializa la parte grafica 
 */
	public final void initUI() {
		setBorder(new TitledBorder(null, "Status Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		message = new String();
		jtaScreen = new JTextArea(message);
		jtaScreen.setEditable(false);

		JScrollPane jspScroll = new JScrollPane(jtaScreen, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(jspScroll);

		

	}
/**
 * metodo que se encarga de actualizar los mensajes de status message
 * @param message message que se quiere poner en este apartado de settings
 */
	public void showMessage(String message) {
		jtaScreen.append(message + "\n");
	}
	public void removeMessage()
	{
		jtaScreen.setText(null);
	}
}
