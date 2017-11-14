package es.ucm.fdi.tp.practica6.cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

		Socket socket;
		ObjectOutputStream sale;
		ObjectInputStream entra;
	// throws …
		public Connection(Socket s)  throws Exception{
		this.socket = s;
		this.sale = new ObjectOutputStream( s.getOutputStream() );
		this.entra = new ObjectInputStream( s.getInputStream() );
		}
		
		public void sendObject(Object r) throws Exception{
		sale.writeObject(r);
		sale.flush();
		sale.reset();
		}
		public Object getObject() throws Exception {
		return entra.readObject();
		}
		
		public void stop() throws Exception{
		socket.close();
		}
}
