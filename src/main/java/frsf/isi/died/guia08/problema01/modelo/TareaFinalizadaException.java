package frsf.isi.died.guia08.problema01.modelo;

public class TareaFinalizadaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TareaFinalizadaException(Integer idTarea) {
		
		super("La tarea " + idTarea +" ya ha finalizado");
		
	}
}