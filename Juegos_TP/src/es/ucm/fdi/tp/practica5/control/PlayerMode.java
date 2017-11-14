package es.ucm.fdi.tp.practica5.control;

/**
 * clase que representa a un modo de jugador el cual puede eligir para 
 * realizar un movimientos:  Manual,Random y Inteligente.
 * @author Pablo-Por
 *
 */
public class PlayerMode {

	private String id;
	private String desc;
/**
 * contructora del un modo de jugador
 * @param id identificador del modo
 * @param desc nombre del modo de jugador
 */
	public PlayerMode(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	/**
	 * metodo que devueleve id del modo
	 * @return identificador
	 */
	public String getId() {
		return id;
	}
/**
 * metodo que devuelve la descripcion del modo de jugador
 * @return descripcion
 */
	public String getDesc() {
		return desc;
	}


}
